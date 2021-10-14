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
import Mypage from "./components/Mypage.js"
import PatchWrite from "./components/PatchWrite.js";

const App = () => {
  const [isLogin,setIsLogin] = useState(false);
  const [accessToken,setAccessToken] = useState(undefined);
  const [curPatchId,changePatchId] = useState(-1);
  const [curGameId,changeGameId] = useState(-1);

  const BASE_URL = "https://localhost:8080/";

  useEffect(async () => {
    const url = new URL(window.location.href)
    const authorizationCode = url.searchParams.get('code')
    if (authorizationCode) {
      // authorization server로부터 클라이언트로 리디렉션된 경우, authorization code가 함께 전달된다.
      await getAccessToken(authorizationCode);
    }
  },[])

  async function getAccessToken(authorizationCode) {
    let callbackURL = BASE_URL + 'oauth';
    await axios.get(callbackURL, { params: { code: authorizationCode }})
          .then(res => {
            return res.data;
          }).then(setAccessToken).then(() => setIsLogin(true))
          .catch(err => {
            console.log(err);
          });
  }

  return (
    <div className="App">
      <BrowserRouter>
        <Switch>
          <Route exact path="/login">
            <Login />
          </Route>
          <Route exact path="/">
            {isLogin ? 
              <Main
                accessToken={accessToken}
                BASE_URL={BASE_URL}
                curGameId={curGameId}
                changeGameId={changeGameId}
              /> 
              : 
              <About />}
          </Route>
          <Route exact path="/patch">
            <Fetch
              curGameId={curGameId}
              curPatchId = {curPatchId}
              changePatchId = {changePatchId}
              accessToken={accessToken}
            />
          </Route>
          <Route exact path="/fetchNote">
            <FetchNote
              curPatchId = {curPatchId}
              changePatchId = {changePatchId}
              accessToken = {accessToken}
            />
          </Route>
          <Route exact path="/write">
            <PatchWrite
              curPatchId = {curPatchId}
              accessToken={accessToken}
            />
          </Route>
          <Route exact path="/mypage">
            <Mypage
              accessToken={accessToken}
            />
          </Route>
        </Switch>
      </BrowserRouter>
    </div>
  );
}

export default App;