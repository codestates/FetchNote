import {faStar} from "@fortawesome/free-solid-svg-icons";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { Link } from "react-router-dom";
import "../css/Block.css"

function Block(){
    return (
        <Link to="/" className="link">
            <img alt="League Of Legends" src="img/lol.jpg" width="800px">
            </img>
            <FontAwesomeIcon icon={faStar} size="2x" className="favorite"></FontAwesomeIcon>
        </Link>
    )
}

export default Block;