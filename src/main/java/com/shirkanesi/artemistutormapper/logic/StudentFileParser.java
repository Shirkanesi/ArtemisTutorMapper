package com.shirkanesi.artemistutormapper.logic;

import java.util.Arrays;
import java.util.List;

public class StudentFileParser {

    public static List<String> parseStudentsFile(final String fileContent) {
        String[] studentNames = fileContent.toLowerCase()
                .replaceAll("[\\s,;]+", " ")
                .split("\s+");

        return Arrays.asList(studentNames);
    }

}
