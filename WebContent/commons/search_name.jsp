<%@ include file="../base.jsp" %>
<style>
 .my-title {
    color: rgba(23,31,35,.87);     font-weight: bold;    
    font-size: 0.9rem; max-height:30px;padding-top:5px; 
 }
 .my-sub-title {
    color: rgba(23,31,35,.87);     font-size: 0.9rem;max-height:20px; 
 }
 
 .list-group { height:400px;overflow-y:auto} 
 .rowSearch {  width:100%;height:40px; border: 1px solid gray; border-radius:5px;cursor:pointer;margin-bottom:5px }
 .rowSearch:hover { box-shadow: 0 5px 10px rgba(0, 0, 0, 0.3);  }
 
</style>
 
<div class="container mt-2">
  <div class="input-group mb-3">
    <input type="text" class="form-control" id="searchInput" placeholder="Search..." aria-label="Search">
  </div>
  <div id="searchResults" class="list-group"></div>
</div>    
 
<script>
var selectAction="self.parent.<%=req(request,"selectAction")%>";
$(document).ready(function() {
	
    const searchInput = document.getElementById('searchInput');
    const resultsContainer = document.getElementById('searchResults');
    let debounceTimeout = null;

    searchInput.addEventListener('input', function () {
        const query = this.value.trim();

        // Clear any existing timeout
        if (debounceTimeout) {
            clearTimeout(debounceTimeout);
        }

        // Only proceed if input has at least 2 characters
        if (query.length < 2) {
            resultsContainer.innerHTML = ''; // Clear previous results
            return;
        }

        // Debounce the request by 300ms
        debounceTimeout = setTimeout(() => {
            performSearch(query);
        }, 300);
    });
 });
    
    
 function performSearch(queryTxt) {
        $.ajax({
            type: 'POST',
            url:  '<%=basePath%>commons/search_name_action.jsp?pgm=selectPeople&like='+queryTxt,
            dataType: 'json',
    		success: function(data) {
    			displayResults(data)
           },
    	   error: function(xhr, textStatus, errorThrown) {
    		   alert("error")
    	   }
    	  });
     }
        
   
    function displayResults(results) {
    	const resultsContainer = document.getElementById('searchResults');
        if (!results.length) {
            resultsContainer.innerHTML = '<div class="list-group-item">No results found.</div>';
            return;
        }

        resultsContainer.innerHTML = results.map(repo => {
            //return `<a href="#" class="list-group-item list-group-item-action">${result.p_name}</a>`;
            return  "<div class='divTableCell rowSearch' onClick='selectPerson("+repo.id+")'><spam class='my-title' >"+repo.p_surname+"  "+repo.p_name+"</spam>&nbsp;"+
            "<span class='my-sub-title'>"+repo.loc +" "+repo.data_nascita+"</span></div>";
        }).join('');
    }

    function selectPerson(aId){
    	eval(selectAction + '(' + aId + ')');
    }

function jAlert(aVal){
	alert(JSON.stringify(aVal));
};
</script> 