import React, { Component } from 'react'
import { connect } from 'react-redux';
import { bindActionCreators } from 'redux';
import axios from 'axios';

import * as userActions from '../store/modules/user';
import { Modify } from '../components';
import domain from '../lib/url';
import { isValid } from '../lib/token';

class ModifyContainer extends Component {

  constructor(props) {
    super(props);
    this.state = {
      id: undefined,
      author: '',
      title: '',
      content: ''
    }
    this.handleTitleChange = this.handleTitleChange.bind(this);
    this.handleContentChange = this.handleContentChange.bind(this);
    this.handleSubmit = this.handleSubmit.bind(this);
    this.checkToken = this.checkToken.bind(this);
  }

  // do not manipulate state.
  // this scope just check whether current user state is logged in user or not.
  componentWillMount = () => {
    const { logged } = this.props.user;
    const { history } = this.props;
    if (!logged) {
      window.alert('로그인된 사용자만 접근이 가능합니다.');
      history.push('/list');
    }
  }

  // fetching data before modify article
  componentDidMount = () => {
    const { id } = this.props.match.params;
    const { history } = this.props;
    const { username } = this.props.user.userInfo;
    this.setState({
      author: username
    })

    axios.get(`${domain}/article/${id}`)
    .then(res => {
      if (res.status === 200) {
        const article = res.data;
        this.setState({
          id: article.id,
          title: article.title,
          content: article.content
        })
      } else {
        // may return 204 no content...
        window.alert('이미 삭제되었거나 존재하지 않는 게시물입니다.');
        history.push('/list');
      }
    })
    .catch(err => {
      window.alert('이미 삭제되었거나 존재하지 않는 게시물입니다.');
      history.push('/list');
      console.log(err);
    })
  }

  handleTitleChange = e => {
    e.preventDefault();
    this.setState({
      title: e.target.value
    })
  }

  handleContentChange = (e, editor) => {
    this.setState({
      content: editor.getData()
    })
  }

  handleSubmit = e => {
    e.preventDefault();

    this.checkToken();
    
    const { accessToken } = this.props.user.tokens;
    const { history } = this.props;

    const { id, author, title, content } = this.state;
    const params = {
      id: id,
      author: {
        username: author
      },
      title: title,
      content: content
    }

    const config = {
      headers: {
        Authorization: `Bearer ${accessToken}`
      }
    }

    axios
      .put(`${domain}/article`, params, config)
      .then(() => {
        window.alert('수정되었습니다.');
        history.push(`/detail/${id}`);
      })
      .catch(err => {
        window.alert('수정에 실패했습니다.');
        console.log(err);
      })

  }

  // this function must be carried out synchronously
  checkToken = async () => {
    const { accessToken, refreshToken } = this.props.user.tokens;
    const { history } = this.props;
    if (!accessToken) {
      window.alert('부정한 접근입니다.');
      history.push('/list');
    }
    
    // if access token has been expired...
    if (!isValid(accessToken)) {
      const { UserActions } = this.props;
      await UserActions.reissue(refreshToken)
      .then(() => { /* do nothing */ })
      .catch(() => {
        // refrehs token has been expired. required to log-in again.
        window.alert('세션이 만료되었습니다. 다시 로그인 해 주세요.');
        UserActions.logout();
        history.push('/list');
      });
    }

  }

  render() {
    const { id, author, title, content } = this.state;
    if (!id)
      return <div style={{ minHeight: '760px' }}>loading...</div>;
    const { handleTitleChange, handleContentChange, handleSubmit } = this;
    return (
      <Modify
        author={ author }
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
)(ModifyContainer);