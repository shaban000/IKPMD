package shaban.jama.eindopdracht.Model;

import java.util.ArrayList;

/**
 * Created by Shaban on 05-01-17.
 */

public class Leerdoel {
    private String naam;
    private int percentage;
    private ArrayList<Subdoel> subdoels;

    public Leerdoel(String naam, int percentage) {
        this.naam = naam;
        this.percentage = percentage;
    }

    public String getNaam() {
        return naam;
    }

    public void setNaam(String naam) {
        this.naam = naam;
    }

    public int getPercentage() {
        return percentage;
    }

    public void setPercentage(int percentage) {
        this.percentage = percentage;
    }
}
