import React, { useEffect, useState } from "react";
import {faThumbsUp} from "@fortawesome/free-solid-svg-icons"
import { faThumbsDown } from "@fortawesome/free-solid-svg-icons";
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome"
import { Link } from "react-router-dom";
import Comment from "./Conment";
import Sidebar from "./Sidebar";
import "../css/FetchNote.css";
import axios from "axios";

function FetchNote({ BASE_URL, curGameId, curPatchId, accessToken, changePatchId, favGame, setFavGame }){
    const [patchTitle,setPatchTitle] = useState("");
    const [patchBody,setPatchBody] = useState("");
    const [patchRight,setPatchRight] = useState(0);
    const [patchWrong,setPatchWrong] = useState(0);
    const [patchUser,setPatchUser] = useState(0);
    const [patchWriter,setPatchWriter] = useState("");
    const [patchWriterExp,setPatchWriterExp] = useState(0);
    const [comments,setComments] = useState([]);
    const [np,setNP] = useState([-1,-1]);
    const [curComment,setCurComment] = useState("");

    const [r_1,reloadEffect_1] = useState(false);

    const commentSync = (e) => {
        setCurComment(e.target.value);
    }

    const getPatchList = async () => {
        try {
            return await axios({
                headers: {
                    'Content-Type': 'application/json',
                    'authorization': accessToken,
                },
                method: 'get',
                url: BASE_URL + '/patches?gameId=' + curGameId,
            })
        } catch (e) {
            console.error(e);
        }
    }

    const getPatches = async () => {
        try {
            return await axios({
                headers: {
                    'Content-Type': 'application/json',
                    'authorization': accessToken,
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
                    'Content-Type': 'application/json',
                    'authorization': accessToken,
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
                    'Content-Type': 'application/json',
                    'authorization': accessToken,
                },
                method: 'post',
                url: BASE_URL + "rating/" + curPatchId,
                data: {
                    rating: str,
                }
            }).then(() => reloadEffect_1(!r_1));
        } catch (e) {
            console.error(e);
        }
    }

    const postComment = async () => {
        if(curComment.length === 0) alert("댓글을 입력해주세요")
        else {
            try {
                return await axios({
                    headers: {
                        'Content-Type': 'application/json',
                        'authorization': accessToken,
                    },
                    method: 'post',
                    url: BASE_URL + "comment",
                    data: {
                        patchId: curPatchId,
                        comment: curComment,
                    }
                }).then(() => reloadEffect_1(!r_1));
            } catch (e) {
                console.error(e);
            }
        }
    }

    const prevClick = () => {
        if(np[0] === -1){
            alert("이전 글이 없습니다.");
        } else {
            changePatchId(np[0]);
            reloadEffect_1(!r_1);
        }
    }

    const nextClick = () => {
        if(np[1] === -1){
            alert("다음 글이 없습니다.");
        } else {
            changePatchId(np[1]);
            reloadEffect_1(!r_1);
        }
    }

    useEffect(() => {
        let info = null;
        async function fetchPatch () {
            await getPatches().then(resp => {
                info = resp.data.info;
                setComments(resp.data.comments);
            }).then(() => {
                setPatchUser(info.userId);
                setPatchBody(info.body);
                setPatchTitle(info.title);
                setPatchRight(info.right);
                setPatchWrong(info.wrong);
                getWriter(info.userId).then(resp => {
                    setPatchWriter(resp.data.info.nickname);
                    setPatchWriterExp(resp.data.info.exp);
                })
                getPatchList().then(resp => {
                    const list = [];
                    resp.data.patches.map(el => list.push(el.patchesId));
                    const cur = list.indexOf(curPatchId);
                    const arr = [-1, -1];
                    if(cur > 0) arr[0] = list[cur-1];
                    if(cur < list.length - 1) arr[1] = list[cur+1];
                    setNP(arr);
                })
            });
        }
        fetchPatch();
    },[r_1]);

    return (
        <div>
            <Sidebar
                accessToken={accessToken}
                favGame = {favGame}
                setFavGame={setFavGame}
            />
            <div className="patchNote">
                <div className="petchNote_body">
                    { patchWriter === "" ? 
                    (
                        <div>내용이 없습니다</div>
                    )
                    : 
                    (
                        <div className="text-left text-wrapper"> 
                            <h1>{patchTitle}</h1>
                            <div className="tet-left">작성자 : {patchWriter}</div>
                            <div className="text-left">경험치 : {patchWriterExp}</div>
                            <div dangerouslySetInnerHTML={{__html: patchBody} } className="text-left"></div>
                        </div>
                    )}
                </div>
                <div className="petchNote_likeBtns">
                    <div className="petchNote_likeBtns_likeBtn.">
                        <button onClick={() => postrating("right")}>
                            <FontAwesomeIcon icon={faThumbsUp} size="2x"></FontAwesomeIcon>
                        </button>
                        <span>{patchRight}</span>
                    </div>
                    
                    <div ></div>
                    <button onClick={() => postrating("wrong")}>
                        <FontAwesomeIcon icon={faThumbsDown} size="2x"></FontAwesomeIcon>
                    </button>
                    <span>{patchWrong}</span>
                </div>
                <div className="patchNote_btns">
                    <Link to="/fetchnote" id="link-prev" onClick={prevClick}>
                        <span>이전</span>
                    </Link>
                    
                    <Link to="/patch">
                        <span>목록</span>
                    </Link>
                    <Link to="/fetchnote" id="link-next" onClick={nextClick}>
                        <span>다음</span>
                    </Link>
                </div>
                <div className="commentList">
                    {comments.length === 0 ? 
                    (
                        <div></div>
                    ) : 
                    (
                        comments.map((el,idx) => {
                            return(
                                <Comment
                                    key={idx + 1000}
                                    info={el}
                                />
                            )
                        })
                    )}
                    <div className="commentList_input">
                        <input type="text" placeholder="의견을 표현해보세요" onChange={commentSync}></input>
                        <button onClick={postComment}>입력</button>
                    </div>
                </div>
            </div>
        </div>
    )
}
export default FetchNote;