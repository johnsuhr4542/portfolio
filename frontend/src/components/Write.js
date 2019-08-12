import React from 'react';
import { withRouter } from 'react-router-dom';
import CKEditor from '@ckeditor/ckeditor5-react';
import ClassEditor from '@ckeditor/ckeditor5-build-classic';
import { Button, Container } from 'react-bootstrap';

import domain from '../lib/url';

const Write = ({
  author,
  title,
  content,
  onTitleChange,
  onContentChange,
  onSubmit,
  history
}) => {

  const toList = e => {
    e.preventDefault();
    if (!window.confirm('작성중인 글은 저장되지 않습니다. 정말 나가시겠습니까?')) return;
    history.push('/list');
  }

  return(
    <Container style={{ minHeight: '760px' }}>
      <div className="mt-3">
        <label htmlFor="author">author</label>
        <input className="form-control" type="text" value={ author } id="author" readOnly />
      </div>

      <div className="mt-3">
        <label htmlFor="title">title</label>
        <input
          type="text"
          value={ title }
          onChange={ onTitleChange }
          className="form-control"
        />
      </div>

      <div className="mt-3">
        <label htmlFor="content">content</label>
        <CKEditor
          editor={ ClassEditor }
          data={ content }
          onChange={ onContentChange }
          config={{
            ckfinder: { uploadUrl: `${domain}/article/upload` }
          }}
        />
      </div>

      <div className="mt-3 mb-3">
        <Button
          className="mr-3"
          variant="outline-primary"
          onClick={ onSubmit }>submit</Button>
        <Button variant="outline-danger" onClick={ toList }>cancel</Button>
      </div>
    </Container>
  );
}

export default withRouter(Write);