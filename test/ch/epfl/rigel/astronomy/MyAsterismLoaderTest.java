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

                while(ii.hasNext()){
                   Star star = (Star) ii.next();
                   int id = star.hipparcosId();
                    if(sss.contains(id)){
                        sss.remove(id);
                    }
                }
                if (sss.isEmpty()){
                    //System.out.println(st.toString());
                    assertEquals(List.of("Rigel","Saiph","Alnitak","Betelgeuse","Mu Ori","Xi Ori", "Chi-2 Ori").toString(), st.toString());
                }
            }
        }catch(Exception e){
            //System.out.println(e);
        }
    }


    @Test
    void asterismTest2() throws IOException {
        try{
            InputStream hygStream = getClass().getResourceAsStream(HYG_CATALOGUE_NAME);
            InputStream astStream = getClass().getResourceAsStream(ASTERISM);
            StarCatalogue catalogue = new StarCatalogue.Builder().loadFrom(hygStream, HygDatabaseLoader.INSTANCE).loadFrom(astStream,AsterismLoader.INSTANCE).build();
            Set<Asterism> ast = catalogue.asterisms();
            List<Star> starList = catalogue.stars();
            Iterator i = ast.iterator();
            while(i.hasNext()){
                Asterism a = (Asterism) i.next();
                List<Star> st = a.stars();
                Iterator ii = st.iterator();
                Set<Integer> sss = new HashSet<>(List.of(54879,54872,57632,54879,49669,49583,50583,50335,48455,47908));

                while(ii.hasNext()){
                    Star star = (Star) ii.next();
                    int id = star.hipparcosId();
                    if(sss.contains(id)){
                        sss.remove(id);
                    }
                }
                if (sss.isEmpty()){
                    //System.out.println(st.toString());
                    //System.out.println(catalogue.asterismIndices(a).toString());
                    List<Integer> ltc = catalogue.asterismIndices(a);
                    List<Integer> newhipList = new ArrayList<>();
                    for(Integer num : ltc){
                        newhipList.add(starList.get(num).hipparcosId());
                    }
                    assertEquals(List.of(54879,54872,57632,54879,49669,49583,50583,50335,48455,47908).toString(),newhipList.toString());
                }
            }
        }catch(Exception e){
            //System.out.println(e);
        }
    }








}