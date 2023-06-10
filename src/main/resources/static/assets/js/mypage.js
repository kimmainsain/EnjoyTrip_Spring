document.querySelector("#quit-button").addEventListener("click", async function () {
	let pw = document.getElementById("userpw").value;

	if (pw == null || pw == "") {
		alert("패스워드를 입력해주세요.");
		return;
	}

	let hash = sha256(id + pw);

	let config = {
		method: "DELETE",
		headers: {
			"Content-Type": "application/json",
		},
		body: JSON.stringify({
			pw: hash,
		}),
	}

	let status;
	await fetch("/user/drop", config).then(res => status = res.status)

	if(status === 400){ // bad request
		alert("회원 탈퇴에 실패하였습니다.")
	}else if(status === 401){ // unauthorized
		alert("회원 탈퇴 권한이 없습니다.")
	}else if(status === 200) { // OK
		alert(`회원 탈퇴되었습니다.`);
		location.href = "/";
	}else{
		let cat = status / 100;
		if(cat === 4){
			alert("서버에서 요청이 거부되었습니다. 지속될 경우 관리자에게 문의하세요.")
		}else if(cat === 5){
			alert("서버에 오류가 발생하였습니다. 지속될 경우 관리자에게 문의하세요.")
		}
	}
});

document.querySelector("#modify-button").addEventListener("click", async function () {
	let pw = document.getElementById('userpw').value;

	if (pw == null || pw == "") {
		alert("패스워드를 입력해주세요.");
		return;
	}

	let hash = sha256(id + pw);

	let nickname = document.getElementById("nickname").value;

	let config = {
		method: "PUT",
		headers: {
			"Content-Type": "application/json",
		},
		body: JSON.stringify({
			id : id,
			pw: hash,
			email : document.getElementById("email").value,
			nickname : nickname
		}),
	}


	let status;
	await fetch("/user", config).then((res) => {
		status = res.status
	});

	if (status === 400) { // bad request
		alert("회원 정보 수정에 실패하였습니다.")
	} else if (status === 401) { // unauthorized
		alert("수정 권한이 없습니다.")
	} else if (status === 200) { // OK
		alert(`${nickname}님의 정보가 수정되었습니다.`);
		location.href = "/";
	} else {
		let cat = status / 100;
		if (cat === 4) {
			alert("서버에서 요청이 거부되었습니다. 지속될 경우 관리자에게 문의하세요.")
		} else if (cat === 5) {
			alert("서버에 오류가 발생하였습니다. 지속될 경우 관리자에게 문의하세요.")
		}
	}
});