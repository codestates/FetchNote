import PatchnoteBlock from "./PatchnoteBlock";
import Sidebar from "./Sidebar"
import "../css/Fetch.css"
import { Switch, Link, Redirect, Route } from "react-router-dom";
import React, { useState, useEffect } from "react";
import axios from "axios";
import PatchWrite from "./PatchWrite";

function Fetch(props) {
    const [patchList,setPatchList] = useState([]);

    const getPatchList = async () => {
        try {
            return await axios({
                headers: {
                    'Content-Type': 'application/json',
                    'authorization': props.accessToken,
                },
                method: 'get',
                url: props.BASE_URL + 'patches?gameId=' + props.curGameId,
            })
        } catch (e) {
            console.error(e);
        }
    }

    useEffect(async () => {
        await getPatchList().then(resp => {
            setPatchList(resp.data.patches);
        })
    },[])

    const writePatch = async () => {
        let suc = true;

        const postPatch = async () => {
            try {
                return await axios({
                    headers: {
                      'Content-Type': 'application/json',
                      'authorization': props.accessToken,
                    },
                    method: 'post',
                    url: props.BASE_URL + 'patches',
                    data: {
                        gameId : props.curGameId,
                        title: "temp",
                        body: "temp"
                    }
                })
            } catch (e) {
                suc = false;
                console.log(e);
            }
        }

        await postPatch().then(resp => {
            props.changePatchId(resp.data.id);
            return;
        }).then(() => {
            console.log(props.curPatchId);
            document.getElementById("link-patchwrite").click();
        });
    }

    return(
        <div>
            <Sidebar
                accessToken={props.accessToken}
                favGame={props.favGame}
                setFavGame={props.setFavGame}
            />
            <main className="editepage">
                <div className="editpage-link__wrapper">
                    <Link to="/write" id="link-patchwrite" hidden />
                    <button className="editpage-link" onClick={writePatch}>?????? ??????</button>
                </div>
                
                <div className="subContent_wrapper">
                    {patchList.length === 0 ? <div>Loading...</div> : (
                        patchList.map((el, idx) => {
                            return (
                            <PatchnoteBlock 
                                key={idx + 1000000}
                                info={el}
                                changePatchId = {props.changePatchId}
                            />
                            )
                        })
                    )}
                </div>
            </main>
        </div>    
    )
}

export default Fetch;