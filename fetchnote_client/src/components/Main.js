import {faSearch} from "@fortawesome/free-solid-svg-icons";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import Sidebar from "./Sidebar"
import GameBlock from "./GameBlock"
import "../css/Main.css"
import { Component } from "react";
import axios from "axios";

class Main extends Component {
    constructor(props) {
        super(props);
        this.state = {
            userinfo: {}
        }
    }

    async getUserInfo() {
        const { accessToken }  = this.props
        console.log(accessToken);
        let res = await axios.get('https://localhost:8080/user', {
            headers: {
                authorization: accessToken
            }
        });
        this.setState({
          userinfo: res.data.userinfo
        })
    }

    componentDidMount() {
        this.getUserInfo();
    }
    
    render() {
        return(
        <div>
            <Sidebar userinfo={this.state.userinfo}/>
            <main>
                <div className="wrapper">
                    <div className="searchBox">
                        <input type="text" ></input>
                        <button type="button">
                            <FontAwesomeIcon icon={faSearch} size="2x"></FontAwesomeIcon>
                        </button>
                    </div>
                </div>
                <div className="contentGrid">
                    <GameBlock/>
                </div>
            </main>
        </div>
        )
    }
}
// function Main(){
//     return(
//         <div>
//             <Sidebar />
//             <main>
//                 <div className="wrapper">
//                     <div className="searchBox">
//                         <input type="text" ></input>
//                         <button type="button">
//                             <FontAwesomeIcon icon={faSearch} size="2x"></FontAwesomeIcon>
//                         </button>
//                     </div>
//                 </div>
//                 <div className="contentGrid">
//                     <GameBlock/>
//                 </div>
//             </main>
//         </div>
//     )
// }

export default Main;