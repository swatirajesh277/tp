package seedu.address.logic.commands;

import java.util.Arrays;
import java.util.List;

import seedu.address.model.Model;

/**
 * Format full help instructions for every command for display.
 */
public class HelpCommand extends Command {

    public static final String COMMAND_WORD = "help";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Shows program usage instructions.\n"
            + "Example: " + COMMAND_WORD;

    public static final List<String> HELP_COMMANDS = Arrays.asList(
            "add: Add a new student in Prof-iler.\n"
                    + "Usage: add NAME p/PHONE id/STUDENT_ID e/EMAIL pr/PROJECT [pb/PROGRESS] [t/TAG]...\n"
                    + "Example: add John Doe p/98765432 id/A0293712D e/john@example.com pr/Orbital pb/38 t/Y3 t/CS",

            "delete: Remove student(s) by index number.\n"
                    + "Usage: delete INDEX [MORE_INDEXES] (separated by commas)\n"
                    + "Example: delete 1, 3",

            "edit: Modify student details by index.\n"
                    + "Usage: edit INDEX [n/NAME] [id/ID] [p/PHONE] [e/EMAIL] [pr/PROJECT] [t/TAG]...\n"
                    + "Example: edit 1 p/91234567 e/john@example.com",

            "progress: Update a student's project progress by index.\n"
                    + "Usage: progress INDEX pb/PROGRESS\n"
                    + "Example: progress 1 pb/87",

            "log: Adds log for student by index.\n"
                    + "Format: log INDEX l/LOG\n"
                    + "Example: log 1 l/CS2103T tutor",

            "find: Search students by name (case-insensitive).\n"
                    + "Usage: find KEYWORD [MORE_KEYWORDS]...\n"
                    + "Example: find John Doe",

            "filter: Display students by specific project or tag.\n"
                    + "Usage: filter [pr/PROJECT] | [t/TAG]...\n"
                    + "Examples: filter pr/Orbital, filter t/Y3",

            "sort: Organize students by progress in ascending or descending order.\n"
                    + "Usage: sort asc|desc\n"
                    + "Example: sort desc",

            "list: Show all students.\n"
                    + "Usage: list",

            "clear: Remove all students from Prof-iler.\n"
                    + "Usage: clear\n"
    );

    public static final String SHOWING_HELP_MESSAGE = "Opened help window.";

    @Override
    public CommandResult execute(Model model) {
        return new CommandResult(SHOWING_HELP_MESSAGE, true, false);
    }
}
