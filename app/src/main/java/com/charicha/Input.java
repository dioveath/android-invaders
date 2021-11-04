package com.charicha;

import java.util.List;

/**
 * Created by Charicha on 12/28/2017.
 */

public interface Input {

    public static class TouchEvent {
        public static final int TOUCH_DOWN = 0;
        public static final int TOUCH_UP = 1;
        public static final int TOUCH_DRAGGED = 2;

        public int type;
        public int pointerId;
        public int x, y;
    }

    public List<TouchEvent> getTouchEvents();

    public float getAccelX();

    public float getAccelY();

    public float getAccelZ();

}
