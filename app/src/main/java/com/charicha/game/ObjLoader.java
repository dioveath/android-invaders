package com.charicha.game;

import com.charicha.impl.GLGame;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Charicha on 1/23/2018.
 */

public class ObjLoader {

    public static Vertices3 loadObj(GLGame game, String fileName){
        InputStream iStream = null;
        try {
            iStream = game.getFileIO().openAsset(fileName);
            List<String> objInfos = loadAllLines(iStream);

            float[] allVertices = new float[objInfos.size() * 3];
            float[] allUVs = new float[objInfos.size() * 2];
            float[] allNormals = new float[objInfos.size() * 3];

            int vertexIndex = 0;
            int normalIndex = 0;
            int uvIndex = 0;
            int numVertices = 0;
            int numNormals = 0;
            int numUVs = 0;

            int[] faceVertexIndices = new int[objInfos.size() * 3];
            int[] faceUVIndices = new int[objInfos.size() * 3];
            int[] faceNormalIndices = new int[objInfos.size() * 3];
            int faceIndex = 0;
            int numFaces = 0;

            for(int i = 0; i < objInfos.size(); i++){

                String line = objInfos.get(i);

                if(line.startsWith("v ")){
                    String[] tokens = line.split("[ ]+");

                    allVertices[vertexIndex++] = Float.parseFloat(tokens[1]);
                    allVertices[vertexIndex++] = Float.parseFloat(tokens[2]);
                    allVertices[vertexIndex++] = Float.parseFloat(tokens[3]);
                    numVertices++;
                    continue;
                }

                if(line.startsWith("vn ")){
                    String[] tokens = line.split("[ ]+");

                    allNormals[normalIndex++] = Float.parseFloat(tokens[1]);
                    allNormals[normalIndex++] = Float.parseFloat(tokens[2]);
                    allNormals[normalIndex++] = Float.parseFloat(tokens[3]);
                    numNormals++;
                    continue;
                }

                if(line.startsWith("vt ")){
                    String[] tokens = line.split("[ ]+");

                    allUVs[uvIndex++] = Float.parseFloat(tokens[1]);
                    allUVs[uvIndex++] = Float.parseFloat(tokens[2]);
                    numUVs++;
                    continue;
                }

                if(line.startsWith("f ")){
                    String[] tokens = line.split("[ ]+");

                    String[] parts = tokens[1].split("/");
                    faceVertexIndices[faceIndex] = getIndex(parts[0], numVertices);
                    if(parts.length > 2 && !parts[2].isEmpty())
                        faceNormalIndices[faceIndex] = getIndex(parts[2], numNormals);
                    if(parts.length > 1 && !parts[1].isEmpty())
                        faceUVIndices[faceIndex] = getIndex(parts[1], numUVs);
                    faceIndex++;

                    parts = tokens[2].split("/");
                    faceVertexIndices[faceIndex] = getIndex(parts[0], numVertices);
                    if(parts.length > 2 && !parts[2].isEmpty())
                        faceNormalIndices[faceIndex] = getIndex(parts[2], numNormals);
                    if(parts.length > 1 && !parts[1].isEmpty())
                        faceUVIndices[faceIndex] = getIndex(parts[1], numUVs);
                    faceIndex++;

                    parts = tokens[3].split("/");
                    faceVertexIndices[faceIndex] = getIndex(parts[0], numVertices);
                    if(parts.length > 2 && !parts[2].isEmpty())
                        faceNormalIndices[faceIndex] = getIndex(parts[2], numNormals);
                    if(parts.length > 1 && !parts[1].isEmpty())
                        faceUVIndices[faceIndex] = getIndex(parts[1], numUVs);
                    faceIndex++;

                    numFaces++;
                    continue;
                }
            }


            float[] verticesInfo = new float[(numFaces * 3) * (3 + (numUVs > 0 ? 2: 0) + (numNormals > 0 ? 3 : 0))];

            for(int i = 0, vi = 0; i < numFaces * 3; i++){
                int tempIndex = faceVertexIndices[i] * 3;

                verticesInfo[vi++] = allVertices[tempIndex];
                verticesInfo[vi++] = allVertices[tempIndex + 1];
                verticesInfo[vi++] = allVertices[tempIndex + 2];

                if(numUVs > 0){
                    tempIndex = faceUVIndices[i] * 2;
                    verticesInfo[vi++] = allUVs[tempIndex];
                    verticesInfo[vi++] = 1 - allUVs[tempIndex + 1];
                }

                if(numNormals > 0){
                    tempIndex = faceNormalIndices[i] * 3;
                    verticesInfo[vi++] = allNormals[tempIndex];
                    verticesInfo[vi++] = allNormals[tempIndex + 1];
                    verticesInfo[vi++] = allNormals[tempIndex + 2];
                }

            }

            Vertices3 model = new Vertices3(game.getGLGraphics().getGL(), numFaces * 3, 0, numUVs > 0, false, numNormals > 0);
            model.setVertices(verticesInfo, 0, verticesInfo.length);

            return  model;

        } catch(IOException ioe){
            ioe.printStackTrace();
        } finally {
            if (iStream != null) {
                try {
                    iStream.close();
                } catch(IOException ioe){
                    System.err.println("Couldn't close the file!");
                }
            }
        }
        return null;
    }

    static List<String> loadAllLines(InputStream iStream) throws IOException{
        ArrayList<String> allLines = new ArrayList<>();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(iStream));
        String line;

        while((line = bufferedReader.readLine()) != null){
            allLines.add(line);
        }

        return allLines;
    }

    static int getIndex(String index, int size){
        int idx = Integer.parseInt(index);
        if(idx < 0)
            return idx + size;
        else
            return idx - 1;
    }

}
