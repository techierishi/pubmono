import { useEffect, useRef, useState } from 'preact/hooks';
import Form from '@rjsf/core';
import validator from '@rjsf/validator-ajv8';
import { customTemplates, customWidgets } from '../rjsf-theme/index.js';
import { extractBetterActionFromInput } from '../../utils/yaml-parser.js';
import { resolve, resolveObject } from '../../utils/interpolate.js';

export function FormView({
  workflowInfo,
  onBack,
  branches = []
}) {
  const [selectedBranch, setSelectedBranch] = useState(workflowInfo?.ref || 'master');
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const [schemaData, setSchemaData] = useState(null); // { schema, uiSchema, formData }
  const [submitting, setSubmitting] = useState(false);
  const [submitResult, setSubmitResult] = useState(null);

  const standardInputsRef = useRef([]);
  const formKeyRef = useRef('better_action_form');

  const { owner, repo, workflowFilename, workflowPath } = workflowInfo || {};

  useEffect(() => {
    loadWorkflowForm();
  }, [owner, repo, workflowPath, selectedBranch]);

  async function loadWorkflowForm() {
    if (!owner || !repo || !workflowPath) {
      setError('Missing workflow information.');
      setLoading(false);
      return;
    }

    setLoading(true);
    setError(null);
    setSchemaData(null);
    setSubmitResult(null);

    try {
      const secretsResp = await sendMsg('GET_SECRETS');
      const secrets = secretsResp.secrets || {};

      const workflowResp = await sendMsg('FETCH_WORKFLOW', {
        owner,
        repo,
        path: workflowPath,
        ref: selectedBranch
      });

      if (!workflowResp.success) {
        throw new Error(workflowResp.error || 'Failed to fetch workflow file');
      }

      const parsed = extractBetterActionFromInput(workflowResp.content);
      standardInputsRef.current = parsed?.standardInputs || [];
      formKeyRef.current = parsed?.formKey || 'better_action_form';

      if (parsed?.formFileRef) {
        // Advanced Form definition found
        const formResp = await sendMsg('FETCH_WORKFLOW', {
          owner,
          repo,
          path: parsed.formFileRef,
          ref: selectedBranch
        });

        if (!formResp.success) {
          throw new Error('Failed to fetch form definition: ' + formResp.error);
        }

        const rawJson = JSON.parse(formResp.content);
        const { schema, uiSchema, formData } = normalizeRjsfSchema(rawJson);

        // Resolve any dynamic datasources (choicesByUrl)
        const resolvedSchema = await resolveDynamicDatasources(schema, uiSchema, secrets);

        setSchemaData({
          schema: resolvedSchema,
          uiSchema: uiSchema || {},
          formData: formData || {}
        });
      } else {
        // Fallback: Generate a standard JSON Schema from GitHub inputs
        const standardSchema = generateStandardSchema(parsed?.standardInputs || []);
        setSchemaData({
          schema: standardSchema,
          uiSchema: {},
          formData: {}
        });
      }
    } catch (err) {
      setError(err.message);
    } finally {
      setLoading(false);
    }
  }

  async function handleSubmit({ formData }) {
    setSubmitting(true);
    setSubmitResult(null);

    try {
      const data = formData || {};
      const result = {};
      const customValues = {};

      for (const [key, val] of Object.entries(data)) {
        const strVal = Array.isArray(val) ? val.join(',') : String(val ?? '');
        if (standardInputsRef.current.includes(key)) {
          result[key] = strVal;
        } else {
          customValues[key] = strVal;
        }
      }

      if (Object.keys(customValues).length > 0) {
        result[formKeyRef.current] = JSON.stringify(customValues);
      }

      const resp = await sendMsg('DISPATCH_WORKFLOW', {
        owner,
        repo,
        workflowId: workflowFilename,
        ref: selectedBranch,
        inputs: result
      });

      if (resp.success) {
        setSubmitResult({ type: 'success', message: 'Workflow dispatched successfully! ⚡' });
      } else {
        setSubmitResult({ type: 'error', message: resp.error || 'Dispatch failed' });
      }
    } catch (e) {
      setSubmitResult({ type: 'error', message: e.message });
    } finally {
      setSubmitting(false);
    }
  }

  return (
    <div class="sp-form-view">
      {/* Navigation Header */}
      <div class="sp-form-nav">
        <button class="sp-btn-back" onClick={onBack} title="Back to workflow list">
          ← Workflows
        </button>

        <div class="sp-form-branch-select">
          <label style="color: var(--text-muted);">Branch:</label>
          <select
            class="sp-select"
            value={selectedBranch}
            onChange={(e) => setSelectedBranch(e.target.value)}
          >
            {branches.length > 0 ? (
              branches.map(b => (
                <option key={b} value={b}>{b}</option>
              ))
            ) : (
              <option value={selectedBranch}>{selectedBranch}</option>
            )}
          </select>
        </div>
      </div>

      <div class="sp-form-scroll">
        <div style="margin-bottom: 14px;">
          <h2 style="font-size: 15px; font-weight: 600; color: var(--text-primary);">
            {workflowInfo?.workflowName || workflowFilename}
          </h2>
          <span style="font-size: 11px; color: var(--text-muted); font-family: monospace;">
            {workflowPath}
          </span>
        </div>

        {/* Loading Spinner */}
        {loading && (
          <div class="sp-empty-state">
            <div class="sp-spinner"></div>
            <span style="font-size: 12px;">Loading form schema...</span>
          </div>
        )}

        {/* Error Banner */}
        {!loading && error && (
          <div class="sp-alert error" style="margin-bottom: 14px;">
            <span>⚠️ {error}</span>
          </div>
        )}

        {/* Success / Error Submit Result */}
        {submitResult && (
          <div class={`sp-alert ${submitResult.type}`} style="margin-bottom: 14px; flex-direction: column; align-items: flex-start;">
            <div style="display:flex; align-items:center; gap:6px;">
              <span>{submitResult.type === 'success' ? '✅' : '❌'}</span>
              <span style="font-weight: 600;">{submitResult.message}</span>
            </div>
            {submitResult.type === 'success' && (
              <div style="margin-top: 6px; display:flex; gap:8px;">
                <a
                  href={`https://github.com/${owner}/${repo}/actions/workflows/${workflowFilename}`}
                  target="_blank"
                  rel="noreferrer"
                  style="color: var(--accent-blue); font-size: 11px; text-decoration: underline;"
                >
                  View workflow runs on GitHub ↗
                </a>
              </div>
            )}
          </div>
        )}

        {/* RJSF Form Rendering */}
        {!loading && !error && schemaData && (
          <div class="rjsf-form-container">
            <Form
              schema={schemaData.schema}
              uiSchema={schemaData.uiSchema}
              formData={schemaData.formData}
              validator={validator}
              templates={customTemplates}
              widgets={customWidgets}
              onSubmit={handleSubmit}
              disabled={submitting}
              showErrorList={false}
            />

            {submitting && (
              <div class="sp-empty-state" style="padding: 16px;">
                <div class="sp-spinner"></div>
                <span>Dispatching workflow via GitHub REST API...</span>
              </div>
            )}
          </div>
        )}
      </div>
    </div>
  );
}

/**
 * Normalizes input JSON into standard RJSF { schema, uiSchema, formData }
 */
function normalizeRjsfSchema(json) {
  if (json.schema && typeof json.schema === 'object') {
    return {
      schema: json.schema,
      uiSchema: json.uiSchema || {},
      formData: json.formData || json.data || {}
    };
  }

  // If json is a direct JSON Schema (root type is object or properties exist)
  if (json.type === 'object' || json.properties) {
    return {
      schema: json,
      uiSchema: json.uiSchema || {},
      formData: json.formData || {}
    };
  }

  // Fallback / legacy converter if old SurveyJS format is encountered
  if (json.elements || json.pages) {
    return convertLegacySurveyToSchema(json);
  }

  return { schema: { type: 'object', properties: {} }, uiSchema: {}, formData: {} };
}

/**
 * Converts legacy SurveyJS elements to JSON Schema for backwards compatibility
 */
function convertLegacySurveyToSchema(surveyJson) {
  const elements = surveyJson.elements || (surveyJson.pages?.[0]?.elements) || [];
  const properties = {};
  const uiSchema = {};

  for (const el of elements) {
    const name = el.name;
    if (!name) continue;

    if (el.type === 'comment') {
      properties[name] = { type: 'string', title: el.title || name };
      uiSchema[name] = { 'ui:widget': 'textarea' };
    } else if (el.type === 'tagbox') {
      properties[name] = {
        type: 'array',
        title: el.title || name,
        items: { type: 'string', enum: el.choices || [] },
        uniqueItems: true
      };
      uiSchema[name] = { 'ui:widget': 'checkboxes' };
      if (el.choicesByUrl) {
        uiSchema[name]['choicesByUrl'] = el.choicesByUrl;
        uiSchema[name]['fetchConfig'] = el.fetchConfig;
      }
    } else if (el.type === 'dropdown') {
      properties[name] = {
        type: 'string',
        title: el.title || name,
        enum: el.choices || []
      };
      uiSchema[name] = { 'ui:widget': 'select' };
    } else if (el.type === 'boolean') {
      properties[name] = { type: 'boolean', title: el.title || name, default: false };
    } else {
      properties[name] = { type: 'string', title: el.title || name, default: el.defaultValue || '' };
    }
  }

  return {
    schema: {
      type: 'object',
      title: surveyJson.title || 'Workflow Form',
      description: surveyJson.description || '',
      properties
    },
    uiSchema,
    formData: {}
  };
}

/**
 * Generates standard JSON Schema from GitHub Actions native workflow inputs
 */
function generateStandardSchema(inputsList) {
  const properties = {};
  for (const inputName of inputsList) {
    properties[inputName] = {
      type: 'string',
      title: inputName
    };
  }
  return {
    type: 'object',
    title: 'Standard Workflow Inputs',
    description: inputsList.length ? 'Fill in workflow dispatch inputs below:' : 'This workflow takes no additional inputs.',
    properties
  };
}

/**
 * Resolves external API datasources defined in schema or uiSchema
 */
async function resolveDynamicDatasources(schema, uiSchema, secrets) {
  const cloneSchema = JSON.parse(JSON.stringify(schema));
  if (!cloneSchema.properties) return cloneSchema;

  for (const [propName, propDef] of Object.entries(cloneSchema.properties)) {
    const uiDef = uiSchema?.[propName] || {};
    const cbu = uiDef.choicesByUrl || propDef.choicesByUrl || uiDef['ui:dataSource'];
    if (!cbu?.url) continue;

    try {
      const fetchConfig = uiDef.fetchConfig || propDef.fetchConfig || {};
      const method = fetchConfig.method || 'GET';
      const headers = fetchConfig.headers ? await resolveObject(fetchConfig.headers, secrets) : {};
      const resolvedUrl = await resolve(cbu.url, secrets);

      const resp = await sendMsg('FETCH_DATASOURCE', { url: resolvedUrl, method, headers });
      if (resp.success) {
        const data = getByPath(resp.data, cbu.path);
        const list = Array.isArray(data) ? data : [];
        const values = list.map(item => String(cbu.valueName ? item[cbu.valueName] : item));
        const labels = list.map(item => String(cbu.titleName ? item[cbu.titleName] : item));

        if (propDef.type === 'array' && propDef.items) {
          propDef.items.enum = values;
          propDef.items.enumNames = labels;
        } else {
          propDef.enum = values;
          propDef.enumNames = labels;
        }
      }
    } catch (e) {
      console.warn(`Could not resolve dynamic datasource for ${propName}:`, e);
    }
  }

  return cloneSchema;
}

function sendMsg(type, payload) {
  return new Promise((resolve) => {
    chrome.runtime.sendMessage({ type, payload }, resolve);
  });
}

function getByPath(obj, path) {
  if (!path) return obj;
  return path.split('.').reduce((acc, key) => acc?.[key], obj);
}
