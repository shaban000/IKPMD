package shaban.jama.eindopdracht.model;

/**
 * Created by sangam on 30/12/2016.
 */

public class Subdoel {
    private int subdoel_id;
    private String subdoel_naam;
    private String week;
    private String voldaan;
    private String fk_id_leerdoel;

    public Subdoel(){

    }
    public int getSubdoel_id() {
        return subdoel_id;
    }

    public void setSubdoel_id(int subdoel_id) {
        this.subdoel_id = subdoel_id;
    }
    public String getFk_id_leerdoel() {
        return fk_id_leerdoel;
    }

    public void setFk_id_leerdoel(String fk_id_leerdoel) {
        this.fk_id_leerdoel = fk_id_leerdoel;
    }

    public String getVoldaan() {
        return voldaan;
    }

    public void setVoldaan(String voldaan) {
        this.voldaan = voldaan;
    }

    public String getWeek() {
        return week;
    }

    public void setWeek(String week) {
        this.week = week;
    }

    public String getSubdoel_naam() {
        return subdoel_naam;
    }

    public void setSubdoel_naam(String subdoel_naam) {
        this.subdoel_naam = subdoel_naam;
    }
}
