<%@ include file="./base.jsp" %>
<style>
 .divImageSx {
    background-image: url(./images/sp/login.jpg);
    -webkit-background-size: cover;
    background-size: cover;
    margin-top: 0px;
    border-radius: 4px;
    height: 100%;
    width: 290px;
}
 
.fbButton{
 	height: 40;
 	color: white;
 	font-size:14px;
 	background-color: #3566a5; cursor:hand
}
.gButton{
 	height: 40;
 	padding-top:7px;
 	color: black;
 	font-size:14px;
 	background-color: white;
 	border:1px solid black; cursor:hand
}

</style>
<div class="align-items-center area_main">
<%@ include file="./commons/header_noconn.jsp" %>
<!-- intera riga elemento -->
 <div class="d-flex py-10 area_app area_app_login">
<!-- area aPP -->
 <div class="d-flex justify-content-center align-items-center p-10 mx-auto" 
    style="border-radius:10px;height: 490px;border:px solid red">
 
    <div id="areaSx" class="p-2 h-100 bg-light text-white shadow">
       <div class="divImageSx pt-2 hidden-sm-down" ></div>
    </div>
     
     
    <div class="p-2  h-100  bg-light text-black shadow rounded-6">
   
    <div class="card card-body">
     <ul class="list-group list-group-flush">
        <li class="list-group-item">Connetti con</li>
    <li class="list-group-item">
    
     <div class="fbButton">
     <img src='./images/sp/fbIcon.png' style='width:25px'>&nbsp;
     Login con Facebook</div>
     </li>
    <li class="list-group-item">
    <div class="gButton">
     <img src='./images/sp/googleIcon.png' style='width:25px'>&nbsp;
     Login con Google</div>
    </li>
    </ul>
    </div>
   
    <div class="card card-body align-bottom">
    <form  class="col-12">
      <div class="form-group">
        <label for="formGroupExampleInput">Email</label>
        <input type="text" class="form-control" id="formGroupExampleInput">
      </div>
      <div class="form-group">
        <label for="formGroupExampleInput2">Password</label>
        <input type="text" class="form-control" id="formGroupExampleInput2">
      </div>
      <button type='submit'>Let me in</button>
    </form>   
    </div>
    </div>
  </div>  
  <!-- fine area app -->  
 </div>   

</div>
 
<%@ include file="./commons/footer.jsp" %>
<%@ include file="./commons/js.jsp" %>
<script>
doResize();
$(window).bind("resize", function(){
 doResize();
});

function doResize(){
    var mW=$(window).width();
    $("#myW").html($(window).width()+" "+$(window).height());
    if(mW < 647)
    	$("#areaSx").hide();
    else
    	$("#areaSx").show();
}
</script>