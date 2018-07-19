package file;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import java.util.ArrayList;
import java.util.List;

public class FileManager {

    private static String PATH_TO_FILES = "etc/"; //ruta relativa directorio de archivos

    public static List<String> getAvailableFileNames() {
        List<String> results = new ArrayList<String>();
        File directory = new File(PATH_TO_FILES);
        File[] fileNames = directory.listFiles();
        for (int i = 0; i < fileNames.length; i++) {
            if (fileNames[i].isFile()) {
                results.add(fileNames[i].getName());
            }
        }
        return results;
    }

    public static byte[] getFileContents(String name) throws FileNotFoundException, IOException {
        byte[] filebytes = null;
        String path = PATH_TO_FILES + name;
        FileInputStream fileInput = null;
        BufferedInputStream bufferedInput = null;
        try {
            File file = new File(path);
            fileInput = new FileInputStream(file);
            bufferedInput = new BufferedInputStream(fileInput);
            filebytes = new byte[(int) file.length()];
            bufferedInput.read(filebytes, 0, filebytes.length);

        } catch (FileNotFoundException e) {
            throw new FileNotFoundException();

        } catch (IOException ex) {
            throw new IOException();
        } finally {
            if (bufferedInput != null) {
                bufferedInput.close();
            }
            if (fileInput != null) {
                fileInput.close();
            }
        }
        return filebytes;
    }

}
