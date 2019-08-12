import { createStore, applyMiddleware } from 'redux';
import { persistReducer, persistStore } from 'redux-persist';
import penderMiddleware from 'redux-pender';
import storage from 'redux-persist/lib/storage';
import modules from './modules';

export default function() {
  const persistConfig = {
    key: 'root',
    storage
  }
  const persistedReducer = persistReducer(persistConfig, modules);
  const store = createStore(persistedReducer, applyMiddleware(penderMiddleware()));
  const persistor = persistStore(store);
  return { store, persistor };
}
