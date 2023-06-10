async function blogSearch(keyword){
    keyword = encodeURI(keyword);

    let url = `/api/search?keyword=${keyword}`;

    //console.log(option);
    let json = await fetch(url).then((response)=>response.json());

    console.log(json);

    //console.log(json.documents);
    return json.documents;
}