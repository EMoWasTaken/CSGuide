package org.rest;

import io.javalin.*;
import io.javalin.http.staticfiles.Location;
import io.javalin.rendering.template.*;
import org.admin.Result;
import org.data.Modul;
import org.data.Professor;
import org.data.Tag;

import java.util.*;

public class RESTHandler {
    public RESTHandler() {
        startJavalin();
    }

    private void startJavalin() {
        Javalin app = Javalin.create(config -> {
            config.fileRenderer(new JavalinFreemarker());
            config.staticFiles.add("/public");
        }).start(7777);

        app.get("/", ctx -> {
            List<String> tagNames = new ArrayList<>();
            for(Tag tag : Tag.tags)
                tagNames.add(tag.name);

            List<String> profNames = new ArrayList<>();
            for(Professor prof : Professor.professors)
                profNames.add(prof.name);

            Map<String, Object> model = new HashMap<>();
            model.put("resources", "");
            model.put("tags", Tag.tags);
            model.put("profs", Professor.professors);
            model.put("tagNames", tagNames.toArray(new String[0]));
            model.put("profNames", profNames.toArray(new String[0]));
            ctx.render("/templates/website.ftl", model);
            ctx.status(200);
        });

        app.put("/setTagPop", ctx -> {
            float popularity = Float.parseFloat(ctx.queryParam("popularity"));
            String tagName = ctx.queryParam("tag");
            Tag.findByName(tagName).popularity = popularity;
            ctx.status(200);
        });

        app.put("/setProfPop", ctx -> {
            float popularity = Float.parseFloat(ctx.queryParam("popularity"));
            String profName = ctx.queryParam("prof");
            Professor.findByName(profName).popularity = popularity;
            ctx.status(200);
        });

        app.put("/greedyPick", ctx -> {
            for(Modul m : Modul.moduleList)
                m.rate();

            List<Modul> sortedModules = new ArrayList<>(Modul.moduleList);
            sortedModules.sort((item1, item2) -> Float.compare(item1.rating, item2.rating));
            Collections.reverse(sortedModules);

            List<Modul> recommendation = Result.greedyPick(sortedModules);

            StringBuilder makeJson = new StringBuilder("{"); // Create a JSON object that saves the delegate IDs as keys and the names as values.

            boolean firstMatch = true;
            for (Modul m : recommendation) {
                if (!firstMatch)
                    makeJson.append(", ");
                makeJson.append("\"").append(m.abbreviation).append("\": {").append("\"name\": \"").append(m.name).append("\", \"cp\": \"").append(m.cp).append("\"}");
                firstMatch = false;
            }
            makeJson.append("}");

            ctx.contentType("application/json");
            ctx.result(makeJson.toString());

            ctx.status(200);
        });

        app.put("/linearSum", ctx -> {
            for(Modul m : Modul.moduleList)
                m.rate();

            List<Modul> sortedModules = new ArrayList<>(Modul.moduleList);
            sortedModules.sort((item1, item2) -> Float.compare(item1.rating, item2.rating));
            Collections.reverse(sortedModules);

            List<Modul> recommendation = Result.linearSum(sortedModules);

            StringBuilder makeJson = new StringBuilder("{"); // Create a JSON object that saves the delegate IDs as keys and the names as values.

            boolean firstMatch = true;
            for (Modul m : recommendation) {
                if (!firstMatch)
                    makeJson.append(", ");
                makeJson.append("\"").append(m.abbreviation).append("\": {").append("\"name\": \"").append(m.name).append("\", \"cp\": \"").append(m.cp).append("\"}");
                firstMatch = false;
            }
            makeJson.append("}");

            ctx.contentType("application/json");
            ctx.result(makeJson.toString());

            ctx.status(200);
        });
    }
}
