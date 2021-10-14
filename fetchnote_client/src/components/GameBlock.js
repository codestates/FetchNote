import {faStar} from "@fortawesome/free-solid-svg-icons";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { Link } from "react-router-dom";
import "../css/Block.css"

function GameBlock({ info, changeGameId }){
    const { id, name, image } = info;

    const linkClick = () => {
        changeGameId(id);
        document.getElementById(name + id).click();
    }
    
    return (
        <div className="contentWrapper" onClick={linkClick}>
            <Link to="/patch" id={name + id} className="link" hidden/>
            <img alt={"game_img_" + id} src={"img/games/" + id + ".jpg"} width="800px" height="490px" />
            <button type="button" className="favorite">
                <FontAwesomeIcon icon={faStar} size="2x" ></FontAwesomeIcon>
            </button>
        </div>
        
    )
}

export default GameBlock;