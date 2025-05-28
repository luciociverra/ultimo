package base.sp;

import base.db.DbConn;
import base.db.RsTable;
import base.utility.BaseObj;
import base.utility.Sys;

public class BaseSpObj extends BaseObj {
	protected RsTable rs;
	protected Sys sys;
	public DbConn getDb(Sys sys) {
		DbConn db = new DbConn(sys);
		db.connect("SP_DB");
		return db;
	}
	protected void getRs(Sys sys, String sql) {
		DbConn db =getDb(sys);
		rs = db.getRsTable(sql,true);
	}
	protected RsTable getRsTable(Sys sys, String sql) {
		DbConn db =getDb(sys);
		RsTable rsT = db.getRsTable(sql,true);
		return rsT;
	}
	protected String get(String aFldName) {
		try {
			return rs.getField(aFldName).trim();
		}
		catch(Exception e) {
			return "";
		}
		
	}
	
}
