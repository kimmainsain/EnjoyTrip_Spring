document.querySelector("#btn-search").addEventListener("click", search);

var markers = [];
async function search() {
    let select = document.getElementById("search-content-id");
    let type = select.options[select.selectedIndex].value;
    let keyword = document.getElementById("search-keyword").value;
    let area = document.getElementById("search-area");
    area = area.options[area.selectedIndex].value;

    /*
    let type = 12;
    let keyword = '해운대';
    let area = '';
    */

    keyword = encodeURI(keyword);
    
    let url = root + `/attraction?action=keyword&keyword=${keyword}&contentTypeId=${type}&areaCode=${area}`;
    //console.log(url);
    
    let config = {
    		method: "get",
    };
    
    let response = await fetch(`/api/attraction/list/paging?keyword=${keyword}&contentTypeId=${type}&sidoCode=${area}`, config);
    let json = await response.json();
    
    //let json = await fetch(url).then((response) => response.json());
    //console.log(json);
    
    let tmp_json = json;
    if (json.data == null || json.data.length == 0) return;

    let table = document.querySelector("#result").children[0].children[0];
    table.setAttribute("style", "");
    let list = document.getElementById("board-list");
    list.innerHTML = "";
    
    for (let elem of json.data) {
        var tmp = elem;
        console.log(elem);
        let item = document.createElement("tr");
        let td1 = document.createElement("td");
        let img = document.createElement("img");
        img.setAttribute("src", elem['firstImage']);
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
    
    for (let elem of json.data) {
        let point = new kakao.maps.LatLng(elem.latitude, elem.longitude);
        let marker = new kakao.maps.Marker({ position: point });
        
        markers.push(marker);

        let overlayContent = makeOverlayContent(elem.title, elem.first_image, elem.addr1);
        let overlay = await makeOverlay(marker, overlayContent, elem.title);

        marker.setMap(map);
        overlay.setMap(null);
        
        bounds.extend(point);
    }

    setBounds();
    
}

init();
