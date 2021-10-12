import axios from "axios";
import {Link} from "react-router-dom"
import dotenv from "dotenv";
import "../css/Login.css"
import { useEffect } from "react"; 

dotenv.config();
function Login (){
    useEffect(()=>{
        dotenv.config()
        window.Kakao.init(process.env.REACT_APP_API_KEY);
        window.Kakao.isInitialized();
    })
    function click(){
        window.Kakao.Auth.authorize({
            redirectUri: `${process.env.REACT_APP_API_KEY}`
        })
    }
    return (
        <div id="form">
        <form id="LoginFrom">
            <div id="LoginForm_Title">
                <Link to="/login">
                    <img alt="fetchNote" src="img/logo.png"></img>
                </Link>
               <h1>Log In</h1>
            </div>
            
            <button type="button" onClick={click}>
                <img alt="Kakao" src="img/kakao_login_medium_wide.png"></img>
            </button>
        </form>
        </div>
    )
}

export default Login;