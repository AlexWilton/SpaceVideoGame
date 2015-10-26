package alex.wilton.cs4303.p2.game.entity;

import alex.wilton.cs4303.p2.game.App;

/**
 * Abstract Class for an Entity
 */
public abstract class Entity{
    protected App app = App.app;

    public abstract void draw();

}
