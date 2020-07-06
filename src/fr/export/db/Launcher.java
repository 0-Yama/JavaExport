package fr.export.db;

import java.util.HashMap;

public class Launcher {
    static final String[]                  TABLE_NAMES = {"avion","facture","hangar","membre","historique","personel","service","ulm"};
    static final HashMap<String, Object[]> TABLE       = new HashMap<String, Object[]>(){{
        put("avion"     , new Object[] {"id","model","groupe","type","place","disponibilité", "stationement",
                "loueur","image","disponibilité","durée location","réservoir","consomation"});

        put("facture"   , new Object[] {"n° Facture","nom membre","prénom membre","n° Tel","n° Service","Prix","Adresse facturation"});

        put("hangar"    , new Object[] {"Id","Etat","Dimension","Capacité"});

        put("membre"    , new Object[] {"Id","Mail","Mot de passe","Prénom","Nom","Age","Ville","Code Postal","Client","Abonées"});

        put("historique", new Object[] {"Id","Id Service","Nom Service","Prix Service","Horraire début","Horraire fin","Type","Date"});

        put("personel"  , new Object[] {"Id","Mail","Nom","Prénom","Age","Adresse","Code Postal","Ville","Mot de Passe","Poste"});

        put("service"   , new Object[] {"Id","Nom","Prix","Horraire début","Horraire fin","Type","Date","Accompagnant"});

        put("ulm"       , new Object[] {"Id","Disponible","Loueur","Stationement","durée Location","Reservoir","Consomation"});
    }};
    public static void main(String[] args) {
        Database db = new Database("localhost", "aen","3308", "dbaccess", "<Dbaccess@3>",TABLE);
        Exporter exporter = new Exporter(db,TABLE);
        for (String table : TABLE_NAMES) {
            exporter.addSheet(table);
        }
        exporter.createDocument();
    }
}
