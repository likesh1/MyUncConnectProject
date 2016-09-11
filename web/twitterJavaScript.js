/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


  function call(){
 var kcyear = document.getElementsByName("year")[0],
  kcmonth = document.getElementsByName("month")[0],
  kcday = document.getElementsByName("day")[0];
       
 var d = new Date();
 var n = d.getFullYear();
 for (var i = n; i >= 1980; i--) {
  var opt = new Option();
  opt.value = opt.text = i;
  kcyear.add(opt);
    }
 kcyear.addEventListener("change", validate_date);
 kcmonth.addEventListener("change", validate_date);

 function validate_date() {
 var y = +kcyear.value, m = kcmonth.value, d = kcday.value;
 if (m === "2")
     var mlength = 28 + (!(y & 3) && ((y % 100) !== 0 || !(y & 15)));
 else var mlength = [31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31][m - 1];
 kcday.length = 0;
 for (var i = 1; i <= mlength; i++) {
     var opt = new Option();
     opt.value = opt.text = i;
     if (i == d) opt.selected = true;
     kcday.add(opt);
 }
     }
    validate_date();
  }
  
  
  function hashTagValues(inputText){
var string = inputText;

var result = string.replace(/(^|\s)(#[a-z\d-]+)/ig, "sunil has done it");
document.write(result);
return result;
  }
  
  
String.prototype.parseHashtag = function() {
	return this.replace(/[#]+[A-Za-z0-9-_]+/g, function(t) {
		var tag = t.replace("#","#")
		return '<a href="loginServlet?action=hashTags&value='+tag.substr(1)+'" style="text-decoration:none" id ="tweetMentioned" onClick="getTweet()"><span style="color:#0ca3d2">'+tag+'</span></a>';
	});
};

String.prototype.parseUsername = function() {
	return this.replace(/[@]+[A-Za-z0-9-_]+/g, function(u) {
		var username = u.replace("@","@")
		return '<span style="color:#0ca3d2">'+username+'</span>'
	});
};
 
    
function hashTags(text){
                           var message = text;
                      var msg = message.parseHashtag();
                      document.writeln(msg.parseUsername());
}
function onlyHashTags(text)
{
    var message = text;
    document.writeln(message.parseUsername());
   
}
function getTweet(){
    var x = document.getElementById("tweetMentioned").text;
    document.getElementById("hiddenField").value = x;
    
}

