import {faStar} from "@fortawesome/free-solid-svg-icons";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { Link } from "react-router-dom";
import "../css/Block.css"

function GameBlock(props){
    return (
        <div className="contentWrapper">
            <Link to="/patch" className="link">
                <img alt="League Of Legends" src="img/lol.jpg" width="800px" height="490px">
                </img>
                <button type="button" className="favorite">
                    <FontAwesomeIcon icon={faStar} size="2x" ></FontAwesomeIcon>
                </button>
            </Link>
        </div>
        
    )
}

export default GameBlock;