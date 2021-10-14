import { Link, useHistory } from "react-router-dom";
import { useEffect, useState } from "react";
import Sidebar from "./Sidebar";
import "../css/Mypage.css"
import axios from "axios";

function Mypage({ BASE_URL, accessToken, favGame, setFavGame }){
    const history = useHistory();
    const [userinfo, setUserinfo] = useState({});

    async function getUserInfo() {
        console.log(accessToken);
        let res = await axios.get(BASE_URL + 'user', {
            headers: {
                authorization: accessToken
            }
        });
        setUserinfo(res.data.userinfo);
    }

    async function handleLogout() {
        let res = await axios.get('https://localhost:8080/logout', {
            headers: {
                authorization: accessToken
            }
        });
        setIsLogin(false);
        history.push('/');
    }

    useEffect(() => {
        getUserInfo();
    }, []);

    return(
        <div>
            <Sidebar
                accessToken={accessToken}
                favGame = {favGame}
                setFavGame = {setFavGame}
            />
            <div className="mypage">
                <div className="mypage_user">
                    <img alt="userProfile" src="img/user_profile.svg" height="60px"></img>
                    <div className="mypage_user_info">
                        <span className="mypage_user_nickname">{userinfo.nickname}</span>
                        <span className="mypage_user_level">{userinfo.exp}</span>
                    </div>
                    
                </div>
                <div className="mypage_btn">
                    <button onClick = {handleLogout}>로그아웃</button>
                </div>
                <div className="mypage_btn">
                    <button>이름 바꾸기</button>
                </div>
                <div className="mypage_link">
                    <Link to="/">전체 게임 보기</Link>
                </div>
                <div className="mypage_btn">
                    <button>회원 탈퇴</button>
                </div>
            </div>
        </div>
       
    )
}
export default Mypage;