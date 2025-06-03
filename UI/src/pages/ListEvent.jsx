import React, { useEffect, useState } from 'react';
import { listEvents } from '../apis/connectors/listEvent';
import { useDispatch } from 'react-redux';
import { setEventList } from '../slices/EventListSlice';

export default function EventListPage() {
  const [events, setEvents] = useState([]);
  const [loading, setLoading] = useState(true);
  const [page, setPage] = useState(0);
  const size = 10;
  const dispatch = useDispatch();

  const fetchEvents = async (page) => {
    setLoading(true);
    try {
      const data = await listEvents({ page, size });
        data = data.map(({ id, name, start, end, venue, status }) => ({
        id,
        name,
        start,
        end,
        venue,
        status
        }));
      setEvents(data);
      dispatch(setEventList(data))

    } catch (error) {
      console.error('Error fetching events:', error);
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    fetchEvents(page);
  }, [page]);

  const handleEdit = (eventId) => {
    console.log(`Edit event: ${eventId}`);
  };

  const handleDelete = (eventId) => {
    console.log(`Delete event: ${eventId}`);
  };

  const handleNextPage = () => {
    setPage((prev) => prev + 1);
  };

  if (loading) return <p className="text-center mt-10">Loading events...</p>;

  return (
    <div className="max-w-6xl mx-auto p-4">
      <h1 className="text-2xl font-bold mb-6">Event List (Page {page})</h1>
      <div className="flex flex-wrap gap-4">
        {events.map((event, index) => (
          <div
            key={event.id}
            className={`w-full sm:w-[48%] lg:w-[31%] p-4 rounded-xl shadow-md border ${
              index % 2 === 0 ? 'bg-white' : 'bg-gray-50'
            }`}
          >
            <h2 className="text-xl font-semibold mb-2">{event.name}</h2>
            <p className="text-sm text-gray-600 mb-1">
              <strong>Start:</strong> {new Date(event.start).toLocaleString()}
            </p>
            <p className="text-sm text-gray-600 mb-1">
              <strong>End:</strong> {new Date(event.end).toLocaleString()}
            </p>
            <p className="text-sm text-gray-600 mb-1">
              <strong>Venue:</strong> {event.venue}
            </p>
            <p className="text-sm text-gray-600 mb-3">
              <strong>Status:</strong> {event.status}
            </p>
            <div className="flex gap-2">
              <button
                onClick={() => handleEdit(event.id)}
                className="px-3 py-1 text-sm text-white bg-blue-600 rounded hover:bg-blue-700"
              >
                Edit
              </button>
              <button
                onClick={() => handleDelete(event.id)}
                className="px-3 py-1 text-sm text-white bg-red-600 rounded hover:bg-red-700"
              >
                Delete
              </button>
            </div>
          </div>
        ))}
      </div>

      {/* Next Page Button */}
      <div className="flex justify-center mt-6">
        <button
          onClick={handleNextPage}
          className="px-4 py-2 text-white bg-gray-800 rounded hover:bg-gray-900"
        >
          Next Page
        </button>
      </div>
    </div>
  );
}