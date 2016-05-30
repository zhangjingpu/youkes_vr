function getHtml()
{
    var html= '<html>';
    html+=document.head.innerHTML;
    html+=document.body.innerHTML;
    
    for(var idx=0;idx<window.frames.length;idx++){
		var frame=window.frames[idx];
		try{
			html+=frame.window.document.body.innerHTML;
		}catch(e){
		
		}finally{
		}
     }
     html+='</html>';
    return html;
}
var html=getHtml();
window.HtmlParser.getHtml(html);

