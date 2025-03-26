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
            "Add: Add a new student in Prof-iler.\n" +
                    "Usage: add NAME p/PHONE id/ID e/EMAIL pr/PROJECT [t/TAG]...\n" +
                    "Example: add John Doe p/98765432 id/A0293712D e/john@example.com pr/Orbital t/Y3 t/CS",

            "Delete: Remove student(s) by index number.\n" +
                    "Usage: delete INDEX [MORE_INDEXES] (separated by commas)\n" +
                    "Example: delete 1, 3",

            "Edit: Modify student details by index.\n" +
                    "Usage: edit INDEX [n/NAME] [id/ID] [p/PHONE] [e/EMAIL] [pr/PROJECT] [t/TAG]...\n" +
                    "Example: edit 1 p/91234567 e/john@example.com",

            "Progress: Update a student's project progress by index.\n" +
                    "Usage: progress INDEX pb/PROGRESS\n" +
                    "Example: progress 1 pb/87",

            "Find: Search students by name (case-insensitive).\n" +
                    "Usage: find KEYWORD [MORE_KEYWORDS]...\n" +
                    "Example: find John Doe",

            "Filter: Display students by specific project or tag.\n" +
                    "Usage: filter [pr/PROJECT] [t/TAG]...\n" +
                    "Examples: filter pr/Orbital, filter t/Y3",

            "Sort: Organize students by progress in ascending or descending order.\n" +
                    "Usage: sort asc|desc\n" +
                    "Example: sort desc",

            "List: Show all students.\n" +
                    "Usage: list",

            "Clear: Remove all students from Prof-iler.\n" +
                    "Usage: clear\n"
    );

    public static final String SHOWING_HELP_MESSAGE = "Opened help window.";

    @Override
    public CommandResult execute(Model model) {
        return new CommandResult(SHOWING_HELP_MESSAGE, true, false);
    }
}
