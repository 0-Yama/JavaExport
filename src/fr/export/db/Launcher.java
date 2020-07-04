package fr.export.db;

public class Launcher {
    public static void main(String[] args) {
        Database db = new Database("localhost", "teiteille","3308", "dbaccess", "<Dbaccess@3>");
        Exporter exporter = new Exporter(db);
        exporter.addSheet("ulm");
        exporter.addSheet("personel");
        exporter.addSheet("service");
        exporter.addSheet("avion");
        exporter.addSheet("membre");
        exporter.addSheet("facture");
        exporter.addSheet("hangar");
        exporter.addSheet("historique");
        exporter.createDocument();
    }
}
