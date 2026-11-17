import { startDeviceFlow, pollDeviceToken, fetchUserProfile } from '../utils/oauth.js';
import {
  fetchWorkflows,
  fetchWorkflowRuns,
  fetchBranches,
  fetchRepoFile,
  triggerWorkflowDispatch
} from '../utils/github-api.js';

// Configure Side Panel to open on action (toolbar icon) click
if (chrome.sidePanel && chrome.sidePanel.setPanelBehavior) {
  chrome.sidePanel.setPanelBehavior({ openPanelOnActionClick: true })
    .catch((err) => console.warn('Could not set side panel behavior:', err));
}

// Background message dispatcher
chrome.runtime.onMessage.addListener((message, sender, sendResponse) => {
  const { type, payload } = message;

  handleMessage(type, payload, sender)
    .then(result => sendResponse({ success: true, ...result }))
    .catch(err => sendResponse({ success: false, error: err.message }));

  return true; // Keep message channel open for async response
});

async function handleMessage(type, payload = {}, sender) {
  switch (type) {
    // ------------------------------------------------------------------------
    // Side Panel Control
    // ------------------------------------------------------------------------
    case 'OPEN_SIDEBAR': {
      let tabId = sender?.tab?.id;
      if (!tabId) {
        const [activeTab] = await chrome.tabs.query({ active: true, currentWindow: true });
        tabId = activeTab?.id;
      }
      if (tabId && chrome.sidePanel && chrome.sidePanel.open) {
        await chrome.sidePanel.open({ tabId });
      }
      // If payload includes workflow data, broadcast to sidepanel
      if (payload.workflowFilename || payload.workflowPath) {
        setTimeout(() => {
          chrome.runtime.sendMessage({
            type: 'SELECT_WORKFLOW',
            payload
          }).catch(() => {});
        }, 300);
      }
      return { opened: true };
    }

    // ------------------------------------------------------------------------
    // Authentication (OAuth & PAT)
    // ------------------------------------------------------------------------
    case 'AUTH_GET_STATUS': {
      const data = await chrome.storage.local.get(['github_token', 'user_profile', 'auth_method', 'oauth_client_id']);
      return {
        isAuthenticated: !!data.github_token,
        token: data.github_token || '',
        user: data.user_profile || null,
        authMethod: data.auth_method || (data.github_token ? 'pat' : 'none'),
        oauthClientId: data.oauth_client_id || ''
      };
    }

    case 'AUTH_START_DEVICE': {
      const { oauth_client_id } = await chrome.storage.local.get(['oauth_client_id']);
      const clientId = payload.clientId || oauth_client_id;
      if (!clientId) {
        throw new Error('Please configure a GitHub OAuth Client ID in Settings first.');
      }
      const flowData = await startDeviceFlow(clientId);
      return { flow: flowData };
    }

    case 'AUTH_POLL_DEVICE': {
      const { clientId, deviceCode } = payload;
      const pollResult = await pollDeviceToken(clientId, deviceCode);
      if (pollResult.status === 'success' && pollResult.token) {
        const user = await fetchUserProfile(pollResult.token);
        await chrome.storage.local.set({
          github_token: pollResult.token,
          user_profile: user,
          auth_method: 'oauth'
        });
        return { status: 'success', user, token: pollResult.token };
      }
      return pollResult;
    }

    case 'AUTH_SAVE_TOKEN': {
      const token = (payload.token || '').trim();
      if (!token) throw new Error('Token cannot be empty');
      const user = await fetchUserProfile(token);
      await chrome.storage.local.set({
        github_token: token,
        user_profile: user,
        auth_method: payload.authMethod || 'pat'
      });
      return { user };
    }

    case 'AUTH_LOGOUT': {
      await chrome.storage.local.remove(['github_token', 'user_profile', 'auth_method']);
      return { loggedOut: true };
    }

    case 'SAVE_OAUTH_CLIENT_ID': {
      await chrome.storage.local.set({ oauth_client_id: (payload.clientId || '').trim() });
      return { saved: true };
    }

    // ------------------------------------------------------------------------
    // GitHub Actions API
    // ------------------------------------------------------------------------
    case 'FETCH_WORKFLOWS': {
      const { owner, repo } = payload;
      const token = await getToken();
      const workflows = await fetchWorkflows(owner, repo, token);
      return { workflows };
    }

    case 'FETCH_WORKFLOW_RUNS': {
      const { owner, repo, workflowId, limit } = payload;
      const token = await getToken();
      const runs = await fetchWorkflowRuns(owner, repo, token, workflowId, limit);
      return { runs };
    }

    case 'FETCH_BRANCHES': {
      const { owner, repo } = payload;
      const token = await getToken();
      const branches = await fetchBranches(owner, repo, token);
      return { branches };
    }

    case 'FETCH_WORKFLOW': {
      const { owner, repo, path, ref } = payload;
      const token = await getToken();
      const content = await fetchRepoFile(owner, repo, path, ref, token);
      return { content };
    }

    case 'DISPATCH_WORKFLOW': {
      const { owner, repo, workflowId, ref, inputs } = payload;
      const token = await getToken();
      await triggerWorkflowDispatch(owner, repo, workflowId, ref, inputs, token);
      return { success: true };
    }

    // ------------------------------------------------------------------------
    // External Datasources & Secrets
    // ------------------------------------------------------------------------
    case 'FETCH_DATASOURCE': {
      const { url, method = 'GET', headers = {}, body } = payload;
      const fetchOptions = { method, headers };
      if (body && method !== 'GET') {
        fetchOptions.body = typeof body === 'string' ? body : JSON.stringify(body);
      }
      const response = await fetch(url, fetchOptions);
      if (!response.ok) throw new Error(`Datasource request failed with status: ${response.status}`);
      const data = await response.json();
      return { data };
    }

    case 'GET_SECRETS': {
      const result = await chrome.storage.local.get(['secrets', 'github_token']);
      return {
        secrets: result.secrets || {},
        github_token: result.github_token || ''
      };
    }

    // ------------------------------------------------------------------------
    // Legacy / Tab Navigation Support
    // ------------------------------------------------------------------------
    case 'OPEN_BETTER_ACTION':
    case 'OPEN_BETTER_DISPATCH': {
      const { owner, repo, ref, workflowPath, workflowFilename, config } = payload;
      const tabUrl = chrome.runtime.getURL('tab/index.html') +
        `?owner=${encodeURIComponent(owner || '')}` +
        `&repo=${encodeURIComponent(repo || '')}` +
        `&ref=${encodeURIComponent(ref || '')}` +
        `&workflowPath=${encodeURIComponent(workflowPath || '')}` +
        `&workflowFilename=${encodeURIComponent(workflowFilename || '')}` +
        `&config=${encodeURIComponent(config ? JSON.stringify(config) : '')}`;
      await chrome.tabs.create({ url: tabUrl, active: true });
      return { success: true };
    }

    default:
      throw new Error(`Unknown message type: ${type}`);
  }
}

async function getToken() {
  const { github_token } = await chrome.storage.local.get(['github_token']);
  return github_token || '';
}
