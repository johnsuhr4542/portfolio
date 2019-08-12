import React from 'react';
import { Container, Row, Col, Button } from 'react-bootstrap';

const Search = ({
  onChange,
  onSearch,
  searchOption,
  searchValue
}) => {
  return(
    <Container className="mt-2 mb-2">
      <Row>
        <Col md={ 2 }></Col>
        <Col md={ 2 }>
          <select
            name="searchOption"
            value={ searchOption }
            className="form-control"
            onChange={ onChange }
          >
            <option value="all">all</option>
            <option value="author">author</option>
            <option value="title">title</option>
            <option value="content">content</option>
          </select>
        </Col>
          
        <Col>
          <input
            type="text"
            name="searchValue"
            value={ searchValue }
            onChange={ onChange }
            className="form-control"
          />
        </Col>

        <Col>
          <Button
            onClick={ onSearch }
            variant="outline-info"
          >검색</Button>
        </Col>
      </Row>
    </Container>
  );
}

export default Search;