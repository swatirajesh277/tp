package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.Comparator;

import seedu.address.model.Model;
import seedu.address.model.person.Person;

public class SortCommand extends Command {
    public static final String COMMAND_WORD = "sort";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Sorts the student's progress based on the given order"
            + "Parameters: ORDER (asc/desc)\n"
            + "Example: " + COMMAND_WORD + " asc";

    private final boolean isAscending;

    public SortCommand(boolean isAscending) {
        this.isAscending = isAscending;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        Comparator<Person> comparator = Comparator.comparingInt(
                person -> Integer.parseInt(person.getProgress().getValue())
        );
        if (!isAscending) {
            comparator = comparator.reversed();
        }
        model.updateSortedPersonList(comparator);

        return new CommandResult(
                String.format("Sorted in %s order.", isAscending ? "ascending" : "descending")
        );
    }

}
