package shaban.jama.eindopdracht.model;

import java.util.ArrayList;

/**
 * Created by sangam on 13/01/2017.
 */

public class Leerdoel {
    private String leerdoel_naam;
    private int leerdoel_id;

    private ArrayList<Subdoel> subdoels;

    public String getLeerdoel_naam() {
        return leerdoel_naam;
    }

    public void setLeerdoel_naam(String leerdoel_naam) {
        this.leerdoel_naam = leerdoel_naam;
    }

    public int getLeerdoel_id() {
        return leerdoel_id;
    }

    public void setLeerdoel_id(int leerdoel_id) {
        this.leerdoel_id = leerdoel_id;
    }
}
