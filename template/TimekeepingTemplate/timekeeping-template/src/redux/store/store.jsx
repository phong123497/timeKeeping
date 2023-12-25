import { configureStore } from '@reduxjs/toolkit'
import rootReducer from '../reducers';
import { applyMiddleware, compose } from "redux";
import thunk from "redux-thunk";

const middlewares = [thunk];
const composeEnhancers = window.__REDUX_DEVTOOLS_EXTENSION_COMPOSE__ || compose;
export const store = configureStore({ reducer: rootReducer },null,composeEnhancers(applyMiddleware(...middlewares)))