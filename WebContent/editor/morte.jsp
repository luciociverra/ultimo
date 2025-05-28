<form class="form-card" id="myFormM" method="POST" action="">
<div class="row">
 <div class="col-md-8">
  <div class="card-body">
			<div class="form_container">
		     <input type="hidden" name="page" value="morte"/>
		     
		     
			<div class="row justify-content-between text-left">
				<!-- terza riga -->
				<div class="form-group col-12 flex-column d-flex">
				    	<% 
			   spdata_label="Data di morte"; 
               sp_suffix="death"; 
            
            %>
			   <%@ include file="../commons/sp_data.jsp" %>
			</div>	    
				    			</div>
		     <div class="row justify-content-between text-left">
				<!-- prima riga -->
				<div class="form-group col-sm-12 col-lg-6 flex-column d-flex">
					<label class="form-control-label">Località</span></label> <input
						type="text" id="death_location" name="death_location" value="<%=p.get("death_location")%>">
				</div>

				<div class="form-group col-sm-12 col-lg-6 flex-column d-flex d-inline-block">
					<label class="form-control-label">Città - Nazione</label>
					<div class="d-inline-block">
					 <input type="text" id="death_city" name="death_city" value="<%=p.get("death_city")%>">
					 </div>
				</div>
			</div>
			    
		    <div class="row mb-3">
		        <div class="col-12">
		            <label for="editDeathNote" class="form-label">Note (luogo di sepoltura)</label>
		            <textarea class="form-control" id="death_notes" name="death_notes" rows="3"><%=p.get("death_notes")%></textarea>
		        </div>
		    </div>
			<!-- 4 riga fine -->
			<div class="row justify-content-start">
				<div class="form-group">
					<button type="button" class="btActS" id="btMorteSave" onclick="submitFormM()">Registra questi dati</button>
					<span id="msgOut" class="w-100 mt-5"></span>	
				</div>
			</div>
		</div>
		<!-- form container -->
	</div>
</div>
<div class="col-md-4 col-sm-12"></div>
 </form>
 </div>
<script>
   $(document).ready(function() {
	   // DATA MORTE
	   var deathTipoData="<%=p.getTipoData("death")%>"; // D - Y (DEFAULT D)
	   var deathCheckName ="#deathType"+deathTipoData; // tipo
	   var deathDataName  ="#deathDate_"+deathTipoData; // valore
	   var deathProvvName ="#deathApproximate_"+deathTipoData; // flag  certa
	 
	   settaDate("death",deathTipoData); // attiva visualizzazioni
	   var dataDeath="<%=p.getDataEstesa("death")%>"; // valore registrato o data estesa o solo anno (DEFAULT blank)
	  
	  // alert(dataDeath+"  "+deathCheckName+" "+deathDataName);
	 
	   $(deathCheckName).prop("checked",true); // attivo tipo data
	   $(deathProvvName).prop("checked",<%=p.isProvvisorio("death")%>); //attivo flag certa
	   $(deathDataName).val(dataDeath); // imposto valore 
    });
    

   function submitFormM(){
	   	   // test alert($('#sexF').is(":checked"));
	       var serializedForm = $("#myFormM").serialize()+getDateValues("death");
		//   alert(serializedForm);
		   var url  = basePath+"editor/people_edit.jsp";
		   $.ajax({
	              type:'POST', url:url, data:serializedForm,  dataType: 'html',
						success: function(aJresp) {
							jLog(aJresp);
							var jresp=JSON.parse(aJresp); 
							if(jresp.rc==1)  {
								$("#msgOut").html("OK");
							} 
							if(jresp.rc==0 && jresp.msg != "" )
							{
								$("#msgOut").html(jresp.msg);
							}	
				       },
					   error: function(xhr, textStatus, errorThrown) {
					   } 
	             });

    }
	function reqDataMsg(aField)
	{
	  $(aField).addClass("rounded fieldsObb");
	}
	 
</script>