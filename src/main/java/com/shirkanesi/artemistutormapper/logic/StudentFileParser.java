package com.shirkanesi.artemistutormapper.logic;

import java.util.Arrays;
import java.util.List;

public final class StudentFileParser {

    private StudentFileParser() {
        throw new IllegalAccessError("Utility-classes may not be instantiated!");
    }

    /**
     * Parses the given content of a file containing the login-names of all students.
     * @param fileContent the content of the student-file
     * @return a list containing all login-names from the file (in lower-case)
     */
    public static List<String> parseStudentsFile(final String fileContent) {
        String[] studentNames = fileContent.toLowerCase()
                .replaceAll("[\\s,;]+", " ")
                .split("\s+");

        return Arrays.asList(studentNames);
    }

}
