import React, { useState, useEffect } from "react";
import { Link } from "react-router-dom";
import "../css/PatchNoteBlock.css";

function PatchnoteBlock({ info, changePatchId }){
    const {
        body,
        createdAt,
        gameId,
        patchesId,
        right,
        title,
        updatedAt,
        userId,
        wrong
    } = info;

    const clickLink = () => {
        changePatchId(patchesId);
        document.getElementById("fetchnote" + patchesId).click();
    }

    return(
        <div className="patchnote_block" onClick={clickLink}>
            <Link to="/fetchnote" id={"fetchnote" + patchesId}/>
            <div>{title}</div>
            <div>{right}</div>
            <div>{wrong}</div>
            <div>{createdAt}</div>
        </div>
    )
}

export default PatchnoteBlock;