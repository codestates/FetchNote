import React, { useEffect, useState } from "react";
import Editor from "./ckeditor/Editor";
import axios from "axios";
import { Link } from "react-router-dom";
import "../css/ckContent.css";
import "../css/PatchWrite.css";
import Sidebar from "./Sidebar";

const PatchWrite = (props) => {
    const [patchId, setPatchId] = useState(props.curPatchId);
    const [bodyText, setBodyText] = useState("");
    const [titleText, setTitleText] = useState("");
    const [gameId, setGameId] = useState(1);

    useEffect(() => {
        setPatchId(props.curPatchId);
    },[props])

    const getTitleText = (e) => {
        setTitleText(e.target.value);
    }

    const textsend = async () => {
        let suc = true;

        const postServer = async () => {
            try {
                return await axios({
                  headers: {
                    'Content-Type': 'application/json'
                  },
                  method: 'patch',
                  url: 'https://localhost:8080/patches' + '/' + patchId,
                  data: {
                      gameId,
                      title: titleText,
                      body: bodyText
                  }
                })
            } catch (e) {
                suc = false;
                console.error(e);
            }
        }

        await postServer().then(resp => {
            if(suc){
                document.getElementById("link-fetchnote").click();
            }
        });
    }

    return (
      <div id="patchWrite">
        <Link to="/fetchNote" id="link-fetchnote" hidden />
        <div id="patchWrite_header">
            <input id="patchWrite_header_btn" type="button" value="전송" id="patchWrite_send" onClick={textsend} />
        </div>
        <div id="patchWrite_title">
            <input id="patchWrite_title_input" type="text" placeholder="제목을 입력하세요" onChange={getTitleText} />
        </div>
        <Editor
            data={""}
            uploadFolder="Test"
            uploader="bloodseeker"
            onChange={(event, editor) => {
                setBodyText(editor.getData());
            }}
        />
      </div>
    );
  };
  
export default PatchWrite;