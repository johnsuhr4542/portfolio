import React, { Component } from 'react'
import { BrowserRouter as Router, Route, Switch, Redirect } from 'react-router-dom';

import { Footer, NotFound } from './components';
import { 
  MainContainer
  , ListContainer
  , WriteContainer
  , ModifyContainer
  , HeaderContainer
  , DetailContainer
  , TokenChecker } from './containers';
import LoginContainer from './containers/LoginContainer';
import RegisterContainer from './containers/RegisterContainer';


export default class App extends Component {
  render() {
    return (
       <Router>
         <Route component={ TokenChecker } />
         <Route component={ HeaderContainer } />
         <Switch>
           <Route exact path="/"            render={ props => <Redirect { ...props } to='/main' /> }  />
           <Route exact path="/main"        render={ props => <MainContainer { ...props } /> }        />
           <Route exact path="/list"        render={ props => <ListContainer { ...props } /> }        />
           <Route exact path="/detail/:id"  render={ props => <DetailContainer { ...props } /> }      />
           <Route exact path="/write"       render={ props => <WriteContainer { ...props } /> }       />
           <Route exact path="/modify/:id"  render={ props => <ModifyContainer { ...props } /> }      />
           <Route exact path="/login"       render={ props => <LoginContainer { ...props } /> }       />
           <Route exact path="/register"    render={ props => <RegisterContainer { ...props } /> }    />
           <Route component={ NotFound } />
         </Switch>
         <Route component={ Footer } />
       </Router>
    )
  }
}