package org.admin;

import com.opencsv.*;
import org.data.*;
import java.io.*;
import java.util.*;

public class LoadData {
    public static void dataMine(File dataFile, File profsFile) {
        Map<String, String[]> profMap = new HashMap<>();
        List<List<String>> records = new ArrayList<>();

        try (BufferedReader profsReader = new BufferedReader(new FileReader(profsFile))) {
            String line;

            while((line = profsReader.readLine()) != null) {
                String[] values = line.split(";");

                profMap.put(values[0], new String[]{ values[1], values[2], values[3], values.length == 5 ? values[4] : "" });
            }
        } catch(Exception e) {
            e.printStackTrace();
        }

        try (BufferedReader dataReader = new BufferedReader(new FileReader(dataFile))) {
            String line;

            while((line = dataReader.readLine()) != null) {
                String[] values = line.split(";");
                records.add(Arrays.asList(values));
            }

            for(int i = 0; i < records.size(); i++) {
                List<String> row = records.get(i);
                Type moduleType;
                switch(row.get(4)) {
                    case "V":
                        moduleType = Type.VORLESUNG;
                        break;
                    case "S":
                        moduleType = Type.SEMINAR;
                        break;
                    case "P":
                        moduleType = Type.PRAKTIKUM;
                        break;
                    default:
                        moduleType = Type.FORSCHUNG;
                        break;
                }

                String[] profStrings = row.get(3).split(":");
                Professor[] profs = new Professor[profStrings.length];
                for(int j = 0; j < profs.length; j++) {
                    profs[j] = Professor.findByName(profStrings[j]);
                    if (profs[j] == null)
                        profs[j] = new Professor(profStrings[j], profMap.get(profStrings[j])[0], profMap.get(profStrings[j])[1], profMap.get(profStrings[j])[2],profMap.get(profStrings[j])[3]);
                }

                Modul m = new Modul(row.get(5), row.get(0), Integer.parseInt(row.get(1)), profs, moduleType);

                for(Professor prof : profs)
                    prof.modules.add(m);

                String[] tagStrings = row.get(2).split(":");
                for(int j = 0; j < tagStrings.length; j++) {
                    Tag t = Tag.findByName(tagStrings[j]);
                    if (t == null)
                        t = new Tag(tagStrings[j]);
                    m.tags.add(t);
                }

                boolean firstProf = true;
                StringBuilder profNames = new StringBuilder();
                for(Professor prof : profs) {
                    profNames.append(firstProf ? "" : ", ").append(prof.toString());
                    firstProf = false;
                }

                System.out.println("Registriere " + moduleType + " " + row.get(0) + " bei " + profNames + ".");
            }
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }
}
