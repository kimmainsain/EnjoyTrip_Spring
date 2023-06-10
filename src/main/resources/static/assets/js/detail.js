if(document.getElementById("del-btn") != null)
    document.getElementById("del-btn").addEventListener("click", del);

if(document.getElementById("modify-btn") != null){
    document.getElementById("modify-btn").addEventListener("click", function (){
        location.href = `/board/${board_name}/${article_no}/edit`
    });
}


async function del(){

    let config = {
        method: "DELETE",
        headers: {
            "Content-Type": "application/json",
        },
        body: article_no
    }

    let res = await fetch(`/api/board/${board_name}`, config);
    let status = res.status;

    if(status === 400){ // bad request
        alert("삭제 실패")
    }else if(status === 401){ // unauthorized
        alert("삭제할 권한이 없습니다.")
    }else if(status === 200) { // OK
        alert(`삭제되었습니다.`);
        location.href = `/board/${board_name}`;
    }else{
        let cat = status / 100;
        if(cat === 4){
            alert("서버에서 요청이 거부되었습니다. 지속될 경우 관리자에게 문의하세요.")
        }else if(cat === 5){
            alert("서버에 오류가 발생하였습니다. 지속될 경우 관리자에게 문의하세요.")
        }
    }

}