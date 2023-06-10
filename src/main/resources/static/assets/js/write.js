document.getElementById("write-btn").addEventListener("click", write);

async function write(){
    let title = document.getElementById('title').value;
    let content = document.getElementById('content').value;

    if(title == ""){
        alert("제목을 입력해주세요.");
        return;
    }

    if(content == ""){
        alert("내용을 입력해주세요.")
        return;
    }

    let config = {
        method : "POST",
        headers : {
            "Content-Type": "application/json",
        },
        body : JSON.stringify({
            title : title,
            content : content
        }),
    }

    let status;
    let res = await fetch(`/api/board/${board_name}`, config).then((res)=>{
        console.log(res);
        status = res.status;
        return res;
    })

    let no = await res.json();
    console.log(no);

    if(status == 200){ // OK
        alert("글이 정상적으로 등록되었습니다.")
        location.href = `/board/${board_name}/${no}`
    }else{
        let cat = status / 100;
        if(cat === 4){
            alert("서버에서 요청이 거부되었습니다. 지속될 경우 관리자에게 문의하세요.")
        }else if(cat === 5){
            alert("서버에 오류가 발생하였습니다. 지속될 경우 관리자에게 문의하세요.")
        }
    }
}