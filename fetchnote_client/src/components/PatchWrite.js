import React, { useState } from "react";
import Editor from "./ckeditor/Editor";
import axios from "axios";
import "../css/ckContent.css";
import "../css/PatchWrite.css";

// const Viewer = ({content}) => (
//   <div
//     className="ck-content"
//     dangerouslySetInnerHTML={{ __html: content }}
//   ></div>
// );

const PatchWrite = () => {
    const [bodyText, setBodyText] = useState("");
    const [titleText, setTitleText] = useState("");
    const [gameId, setGameId] = useState(1);

    const textFiltering = (str) => {
        str = str.replace(/<\/p>/g, "\n").replace(/<p>/g, "");
    }

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
                  url: 'https://localhost:8080/patches',
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

        const resp = await postServer();
        let info = null;
        if(suc) {
            info = await resp;
            await console.log(info);
        }
    }

    return (
      <div>
        <div id="patchWrite_header">
            <input type="button" value="전송" id="patchWrite_send" />
        </div>
        <div>
            <input id="patchWrite_title" type="text" onChange={getTitleText} />
        </div>
        <Editor
            data={""}
            uploadFolder="Test"
            uploader="bloodseeker"
            onChange={(event, editor) => {
                setBodyText(editor.getData());
                console.log(bodyText);
            }}
        />
      </div>
    );
  };
  
export default PatchWrite;


// function PatchWrite(){
//     return(
//         <form className="pageForm">
//             <div className="pageForm_title">
//                 <input type="text" placeholder="제목을 입력하세요"></input>
//                 <textarea></textarea>
//             </div>
//         </form>
//     )
// }
// export default PatchWrite;