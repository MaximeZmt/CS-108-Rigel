package ch.epfl.rigel.astronomy;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class MyAsterismLoaderTest {

    private static final String HYG_CATALOGUE_NAME = "/hygdata_v3.csv";
    private static final String ASTERISM = "/asterisms.txt";

    @Test
    void asterismTest() throws IOException {
        try{
            InputStream hygStream = getClass().getResourceAsStream(HYG_CATALOGUE_NAME);
            InputStream astStream = getClass().getResourceAsStream(ASTERISM);
            StarCatalogue catalogue = new StarCatalogue.Builder().loadFrom(hygStream, HygDatabaseLoader.INSTANCE).loadFrom(astStream,AsterismLoader.INSTANCE).build();
            Set<Asterism> ast = catalogue.asterisms();
            Iterator i = ast.iterator();
            while(i.hasNext()){
                Asterism a = (Asterism) i.next();
                List<Star> st = a.stars();
                Iterator ii = st.iterator();
                Set<Integer> sss = new HashSet<>(List.of(24436,27366,26727,27989,28614,29426,28716));
                //System.out.println(sss.toString());

                while(ii.hasNext()){
                   Star star = (Star) ii.next();
                   int id = star.hipparcosId();
                    if(sss.contains(id)){
                        sss.remove(id);
                    }
                }
                if (sss.isEmpty()){
                    System.out.println("Has been empty");
                    System.out.println(st.toString());
                }



            }

        }catch(Exception e){
            System.out.println(e);
        }

    }
}