let map;
let bounds;
let curOverlay = null;

async function init() {
    let content = document.querySelector("#map");
    let coord = new kakao.maps.LatLng(37.5013, 127.0397);
    let options = {
        center: coord,
        level: 3,
    };
    bounds = new kakao.maps.LatLngBounds();
    map = new kakao.maps.Map(content, options);

    let marker = new kakao.maps.Marker({
        map:map,
        position:coord
    });

    marker.setMap(map);

    let overlayContent = makeOverlayContent('SSAFY World!', 'assets/img/mob_ssafy_logo_blue.png',
    '강남구 테헤란로 212, 301호'
    );


    let overlay = makeOverlay(marker, overlayContent, 'SSAFY');

    var mapTypeControl = new kakao.maps.MapTypeControl();
    map.addControl(mapTypeControl, kakao.maps.ControlPosition.TOPRIGHT);

    var zoomControl = new kakao.maps.ZoomControl();
    map.addControl(zoomControl, kakao.maps.ControlPosition.RIGHT);
}

function makeOverlayContent(title, img, addr) {
    title = title.length > 15 ? title.substring(0, 15) + "..." : title;
    if (addr.length > 13) {
        let l = "",
            r = "";
        if (addr.lastIndexOf(" ") == -1) {
            l = addr.substring(0, 13);
            r = addr.substring(13);
        } else {
            l = addr.substring(0, addr.lastIndexOf(" "));
            r = addr.substring(addr.lastIndexOf(" "));
        }
        addr = l + "<br/>" + r;
    }

    let content = ` <div class="wrap">
    <div class="info">
        <div class="title">
            ${title}
            <button class="close" onclick="closeOverlay()" title="닫기"></button>
        </div>
        <div class="body">
            <div class="img">
                <img src="${img}" width="73" height="70">
            </div>
            <div class="desc">
                <div class="ellipsis">${addr}</div>`;
    return content;
}

function closeOverlay() {
    if (curOverlay != null) curOverlay.setMap(null);
    curOverlay = null;
}

function setBounds() {
    map.setBounds(bounds);
}

async function makeOverlay(marker, overlayContent, keyword){
    //console.log('search keyword : ' + keyword);
    if(keyword != null){
        let articles = await blogSearch(keyword);
        let idx = Math.floor(Math.random() * articles.length);
        
        let span = `
            <span style="color:#3396f4" onclick='openWindow("${articles[idx].url}", "${keyword}")'>${articles[idx].title}</span>
        `;

        overlayContent += span;
    }else{

    }

    overlayContent += `
            </div>
        </div>
    </div> 
</div>`;

    let overlay = new kakao.maps.CustomOverlay({
        content : overlayContent,
        map:map,
        position:marker.getPosition(),
    });

    if(curOverlay == null)
        curOverlay = overlay;

    kakao.maps.event.addListener(marker, "click", function () {
        closeOverlay();
        curOverlay = overlay;
        overlay.setMap(map);
    });

    return overlay;
}

function openWindow(url, title){
    window.open(url, title);
}