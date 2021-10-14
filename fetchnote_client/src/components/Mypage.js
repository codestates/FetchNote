import { Link } from "react-router-dom";
import Sidebar from "./Sidebar";
import "../css/Mypage.css"

function Mypage(){
    return(
        <div>
            <Sidebar/>
            <div className="mypage">
                <div className="mypage_user">
                    <img alt="userProfile" src="img/user_profile.svg" height="60px"></img>
                    <div className="mypage_user_info">
                        <span className="mypage_user_nickname">Give Me A Job</span>
                        <span className="mypage_user_level">100,000</span>
                    </div>
                    
                </div>
                <div className="mypage_btn">
                    <button>로그아웃</button>
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