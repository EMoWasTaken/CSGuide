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

        /*System.out.println("Geben Sie für jedes der folgenden Themengebiete an, wie interessiert Sie daran im Allgemeinen sind (Wert zwischen 0 und 1).");

        for(Tag tag : Tag.tags) {
            System.out.print(tag + ": ");
            String popString = scanner.next();

            while(true) {
                try {
                    float pop = Float.parseFloat(popString);
                    if (pop > 0 && pop <= 1) {
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
                    if (pop > 0 && pop <= 1) {
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

        for(Modul m : Modul.moduleList)
            m.rate();

        Modul.moduleList.sort((item1, item2) -> Float.compare(item1.rating, item2.rating));
        Collections.reverse(Modul.moduleList);

        /*System.out.println("Basierend auf Ihren Interessen sollten Sie folgende Module belegen:");
        List<Modul> recommendation = Result.greedyPick(reverseModules);
        for(Modul m : recommendation)
            System.out.println(m + " (" + m.cp + " CP) " + m.rating);*/

        Result.linearSum(Modul.moduleList);
    }
}