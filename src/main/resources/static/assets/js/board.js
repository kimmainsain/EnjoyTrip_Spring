document.getElementById("keyword-button").addEventListener("click", function(){
	keywordSearch();
});

var reloadingPage = `/api/board/${board_name}/`;

async function keywordSearch(){
	let keyword = document.getElementById("keyword").value;

	console.log(keyword);

	let config = {
		method : "GET",
		headers : {
			"Content-Type": "application/json",
		},
	}

	let encoded = encodeURI(keyword);

	let response = await fetch(`/api/board/${board_name}/search/paging?keyword=${encoded}`, config);
	let json = await response.json();

	reloadingPage = `/api/board/${board_name}/search/`

	buildBoard(json);
	buildPageNav(json);
}


/*
const editBoard = (article_no, name) => {
	
	console.log("edit");
    window.location = "./main?action=mvedit&article_no="+article_no+"&name="+name;
}

const deleteBoard = (article_no, name) => {
	
	console.log("delete");
    window.location = "./main?action=delete&article_no="+article_no+"&name="+name;
}
*/

window.onload = async function () {
	let config = {
	        method: "get"
	    };

    let response = await fetch(`/api/board/${board_name}/paging`, config);
    let json = await response.json();
    
    console.log("imported json ", json);
	
	buildBoard(json);
	buildPageNav(json);
}

const buildBoard = function(json) {
    
    let board_list = document.querySelector("#board-list");
    board_list.innerHTML = "";

    console.log(json);
    
    let listContainer = "";
    
    for (let i = 0 ; i < json.data.length ; i++) {
        let item = json.data[i];
        
        let html = `
            <tr>
                <td class="w-25">${item.articleNo }</td>
                <td class="w-25">${item.nickName }</td>
                <td class="w-50"><a
                    href="/board/${board_name}/${item.articleNo}">${item.title}</a></td>
            </tr>
        `
        listContainer += html;
    }
    
    board_list.innerHTML = listContainer;
    
    console.log("board data import complete");
}

const buildPageNav = function(json) {
	
	console.log("buildPageNav");
	
	let html = `<div class="d-flex justify-content-center" id="pageNav">
					<input id="pageNumber" onkeyup="if(window.event.keyCode==13){enterPressed(${json.pages})}" style="width: 40px" placeholder="${json.pageNum}"/>
					 / ${json.pages}
				</div>`
	
	document.querySelector('#page-nav').innerHTML = html;
	console.log("buildPageNav complete")
}

function enterPressed(pages) {
	
	console.log("enter Pressed");
	
	let pageNumberInput = document.querySelector("#pageNumber");
	
	let inputNumber = pageNumberInput.value;
	
	console.log(inputNumber);
	
	if (!Number(inputNumber)) {
		alert("숫자를 입력해주세요");
		pageNumberInput.value = "";
		return;
	} else if (inputNumber > pages) {
		alert("페이지 범위 밖입니다. 작은 숫자를 입력하세요");
		pageNumberInput.value = "";
		return;
	}
	
	reloadNewPage(inputNumber);
}

const reloadNewPage = async function (page) {
	console.log("reloading new page");
	
	let config = {
	        method: "get"
	    };

    let response = await fetch(reloadingPage + `paging?pageNum=${page}`, config);
    let json = await response.json();
    
    console.log("imported json ", json);
	
	buildBoard(json);
}

