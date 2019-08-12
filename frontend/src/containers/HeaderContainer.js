import React, { Component } from 'react'
import { connect } from 'react-redux';
import { bindActionCreators } from 'redux';

import { Header } from '../components';
import * as userActions from '../store/modules/user';

class HeaderContainer extends Component {

  constructor(props) {
    super(props);
    this.handleLogout = this.handleLogout.bind(this);
  }

  handleLogout = () => {
    if (!window.confirm('로그아웃 하시겠습니까?')) return;
    const { UserActions, history } = this.props;
    UserActions.logout();
    window.alert('로그아웃 되었습니다.');
    history.push('/main');
  }

  render() {
    const { logged } = this.props.user;
    const { handleLogout } = this;
    return (
      <Header
        logged={ logged }
        onLogout={ handleLogout }
      />
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
)(HeaderContainer);