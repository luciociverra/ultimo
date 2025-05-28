<%@ include file="../base.jsp" %>
<%
People p= (People)session.getAttribute("people");

String area="ico";
String currId=p.getId();
%>
<style>
 .btna { padding:0px;height:40px;width:40px;}
  .bi { font-size:25px;font-weight: bold!important}
 .raised-button { width:100% } 
 .raised-button.tlist { min-width: 60px!important;border-radius: 0%!important;background-color:yellow;font-size: 0.90em!important;} 
 
    #inputImage {
    width: 0.1px;
    height: 0.1px;
    opacity: 0;
    overflow: hidden;
    position: absolute;
    z-index: -1;
  } 
  .raised-button {margin-bottom:5px}
  .img-container {width:200px;height:200px;min-height:50px;margin-left:20%;
  			      backgound-color:#e1e8cc;border:px  gray;v-align:top;padding-top:0px;}
  .movBtn { 
	background-color:#f0f6ff;
	cursor:pointer;
	color:#ffffff;
	text-align:center;
	font-family:Arial;
	font-size:15px;
	height:38px;width:40px
   }
   #imgToolBar{ height:60px;top:-10px;left:-20px}
   

@media screen  and (max-width:768px) {
 .divTable { height: 300px;border:px dotted blue;text-align:center}
 .cropper-drag-box { width:200px;}
 .img-container {width:200px;height:200px;min-height:50px;margin-left:calc($(".divTable").width() - 100px);
  }
}

@media screen  and (max-width:400px) {
 .divTable {height: 400px;border:px dotted cyan;text-align:center }
 .img-container {width:200px;height:200px;min-height:50px;margin:0 auto;}
</style>

<link rel="stylesheet" type="text/css" href="<%=basePath%>editor/cropper/cropper.css" />
 <body>
 
<div class="divTable">

<div class="btn-group mb-2" role="group">
<button type="button" class="btn" id="btLoad"><i class="icon-publish"></i> Load Image</button>
<button type="button" class="btn  btn-warning"  id="btSave" style="float:right"><i class="icon-checkmark"></i> Save</button> 
<button type="button" class="btn" id="btClose" style="float:right" onclick="self.parent.closeModal();"><i class="icon-close"></i> Close</button>
</div>
 
<div class="btn-group img-actions-buttons mb-5" role="group"  id="divComandMove" >
	<button type="button" class="btna" id="btMoveUp" data-method="move" data-option="0" data-second-option="-10">
	         <i class="bi bi-arrow-up-square"></i></button>
	<button type="button" class="btna" id="btMoveDown"  data-method="move" data-option="0" data-second-option="10">
	       <i class="bi bi-arrow-up-square"></i></button>             
	<button type="button" class="btna" id="btRotateLeft" data-method="rotate" data-option="-90">
        <i class="bi bi-arrow-counterclockwise"></i></button>
	<button type="button" class="btna" id="btRotateRight" data-method="rotate" data-option="90">
	       <i class="bi bi-arrow-clockwise"></i></button>               
 </div>
 
 

 <div class="card-body w-100">  
  <form name="upload_form" id="upload_form" enctype="multipart/form-data" method="post" action="code/imupload.jsp" onsubmit="return controlli();">
	<input type="hidden" name="area" value="<%=area%>">
	<input type="hidden" id="dataX" name="dataX">
	<input type="hidden" id="dataY" name="dataY">
	<input type="hidden" id="dataWidth"  name="dataWidth">
	<input type="hidden" id="dataHeight" name="dataHeight">
	<input type="hidden" id="origWidth"  name="origWidth">         
    <input type="file" class="sr-only" id="inputImage" name="inputImage" accept=".jpg,.jpeg,.png,.gif,.bmp,.tiff" onChange="validateAndUpload(this);">
        
        <div class="img-container" id="img-container">
          <img id="image" src="<%=basePath%>/editor/cropper/generic.png" style="width:300px">
        </div>
  </div>
   
<div class="btn-group img-actions-buttons" role="group"  id="divComandMove" >
	<button type="button" class="btna" id="btZoomOut" data-method="zoom" data-option="-0.1" >
	       <i class="bi bi-zoom-in"></i></button>
	<button type="button" class="btna" id="btZoomOut" data-method="zoom" data-option="0.1">
	        <i class="bi bi-zoom-out"></i></button> 
	<button type="button" class="btna" id="btMoveLeft" data-method="move" data-option="-10" data-second-option="0">
	        <i class="bi bi-arrow-left-square"></i></button>
	<button type="button" class="btna" id="btMoveRight" data-method="move" data-option="10" data-second-option="0">
	         <i class="bi bi-arrow-right-square"></i></button>
 </div>
   
 </div>  
 </body>
 
<script src="<%=basePath%>editor/cropper/cropper.min.js"></script>
<script src="<%=basePath%>editor/cropper/canvas-to-blob.min.js"></script>
<script language="javascript">
 var isLoaded="0";
 function setAspectRatio(aVal){
	 var iData=$('#image').cropper('getCropBoxData');
	 console.log("aspect ratio:"+aVal +"  "+iData.left)
	 if(aVal=="Free") return($('#image').cropper('setAspectRatio',0));
	 var pA = aVal.split(":")[0]; var pB = aVal.split(":")[1];
	 $('#image').cropper('setAspectRatio', pA/pB);
 }
//
 $(document).ready(
		 
	 function () {
	  'use strict';
	  //var console = window.console || { log: function () {} };
	  var URL = window.URL || window.webkitURL;
	  var $image = $('#image');
	  var $dataX = $('#dataX');
	  var $dataY = $('#dataY');
	  var $dataHeight = $('#dataHeight');
	  var $dataWidth =  $('#dataWidth');
	  $('#btLoad').click(function() { $('#inputImage').click()});
	  $('#btSave').click(function() { uploadImg();});
	  var options = {
	        aspectRatio: 1 / 1,
	        preview: '.img-preview',
	        crop: function (e) {
	          $dataX.val(Math.round(e.x));
	          $dataY.val(Math.round(e.y));
	          $dataHeight.val(Math.round(e.height));
              $dataWidth.val(Math.round(e.width));
	         }
	      };
	   var uploadedImageURL;
	
	  // Cropper
	  $image.on({
	    ready: function (e) {
	     // console.log(e.type);
	    },
	    cropstart: function (e) {
	      //  console.log(e.type, e.action);
	    },
	    cropmove: function (e) {
	      //console.log(e.type, e.action);
	    },
	    cropend: function (e) {
	     // console.log(e.type, e.action);
	    },
	    crop: function (e) {
	     // console.log(e.type, e.x, e.y, e.width, e.height, e.rotate, e.scaleX, e.scaleY);
	    },
	    zoom: function (e) {
	     // console.log(e.type, e.ratio);
	    }
	  }).cropper(options);

	  // Methods
	  $('.img-actions-buttons').on('click', '[data-method]', function () {
	    var $this = $(this);
	    var data = $this.data();
	    var $target;
	    var result;
        console.log("data - method:"+data); 
	    if ($this.prop('disabled') || $this.hasClass('disabled')) {
	      return;
	    }

	    if ($image.data('cropper') && data.method) {
	       console.log("inizio operaz."); 
		   data = $.extend({}, data); // Clone a new one

	      if (typeof data.target !== 'undefined') {
	        $target = $(data.target);

	        if (typeof data.option === 'undefined') {
	          try {
	            data.option = JSON.parse($target.val());
	          } catch (e) {
	            console.log(e.message);
	          }
	        }
	      }

	      if (data.method === 'rotate') {
	        $image.cropper('clear');
	      }

	      result = $image.cropper(data.method, data.option, data.secondOption);

	      if (data.method === 'rotate') {
	        $image.cropper('crop');
	      }

	      switch (data.method) {
	        case 'scaleX':
	        case 'scaleY':
	          $(this).data('option', -data.option);
	          break;
	      }

	      if ($.isPlainObject(result) && $target) {
	        try {
	          $target.val(JSON.stringify(result));
	        } catch (e) {
	          console.log(e.message);
	        }
	      }

	    }
	  });

	  // Keyboard
	  $(document.body).on('keydown', function (e) {

	    if (!$image.data('cropper') || this.scrollTop > 300) {
	      return;
	    }

	    switch (e.which) {
	      case 37: //left
	        e.preventDefault();
	        $image.cropper('move', -1, 0);
	        break;

	      case 38: //up
	        e.preventDefault();
	        $image.cropper('move', 0, -1);
	        break;

	      case 39: //right
	        e.preventDefault();
	        $image.cropper('move', 1, 0);
	        break;

	      case 40: //down
	        e.preventDefault();
	        $image.cropper('move', 0, 1);
	        break;
	    }

	  });


	  // Import image
	  var $inputImage = $('#inputImage');
      if (URL) {
	    $inputImage.change(function () {
	      console.log("inizio change");
	      var files = this.files;
	      var file;

	      if (!$image.data('cropper')) {
	    	  console.log("cropper esco");
	        return;
	      }

	      if (files && files.length) {
	        file = files[0];
            console.log("File type"+file.type);
	        if (/^image\/\w+$/.test(file.type)) {
	          if (uploadedImageURL) {
	            URL.revokeObjectURL(uploadedImageURL);
	          }

	          uploadedImageURL = URL.createObjectURL(file);
	          $image.cropper('destroy').attr('src', uploadedImageURL).cropper(options);
	        //  $inputImage.val('');
	        } else {
	          window.alert('Please choose an image file.');
	        }
	      }
	    });
	  } else {
	    $inputImage.prop('disabled', true).parent().addClass('disabled');
	  }

	});
 
function controlli(){
	if(document.getElementById("inputImage").value == "") {
		alert('Attenzione immagine non caricata');
		return false;
	}
	return true;
}
function validateAndUpload(input){
    var URL = window.URL || window.webkitURL;
    var file = input.files[0];
    console.log("validateAndUpload file:"+file);
    if (file) {
        var image = new Image();
        image.onload = function() {
            if (this.width) {
                 console.log('Image has width, I think it is real image'+this.width);
				 $("#origWidth").val(this.width);
				 isLoaded="1";
            }
        };
        image.src = URL.createObjectURL(file);
    }
};
function uploadImg2(){
	if(! controlli()) return;
	$("#img-container").empty();
	var str="X:"+$('#dataX').val()+" Y:"+$('#dataY').val()+" H:"+$('#dataHeight').val()+ " W:"+$('#dataWidth').val()+
    	" orig:"+$("#origWidth").val();
    console.log(str); 
	$("#upload_form").submit(); 
}
function uploadImg(){
 	var opt="{'width':"+ $('#dataWidth').val()+",'height': "+ $('#dataHeight').val()+"}";
	console.log("uploadImg:"+opt);
	var result = $('#image').cropper("getCroppedCanvas",opt);
    var formData = new FormData();
    result.toBlob(function (blob) {
	//$("#img-container").empty().addwaiting();
	formData.append('image', blob, "<%=currId%>tmpfile.jpg");       
    formData.append("currId", "<%=currId%>");
    $.ajax({
      url: "<%=basePath%>peopleimageservlet",
      type: "POST",
      data: formData,
      dataType: 'json',
      processData: false,
      contentType: false,
      success: function(jresponse) {   
     	 if(jresponse.rc==1)
     	   self.parent.reloadAvatarImage();
     	 else
     	   alert("Operazione di aggiornamento non a buon fine ");	 
        closeImStudio(true);
      },
      error: function() {          
        window.alert('Upload error!');
      }
   });                          
  }); 
}
function closeImStudio(flagReload){
	//self.parent.close_iframe(flagReload);
}
</script>
 
</html>