import { Link } from "react-router-dom";

function PatchnoteBlock(){
    return(
        <div>
            <Link to="/patch">
                <img alt="patch note" src="img/patchNot.png" width="600px"></img>
            </Link>
        </div>
    )
}

export default PatchnoteBlock;