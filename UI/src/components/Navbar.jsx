import React from 'react';
import { Link } from 'react-router-dom';
import toast from 'react-hot-toast';
import { FaUserCircle, FaTicketAlt } from 'react-icons/fa';
import keycloak from '../config/KeycloakService'; // import your keycloak instance

export default function Navbar() {
  const isLoggedIn = keycloak.authenticated;
  const email = keycloak.tokenParsed?.email || "guest@example.com";

  const avatarUrl = `https://api.dicebear.com/7.x/initials/svg?seed=${encodeURIComponent(email)}`;

  return (
    <nav className="bg-gray-100 text-gray-800 shadow-md">
      <div className="max-w-7xl mx-auto px-4 py-3 flex justify-between items-center">
        {/* Logo */}
        <Link to="/" className="flex items-center gap-2 text-2xl font-bold text-gray-900">
          <FaTicketAlt className="text-gray-700" />
          <span>EventHub</span>
        </Link>

        {/* Nav Links */}
        <div className="flex items-center gap-6">
          <Link to="/events" className="hover:text-gray-600">
            Events
          </Link>
          <Link to="/mytickets" className="hover:text-gray-600">
            My Tickets
          </Link>
          <Link to="/create-event" className="hover:text-gray-600">
            Create Event
          </Link>
        </div>

        {/* Auth Buttons / Profile */}
        <div className="flex items-center gap-4">
          {!isLoggedIn ? (
            <>
              <button
                onClick={() => {
                    toast.loading('Redirecting to register...');
                    keycloak.register({ redirectUri: window.location.href });
                }}
                className="px-4 py-2 rounded-md bg-white text-gray-800 border border-gray-300 hover:bg-gray-200"
              >
                Register
              </button>
              <button
                onClick={() => {
                    toast.loading('Redirecting to login...');
                    keycloak.login({ redirectUri: window.location.href });
                }}
                className="px-4 py-2 rounded-md bg-gray-800 text-white hover:bg-gray-700"
              >
                Login
              </button>
            </>
          ) : (
            <>
              <Link to="/profile">
                <img
                  src={avatarUrl}
                  alt="Profile"
                  className="w-9 h-9 rounded-full border border-gray-400 shadow-sm"
                />
              </Link>
              <button
                onClick={() => {
                    toast.success('Logged out!');
                    keycloak.logout({ redirectUri: window.location.href });
                }}
                className="px-3 py-1 rounded-md text-sm text-red-600 hover:underline"
              >
                Logout
              </button>
            </>
          )}
        </div>
      </div>
    </nav>
  );
}
