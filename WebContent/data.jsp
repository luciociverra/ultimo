
<!DOCTYPE html> 
<html> 

<head> 

	<!--CDN of Bootstrap-->
	<link rel="stylesheet"
		href= 
"https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-alpha.6/css/bootstrap.min.css"
		integrity= 
"sha384-rwoIResjU2yc3z8GV/NPeZWAv56rSmLldC3R/AZzGRnGxQQKnKkoFVhFQhNUwEyJ"
		crossorigin="anonymous"> 

	<!--CDN of google font-->
	<style> 
		@import url( 
'https://fonts.googleapis.com/css2?family=Roboto+Slab:wght@500&display=swap'); 
		
		@import url( 
'https://fonts.googleapis.com/css2?family=Roboto+Slab:wght@300;500&display=swap'); 
		
		.form-control { 
			display: inline-block; 
			vertical-align: middle; 
			float: none; 
		} 
	</style> 
</head> 

<body style="border:4px solid rgb(0, 128, 28);"> 
	<div align="center" class="container"> 
		<form> 
			<h1 style="font-family:'Roboto Slab',serif; 
				text-align:center;color:green;"> 
				GeeksForGeeks 
			</h1> 

			<br> 
			<div align="center"> 
				<div style="font-family:'Roboto Slab',serif; 
							color:rgb(255, 38, 0);text-align:center;" 
					id="name"> 
					Bootstrap Date Field 
				</div> 
				<br> 

				<input class="form-control" type="date"
					value="yyyy-mm-dd" id="d"
					style="border:2px solid rgb(0, 128, 28); 
							width:200px;height:30px;"> 
			</div> 
			<br><br><br> 

			<input class="form-control" type="number"
				value="1" id="d1" max="31" min="1" size="2"
				style="border:2px solid rgb(0, 128, 28); 
				width:60px; height:45px;"> 

			<input class="form-control" type="number"
				value="1" id="m" max="12" min="1" size="2"
				style="border: 2px solid rgb(0, 128, 28); 
				width:60px; height:45px;"> 

			<input class="form-control" type="number"
				value="2000" id="y" min="1000" max="3000"
				size="4" maxlength="4"
				style="border:2px solid rgb(0, 128, 28); 
				width:90px;height:45px;"> 
			<br> 

			<div style="text-align: center;"> 
				<button type="button" class="btn btn-primary btn-sm"
						onclick="edit()"
						style="background-color: rgb(20, 220, 20); 
							padding:5px;font-family: 'Roboto Slab', serif;"> 
					submit 
				</button> 
			</div> 
		</form> 
		<br> 
	</div> 

	<script> 
		function edit() { 
		
			// Retrieve the values of 'dd', 'mm', 'yy' 
			var dd = document.getElementById("d1").value; 
			var mm = document.getElementById("m").value; 
			var yy = document.getElementById("y").value; 
		
			// Check if the length of day and month is 
			// equal to 1 
			if (dd.length == 1) { 
				dd = "0" + dd; 
			} 
			if (mm.length == 1) { 
				mm = "0" + mm; 
			} 
		
			// Construct a date format like 
			// [yyyy - mm - dd] 
			var date = yy + "-" + mm + "-" + dd; 
		
			// Replace the date format with BootStrap 
			// date field 
			document.getElementById("d").value = date; 
		} 
	</script> 
</body> 

</html> 
