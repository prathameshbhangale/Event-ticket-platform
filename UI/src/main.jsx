import { StrictMode } from 'react';
import { createRoot } from 'react-dom/client';
import './index.css';
import App from './App.jsx';
import { BrowserRouter } from 'react-router-dom';
import keycloak from './config/KeycloakService.js';

keycloak.init({ onLoad: 'check-sso' }).then(() => {
  createRoot(document.getElementById('root')).render(
    <StrictMode>
      <BrowserRouter>
        <App />
      </BrowserRouter>
    </StrictMode>
  );
}).catch(err => {
  console.error("Failed to initialize Keycloak", err);
});
