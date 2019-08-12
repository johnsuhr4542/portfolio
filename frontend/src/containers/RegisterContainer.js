import React, { Component } from 'react'
import { connect } from 'react-redux';
import { bindActionCreators } from 'redux';
import axios from 'axios';

import { Register } from '../components';
import * as userActions from '../store/modules/user';
import domain from '../lib/url';

class RegisterContainer extends Component {

  constructor(props) {
    super(props);
    this.state = {
      username: '',
      password: '',
      email: ''
    }
    this.handleChange = this.handleChange.bind(this);
    this.handleSubmit = this.handleSubmit.bind(this);
  }

  handleChange = e => {
    e.preventDefault();
    this.setState({
      [e.target.name]: e.target.value
    })
  }

  handleSubmit = e => {
    e.preventDefault();
    axios
      .post(`${domain}/member`, this.state)
      .then(res => {
        if (res.status === 200) {
          window.alert('회원가입 되었습니다.');
          const { history } = this.props;
          history.push('/login');
        } else {
          window.alert('회원 가입 실패');
        }
      })
      .catch(() => {
        window.alert('입력값을 확인해 주세요.');
      });
  }

  render() {
    const { username, password, email } = this.state;
    const { handleChange, handleSubmit } = this;
    return (
      <Register
        username={ username }
        password={ password }
        email={ email }
        onChange={ handleChange }
        onSubmit={ handleSubmit }
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
)(RegisterContainer);