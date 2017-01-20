package shaban.jama.eindopdracht.model;

/**
 * Created by sangam on 30/12/2016.
 */

public class Subdoel {
    private int id;
    private String naam;
    private String week;
    private String voldaan;
    private String fk_id_leerdoel;

    public Subdoel(){

    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getNaam() {
        return naam;
    }

    public void setNaam(String naam) {
        this.naam = naam;
    }
}
