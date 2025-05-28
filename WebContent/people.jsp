<%@ include file="./base.jsp" %>
<%  
People p= new People(sys);
if(! digit(request,"new")) {
   p= new People(sys,1);
}
  //  People p= new People(sys); 
    session.setAttribute("people",p);
   
	String area="ico";
	String currId=p.getId();
%>
<style>
.editor_container{
	 background-color: white; 
	 position: absolute;
	 top:125px;
	 padding-top:20px;
	 margin:7px; 
} 
@media (max-width: 992px) {
.editor_container{
	 top:95px;
   }
}


 
/* finestra principale contenete tutti i tabs */
@media screen and (max-width: 992px) {	#mainCard {height:1400px;} }
@media screen and (min-width: 992px) {	#mainCard {height:720px;} }

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
 body {
      margin: 0; /* Rimuove il margine predefinito */
      background-image: linear-gradient(rgba(0, 0, 0, .4), rgba(0, 0, 0, .5)), url('header-img.jpg'); 
      background-size: cover; /* Copre l'intera area del body */
      background-position: center; /* Centra l'immagine */
      background-repeat: no-repeat; /* Impedisce la ripetizione dell'immagine */
      background-attachment: fixed; /* Fissa l'immagine durante lo scrolling (opzionale) */
      min-height: 100vh; /* Assicura che il body occupi tutta l'altezza della finestra */
    }
  
  
.aFooter {
position:fixed;
bottom:0px;
height:60px;
overflow:none;
width: 100vw;
} 
</style> 
 
<body>
 <%@ include file="./commons/header_noconn2.jsp" %>
 <!-- area app -->
 
 <div class="container my-4">
  <div class="row">
   
  <div class="card" id="mainCard"> 
   
  <div class="card-header bg-white" style="border-bottom:0px">
  <ul class="nav nav-tabs" id="myTab" role="tablist">
  <li class="nav-item" role="presentation">
    <a class="nav-link"  onclick="newRecord()">*</a>
  </li>
  <li class="nav-item" role="presentation">
    <button class="nav-link active" id="tab1-tab" data-bs-toggle="tab" data-bs-target="#tab1" type="button" role="tab" aria-controls="tab1" aria-selected="true">Pesona</button>
  </li>
  <li class="nav-item" role="presentation">
    <button class="nav-link" id="tab2-tab" data-bs-toggle="tab" data-bs-target="#tab2" type="button" role="tab" aria-controls="tab2" aria-selected="false">Decesso</button>
  </li>
  <li class="nav-item" role="presentation">
    <button class="nav-link" id="tb3-tab" data-bs-toggle="tab" data-bs-target="#tab3" type="button" role="tab" aria-controls="tab3" aria-selected="false">Legami</button>
  </li>

  <li class="nav-item" role="presentation">
    <button class="nav-link" id="tb4-tab" data-bs-toggle="tab" data-bs-target="#tab4" type="button" role="tab" aria-controls="tab4" aria-selected="false">Storie</button>
  </li>
  
  <li class="nav-item" role="presentation">
    <a class="nav-link"  onclick="openModal('./slide.jsp','1100px','800px','slide');">Slide</a>
  </li>

  <li class="nav-item" role="presentation">
	<a class="nav-link"  onClick="self.document.location='people.jsp?id=<%=Utils.getTime()%>'">@</a>	
	 <a class="nav-link"  onclick="openStories()">IMA</a>
  </li>
 </ul>
</div>

 <div class="card-body">
  <div class="tab-content" id="myTabContent">
  <div class="tab-pane fade show active" id="tab1" role="tabpanel" aria-labelledby="tab1-tab"><%@ include file="./editor/nascita.jsp" %></div>
  <div class="tab-pane fade" id="tab2" role="tabpanel" aria-labelledby="tab2-tab"><%@ include file="./editor/morte.jsp" %></div>
  <div class="tab-pane fade" id="tab3" role="tabpanel" aria-labelledby="tab3-tab"><%@ include file="./editor/legami.jsp" %></div>
  </div>

 </div><!--  fine crd body -->
</div><!--  fine card -->

<!--  3finali -->
  </div>
  </div>
 </div>
 
  <!-- Modal Start-->
   <style>
        /* Custom styles for the modal and iframe */
        .modal-dialog.modal-fullsize {
            max-width: 90%;
            width:  380px;
            height: 500px;
            margin: 2% auto;
        }
        
        .modal-content {
            height: 100%;
            display: flex;
            flex-direction: column;
        }
        
        .modal-body {
            flex: 1;
            padding: 0; /* Remove padding to allow iframe to fill the space */
        }
        
        .modal-iframe {
            width: 100%;
            height: 100%;
            border: none; /* Remove default iframe border */
        }
    </style>
    <div class="modal fade" id="iframeModal" tabindex="-1" aria-labelledby="iframeModalLabel" aria-hidden="true">
        <div class="modal-dialog modal-fullsize">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="iframeModalLabel">Modal with iframe</h5>
                    <button type="button" class="btn-close" onClick="closeModal();" aria-label="Close"></button>
                </div>
                <div class="modal-body">
                    <!-- iframe that takes up 100% of the modal body -->
                    <iframe id="modalIframe" class="modal-iframe" src="" allowfullscreen></iframe>
                </div>
            </div>
        </div>
    </div>
  <!-- Modal End-->
    
<%@ include file="./commons/footer.jsp" %>
<%@ include file="./commons/js.jsp" %>
<script>
var mW=0;
var mH=0;
var bH=0;
doResize();
$( window ).bind("resize", function(){
 doResize();
}); 
 

function doResize(){
    mW=$(window).width();
    mH=$(window).height();
   
    $("#myW").css("font-size","12px");
    $("#myW").html("scr: "+mH+" w: "+ mW);
    /*
    if(mW < 1000) {
    	$("#header").height(bH-fH);
    } else {
    	$(".editor_container").height(650);
     
    }
  */
}
function newRecord() {
    self.document.location="<%=basePath%>people.jsp?new=1";
}


//Function to open modal with custom iframe settings
const iframeModal = new bootstrap.Modal(document.getElementById('iframeModal'));
const modalIframe = document.getElementById('modalIframe');
const modalDialog = document.querySelector('.modal-dialog');
function openModal(src, width, height,titolo="") {
    modalIframe.src = src;
    modalDialog.style.width = width;
    modalDialog.style.height = height;
    document.getElementById('iframeModalLabel').textContent = titolo;
    iframeModal.show();
}
function closeModal() {
    iframeModal.hide();
}
function openStories(){
	  openModal('./new_stories.html','800px','800px');
}
</script>
