package alex.wilton.cs4303.p2.game;

import alex.wilton.cs4303.p2.util.JSONconvertable;
import processing.data.JSONArray;
import processing.data.JSONObject;

import java.util.Iterator;

public class Galaxy implements JSONconvertable{
    private static final int NUMBER_OF_SYSTEMS = 23;

    private GalaxySystem[] systems;

    public Galaxy(GalaxySystem[] systems) {
        this.systems = systems;
    }

    public GalaxySystem selectRandomSystem() {
        int randomSystemNum = (int) (systems.length * Math.random());
        return systems[randomSystemNum];
    }

    public static Galaxy createRandomGalaxy() {
        GalaxySystem[] galaxySystems = new GalaxySystem[NUMBER_OF_SYSTEMS];

        Iterator<Faction> factionIterator = Faction.getFactionIterator();
        for(int systemIndex=0; systemIndex< galaxySystems.length; systemIndex++){
            int id = systemIndex;
            Faction faction = factionIterator.next();
            galaxySystems[systemIndex] = new GalaxySystem(id, faction, getPlanetName(id));
        }


        return new Galaxy(galaxySystems);
    }

    /**
     * Method Maps a unique planet id, to its name
     * @param id Planet ID
     * @return Planet Name
     */
    public static String getPlanetName(int id){
        String[] names = {  "Fepraulara","Fathouria","Pebleshan","Watrorix","Oynus","Sunia","Stufalia","Grabalea","Fronoe","Crore","Muchoazuno","Yebliulara","Badreon","Ocrurn","Yoaliv","Mouliv","Dreceruta","Prabogantu","Clilia","Shurn","Eflemia","Yecronerth","Vegrolla","Moprora","Leutis","Suyama","Brufocury","Brabunus","Thurn","Blars"};
        return names[id % names.length];
    }


    public JSONObject asJSONObject() {
        JSONObject galaxy = new JSONObject();
        JSONArray systemArray = new JSONArray();
        for(GalaxySystem system : systems) systemArray.append(system.asJSONObject());
        galaxy.setJSONArray("systems", systemArray);
        return galaxy;
    }

    public static Galaxy parseJson(String galaxy) {
        return createRandomGalaxy();
    }
}
