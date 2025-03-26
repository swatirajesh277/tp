package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.Comparator;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.Model;
import seedu.address.model.person.Person;

/**
 * Sorts and list all students in Prof-iler based on the order given.
 * Order given is either asc or desc and it is case-insensitive.
 */
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

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof SortCommand)) {
            return false;
        }

        SortCommand otherSortCommand = (SortCommand) other;
        return isAscending == otherSortCommand.isAscending;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("isAscending", isAscending)
                .toString();
    }
}
