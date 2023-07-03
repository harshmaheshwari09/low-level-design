package com.switchcase.database.model;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Database {

    public static void storeData(Object data, final String FILE_PATH) throws IOException {
        Path path = Paths.get(FILE_PATH);
        Path parentDir = path.getParent();

        if (parentDir != null && !Files.exists(parentDir)) {
            Files.createDirectories(parentDir);
        }

        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_PATH));
        oos.writeObject(data);
        oos.close();
    }

    public static Object loadData(final String FILE_PATH) throws IOException, ClassNotFoundException {
        ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_PATH));
        Object data = ois.readObject();
        ois.close();
        return data;
    }
}
