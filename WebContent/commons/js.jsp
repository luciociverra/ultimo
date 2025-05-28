
</body>
</html>
<script>

$(document).ready(function() {
    // blocco ctr c ctr v	
	var ctrlDown = false, ctrlKey = 17,cmdKey = 91, vKey = 86, cKey = 67;
	$(document).keydown(function(e) { 
	    if (e.keyCode == ctrlKey || e.keyCode == cmdKey) ctrlDown = true;
	 }).keyup(function(e) {
	    if (e.keyCode == ctrlKey || e.keyCode == cmdKey) ctrlDown = false;
	 });
	 $(document).keydown(function(e) { 
	        if (ctrlDown && (e.keyCode == vKey || e.keyCode == cKey)) {
	        	log("funzione proibita");
	        	return false;
	        }
	  });

	  $(document).on('click','input[type=text]',function(){ this.select(); });
	  $(document).on('click','.homeTitle',function(){ self.document.location=basePath+"index.jsp"; });
	  $(window).on("resize", function() {
		   var topW = ($(window).scrollTop() || $("body").scrollTop());
		   var iH=window.innerWidth;
		   log("topW :"+ topW + " window.innerWidth: "+iH+" innerH: "+window.innerHeight);
		   if(iH<600) {
			   $(".tappo").height(1500);
		   }
		});
});

function log(aTxt){
	if(isMyPc) console.log(aTxt);
}
function jLog(aVal){
	log(JSON.stringify(aVal));
};
function jAlert(aVal){
	alert(JSON.stringify(aVal));
};
function isEmpty(el)
{
	var v=el.val();
	return v.trim().length == 0;
}
function invalidMail(el) {
	if(isEmpty(el)) return false;
    var pattern = /^([a-z\d!#$%&'*+\-\/=?^_`{|}~\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF]+(\.[a-z\d!#$%&'*+\-\/=?^_`{|}~\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF]+)*|"((([ \t]*\r\n)?[ \t]+)?([\x01-\x08\x0b\x0c\x0e-\x1f\x7f\x21\x23-\x5b\x5d-\x7e\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF]|\\[\x01-\x09\x0b\x0c\x0d-\x7f\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF]))*(([ \t]*\r\n)?[ \t]+)?")@(([a-z\d\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF]|[a-z\d\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF][a-z\d\-._~\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF]*[a-z\d\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])\.)+([a-z\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF]|[a-z\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF][a-z\d\-._~\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF]*[a-z\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])\.?$/i;
    return ! pattern.test(el.val());
} 
function endsWith(str, suffix) {
    return str.indexOf(suffix, str.length - suffix.length) !== -1;
}
function twoDigits(n) {
	    return n > 9 ? "" + n: "0" + n;
} 
function createCookie(name,value,days) {
    var expires = "";
    if (days) {
        var date = new Date();
        date.setTime(date.getTime() + (days*24*60*60*1000));
        expires = "; expires=" + date.toUTCString();
    }
    log("write cooki "+name + " "+expires);
    document.cookie = name + "=" + value + expires + "; path=/";
}
function readCookie(name) {
    var nameEQ = name + "=";
    var ca = document.cookie.split(';');
    for(var i=0;i < ca.length;i++) {
        var c = ca[i];
        while (c.charAt(0)==' ') c = c.substring(1,c.length);
        if (c.indexOf(nameEQ) == 0) return c.substring(nameEQ.length,c.length);
    }
    return null;
}
function readAllCookie() {
    var ret="";
	var ca = document.cookie.split(';');
    for(var i=0;i < ca.length;i++) {
        var c = ca[i];
        while (c.charAt(0)==' ') c = c.substring(1,c.length);
        if (c.indexOf(nameEQ) == 0) return c.substring(nameEQ.length,c.length);
    }
    return null;
}
function deleteCookie(name) {
    createCookie(name,"",-1);
}
function logJson(json) {
     Object.keys(json).forEach(function(key) {
	  console.log('Key : ' + key + '  Value : ' + json[key]);
	})
}
function callF(aFunc) {
	  $.post(basePath+'code/service.jsp?action='+aFunc);
}
function openWindow(aFolder) {
	  var open=window.open(aFolder, '_blank');
	  if (open == null || typeof(open)=='undefined')
        alert("Turn off your pop-up blocker!\n\nSafari : Preferenze - Sicurezza  blocca finestre a scomparsa=no");
}
 
//Funzione  
function api(method, url, data) {
 try {	
	 $.ajax({
		 type:method, url:url, data:data,  dataType: 'html',
				success: function(aJresp) {
					return JSON.parse(aJresp); 
		       },
			   error: function(xhr, textStatus, errorThrown) {
				   return null;
			   } 
        });
 } catch(e){alert(e);}
}

</script>