package com.shirkanesi.artemis.clienttest.ui;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Option;

import java.io.Console;
import java.util.Scanner;

public final class TUIHelper {

    private static final Scanner scanner = new Scanner(System.in);
    private static final Console console = System.console();

    static {
        Runtime.getRuntime().addShutdownHook(new Thread(scanner::close));
    }

    private TUIHelper() {
        throw new IllegalAccessError("Utility-classes may not be instantiated!");
    }

    public static String getStringParameter(CommandLine cmd, Option option) {
        if (cmd.hasOption(option)) {
            return cmd.getOptionValue(option);
        }

        System.out.print(option.getDescription() + ": ");
        return scanner.nextLine();
    }

    public static String getPasswordParameter(CommandLine cmd, Option option) {
        if (cmd.hasOption(option)) {
            return cmd.getOptionValue(option);
        }

        if (console != null) {
            // Get password from console (not visible)
            return String.valueOf(console.readPassword(option.getDescription() + ": "));
        }

        // Last fallback-option: get password directly from System.in using a scanner (input visible!)
        System.out.print(option.getDescription() + "(cleartext): ");
        return scanner.nextLine();
    }

    public static int getIntegerParameter(CommandLine cmd, Option option) {
        if (cmd.hasOption(option)) {
            try {
                return Integer.parseInt(cmd.getOptionValue(option));
            } catch (NumberFormatException ignored) {
                // ignore ==> ask again down below.
            }
        }

        int result = -1;

        while (result == -1) {
            System.out.print(option.getDescription() + ": ");
            try {
                result = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException ignored) {
                // try again...
            }
        }
        return result;
    }

}
