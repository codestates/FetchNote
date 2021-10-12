import { Link } from "react-router-dom";
import "../css/Introduce.css"

function Introduce(){
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
                    <img alt="playing game" src="img/playingGame.jpg"></img>
                    <div>패치노트 찾아다니기 <br/>귀찮으셨죠</div>
                </div>
                <div className="contentList_elements">
                    <img alt="playing game" src="img/playingGame.jpg"></img>
                    <div>패치노트가 수정이 됬어여</div>
                </div>
                <div className="contentList_elements">
                    <img alt="playing game" src="img/playingGame.jpg"></img>
                    <div>아아아아</div>
                </div>
                <div className="contentList_elements">
                    <img alt="playing game" src="img/playingGame.jpg"></img>
                    <div>여러분도 시작해보세요!</div>
                </div>
            </main>
        </div>
        
    )
}

export default Introduce;