import { BrowserRouter, Switch, Route,  useHistory} from "react-router-dom";
import Login from "./components/Login.js"; 
import './css/App.css';
import React, { Component, useEffect, useState } from "react";
import Main from "./components/Main"
import About from "./components/About.js";
import Fetch from "./components/Fetch.js";
import axios from 'axios';
import FetchNote from "./components/FetchNote.js";
import PatchWrite from "./components/PatchWrite.js";

const App = () => {
  const [isLogin,setIsLogin] = useState(false);
  const [curPatchId,changePatchId] = useState(-1);
  const [accessToken,setAccessToken] = useState("");

  useEffect(() => {
    const url = new URL(window.location.href)
    const authorizationCode = url.searchParams.get('code')
    if (authorizationCode) {
      // authorization server로부터 클라이언트로 리디렉션된 경우, authorization code가 함께 전달된다.
      setAccessToken(authorizationCode);
      getAccessToken(accessToken);
    }
  },[])

  function getAccessToken(authorizationCode) {
    let callbackURL = 'https://localhost:8080/oauth';
    axios.get(callbackURL, { params: { code: authorizationCode }})
          .then(res => {
            setIsLogin(true);
          })
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
            {isLogin ? <Main /> : <About />}
          </Route>
          <Route exact path="/patch">
            <Fetch
              curPatchId = {curPatchId}
              changePatchId = {changePatchId}
            />
          </Route>
          <Route exact path="/fetchNote">
            <FetchNote
              curPatchId = {curPatchId}
            />
          </Route>
          <Route exact path="/write">
            <PatchWrite
              curPatchId = {curPatchId}
            />
          </Route>
          <Route exact path="/mypage"></Route>
        </Switch>
      </BrowserRouter>
    </div>
  );
}

export default App;
