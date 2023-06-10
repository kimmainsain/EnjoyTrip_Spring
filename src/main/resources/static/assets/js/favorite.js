document.getElementById("search-button").addEventListener("click", search);

var markers = [];

async function search() {
    let id = sessionStorage.getItem("logged");
    let userDat = JSON.parse(localStorage.getItem(id));

    let type = userDat.keyword;
    let keyword = document.getElementById("keyword").value;
    let area = userDat.region;

    /*
    let type = 12;
    let keyword = '해운대';
    let area = '';
    */

    keyword = encodeURI(keyword);
    let url = root + `/attraction?action=keyword&keyword=${keyword}&contentTypeId=${type}&areaCode=${area}`;let json = await fetch(url).then((response) => response.json());
    //let list = document.getElementById('trip-list');
    //list.innerHTML = '';

    console.log("before");
    console.log(json);
    if (json["response"]["body"]["totalCount"] == "0") {
        console.log("here");
        console.log(userDat);
        return;
    }

    let table = document.querySelector("#result").children[0].children[0];
    table.setAttribute("style", "");
    let list = document.getElementById("board-list");
    list.innerHTML = "";

    for (let elem of json["response"]["body"]["items"]["item"]) {
        let item = document.createElement("tr");
        let td1 = document.createElement("td");
        let img = document.createElement("img");
        img.setAttribute("src", elem.firstimage);
        img.setAttribute("style", "width:50px;height:50px;");
        td1.appendChild(img);
        item.appendChild(td1);
        let td2 = document.createElement("td");
        td2.textContent = elem.title;
        item.appendChild(td2);
        let td3 = document.createElement("td");
        td3.textContent = elem.addr1;
        item.appendChild(td3);
        list.appendChild(item);
    }
    
    while(markers.length > 0){
    	markers.pop().setMap(null);
    }
    
    if(curOverlay != null){
    	curOverlay.setMap(null);
    	curOverlay = null;
    }

    for (let elem of json["response"]["body"]["items"]["item"]) {
        let point = new kakao.maps.LatLng(elem.mapy, elem.mapx);
        let marker = new kakao.maps.Marker({ position: point });

        markers.push(marker);

        let overlayContent = makeOverlayContent(elem.title, elem.firstimage, elem.addr1);
        
        console.log(elem);
        let overlay = await makeOverlay(marker, overlayContent, elem.title);

        marker.setMap(map);
        overlay.setMap(null);
        
        bounds.extend(point);
    }

    setBounds();
}
init();
