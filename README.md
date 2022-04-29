# ArtemisTutorMapper

*Note: This tool has not been fully tested and therefore might cause issues. This IS work in progress. However, in theory, there <u>shouldn't</u> be any problems using it.*

*Technically Artemis has a limit for how many assessments a tutor can have open at once per course which is set to 10. Unfortunately, this value is (at time of writing) hard-coded in Artemis; planned to be moved into config-file. This means it is imposible to lock all submissions for one tutor at the beginning of correction. This can be "fixed" (worked around) by locking 10 exercises, correcting them (which releases the lock), and then lock the next 10 exercises. Tedeous, but better than nothing.*
*If you are interested in fixing this issue, take a look at the hard-coded constant [here](https://github.com/ls1intum/Artemis/blob/f13a8dc62205f950fe1ea39b7d0cb50b44a8b091/src/main/java/de/tum/in/www1/artemis/config/Constants.java#L94).*
*Until this is fixed in Artemis we've got to stick with this solution :(*

*Note2: The current implementation only supports programming-exercises. Other types can/will be implemented in the future.*

### Motivation
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
