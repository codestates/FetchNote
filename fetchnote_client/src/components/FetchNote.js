import React, { useEffect, useState } from "react";
import {faThumbsUp} from "@fortawesome/free-solid-svg-icons"
import { faThumbsDown } from "@fortawesome/free-solid-svg-icons";
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome"
import { Link } from "react-router-dom";
import Comment from "./Conment";
import Sidebar from "./Sidebar";
import "../css/FetchNote.css";
import axios from "axios";

function FetchNote({ curPatchId, accessToken }){
    const [patchTitle,setPatchTitle] = useState("");
    const [patchBody,setPatchBody] = useState("");
    const [patchRight,setPatchRight] = useState(0);
    const [patchWrong,setPatchWrong] = useState(0);
    const [patchUser,setPatchUser] = useState("");

    const url = 'https://localhost:8080/patches?patchesId=' + curPatchId;

    const getPatches = async () => {
        try {
            return await axios({
                headers: {
                    'Content-Type': 'application/json'
                },
                method: 'get',
                url: url,
            })
        } catch (e) {
            console.error(e);
        }
    }

    useEffect(() => {
        getPatches().then(resp => {
            const info = resp.data.info;
            setPatchBody(info.body);
            setPatchTitle(info.title);
            setPatchRight(info.right);
            setPatchWrong(info.wrong);
            setPatchUser(info.userId);
        })
    },[patchTitle])

    return (
        <div>
            <Sidebar accessToken={accessToken}/>
            <div className="patchNote">
                <div className="petchNote_body">
                    { patchTitle === "" ? 
                    (
                        <div>내용이 없습니다</div>
                    )
                    : 
                    (
                        <div>
                            <h1>{patchTitle}</h1>
                            <div>작성자 : {patchUser}</div>
                            <div>경험치 : userinfo 어떻게할지</div>
                            <div dangerouslySetInnerHTML={{__html: patchBody}}></div>
                        </div>
                    )}
                </div>
                <div className="petchNote_likeBtns">
                    <button>
                        <FontAwesomeIcon icon={faThumbsUp} size="2x"></FontAwesomeIcon>
                    </button>
                    <span>{patchRight}</span>
                    <button>
                        <FontAwesomeIcon icon={faThumbsDown} size="2x"></FontAwesomeIcon>
                    </button>
                    <span>{patchWrong}</span>
                </div>
                <div className="patchNote_btns">
                    <Link to="/fetchnote">
                        <span>이전</span>
                    </Link>
                    <Link to="/patch">
                        <span>목록</span>
                    </Link>
                    <Link to="/fetchnote">
                        <span>다음</span>
                    </Link>
                </div>
                <div className="commentList">
                    <Comment></Comment>
                    <Comment></Comment>
                    <div className="commentList_input">
                        <input type="text" placeholder="의견을 표현해보세요"></input>
                        <button>입력</button>
                    </div>
                </div>
            </div>
        </div>
    )
}
export default FetchNote;