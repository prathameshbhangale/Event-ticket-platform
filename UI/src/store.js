import { configureStore } from '@reduxjs/toolkit';
import eventListReducer from "./slices/EventListSlice";

const store = configureStore({
  reducer: {
    eventList: eventListReducer,
  }
});

export default store;
