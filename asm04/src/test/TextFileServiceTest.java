package test;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import service.TextFileService;
import java.io.FileNotFoundException;

class TextFileServiceTest {

    @Test
    void textFileService(){
        Assertions.assertThrows(FileNotFoundException.class, () -> {
            TextFileService.textFileService("");
        });

        Assertions.assertDoesNotThrow(() -> {
            TextFileService.textFileService("store/customers.txt");
        });

    }



}