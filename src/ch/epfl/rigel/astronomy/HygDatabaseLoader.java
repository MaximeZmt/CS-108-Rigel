package ch.epfl.rigel.astronomy;

import javafx.util.Builder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;


public enum HygDatabaseLoader implements StarCatalogue.Loader { // public et immuable
    INSTANCE();


    @Override
    public void load(InputStream inputStream, Builder builder) throws IOException {
        InputStreamReader isr = new InputStreamReader(inputStream, StandardCharsets.US_ASCII);
        BufferedReader br = new BufferedReader(isr); //TODO Check if this is the correct syntax
        String s = br.readLine();
        System.out.println(s);
    }
}
