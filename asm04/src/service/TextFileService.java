package service;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
public class TextFileService {
    private static final String COMMA_DELIMITER = ",";

    public static List<List<String>> textFileService(String fileName) throws FileNotFoundException {
        List<String> user;
        List<List<String>> users = new ArrayList<>();
        try (Scanner scanner = new Scanner(new BufferedReader(new FileReader(fileName)))) {
            while (scanner.hasNextLine()) {
                String u = scanner.nextLine();
                user = Arrays.asList(u.split(COMMA_DELIMITER));
                users.add(user);
            }
        } catch (FileNotFoundException e) {
            throw new FileNotFoundException("File không tồn tại");
//            System.out.println("File không tồn tại");
        }
        return users;
    }
}

