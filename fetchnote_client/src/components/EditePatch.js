import Sidebar from "./Sidebar";
import "../css/EditePatch.css"
import { useHistory } from "react-router-dom"

function EditePatch(){
    const history = useHistory();
    function click(){
        history.push("/patch")
    }
    return(
        <div className="write">
            <Sidebar/>
            <form className="writePage">
                <div className="writePage_title">
                    <input type="text" placeholder="제목을 입력하세요"></input>
                </div>
                <div className="writePage_menu">
                    <button type="button">h</button>
                    <button type="button">p</button>
                    <button type="button">b</button>
                    <button type="button">br</button>
                </div>
                <textarea className="writePage_content" placeholder="내용을 입력하세요">
                </textarea>
                <input type="file"></input>
                <div className="writePage_submitBtns">

                    <button type="submit" onClick={click}>확인</button>
                    <button type="reset">취소</button>
                </div>
            </form>
        </div>
        
    )

}

export default EditePatch;