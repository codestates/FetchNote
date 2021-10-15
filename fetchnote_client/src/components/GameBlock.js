import {faStar} from "@fortawesome/free-solid-svg-icons";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { Link } from "react-router-dom";
import "../css/Block.css"

function GameBlock({ info, changeGameId, addFavGame }){
    const { id, name, image } = info;

    const linkClick = () => {
        changeGameId(id);
        document.getElementById(name + id).click();
    }
    
    return (
        <div>
            <button type="button" className="favorite" onClick={() => addFavGame(id)}>
                    <FontAwesomeIcon icon={faStar} size="2x" ></FontAwesomeIcon>
                </button>
            <div className="contentWrapper" onClick={linkClick}>
                <Link to="/patch" id={name + id} className="link" hidden/>
                <img alt={"game_img_" + id} src={"img/games/" + id + ".jpg"} width="400px" height="250px" />
            </div>
        </div>
    )
}

export default GameBlock;