import { StrictMode } from 'react';
import { createRoot } from 'react-dom/client';
import './index.css';
import App from './App.jsx';
import { BrowserRouter } from 'react-router-dom';
import keycloak from './config/KeycloakService.js';
// import store from './store.js';
// import { Provider } from 'react-redux';

keycloak.init({ onLoad: 'check-sso' }).then(() => {
  createRoot(document.getElementById('root')).render(
    <StrictMode>
      <BrowserRouter>
        {/* <Provider store={store}> */}
          <App />
        {/* </Provider> */}
      </BrowserRouter>
    </StrictMode>
  );
}).catch(err => {
  console.error("Failed to initialize Keycloak", err);
});
