import PatchnoteBlock from "./PatchnoteBlock";
import Sidebar from "./Sidebar"
import "../css/Fetch.css"
import { Switch, Link, Redirect, Route } from "react-router-dom";
import React, { useState } from "react";
import axios from "axios";
import PatchWrite from "./PatchWrite";

function Fetch(props) {
    const writePatch = async () => {
        let suc = true;

        const postPatch = async () => {
            try {
                return await axios({
                    headers: {
                      'Content-Type': 'application/json'
                    },
                    method: 'post',
                    url: 'https://localhost:8080/patches',
                    data: {
                        gameId : 1,
                        title: "temp",
                        body: "temp"
                    }
                })
            } catch (e) {
                suc = false;
                console.log(e);
            }
        }

        const resp = await postPatch();
        let id = null;
        if(suc) {
            id = await resp.data.id;
            props.changePatchId(id);
            console.log(props.curPatchId);
        }
    }

    

    return(
        <div>
            <Sidebar></Sidebar>
            <main className="editepage">
                <div className="editpage-link__wrapper">
                    <Link to="/write" className="editpage-link" onClick={writePatch}>
                        <span>패치 쓰기</span>
                    </Link>
                </div>
                <div className="subContent_wrapper">
                    <PatchnoteBlock/>
                    <PatchnoteBlock/>
                    <PatchnoteBlock/>
                    <PatchnoteBlock/>
                </div>
            </main>
        </div>
        
    )
}

export default Fetch;