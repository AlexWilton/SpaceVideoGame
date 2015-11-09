package alex.wilton.cs4303.p2.game;

import com.sun.istack.internal.NotNull;

import java.awt.*;
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

    @NotNull
    public Color getFactionColour() {
        switch (this){
            case Villt:
                return new Color(231, 76, 60);
            case Qalz:
                return new Color(52, 152, 219);
            case Doloe:
                return new Color(155, 89, 182);
            default:
                System.out.println("ERROR: Unknown Faction");
                return Color.WHITE;
        }
    }


}
