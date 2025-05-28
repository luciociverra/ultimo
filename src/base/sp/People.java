package base.sp;

import org.apache.poi.ss.excelant.ExcelAntPrecision;

import base.db.DbConn;
import base.db.RsTable;
import base.utility.BaseObj;
import base.utility.Sys;
/*
 public.people (
	id serial4 NOT NULL,
	surname varchar(50) DEFAULT ''::character varying NOT NULL,
	"name" varchar(50) DEFAULT ''::character varying NOT NULL,
	nickname varchar(50) DEFAULT ''::character varying NOT NULL,
	sex varchar(1) NULL,
	born_date int4 NULL,
	born_location varchar(50) NULL,
	born_city varchar(50) NULL,
	born_nation varchar(20) DEFAULT ''::character varying NOT NULL,
	born_dateflag int4 DEFAULT 0 NOT NULL,
	death_date int4 NULL,
	death_location varchar(50) NULL,
	death_city varchar(50) NULL,
	death_nation varchar(20) NULL,
	death_dateflag int4 DEFAULT 0 NOT NULL,
	buried_at varchar DEFAULT ''::character varying NOT NULL,
	writer_id int4 DEFAULT 0 NOT NULL,
	writer_date timestamp DEFAULT now() NOT NULL,
	avatar varchar(255) NULL,
	title varchar(255) NULL,
	peoplevisibility int4 NULL,
	peopleaccess int4 NULL,
	route varchar(255) NULL,
	CONSTRAINT persons_pkey PRIMARY KEY (id)
 */
public class People extends BaseSpObj {
    public String test="TEST";
    public RsTable rsTies=new RsTable();
	public People () {
	}
	public People (Sys aSys) {
	  sys=aSys;
	}

	public People (Sys sys, int id) {
		getRs(sys,"SELECT * FROM people WHERE id="+id);
	}
	public void loadTies() {
		rsTies=getRsTable(sys,"SELECT A.id,A.tie_type,B.p_name,B.p_surname,'' AS tipoLegame FROM ties A INNER JOIN people B "+
                "ON A.from_id=B.id WHERE A.to_id="+this.getId());
		while(rsTies.next()) {
			String s="??";
			if(rsTies.getFieldTrim("tie_type").equals("FP")) s="PADRE";
			if(rsTies.getFieldTrim("tie_type").equals("FM")) s="MADRE";
			rsTies.getCurrentRow().put("tipoLegame", s);
        }
		rsTies.first();
	}
    public String get(String aFname) {
    	return super.get(aFname);
    }
    
    public String get(String aFname,String aDefault) {
    	String rString=super.get(aFname);
    	if(isBlank(rString)) return aDefault;
    	return rString;
    }
   
    public String getId() {
    	try {
    	  return super.get("id");
    	} catch(Exception e) {
    	  return "";
    	}
    }
    //D FULL Y SOLO ANNO
    public String getTipoData(String aTipo) {
    	try {
    	  String s = super.get(aTipo+"_dateflag");
    	  return s.substring(0,1);
    	} catch(Exception e) {
    	  return "D";
    	}
    }
    // S data probabile quindi setto il check a true N certa cecco a false
    public String isProvvisorio(String aTipo) {
    	try {
    	  String s = super.get(aTipo+"_dateflag");
    	  if(s.substring(1,2).equals("S")) return "true";
    	  return "false";
    	} catch(Exception e) {
    	  return "false";
    	}
    }
    
    public String getDataEstesa(String aTipo) {
    	try {
    	  String s = super.get(aTipo+"_date");
    	  if(s.length()==8) {
    		  return s.substring(0,4)+"-"+s.substring(4,6)+"-"+s.substring(6,8);
    	  }
    	  return s.substring(0,4);
    	} catch(Exception e) {
    	  return "";
    	}
    }
    
}
