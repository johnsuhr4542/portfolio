import React, { Component } from 'react'
import { connect } from 'react-redux';
import { bindActionCreators } from 'redux';
import axios from 'axios';

import { Detail } from '../components';
import domain from '../lib/url';
import { isValid } from '../lib/token';
import * as userActions from '../store/modules/user';

class DetailContainer extends Component {

  constructor(props) {
    super(props);
    this.state = {
      article: undefined,
      reply: ''
    }
    this.handleReplyChange = this.handleReplyChange.bind(this);
    this.handleReplySubmit = this.handleReplySubmit.bind(this);
    this.checkToken = this.checkToken.bind(this);
    this.handleReplyDelete = this.handleReplyDelete.bind(this);
    this.handleArticleModify = this.handleArticleModify.bind(this);
    this.handleArticleDelete = this.handleArticleDelete.bind(this);
  }

  componentDidMount = () => {
    const { id } = this.props.match.params;
    axios
      .get(`${domain}/article/${id}`)
      .then(res => {
        // response may be [204 no content]
        if (res.status === 200) {
          this.setState({
            article: res.data
          });
        }
      })
      .catch(err => {
        window.alert('통신 실패');
        console.log(err);
      });
  }

  handleReplyChange = e => {
    e.preventDefault();
    this.setState({
      reply: e.target.value
    })
  }

  handleReplySubmit = e => {
    e.preventDefault();
    const currentUser = this.props.user.userInfo.username;
    if (!currentUser) {
      window.alert('댓글을 남기려면 로그인해야 합니다.');
      return;
    }

    this.checkToken();

    const { id } = this.state.article;
    const { reply } = this.state;
    const { accessToken } = this.props.user.tokens;

    const params = {
      article: {
        id: id
      },
      author: {
        username: currentUser
      },
      content: reply
    }

    const config = {
      headers: {
        Authorization: `Bearer ${accessToken}`
      }
    }

    axios
      .post(`${domain}/reply`, params, config)
      .then(res => {
        if (res.status === 200) {
          window.alert('댓글을 등록했습니다.');
          window.location.reload();
        } else {
          window.alert('댓글 등록에 실패했습니다.');
        }
      })
      .catch(err => {
        window.alert('통신 실패');
        console.log(err);
      });
  }

  // literally, check expiration time of access token
  // if access token has been exired, try to refresh token
  checkToken = async () => {
    const { accessToken, refreshToken } = this.props.user.tokens;
    const { history } = this.props;

    // return if no token information exists
    if (!accessToken) return;

    // if access token expired.
    if (!isValid(accessToken)) {
      const { UserActions } = this.props;
      await UserActions.reissue(refreshToken)
      .then(() => console.log('access token was refreshed'))
      .catch(err => {
        // if failed, carry out logout automatically and redirect to main page
        console.log(err);
        UserActions.logout();
        history.push('/login')
      });
    }
  }

  // handle delete on reply. need to set header "Authorization"...
  handleReplyDelete = (e, id) => {
    e.preventDefault();
    if (!window.confirm('정말 삭제하시겠습니끼?')) return;
    
    // check minimum authentication => only logged on user.
    const { logged } = this.props.user;
    if (!logged) {
      window.alert('권한이 없습니다.');
      return;
    }

    // check token if access token avaliable
    // this function run synchronously so that use refreshed access token if expired.
    this.checkToken();

    const { accessToken } = this.props.user.tokens;

    const config = {
      headers: {
        Authorization: `Bearer ${accessToken}`
      }
    }

    axios
      .delete(`${domain}/reply/${id}`, config)
      .then(() => {
        window.alert('삭제되었습니다.');
        window.location.reload();
      })
      .catch(err => {
        window.alert('삭제 실패');
        console.log(err);
      });

  }

  handleArticleModify = (e, id) => {
    e.preventDefault();
    if (!window.confirm('수정하시겠습니까?')) return;
    const { history } = this.props;
    history.push(`/modify/${id}`);
  }

  handleArticleDelete = (e, id) => {
    e.preventDefault();
    if (!window.confirm('삭제하시겠습니까?')) return;
    
    // check access token if avaliable
    this.checkToken();

    const { accessToken } = this.props.user.tokens;

    const config = {
      headers: {
        Authorization: `Bearer ${accessToken}`
      }
    }

    axios
      .delete(`${domain}/article/${id}`, config)
      .then(() => {
        window.alert('삭제했습니다.');
        const { history } = this.props;
        history.push('/list');
      })
      .catch(err => {
        window.alert('삭제 실패');
        console.log(err);
      })

  }

  render() {
    const { article, reply } = this.state;
    const currentUser = this.props.user.userInfo.username;
    const { handleReplyChange, handleReplySubmit, handleReplyDelete, handleArticleDelete, handleArticleModify } = this;
    if (article === undefined) return <></>
    return (
      <Detail
        article={ article }
        onArticleModify={ handleArticleModify }
        onArticleDelete={ handleArticleDelete }
        currentUser={ currentUser }
        reply={ reply }
        onReplyChange={ handleReplyChange }
        onReplySubmit={ handleReplySubmit }
        onReplyDelete={ handleReplyDelete }
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
)(DetailContainer);