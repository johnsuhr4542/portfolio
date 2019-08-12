import React from 'react';
import { Container, Row, Col, ListGroup } from 'react-bootstrap';
import { withRouter } from 'react-router-dom';
import dompurify from 'dompurify';
import { Link } from 'react-router-dom';

import { Button } from 'react-bootstrap';

const Detail = ({
  article,
  onArticleModify,
  onArticleDelete,
  currentUser,
  reply,
  onReplyChange,
  onReplySubmit,
  onReplyDelete,
  history
}) => {

  const toList = e => {
    e.preventDefault();
    history.push('/list');
  }

  const renderReplies = replies => {
    let elements = [];
    replies.map((item, idx) => elements.push(
      <ListGroup.Item key={ `reply_${idx}` }>
        <b>{ item.author.username }</b>
        <i> posted by { new Intl.DateTimeFormat('ko-KR').format(item.regDate) }</i>
        <p>
          { item.content }
          { currentUser === item.author.username && <Link to="#" onClick={ e => onReplyDelete(e, item.id) }>&times;</Link> }
        </p>
      </ListGroup.Item>
    ));
    return elements;
  }

  const { id, title, content, regDate, replies } = article;
  const { username } = article.author;
  return(
    <Container style={{ minHeight: '760px' }}>
      <Row>
        <Col>
          <div className="mt-3">
            <h2>
              <i>{ `<${title}>` }</i>
            </h2>
            wrote by <i>{ username }</i>, posted by { new Intl.DateTimeFormat('ko-KR').format(regDate) }
            <hr />
          </div>

          <div className="mt-3">
            <div 
              style={{ overflow: 'auto' }}
              dangerouslySetInnerHTML={{ __html: dompurify.sanitize(content) }}></div>
            <hr />
          </div>

          <div align="center">
            <button
              className="btn btn-outline-primary mr-2"
              onClick={ toList }>
              목록으로
            </button>

            { username === currentUser &&<>
              <button
                className="btn btn-outline-info mr-2"
                onClick={ e => onArticleModify(e, id) }>
                수정
              </button>

              <button
                className="btn btn-outline-danger"
                onClick={ e => onArticleDelete(e, id) }>
                삭제
              </button>
            </> }
          </div>

          <div className="mt-3">
            <label htmlFor="comment">Leave a Comment!</label>
            <textarea
              className="form-control"
              rows="3"
              id="comment"
              value={ reply }
              onChange={ onReplyChange }></textarea>
          </div>

          <div className="mt-2" align="right">
            <Button onClick={ onReplySubmit } className="mr-2" variant="outline-info">등록</Button>
          </div>

          <div className="mt-3">
            <ListGroup variant="flush">
              { renderReplies(replies) }
            </ListGroup>
          </div>

        </Col>
      </Row>
    </Container>
  );
}

export default withRouter(Detail);