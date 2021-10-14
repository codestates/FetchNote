import { useEffect, useState } from "react";
import {Link} from "react-router-dom";
import {faHome} from "@fortawesome/free-solid-svg-icons";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import "../css/Sidebar.css";
import axios from "axios";

function Sidebar({ accessToken, favGame, setFavGame, changeGameId }){
    // 좋아하는 게임 목록을 받아와 출력을 해야 한다
    // const { userinfo } = props;
    const [userinfo, setUserinfo] = useState({});
    
    const [r_1,reloadEffect_1] = useState(false);
    const [r_2,reloadEffect_2] = useState(0);

    const BASE_URL = "https://localhost:8080/";

    async function getUserInfo() {
        let res = await axios.get(BASE_URL + 'user', {
            headers: {
                authorization: accessToken
            }
        });
        setUserinfo(res.data.userinfo);
    }

    const getGames = async () => {
        try {
            return await axios({
                headers: {
                    'Content-Type': 'application/json',
                    'authorization': accessToken,
                },
                method: 'get',
                url: BASE_URL + "game?prefer=true",
            }).then(resp => setFavGame(resp.data.games))
            .then(reloadEffect_2(favGame));
        } catch (e) {
            console.error(e);
        }
    }

    useEffect(() => {
        async function getSideInfo() {
            await getUserInfo();
            await getGames();
        }
        getSideInfo();
    },[r_2]);

    return (
        <nav className="navigationBar">
            {/* 로고
                프로필
                레벨
                메인 페이지 가는 건 
                선호게임들 리스트*/}
            <ul className="navigationBar_list">
                <li className="navigationBar_list_elements logo">
                    <Link to="/">
                        <img alt="fetchNote" src="img/logo.png"></img>
                    </Link>
                </li>
                <li className="navigationBar_list_elements userProfile">
                        <img className="user" alt="my_page" src="img/user_profile.svg"></img>
                        <span>{userinfo.nickname}</span>
                </li>
                <li className="navigationBar_list_elements userLevel">
                    <span className="userLevel">exp : {userinfo.exp}</span>
                </li>
                <li className="navigationBar_list_elements mainPage">
                    <Link to="/mypage">
                        <FontAwesomeIcon icon={faHome} size="3x"></FontAwesomeIcon>
                    </Link>
                   
                </li>
                <li>
                {
                    favGame.length === 0 ? (<div></div>) : (
                    <ul className="favoriteGames">
                        {favGame.map((el,idx) => {
                            const linkClick = () => {
                                changeGameId(id);
                                document.getElementById(name + id + "s").click();
                            }

                            const { id, name, image } = el;

                            return(
                                <li key={id + 400} className="game">
                                <Link to="/patch" id={name + id + "s"} className="link" hidden/>
                                <div>
                                    <img alt={"game_small_img_" + id} src={"img/games/" + id + ".jpg"} onClick={linkClick}></img>
                                </div>
                                </li>
                            ) 
                        })}
                    </ul>
                    )
                }
                </li>
            </ul>
        </nav>
    )
}

export default Sidebar;