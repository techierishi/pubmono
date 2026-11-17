import { useState, useEffect, useRef } from 'preact/hooks';

export function OAuthLogin({
  auth,
  onClose,
  onAuthSuccess,
  onLogout
}) {
  const [activeTab, setActiveTab] = useState('oauth'); // 'oauth' | 'pat'
  const [deviceFlow, setDeviceFlow] = useState(null); // { device_code, user_code, verification_uri, interval }
  const [pollingStatus, setPollingStatus] = useState('');
  const [error, setError] = useState('');
  const [patInput, setPatInput] = useState('');
  const [clientIdInput, setClientIdInput] = useState(auth?.oauthClientId || '');
  const [showClientIdConfig, setShowClientIdConfig] = useState(!auth?.oauthClientId);
  const [loading, setLoading] = useState(false);

  const pollTimerRef = useRef(null);

  useEffect(() => {
    return () => {
      if (pollTimerRef.current) clearTimeout(pollTimerRef.current);
    };
  }, []);

  async function handleStartOAuth() {
    setError('');
    setLoading(true);

    try {
      const clientId = clientIdInput.trim();
      if (!clientId) {
        setShowClientIdConfig(true);
        throw new Error('Please enter your GitHub OAuth App Client ID below to start OAuth.');
      }

      // Save client ID if changed
      await sendMsg('SAVE_OAUTH_CLIENT_ID', { clientId });

      const resp = await sendMsg('AUTH_START_DEVICE', { clientId });
      if (!resp.success) {
        throw new Error(resp.error || 'Failed to start OAuth flow');
      }

      const flow = resp.flow;
      setDeviceFlow(flow);
      setPollingStatus('Waiting for authorization on GitHub...');

      // Copy user code to clipboard
      try {
        await navigator.clipboard.writeText(flow.user_code);
      } catch (_) {}

      // Open GitHub verification page in new tab
      chrome.tabs.create({ url: flow.verification_uri });

      // Start polling for token
      startPolling(clientId, flow.device_code, flow.interval || 5);
    } catch (err) {
      setError(err.message);
    } finally {
      setLoading(false);
    }
  }

  function startPolling(clientId, deviceCode, intervalSec) {
    if (pollTimerRef.current) clearTimeout(pollTimerRef.current);

    pollTimerRef.current = setTimeout(async () => {
      try {
        const resp = await sendMsg('AUTH_POLL_DEVICE', { clientId, deviceCode });
        if (resp.status === 'success') {
          setPollingStatus('Authorized! Signed in.');
          setDeviceFlow(null);
          onAuthSuccess(resp.user);
          return;
        }

        if (resp.status === 'pending') {
          setPollingStatus('Waiting for code entry on GitHub...');
          startPolling(clientId, deviceCode, intervalSec);
          return;
        }

        if (resp.status === 'slow_down') {
          const nextInterval = (resp.interval || intervalSec + 5);
          startPolling(clientId, deviceCode, nextInterval);
          return;
        }

        if (resp.error) {
          setError(resp.error);
          setDeviceFlow(null);
        }
      } catch (err) {
        setError(err.message);
        setDeviceFlow(null);
      }
    }, intervalSec * 1000);
  }

  async function handleSavePat(e) {
    e.preventDefault();
    setError('');
    setLoading(true);

    try {
      const token = patInput.trim();
      if (!token) throw new Error('Personal Access Token cannot be empty');

      const resp = await sendMsg('AUTH_SAVE_TOKEN', { token, authMethod: 'pat' });
      if (!resp.success) throw new Error(resp.error);

      onAuthSuccess(resp.user);
    } catch (err) {
      setError(err.message);
    } finally {
      setLoading(false);
    }
  }

  return (
    <div class="sp-modal-backdrop" onClick={onClose}>
      <div class="sp-modal" onClick={e => e.stopPropagation()}>
        <div class="sp-modal-header">
          <h3>GitHub Authentication</h3>
          <button class="sp-icon-btn" onClick={onClose}>✕</button>
        </div>

        <div class="sp-modal-body">
          {auth?.isAuthenticated && auth.user ? (
            // Currently Authenticated View
            <div style="display:flex; flex-direction:column; gap:12px; align-items:center; text-align:center;">
              <img
                src={auth.user.avatar_url}
                alt={auth.user.login}
                style="width: 56px; height: 56px; border-radius: 50%; border: 2px solid var(--accent-green);"
              />
              <div>
                <h4 style="font-size: 15px; font-weight: 600;">{auth.user.name || auth.user.login}</h4>
                <p style="font-size: 12px; color: var(--text-secondary);">@{auth.user.login}</p>
              </div>

              <div class="sp-badge ba-badge" style="font-size: 11px;">
                ✓ Authenticated via {auth.authMethod?.toUpperCase() || 'GITHUB'}
              </div>

              <button
                class="sp-icon-btn"
                onClick={onLogout}
                style="color: var(--accent-red); border-color: var(--border-color); padding: 6px 16px; margin-top: 8px;"
              >
                Sign Out
              </button>
            </div>
          ) : (
            // Sign-in Forms
            <>
              <div class="sp-tabs" style="margin-bottom: 6px;">
                <button
                  class={`sp-tab ${activeTab === 'oauth' ? 'active' : ''}`}
                  onClick={() => { setActiveTab('oauth'); setError(''); }}
                >
                  GitHub OAuth
                </button>
                <button
                  class={`sp-tab ${activeTab === 'pat' ? 'active' : ''}`}
                  onClick={() => { setActiveTab('pat'); setError(''); }}
                >
                  Access Token (PAT)
                </button>
              </div>

              {error && (
                <div class="sp-alert error">
                  <span>⚠️ {error}</span>
                </div>
              )}

              {/* OAuth Tab */}
              {activeTab === 'oauth' && (
                <div style="display:flex; flex-direction:column; gap:12px;">
                  {!deviceFlow ? (
                    <>
                      <p style="font-size: 12px; color: var(--text-secondary);">
                        Sign in seamlessly using GitHub's Device Flow. No manual token creation needed.
                      </p>

                      {showClientIdConfig && (
                        <div style="display:flex; flex-direction:column; gap:4px;">
                          <label style="font-size: 11px; font-weight: 600; color: var(--text-secondary);">
                            GitHub OAuth App Client ID:
                          </label>
                          <input
                            type="text"
                            class="sp-search-input"
                            placeholder="e.g. Ov23li..."
                            value={clientIdInput}
                            onInput={e => setClientIdInput(e.target.value)}
                          />
                          <span style="font-size: 10px; color: var(--text-muted);">
                            Create a free OAuth App under GitHub Settings &gt; Developer Settings &gt; OAuth Apps (check "Enable Device Flow").
                          </span>
                        </div>
                      )}

                      <button
                        class="sp-btn-github"
                        onClick={handleStartOAuth}
                        disabled={loading}
                      >
                        <span>🐙</span>
                        <span>{loading ? 'Starting OAuth...' : 'Sign in with GitHub'}</span>
                      </button>

                      {!showClientIdConfig && (
                        <button
                          style="background:none; border:none; color:var(--accent-blue); font-size:11px; cursor:pointer; text-align:left;"
                          onClick={() => setShowClientIdConfig(true)}
                        >
                          Change OAuth Client ID ⚙️
                        </button>
                      )}
                    </>
                  ) : (
                    <div style="display:flex; flex-direction:column; gap:10px;">
                      <div class="sp-device-code-box">
                        <span style="font-size: 11px; color: var(--text-muted);">Your One-Time Code:</span>
                        <span class="sp-code-display">{deviceFlow.user_code}</span>
                        <span style="font-size: 11px; color: var(--accent-green);">Code copied to clipboard!</span>
                      </div>

                      <p style="font-size: 12px; color: var(--text-secondary); text-align:center;">
                        {pollingStatus}
                      </p>

                      <button
                        class="sp-btn-run"
                        style="justify-content: center;"
                        onClick={() => chrome.tabs.create({ url: deviceFlow.verification_uri })}
                      >
                        Open GitHub Verification Page ↗
                      </button>

                      <button
                        class="sp-icon-btn"
                        style="color: var(--text-muted); font-size: 11px; text-align: center;"
                        onClick={() => {
                          if (pollTimerRef.current) clearTimeout(pollTimerRef.current);
                          setDeviceFlow(null);
                        }}
                      >
                        Cancel
                      </button>
                    </div>
                  )}
                </div>
              )}

              {/* PAT Tab */}
              {activeTab === 'pat' && (
                <form onSubmit={handleSavePat} style="display:flex; flex-direction:column; gap:10px;">
                  <p style="font-size: 12px; color: var(--text-secondary);">
                    Paste a Personal Access Token with <code>workflow</code> and <code>repo</code> scopes.
                  </p>

                  <div style="display:flex; flex-direction:column; gap:4px;">
                    <label style="font-size: 11px; font-weight: 600; color: var(--text-secondary);">
                      Personal Access Token:
                    </label>
                    <input
                      type="password"
                      class="sp-search-input"
                      placeholder="ghp_xxxxxxxxxxxxxxxxxxxx"
                      value={patInput}
                      onInput={e => setPatInput(e.target.value)}
                    />
                  </div>

                  <a
                    href="https://github.com/settings/tokens/new?scopes=repo,workflow&description=Better+Action+Extension"
                    target="_blank"
                    rel="noreferrer"
                    style="font-size: 11px; color: var(--accent-blue); text-decoration: underline;"
                  >
                    Generate a token on GitHub ↗
                  </a>

                  <button
                    type="submit"
                    class="sp-btn-run"
                    disabled={loading}
                    style="justify-content: center; margin-top: 4px;"
                  >
                    {loading ? 'Verifying token...' : 'Save & Authenticate'}
                  </button>
                </form>
              )}
            </>
          )}
        </div>
      </div>
    </div>
  );
}

function sendMsg(type, payload) {
  return new Promise((resolve) => {
    chrome.runtime.sendMessage({ type, payload }, resolve);
  });
}
