import { useState, useMemo } from 'preact/hooks';

export function WorkflowListView({
  workflows = [],
  runs = [],
  loading = false,
  error = null,
  onSelectWorkflow,
  onManualLoadRepo,
  repoInfo
}) {
  const [searchQuery, setSearchQuery] = useState('');
  const [activeTab, setActiveTab] = useState('workflows'); // 'workflows' | 'runs'
  const [manualRepoInput, setManualRepoInput] = useState('');

  // Filter workflows by search query
  const filteredWorkflows = useMemo(() => {
    const q = searchQuery.trim().toLowerCase();
    if (!q) return workflows;
    return workflows.filter(w => {
      const name = (w.name || '').toLowerCase();
      const path = (w.path || '').toLowerCase();
      const state = (w.state || '').toLowerCase();
      return name.includes(q) || path.includes(q) || state.includes(q);
    });
  }, [workflows, searchQuery]);

  // Filter runs/jobs by search query
  const filteredRuns = useMemo(() => {
    const q = searchQuery.trim().toLowerCase();
    if (!q) return runs;
    return runs.filter(r => {
      const name = (r.name || '').toLowerCase();
      const headBranch = (r.head_branch || '').toLowerCase();
      const status = (r.status || '').toLowerCase();
      const conclusion = (r.conclusion || '').toLowerCase();
      return name.includes(q) || headBranch.includes(q) || status.includes(q) || conclusion.includes(q);
    });
  }, [runs, searchQuery]);

  // Check if a workflow is likely a Better Action enhanced workflow
  function isEnhancedWorkflow(workflow) {
    const path = workflow.path || '';
    return path.includes('better_action') || path.includes('sample');
  }

  function getRunStatusBadge(run) {
    const status = run.status;
    const conclusion = run.conclusion;

    if (status === 'in_progress') {
      return { icon: '🔄', text: 'In progress', color: 'var(--accent-blue)' };
    }
    if (status === 'queued') {
      return { icon: '⏳', text: 'Queued', color: 'var(--accent-yellow)' };
    }
    if (conclusion === 'success') {
      return { icon: '✅', text: 'Success', color: '#3fb950' };
    }
    if (conclusion === 'failure') {
      return { icon: '❌', text: 'Failed', color: 'var(--accent-red)' };
    }
    if (conclusion === 'cancelled') {
      return { icon: '🚫', text: 'Cancelled', color: 'var(--text-muted)' };
    }
    return { icon: '⚪', text: status || 'Unknown', color: 'var(--text-secondary)' };
  }

  function handleManualSubmit(e) {
    e.preventDefault();
    const val = manualRepoInput.trim();
    if (val && val.includes('/')) {
      const [owner, repo] = val.split('/');
      onManualLoadRepo(owner.trim(), repo.trim());
    }
  }

  // Not on actions page and no workflows loaded
  if (!repoInfo?.owner && workflows.length === 0 && !loading) {
    return (
      <div class="sp-list-container">
        <div class="sp-empty-state">
          <span class="sp-empty-icon">📂</span>
          <span class="sp-empty-title">No Actions Page Detected</span>
          <p class="sp-empty-desc">
            Open a GitHub repository Actions tab (e.g. <code>github.com/owner/repo/actions</code>) or enter a repo below:
          </p>
          <form onSubmit={handleManualSubmit} style="display:flex; gap:6px; width:100%; max-width:280px; margin-top:8px;">
            <input
              type="text"
              class="sp-search-input"
              placeholder="owner/repo"
              value={manualRepoInput}
              onInput={e => setManualRepoInput(e.target.value)}
            />
            <button type="submit" class="sp-btn-run">Load</button>
          </form>
        </div>
      </div>
    );
  }

  return (
    <div class="sp-list-container">
      {/* Searchbar on top */}
      <div class="sp-search-bar">
        <span class="sp-search-icon">🔍</span>
        <input
          type="text"
          class="sp-search-input"
          placeholder="Search workflows, jobs, branches..."
          value={searchQuery}
          onInput={e => setSearchQuery(e.target.value)}
        />
        {searchQuery && (
          <button class="sp-search-clear" onClick={() => setSearchQuery('')} title="Clear search">
            ×
          </button>
        )}
      </div>

      {/* Tabs */}
      <div class="sp-tabs">
        <button
          class={`sp-tab ${activeTab === 'workflows' ? 'active' : ''}`}
          onClick={() => setActiveTab('workflows')}
        >
          <span>Workflows</span>
          <span class="sp-tab-count">{workflows.length}</span>
        </button>
        <button
          class={`sp-tab ${activeTab === 'runs' ? 'active' : ''}`}
          onClick={() => setActiveTab('runs')}
        >
          <span>Recent Runs</span>
          <span class="sp-tab-count">{runs.length}</span>
        </button>
      </div>

      {/* Error Notice */}
      {error && (
        <div class="sp-alert error">
          <span>⚠️ {error}</span>
        </div>
      )}

      {/* Loading state */}
      {loading && (
        <div class="sp-empty-state">
          <div class="sp-spinner"></div>
          <span style="font-size: 12px;">Loading workflows & jobs...</span>
        </div>
      )}

      {/* Workflows Tab */}
      {!loading && activeTab === 'workflows' && (
        <div class="sp-workflow-list">
          {filteredWorkflows.length === 0 ? (
            <div class="sp-empty-state">
              <span class="sp-empty-icon">🔍</span>
              <span class="sp-empty-title">No workflows found</span>
              <p class="sp-empty-desc">
                {searchQuery ? `No workflows match "${searchQuery}"` : 'No workflows configured in this repository.'}
              </p>
            </div>
          ) : (
            filteredWorkflows.map(workflow => {
              const filename = workflow.path?.split('/').pop() || '';
              return (
                <div key={workflow.id} class="sp-card">
                  <div class="sp-card-header">
                    <div class="sp-card-title-group">
                      <span class="sp-card-name">{workflow.name}</span>
                      <span class="sp-card-path" title={workflow.path}>{workflow.path}</span>
                    </div>
                  </div>

                  <div class="sp-card-footer">
                    <div style="display: flex; gap: 6px; align-items: center;">
                      <span class={`sp-badge ${isEnhancedWorkflow(workflow) ? 'ba-badge' : 'active-badge'}`}>
                        {isEnhancedWorkflow(workflow) ? '⚡ Better Action' : (workflow.state || 'active')}
                      </span>
                    </div>

                    <button
                      class="sp-btn-run"
                      onClick={() => onSelectWorkflow({
                        owner: repoInfo?.owner,
                        repo: repoInfo?.repo,
                        ref: repoInfo?.ref || 'master',
                        workflowId: workflow.id,
                        workflowFilename: filename,
                        workflowPath: workflow.path,
                        workflowName: workflow.name
                      })}
                      title="Open run form in sidebar"
                    >
                      ⚡ Run workflow
                    </button>
                  </div>
                </div>
              );
            })
          )}
        </div>
      )}

      {/* Recent Runs Tab */}
      {!loading && activeTab === 'runs' && (
        <div class="sp-workflow-list">
          {filteredRuns.length === 0 ? (
            <div class="sp-empty-state">
              <span class="sp-empty-icon">📋</span>
              <span class="sp-empty-title">No recent runs</span>
              <p class="sp-empty-desc">
                {searchQuery ? `No runs match "${searchQuery}"` : 'No recent workflow runs found.'}
              </p>
            </div>
          ) : (
            filteredRuns.map(run => {
              const badge = getRunStatusBadge(run);
              return (
                <div key={run.id} class="sp-card">
                  <div class="sp-card-header">
                    <div class="sp-card-title-group">
                      <span class="sp-card-name" style="font-size:12px;">{run.name || run.display_title || `Run #${run.run_number}`}</span>
                      <span class="sp-card-path">
                        🌿 {run.head_branch || 'default'} · commit {run.head_sha?.slice(0, 7)}
                      </span>
                    </div>
                    <span style={`font-size: 11px; font-weight: 600; color: ${badge.color}; display: flex; align-items: center; gap: 4px;`}>
                      {badge.icon} {badge.text}
                    </span>
                  </div>

                  <div class="sp-card-footer">
                    <span style="font-size: 11px; color: var(--text-muted);">
                      #{run.run_number} by {run.actor?.login || 'actor'}
                    </span>
                    <a
                      href={run.html_url}
                      target="_blank"
                      rel="noreferrer"
                      style="font-size: 11px; color: var(--accent-blue); text-decoration: none;"
                    >
                      View on GitHub ↗
                    </a>
                  </div>
                </div>
              );
            })
          )}
        </div>
      )}
    </div>
  );
}
