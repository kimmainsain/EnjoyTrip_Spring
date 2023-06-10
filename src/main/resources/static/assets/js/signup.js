if (document.querySelector("#signup-button")) {
    document.querySelector("#signup-button").addEventListener("click", async (e) => {
        let id = document.querySelector("#userid").value;
        let pw = document.querySelector("#userpw").value;
        let nickname = document.querySelector("#nickname").value;
        //let region = document.querySelector("#region").value;
        //let keyword = document.querySelector("#keyword").value;
        let email = document.querySelector("#email").value;

        let rst = await register(id, pw, nickname, /*region, keyword,*/ email);

        if(rst){
            location.href = "/";
        }

    });
}

async function register(id, pw, nickname, /*region, keyword,*/ email) {

    if (document.getElementById("userid").value == "") {
        alert("아이디를 입력해주세요.");
        return;
    }

    if (document.getElementById("userpw").value == "") {
        alert("패스워드를 입력해주세요.")
        return;
    }

    if (document.getElementById("nickname".value == "")) {
        alert("닉네임을 입력해주세요.");
        return;
    }

    if (document.getElementById("email").value == "") {
        alert("이메일을 입력해주세요.");
        return;
    }

    let hash = sha256(id + pw);

    let config = {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
        },
        body: JSON.stringify({
            id: id,
            pw: hash,
            nickname: nickname,
            email : email
        }),
    }

    let status;
    await fetch("/user", config).then((res) => {
        status = res.status
    });

    if (status === 400) { // bad request
        alert("회원가입에 실패하였습니다.")
    }else if (status === 200) { // OK
        alert(`${nickname}님 환영합니다!`);
        return true;
    } else {
        let cat = status / 100;
        if (cat === 4) {
            alert("서버에서 요청이 거부되었습니다. 지속될 경우 관리자에게 문의하세요.")
        } else if (cat === 5) {
            alert("서버에 오류가 발생하였습니다. 지속될 경우 관리자에게 문의하세요.")
        }
    }
    return false;
}
/*
function validate(email, nickname, region, keyword) {
    // let emailReg = '^[a-zA-Z0-9+-\_.]+@[a-zA-Z0-9-]+\.[a-zA-Z0-9-.]+$';
    // if(!email.match(emailReg)){
    //     alert("이메일 형식을 체크해주세요.");
    //     return false;
    // }

    if (keyword == "") {
        alert("키워드를 넣어 주세요.");
        return false;
    }

    if (nickname == "") {
        alert("닉네임을 넣어 주세요.");
        return false;
    }

    if (region == "") {
        alert("지역을 선택해 주세요.");
        return false;
    }

    return true;
}
*/