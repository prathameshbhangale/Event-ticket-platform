import { useEffect, useState } from "react";
import { useNavigate, useParams } from "react-router-dom";
import { apiConnector } from "../apis/apiCaller";
import { get_event_url, update_event_url } from "../apis/urls";
import toast from "react-hot-toast";
import { isLoggedIn, updateToken, getToken, login } from "../config/KeycloakService";

export default function EditEvent() {
  const { id } = useParams();
  const navigate = useNavigate();
  const [eventData, setEventData] = useState(null);

  useEffect(() => {
    if (!isLoggedIn()) {
      login();
      return;
    }

    const fetchEvent = async () => {
      try {
        await new Promise((resolve) => updateToken(resolve));
        const token = getToken();

        const response = await apiConnector(
          "GET",
          `${get_event_url}/${id}`,
          null,
          { Authorization: `Bearer ${token}` }
        );

        setEventData(response.data);
      } catch (err) {
        console.error("Failed to load event:", err);
        toast.error("Failed to load event.");
      }
    };

    fetchEvent();
  }, [id]);

  const handleChange = (e) => {
    const { name, value } = e.target;
    setEventData((prev) => ({ ...prev, [name]: value }));
  };

  const handleTicketChange = (index, e) => {
    const { name, value } = e.target;
    const updatedTickets = [...eventData.ticketTypes];
    updatedTickets[index][name] = name === "price" || name === "totalAvailable" ? Number(value) : value;
    setEventData((prev) => ({ ...prev, ticketTypes: updatedTickets }));
  };

  const addTicketType = () => {
    setEventData((prev) => ({
      ...prev,
      ticketTypes: [...prev.ticketTypes, { name: "", price: "", description: "", totalAvailable: "" }],
    }));
  };

  const removeTicketType = (index) => {
    if (eventData.ticketTypes.length === 1) {
      toast.error("At least one ticket type is required.");
      return;
    }
    setEventData((prev) => ({
      ...prev,
      ticketTypes: prev.ticketTypes.filter((_, i) => i !== index),
    }));
  };

  const handleSubmit = async (e) => {
    e.preventDefault();

    try {
      await new Promise((resolve) => updateToken(resolve));
      const token = getToken();

      const response = await apiConnector(
        "POST",
        `${update_event_url}/${id}`,
        eventData,
        {
          "Content-Type": "application/json",
          Authorization: `Bearer ${token}`,
        }
      );

      toast.success("Event updated successfully!");
      navigate("/events");
    } catch (err) {
      console.error("Error updating event:", err);
      toast.error("Failed to update event.");
    }
  };

  if (!eventData) {
    return <div className="p-10 text-center text-gray-600">Loading event...</div>;
  }

  return (
    <div className="min-h-screen bg-gray-100 flex justify-center items-start py-10 px-4">
      <div className="w-full max-w-3xl bg-white rounded-2xl p-8 shadow-md">
        <h1 className="text-3xl font-bold mb-6 text-gray-800 text-center">Edit Event</h1>
        <form onSubmit={handleSubmit} className="space-y-5">
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

          <div>
            <label className="block text-gray-700 mb-1">Status</label>
            <select
              name="status"
              value={eventData.status}
              onChange={handleChange}
              required
              className="w-full border border-gray-300 rounded-lg p-2 focus:outline-none focus:ring-2 focus:ring-gray-400"
            >
              <option value="DRAFT">DRAFT</option>
              <option value="PUBLISHED">PUBLISHED</option>
              <option value="CANCELLED">CANCELLED</option>
              <option value="COMPLETED">COMPLETED</option>
            </select>
          </div>

          {eventData.ticketTypes.map((ticket, index) => (
            <div
              key={index}
              className="mb-4 p-4 bg-gray-50 border border-gray-200 rounded-xl space-y-2 relative"
            >
              <button
                type="button"
                onClick={() => removeTicketType(index)}
                className="absolute top-2 right-2 text-red-600 hover:text-red-800 text-sm"
              >
                ✕
              </button>
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

          <button
            type="submit"
            className="w-full bg-gray-800 text-white py-2 rounded-lg hover:bg-gray-700 transition"
          >
            Update Event
          </button>
        </form>
      </div>
    </div>
  );
}
