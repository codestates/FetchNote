import PatchnoteBlock from "./PatchnoteBlock";
import Sidebar from "./Sidebar"
import "../css/Fetch.css"
import { Link } from "react-router-dom";
import React, { useState } from "react";
import axios from "axios";

function Fetch(){
    const [patchId, setPatchId] = useState(0);

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
        let info = null;
        if(suc) {
            info = await resp;
            await console.log(info);
        }
    }

    

    return(
        <div>
            <Sidebar></Sidebar>
            <main className="editepage">
                <div className="editpage-link__wrapper">
                    {/* <input type="button" className="editpage-link" value="패치 쓰기" /> */}
                    <Link to="/write" className="editpage-link">
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