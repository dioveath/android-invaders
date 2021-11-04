package com.charicha;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by Charicha on 12/29/2017.
 */

public interface FileIO {

    public InputStream readFile(String fileName) throws IOException;

    public InputStream openAsset(String fileName) throws IOException;

    public OutputStream writeFile(String fileName) throws IOException;

}
