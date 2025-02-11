package com.vrozsa;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.stream.Collectors;

class FileReader {

    static String readFile(String fileName) {

        var classLoader = Thread.currentThread().getContextClassLoader();
        try (var inputStream = classLoader.getResourceAsStream(fileName);
             var reader = new BufferedReader(new InputStreamReader(inputStream))) {
            return reader.lines().collect(Collectors.joining("\n"));
        } catch (Exception e) {
            throw new RuntimeException("Error reading resource file: " + fileName, e);
        }
        // TODO: handle errors if we provide this as a feature.

        /*
        try {
            // TODO: better handle this.
            var content = Files.readString(Paths.get(getClass().getResource(filePath).toURI()));
            return Optional.of(content);
        } catch (IOException e) {
            System.out.println(e); // todo: handle
            return Optional.empty();
        } catch (URISyntaxException e) {
            System.out.println(e); // todo: handle
            return Optional.empty();
        }
         */
    }
}
