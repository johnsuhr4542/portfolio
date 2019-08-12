import { createAction, handleActions } from 'redux-actions';
import axios from 'axios';
import { pender } from 'redux-pender';

import domain from '../../lib/url';

const LOGIN = 'user/LOGIN';
const LOGOUT = 'user/LOGOUT';
const REISSUE = 'user/REISSUE';

const doLogin = ({ username, password }) => {
  const params = {
    username: username,
    password: password
  }
  return axios.post(`${domain}/auth/token`, params);
}

const doReissue = refreshToken => {
  const params = {
    refreshToken: refreshToken
  }
  return axios.post(`${domain}/auth/refresh`, params);
}

export const login = createAction(LOGIN, doLogin);
export const logout = createAction(LOGOUT);
export const reissue = createAction(REISSUE, doReissue);

const initialState = {
  logged: false,
  userInfo: {
    username: '',
    authority: ''
  },
  tokens: {
    accessToken: '',
    refreshToken: ''
  }
}

export default handleActions({

  ...pender({
    type: LOGIN,
    onSuccess: ( state, action ) => {
      const { accessToken, refreshToken, userDetails } = action.payload.data;
      return {
        logged: true,
        userInfo: {
          username: userDetails.username,
          authority: userDetails.authority
        },
        tokens: {
          accessToken: accessToken,
          refreshToken: refreshToken
        }
      }
    }
  }),

  [LOGOUT]: () => ({
    logged: false,
    userInfo: {
      username: '',
      authorities: ''
    },
    tokens: {
      accessToken: '',
      refreshToken: ''
    }
  }),

  ...pender({
    type: REISSUE,
    onSuccess: ( state, action ) => {
      const { accessToken } = action.payload.data;
      return {
        ...state,
        tokens: {
          ...state.tokens,
          accessToken: accessToken
        }
      }
    }
  })

}, initialState)