import React from 'react';
import { Navbar, Nav, Container, Button } from 'react-bootstrap';
import { withRouter } from 'react-router-dom';

const Header = ({
  logged,
  onLogout,
  history
}) => {

  const toMain = e => {
    e.preventDefault();
    history.push('/');
  }

  const toList = e => {
    e.preventDefault();
    history.push('/list?page=1')
  }

  const toWrite = e => {
    e.preventDefault();
    history.push('/write')
  }

  const toLogin = e => {
    e.preventDefault();
    history.push('/login');
  }

  const toRegister = e => {
    e.preventDefault();
    history.push('/register');
  }
 
  return(
    <Navbar variant="light" bg="light">
      <Container>
        <Navbar.Brand href="#">Bootstrap Web Appliction</Navbar.Brand>
        <Nav>
          <Nav.Link href="/" onClick={ toMain }>Main Page</Nav.Link>
          <Nav.Link href="/list" onClick={ toList }>Article List</Nav.Link>
          <Nav.Link href="/write" onClick={ toWrite }>Write Article</Nav.Link>
          { !logged ? 
            <><Button className="ml-2 mr-2" onClick={ toLogin } variant="outline-primary">Login</Button>
            <Button variant="outline-info" onClick={ toRegister }>Register</Button></> :
            
            <Button className="ml-2" onClick={ onLogout } variant="outline-danger">Logout</Button>
          }
        </Nav>
      </Container>
    </Navbar>
  );
}

Header.defaultProps = {
  logged: false
}

export default withRouter(Header);