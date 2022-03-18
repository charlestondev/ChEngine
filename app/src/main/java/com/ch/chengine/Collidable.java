package com.ch.chengine;


/**
 * Created by charleston on 02/03/15.
 */
public interface Collidable {
    public Rect getCollisionRect();
    public Circle getCollisionCircle();
    public void collided();
}
