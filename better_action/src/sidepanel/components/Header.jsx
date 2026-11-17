export function Header({
  repoInfo,
  auth,
  onOpenAuth,
  onOpenSettings,
  onRefresh
}) {
  return (
    <header class="sp-header">
      <div class="sp-header-top">
        <div class="sp-logo">
          <span>⚡</span>
          <span>Better Action</span>
          <span class="sp-logo-badge">Sidebar</span>
        </div>

        <div class="sp-header-actions">
          {auth?.isAuthenticated && auth.user ? (
            <div class="sp-user-badge" title={`Signed in as @${auth.user.login} (${auth.authMethod.toUpperCase()})`}>
              {auth.user.avatar_url && (
                <img src={auth.user.avatar_url} class="sp-user-avatar" alt={auth.user.login} />
              )}
              <span>@{auth.user.login}</span>
            </div>
          ) : (
            <button class="sp-icon-btn" onClick={onOpenAuth} title="Sign in with GitHub">
              🔑 Sign in
            </button>
          )}

          <button class="sp-icon-btn" onClick={onRefresh} title="Refresh workflows">
            🔄
          </button>
          <button class="sp-icon-btn" onClick={onOpenSettings} title="Settings & Secrets">
            ⚙️
          </button>
        </div>
      </div>

      {repoInfo?.owner && repoInfo?.repo ? (
        <div class="sp-repo-bar">
          <span class="sp-repo-name" title={`${repoInfo.owner}/${repoInfo.repo}`}>
            {repoInfo.owner} / {repoInfo.repo}
          </span>
          {repoInfo.ref && (
            <span class="sp-branch-pill" title={`Branch: ${repoInfo.ref}`}>
              🌿 {repoInfo.ref}
            </span>
          )}
        </div>
      ) : (
        <div class="sp-repo-bar" style="color: var(--text-muted); font-size: 11px;">
          <span>Navigate to github.com/.../actions to load repo</span>
        </div>
      )}
    </header>
  );
}
