package alex.wilton.cs4303.p2.game;

import alex.wilton.cs4303.p2.util.DashedLineDrawer;
import alex.wilton.cs4303.p2.util.JSONconvertable;
import processing.core.PVector;
import processing.data.JSONArray;
import processing.data.JSONObject;

import java.awt.*;
import java.util.*;

public class Galaxy implements JSONconvertable{
    App app = App.app;
    private static final int NUMBER_OF_SYSTEMS = 23;

    private GalaxySystem[] systems;
    private Set<Link> links;

    public Galaxy(GalaxySystem[] systems, Set<Link> links) {
        this.systems = systems;
        this.links = links;
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
            galaxySystems[systemIndex] = new GalaxySystem(id, faction, getSystemName(id), getSystemMapLocation(id));
        }

        Set<Link> links = getDefaultLinks();

        return new Galaxy(galaxySystems, links);
    }

    /**
     * Find all links which has the given id as an endpoint
     * @param id System Id
     * @return Links which connect to the system
     */
    public ArrayList<Link> findLinks(int id) {
        ArrayList<Link> connectingLinks = new ArrayList<>();
        for(Link link : links){
            if(link.leftId == id || link.rightId == id) connectingLinks.add(link);
        }
        return connectingLinks;
    }


    public static class Link{
        public int leftId, rightId;

        public Link(int leftId, int rightId) {
            this.leftId = leftId;
            this.rightId = rightId;
        }

        public boolean containsSystem(int id) {
            return (leftId == id || rightId == id);
        }
    }
    
    private static Set<Link> getDefaultLinks() {
        Set<Link> links = new HashSet<>();
        links.add(new Link(0, 1)); links.add(new Link(1, 2)); links.add(new Link(2, 3)); links.add(new Link(3, 4));
        links.add(new Link(4, 5)); links.add(new Link(5, 6)); links.add(new Link(6, 7)); links.add(new Link(7, 8));
        links.add(new Link(8, 9)); links.add(new Link(9, 10)); links.add(new Link(9, 11)); links.add(new Link(11, 12));
        links.add(new Link(12, 13)); links.add(new Link(12,16)); links.add(new Link(13, 14)); links.add(new Link(14, 15));
        links.add(new Link(15, 19)); links.add(new Link(15, 16)); links.add(new Link(16, 17)); links.add(new Link(17, 18));
        links.add(new Link(16, 17));  links.add(new Link(17, 20)); links.add(new Link(18,19)); links.add(new Link(20, 4));
        links.add(new Link(20, 21)); links.add(new Link(21, 22));  links.add(new Link(22, 12));
        return links;
    }

    /**
     * Method Maps a unique System id, to its name
     * @param id System ID
     * @return Planet Name
     */
    public static String getSystemName(int id){
        String[] names = {  "Fepraulara","Fathouria","Pebleshan","Watrorix","Oynus","Sunia","Stufalia","Grabalea","Fronoe","Crore","Muchoazuno","Yebliulara","Badreon","Ocrurn","Yoaliv","Mouliv","Dreceruta","Prabogantu","Clilia","Shurn","Eflemia","Yecronerth","Vegrolla","Moprora","Leutis","Suyama","Brufocury","Brabunus","Thurn","Blars"};
        return names[id % names.length];
    }

    /**
     * Method maps a unique planet id to a co-ordinate location on the galaxy map.
     * @param id System Id
     * @return Location on Map
     */
    public static PVector getSystemMapLocation(int id){
        PVector[] pts = {new PVector(885,397), new PVector(773,454), new PVector(664,469), new PVector(548, 493), new PVector(521, 407), new PVector(383,410), new PVector(292,322), new PVector(266,261), new PVector(280,200), new PVector(360,112), new PVector(234,80), new PVector(461,77), new PVector(539,163), new PVector(590,90), new PVector(655,156), new PVector(700,218), new PVector(601,255), new PVector(563,305), new PVector(639,334), new PVector(729,272), new PVector(483,325), new PVector(376,239), new PVector(418,172) };
        if(id >= pts.length){ System.out.println("There is no map location for this system!"); System.exit(1);}
        return pts[id];
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

    public GalaxySystem[] getSystems() {
        return systems;
    }

    public void drawSystemsOnMap() {
        for(GalaxySystem sys : systems){
            PVector location = sys.getMapLocation();
            app.fill(sys.getFaction().getFactionColour().getRGB());
            app.strokeWeight(0);
            app.ellipse(location.x, location.y, 15, 15);
            app.fill(Color.WHITE.getRGB());
            app.text(sys.getId(), location.x, location.y);
        }
    }

    public void drawConnectionsOnMap() {
        for(Link link : links){
            drawLink(link, 100f);
        }
    }

    /**
     * Draw Link
     * @param alpha Set Transparency of drawing for link
     */
    public void drawLink(Link link, float alpha){
        app.stroke(Color.WHITE.getRGB(), alpha);
        int startId = link.leftId;
        int endId = link.rightId;
        PVector start = systems[startId].getMapLocation(), end = systems[endId].getMapLocation();
        DashedLineDrawer.dashline(start.x, start.y, end.x, end.y, 10,10);
    }

    /**
     * Use Breadth-first search to calculate distance between two systems in galaxy
     * @param startLocation Starting System
     * @param endLocation Destination System
     * @return Minimum number of jumps to get from start to end system
     */
    public int calculateMinimumNumberOfJumps(GalaxySystem startLocation, GalaxySystem endLocation) {
        HashMap<GalaxySystem, GalaxySystem> cameFrom = new HashMap<>(); //map id to parent id
        Queue<GalaxySystem> queue = new LinkedList<>();
        queue.add(startLocation);
        cameFrom.put(startLocation, null);
        while(!queue.isEmpty()){
            GalaxySystem current = queue.poll();
            if(current == endLocation) break;

            ArrayList<GalaxySystem> adjacentSystems = findAdjacentSystems(current);
            for(GalaxySystem adjSystem : adjacentSystems){
                if(!cameFrom.containsKey(adjSystem)){
                    queue.add(adjSystem);
                    cameFrom.put(adjSystem, current);
                }

            }
        }

        //trace back path from end to start and count steps to find distance
        int distance = 0;
        GalaxySystem sys = endLocation;
        while(true){
            sys = cameFrom.get(sys);
            if(sys == null) break;
            distance++;
        }
        return distance;
    }

    private ArrayList<GalaxySystem> findAdjacentSystems(GalaxySystem system) {
        ArrayList<GalaxySystem> adjSystems = new ArrayList<>();
        ArrayList<Link> sysLinks = findLinks(system.getId());
        for(Link link : sysLinks){
            adjSystems.add(systems[(link.leftId != system.getId()) ? link.leftId : link.rightId]);
        }
        return adjSystems;
    }

}
