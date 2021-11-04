package com.charicha.impl;

import android.content.Context;
import android.content.res.AssetManager;
import android.os.Environment;

import com.charicha.FileIO;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by Charicha on 12/29/2017.
 */

public class AndroidFileIO implements FileIO{

    Context mContext;
    AssetManager mAssetManager;
    String mExternalRootDir;

    public AndroidFileIO(Context context){
        this.mContext = context;
        this.mAssetManager = context.getAssets();
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            mExternalRootDir = mContext.getExternalFilesDir(null).getAbsolutePath();
        } else {
            mExternalRootDir = mContext.getFilesDir().getAbsolutePath();
        }
    }

    @Override
    public InputStream readFile(String fileName) throws IOException {
        return new FileInputStream(mExternalRootDir + fileName);
    }

    @Override
    public InputStream openAsset(String fileName) throws IOException {
        return mAssetManager.open(fileName);
    }

    @Override
    public OutputStream writeFile(String fileName) throws IOException {
        return new FileOutputStream(fileName);
    }
}
