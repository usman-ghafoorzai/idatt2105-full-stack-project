export function saveToken(token) {
  sessionStorage.setItem("jwt", token);
}

export function getToken() {
  return sessionStorage.getItem("jwt");
}

export function removeToken() {
  sessionStorage.removeItem("jwt");
}

export function isLoggedIn() {
  return !!getToken();
}
