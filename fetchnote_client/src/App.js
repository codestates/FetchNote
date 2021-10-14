import { BrowserRouter, Switch, Route,  useHistory} from "react-router-dom";
import Login from "./components/Login.js"; 
import './css/App.css';
import React, { Component, useEffect, useState } from "react";
import Main from "./components/Main"
import dotenv from "dotenv";
import About from "./components/About.js";
import Fetch from "./components/Fetch.js";
import axios from 'axios';
import FetchNote from "./components/FetchNote.js";

class App extends Component {
  constructor() {
    super();
    this.state = {
      isLogin: false,
      accessToken: null
    };
    this.getAccessToken = this.getAccessToken.bind(this);
  }

  async getAccessToken(authorizationCode) {
    let callbackURL = 'https://localhost:8080/oauth';
    await axios.get(callbackURL, { params: { code: authorizationCode }})
          .then(res => {
            this.setState({
              isLogin: true,
              accessToken: res.data
            });
            console.log(this.state.accessToken);
          })
          .catch(err => {
            console.log(err);
          });
  }

  async componentDidMount() {
    const url = new URL(window.location.href)
    const authorizationCode = url.searchParams.get('code')
    if (authorizationCode) {
      // authorization server로부터 클라이언트로 리디렉션된 경우, authorization code가 함께 전달된다.
      this.getAccessToken(authorizationCode)
    }
  }

  render() {
    const { isLogin, accessToken } = this.state;
    return (
      <div className="App">
        <BrowserRouter>
          <Switch>
            <Route exact path="/login">
              <Login />
            </Route>
            <Route exact path="/">
              {
                isLogin ? (
                  <Main accessToken={accessToken}></Main>
                ) : (
                <About/>
                )
              }
            </Route>
            <Route exact path="/patch">
              <Fetch/>
            </Route>
            <Route exact path="/fetchNote">
              <FetchNote/>
            </Route>
            <Route exact path="/mypage"></Route>
          </Switch>
        </BrowserRouter>
      </div>
    );
  }
}

export default App;
