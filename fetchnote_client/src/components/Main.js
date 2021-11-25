import {faSearch} from "@fortawesome/free-solid-svg-icons";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import Sidebar from "./Sidebar"
import GameBlock from "./GameBlock"
import "../css/Main.css"
import React, { useEffect, useState } from "react";
import axios from "axios";

const Main = (props) => {
    const [games,setGames] = useState([]);

    const [r_1,reloadEffect_1] = useState(false);

    const getGames = async () => {
        try {
            return await axios({
                headers: {
                    'Content-Type': 'application/json',
                    'authorization': props.accessToken,
                },
                method: 'get',
                url: props.BASE_URL + "game",
            });
        } catch (e) {
            console.error(e);
        }
    }

    const addFavGame = async (id) => {
        try {
            return await axios({
                headers: {
                    'Content-Type': 'application/json',
                    'authorization': props.accessToken,
                },
                method: 'post',
                url: props.BASE_URL + "game",
                data: {
                    gameId: id,
                }
            })
        } catch (e) {
            console.error(e);
        }
    }

    useEffect(() => {
        async function fetchGame () {
            await getGames().then(resp => setGames(resp.data.games));
        }
        fetchGame();
    },[r_1])
        
    return(
    <div>
        <Sidebar
            accessToken={props.accessToken}
            favGame={props.favGame}
            setFavGame={props.setFavGame}
            changeGameId={props.changeGameId}
        />
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
                {games.length === 0 ? (
                    <div>Loading...</div>
                ) : (
                    games.map((el,idx) => {
                        return(
                            <GameBlock
                                key={idx + 900}
                                info={el}
                                changeGameId={props.changeGameId}
                                addFavGame={addFavGame}
                            />
                        )
                    })
                )}
            </div>
        </main>
    </div>
    )
}

export default Main;