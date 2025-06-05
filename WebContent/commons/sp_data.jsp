 <div class="row mb-3 mt-3">
        <div class="col-6 col-sm-12 d-flex">
            <label class="form-label"><%=spdata_label%></label>
        </div>
        <div class="col-6 col-sm-12 d-flex">
              <div class="btn-group w-100" role="group">
                    <input type="radio" class="btn-check" name="dataType" id="<%=sp_suffix%>TypeD" value="D" autocomplete="off">
                    <label class="btn btn-outline-primary" for="<%=sp_suffix%>TypeD">
                        Data completa
                    </label>
                    
                    <input type="radio" class="btn-check" name="dataType" id="<%=sp_suffix%>TypeY" value="Y" autocomplete="off">
                    <label class="btn btn-outline-warning" for="<%=sp_suffix%>TypeY">
                       Solo anno
                    </label>
                </div>
        </div>
    </div>
    
    <div class="row mb-3" id="<%=sp_suffix%>DateContainer">
        <div class="col-md-6">
            <input type="date" class="form-control" id="<%=sp_suffix%>Date_D">
        </div>
        <div class="col-md-6">
            <div class="form-check">
                <input class="form-check-input" type="checkbox" id="<%=sp_suffix%>Approximate_D" >
                <label class="form-check-label" for="editDeathDateApproximate">Data probabile</label>
            </div>
        </div>
    </div>
    
    <div class="row mb-3 d-none" id="<%=sp_suffix%>YearContainer">
        <div class="col-md-6">
            <input type="number" class="form-control" id="<%=sp_suffix%>Date_Y" placeholder="Anno (AAAA)">
        </div>
        <div class="col-md-6">
            <div class="form-check">
                <input class="form-check-input" type="checkbox" id="<%=sp_suffix%>Approximate_Y">
                <label class="form-check-label" for="editDeathYearApproximate">Anno probabile</label>
            </div>
        </div>
    </div>
    
<script>
 $(document).ready(function(){
 	 $('input[name="dataType"]').on('change', function () {
	      settaDate('<%=sp_suffix%>', $(this).val());
	    });
 });
// Toggle between full date and year-only input
 var currType="D";   
 function settaDate(aSuffix,aType) {
	   log("................settaDate......"+aSuffix +" "+aType);
	   const fullDateContainer = document.getElementById(aSuffix+'DateContainer');
       const yearContainer     = document.getElementById(aSuffix+'YearContainer');
       currType=aType;
        if (aType === 'D') {
            fullDateContainer.classList.remove('d-none');
            yearContainer.classList.add('d-none');
        } else {
            fullDateContainer.classList.add('d-none');
            yearContainer.classList.remove('d-none');
        }
}
 
 // born_dateflag 2 bytes il primo D o Y il secondo S=non sicura N=sicura
 //
    function getDateValues(sp_suffix) {
	    //alert("campi:"+sp_suffix+"Date_"+currType +"  "+sp_suffix+"Approximate_"+currType);
    	var approx=$("#"+sp_suffix+"Approximate_"+currType).prop("checked") ? "S" :"N";
    	var valore=$("#"+sp_suffix+"Date_"+currType).val();  // formato 1987-02-28
        var ret="&"+sp_suffix+"Date="+valore+"@"+currType+"@"+approx;
    	log(ret);
    	return ret;
    }
</script>