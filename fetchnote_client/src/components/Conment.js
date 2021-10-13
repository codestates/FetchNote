import "../css/Comment.css"

function Comment(){
    return (
        <div className="comment">
            <div className="comment_header">
                <span className="comment_header_nickName"></span>
                <button>수정</button>
            </div>
            <div className="comment_body">
                <span></span>
            </div>
        </div>
    )
}
export default Comment;