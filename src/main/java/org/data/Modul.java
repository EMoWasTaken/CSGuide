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

    public void rate(int cpPref) {
        List<Integer> cpCounts = new ArrayList<>();
        for(Modul m : moduleList)
            cpCounts.add(m.cp);

        float maxCp = Collections.max(cpCounts);
        float minCp = Collections.min(cpCounts);

        float relCp = (float) cp / (maxCp + minCp);

        float cpRating;
        if(cpPref == 1)
            cpRating = relCp;
        else if(cpPref == 0)
            cpRating = 1 - 2 * Math.abs(relCp - 0.5f);
        else
            cpRating = 1 - relCp;

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
