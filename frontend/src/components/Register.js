import React from 'react';
import { Container, Row, Col } from 'react-bootstrap';
import { withRouter } from 'react-router-dom';

const Register = ({
  username,
  password,
  email,
  onChange,
  onSubmit,
  history
}) => {

  const toMain = e => {
    e.preventDefault();
    if(!window.confirm('작성을 취소하고 메인 페이지로 가시겠습니까?')) return;
    history.push('/main')
  }
  
  return(
    <Container style={{ minHeight: '750px' }}>
      <Row className="justify-content-md-center">
        <Col md={ 6 }>
          <div className="mt-3">
            <h2>
              <i>Register Form</i>
              <hr />
            </h2>
          </div>

          <form onSubmit={ onSubmit }>
            <label htmlFor="username">username</label>
            <input
              type="text"
              name="username"
              id="username"
              className="form-control"
              value={ username }
              onChange={ onChange }
              placeholder="enter username"
              required={ true }
            />

            <label className="mt-2" htmlFor="password">password</label>
            <input
              type="password"
              name="password"
              id="password"
              className="form-control"
              value={ password }
              onChange={ onChange }
              placeholder="enter password"
              required={ true }
            />

            <label className="mt-2" htmlFor="email">email</label>
            <input
              type="email"
              name="email"
              id="email"
              className="form-control"
              value={ email }
              onChange={ onChange }
              placeholder="enter email"
              required={ true }
            />

            <div className="mt-3" align="right">
              <input
                type="submit"
                value="등록"
                className="btn btn-outline-primary mr-2"
              />

              <input
                type="button"
                value="취소"
                className="btn btn-outline-danger"
                onClick={ toMain }
              />
            </div>
          </form>
        </Col>
      </Row>
    </Container>
  );
}

export default withRouter(Register);