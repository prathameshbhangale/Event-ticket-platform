import { apiConnector } from '../apiCaller';
import { list_event_url } from '../urls';        
import { getToken,updateToken,isLoggedIn,login } from '../../config/KeycloakService';


export const listEvents = async (queryParams) => {
  return new Promise((resolve, reject) => {

    updateToken(async () => {
      try {
        if (!isLoggedIn()) {  // <-- call the function here
          login();
          return; // prevent further execution after login redirect
        }
        const token = getToken();
        const headers = {
          Authorization: `Bearer ${token}`,
        };

        const response = await apiConnector(
          'GET',
          list_event_url,
          null,
          headers,
          queryParams
        );

        resolve(response.data); // return response body only
      } catch (error) {
        reject(error);
      }
    });
  });
};
