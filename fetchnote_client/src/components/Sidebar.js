import { useState } from "react";
import {Link} from "react-router-dom";
import {faHome} from "@fortawesome/free-solid-svg-icons";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import "../css/Sidebar.css";

function Sidebar(){
    // 좋아하는 게임 목록을 받아와 출력을 해야 한다
    const [likeGames , SetLikeGames] = useState(1); 
    function click(){
        console.log("버튼이 눌렀습니다");
    }
    return (
        <nav className="navigationBar">
            {/* 로고
                프로필
                레벨
                메인 페이지 가는 건 
                선호게임들 리스트*/}
            <input className="sideBarBtn" type="checkbox"></input>
            <ul className="navigationBar_list">
                <li className="navigationBar_list_elements logo">
                    <Link to="/">
                        <img alt="fetchNote" src="img/logo.png"></img>
                    </Link>
                </li>
                <li className="navigationBar_list_elements userProfile">
                        <img className="user" alt="my_page" src="img/user_profile.svg"></img>
                        <span>내 프로필</span>
                </li>
                <li className="navigationBar_list_elements userLevel">
                    <span className="userLevel">100,000</span>
                </li>
                <li className="navigationBar_list_elements mainPage">
                   <FontAwesomeIcon icon={faHome} size="3x"></FontAwesomeIcon>
                </li>
                <li>
                {
                    likeGames === null ?<div></div> : (
                    
                    <ul className="favoriteGames">
                        <li className="game">
                            <div>
                                <img alt="League Of Legends" src="img/lol-logo.svg"></img>
                            </div>
                        </li>
                        <li className="game">
                            <div>
                                <img alt="Lost Ark" src="img/Lost Ark.jpg"></img>
                            </div>
                        </li>
                        <li className="game">
                            <div>
                            <img alt="Battle Grounds" src="img/bage.jpg"></img>
                            </div>
                        </li>
                        <li className="game">
                            <div>
                            <img alt="Raindow Sixisiege" src="img/RainbowSixisiege.jpg"></img>
                            </div>
                        </li>
                    </ul>)
                }
                </li>
            </ul>
            
        </nav>
    )
}

export default Sidebar;