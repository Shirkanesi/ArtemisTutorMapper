# ArtemisTutorMapper
The platform Artemis does not offer a possibility for tutors to correct their **own** students.
This project provides a workaround for this by allowing tutors to start the correction of submissions of their own students at once
and therefore allows them to open the correction-page right from their dashboard.

### Quick-Start
Simply download the latest version of the tool and run it with the proper arguments:
```
java -jar <FileName.jar> -u <username> -p <password> -e <exercise-id> -f <path/to/students-file.txt>
```
The students-file should be a textfile containing the identifiers of the students (in case of KIT: "uxxxx").
Those must be separated by comma, spaces or new-lines; the tool will figure it out automatically.

The exercise-id can be found in the url of the dashboard (second number)

If you do not want your password to show up in the bash-history, you can also leave out `-p` and you'll be prompted to input it later.

List of all parameters:
```
 -a,--artemis <arg>    Artemis URL (default: https://artemis.praktomat.cs.kit.edu)
 -e,--exercise <arg>   id of exercise to lock
 -f,--file <arg>       Path to student-file
 -h,--help             Print this help
 -p,--password <arg>   Artemis password
 -u,--username <arg>   Artemis username
```

### Technical Details
The tool simply loads a list of all submissions from Artemis and filters it for the students assigned to the tutor.
Then it will simply acquire a lock on those submissions (by calling `/api/programming-submissions/<submission-id>/lock`).
This will in turn put all those submissions on the dashboard of the tutor (as they are marked as begun).