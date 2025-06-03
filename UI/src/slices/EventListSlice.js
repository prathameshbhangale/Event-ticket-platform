import { createSlice } from '@reduxjs/toolkit';


const initialState = {
  events: [],
  loading: false,
  error: null,
};

const eventListSlice = createSlice({
  name: 'eventList',
  initialState,
  reducers: {
    setEventList(state,action){
        state.events = action.payload
    },
    addEvent(state, action) {
      state.events.push(action.payload);
    },
    updateLoader(state){
        state.loading = !state.loading;
    },
    setError(state,action){
        state.error=action.payload;
    },
    updateEvent(state, action) {
      const { id, name, start, end, venue, status } = action.payload;
      const event = state.events.find((e) => e.id === id);
      if (event) {
        event.name = name;
        event.start = start;
        event.end = end;
        event.venue = venue;
        event.status = status;
      }
    },
    deleteEvent(state, action) {
      state.events = state.events.filter((e) => e.id !== action.payload);
    },
  }
});

export const { addEvent, updateEvent,setEventList, deleteEvent,updateLoader,setError } = eventListSlice.actions;
export default eventListSlice.reducer;
