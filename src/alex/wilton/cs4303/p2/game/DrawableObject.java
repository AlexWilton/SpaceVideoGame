package alex.wilton.cs4303.p2.game;


import processing.core.PVector;

import java.util.List;

public abstract class DrawableObject {

    public abstract List<PVector> getCircumferencePts();
    public abstract boolean containsPt(PVector pt);
    public abstract PVector getCenterPosition();

}
