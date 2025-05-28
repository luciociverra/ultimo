<%@page import="base.utility.Utils"%>
<%
	boolean isAvatar = true;  // per imgstudio
	// http://localhost:82/ultimo/getavatar?id=61
%>
<style>

input:first-letter#name,input:first-letter#surname 
{ text-transform:uppercase;  }
input#born_city {
    width: 200px !important;
}
input#born_nation {
    width: 60px !important;
}
.labelSex.active {
    color: black!important;
    font-weight:bold!important;
    background-color: #ebeadd!important;
    border-color: #ebeadd!important;
    border-size: 0px;
}
.labelSex:hover {
    color: red!important;
    font-weight:bold!important;
    background-color: white!important;
    border-size: 0px;
}


</style>
<!--  lg dopo i 992 in width -->
<form class="form-card" id="myFormN">
 
 <input type="hidden" name="page" value="nascita"/>
 <div class="row my-5">

	<div class="col-lg-4 col-sm-12 text-center pt-5"> <!-- left -->
        
      <img src="getavatar?id=<%=currId%>&time=<%=Utils.getTime()%>" id="myAvatar" style="height:220px;width:220px;border-radius:15px">
      <div class="text-center">
      <% if(! isBlank(currId)) { %>
       <button type="button" class="btn btn-dark mt-5" style="width:250px" onclick="avatar();">
           Modifica immagine
        </button>        
      <% } else { %>
        <p class="mt-4">Creare prima i dati base</p> 
      <% } %>
        
	</div>
      
	</div>
	<!--  <span class="float-right"><ml:ml>* Dato richiesto</ml:ml></span> -->
	<div class="col-lg-8"> <!-- right -->

		<div class="card-body">

			<div class="form_container">

						<div class="row justify-content-between text-left">
						<!-- prima riga -->
						<div class="form-group col-sm-6 flex-column d-flex">
							<label class="form-control-label">Nome<span
								class="text-danger"> *</span></label> <input type="text" id="name"
								name="name" value="<%=p.get("p_name")%>">
						</div>

						<div class="form-group col-sm-6 flex-column d-flex">
							<label class="form-control-label">Cognome<span
								class="text-danger"> *</span></label> <input type="text" id="surname"
								name="surname" value="<%=p.get("p_surname")%>">
						</div>
					</div>
					<!-- prima riga fine -->

					<div class="row justify-content-between text-left">
						<!-- seconda riga -->
						<div class="form-group col-sm-6 flex-column d-flex">
							<label class="form-control-label">Detto ...<span
								class="text-danger"> *</span></label> <input type="text" id="nickname"
								name="nickname" value="<%=p.get("nickname")%>">
						</div>

						<div class="form-group col-sm-6 flex-column d-flex">
							<label class="form-control-label">Sesso<span
								class="text-danger"> *</span></label>

					<div class="btn-group w-100" role="group">
                    <input type="radio" class="btn-check" name="sex" id="sexMale" value="M" autocomplete="off">
                    <label class="btn btn-outline-primary" for="sexMale">
                        <i class="fas fa-mars me-2"></i>Maschio
                    </label>
                    
                    <input type="radio" class="btn-check" name="sex" id="sexFemale" value="F" autocomplete="off">
                    <label class="btn btn-outline-danger" for="sexFemale">
                        <i class="fas fa-venus me-2"></i>Femmina
                    </label>
                </div>
						</div>
					</div>
			</div>
			<% 
			   String spdata_label="Data di nascita"; 
               String sp_suffix="born"; 
            
            %>
			<%@ include file="../commons/sp_data.jsp" %>
			<!-- seconda riga fine -->

		 
			<!-- terza riga fine -->
            <!-- 4 riga fine -->
			<div class="row justify-content-between text-left">
				<!-- prima riga -->
				<div class="form-group col-sm-12 col-md-6 flex-column d-flex">
					<label class="form-control-label">Località</span></label> <input
						type="text" id="born_location" name="born_location" value="<%=p.get("born_location")%>">
				</div>

				<div class="form-group col-sm-12 col-md-6 flex-column d-flex d-inline-block">
					<label class="form-control-label">Città - Nazione</label>
					<div class="d-inline-block">
					 <input type="text" id="born_city" name="born_city" value="<%=p.get("born_city")%>">
					 
					 </div>
				</div>
			</div>
			<!-- 4 riga fine -->
			<div class="row mt-4">
			<div class="form-group col-md-12">
			
			 <button type="button" class="btActS" onclick="submitFormN()">Registra questi dati</button>
			  
			<span id="msgOut" class="w-100 mt-5"></span>	
			
			</div>
			</div>
		</div>
		<!-- form container -->
	</div>
  </div>
 </form>
 
<script>
  function submitFormN() {
       
	  var dataToSend = $("#myFormN").serialize()+getDateValues("born");
      var url  = basePath+"editor/people_edit.jsp";
	  //alert(dataToSend);
      $.ajax({
        type:'POST', url:url, data:dataToSend,  dataType: 'html',
		success: function(aJresp) {
			jLog(aJresp);
			var jresp=JSON.parse(aJresp); 
			if(jresp.rc==1)  {	$("#msgOut").html("OK"); } 
			if(jresp.rc==0 && jresp.msg != "" )
			{	$("#msgOut").html(jresp.msg); }	
       },
	   error: function(xhr, textStatus, errorThrown) {
	   } 
      });
   }
	
   $(document).ready(function() {
	   // DATA NASCITA
	   var tipoData="<%=p.getTipoData("born")%>"; // D - Y (DEFAULT D)
	   var bornCheckName ="#bornType"+tipoData; // tipo
	   var bornDataName  ="#bornDate_"+tipoData; // valore
	   var bornProvvName ="#bornApproximate_"+tipoData; // flag  certa
	   
	   settaDate("born",tipoData); // attiva visualizzazioni
	   var data="<%=p.getDataEstesa("born")%>"; // valore registrato o data estesa o solo anno (DEFAULT blank)
	  
	   //alert(data+"  "+bornCheckName+" "+bornDataName);
	 
	   $(bornCheckName).prop("checked",true); // attivo tipo data
	   $(bornProvvName).prop("checked",<%=p.isProvvisorio("born")%>); //attivo flag certa
	   $(bornDataName).val(data); // imposto valore
	// SESSO
	   $("#sexMale").prop("checked",true);
	   if("<%=p.get("sex")%>"==="F") {
		   $("#sexFemale").prop("checked",true)
		   $("#sexMale").prop("checked",false);
	   }
	   //$("input[name=born_dateflag]:checked").parent().addClass('active');
       

    });
  
   function controlliNascita(){
    	$("#myFormN #msgOut").html("");
		if(isEmpty($("#name")) || isEmpty($("#surname")) )
		{   reqDataMsg("#name,#surname")
			$("#myFormN #msgOut").html("Dati mancanti");
	     	return false;	
		}
		return true;
	}
	function reqDataMsg(aField)
	{
	   $(aField).addClass("rounded fieldsObb");
	}
	 
	function reloadAvatarImage()
	{  
	  var img = document.getElementById('myAvatar');
	  var currentSrc = img.src;
	  var timestamp = new Date().getTime();   // Crea un timestamp come parametro di cache-busting
	  img.src = currentSrc + '&t=' + timestamp; // Aggiunge il timestamp all'URL
	}
	
	function avatar(){
	 //src, width, height,titolo="")
	  openModal('./editor/imstudio.jsp','400px','700px');
	}
	</script>