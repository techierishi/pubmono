import { useEffect, useRef, useState } from 'preact/hooks';
import { Model } from 'survey-core';
import { renderSurvey } from 'survey-js-ui';
import { extractBetterActionFromInput } from '../../utils/yaml-parser.js';
import { resolve, resolveObject } from '../../utils/interpolate.js';
import { githubDarkTheme } from '../github-theme.js';

export function FormView({
  workflowInfo,
  onBack,
  branches = []
}) {
  const [selectedBranch, setSelectedBranch] = useState(workflowInfo?.ref || 'master');
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const [survey, setSurvey] = useState(null);
  const [standardInputs, setStandardInputs] = useState(null);
  const [standardFormValues, setStandardFormValues] = useState({});
  const [submitting, setSubmitting] = useState(false);
  const [submitResult, setSubmitResult] = useState(null);

  const containerRef = useRef(null);
  const standardInputsRef = useRef([]);
  const formKeyRef = useRef('better_action_form');

  const { owner, repo, workflowFilename, workflowPath } = workflowInfo || {};

  useEffect(() => {
    loadWorkflowForm();
  }, [owner, repo, workflowPath, selectedBranch]);

  useEffect(() => {
    if (survey && containerRef.current) {
      renderSurvey(survey, containerRef.current);
      return () => {
        try { survey.dispose(); } catch (_) {}
      };
    }
  }, [survey]);

  async function loadWorkflowForm() {
    if (!owner || !repo || !workflowPath) {
      setError('Missing workflow information.');
      setLoading(false);
      return;
    }

    setLoading(true);
    setError(null);
    setSurvey(null);
    setStandardInputs(null);
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
        // Advanced SurveyJS Form found
        const formResp = await sendMsg('FETCH_WORKFLOW', {
          owner,
          repo,
          path: parsed.formFileRef,
          ref: selectedBranch
        });

        if (!formResp.success) {
          throw new Error('Failed to fetch advanced form: ' + formResp.error);
        }

        const model = new Model(JSON.parse(formResp.content));
        model.applyTheme(githubDarkTheme);
        model.showCompletedPage = false;

        await preloadDatasources(model, secrets);

        model.onCompleting.add(async (sender, options) => {
          options.allowComplete = false;
          setSubmitting(true);
          setSubmitResult(null);

          try {
            const data = sender.data;
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
              model.doComplete();
              setSubmitResult({ type: 'success', message: 'Workflow dispatched successfully! ⚡' });
            } else {
              setSubmitResult({ type: 'error', message: resp.error || 'Dispatch failed' });
            }
          } catch (e) {
            setSubmitResult({ type: 'error', message: e.message });
          } finally {
            setSubmitting(false);
          }
        });

        setSurvey(model);
      } else {
        // Fallback: Standard GitHub workflow inputs
        setStandardInputs(parsed?.standardInputs || []);
      }
    } catch (err) {
      setError(err.message);
    } finally {
      setLoading(false);
    }
  }

  async function handleStandardSubmit(e) {
    e.preventDefault();
    setSubmitting(true);
    setSubmitResult(null);

    try {
      const resp = await sendMsg('DISPATCH_WORKFLOW', {
        owner,
        repo,
        workflowId: workflowFilename,
        ref: selectedBranch,
        inputs: standardFormValues
      });

      if (resp.success) {
        setSubmitResult({ type: 'success', message: 'Workflow dispatched successfully! ⚡' });
      } else {
        setSubmitResult({ type: 'error', message: resp.error || 'Dispatch failed' });
      }
    } catch (err) {
      setSubmitResult({ type: 'error', message: err.message });
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
        <div style="margin-bottom: 12px;">
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
            <span style="font-size: 12px;">Loading form definition...</span>
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

        {/* Advanced SurveyJS Form */}
        {!loading && !error && survey && (
          <div class="sp-survey-container">
            <div ref={containerRef}></div>
            {submitting && (
              <div class="sp-empty-state" style="padding: 16px;">
                <div class="sp-spinner"></div>
                <span>Dispatching workflow via GitHub REST API...</span>
              </div>
            )}
          </div>
        )}

        {/* Fallback Standard Form */}
        {!loading && !error && !survey && standardInputs && (
          <form onSubmit={handleStandardSubmit} style="display:flex; flex-direction:column; gap:12px;">
            <div class="sp-alert warning" style="font-size: 11px;">
              <span>💡 Standard workflow detected without a custom SurveyJS form file.</span>
            </div>

            {standardInputs.length === 0 ? (
              <p style="font-size: 12px; color: var(--text-secondary);">
                This workflow takes no additional inputs. Click below to trigger execution.
              </p>
            ) : (
              standardInputs.map(inputName => (
                <div key={inputName} style="display:flex; flex-direction:column; gap:4px;">
                  <label style="font-size: 12px; font-weight: 600; color: var(--text-primary);">
                    {inputName}
                  </label>
                  <input
                    type="text"
                    class="sp-search-input"
                    value={standardFormValues[inputName] || ''}
                    onInput={(e) => setStandardFormValues({
                      ...standardFormValues,
                      [inputName]: e.target.value
                    })}
                  />
                </div>
              ))
            )}

            <button
              type="submit"
              class="sp-btn-run"
              disabled={submitting}
              style="margin-top: 8px; justify-content: center;"
            >
              {submitting ? '⚡ Dispatching...' : '⚡ Run workflow'}
            </button>
          </form>
        )}
      </div>
    </div>
  );
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

async function preloadDatasources(model, secrets) {
  const fetches = [];
  for (const question of model.getAllQuestions()) {
    const cbu = question.choicesByUrl;
    if (!cbu?.url) continue;

    const fetchConfig = question.getPropertyValue('fetchConfig') || {};
    const method = fetchConfig.method || 'GET';
    const headers = fetchConfig.headers ? await resolveObject(fetchConfig.headers, secrets) : {};
    const resolvedUrl = await resolve(cbu.url, secrets);

    const promise = sendMsg('FETCH_DATASOURCE', { url: resolvedUrl, method, headers })
      .then(resp => {
        if (resp.success) {
          const data = getByPath(resp.data, cbu.path);
          question.choices = (Array.isArray(data) ? data : []).map(item => ({
            value: String(cbu.valueName ? item[cbu.valueName] : item),
            text: String(cbu.titleName ? item[cbu.titleName] : item)
          }));
        }
        question.choicesByUrl = null;
      });
    fetches.push(promise);
  }
  await Promise.all(fetches);
}
