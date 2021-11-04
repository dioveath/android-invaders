package com.charicha.game;

import com.charicha.math.Rectangle;
import com.charicha.math.Vector2;

/**
 * Created by Charicha on 1/10/2018.
 */

public class GameObject {

    /***
     If a final variable holds a reference to an object, then the state of the object may be changed by operations on the
     object, but the variable will always refer to the same object (this property of final is called non-transitivity).
     ***/

    public final Vector2 position; //final keyword: transitivity
    public final Rectangle bounds;

    public GameObject(float x, float y, float width, float height){
        this.position = new Vector2(x, y);
        this.bounds = new Rectangle(x - width/2, y - height/2, width, height);
    }

}
