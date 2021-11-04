package com.charicha.androidinvaders;

import com.charicha.FileIO;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

/**
 * Created by Charicha on 1/24/2018.
 */

public class Settings {

    public static boolean soundEnabled = true;
    public static boolean touchEnabled = false;
    static final String fileName = "and.inv";

    public static void loadSettings(FileIO fileIO){
        InputStream inputStream = null;
        try {
            inputStream = fileIO.readFile(fileName);
            BufferedReader bReader = new BufferedReader(new InputStreamReader(inputStream));

            soundEnabled = Boolean.parseBoolean(bReader.readLine());
            touchEnabled = Boolean.parseBoolean(bReader.readLine());

        } catch(IOException ioe){
            ioe.printStackTrace();
        }
    }

    public static void saveSettings(FileIO fileIO){
        OutputStream outputStream = null;
        try {
            outputStream = fileIO.writeFile(fileName);
            BufferedWriter bWriter = new BufferedWriter(new OutputStreamWriter(outputStream));

            bWriter.write(Boolean.toString(soundEnabled));
            bWriter.write(Boolean.toString(touchEnabled));

        } catch(IOException ioe){
            ioe.printStackTrace();
        }
    }

}
