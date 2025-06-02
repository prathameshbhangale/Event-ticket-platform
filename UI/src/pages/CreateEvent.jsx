import { useState } from "react";
import { useNavigate } from "react-router-dom";
import { apiConnector } from "../apis/apiCaller";
import { create_event_url } from "../apis/urls.js"
import toast from "react-hot-toast";
import { isLoggedIn, updateToken,getToken,login } from "../config/KeycloakService.js";

export default function CreateEvent() {
  const navigate = useNavigate();

  const [eventData, setEventData] = useState({
    name: "",
    start: "",
    end: "",
    venue: "",
    salesStart: "",
    salesEnd: "",
    status: "PUBLISHED",
    ticketTypes: [
      {
        name: "",
        price: "",
        description: "",
        totalAvailable: "",
      },
    ],
  });

  const handleChange = (e) => {
    const { name, value } = e.target;
    setEventData((prev) => ({ ...prev, [name]: value }));
  };

  const handleTicketChange = (index, e) => {
    const { name, value } = e.target;
    const updatedTickets = [...eventData.ticketTypes];
    updatedTickets[index][name] = name === "price" || name === "totalAvailable"
      ? Number(value)
      : value;
    setEventData((prev) => ({ ...prev, ticketTypes: updatedTickets }));
  };

  const addTicketType = () => {
    setEventData((prev) => ({
      ...prev,
      ticketTypes: [
        ...prev.ticketTypes,
        { name: "", price: "", description: "", totalAvailable: "" },
      ],
    }));
  };

  const handleSubmit = async (e) => {
  e.preventDefault();

  if (!isLoggedIn()) {
    login();  // Redirect to login if not logged in
    return;
  }

  try {
    // This will refresh token only if expiring in next 70 seconds
    await new Promise((resolve, reject) => {
      updateToken(() => resolve());
    });

    const token = getToken();

    const res = await apiConnector(
      "POST",
      create_event_url,
      eventData,
      {
        "Content-Type": "application/json",
        Authorization: `Bearer ${token}`,
      }
    );

    console.log("Event created:", res.data);
    toast.success("Event created successfully");
    navigate("/events");
  } catch (err) {
    console.error("Error creating event:", err);
    alert("Failed to create event. Check console for details.");
  }
};


  return (
    <div className="min-h-screen bg-gray-100 flex justify-center items-start py-10 px-4">
      <div className="w-full max-w-3xl bg-white rounded-2xl p-8 shadow-md">
        <h1 className="text-3xl font-bold mb-6 text-gray-800 text-center">Create Event</h1>
        <form onSubmit={handleSubmit} className="space-y-5">
          {/* Event fields */}
          {[
            { label: "Event Name", name: "name", type: "text" },
            { label: "Start Time", name: "start", type: "datetime-local" },
            { label: "End Time", name: "end", type: "datetime-local" },
            { label: "Venue", name: "venue", type: "text" },
            { label: "Sales Start", name: "salesStart", type: "datetime-local" },
            { label: "Sales End", name: "salesEnd", type: "datetime-local" },
          ].map(({ label, name, type }) => (
            <div key={name}>
              <label className="block text-gray-700 mb-1">{label}</label>
              <input
                type={type}
                name={name}
                value={eventData[name]}
                onChange={handleChange}
                required
                className="w-full border border-gray-300 rounded-lg p-2 focus:outline-none focus:ring-2 focus:ring-gray-400"
              />
            </div>
          ))}

          {/* Ticket Types */}
          <div>
            <h2 className="text-lg font-semibold text-gray-700 mb-2">Ticket Types</h2>
            {eventData.ticketTypes.map((ticket, index) => (
              <div
                key={index}
                className="mb-4 p-4 bg-gray-50 border border-gray-200 rounded-xl space-y-2"
              >
                <input
                  type="text"
                  name="name"
                  value={ticket.name}
                  placeholder="Ticket Name"
                  onChange={(e) => handleTicketChange(index, e)}
                  required
                  className="w-full border border-gray-300 rounded p-2"
                />
                <input
                  type="number"
                  name="price"
                  value={ticket.price}
                  placeholder="Price"
                  onChange={(e) => handleTicketChange(index, e)}
                  required
                  className="w-full border border-gray-300 rounded p-2"
                />
                <input
                  type="text"
                  name="description"
                  value={ticket.description}
                  placeholder="Description"
                  onChange={(e) => handleTicketChange(index, e)}
                  className="w-full border border-gray-300 rounded p-2"
                />
                <input
                  type="number"
                  name="totalAvailable"
                  value={ticket.totalAvailable}
                  placeholder="Total Available"
                  onChange={(e) => handleTicketChange(index, e)}
                  required
                  className="w-full border border-gray-300 rounded p-2"
                />
              </div>
            ))}
            <button
              type="button"
              onClick={addTicketType}
              className="bg-gray-200 text-gray-800 px-4 py-2 rounded hover:bg-gray-300 transition"
            >
              + Add Ticket Type
            </button>
          </div>

          {/* Submit */}
          <button
            type="submit"
            className="w-full bg-gray-800 text-white py-2 rounded-lg hover:bg-gray-700 transition"
          >
            Create Event
          </button>
        </form>
      </div>
    </div>
  );
}
