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
                <div className="patchNote-body"></div>
                <div>
                    <button>
                        <FontAwesomeIcon icon={faThumbsUp} size="2x"></FontAwesomeIcon>
                    </button>
                    <button>
                        <FontAwesomeIcon icon={faThumbsDown} size="2x"></FontAwesomeIcon>
                    </button>
                </div>
                <div>
                    <Link>
                        <span>이전</span>
                    </Link>
                    <Link>
                        <span>목록</span>
                    </Link>
                    <Link>
                        <span>다음</span>
                    </Link>
                </div>
                <div>
                    <Comment></Comment>
                </div>
            </div>
        </div>
    )
}
export default FetchNote;