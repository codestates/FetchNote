import {faSearch} from "@fortawesome/free-solid-svg-icons";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import Sidebar from "./Sidebar"
import Block from "./Block"
import "../css/Main.css"

function Main(){
    return(
        <div>
            <Sidebar/>
            <main>
                <div className="wrapper">
                    <div className="searchBox">
                        <input type="text"></input>
                        <button type="button">
                            <FontAwesomeIcon icon={faSearch} size="2x"></FontAwesomeIcon>
                        </button>
                    </div>
                </div>
                
                
                <div>
                    <Block/>
                    <Block/>
                    <Block/>
                    <Block/>
                </div>
            </main>
        </div>
    )
}

export default Main;