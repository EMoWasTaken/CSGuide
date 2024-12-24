package org.data;

import java.util.*;

public class Professor {
    public static List<Professor> professors = new ArrayList<>();

    public String name;
    public float popularity;
    public List<Modul> modules = new ArrayList<>();

    public Professor(String name) {
        professors.add(this);
        this.name = name;
    }

    public static Professor findByName(String name) {
        for(Professor p : professors)
            if(p.name.equals(name))
                return p;
        return null;
    }

    @Override
    public String toString() {
        return "Prof. " + name;
    }
}
