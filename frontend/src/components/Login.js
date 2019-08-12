import React from 'react';
import { Container, Row, Col, Button } from 'react-bootstrap';

const Login = ({
  username,
  password,
  onChange,
  onSubmit
}) => {
  return(
    <Container style={{ minHeight: '760px' }}>
      <Row className="mt-3 justify-content-md-center">
        <Col md={ 6 }>
          <div className="mt-2">
            <h2>Login page</h2>
            <i>demo account : john, 123456</i>
            <hr />
          </div>

          <div className="mt-2">
            <label htmlFor="username">username</label>
            <input
              type="text"
              id="username"
              name="username"
              className="form-control"
              value={ username }
              onChange={ onChange }
              placeholder="enter username"
            />
          </div>

          <div className="mt-2">
            <label htmlFor="password">password</label>
            <input
              type="password"
              id="password"
              name="password"
              className="form-control"
              value={ password }
              onChange={ onChange }
              placeholder="enter password"
            />
          </div>

          <div className="mt-3" align="right">
            <Button variant="outline-primary" onClick={ onSubmit }>로그인</Button>
          </div>
        </Col>
      </Row>
    </Container>
  );
}

Login.defaultProps = {
  username: '',
  password: '',
  onChange: () => console.log('function [onChange] is not defined'),
  onSubmit: () => console.log('function [onSubmit] is not defined')
}

export default Login;