import React from 'react';
import { Pagination } from 'react-bootstrap';

const Paging = ({
  pageTo,
  pagination
}) => {

  const renderDelegate = ({
    page,
    prevPage,
    nextPage,
    blockBegin,
    blockEnd,
    firstBlock,
    lastBlock
  }) => {
    let elements = [];
    
    elements.push(
      <Pagination.Prev
        key={ 'page-prev' }
        onClick={ () => pageTo(prevPage) }
        disabled={ firstBlock }
      />
    );

    for (let i = blockBegin; i <= blockEnd; i++) {
      elements.push(
        <Pagination.Item
          key={ `page-item_${ i }` }
          onClick={ () => pageTo(i) }
          active={ page === i }
        >
          { i }
        </Pagination.Item>
      );
    }

    elements.push(
      <Pagination.Next
        key={ 'next-page' }
        onClick={ () => pageTo(nextPage) }
        disabled={ lastBlock }
      />
    );

    return elements;
  }

  return(
    <Pagination className="justify-content-center">
      { renderDelegate(pagination) }
    </Pagination>
  );
}

export default Paging;