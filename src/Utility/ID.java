package Utility;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ID {
    private ResultSet rs = null;
    private String newId="";

    private String es;
    private int id;
    private Connect conn = new Connect();
    ID(String newEs) throws SQLException {
        switch(newEs) {
            case "Popsicle": {
                this.newId="PO";
                break;
            }
            case "Gelato":{
                this.newId="GL";
                break;
            }
            case "Frozen Yogurt":{
                this.newId="FY";
                break;
            }
        }

        String SQL = "Select MAX(RIGHT(ID,3)) FROM icecream WHERE LEFT(ID,2)='"+newId+"'";
        rs = conn.executedQuery(SQL);
        while(rs.next()){
            this.id = rs.getInt(1);
        }
    }
    public String getNewId(){
        return newId+""+String.format("%03d", id+1);
    }
}
