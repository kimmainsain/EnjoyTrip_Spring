if (document.querySelector("#login-button") != null) {
    document.querySelector("#login-button").addEventListener("click", async (e) => {
        let id = document.getElementById("userid").value;
        let pw = document.getElementById("userpw").value;

        if (id == null) {
            alert("아이디를 입력해주세요.");
            return;
        }

        if (pw == null) {
            alert("패스워드를 입력해주세요.");
            return;
        }

        let res = await login(id, pw);

        if (res) {
            location.href = "/";
        } else {
            document.getElementById("userid").value = "";
            document.getElementById("userpw").value = "";
        }
    });

    document.querySelector('#login-button').setAttribute('style',
        `display: inline-block;
        margin-top: 14px;
        padding: 14px 0 13px;
        border-radius: 6px;
        border: solid 1px rgba(0,0,0,.05);
        background-color: #3396f4;
        box-sizing: border-box;
        color:white`
    );
}

if(document.querySelector('#find-button') != null){
    document.querySelector('#find-button').setAttribute('style', 
    `display: inline-block;
    margin-top: 14px;
    padding: 14px 0 13px;
    border-radius: 6px;
    border: solid 1px rgba(0,0,0,.05);
    background-color: #3396f4;
    box-sizing: border-box;
    color:white`
    );
}

if(document.querySelector('#userid') != null){
    document.querySelector('#userid').setAttribute('style',
    `
    height: 22px;
    font-size: 16px;
    line-height: 22px;
    letter-spacing: -.53px;
    color: #303038;
    z-index: 5;`
    );
}

if(document.querySelector('#userpw') != null){
    document.querySelector('#userpw').setAttribute('style',
    `
    height: 22px;
    font-size: 16px;
    line-height: 22px;
    letter-spacing: -.53px;
    color: #303038;
    z-index: 5;
    `
    );
}

if (document.querySelector("#logout-button") != null) {
    document.querySelector("#logout-button").addEventListener("click", async (e) => {
        let res = await logout()
        if (res) {
            location.href = "/";
        }
    });
}

if (document.querySelector('#find-button') != null){
    document.querySelector("#find-button").addEventListener("click", async (e) => {
        let id = document.getElementById("userid").value;
        console.log(id);
        if (id == null) {
            alert("ID를 입력해주세요.")
            return;
        }

        let res = await resetPW(id);

        if (res) {
            location.href = "/";
        }
    });
}

async function login(id, pw) {
    let hash = sha256(id + pw);

    let config = {
        method : "POST",
        headers : {
            "Content-Type": "application/json",
        },
        body : JSON.stringify({
            id : id,
            pw : hash
        }),
    }

    let status;
    let nickname = await fetch("/user/login", config).then((res)=>{
        status = res.status
        return res.text()
    });

    if(status === 400){ // bad request
        alert("아이디 혹은 패스워드가 누락되었습니다.")
    }else if(status === 401){ // unauthorized
        alert("로그인에 실패하였습니다.\n아이디와 패스워드를 확인해주세요.")
    }else if(status === 200) { // OK
        alert(`${nickname}님 환영합니다!`);
        return true;
    }else{
        let cat = status / 100;
        if(cat === 4){
            alert("서버에서 요청이 거부되었습니다. 지속될 경우 관리자에게 문의하세요.")
        }else if(cat === 5){
            alert("서버에 오류가 발생하였습니다. 지속될 경우 관리자에게 문의하세요.")
        }
    }

    return false;
}

async function logout() {
    let config = {
        method : "GET",
        headers : {
            "Content-Type": "application/json",
        },
    }

    let res = await fetch("/user/logout", config);
    let status = res.status;

    if(status === 200){
        alert("로그아웃 되었습니다.");
        return true;
    }else{
        alert("로그아웃에 실패하였습니다.");
        return false;
    }
}

async function resetPW(id){
    let config = {
        method : "POST",
        headers : {
            "Content-Type": "application/json",
        },
        body : JSON.stringify({
            id : id,
        }),
    }

    let status;
    let nickname = await fetch("/user/reset", config).then((res)=>{
        status = res.status
        return res.text()
    });

    if(status === 400){ // bad request
        alert("아이디가 누락되었거나, 문제가 발생하였습니다.")
    }else if(status == 500){ // internal server error
        alert("서버에 문제가 발생하였습니다. 메일을 확인하고 누락되었을 경우 다시 초기화를 시도해보시고, 지속될 경우 관리자에게 문의 바랍니다.")
    }else if(status == 200) { // OK
        alert(`${nickname}님 패스워드가 초기화되어 회원 정보 내의 메일로 전송되었습니다.!`);
        return true;
    }else{
        let cat = status / 100;
        if(cat == 4){
            alert("서버에서 요청이 거부되었습니다. 지속될 경우 관리자에게 문의하세요.")
        }else if(cat == 5){
            alert("서버에 오류가 발생하였습니다. 지속될 경우 관리자에게 문의하세요.")
        }
    }
    return false;
}