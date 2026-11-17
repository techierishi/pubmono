// Better Action Content Script
// Observes GitHub Actions pages and provides sidebar integration

let lastUrl = location.href;

function init() {
  checkAndNotifyPage();
  observePageChanges();
  attachRunWorkflowHooks();
}

function isActionsPage() {
  return /^\/[^/]+\/[^/]+\/actions(?:\/.*)?$/.test(location.pathname);
}

function getRepoCoordinates() {
  const parts = location.pathname.split('/').filter(Boolean);
  if (parts.length >= 2) {
    return { owner: parts[0], repo: parts[1] };
  }
  return null;
}

function getWorkflowFilename() {
  const match = location.pathname.match(/\/workflows\/([^/]+\.(?:ya?ml))/i);
  if (match) return match[1];

  const params = new URLSearchParams(location.search);
  const qp = params.get('workflow');
  if (qp) return /\.(ya?ml)$/i.test(qp) ? qp : `${qp}.yaml`;

  return null;
}

function getActiveBranch() {
  // Try branch picker button or selectors
  const selectors = [
    '[data-menu-trigger="branch-picker"] span',
    '[data-testid="branch-picker"] span',
    '#branch-picker-repos-header-ref-selector span',
    'summary[aria-label*="branch"] span',
    '[class*="branch"] [class*="name"]',
    '[data-testid*="branch"] span'
  ];

  for (const s of selectors) {
    const el = document.querySelector(s);
    const text = el?.textContent?.trim();
    if (text && text.length < 50) return text;
  }

  const branchMatch = location.pathname.match(/\/tree\/([^/]+)/);
  if (branchMatch) return decodeURIComponent(branchMatch[1]);

  const queryBranch = new URLSearchParams(location.search).get('branch');
  if (queryBranch) return queryBranch;

  return 'master';
}

function checkAndNotifyPage() {
  if (!isActionsPage()) return;

  const repo = getRepoCoordinates();
  if (!repo) return;

  const filename = getWorkflowFilename();
  const branch = getActiveBranch();

  chrome.runtime.sendMessage({
    type: 'PAGE_CONTEXT_CHANGED',
    payload: {
      owner: repo.owner,
      repo: repo.repo,
      ref: branch,
      workflowFilename: filename,
      workflowPath: filename ? `.github/workflows/${filename}` : null,
      isActionsPage: true
    }
  }).catch(() => {});
}

function attachRunWorkflowHooks() {
  if (!isActionsPage()) return;

  // Look for GitHub's native "Run workflow" button or menu
  const runWorkflowTriggers = document.querySelectorAll(
    'summary[data-menu-trigger="run-workflow-menu"], ' +
    'button[data-testid="run-workflow-button"], ' +
    'details.details-reset.details-overlay button'
  );

  runWorkflowTriggers.forEach((trigger) => {
    if (trigger.dataset.baHooked) return;
    trigger.dataset.baHooked = 'true';

    // When the user clicks "Run workflow", also inject our Better Action button in the dropdown
    trigger.addEventListener('click', () => {
      setTimeout(injectSidebarButtonIntoDropdown, 100);
    });
  });
}

function injectSidebarButtonIntoDropdown() {
  const dropdown = document.querySelector(
    'details[data-menu-trigger="run-workflow-menu"] .SelectMenu-list, ' +
    'details.details-reset.details-overlay .SelectMenu-list, ' +
    'div[data-testid="run-workflow-dialog"], ' +
    'form[action*="/dispatches"]'
  );

  if (!dropdown || dropdown.querySelector('.ba-open-sidebar-btn')) return;

  const repo = getRepoCoordinates();
  const filename = getWorkflowFilename();
  const branch = getActiveBranch();

  const container = document.createElement('div');
  container.className = 'ba-dropdown-addon';
  container.style.cssText = 'padding: 10px 16px; border-bottom: 1px solid #30363d; background: #161b22;';

  const btn = document.createElement('button');
  btn.type = 'button';
  btn.className = 'ba-open-sidebar-btn';
  btn.innerHTML = '⚡ Open in Better Action Sidebar';
  btn.style.cssText =
    'display: flex; align-items: center; justify-content: center; width: 100%; ' +
    'gap: 8px; background: linear-gradient(135deg, #238636, #2ea043); color: #fff; ' +
    'border: 1px solid #2ea043; border-radius: 6px; padding: 6px 12px; font-size: 13px; ' +
    'font-weight: 600; cursor: pointer; transition: opacity 0.2s; font-family: inherit;';

  btn.addEventListener('mouseenter', () => { btn.style.opacity = '0.9'; });
  btn.addEventListener('mouseleave', () => { btn.style.opacity = '1'; });

  btn.addEventListener('click', (e) => {
    e.preventDefault();
    e.stopPropagation();

    chrome.runtime.sendMessage({
      type: 'OPEN_SIDEBAR',
      payload: {
        owner: repo?.owner,
        repo: repo?.repo,
        ref: branch,
        workflowFilename: filename,
        workflowPath: filename ? `.github/workflows/${filename}` : null
      }
    });
  });

  container.appendChild(btn);
  dropdown.prepend(container);
}

function observePageChanges() {
  const observer = new MutationObserver(() => {
    if (location.href !== lastUrl) {
      lastUrl = location.href;
      checkAndNotifyPage();
    }
    attachRunWorkflowHooks();
  });

  observer.observe(document.body, { childList: true, subtree: true });
}

// Start observing when document is ready
if (document.readyState === 'loading') {
  document.addEventListener('DOMContentLoaded', init);
} else {
  init();
}
