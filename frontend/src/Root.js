import React, { Component } from 'react'
import { Provider } from 'react-redux';
import { PersistGate } from 'redux-persist/es/integration/react';

import App from './App';
import configure from './store/configure';

const { persistor, store } = configure();

export default class Root extends Component {
  render() {
    return (
      <Provider store={ store }>
        <PersistGate loading={ null } persistor={ persistor }>
          <App />
        </PersistGate>
      </Provider>
    )
  }
}