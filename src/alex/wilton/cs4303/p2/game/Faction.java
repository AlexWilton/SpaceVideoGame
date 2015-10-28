package alex.wilton.cs4303.p2.game;

import java.util.Iterator;

public enum Faction {
    Villt, Qalz, Doloe;


    public static Iterator<Faction> getFactionIterator(){
        return new Iterator<Faction>() {
            private int currentIndex = 0;
            @Override
            public boolean hasNext() {
                return true;
            }

            @Override
            public Faction next() {
                if(! (currentIndex < Faction.values().length)) currentIndex = 0;
                return Faction.values()[currentIndex++];
            }
        };
    }


} //Dol'eo is the proper name (requires ')
