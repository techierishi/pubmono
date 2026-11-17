import { useState, useEffect } from 'preact/hooks';

export function SettingsModal({ onClose }) {
  const [secrets, setSecrets] = useState({});
  const [newKey, setNewKey] = useState('');
  const [newValue, setNewValue] = useState('');
  const [clientId, setClientId] = useState('');
  const [statusMsg, setStatusMsg] = useState('');

  useEffect(() => {
    loadSettings();
  }, []);

  async function loadSettings() {
    chrome.storage.local.get(['secrets', 'oauth_client_id'], (result) => {
      setSecrets(result.secrets || {});
      setClientId(result.oauth_client_id || '');
    });
  }

  function handleAddSecret(e) {
    e.preventDefault();
    const key = newKey.trim();
    const val = newValue.trim();
    if (!key) return;

    const updated = { ...secrets, [key]: val };
    chrome.storage.local.set({ secrets: updated }, () => {
      setSecrets(updated);
      setNewKey('');
      setNewValue('');
      showNotice('Secret saved');
    });
  }

  function handleDeleteSecret(key) {
    const updated = { ...secrets };
    delete updated[key];
    chrome.storage.local.set({ secrets: updated }, () => {
      setSecrets(updated);
      showNotice('Secret removed');
    });
  }

  function handleSaveClientId() {
    chrome.storage.local.set({ oauth_client_id: clientId.trim() }, () => {
      showNotice('OAuth Client ID saved');
    });
  }

  function showNotice(msg) {
    setStatusMsg(msg);
    setTimeout(() => setStatusMsg(''), 2500);
  }

  return (
    <div class="sp-modal-backdrop" onClick={onClose}>
      <div class="sp-modal" style="max-width: 420px;" onClick={e => e.stopPropagation()}>
        <div class="sp-modal-header">
          <h3>Settings & Secrets Vault</h3>
          <button class="sp-icon-btn" onClick={onClose}>✕</button>
        </div>

        <div class="sp-modal-body" style="max-height: 80vh; overflow-y: auto;">
          {statusMsg && (
            <div class="sp-alert success" style="font-size: 11px;">
              <span>✓ {statusMsg}</span>
            </div>
          )}

          {/* GitHub OAuth Settings */}
          <div>
            <h4 style="font-size: 13px; font-weight: 600; margin-bottom: 4px;">GitHub OAuth Configuration</h4>
            <p style="font-size: 11px; color: var(--text-muted); margin-bottom: 8px;">
              Used for "Sign in with GitHub" Device Flow.
            </p>
            <div style="display:flex; gap:6px;">
              <input
                type="text"
                class="sp-search-input"
                placeholder="OAuth App Client ID"
                value={clientId}
                onInput={e => setClientId(e.target.value)}
              />
              <button class="sp-btn-run" onClick={handleSaveClientId} style="padding: 5px 10px;">
                Save
              </button>
            </div>
          </div>

          <div style="border-top: 1px solid var(--border-color); padding-top: 12px;">
            <h4 style="font-size: 13px; font-weight: 600; margin-bottom: 4px;">External API Secrets Vault</h4>
            <p style="font-size: 11px; color: var(--text-muted); margin-bottom: 8px;">
              Keys used for <code>${'{SETTINGS_*}'}</code> interpolation in datasource URLs.
            </p>

            {/* List existing secrets */}
            <div style="display:flex; flex-direction:column; gap:6px; margin-bottom: 12px;">
              {Object.keys(secrets).length === 0 ? (
                <span style="font-size: 11px; color: var(--text-muted);">No custom secrets configured yet.</span>
              ) : (
                Object.entries(secrets).map(([k, v]) => (
                  <div key={k} style="display:flex; align-items:center; justify-content:space-between; background:var(--bg-primary); padding:6px 10px; border-radius:6px; border:1px solid var(--border-color);">
                    <span style="font-family:monospace; font-size:11px; color:var(--text-primary);">{k}</span>
                    <div style="display:flex; align-items:center; gap:8px;">
                      <span style="font-size:10px; color:var(--text-muted);">{v ? '••••••••' : '(empty)'}</span>
                      <button
                        onClick={() => handleDeleteSecret(k)}
                        style="background:none; border:none; color:var(--accent-red); cursor:pointer; font-size:11px;"
                      >
                        Delete
                      </button>
                    </div>
                  </div>
                ))
              )}
            </div>

            {/* Add new secret */}
            <form onSubmit={handleAddSecret} style="display:flex; flex-direction:column; gap:6px;">
              <input
                type="text"
                class="sp-search-input"
                placeholder="SETTINGS_API_TOKEN"
                value={newKey}
                onInput={e => setNewKey(e.target.value)}
              />
              <input
                type="password"
                class="sp-search-input"
                placeholder="Secret value"
                value={newValue}
                onInput={e => setNewValue(e.target.value)}
              />
              <button type="submit" class="sp-btn-run" style="justify-content:center;">
                + Add Secret
              </button>
            </form>
          </div>
        </div>
      </div>
    </div>
  );
}
