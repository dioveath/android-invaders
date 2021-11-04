package com.charicha.game;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Charicha on 1/10/2018.
 */

public class SpatialHashGrid {

    List<GameObject>[] staticObjects;
    List<DynamicObject>[] dynamicObjects;

    int cellsPerRow, cellsPerColumnn;
    float cellSize;

    int cellIds[];
    List<GameObject> potentialObjects;

    public SpatialHashGrid(float worldWidth, float worldHeight, float cellSize){
        this.cellSize = cellSize;
        this.cellsPerRow = (int) Math.ceil(worldWidth/cellSize);
        this.cellsPerColumnn = (int) Math.ceil(worldHeight/cellSize);
        int numCells = this.cellsPerRow * this.cellsPerColumnn;

        staticObjects = new List[numCells];
        dynamicObjects = new List[numCells];

        for(int i = 0; i < numCells; i++){
            staticObjects[i] = new ArrayList<>(10);
            dynamicObjects[i] = new ArrayList<>(10);
        }

        cellIds = new int[4]; //This int array is working array used to get the cellIds of given object
        potentialObjects = new ArrayList<>(10); //This is also working array used to get the potential objects that can collide with the given object
    }

    public void insertStaticObject(GameObject gameObject){
        setCellIds(gameObject);
        int i = 0;
        while(i < 4 && cellIds[i] != -1){
            staticObjects[cellIds[i++]].add(gameObject);
        }
    }

    public void insertDynamicObject(DynamicObject gameObject){
        setCellIds(gameObject);
        int i = 0;
        while(i < 4 && cellIds[i] != -1){
            dynamicObjects[cellIds[i++]].add(gameObject);
        }
    }

    public void removeGameObject(GameObject gameObject){
        setCellIds(gameObject);
        int i = 0;
        while(i < 4 && cellIds[i] != -1){
            int cellId = cellIds[i++];
            staticObjects[cellId].remove(gameObject);
            dynamicObjects[cellId].remove(gameObject);
        }
    }

    public List<GameObject> getPotentialColliders(GameObject gameObject){
        setCellIds(gameObject);
        int i = 0;
        int len = 0;
        int cellId = -1;
        GameObject potentialObject;
        potentialObjects.clear();
        while(i < 4 && cellIds[i] != -1){
            cellId = cellIds[i++];
            len = staticObjects[cellId].size();
            for(int j = 0; j < len; j++){
                potentialObject = staticObjects[cellId].get(j);
                if(!potentialObjects.contains(potentialObject))
                    potentialObjects.add(potentialObject);
            }
            len = dynamicObjects[cellId].size();
            for(int j = 0; j < len; j++){
                potentialObject = dynamicObjects[cellId].get(j);
                if(!potentialObjects.contains(potentialObject))
                    potentialObjects.add(potentialObject);
            }
        }
        return potentialObjects;
    }

    public void setCellIds(GameObject gameObject){
        int x1 = (int) Math.floor(gameObject.bounds.lowerLeft.x / cellSize);

        //REMINDER::BUG::MISTAKE: I didn't put the brackets here, and everything was kinda working correct but
        // the x2 getting smaller than 0 wasn't working, Still wondering why?
        int x2 = (int) Math.floor((gameObject.bounds.lowerLeft.x + gameObject.bounds.width) / cellSize);

        int y1 = (int) Math.floor(gameObject.bounds.lowerLeft.y / cellSize);
        int y2 = (int) Math.floor((gameObject.bounds.lowerLeft.y + gameObject.bounds.height) / cellSize);

        if((x1 > cellsPerRow || x2 > cellsPerRow) || (y1 > cellsPerColumnn || y2 > cellsPerColumnn))
            System.out.println("Can't be");


        int i = 0;
        if(x1 == x2 && y1 == y2){ //if all the points(vertex :/?) from the bounded rectangle lies in same cell Ids
            if(x1 >= 0 && x1 < cellsPerRow && y1 >= 0 && y1 < cellsPerColumnn)
                cellIds[i++] = x1 + y1 * cellsPerRow;
        } else if(x1 == x2){ //if bounded rectangle lies between two separate y cells
            if(x1 >= 0 && x1 < cellsPerRow) {
                if(y1 >= 0 && y1 < cellsPerColumnn)
                    cellIds[i++] = x1 + y1 * cellsPerRow;
                if(y2 >= 0 && y2 < cellsPerColumnn)
                    cellIds[i++] = x1 + y2 * cellsPerRow;
            }
        } else if(y1 == y2){ //if bounded rectangle lies between two separate x cells
            if(y1 >= 0 && y1 < cellsPerColumnn){ /*MISTAKE::I used <= instead of < and leads to ArrayIndexOutOfBounds Exception in getPotentialColliders()*/
                if(x1 >= 0 && x1 < cellsPerRow)
                    cellIds[i++] = x1 + y1 * cellsPerRow;
                if(x2 >= 0 && x2 < cellsPerRow)
                    cellIds[i++] = x2 + y1 * cellsPerRow;
            }
        } else { //if bounded rectangle points(vertex) all lies in different cells
            if(x1 >= 0 && x1 < cellsPerRow){
                if(y1 >= 0 && y1 < cellsPerColumnn)
                    cellIds[i++] = x1 + y1 * cellsPerRow;
                if(y2 >= 0 && y2 < cellsPerColumnn)
                    cellIds[i++] = x1 + y2 * cellsPerRow;
            }
            if(x2 >= 0 && x2 < cellsPerRow){ /*MISTAKE:: i used x1 in here to calculate instead of x2 */
                if(y1 >= 0 && y1 < cellsPerColumnn)
                    cellIds[i++] = x2 + y1 * cellsPerRow;
                if(y2 >= 0 && y2 < cellsPerColumnn)
                    cellIds[i++] = x2 + y2 * cellsPerRow;
            }
        }
        while(i < 4){
            cellIds[i++] = -1;
        }
    }

    public void clearDynamicObjects(){
        for(int i = 0; i < dynamicObjects.length; i++)
            dynamicObjects[i].clear();
    }

}
