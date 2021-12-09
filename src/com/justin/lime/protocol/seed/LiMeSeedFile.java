package com.justin.lime.protocol.seed;

import java.io.File;

/**
 * @author Justin Lee
 */
public class LiMeSeedFile extends LiMeSeed {
    private final File file;

    public LiMeSeedFile(String sender, String receiver, File file) {
        super(FILE, sender, receiver, null, null);
        this.file = file;
    }

    public File getFile() {
        return file;
    }
}
