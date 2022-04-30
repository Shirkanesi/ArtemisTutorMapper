package com.shirkanesi.artemistutormapper;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.shirkanesi.artemistutormapper.logic.ArtemisClient;
import com.shirkanesi.artemistutormapper.logic.AuthenticationService;
import com.shirkanesi.artemistutormapper.logic.StudentFileParser;
import com.shirkanesi.artemistutormapper.logic.ui.TUIHelper;
import com.shirkanesi.artemistutormapper.model.exercise.Exercise;
import com.shirkanesi.artemistutormapper.model.exercise.ExerciseTypes;
import com.shirkanesi.artemistutormapper.model.submission.Submission;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
public class ArtemisTutorMapper {

    public static String ARTEMIS_BASE_URL = "https://artemis.praktomat.cs.kit.edu";
    public static final ObjectMapper OBJECT_MAPPER = new ObjectMapper() {{
        enable(DeserializationFeature.READ_UNKNOWN_ENUM_VALUES_USING_DEFAULT_VALUE);
    }};
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

        String username = TUIHelper.getStringParameter(cmd, usernameOption);
        String password = TUIHelper.getPasswordParameter(cmd, passwordOption);

        if (cmd.hasOption(artemisOption)) {
            String optionValue = cmd.getOptionValue(artemisOption);
            while (optionValue.endsWith("/")) {
                // remove slash at the end
                optionValue = optionValue.substring(0, optionValue.length() - 1);
            }
            ARTEMIS_BASE_URL = optionValue;
        }

        String file = TUIHelper.getStringParameter(cmd, fileOption);


        // Auth-Parameters parsed and available

        AuthenticationService authenticationService = new AuthenticationService(username, password);
        ArtemisClient client = new ArtemisClient(authenticationService);

        Set<Exercise> exercises = client.getCoursesForManagement()
                .stream()
                .flatMap(course -> course.getExercises().stream())
                .filter(exercise -> !exercise.getType().equals(ExerciseTypes.UNKNOWN))
                .collect(Collectors.toSet());

        if (!cmd.hasOption(exerciseOption)) {
            log.info("List of all exercises:");
            exercises.stream()
                    .sorted(Comparator.comparingInt(Exercise::getId))
                    .map(exercise -> String.format("%d: %s", exercise.getId(), exercise.getTitle()))
//                    .map(exercise -> String.format("%s: %s", exercise.getType(), exercise.getExerciseType()))
//                    .map(exercise -> exercise.getClass().getCanonicalName())
                    .forEachOrdered(System.out::println);
        }

        int exerciseId = TUIHelper.getIntegerParameter(cmd, exerciseOption);

        Exercise exercise = exercises.stream().filter(e -> e.getId() == exerciseId).findFirst().orElseThrow();

        ArtemisTutorMapper artemisTutorMapper = new ArtemisTutorMapper(client);

        artemisTutorMapper.loadOwnStudents(file);

        artemisTutorMapper.lockAllSubmissionsOfOwnStudents(exercise);
    }

    public ArtemisTutorMapper(ArtemisClient artemisClient) {
        this.artemisClient = artemisClient;
    }

    private void loadOwnStudents(final String filePath) throws IOException {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath))) {
            StringBuilder content = new StringBuilder();

            String read;
            while ((read = bufferedReader.readLine()) != null) {
                content.append(read).append("\n");
            }

            this.studentNames = StudentFileParser.parseStudentsFile(content.toString());
            log.info("Loaded {} students from {}", this.studentNames.size(), filePath);
        }
    }

    private void lockAllSubmissionsOfOwnStudents(Exercise exercise) {
        List<? extends Submission> submissions = exercise.getSubmissionsForExercise(this.artemisClient);

        log.info("Trying to lock submissions of own students (for exercise {}).", exercise.getId());

        log.info("Found {} submissions in total", submissions.size());

        submissions.stream()
//                .map(submission -> submission.getParticipation().getStudent().getName()).forEach(System.out::println);    // TODO: debug only
//                .forEach(System.out::println);
                .filter(this::isOwnStudent) // we only want to lock submissions of own students
                .forEach(submission -> submission.lockSubmission(this.artemisClient));    // lock the submissions
    }

    private boolean isOwnStudent(Submission submission) {
        return studentNames.contains(submission.getParticipation().getStudent().getParticipantIdentifier().toLowerCase());
    }

}
