import { BrowserRouter, Switch,Route} from "react-router-dom";
import Login from "./components/Login.js"; 
import './css/App.css';
import { useEffect, useState } from "react";
import Main from "./components/Main"
import dotenv from "dotenv";
import About from "./components/About.js";
import EditePatch from "./components/EditePatch.js";
import Fetch from "./components/Fetch.js";

function App() {
  const [userInfo, setUserInfo] = useState(true);
  return (
    <div className="App">
      <BrowserRouter>
        <Switch>
          <Route exact path="/login">
            <Login setUserInfo={setUserInfo}/>
          </Route>
          <Route exact path="/">
            {
              userInfo ? (
                <Main></Main>
              ) : (
              <About/>
              )
            }
          </Route>
          <Route exact path="/patch">
            <Fetch/>
          </Route>
          <Route exact path="/write">
            <EditePatch/>
          </Route>
        </Switch>
      </BrowserRouter>
    </div>
  );
}

export default App;
