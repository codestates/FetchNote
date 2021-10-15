import { Link } from "react-router-dom";
import "../css/About.css"

function About(){
    return(
        <div className="IntroducPage">
            <header>
                <nav className="introduceMenu">
                    <img alt="Fetch Note" src="img/logo.png"></img>
                    <Link to="/login">로그인</Link>
                </nav>
            </header>
            <main className="contentList">
                <div className="contentList_elements">
                    <div className="frontground"></div>
                    <img alt="playing game" src="img/video-game.jpg"></img>
                    <div className="text">갑작스러운 게임의 <br/>너프때문에 <br/>당황스러우셨죠?</div>
                    
                </div>
                <div className="contentList_elements">
                    <div className="frontground" id="secondPage">
                        
                    </div>
                    <img alt="playing game" src="img/note.jpg"></img>
                    <div className="text">패치노트가 <br/>어디에 있는 찾기 <br/>어려우셨나요?</div>
                </div>
                <div className="contentList_elements">
                    <div className="frontground">
                        
                    </div>
                    <img alt="playing game" src="img/playingGame.jpg"></img>
                    <div className="text">그런 당신을 위해 만들었습니다!</div>
                </div>
                <div className="contentList_elements">
                    <div className="frontground"></div>
                    <img alt="playing game" src="img/window.jpg"></img>
                    <div className="text">지금 당장 시작해 보세요!</div>
                </div>
            </main>
        </div>
        
    )
}

export default About;