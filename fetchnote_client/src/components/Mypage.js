import { Link, useHistory } from "react-router-dom";
import { useEffect, useState } from "react";
import Sidebar from "./Sidebar";
import "../css/Mypage.css"
import axios from "axios";

function Mypage({ BASE_URL, accessToken, favGame, setFavGame, setIsLogin }){
    const history = useHistory();
    const [userinfo, setUserinfo] = useState({});
    const [newNick,setNewNick] = useState("");
    let vis = false;

    const [r_1,reloadEffect_1] = useState(false);

    const changeNick = (e) => {
        setNewNick(e.target.value);
    }

    const switchVisible = () => {
        if(!vis){
            document.getElementById("change_user_nickname").style.display = "";
            document.getElementById("change_user_nickname_send").style.display = "";
            vis = true;
        }else {
            document.getElementById("change_user_nickname").style.display = "none";
            document.getElementById("change_user_nickname_send").style.display = "none";
            vis = false;
        }
    }

    const sendNewNick = async () => {
        if(newNick === "") alert("이름을 입력하세요");
        else {
            try {
                return await axios({
                    headers: {
                        'Content-Type': 'application/json',
                        'authorization': accessToken,
                    },
                    method: 'patch',
                    url: BASE_URL + "user",
                    data: {
                        nickname: newNick,
                    }
                }).then(() => reloadEffect_1(!r_1)).then(() => {
                    setNewNick("");
                    document.getElementById("change_user_nickname").value = "";
                    document.getElementById("change_user_nickname").style.display = "none";
                    document.getElementById("change_user_nickname_send").style.display = "none";
                    vis = false;
                });
            } catch (e) {
                console.error(e);
            }
        }
    }

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
        try{
            return await axios.patch({
                headers: {
                    'Content-Type': 'application/json',
                    'authorization': accessToken,
                },
                method: 'patch',
                url: 'https://localhost:8080/user',
                data:{
                    nickname: "컵아"
                }
            })
        }catch(e) {
            console.log(e)
        }
        
    }

    async function handleChangeNickname(){

        let res = await axios.patch('https://localhost:8080/user',{
            headers: {
                authorization: accessToken
            },
            data: {
                nickname : '컵아'
            }
        });
    }

    useEffect(() => {
        getUserInfo();
    }, [r_1]);

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
                    <button onClick={switchVisible}>이름 바꾸기</button>
                    <input type="text" id="change_user_nickname" placeholder="바꿀 이름" onChange={changeNick} style={{display: "none"}}/>
                    <button id="change_user_nickname_send" onClick={sendNewNick} style={{display: "none"}}>변경하기</button>
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