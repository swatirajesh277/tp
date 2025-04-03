package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;

/**
 * Deletes a person identified using it's displayed index from prof-iler.
 */
public class DeleteCommand extends Command {

    public static final String COMMAND_WORD = "delete";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the person identified by the index number used in the displayed person list.\n"
            + "Parameters: INDEX [,INDEXES] ... (must be positive integers separated by commas)\n"
            + "Example: " + COMMAND_WORD + " 1,2,4";

    private final List<Index> targetIndexes;

    public DeleteCommand(List<Index> targetIndexes) {
        this.targetIndexes = targetIndexes;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getSortedPersonList();

        List<Index> sortedIndexes = targetIndexes.stream()
                .sorted(Comparator.comparingInt(Index::getZeroBased).reversed())
                .collect(Collectors.toList());

        List<Person> deletedPersons = new ArrayList<>();
        for (Index targetIndex : sortedIndexes) {
            if (targetIndex.getZeroBased() >= lastShownList.size()) {
                throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
            }

            Person personToDelete = lastShownList.get(targetIndex.getZeroBased());
            model.deletePerson(personToDelete);
            deletedPersons.add(personToDelete);
        }

        return new CommandResult(createResultMessage(deletedPersons));
    }

    private String createResultMessage(List<Person> deletedPersons) {
        String result = "Deleted Student(s): \n";
        for (Person person : deletedPersons) {
            result += Messages.format(person) + "\n";
        }
        return result.trim();
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof DeleteCommand
                && targetIndexes.equals(((DeleteCommand) other).targetIndexes));
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("targetIndexes", targetIndexes)
                .toString();
    }
}
