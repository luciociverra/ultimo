<%@ include file="../base.jsp" %>
<%!
// http://localhost:82/ultimo/editor/people_edit.jsp?action=who&id=4
String getDateBornDeath(String date10_4)
{
	if(date10_4.length()==10) {
	   String[] strArr=date10_4.split("-");
	   return strArr[0]+strArr[1]+strArr[2];
	}	
	return date10_4;
}
%>
<%
/*
CREATE TABLE public.people (
	id serial4 NOT NULL,
	p_surname varchar(50) DEFAULT ''::character varying NOT NULL,
	p_name varchar(50) DEFAULT ''::character varying NOT NULL,
	nickname varchar(50) DEFAULT ''::character varying NOT NULL,
	sex varchar(1) NULL,
	born_date int4 NULL,
	born_location varchar(50) NULL,
	born_city varchar(50) NULL,
	born_nation varchar(20) DEFAULT ''::character varying NOT NULL,
	born_dateflag varchar(1) DEFAULT 'N' NOT NULL,
	death_date int4 NULL,
	death_location varchar(50) NULL,
	death_city varchar(50) NULL,
	death_nation varchar(20) NULL,
	death_dateflag varchar(1) DEFAULT 'N' NOT NULL,
	
	buried_at varchar DEFAULT ''::character varying NOT NULL,
	writer_id int4 DEFAULT 0 NOT NULL,
	writer_date timestamp DEFAULT now() NOT NULL,
	avatar varchar(255) NULL,
	title varchar(255) NULL,
	peoplevisibility int4 NULL,
	peopleaccess int4 NULL,
	route varchar(255) NULL,
	people_img bytea NULL,
	CONSTRAINT persons_pkey PRIMARY KEY (id)
);
*/
out.clear();
debugRequest(sys,request);

String my_page=req(request,"page").trim();
String action=req(request,"action").trim();

People p=(People)session.getAttribute("people");
boolean peopleExist=! p.getId().equals("");
sys.debug(" peopleExist ? :"+peopleExist +"  num legami "+p.rsTies.size());
Jresponse jr=new Jresponse();
jr.rc=1;

int born_dateflag=reqInt(request,"born_dateflag");
int death_dateflag=reqInt(request,"death_dateflag");

if(my_page.equals("nascita") & peopleExist){
	String[] strArr = req(request,"bornDate").split("@");
	DbConn db = p.getDb(sys);
	db.startProc();
	db.addProc("p_name", "s",Utils.firstUpper(req(request,"name")),50);
	db.addProc("p_surname", "s",Utils.firstUpper(req(request,"surname")),50);
	db.addProc("nickname", "s",Utils.firstUpper(req(request,"nickname")),50);

	db.addProc("born_dateflag", "s",(strArr[1]+strArr[2]),2);
	db.addProc("born_date", "n", getDateBornDeath(strArr[0]),8);
	
	db.addProc("sex", "s",request,1);
	db.addProc("born_location", "s",request,50);
	db.addProc("born_city", "s",request,50);
	//db.addProc("born_nation", "s",request,50);
 	 
	int retCode = db.execProc("UPDATE", "people","id="+p.get("id"));
	if (retCode < 1) {
		sys.debugWhite("err db:" + db.str_errori);
		jr.error("error on update "+db.str_errori);
	}
}
if(my_page.equals("nascita") & ! peopleExist){
	
	String[] strArr = req(request,"bornDate").split("@");
	
	
	DbConn db = p.getDb(sys);
	db.startProc();
	db.addProc("p_name", "s",Utils.firstUpper(req(request,"name")),50);
	db.addProc("p_surname", "s",Utils.firstUpper(req(request,"surname")),50);
	db.addProc("nickname", "s",Utils.firstUpper(req(request,"nickname")),50);
	// DATA CERTA &bornDate=2222-02-11@D@N  @Y probabile
	// ANNO PROBABILE &bornDate=2000@Y@S
	//      CERTA &bornDate=2001@Y@N
	db.addProc("born_dateflag", "s",(strArr[1]+strArr[2]),2);
	
	db.addProc("born_date", "n", getDateBornDeath(strArr[0]),8);
	
	db.addProc("sex", "s",request,1);
	db.addProc("born_location", "s",request,50);
	db.addProc("born_city", "s",request,50);
	//db.addProc("born_nation", "s",request,50);
 	 
	int retCode = db.execProc("INSERT", "people","");
	if (retCode < 1) {
		sys.debugWhite("err db:" + db.str_errori);
		jr.error("error on update "+db.str_errori);
	}
}
//fine nascita
if(my_page.equals("morte") & peopleExist){
	String[] strArr = req(request,"deathDate").split("@");
	DbConn db = p.getDb(sys);
	db.startProc();

	db.addProc("death_dateflag", "s",(strArr[1]+strArr[2]),2);
	db.addProc("death_date", "n",getDateBornDeath(strArr[0]),8);
 
	db.addProc("death_location", "s",request,50);
	db.addProc("death_city", "s",request,50);
	db.addProc("death_notes", "s",request,50);
 	 
	int retCode = db.execProc("UPDATE", "people","id="+p.get("id"));
	if (retCode < 1) {
		sys.debugWhite("err db:" + db.str_errori);
		jr.error("error on update "+db.str_errori);
	}
}
if(my_page.equals("morte") & ! peopleExist){
	jr.error("Mancano ancora i dati principali di registrazione");
}
// fine morte
sys.debug(" page "+my_page.equals("legami") );
sys.debug(" ACTION "+action.equals("ins") );

if(my_page.equals("legami") && action.equals("ins")) {
	sys.debugWhite("inseriemnto legami per "+p.getId());
/*
id |from_id|tie_type|to_id|
---+-------+--------+-----+
 25|     60|FM      |   64|
*/
	DbConn db = p.getDb(sys);
    //db.firstRecord(strQuery)

	db.startProc();
	
	db.addProc("from_id", "n",req(request,"selectId"),9);
	db.addProc("tie_type", "s",req(request,"tipoLegame"),2);
	db.addProc("to_id",    "n",p.get("id"),9);
 
	int retCode = db.execProc("INSERT", "ties","");
	if (retCode < 1) {
		sys.debugWhite("err db:" + db.str_errori);
		jr.error("error on insert "+db.str_errori);
	} else {
		sys.debugWhite("Esito inserimento OK :" + retCode);
	}
}
if(my_page.equals("legami") && action.equals("dele")) {
	DbConn db = p.getDb(sys);
	int retCode = db.executeUpdate("DELETE FROM ties WHERE id="+ req(request,"idLegame"));
	if (retCode < 1) {
		sys.debugWhite("err db:" + db.str_errori);
		jr.error("error on insert "+db.str_errori);
	}
}
//http://localhost:82/ultimo/editor/people_edit.jsp?page=legami&action=read&id=1
if(my_page.equals("legami") && action.equals("read")) {
	p.loadTies();
	sys.debug("Lista legami attiva :"+p.rsTies.size());
	out.write(json(sys,response,p.rsTies.getVectorRighe()));
	return;
}

/* ritorno record
{
peoplevisibility: "",
born_city: "",
death_notes: "",
peopleaccess: "",
p_surname: "Dondini",
born_nation: "",
born_date: "19210108",
death_location: "",
p_name: "Leda",
sex: "F",
born_dateflag: "DN",
death_city: "",
death_nation: "",
writer_id: "0",
id: "4",
death_date: "",
nickname: "",
writer_date: "2025-05-05 11:17:00.629101",
death_dateflag: "N",
people_img: "",
avatar: "",
route: "",
title: "",
born_location: "San Benedetto Val di Sambro"
}
*/
if(action.equals("who")){
    RsTable rs=getRsTable(sys, "SELECT * from people WHERE id="+req(request,"id"));
    out.write(json(sys,response,rs.getCurrentRow()));
    return;
}
out.clear();
out.write(json(sys,response,jr));

%>
