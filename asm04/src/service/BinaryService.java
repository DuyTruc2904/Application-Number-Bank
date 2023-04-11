package service;

import models.Customer;
import org.testng.IObjectFactory;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class BinaryService {

    public static  <T> List<T> readFile(String fileName) {
        List<T> objects = new ArrayList<>();

        try(ObjectInputStream file = new ObjectInputStream(new BufferedInputStream(new FileInputStream(fileName)))) {
            boolean eof = false;
            while (!eof) {
                try {
                    T object = (T) file.readObject();
                    objects.add(object);
                } catch (EOFException e) {
                    eof = true;
                }
            }
        } catch (EOFException e) {
            return new ArrayList<>();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return objects;

    }

    public static  <T> void writeFile(String fileName, List<T> objects) {
        if(objects.size() == 0) return;
        try (ObjectOutputStream file = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(fileName)))) {
            for(T object: objects) {
                file.writeObject(object);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
