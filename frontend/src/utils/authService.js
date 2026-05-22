/**
 * Saves the authentication token to session storage
 */
export function saveToken(token) {
  sessionStorage.setItem("jwt", token);
  // Dispatch an event that components can listen for
  window.dispatchEvent(new Event('auth-change'));
}

/**
 * Gets the authentication token from session storage
 */
export function getToken() {
  return sessionStorage.getItem("jwt");
}

/**
 * Removes the authentication token from session storage
 */
export function removeToken() {
  sessionStorage.removeItem("jwt");
  // Dispatch an event when token is removed
  window.dispatchEvent(new Event('auth-change'));
}

/**
 * Checks if a user is currently logged in
 */
export function isLoggedIn() {
  return !!getToken();
}

/**
 * Saves the user data to session storage
 */
export function saveUser(user) {
  sessionStorage.setItem("user", JSON.stringify(user));
}

/**
 * Logs out the current user by removing token and user data
 */
export function logout() {
  removeToken();
  sessionStorage.removeItem("user");
  // Event already dispatched by removeToken
}
