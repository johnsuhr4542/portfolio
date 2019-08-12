import React, { Component } from 'react'
import { connect } from 'react-redux';
import { bindActionCreators } from 'redux';

import * as userActions from '../store/modules/user';
import { isValid } from '../lib/token';

class TokenChecker extends Component {

  componentDidUpdate() {
    const { logged } = this.props.user;
    const { accessToken, refreshToken } = this.props.user.tokens;
    if (!logged) return;
    
    if (!isValid(accessToken)) {
      const { UserActions } = this.props;
      UserActions.reissue(refreshToken)
      .then(resp => {
        if (resp.status === 200) {
          console.log('refreshed token');
        } else {
          console.log('refresh token invalid force to logout');
          UserActions.logout();
        }
      })
      .catch(err => {
        console.log('connection failed');
        console.log(err);
        UserActions.logout();
      });
    }
  }

  render() {
    return (
      <></>
    )
  }
}

export default connect(
  state => ({
    user: state.user
  }),
  dispatch => ({
    UserActions: bindActionCreators(userActions, dispatch)
  })
)(TokenChecker);