package alex.wilton.cs4303.p2.game;


import alex.wilton.cs4303.p2.game.entity.staticImage.Planet;
import alex.wilton.cs4303.p2.game.screen.FightScreen;
import processing.core.PVector;

import java.awt.*;

public class Missile {
    private PVector position;
    private PVector velocity;
    private boolean exploded = false;

    public Missile(PVector position, PVector velocity) {
        this.position = position;
        this.velocity = velocity;
    }

    public PVector getPosition() {
        return position;
    }

    public void setPosition(PVector position) {
        this.position = position;
    }

    public void update() {
        if(exploded) return;
        position.add(velocity);

        if(FightScreen.getDisallowedArea().containsPt(position)) exploded = true;
        if(Planet.getPlanet(0).containsPt(position)) exploded = true;

    }

    public void draw() {
        if(exploded) return;

        App app = App.app;
        app.stroke(Color.white.getRGB());
        app.strokeWeight(2);
        app.fill(Color.white.getRGB());
        app.ellipse(position.x, position.y, 4, 4);
        app.line((position.x-velocity.x*2), (position.y-velocity.y*2), position.x, position.y);
    }

    public boolean isExploded() {
        return exploded;
    }

    public void setExploded(boolean exploded) {
        this.exploded = exploded;
    }
}
