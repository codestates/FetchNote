import "../css/Comment.css"

function Comment(){
    return (
        <div className="comment">
            <div className="comment_header">
                <span className="comment_header_nickName"> name</span>
                <button>수정</button>
            </div>
            <div className="comment_body">
                <span>
                Lorem ipsum dolor sit amet consectetur, adipisicing elit. Dignissimos suscipit nulla sed porro blanditiis quaerat! Similique dolore inventore perspiciatis maiores a. Deleniti atque similique tempora dignissimos doloribus a ab fugit!
                </span>
            </div>
        </div>
    )
}
export default Comment;