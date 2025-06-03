// src/features/event/eventSlice.js

import { createSlice } from '@reduxjs/toolkit';

const initialState = {
  event: {
    id: null,
    name: '',
    start: '',
    end: '',
    venue: '',
    salesStart: '',
    salesEnd: '',
    status: '',
    ticketTypes: [],
  },
};

const eventSlice = createSlice({
  name: 'event',
  initialState,
  reducers: {
    setEvent: (state, action) => {
      state.event = action.payload;
    },
    updateEventField: (state, action) => {
      const { field, value } = action.payload;
      state.event[field] = value;
    },
    addTicketType: (state, action) => {
      state.event.ticketTypes.push(action.payload);
    },
    updateTicketType: (state, action) => {
      const { index, data } = action.payload;
      if (state.event.ticketTypes[index]) {
        state.event.ticketTypes[index] = {
          ...state.event.ticketTypes[index],
          ...data,
        };
      }
    },
    removeTicketType: (state, action) => {
      const index = action.payload;
      if (state.event.ticketTypes[index]) {
        state.event.ticketTypes.splice(index, 1);
      }
    },
    resetEvent: (state) => {
      state.event = {
        id: null,
        name: '',
        start: '',
        end: '',
        venue: '',
        salesStart: '',
        salesEnd: '',
        status: '',
        ticketTypes: [],
      };
    },
  },
});

export const {
  setEvent,
  updateEventField,
  addTicketType,
  updateTicketType,
  removeTicketType,
  resetEvent,
} = eventSlice.actions;

export default eventSlice.reducer;
