package org.admin;

import org.data.*;

import java.io.*;
import java.net.URISyntaxException;
import java.util.Collections;
import java.util.*;
import java.util.function.Function;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Willkommen bei InfoGuide. Daten werden geladen.");
        LoadData.dataMine(new File(Main.class.getClassLoader().getResource("data.csv").getFile()));

        System.out.println("Geben Sie für jedes der folgenden Themengebiete an, wie interessiert Sie daran im Allgemeinen sind (Wert zwischen 0 und 1).");

        for(Tag tag : Tag.tags) {
            System.out.print(tag + ": ");
            String popString = scanner.next();

            while(true) {
                try {
                    float pop = Float.parseFloat(popString);
                    if (pop >= 0 && pop <= 1) {
                        tag.popularity = pop;
                        break;
                    } else {
                        System.out.println("Ungültige Eingabe.");
                        popString = scanner.next();
                    }
                } catch (Exception e) {
                    System.out.println("Ungültige Eingabe.");
                    popString = scanner.next();
                }
            }
        }

        System.out.println("Geben Sie für jeden der folgenden Dozenten an, wie gut Sie mit ihm/ihr klarkommen (Wert zwischen 0 und 1).");

        for(Professor prof : Professor.professors) {
            System.out.print(prof + ": ");
            String popString = scanner.next();

            while(true) {
                try {
                    float pop = Float.parseFloat(popString);
                    if (pop >= 0 && pop <= 1) {
                        prof.popularity = pop;
                        break;
                    } else {
                        System.out.println("Ungültige Eingabe.");
                        popString = scanner.next();
                    }
                } catch (Exception e) {
                    System.out.println("Ungültige Eingabe.");
                    popString = scanner.next();
                }
            }
        }

        System.out.println("Geben Sie -1 ein, wenn Sie Module mit geringer CP-Zahl bevorzugen, 0 für Module mit mittelmäßiger CP-Zahl und 1 für Module mit hoher CP-Zahl.");
        String cpString = scanner.next();
        int cpPreference;

        while(true) {
            try {
                int pref = Integer.parseInt(cpString);
                if(pref == -1 || pref == 0 || pref == 1) {
                    cpPreference = pref;
                    break;
                } else {
                    System.out.println("Ungültige Eingabe.");
                    cpString = scanner.next();
                }
            } catch(Exception e) {
                System.out.println("Ungültige Eingabe.");
                cpString = scanner.next();
            }
        }

        for(Modul m : Modul.moduleList)
            m.rate(cpPreference);

        Modul.moduleList.sort((item1, item2) -> Float.compare(item1.rating, item2.rating));
        List<Modul> reverseModules = Modul.moduleList.reversed();

        Modul seminar = null;
        Modul practical = null;

        for(int i = 0; i < reverseModules.size(); i++) {
            if(reverseModules.get(i).type == Type.SEMINAR) {
                seminar = reverseModules.get(i);
                reverseModules.remove(i);
                break;
            }
        }

        for(int i = 0; i < reverseModules.size(); i++) {
            if(reverseModules.get(i).type == Type.PRAKTIKUM) {
                practical = reverseModules.get(i);
                reverseModules.remove(i);
                break;
            }
        }

        System.out.println("Basierend auf Ihren Interessen sollten Sie folgende Module belegen:");

        int cp = 0;
        System.out.println(seminar + " (" + seminar.cp + ")");
        cp += seminar.cp;
        System.out.println(practical + " (" + practical.cp + ")");
        cp += practical.cp;

        int seminarCount = 1;
        int practCount = 1;

        while(cp < 43) {
            boolean valid = true;
            Modul module = reverseModules.get(0);
            if(module.type == Type.SEMINAR) {
                if(seminarCount >= 3)
                    valid = false;
                seminarCount++;
            }
            else if(module.type == Type.PRAKTIKUM) {
                if(practCount >= 2)
                    valid = false;
                practCount++;
            }

            if(valid) {
                System.out.println(module + " (" + module.cp + ")");
                cp += module.cp;
            }

            reverseModules.remove(0);
        }
    }
}