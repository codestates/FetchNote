import {faThumbsUp} from "@fortawesome/free-solid-svg-icons"
import { faThumbsDown } from "@fortawesome/free-solid-svg-icons";
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome"
import { Link } from "react-router-dom";
import Comment from "./Conment";
import Sidebar from "./Sidebar";
import "../css/FetchNote.css";

function FetchNote(){
    return (
        <div>
            <Sidebar/>
            <div className="patchNote">
                <div className="petchNote_body"></div>
                <div className="petchNote_likeBtns">
                    <button>
                        <FontAwesomeIcon icon={faThumbsUp} size="2x"></FontAwesomeIcon>
                    </button>
                    <button>
                        <FontAwesomeIcon icon={faThumbsDown} size="2x"></FontAwesomeIcon>
                    </button>
                </div>
                <div className="patchNote_btns">
                    <Link to="/fetchnote">
                        <span>이전</span>
                    </Link>
                    <Link to="/fetchnote">
                        <span>목록</span>
                    </Link>
                    <Link to="/fetchnote">
                        <span>다음</span>
                    </Link>
                </div>
                <div className="commentList">
                    <Comment></Comment>
                    <Comment></Comment>
                    <div className="commentList_input">
                        <input type="text" placeholder="의견을 표현해보세요"></input>
                        <button>입력</button>
                    </div>
                </div>
            </div>
        </div>
    )
}
export default FetchNote;