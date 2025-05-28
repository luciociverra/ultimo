<!DOCTYPE html>
<%@ include file="../base.jsp" %>
<%  People p= new People(sys,61); 
    session.setAttribute("people",p);
   
	String area="ico";
	String currId="1";
%>
<style>
header {
  height: 100vh;
  background-color: gray;
  padding-top: 180px;
  border:2px dotted red;
}
.editor_container{
	 background-color: white; 
	 padding-top:20px;
	 
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
 <!--usato per date nascita morte -->
 .tipoData {
	width: 160px;
	height: 45px;
	padding-top: 10px;
	display: inline-block;
}
#footer2 {
	position:relative;
	border:4px solid orange !important;
	 
}
 
</style> 
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <meta http-equiv="X-UA-Compatible" content="ie=edge">
  <link rel="icon" href="images/title-img.png">
  <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.0/css/bootstrap.min.css" integrity="sha384-9gVQ4dYFwwWSjIDZnLEWnxCjeSWFphJiwGPXr1jddIhOegiu1FwO5qRGvFXOdJZ4" crossorigin="anonymous">
  <script defer src="https://use.fontawesome.com/releases/v5.0.10/js/all.js" integrity="sha384-slN8GvtUJGnv6ca26v8EzVaR9DC58QEwsIk9q1QXdCU8Yu8ck/tL/5szYlBbqmS+" crossorigin="anonymous"></script>
  <link href="https://fonts.googleapis.com/css?family=Montserrat" rel="stylesheet">
  <link rel="stylesheet" href="style.css">
  <title>PhotoX</title>
</head>
<body>
  
  <!-- header -->
  <header>
    <!-- navbar -->
    <nav class="navbar navbar-expand-lg fixed-top nav-menu">
      <a href="#" class="navbar-brand text-light text-uppercase"><span class="h2 font-weight-bold">....</span><span id="myW"></span><span class="h1">X</span></a>
      <button class="navbar-toggler nav-button" type="button" data-toggle="collapse" data-target="#myNavbar">
        <div class="bg-light line1"></div>
        <div class="bg-light line2"></div>
        <div class="bg-light line3"></div>
      </button>
      <div class="collapse navbar-collapse justify-content-end text-uppercase font-weight-bold" id="myNavbar">
        <ul class="navbar-nav">
          <li class="nav-item">
            <a href="#" class="nav-link m-2 menu-item nav-active">Home</a>
          </li>
          <li class="nav-item">
            <a href="#" class="nav-link m-2 menu-item">Mission</a>
          </li>
          <li class="nav-item">
            <a href="#" class="nav-link m-2 menu-item">Collection</a>
          </li>
          <li class="nav-item">
            <a href="#" class="nav-link m-2 menu-item">Gallery</a>
          </li>
          <li class="nav-item">
            <a href="#" class="nav-link m-2 menu-item">Customers</a>
          </li>
          <li class="nav-item">
            <a href="#" class="nav-link m-2 menu-item">Pricing</a>
          </li>
          <li class="nav-item">
            <a href="#" class="nav-link m-2 menu-item">Contact</a>
          </li>
        </ul>
      </div>
    </nav>
    <!-- end of navbar -->

 
   <div class="container bord editor_container">
	<ul class="nav nav-tabs" role="tablist">
		<li class="nav-item">
			<a class="nav-link active" data-toggle="tab" href="#tabs-1" role="tab">Dati Anagrafici</a>
		</li>
		<li class="nav-item">
			<a class="nav-link" data-toggle="tab" href="#tabs-2" role="tab">Decesso</a>
		</li>
	</ul><!-- Tab panes -->
	<div class="tab-content">
		<div class="tab-pane p-3 active" id="tabs-1" role="tabpanel">
			<div>1Alberi</div>
		</div>
		<div class="tab-pane p-3" id="tabs-2" role="tabpanel">
		</div>
	</div>
</div>
  
  </header>
  <!-- end of header -->
  
  <div class="bg-dark px-5 aFooter" style="position:relative;top:1px;left:1px;border:2px solid cyan;margin-top:20px">
    <div class="container-fluid">
      <div class="row text-light py-4">
  </div>
      <div class="row">
        <div class="col text-center text-light border-top pt-3">
          <p>&copy; 2018 Copyright, All Rights Reserved</p>
        </div>
      </div>
    </div>
  </div>
 
  <!-- end of footer -->
 </body>
<script>
doResize();
$( window ).bind("resize", function(){
 doResize();
});
function doResize(){
    var mW=$(window).width();
    var mH=$(window).height();
    var fH=parseInt($(".aFooter").height());
    $("#myW").html("w :"+ mW +" h "+mH +" f:"+fH);
    if(mW < 1000){
    	$(".editor_container").height(mH-fH);
    	 
    	 
    } else {
    	$(".editor_container").height(650);
    	 
     
    }
   
}
</script>
  <script src="https://code.jquery.com/jquery-3.3.1.js" integrity="sha256-2Kok7MbOyxpgUVvAk/HJ2jigOSYS2auK4Pfzbm7uH60="
  crossorigin="anonymous"></script>
   
</html>



