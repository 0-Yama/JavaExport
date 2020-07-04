package fr.export.db;
import java.sql.*;
import java.util.HashMap;

public class Database {

    Connection db;
    ResultSet result;
    private HashMap<String, Object[]> table= new HashMap<String, Object[]>(){{
        put("avion"     , new Object[] {"id","model","groupe","type","place","disponibilité", "stationement",
                "loueur","image","durée location","réservoir","consomation"});

        put("facture"   , new Object[] {"n° Facture","nom membre","prénom membre","n° Tel","n° Service","Prix","Adresse facturation"});

        put("hangar"    , new Object[] {"Id","Etat","Dimension","Capacité"});

        put("membre"    , new Object[] {"Id","Mail","Mot de passe","Prénom","Nom","Age","Ville","Code Postal","Client","Abonées"});

        put("historique", new Object[] {"Id","Id Service","Nom Service","Prix Service","Horraire début","Horraire fin","Type","Date"});

        put("personel"  , new Object[] {"Id","Mail","Nom","Prénom","Age","Adresse","Code Postal","Ville","Mot de Passe","Poste"});

        put("service"   , new Object[] {"Id","Nom","Prix","Horraire début","Horraire fin","Type","Date","Accompagnant"});

        put("ulm"       , new Object[] {"Id","Disponible","Loueur","Stationement","durée Location","Reservoir","Consomation"});
    }};

    public Database(String host, String dbName, String port, String user, String password){
        try {
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
        return returnValue;
    }
    public void next() throws Exception{
        try{
            result.next();
        }catch (Exception e){throw new Exception();}
    }
}
