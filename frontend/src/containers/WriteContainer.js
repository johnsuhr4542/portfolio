import React, { Component } from 'react'
import { connect } from 'react-redux';
import { bindActionCreators } from 'redux';
import axios from 'axios';

import domain from '../lib/url';
import { Write } from '../components';
import * as userActions from '../store/modules/user';

class WriteContainer extends Component {

  constructor(props) {
    super(props);
    this.state = {
      title: '',
      content: ''
    }
    this.handleTitleChange = this.handleTitleChange.bind(this);
    this.handleContentChange = this.handleContentChange.bind(this);
    this.handleSubmit = this.handleSubmit.bind(this);
  }

  componentWillMount = () => {
    const { logged } = this.props.user;
    const { history } = this.props;
    if (!logged) {
      window.alert('로그인이 필요합니다');
      history.push('/login');
    }
  }

  handleTitleChange = e => {
    this.setState({
      title: e.target.value
    });
  }

  handleContentChange = (e, editor) => {
    this.setState({
      content: editor.getData()
    });
  }

  handleSubmit = e => {
    e.preventDefault();
    console.log(this.state);
  }

  sendData = data => {
    axios.post(`${domain}/article`)
  }

  render() {
    const { title, content } = this.state;
    const { handleTitleChange, handleContentChange, handleSubmit } = this;
    const { username } = this.props.user.userInfo;
    return (
      <Write
        author={ username }
        title={ title }
        content={ content }
        onTitleChange={ handleTitleChange }
        onContentChange={ handleContentChange }
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
)(WriteContainer);