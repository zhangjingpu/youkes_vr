var articleList=[];

function isElementParNode(ele){
    if(ele==null){
    	return false
    }

    var children=ele.childNodes;
   if(ele.tagName=="P"||ele.tagName=="DIV"||ele.tagName=="p"||ele.tagName=="div"){

	    if(children==null||children.length<=0){

	        return true;

	    }
	    if(children.length==1){
	    	if(children[0].nodeName=="#text"){
	    		return true;
	    	}
	    }
   }else{
        return false;
   }

	for(var i=0; i<children.length; i++) {
        var childE=children[i];
        if(childE.tagName=="A"||childE.tagName=="a"
        ||childE.tagName=="B"||childE.tagName=="b"
        ||childE.tagName=="CODE"||childE.tagName=="code"
        ||childE.tagName=="SPAN"||childE.tagName=="span"
        ||childE.tagName=="STRONG"||childE.tagName=="strong"
        ){
        }else{
            return false;
        }

    }

    return true;


    //only have <a> <code> <span> <strong> <b>


}

String.prototype.trim= function(){
    // 用正则表达式将前后空格
    // 用空字符串替代。
    return this.replace(/(^\s*)|(\s*$)/g, "");
}

function traverseElements(ele){
    if(ele==null){
        return;
    }



    var childrenCount=0;
    var children=ele.childNodes;
    if(children==null||children.length==0){
        childrenCount=0;
    }else{
        childrenCount=children.length;
    }

    if(ele.tagName=="video"||ele.tagName=="VIDEO"){
        var videoSrc=ele.getAttribute("src");
        if(videoSrc==null||videoSrc==""){
            var videoSE=ele.querySelector('source');
            if(videoSE!=null){
                 videoSrc=videoSE.getAttribute("src");
            }
        }
        var poster=ele.getAttribute("poster");
        articleList.push({type:"video",src:videoSrc,poster:poster});

    }else if(ele.tagName=="img"||ele.tagName=="IMG"){
            var imgSrc=ele.getAttribute("src");
            articleList.push({type:"img",src:imgSrc});
    }else if(ele.tagName=="a"||ele.tagName=="A"){
             var href=ele.getAttribute("href");
             var text=ele.textContent;
             if(text!=null&&text.trim()!=""){
             	articleList.push({type:"a",href:href,text:text});
             }
    }

     if(isElementParNode(ele)){
	        var text=ele.textContent;
	        articleList.push({type:"p",text:text});

	    }

    var children=ele.childNodes;
    if(children!=null&&children.length>0){
	    for(var i=0; i<children.length; i++) {
	        traverseElements(children[i])
	    }

    }
}


function go() {
    var b = document.body;
    traverseElements(b);//.children
    var json = JSON.stringify(articleList);
    //console.log(json);
    return json;

}

go();
