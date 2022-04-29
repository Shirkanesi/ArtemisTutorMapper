package com.shirkanesi.artemistutormapper;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shirkanesi.artemistutormapper.logic.ArtemisClient;
import com.shirkanesi.artemistutormapper.logic.AuthenticationService;
import com.shirkanesi.artemistutormapper.logic.StudentFileParser;
import com.shirkanesi.artemistutormapper.model.Submission;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import java.io.BufferedReader;
import java.io.Console;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

@Slf4j
public class ArtemisTutorMapper {

    public static String ARTEMIS_BASE_URL = "https://artemis.praktomat.cs.kit.edu";
    public static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    private List<String> studentNames;

    private final ArtemisClient artemisClient;

    public static void main(String[] args) throws IOException, ParseException {

        // TODO: cleanup of main-method. CLI-parsing right here is ugly...

        Options options = new Options();

        Option usernameOption = new Option("u", "username", true, "Artemis username");
        Option passwordOption = new Option("p", "password", true, "Artemis password");
        Option exerciseOption = new Option("e", "exercise", true, "id of exercise to lock");
        Option artemisOption = new Option("a", "artemis", true, "Artemis URL (default: " + ARTEMIS_BASE_URL + ")");
        Option fileOption = new Option("f", "file", true, "Path to student-file");
        Option helpOption = new Option("h", "help", false, "Print this help");

        options.addOption(usernameOption);
        options.addOption(passwordOption);
        options.addOption(exerciseOption);
        options.addOption(artemisOption);
        options.addOption(fileOption);
        options.addOption(helpOption);

        CommandLine cmd = new DefaultParser().parse(options, args);

        if (cmd.hasOption(helpOption)) {
            new HelpFormatter().printHelp("ArtemisTutorMapper", options);
            return;
        }

        String username = cmd.getOptionValue(usernameOption);
        String password;
        if (cmd.hasOption(passwordOption)) {
            password = cmd.getOptionValue(passwordOption);
        } else {
            Console console = System.console();
            if (console != null) {
                // Get password from console (not visible)
                password = String.valueOf(System.console().readPassword("Password: "));
            } else {
                // Last fallback-option: get password directly from System.in using a scanner (input visible!)
                Scanner scanner = new Scanner(System.in);
                System.out.print("Password (cleartext): ");
                password = scanner.nextLine();
            }
        }

        if (cmd.hasOption(artemisOption)) {
            String optionValue = cmd.getOptionValue(artemisOption);
            while (optionValue.endsWith("/")) {
                optionValue = optionValue.substring(0, optionValue.length() - 1);
            }
            ARTEMIS_BASE_URL = optionValue;
        }

        String file = cmd.getOptionValue(fileOption);
        int exerciseId = Integer.parseInt(cmd.getOptionValue(exerciseOption));


        // Parameters parsed and available

        AuthenticationService authenticationService = new AuthenticationService(username, password);
        ArtemisClient client = new ArtemisClient(authenticationService);

        ArtemisTutorMapper artemisTutorMapper = new ArtemisTutorMapper(client);

        artemisTutorMapper.loadOwnStudents(file);

        artemisTutorMapper.lockAllSubmissionsOfOwnStudents(exerciseId);
    }

    public ArtemisTutorMapper(ArtemisClient artemisClient) {
        this.artemisClient = artemisClient;
    }

    private void loadOwnStudents(final String filePath) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath));
        StringBuilder content = new StringBuilder();

        String read;
        while ((read = bufferedReader.readLine()) != null) {
            content.append(read).append("\n");
        }

        this.studentNames = StudentFileParser.parseStudentsFile(content.toString());
    }
    private void lockAllSubmissionsOfOwnStudents(int exerciseId) {
        List<Submission> submissions = this.artemisClient.getSubmissionsForExercise(exerciseId);

        log.info("Trying to lock submissions of own students (for exercise {}).", exerciseId);

        submissions.stream()
                .filter(this::isOwnStudent) // we only want to lock submissions of own students
                .forEach(artemisClient::lockSubmission);    // lock the submissions
    }

    private boolean isOwnStudent(Submission submission) {
        return studentNames.contains(submission.getParticipation().getStudent().getParticipantIdentifier().toLowerCase());
    }


}
