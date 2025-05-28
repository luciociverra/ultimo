 
 <style>
 

.radio-btn-wrapper {
  margin: 10px 10px;
 }

.radio-btn {
  background-color: #ededed;
  border: 1px solid #4A4A4A;
  box-shadow: 0px 0px 0px 1px #9fb4f2;
  color: #4A5362;
  font-size: 16px;
  width: 300px;
  line-height: 30px;
  outline: none;
 }

.radio-btn-selected {
  background-color: #FFFFFF;
  border: 1px solid #55BC7E;
  color: white;
  font-size: 18px;
  line-height: 26px;
  outline: none;
  width: 300px;
  box-shadow: 0px 0px 0px 2px #9fb4f2;
  background:linear-gradient(to bottom, #7892c2 5%, #476e9e 100%);
}

.parent-card {
       cursor: pointer;
       transition: all 0.3s ease;
   }
  .parent-card:hover {
       transform: translateY(-5px);
       box-shadow: 0 10px 20px rgba(0,0,0,0.1);
}
 .selected {
            border: 3px solid #007bff;
            background-color: rgba(0, 123, 255, 0.1);
 }

</style>
 
 
 
 <form class="form-card" id="myFormLegami" method="POST" action="">
 <input type="hidden" name="page" value="legami"/>
  <div class="row">
   <div class="col-md-6 col-sm-12">
      
        <button type="button" class="btn btn-dark mt-5" style="width:250px" onclick="searchPerson()">
           Cerca
        </button>         
			<div class="form-group col-sm-12 col-md-12 flex-column d-flex pb-5">
	           <h3 id="selectName"></h3>
	           <input type="hidden" name="selectId" id="selectId">
	    
	    
			<div class="form-group col-sm-12 col-md-12 flex-column d-flex">
	        <label class="form-control-label pb-3">Tipo legame</label>
	        
            <div class="row mt-4 hide" id="areaLegami">
                            <div class="col-md-6 mb-4">
                                <div class="card parent-card" id="father-card" data-parent="FP">
                                    <div class="card-body text-center">
                                        <h5 class="card-title">Padre</h5>
                                    </div>
                                </div>
                            </div>
                            <div class="col-md-6 mb-4">
                                <div class="card parent-card" id="mother-card" data-parent="FM">
                                    <div class="card-body text-center">
                                        <h5 class="card-title">Madre</h5>
                                    </div>
                                </div>
                            </div>
             </div>      
             
			</div>
			 
			 </div>
	</div> <!-- area lx -->
		
	<div class="col-md-6 col-sm-12 pt-4">
	   <label class="form-control-label" onClick="leggiLegami()">Legami registrati</label>
	   <ul class="legamiList list-group" id="legami_List"></ul>
	</div>
	</div>
			
	 
 
 </form>
 
<script>
   $(document).ready(function() {
	   log("legami ready");
	   $("#selectName").html('');
	   $("#selectId").val('');
	   $("#areaLegami").hide();
	   let selectedParent = null;
	   leggiLegami();
       // Handle card selection
       $(".parent-card").on("click", function() {
    	   // Remove selected class from all cards
           $(".parent-card").removeClass("selected");
           // Add selected class to clicked card
           $(this).addClass("selected");
           // Store the selected parent type
           var selectedParent = $(this).data("parent");
           addLegameCall($("#selectId").val(),selectedParent);
       });
    });
    // open and close
    function searchPerson(){
       openModal(basePath+'commons/search_name.jsp?selectAction=setIdLegame','400px','490px','Ricerca in archivio');
     }	   
   //Funzione  
   async function addLegameCall(dest, tipo) {
	   await addLegame(dest,tipo);
   }
   async function addLegame(dest, tipo) {
	  log("addLegame");
	  var dataToSend="&selectId="+dest+"&tipoLegame="+tipo
	  var url=basePath+'editor/people_edit.jsp?page=legami&action=ins'+dataToSend;
	  var r= await apiSend('POST',url);
	   $("#selectName").html('');
	   $("#selectId").val('');
	   $("#areaLegami").hide();
	  leggiLegami();
   }
   
   async function deleLegame(aId){
	   log("dele");
	   var dataToSend="&idLegame="+aId
	   var url=basePath+'editor/people_edit.jsp?page=legami&action=dele'+dataToSend;
	   var r=await apiSend('POST',url);
	   setTimeout(() => {
		    leggiLegami();
		 }, 2000);
	   
   }
   
   async function leggiLegami() {
	      log("leggiLegami");
	      var url=basePath+'editor/people_edit.jsp?page=legami&action=read&id=<%=currId%>';
		  var res=await apiSend('GET',url);
		  var legamiList = $("#legami_List");
		  legamiList.empty();
		  res.forEach(elem => {
               var r="<li class='list-group-item d-flex'>"+
	            "<a href='#' onClick='deleLegame("+elem.id+");return false;' class='mr-4'><i class='fa fa-trash'></i></a>"+
	            elem.p_name+" "+elem.p_surname+ "  " +elem.tipoLegame +"</li>";
	            legamiList.append(r);
		  });
	}
   
   async function apiSend(method, url) {
	   log("http://localhost:82"+url);
	   try {	
		   const result = await  $.ajax({
	  		 type:method, url:url, data:'',  dataType: 'html',
	  			   success: function(aJresp) {},
	  			   error: function(xhr, textStatus, errorThrown) {} 
	          });
		    log(JSON.parse(result)); 
		   	return JSON.parse(result); 
	   } catch(e){alert("errore:"+e); return null;}
	  }
  
   async function setIdLegame(aId) {
	   closeModal();
	   $("#selectName").html('error');
	   $("#selectId").val('');
	   $("#areaLegami").hide();
		 
	   let url=basePath+'editor/people_edit.jsp?action=who&id='+aId;
	   var res = await apiSend('GET',url);
	   if(res !=null ) {
	   		$("#selectName").html(res.p_surname+" "+res.p_name);
	   		$("#selectId").val(aId);
	   		$("#areaLegami").show();
	   }
	}
   
 </script>