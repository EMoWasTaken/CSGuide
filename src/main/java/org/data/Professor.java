package org.data;

import java.util.*;

public class Professor {
    public static List<Professor> professors = new ArrayList<>();

    public String name, firstName, title, imgLink;
    public float popularity = 0.5f;
    public List<Modul> modules = new ArrayList<>();

    public Professor(String name, String firstName, String title, String imgLink) {
        professors.add(this);
        this.name = name;
        this.firstName = firstName;
        this.title = title;
        this.imgLink = imgLink;
    }

    public static Professor findByName(String name) {
        for(Professor p : professors)
            if(p.name.equals(name))
                return p;
        return null;
    }

    public String getImgLink() {
        return imgLink;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return title + " " + firstName + " " + name;
    }
}
