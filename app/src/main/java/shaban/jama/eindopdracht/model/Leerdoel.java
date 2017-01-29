package shaban.jama.eindopdracht.Model;

import java.util.ArrayList;

/**
 * Created by Shaban on 05-01-17.
 */

public class Leerdoel {
    private int id;
    private String naam;
    private int percentage;

    public Leerdoel(int id, String naam) {
        this.id = id;
        this.naam = naam;
    }

    public int getid() {
        return id;
    }

    public String getNaam() {
        return naam;
    }

    public int getPercentage() {
        return percentage;
    }

    public void setPercentage(int percentage) {
        this.percentage = percentage;
    }
}
