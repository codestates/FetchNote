import { BrowserRouter, Switch,Route} from "react-router-dom";
import Login from "./components/Login.js"; 
import './css/App.css';
import { useEffect, useState } from "react";
import Main from "./components/Main"
import dotenv from "dotenv";
import Introduce from "./components/Introduce.js";

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
              <Introduce></Introduce>
              )
            }
          </Route>
        </Switch>
      </BrowserRouter>
    </div>
  );
}

export default App;
