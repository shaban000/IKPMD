package shaban.jama.eindopdracht.database;

/**
 * Created by sangam on 09/01/2017.
 */

public class DatabaseInfo {
    public class databaseTabels{
        public static final String subdoel = "Subdoelen";
        public static final String leerdoel = "Leerdoel";
    }
    public class Columns {
        public static final String SUBDOEL_NAME = "Name";
        public static final String LEERDOEL_NAME = "Name";
        public static final String WEEK = "Week";
        public static final String VOLDAAN = "Voldoende";
        public static final String FK_ID_LEERDOEL = "id_leerdoel";

    }

}
