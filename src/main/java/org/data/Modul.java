package org.data;

import java.util.*;

public class Modul {
    public static List<Modul> moduleList = new ArrayList<>();

    public String abbreviation, name;
    public int cp;
    public List<Tag> tags = new ArrayList<>();
    public Professor[] profs;
    public Type type;

    public float rating;

    public Modul(String name, String abbreviation, int cp, Professor[] profs, Type type) {
        moduleList.add(this);
        this.name = name;
        this.abbreviation = abbreviation;
        this.cp = cp;
        this.profs = profs;
        this.type = type;
    }

    public static Modul find(String abbrev) {
        for(Modul m : moduleList)
            if(m.abbreviation.equals(abbrev))
                return m;
        return null;
    }

    public void rate() {
        float tagAvg = 0;
        for(Tag tag : tags)
            tagAvg += tag.popularity;
        tagAvg /= tags.size();

        float profsAvg = 0;
        for(Professor p : profs)
            profsAvg += p.popularity;
        profsAvg /= profs.length;

        rating = profsAvg * ((float) 1 /3) + tagAvg * ((float) 2 /3);
    }

    @Override
    public String toString() {
        return name + "(" + abbreviation + ")";
    }
}
