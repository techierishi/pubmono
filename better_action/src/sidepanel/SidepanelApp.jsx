import { useState, useEffect } from 'preact/hooks';
import { Header } from './components/Header.jsx';
import { WorkflowListView } from './components/WorkflowListView.jsx';
import { FormView } from './components/FormView.jsx';
import { OAuthLogin } from './components/OAuthLogin.jsx';
import { SettingsModal } from './components/SettingsModal.jsx';

export function SidepanelApp() {
  const [currentPage, setCurrentPage] = useState('workflows'); // 'workflows' | 'form'
  const [auth, setAuth] = useState(null);
  const [showAuthModal, setShowAuthModal] = useState(false);
  const [showSettingsModal, setShowSettingsModal] = useState(false);

  const [repoInfo, setRepoInfo] = useState({
    owner: '',
    repo: '',
    ref: 'master',
    workflowFilename: '',
    workflowPath: ''
  });

  const [workflows, setWorkflows] = useState([]);
  const [runs, setRuns] = useState([]);
  const [branches, setBranches] = useState([]);
  const [selectedWorkflow, setSelectedWorkflow] = useState(null);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(null);

  useEffect(() => {
    loadAuthStatus();
    detectActiveTabRepo();

    // Listen for tab activation / navigation
    const handleTabActivated = () => detectActiveTabRepo();
    const handleTabUpdated = (tabId, changeInfo, tab) => {
      if (changeInfo.status === 'complete' && tab?.url) {
        detectActiveTabRepo();
      }
    };

    chrome.tabs.onActivated.addListener(handleTabActivated);
    chrome.tabs.onUpdated.addListener(handleTabUpdated);

    // Listen for runtime messages from content script or background
    const handleMessage = (msg) => {
      if (msg.type === 'SELECT_WORKFLOW' && msg.payload) {
        handleSelectWorkflow(msg.payload);
      } else if (msg.type === 'PAGE_CONTEXT_CHANGED' && msg.payload) {
        const { owner, repo, ref, workflowFilename, workflowPath } = msg.payload;
        setRepoInfo(prev => ({
          ...prev,
          owner: owner || prev.owner,
          repo: repo || prev.repo,
          ref: ref || prev.ref,
          workflowFilename: workflowFilename || prev.workflowFilename,
          workflowPath: workflowPath || prev.workflowPath
        }));
        if (owner && repo) {
          loadRepoData(owner, repo);
        }
      }
    };

    chrome.runtime.onMessage.addListener(handleMessage);

    return () => {
      chrome.tabs.onActivated.removeListener(handleTabActivated);
      chrome.tabs.onUpdated.removeListener(handleTabUpdated);
      chrome.runtime.onMessage.removeListener(handleMessage);
    };
  }, []);

  async function loadAuthStatus() {
    try {
      const resp = await sendMsg('AUTH_GET_STATUS');
      setAuth(resp);
    } catch (_) {}
  }

  async function detectActiveTabRepo() {
    try {
      const [tab] = await chrome.tabs.query({ active: true, currentWindow: true });
      if (!tab?.url) return;

      const url = new URL(tab.url);
      if (url.hostname === 'github.com') {
        const parts = url.pathname.split('/').filter(Boolean);
        if (parts.length >= 2) {
          const owner = parts[0];
          const repo = parts[1];
          const isActions = parts[2] === 'actions';

          let workflowFilename = '';
          const wfMatch = url.pathname.match(/\/workflows\/([^/]+\.(?:ya?ml))/i);
          if (wfMatch) workflowFilename = wfMatch[1];

          setRepoInfo(prev => ({
            ...prev,
            owner,
            repo,
            workflowFilename,
            workflowPath: workflowFilename ? `.github/workflows/${workflowFilename}` : ''
          }));

          if (isActions || parts.length === 2) {
            loadRepoData(owner, repo);
          }
        }
      }
    } catch (_) {}
  }

  async function loadRepoData(owner, repo) {
    if (!owner || !repo) return;
    setLoading(true);
    setError(null);

    try {
      const [wfResp, runsResp, branchesResp] = await Promise.allSettled([
        sendMsg('FETCH_WORKFLOWS', { owner, repo }),
        sendMsg('FETCH_WORKFLOW_RUNS', { owner, repo, limit: 20 }),
        sendMsg('FETCH_BRANCHES', { owner, repo })
      ]);

      if (wfResp.status === 'fulfilled' && wfResp.value.success) {
        setWorkflows(wfResp.value.workflows || []);
      } else if (wfResp.status === 'fulfilled' && wfResp.value.error) {
        setError(wfResp.value.error);
      }

      if (runsResp.status === 'fulfilled' && runsResp.value.success) {
        setRuns(runsResp.value.runs || []);
      }

      if (branchesResp.status === 'fulfilled' && branchesResp.value.success) {
        setBranches(branchesResp.value.branches || []);
      }
    } catch (err) {
      setError(err.message);
    } finally {
      setLoading(false);
    }
  }

  function handleSelectWorkflow(workflow) {
    setSelectedWorkflow({
      ...workflow,
      owner: workflow.owner || repoInfo.owner,
      repo: workflow.repo || repoInfo.repo,
      ref: workflow.ref || repoInfo.ref || 'master'
    });
    setCurrentPage('form'); // Switch to Page 2
  }

  function handleManualLoadRepo(owner, repo) {
    setRepoInfo(prev => ({ ...prev, owner, repo }));
    loadRepoData(owner, repo);
  }

  function handleAuthSuccess(user) {
    setShowAuthModal(false);
    loadAuthStatus();
    if (repoInfo.owner && repoInfo.repo) {
      loadRepoData(repoInfo.owner, repoInfo.repo);
    }
  }

  async function handleLogout() {
    await sendMsg('AUTH_LOGOUT');
    setShowAuthModal(false);
    loadAuthStatus();
  }

  return (
    <div style="display:flex; flex-direction:column; height:100vh;">
      <Header
        repoInfo={repoInfo}
        auth={auth}
        onOpenAuth={() => setShowAuthModal(true)}
        onOpenSettings={() => setShowSettingsModal(true)}
        onRefresh={() => {
          detectActiveTabRepo();
          if (repoInfo.owner && repoInfo.repo) {
            loadRepoData(repoInfo.owner, repoInfo.repo);
          }
        }}
      />

      <main class="sp-body">
        {currentPage === 'workflows' ? (
          // Page 1: List of workflows and jobs with searchbar
          <WorkflowListView
            workflows={workflows}
            runs={runs}
            loading={loading}
            error={error}
            repoInfo={repoInfo}
            onSelectWorkflow={handleSelectWorkflow}
            onManualLoadRepo={handleManualLoadRepo}
          />
        ) : (
          // Page 2: Advanced Run Form
          <FormView
            workflowInfo={selectedWorkflow}
            branches={branches}
            onBack={() => setCurrentPage('workflows')}
          />
        )}
      </main>

      {/* Auth Modal */}
      {showAuthModal && (
        <OAuthLogin
          auth={auth}
          onClose={() => setShowAuthModal(false)}
          onAuthSuccess={handleAuthSuccess}
          onLogout={handleLogout}
        />
      )}

      {/* Settings Modal */}
      {showSettingsModal && (
        <SettingsModal
          onClose={() => setShowSettingsModal(false)}
        />
      )}
    </div>
  );
}

function sendMsg(type, payload) {
  return new Promise((resolve) => {
    chrome.runtime.sendMessage({ type, payload }, resolve);
  });
}
