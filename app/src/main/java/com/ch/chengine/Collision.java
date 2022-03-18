package com.ch.chengine;

import android.util.Log;

/**
 * Created by charleston on 04/03/15.
 */
public class Collision {
    public static boolean testRectCollision(Collidable obj1, Collidable obj2){
        Rect rectObj1 = obj1.getCollisionRect();
        Rect rectObj2 = obj2.getCollisionRect();
        if(rectObj1.getRight()<rectObj2.getLeft())
            return false;
        if(rectObj1.getLeft()>rectObj2.getRight())
            return false;
        if(rectObj1.getTop()<rectObj2.getBottom())
            return false;
        if(rectObj1.getBottom()>rectObj2.getTop())
            return false;
        return true;
    }
    public static boolean testRectPointCollision(float px, float py, Collidable obj){
        Rect rectObj = obj.getCollisionRect();
        if(rectObj.getRight()<px)
            return false;
        if(rectObj.getLeft()>px)
            return false;
        if(rectObj.getTop()<py)
            return false;
        if(rectObj.getBottom()>py)
            return false;
        return true;
    }
    public static boolean testCircleCollision(Collidable obj1, Collidable obj2){
        Circle circleObj1 = obj1.getCollisionCircle();
        Circle circleObj2 = obj2.getCollisionCircle();
        if(circleObj1!=null && circleObj2!=null){
            float a = circleObj2.getX()-circleObj1.getX();
            float b = circleObj2.getY()-circleObj1.getY();
            float c = circleObj2.radius+circleObj1.radius;
            if(a*a+b*b<c*c){
                Log.d("teste", "circle collision");
                return true;
            }
            else{
                Log.d("teste", "circle not collision");
                return false;
            }
        }
        return true;
    }
    public static boolean testRectCircleCollision(Collidable withCircle, Collidable withRect){
        Circle circle = withCircle.getCollisionCircle();
        Rect rect = withRect.getCollisionRect();
        if(circle.getY()>rect.getTop()){
            if(circle.getX()<rect.getLeft()){
                return testCirclePointCollision(circle, rect.getLeft(),rect.getTop());
            }
            else if(circle.getX()>rect.getRight()){
                return testCirclePointCollision(circle, rect.getRight(),rect.getTop());
            }
        }
        else if(circle.getY()<rect.getBottom()){
            if(circle.getY()<rect.getLeft()){
                return testCirclePointCollision(circle, rect.getLeft(),rect.getBottom());
            }
            else if(circle.getX()>rect.getRight()){
                return testCirclePointCollision(circle, rect.getRight(),rect.getBottom());
            }
        }
        if(circle.getX()+circle.radius<rect.getLeft())
            return false;
        else if(circle.getX()-circle.radius>rect.getRight())
            return false;
        else if(circle.getY()+circle.radius<rect.getBottom())
            return false;
        else if(circle.getY()-circle.radius>rect.getTop())
            return false;
        return true;
    }
    public static boolean testCirclePointCollision(Circle circle, float x, float y){
        float a = circle.getX()-x;
        float b = circle.getY()-y;
        float c = circle.radius;
        if(a*a+b*b<c*c)
            return true;
        else
            return false;
    }
}
