import Keycloak from "keycloak-js";

// Initialize Keycloak with your settings
const keycloak = new Keycloak({
  url: "http://localhost:8080", 
  realm: "event-ticket-platform",
  clientId: "event-ticket-platform-app",
});

export default keycloak;
