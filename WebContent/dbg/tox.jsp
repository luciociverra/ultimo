<%@ include file="base_dbg.jsp" %>
 
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"> 
<title>Query</title>
<!--  jquery-3.5.0.js -->
<style>
textarea, select, input {font-family: Verdana,Tahoma; font-size: 9pt;}
</style>
</head>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/2.2.4/jquery.min.js"></script>
<script src="../js/jquery.table2excel.js"></script>
<body>
  <button class="exportToExcel">Export to XLS</button>

 
  <textarea rows="6" name="txtQuery" cols="150"> </textarea></p> 
  <br>
<table class='table2excel'>
<tr><td>CUSTORDID</td><td>CUSTCODE</td><td>CUSTNAME</td><td>CUSTEMAIL</td><td>INSERTDATE<br></td><td>INSERTDATETIME</td><td>LASTMODDATETIM</td><td>FINALCUSSURNAME<br>varchar<br>40,40,0</td><td>FINALCUSNAME<br>varchar<br>40,40,0</td><td>FINALCUSSEX<br>varchar<br>1,1,0</td><td>FINALCUSPHONE<br>varchar<br>30,30,0</td><td>FINALCUSEMAIL<br>varchar<br>60,60,0</td><td>FINALCUSAGE<br>varchar<br>3,3,0</td><td>FINALCUSWEIGHT<br>varchar<br>7,7,0</td><td>RIDINGTYPE<br>varchar<br>1,1,0</td><td>RIDINGNOTE<br>varchar<br>5000,5000,0</td><td>CONFIGREF<br>varchar<br>30,30,0</td><td>MODELSIZE<br>varchar<br>10,10,0</td><td>MEASUREA<br>varchar<br>20,20,0</td><td>MEASUREB<br>varchar<br>20,20,0</td><td>MEASUREC<br>varchar<br>20,20,0</td><td>MEASURED<br>varchar<br>20,20,0</td><td>MEASUREE<br>varchar<br>20,20,0</td><td>MEASUREF<br>varchar<br>20,20,0</td><td>MEASUREG<br>varchar<br>20,20,0</td><td>MEASUREH<br>varchar<br>20,20,0</td><td>MEASUREI<br>varchar<br>20,20,0</td><td>MEASUREJ<br>varchar<br>20,20,0</td><td>MEASUREK<br>varchar<br>20,20,0</td><td>MEASUREL<br>varchar<br>20,20,0</td><td>MEASUREM<br>varchar<br>20,20,0</td><td>MEASUREN<br>varchar<br>20,20,0</td><td>MEASUREO<br>varchar<br>20,20,0</td><td>MEASUREP<br>varchar<br>20,20,0</td><td>MEASUREQ<br>varchar<br>20,20,0</td><td>MEASURER<br>varchar<br>20,20,0</td><td>MEASURES<br>varchar<br>20,20,0</td><td>MEASURET<br>varchar<br>20,20,0</td><td>MEASUREU<br>varchar<br>20,20,0</td><td>MEASUREV<br>varchar<br>20,20,0</td><td>MEASUREW<br>varchar<br>20,20,0</td><td>MEASUREX<br>varchar<br>20,20,0</td><td>MEASUREZ<br>varchar<br>20,20,0</td><td>ARTCODE<br>varchar<br>20,20,0</td><td>ARTDES<br>varchar<br>50,50,0</td><td>BOOT<br>varchar<br>50,50,0</td><td>GLOVES<br>varchar<br>50,50,0</td><td>PROTECTIONCHEST<br>varchar<br>50,50,0</td><td>PROTECTIONBACK<br>varchar<br>50,50,0</td><td>REQDEILVDATE<br>numeric<br>10,8,0</td><td>INFOCLOSING<br>varchar<br>50,50,0</td><td>B2BSALESID<br>char<br>20,20,0</td><td>AXSALESID<br>char<br>20,20,0</td><td>ERRORDATA<br>char<br>1,1,0</td><td>STATUS<br>char<br>1,1,0</td><td>STATUSINFO<br>char<br>2,2,0</td><td>UNDERWEAR<br>varchar<br>50,50,0</td><td>MOTORCYCLE<br>varchar<br>50,50,0</td><td>DATAAREAID<br>varchar<br>4,4,0</td><td>CURRENCY<br>varchar<br>8,8,0</td><td>PRICEGROUP<br>varchar<br>15,15,0</td><td>AXCUSTCODE<br>varchar<br>10,10,0</td><td>CUSTTAYLOR<br>varchar<br>40,40,0</td><td>BOOTPROTECTION<br>varchar<br>1,1,0</td><td>DIFSIZE_JACKET<br>varchar<br>3,3,0</td><td>DIFSIZE_PANT<br>varchar<br>3,3,0</td><td>DIFSIZE_WAIST<br>varchar<br>3,3,0</td></tr><tr><td>2020001290</td><td>C022373</td><td>HOSTETTLER AG</td><td>ezequiel.fernandes@hostettler.com</td><td>20201002</td><td>2020-09-26 10:36:31.027</td><td>2020-10-02 11:10:19.093</td><td>Schönauer</td><td>Yvonne</td><td>F</td><td>076 369 85 48</td><td>yvonne.schoenauer@bluewin.ch</td><td>37</td><td>56</td><td></td><td></td><td></td><td>d40</td><td>47;C;6,00</td><td>100;O;-5,00</td><td>148,5;O;6,00</td><td>78;O;-0,50</td><td>86;O;0,00</td><td>34;;</td><td>35;;</td><td>71;O;1,00</td><td>95,5;O;-0,50</td><td>58;O;4,00</td><td>34,5;O;-2,00</td><td>33,5;O;-1,00</td><td>22;O;0,00</td><td>37,5;O;-0,50</td><td>43;;</td><td>55,5;O;-0,50</td><td>46,5;;</td><td>26;;</td><td>32;O;-1,00</td><td>27,5;O;0,00</td><td>23;;</td><td>23,5;O;-1,50</td><td>15,5;O;-2,50</td><td>89;O;-1,00</td><td>171;O;4,00</td><td>202513467F</td><td>IMAtrA LADY 1PC SUIT F</td><td>Out;Alpinestar SVX R</td><td>Out;-</td><td>No;</td><td>Yes;Dainese Wave D1 Air</td><td>0</td><td>HOSTETTLER-HOSTETTLER at: 02/10/2020 11.10.19</td><td>201002111801176     </td><td>SO200700749         </td><td> </td><td>C</td><td>  </td><td>SIXS 2pc</td><td>Kawasaki 636 Jg. 2013 ZX 6r</td><td>dach</td><td>CHF</td><td>P2</td><td>C022373</td><td>Ezequiel Fernandes</td><td>N</td><td></td><td></td><td></td></tr>

</table>  
</body>
<script language='javascript'>
var preQry = new Array();

$(function() {
	$(".exportToExcel").click(function(e){
		 var table = $(this).prev('.table2excel');
		 alert(table.length);
		if(table && table.length){
			var preserveColors = (table.hasClass('table2excel_with_colors') ? true : false);
			$(table).table2excel({
				exclude: ".noExl",
				name: "Excel Document Name",
				filename: "Export" + new Date().toISOString().replace(/[\-\:\.]/g, "") + ".xls",
				fileext: ".xlsx",
				exclude_img: true,
				exclude_links: true,
				exclude_inputs: true,
				preserveColors: preserveColors
			});
		}
	});
	
});
 </script>
</html>