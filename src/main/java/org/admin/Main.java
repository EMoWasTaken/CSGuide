package org.admin;

import org.data.*;
import org.rest.RESTHandler;

import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.*;
import java.util.function.Function;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Willkommen bei InfoGuide. Daten werden geladen.");
        LoadData.dataMine(new File(URLDecoder.decode(Main.class.getClassLoader().getResource("data.csv").getFile(), StandardCharsets.UTF_8)), new File(URLDecoder.decode(Main.class.getClassLoader().getResource("profs.csv").getFile(), StandardCharsets.UTF_8)));

        RESTHandler rest = new RESTHandler();

        System.out.println("Web-App wurde aufgesetzt. Geben Sie die URL \"localhost:7777\" in einen Browser Ihrer Wahl ein.");
    }
}