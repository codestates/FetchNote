import PatchnoteBlock from "./PatchnoteBlock";
import Sidebar from "./Sidebar"
import "../css/Fetch.css"
import { Link } from "react-router-dom";

function Fetch(){
    return(
        <div>
            <Sidebar></Sidebar>
            <main className="editepage">
                <div className="editpage-link__wrapper">
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