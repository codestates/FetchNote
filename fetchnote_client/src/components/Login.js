import axios from "axios";
import {Link} from "react-router-dom"
import dotenv from "dotenv";
import "../css/Login.css"
import { Component, useEffect } from "react"; 

dotenv.config();
class Login extends Component {
    constructor(props) {
        super(props)
        this.socialLoginHandler = this.socialLoginHandler.bind(this)
    
        let REST_API_KEY = 'b1439c1ca8b3431678a1c6cc28df99c6';
        let REDIRECT_URI = 'https://localhost:3000'
        this.KAKAO_LOGIN_URL = `https://kauth.kakao.com/oauth/authorize?client_id=${REST_API_KEY}&redirect_uri=${REDIRECT_URI}&response_type=code`
    }
    
    socialLoginHandler() {
        window.location.assign(this.KAKAO_LOGIN_URL)
    }

    render() {
        return (
            <div id="form">
            <form id="LoginFrom">
                <div id="LoginForm_Title">
                    <Link to="/login">
                        <img alt="fetchNote" src="img/logo.png"></img>
                    </Link>
                   <h1>Log In</h1>
                </div>
                
                <button type="button" onClick={this.socialLoginHandler}>
                    <img alt="Kakao" src="img/kakao_login_medium_wide.png"></img>
                </button>
            </form>
            </div>
        )
    }
}

export default Login;