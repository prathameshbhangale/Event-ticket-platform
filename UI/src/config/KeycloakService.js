import Keycloak from "keycloak-js";

// Initialize Keycloak with your settings
const keycloak = new Keycloak({
  url: "http://localhost:8080", 
  realm: "event-ticket-platform",
  clientId: "event-ticket-platform-app",
});

export const isLoggedIn = () => {
  return keycloak.authenticated === true;
};

export const login = () => keycloak.login();

export const logout = () => keycloak.logout();

export const getToken = () => keycloak.token;

export const areTokensValid = () => {
  if (!keycloak.token || !keycloak.refreshToken) {
    return false; // tokens do not exist
  }

  const now = Math.floor(Date.now() / 1000); 
  const accessExp = keycloak.tokenParsed?.exp || 0;
  const refreshExp = keycloak.refreshTokenParsed?.exp || 0;
  return accessExp > now && refreshExp > now;
};


export const updateToken = (successCallback) => {
  keycloak.updateToken(70).then((refreshed) => {
    if (refreshed) {
      console.log("Token refreshed");
    }
    successCallback();
  }).catch(() => {
    console.error("Failed to refresh token");
  });
};

export default keycloak;
