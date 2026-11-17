// GitHub OAuth 2.0 Device Authorization Flow (RFC 8628)
// and User Profile Helpers

export const DEFAULT_CLIENT_ID = '';

/**
 * Initiates the GitHub Device Authorization Flow
 * @param {string} clientId - GitHub OAuth App Client ID
 * @param {string} scope - Requested OAuth scopes (default: 'repo,workflow')
 * @returns {Promise<{device_code: string, user_code: string, verification_uri: string, expires_in: number, interval: number}>}
 */
export async function startDeviceFlow(clientId, scope = 'repo,workflow') {
  if (!clientId) {
    throw new Error('GitHub OAuth Client ID is required. Please set it in Settings.');
  }

  const response = await fetch('https://github.com/login/device/code', {
    method: 'POST',
    headers: {
      'Accept': 'application/json',
      'Content-Type': 'application/json'
    },
    body: JSON.stringify({
      client_id: clientId,
      scope: scope
    })
  });

  const data = await response.json();
  if (!response.ok || data.error) {
    throw new Error(data.error_description || data.error || `Failed to start device flow (${response.status})`);
  }

  return {
    device_code: data.device_code,
    user_code: data.user_code,
    verification_uri: data.verification_uri || 'https://github.com/login/device',
    expires_in: data.expires_in || 900,
    interval: data.interval || 5
  };
}

/**
 * Polls GitHub's token endpoint for device authorization completion
 * @param {string} clientId - GitHub OAuth App Client ID
 * @param {string} deviceCode - Device code returned from startDeviceFlow
 * @returns {Promise<{status: 'success'|'pending'|'slow_down'|'error', token?: string, error?: string, interval?: number}>}
 */
export async function pollDeviceToken(clientId, deviceCode) {
  const response = await fetch('https://github.com/login/oauth/access_token', {
    method: 'POST',
    headers: {
      'Accept': 'application/json',
      'Content-Type': 'application/json'
    },
    body: JSON.stringify({
      client_id: clientId,
      device_code: deviceCode,
      grant_type: 'urn:ietf:params:oauth:grant-type:device_code'
    })
  });

  const data = await response.json();

  if (data.access_token) {
    return {
      status: 'success',
      token: data.access_token,
      token_type: data.token_type,
      scope: data.scope
    };
  }

  if (data.error === 'authorization_pending') {
    return { status: 'pending' };
  }

  if (data.error === 'slow_down') {
    return { status: 'slow_down', interval: (data.interval || 5) + 5 };
  }

  return {
    status: 'error',
    error: data.error_description || data.error || 'Authorization failed'
  };
}

/**
 * Fetches authenticated GitHub user profile
 * @param {string} token - Personal Access Token or OAuth Access Token
 * @returns {Promise<{login: string, name: string, avatar_url: string, html_url: string}>}
 */
export async function fetchUserProfile(token) {
  if (!token) throw new Error('No token provided');

  const response = await fetch('https://api.github.com/user', {
    headers: {
      'Accept': 'application/vnd.github.v3+json',
      'Authorization': `Bearer ${token}`
    }
  });

  if (!response.ok) {
    const text = await response.text().catch(() => '');
    throw new Error(`Failed to fetch user profile (${response.status}): ${text.slice(0, 150)}`);
  }

  const user = await response.json();
  return {
    login: user.login,
    name: user.name || user.login,
    avatar_url: user.avatar_url,
    html_url: user.html_url
  };
}
