package ch.epfl.rigel.astronomy;

import javafx.util.Builder;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import static java.nio.charset.StandardCharsets.US_ASCII;

public enum HygDatabaseLoader implements StarCatalogue.Loader { // public et immuable
    INSTANCE();


    @Override
    public void load(InputStream inputStream, Builder builder) throws IOException {
        InputStreamReader isr = new InputStreamReader("",US_ASCII);
    }
}
