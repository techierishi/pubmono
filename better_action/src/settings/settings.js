document.addEventListener('DOMContentLoaded', () => {
  const tokenInput = document.getElementById('github-token');
  const toggleTokenBtn = document.getElementById('toggle-token');
  const tokenStatus = document.getElementById('token-status');
  const authStatusBox = document.getElementById('auth-status-box');
  const oauthClientIdInput = document.getElementById('oauth-client-id');
  const saveOauthBtn = document.getElementById('save-oauth-btn');

  const secretsList = document.getElementById('secrets-list');
  const newKeyInput = document.getElementById('new-secret-key');
  const newValueInput = document.getElementById('new-secret-value');
  const addSecretBtn = document.getElementById('add-secret');
  const saveStatus = document.getElementById('save-status');

  loadSettings();

  let saveTimeout;
  tokenInput.addEventListener('input', () => {
    clearTimeout(saveTimeout);
    saveTimeout = setTimeout(() => {
      chrome.storage.local.set({ github_token: tokenInput.value.trim() }, () => {
        showSaveStatus();
        loadSettings();
      });
    }, 500);
  });

  toggleTokenBtn.addEventListener('click', () => {
    const isPassword = tokenInput.type === 'password';
    tokenInput.type = isPassword ? 'text' : 'password';
    toggleTokenBtn.textContent = isPassword ? '🔒' : '👁';
  });

  saveOauthBtn.addEventListener('click', () => {
    const clientId = oauthClientIdInput.value.trim();
    chrome.storage.local.set({ oauth_client_id: clientId }, () => {
      showSaveStatus();
    });
  });

  addSecretBtn.addEventListener('click', () => {
    const key = newKeyInput.value.trim();
    const value = newValueInput.value.trim();

    if (!key) {
      showFieldError(newKeyInput, 'Key is required');
      return;
    }

    chrome.storage.local.get(['secrets'], (result) => {
      const secrets = result.secrets || {};
      secrets[key] = value;
      chrome.storage.local.set({ secrets }, () => {
        newKeyInput.value = '';
        newValueInput.value = '';
        renderSecrets(secrets);
        showSaveStatus();
      });
    });
  });

  function loadSettings() {
    chrome.storage.local.get(['github_token', 'user_profile', 'auth_method', 'oauth_client_id', 'secrets'], (result) => {
      if (result.oauth_client_id) {
        oauthClientIdInput.value = result.oauth_client_id;
      }

      // Render Auth Status
      if (result.github_token && result.user_profile) {
        authStatusBox.innerHTML = `
          <div style="display:flex; align-items:center; gap:10px; background:#21262d; padding:10px; border-radius:6px; border:1px solid #30363d;">
            <img src="${result.user_profile.avatar_url || ''}" style="width:36px; height:36px; border-radius:50%;" />
            <div style="flex:1;">
              <div style="font-weight:600; color:#f0f6fc;">${result.user_profile.name || result.user_profile.login}</div>
              <div style="font-size:11px; color:#8b949e;">@${result.user_profile.login} · (${(result.auth_method || 'token').toUpperCase()})</div>
            </div>
            <button id="signout-btn" style="background:none; border:1px solid #f85149; color:#f85149; border-radius:6px; padding:4px 10px; font-size:11px; cursor:pointer;">
              Sign Out
            </button>
          </div>
        `;
        document.getElementById('signout-btn')?.addEventListener('click', () => {
          chrome.storage.local.remove(['github_token', 'user_profile', 'auth_method'], () => {
            loadSettings();
            showSaveStatus();
          });
        });
      } else {
        authStatusBox.innerHTML = `
          <div style="padding:10px; background:#161b22; border:1px dashed #30363d; border-radius:6px; font-size:12px; color:#8b949e;">
            Not signed in. You can authenticate via the Better Action Sidebar or enter a PAT below.
          </div>
        `;
      }

      if (result.github_token) {
        tokenInput.value = result.github_token;
        tokenStatus.textContent = 'Token configured';
        tokenStatus.className = 'status saved';
      } else {
        tokenInput.value = '';
        tokenStatus.textContent = 'No token set';
        tokenStatus.className = 'status error';
      }

      renderSecrets(result.secrets || {});
    });
  }

  function renderSecrets(secrets) {
    secretsList.innerHTML = '';
    const entries = Object.entries(secrets);

    if (entries.length === 0) {
      secretsList.innerHTML = '<p class="hint">No secrets configured yet.</p>';
      return;
    }

    for (const [key, value] of entries) {
      const row = document.createElement('div');
      row.className = 'secret-row';

      const keySpan = document.createElement('span');
      keySpan.className = 'key-display';
      keySpan.textContent = key;

      const valueSpan = document.createElement('span');
      valueSpan.className = 'value-display';
      valueSpan.textContent = value ? '••••••••' : '(empty)';
      valueSpan.title = value;

      const deleteBtn = document.createElement('button');
      deleteBtn.className = 'btn-danger';
      deleteBtn.textContent = 'Delete';
      deleteBtn.addEventListener('click', () => {
        delete secrets[key];
        chrome.storage.local.set({ secrets }, () => {
          renderSecrets(secrets);
          showSaveStatus();
        });
      });

      row.appendChild(keySpan);
      row.appendChild(valueSpan);
      row.appendChild(deleteBtn);
      secretsList.appendChild(row);
    }
  }

  function showSaveStatus() {
    saveStatus.textContent = 'Settings saved';
    saveStatus.className = 'save-status visible';
    setTimeout(() => {
      saveStatus.className = 'save-status';
    }, 2000);
  }

  function showFieldError(el, msg) {
    el.classList.add('error');
    setTimeout(() => el.classList.remove('error'), 2000);
  }
});
