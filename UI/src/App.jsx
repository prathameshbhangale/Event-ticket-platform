import {  Routes, Route } from 'react-router-dom';
import Navbar from './components/Navbar';
import { Toaster } from 'react-hot-toast';
import Home from './pages/HomePage';
import CreateEvent from './pages/CreateEvent';
import EventListPage from './pages/ListEvent';
import EditEvent from './pages/EditEvent';

// Dummy components for pages (replace with your actual components)
// const Home = () => <div className="p-4 text-gray-800">Welcome to EventHub!</div>;
// const Events = () => <div className="p-4 text-gray-800">Events Page</div>;
const MyTickets = () => <div className="p-4 text-gray-800">My Tickets</div>;
// const CreateEvent = () => <div className="p-4 text-gray-800">Create Event</div>;
const Register = () => <div className="p-4 text-gray-800">Register Page</div>;
const Login = () => <div className="p-4 text-gray-800">Login Page</div>;
const Profile = () => <div className="p-4 text-gray-800">User Profile</div>;

function App() {
  return (
      <>
      <Toaster position="top-right" reverseOrder={false} />
      <Navbar />
      <Routes>
        <Route path="/" element={<Home />} />
        <Route path="/events" element={<EventListPage />} />
        <Route path="/events/edit/:id" element={<EditEvent />} />
        <Route path="/mytickets" element={<MyTickets />} />
        <Route path="/create-event" element={<CreateEvent />} />
        <Route path="/register" element={<Register />} />
        <Route path="/login" element={<Login />} />
        <Route path="/profile" element={<Profile />} />
      </Routes>
      </>
  );
}

export default App;
