package base.db;
 

import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

public class RsText
{
    private static String ls = System.getProperty("line.separator");
    private static String str_msg="";
    private static String solo_numeri = " ONKEYPRESS = 'if ((event.keyCode < 48) ||(event.keyCode > 57)) event.returnValue = false'";
    private static ResultSetMetaData rsmd;
    public static String getRs(ResultSet rs)
    {
        try
        {
            ResultSetMetaData rsmd = rs.getMetaData ();
            int columnCount = rsmd.getColumnCount ();
            str_msg="";
            addMsg ("<TR>");
            for (int i = 1; i <= columnCount; ++i) {
                addMsg ("<TD>" +  rsmd.getColumnLabel(i) + "<br>" + rsmd.getColumnDisplaySize (i) +
                        "," + rsmd.getPrecision(i) + "," + rsmd.getScale(i) + "<br>" +
                        rsmd.getColumnTypeName(i) + "</td>");
            }
            addMsg ("</TR>");

            // Iterate throught the rows in the result set and output
            // the columns for each row.
            while (rs.next ()) {
                addMsg ("<TR>");
                for (int i = 1; i <= columnCount; ++i) {
                    String value = rs.getString (i);
                    if (rs.wasNull ())
                        value = "null";
                    if (value.trim().equals(""))
                        value = "&nbsp;";
                    addMsg ("<td>" + value + "</td>");
                }
                addMsg ("</TR>");

            }
            return str_msg;
          }
           catch (SQLException e) {
            addMsg   ("ERROR: " + e.getMessage());
            return str_msg;
        }

    }

private static void addMsg(String aStep)
	{
		str_msg += aStep;
        }

public static String getInputRs(ResultSet rs)
    {
        try
        {
            rsmd = rs.getMetaData ();
            int columnCount = rsmd.getColumnCount ();
            str_msg="";
            rs.next ();
            for (int i = 1; i <= columnCount; ++i)
            {
              String aName =rsmd.getColumnLabel(i);
              String aType =rsmd.getColumnTypeName(i);
              int visSize=0;
              int aSize =rsmd.getColumnDisplaySize(i);
              if (aSize > 80) visSize=80;
              else
                  visSize=aSize;
                addMsg ("<tr><td vAlign='top'><b>" +  aName + "</b><br>" + aSize +
                       "&nbsp;" +aType+ "</td><td>");

            String value = rs.getString(i).trim();
            if (rs.wasNull ()) value = "NULL";
                addMsg(campoInput(value,aName,aType,visSize));

             addMsg ("</td></tr>");
             }
            return str_msg;
          }
           catch (SQLException e) {
            addMsg   ("ERROR: " + e.getMessage());
            return "";
        }
    }

public static String getCreate(ResultSet rs)
{
    try
    {
        rsmd = rs.getMetaData ();
        int columnCount = rsmd.getColumnCount ();
        
        str_msg="CREATE TABLE  ......(" + ls ;
        for (int i = 1; i <= columnCount; ++i)
        {
          String aName =rsmd.getColumnLabel(i);
          String aType =rsmd.getColumnTypeName(i);
          int aSize =rsmd.getColumnDisplaySize(i);
          int aPrecision =rsmd.getPrecision(i);
          int aScale=rsmd.getScale(i);
       
          if (aType.equalsIgnoreCase("DECIMAL"))
          {    if(aScale==0)
                 str_msg+=aName + " INTEGER("+aPrecision + "),";
              else
                 str_msg+=aName + " DECIMAL("+aPrecision+","+aScale+"),";
          }       
          else
             str_msg+=aName + " " + aType +"(" +aSize + "),";
        }
        str_msg+=ls+")";
        return str_msg;
      }
       catch (SQLException e) {
        addMsg   ("ERROR: " + e.getMessage());
        return "";
    }
}

public static String getInputNewRecord(DatabaseMetaData dmd,String aLib,String aTable)
    {
      try
        {
         ResultSet rs = dmd.getColumns(null,aLib,aTable,null);
         rsmd = rs.getMetaData ();
	 while(rs.next())
	   {
           String aName = rs.getString(4);
           String aType = rs.getString(6);
           int aSize = rs.getInt(7);
           addMsg ("<tr><td vAlign='top'><b>" +  aName + "</b><br>" + String.valueOf(aSize)+
                    "&nbsp;" +aType + "</td><td>");
           addMsg(campoInput("",aName,aType,aSize));
           addMsg ("</td></tr>");
           }
            return str_msg;
          }
           catch (SQLException e) {
            addMsg   ("ERROR: " + e.getMessage());
            return str_msg;
        }
    }

  private static String campoInput(String aValue,String aId,String aType,int numSize)
  {
  try
   {
    String aSize ="SIZE=" + String.valueOf(numSize);
   //---------------------------------------
    if ((aType.equalsIgnoreCase("NUMERIC")) | (aType.equalsIgnoreCase("DECIMAL")) | (aType.equalsIgnoreCase("BIGDECIMAL")))
        return "<INPUT TYPE='TEXT' VALUE='" + aValue + "'" + aSize +
                   " NAME='" + aId +  "'" + solo_numeri + ">";
   //---------------------------------------
    if ((aType.equalsIgnoreCase("CHAR"))| (aType.equalsIgnoreCase("VARCHAR")))
       {
       if (numSize < 155)
           return "<INPUT TYPE='TEXT' VALUE='"+aValue +"' "+ aSize + " NAME='" + aId + "'>";
       else
           return "<TEXTAREA ROWS=2 COLS=80 NAME="+aId+">"+ aValue + "</TEXTAREA>";
       }
       return "";
    }
   catch (Exception e) {
            addMsg   ("ERROR: " + e.getMessage());
            return "";
        }
  }
 /** dato un Resultset ed un array di valori crea la stringa di update*/
  public static String getStrUpd(ResultSet rs, String[] campi)
    {
        try
        {   String result ="";
            rsmd = rs.getMetaData ();
            for (int i = 1; i <= rsmd.getColumnCount (); ++i)
            {
                String aType,aLabel;
                aType = rsmd.getColumnTypeName(i);
                aLabel =rsmd.getColumnLabel(i);
                if ((aType.equalsIgnoreCase("NUMERIC")) | (aType.equalsIgnoreCase("DECIMAL")) | (aType.equalsIgnoreCase("BIGDECIMAL")))
                    result += aLabel + "=" + campi[i-1];
                else
                    result += aLabel + "='" + campi[i-1] + "'";
                if (i < rsmd.getColumnCount ())
                   result += ",";
             }
            return result;
          }
           catch (SQLException e) {
            addMsg("ERROR: " + e.getMessage());
            return "";
        }

    }

public static String getPrimaryKeys(DatabaseMetaData dmd,String aCata,String aSchema,String aTable)
    {
    try {
	String chiavi="";
    String appo = new String();
	ResultSet rs = dmd.getPrimaryKeys(null,null,aTable);
    ResultSetMetaData rsmd=rs.getMetaData ();
	while(rs.next())
	      // 1=TABLE_CAT 2=TABLE_SCHEM 3=TABLE_NAME 4=COLUMN_NAME 5=KEY_SEQ 6=PK_NAME
	      chiavi += rs.getString(4) + ";";

        rs.close();
	return chiavi;
	}
    catch (SQLException e) {
		return e.getMessage();
        }
	}

 public static String getColumns(DatabaseMetaData dmd,String aSchema,String aTable)
    {
    try {
	String colonne="";
	ResultSet rs = dmd.getColumns(null,aSchema,aTable,null);
        rsmd = rs.getMetaData ();
	while(rs.next())
	{
	colonne += rs.getString(4) + "_" + rs.getString(5)
				   + "_" + rs.getString(6) + ";";
	}
	rs.close();
	return colonne;
	}
    catch (SQLException e) {
		return e.getMessage();
        }
	}


  }
