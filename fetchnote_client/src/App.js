import { BrowserRouter, Switch, Route,  useHistory} from "react-router-dom";
import Login from "./components/Login.js"; 
import './css/App.css';
import { Component, useEffect, useState } from "react";
import Main from "./components/Main"
import dotenv from "dotenv";
import About from "./components/About.js";
import EditePatch from "./components/EditePatch.js";
import Fetch from "./components/Fetch.js";
import axios from 'axios';
import PatchWrite from "./components/PatchWrite.js";

class App extends Component {
  constructor() {
    super();
    this.state = {
      isLogin: true,
    };
    this.getAccessToken = this.getAccessToken.bind(this);
  }

  async getAccessToken(authorizationCode) {
    let callbackURL = 'https://localhost:8080/oauth';
    await axios.get(callbackURL, { params: { code: authorizationCode }})
          .then(res => {
            this.setState({
              isLogin: true
            });
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
    const { isLogin } = this.state;
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
              <PatchWrite />
            </Route>
          </Switch>
        </BrowserRouter>
      </div>
    );
  }
}
// 전에 있던 코드
// function App() {
//   const [userInfo, setUserInfo] = useState(true);
//   return (
//     <div className="App">
//       <BrowserRouter>
//         <Switch>
//           <Route exact path="/login">
//             <Login setUserInfo={setUserInfo}/>
//           </Route>
//           <Route exact path="/">
//             {
//               userInfo ? (
//                 <Main></Main>
//               ) : (
//               <About/>
//               )
//             }
//           </Route>
//           <Route exact path="/patch">
//             <Fetch/>
//           </Route>
//           <Route exact path="/write">
//             <EditePatch/>
//           </Route>
//         </Switch>
//       </BrowserRouter>
//     </div>
//   );
// }

export default App;
