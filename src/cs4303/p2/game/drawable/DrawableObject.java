package cs4303.p2.game.drawable;


import processing.core.PVector;

import java.util.List;

public abstract class DrawableObject {

    public abstract List<PVector> getCircumferencePts();
    public abstract boolean containsPt(PVector pt);
    public abstract PVector getCenterPosition();
    public boolean impulseAwayFromCenter(){
        return true;
    }


}
