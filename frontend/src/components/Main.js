import React from 'react';
import { Container, Row, Col } from 'react-bootstrap';
import { withRouter } from 'react-router-dom';

const Main = () => {
  return(
    <Container style={{ minHeight: '760px' }}>
      <Row>
        <Col>
          <div className="mt-3">
            <h2>
              Spring Boot + ReactJS + Bootstrap 을 활용한 간단한 CRUD 게시판입니다.
            </h2>
            <hr />
          </div>

          <div className="mt-2">
            <ul>
              <h4><i>Backend</i> - Spring Boot 2.1.5</h4>
              <li>모든 설정은 Java Configuration으로 되어 있습니다.</li>
              <li>Persistence는 JPA를 사용하였습니다. Hibernate + QueryDSL을 사용하였습니다.</li>
              <li>로그인 및 인증 관련 처리는 Spring Security + JWT 기반으로 구현하였습니다.</li>
            </ul>

            <ul>
              <h4><i>Frontend</i> - ReactJS</h4>
              <li>Create-React-App을 기반으로 한 Single Page Application으로 제작하였습니다.</li>
              <li>로그인 상태 관련, 토큰의 보관은 Redux & Redux-Persist 기술로 구현하였습니다.</li>
              <li>React-Bootstrap 라이브러리를 적극 활용하였습니다.</li>
            </ul>

            <ul>
              <h4><i>Feature</i></h4>
              <li>회원 가입 : 아이디, 비밀번호, 이메일로 간단히 회원 가입이 가능합니다.</li>
              <li>로그인 : 한 번 로그인 시 Server의 Refresh가 없는 한, 2주간 로그인이 지속됩니다.</li>
              <li>로그아웃 : 즉시 로그아웃 처리되며, 유저 정보, 토큰은 모두 삭제됩니다.</li>
              <li>게시글 열람 : 페이징 처리 & 검색 기능 있으며, 누구나 접근이 가능합니다.</li>
              <li>게시글 작성 : WYSIWYG (CKEditor)를 사용하였으며, 이미지 첨부가 가능합니다. 인가된 사용자만 작성 가능합니다.</li>
              <li>게시글 수정 : 작성자만 수정이 가능합니다.</li>
              <li>게시글 삭제 : 작성자만 삭제가 가능합니다.</li>
              <li>댓글 작성 : 인증된 사용자만 댓글을 작성할 수 있습니다.</li>
              <li>댓글 삭제 : 작성자만 삭제가 가능합니다.</li>
            </ul>

          </div>
        </Col>
      </Row>
    </Container>
  );
}

export default withRouter(Main);