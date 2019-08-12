import React from 'react';
import { Container } from 'react-bootstrap';

const Footer = () => {
  return(
    <footer className="py-5 bg-light">
      <Container>
        <div align="center" style={{ color: 'grey' }}>
          made by johnsuhr, email: shzpqkfl@naver.com, github: https://github.com/johnsuhr4542
        </div>
      </Container>
    </footer>
  );
}

export default Footer;