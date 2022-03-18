package com.ch.chengine;

import com.ch.opengl.OpenGlDrawable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by charleston on 28/03/15.
 */
public class DrawableGroup {
    public int imgAtlas;
    private List<GroupChangedListener> addListeners = new ArrayList<GroupChangedListener>();
    public List<OpenGlDrawable> drawables;

    public DrawableGroup(int imgAtlas){
        drawables = new ArrayList<OpenGlDrawable>();
        this.imgAtlas = imgAtlas;
    }
    public void add(OpenGlDrawable drawable){
        drawables.add(drawable);
        for(GroupChangedListener listener: addListeners){
            listener.spriteAdded();
        }
    }
    public void remove(OpenGlDrawable drawable){
        int position = drawables.indexOf(drawable);
        drawables.remove(position);
        for(GroupChangedListener listener: addListeners){
            listener.spriteRemoved();
        }
    }
    public void addListener(GroupChangedListener addListener){
        this.addListeners.add(addListener);
    }
    public interface GroupChangedListener{
        void spriteAdded();
        void spriteRemoved();
    }
}
