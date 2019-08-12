import React from 'react';
import { Container, Row, Col } from 'react-bootstrap';

const NotFound = () => {
  return(
    <Container style={{ minHeight: '760px' }}>
      <Row>
        <Col>
          <h2>
            <i>Sorry, Not Found...</i>
          </h2>
        </Col>
      </Row>
    </Container>
  );
}

export default NotFound;