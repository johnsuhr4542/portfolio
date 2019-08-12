import React from 'react';
import { Container, Row, Col, ListGroup } from 'react-bootstrap';
import { withRouter } from 'react-router-dom';

const List = ({
  articles,
  history
}) => {

  const renderDelegate = articles => {
    let elements = [];
    articles.map((item, idx) => elements.push(
      <ListGroup.Item 
        action
        key={ idx }
        onClick={ () => history.push(`/detail/${item.id}`) }>
        <div style={{ float: 'left' }}>
          <h2>{ item.title }</h2>
        </div>
        <div style={{ float: 'right' }}>
          <i>posted by { item.author.username }, { new Intl.DateTimeFormat('ko-KR').format(item.regDate) }</i>
        </div>
      </ListGroup.Item>
    ));
    return elements;
  }

  return(
    <Container className="mt-2 mb-3" style={{ minHeight: '760px' }}>
      <Row className="mt-2 mb-2">
        <Col>
          <h1>
            <i>Article List</i>
          </h1>
          <hr />
        </Col>
      </Row>
      <Row>
        <Col>
          <ListGroup variant="flush">
            { renderDelegate(articles) }
          </ListGroup>
        </Col>
      </Row>
    </Container>
  );
}

export default withRouter(List);