import {faSearch} from "@fortawesome/free-solid-svg-icons";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import Sidebar from "./Sidebar"
import GameBlock from "./GameBlock"
import "../css/Main.css"
import { useEffect } from "react";
import axios from "axios";

function Main(){
    useEffect(()=>{
        async function get(){
            const input = await axios.get('https://localhost:3000/user')
            console.log(input)
        }
        get();
    },[])
    return(
        <div>
            <Sidebar/>
            <main>
                <div className="wrapper">
                    <div className="searchBox">
                        <input type="text" ></input>
                        <button type="button">
                            <FontAwesomeIcon icon={faSearch} size="2x"></FontAwesomeIcon>
                        </button>
                    </div>
                </div>
                <div className="contentGrid">
                    <GameBlock/>
                </div>
            </main>
        </div>
    )
}

export default Main;