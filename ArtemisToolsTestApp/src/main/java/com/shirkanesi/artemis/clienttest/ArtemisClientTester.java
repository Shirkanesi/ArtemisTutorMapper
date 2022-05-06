package com.shirkanesi.artemis.clienttest;

import com.shirkanesi.artemis.client.logic.ArtemisClient;
import com.shirkanesi.artemis.client.logic.AuthenticationService;
import com.shirkanesi.artemis.client.model.exercise.Exercise;
import com.shirkanesi.artemis.client.model.exercise.ExerciseTypes;
import com.shirkanesi.artemis.client.model.participation.ProgrammingParticipation;
import com.shirkanesi.artemis.client.model.submission.Submission;
import com.shirkanesi.artemis.clienttest.ui.TUIHelper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.cli.*;

import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
public class ArtemisClientTester {

    private static final String DEFAULT_ARTEMIS_BASE_URL = "https://artemis.praktomat.cs.kit.edu";

    private List<String> studentNames;

    private final ArtemisClient artemisClient;

    public static void main(String[] args) throws IOException, ParseException {

        // TODO: cleanup of main-method. CLI-parsing right here is ugly...

        Options options = new Options();

        Option usernameOption = new Option("u", "username", true, "Artemis username");
        Option passwordOption = new Option("p", "password", true, "Artemis password");
        Option exerciseOption = new Option("e", "exercise", true, "id of exercise to lock");
        Option artemisOption = new Option("a", "artemis", true, "Artemis URL (default: " + DEFAULT_ARTEMIS_BASE_URL + ")");
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

        String artemisUrl = DEFAULT_ARTEMIS_BASE_URL;

        if (cmd.hasOption(artemisOption)) {
            String optionValue = cmd.getOptionValue(artemisOption);
            while (optionValue.endsWith("/")) {
                // remove slash at the end
                optionValue = optionValue.substring(0, optionValue.length() - 1);
            }
            artemisUrl = optionValue;
        }

        String file = TUIHelper.getStringParameter(cmd, fileOption);


        // Auth-Parameters parsed and available

        AuthenticationService authenticationService = new AuthenticationService(username, password, artemisUrl);
        ArtemisClient client = new ArtemisClient(authenticationService);

        Set<Exercise> exercises = client.getCoursesForManagement()
                .stream()   // TODO: filter for active courses
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

        ArtemisClientTester artemisTutorMapper = new ArtemisClientTester(client);

        artemisTutorMapper.lockAllSubmissionsOfOwnStudents(exercise);
    }

    public ArtemisClientTester(ArtemisClient artemisClient) {
        this.artemisClient = artemisClient;
    }

    private void lockAllSubmissionsOfOwnStudents(Exercise exercise) {
        List<? extends Submission> submissions = exercise.getSubmissionsForExercise(this.artemisClient);

        log.info("Trying to lock submissions of own students (for exercise {}).", exercise.getId());

        log.info("Found {} submissions in total", submissions.size());

        submissions.stream().findAny().ifPresent(submission ->
                ((ProgrammingParticipation) submission.getParticipation()).getGitlabProject(this.artemisClient)
        );

    }

}
