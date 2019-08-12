import React, { Component } from 'react'
import { bindActionCreators } from 'redux';
import { connect } from 'react-redux';

import * as userActions from '../store/modules/user';
import { Login } from '../components';

class LoginContainer extends Component {

  constructor(props) {
    super(props);
    this.state = {
      username: '',
      password: ''
    }
    this.handleChange = this.handleChange.bind(this);
  }

  handleChange = e => {
    e.preventDefault();
    this.setState({
      [e.target.name]: e.target.value
    });
  }

  handleSubmit = e => {
    e.preventDefault();
    const { UserActions, history } = this.props;
    UserActions.login(this.state)
    .then(res => {
      if (res.status === 200) {
        window.alert('로그인 성공');
        history.push('/');
      } else {
        window.alert('유저 아이디 혹은 비밀번호가 일치하지 않습니다.');
      }
    })
    .catch(err => {
      window.alert('통신 실패');
      console.log(err);
    });
  }

  render() {
    const { username, password } = this.state;
    const { handleChange, handleSubmit } = this;
    return (
      <Login
        username={ username }
        password={ password }
        onChange={ handleChange }
        onSubmit={ handleSubmit }
      />
    );
  }
}

export default connect(
  null,
  dispatch => ({
    UserActions: bindActionCreators(userActions, dispatch)
  })
)(LoginContainer);