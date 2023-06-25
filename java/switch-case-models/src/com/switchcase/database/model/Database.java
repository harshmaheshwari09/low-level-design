package com.switchcase.database.model;

import java.io.*;

public class Database {

    public static void storeData(Object data, final String FILE_PATH) throws IOException {
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
