import { useEffect, useRef, useState } from 'preact/hooks';
import Form from '@rjsf/core';
import validator from '@rjsf/validator-ajv8';
import { customTemplates, customWidgets } from '../sidepanel/rjsf-theme/index.js';
import '../sidepanel/rjsf-github.css';
import { extractBetterActionFromInput } from '../utils/yaml-parser.js';
import { resolve, resolveObject } from '../utils/interpolate.js';

export function App() {
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const [schemaData, setSchemaData] = useState(null);
  const [submitting, setSubmitting] = useState(false);
  const [submitResult, setSubmitResult] = useState(null);
  const standardInputsRef = useRef([]);
  const formKeyRef = useRef('better_action_form');

  const params = new URLSearchParams(window.location.search);
  const owner = params.get('owner') || '';
  const repo = params.get('repo') || '';
  const ref = params.get('ref') || 'main';
  const workflowPath = params.get('workflowPath') || '';
  const workflowFilename = params.get('workflowFilename') || '';

  useEffect(() => {
    (async () => {
      try {
        const secretsResp = await sendMsg('GET_SECRETS');
        const secrets = secretsResp.secrets || {};

        const workflowResp = await sendMsg('FETCH_WORKFLOW', { owner, repo, path: workflowPath, ref });
        if (!workflowResp.success) throw new Error(workflowResp.error);

        const parsed = extractBetterActionFromInput(workflowResp.content);
        if (!parsed?.formFileRef) throw new Error('No Better Action form reference found.');
        standardInputsRef.current = parsed.standardInputs || [];
        formKeyRef.current = parsed.formKey || 'better_action_form';

        const formResp = await sendMsg('FETCH_WORKFLOW', { owner, repo, path: parsed.formFileRef, ref });
        if (!formResp.success) throw new Error('Failed to fetch form: ' + formResp.error);

        const rawJson = JSON.parse(formResp.content);
        const { schema, uiSchema, formData } = normalizeRjsfSchema(rawJson);

        const resolvedSchema = await resolveDynamicDatasources(schema, uiSchema, secrets);

        setSchemaData({
          schema: resolvedSchema,
          uiSchema: uiSchema || {},
          formData: formData || {}
        });
        setLoading(false);
      } catch (e) {
        setError(e.message);
        setLoading(false);
      }
    })();
  }, []);

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

      const resp = await sendMsg('DISPATCH_WORKFLOW', { owner, repo, workflowId: workflowFilename, ref, inputs: result });

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
    <div class="app" style="max-width: 680px; margin: 0 auto; padding: 20px;">
      <header class="app-header" style="margin-bottom: 20px; border-bottom: 1px solid #30363d; padding-bottom: 12px;">
        <div class="workflow-info" style="font-size: 13px; color: #8b949e;">
          <span class="repo" style="color: #58a6ff; font-weight: 600;">{owner}/{repo}</span>
          <span class="sep" style="margin: 0 6px;">·</span>
          <span class="ref">{ref}</span>
          <span class="sep" style="margin: 0 6px;">·</span>
          <span class="file">{workflowFilename}</span>
        </div>
      </header>

      {loading && (
        <div class="state-panel" style="text-align: center; padding: 30px; color: #8b949e;">
          <p>Loading workflow schema...</p>
        </div>
      )}

      {!loading && error && (
        <div class="sp-alert error" style="margin-bottom: 16px;">
          <p class="error-title" style="font-weight: 600;">Failed to load</p>
          <p>{error}</p>
        </div>
      )}

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
            <p style="text-align: center; margin-top: 12px; color: #8b949e;">Dispatching workflow...</p>
          )}
        </div>
      )}

      {submitResult && (
        <div class={`sp-alert ${submitResult.type}`} style="margin-top: 16px;">
          {submitResult.message}
        </div>
      )}
    </div>
  );
}

function normalizeRjsfSchema(json) {
  if (json.schema && typeof json.schema === 'object') {
    return {
      schema: json.schema,
      uiSchema: json.uiSchema || {},
      formData: json.formData || json.data || {}
    };
  }
  if (json.type === 'object' || json.properties) {
    return {
      schema: json,
      uiSchema: json.uiSchema || {},
      formData: json.formData || {}
    };
  }
  return { schema: { type: 'object', properties: {} }, uiSchema: {}, formData: {} };
}

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
