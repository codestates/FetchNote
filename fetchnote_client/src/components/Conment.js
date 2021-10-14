import "../css/Comment.css"

function Comment({ info }){
    const { comment, createdAt, nickname, updatedAt } = info;

    return (
        <div className="comment">
            <div className="comment_header">
                <span className="comment_header_nickName">{nickname}</span>
                <span>{createdAt.slice(0,10) + "-" + createdAt.slice(11,19)}</span>
                <button>수정</button>
            </div>
            <div className="comment_body">
                <span>{comment}</span>
            </div>
        </div>
    )
}
export default Comment;