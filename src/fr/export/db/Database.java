package fr.export.db;
import java.sql.*;
import java.util.HashMap;

public class Database {

    Connection db;
    ResultSet result;
    private HashMap<String, Object[]> table;


    public Database(String host, String dbName, String port, String user, String password, HashMap<String, Object[]> table){
        try {
            this.table = table;
            // The newInstance() call is a work around for some
            // broken Java implementations
            Class.forName("com.mysql.cj.jdbc.Driver").newInstance();

            db = DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "/" + dbName + "?serverTimezone=UTC&user=" + user + "&password=" + password);
            System.out.println("CONNECTED !!!!!!!!");
        } catch (Exception e) {
            System.out.println("SQLException: " + e.getMessage());
        }
    }
    public boolean query(String query){
        try{
            Statement SQLquery = db.createStatement();
            result             = SQLquery.executeQuery(query);
            result.first();
            return true;
        }catch(Exception e) {
            return false;
        }
    }
    public Object[] fetch(String name) {
        Object[] returnValue;
        try {
            int colomnCount = table.get(name).length;
            returnValue = new Object[colomnCount];
            for (int i = 0; i < returnValue.length; i++) {
                try {
                    returnValue[i] = result.getObject(i + 1);
                }catch(Exception e){
                    System.out.println(e);
                }

            }
        } catch (Exception e) {
            System.out.println("ERROR !!!!!!");
            returnValue = new Object[5];
        }
        System.out.println(returnValue.toString());
        System.out.println(returnValue.length);
        try{
            result.next();
        }catch(Exception e){}
        return returnValue;
    }
    public int getRows(String table){
        try{
            Statement SQLquery = db.createStatement();
            result             = SQLquery.executeQuery("SELECt count(id) FROM " + table);
            result.first();
            return result.getInt(1);
        }catch(Exception e){
            return -1;
        }
    }

}
