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
    const [patchUser,setPatchUser] = useState(0);
    const [patchWriter,setPatchWriter] = useState("");
    const [patchWriterExp,setPatchWriterExp] = useState(0);

    const [r_1,reloadEffect_1] = useState(false);

    const BASE_URL = "https://localhost:8080/";

    const getPatches = async () => {
        try {
            return await axios({
                headers: {
                    'Content-Type': 'application/json'
                },
                method: 'get',
                url: BASE_URL + 'patches?patchesId=' + curPatchId,
            });
        } catch (e) {
            console.error(e);
        }
    }

    const getWriter = async (id) => {
        try {
            return await axios({
                headers: {
                    'Content-Type': 'application/json'
                },
                method: 'get',
                url: BASE_URL + 'user?userId=' + id,
            });
        } catch (e) {
            console.error(e);
        }
    }

    const postrating = async (str) => {
        try {
            return await axios({
                headers: {
                'Content-Type': 'application/json'
                },
                method: 'post',
                url: BASE_URL + "/rating/" + curPatchId,
                data: {
                    rating: str,
                }
            }).then(() => reloadEffect_1(!r_1));
        } catch (e) {
            console.error(e);
        }
    }

    useEffect(() => {
        let info = null;
        async function fetchPatch () {
            await getPatches().then(resp => {
                info = resp.data.info;
            }).then(() => {
                setPatchUser(info.userId);
                setPatchBody(info.body);
                setPatchTitle(info.title);
                setPatchRight(info.right);
                setPatchWrong(info.wrong);
                getWriter(info.userId).then(resp => {
                    setPatchWriter(resp.data.info.nickname);
                    setPatchWriterExp(resp.data.info.exp);
                });
            });
        }
        fetchPatch();
    },[r_1]);

    return (
        <div>
            <Sidebar accessToken={accessToken}/>
            <div className="patchNote">
                <div className="petchNote_body">
                    { patchWriter === "" ? 
                    (
                        <div>내용이 없습니다</div>
                    )
                    : 
                    (
                        <div>
                            <h1>{patchTitle}</h1>
                            <div>작성자 : {patchWriter}</div>
                            <div>경험치 : {patchWriterExp}</div>
                            <div dangerouslySetInnerHTML={{__html: patchBody}}></div>
                        </div>
                    )}
                </div>
                <div className="petchNote_likeBtns">
                    <button onClick={() => postrating("right")}>
                        <FontAwesomeIcon icon={faThumbsUp} size="2x"></FontAwesomeIcon>
                    </button>
                    <span>{patchRight}</span>
                    <button onClick={() => postrating("wrong")}>
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