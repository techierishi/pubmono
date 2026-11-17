// GitHub REST API client for Actions, Workflows, and Repository Contents

function headers(token) {
  const h = {
    'Accept': 'application/vnd.github.v3+json',
    'X-GitHub-Api-Version': '2022-11-28'
  };
  if (token) {
    h['Authorization'] = `token ${token}`;
  }
  return h;
}

/**
 * Fetch all workflows for a repository
 */
export async function fetchWorkflows(owner, repo, token) {
  const url = `https://api.github.com/repos/${encodeURIComponent(owner)}/${encodeURIComponent(repo)}/actions/workflows`;
  const res = await fetch(url, { headers: headers(token) });
  if (!res.ok) {
    const text = await res.text().catch(() => '');
    throw new Error(`GitHub API error (${res.status}) fetching workflows: ${text.slice(0, 200)}`);
  }
  const data = await res.json();
  return data.workflows || [];
}

/**
 * Fetch recent workflow runs for a repository or specific workflow
 */
export async function fetchWorkflowRuns(owner, repo, token, workflowId = null, limit = 20) {
  let url = `https://api.github.com/repos/${encodeURIComponent(owner)}/${encodeURIComponent(repo)}/actions/runs?per_page=${limit}`;
  if (workflowId) {
    url = `https://api.github.com/repos/${encodeURIComponent(owner)}/${encodeURIComponent(repo)}/actions/workflows/${encodeURIComponent(workflowId)}/runs?per_page=${limit}`;
  }
  const res = await fetch(url, { headers: headers(token) });
  if (!res.ok) {
    const text = await res.text().catch(() => '');
    throw new Error(`GitHub API error (${res.status}) fetching runs: ${text.slice(0, 200)}`);
  }
  const data = await res.json();
  return data.workflow_runs || [];
}

/**
 * Fetch branches for a repository
 */
export async function fetchBranches(owner, repo, token) {
  const url = `https://api.github.com/repos/${encodeURIComponent(owner)}/${encodeURIComponent(repo)}/branches?per_page=100`;
  const res = await fetch(url, { headers: headers(token) });
  if (!res.ok) return [];
  const data = await res.json();
  return Array.isArray(data) ? data.map(b => b.name) : [];
}

/**
 * Fetch file content from repository via contents API
 */
export async function fetchRepoFile(owner, repo, path, ref, token) {
  const cleanPath = path.startsWith('/') ? path.slice(1) : path;
  const encodedPath = cleanPath.split('/').map(encodeURIComponent).join('/');
  const refQuery = ref ? `?ref=${encodeURIComponent(ref)}` : '';
  const url = `https://api.github.com/repos/${encodeURIComponent(owner)}/${encodeURIComponent(repo)}/contents/${encodedPath}${refQuery}`;

  const res = await fetch(url, { headers: headers(token) });
  if (!res.ok) {
    const text = await res.text().catch(() => '');
    throw new Error(`GitHub API error (${res.status}) fetching ${path} on ${ref || 'default'}: ${text.slice(0, 200)}`);
  }

  const data = await res.json();
  if (!data.content) {
    throw new Error(`No file content found for ${path}`);
  }

  // Base64 decode handles unicode
  const binaryString = atob(data.content.replace(/\s/g, ''));
  const bytes = Uint8Array.from(binaryString, c => c.charCodeAt(0));
  return new TextDecoder('utf-8').decode(bytes);
}

/**
 * Trigger a workflow_dispatch event
 */
export async function triggerWorkflowDispatch(owner, repo, workflowId, ref, inputs, token) {
  if (!token) {
    throw new Error('GitHub authentication token required to dispatch workflows.');
  }

  const url = `https://api.github.com/repos/${encodeURIComponent(owner)}/${encodeURIComponent(repo)}/actions/workflows/${encodeURIComponent(workflowId)}/dispatches`;
  const res = await fetch(url, {
    method: 'POST',
    headers: {
      ...headers(token),
      'Content-Type': 'application/json'
    },
    body: JSON.stringify({ ref, inputs })
  });

  if (!res.ok) {
    const text = await res.text().catch(() => '');
    throw new Error(`Dispatch failed (${res.status}): ${text}`);
  }

  return { success: true };
}
