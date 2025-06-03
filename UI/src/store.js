import { configureStore } from '@reduxjs/toolkit';
import eventListReducer from "./slices/EventListSlice";
import eventReducer from "./slices/EventSlice"

const store = configureStore({
  reducer: {
    eventList: eventListReducer,
    event: eventReducer,
}
});

export default store;
