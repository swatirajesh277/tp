package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.model.person.ProjectEqualsTargetPredicate;

/**
 * Clears the address book, or clears by project.
 */
public class ClearCommand extends Command {

    public static final String COMMAND_WORD = "clear";

    public static final String MESSAGE_CLEAR_ALL_SUCCESS = "Prof-iler data has been cleared!";

    private final ProjectEqualsTargetPredicate predicate;

    /**
     * Clears the whole address book
     */
    public ClearCommand() {
        this(null);
    }

    /**
     * Clears people according to predicate
     * @param predicate The predicate to test against
     */
    public ClearCommand(ProjectEqualsTargetPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        if (predicate == null) {
            model.setAddressBook(new AddressBook());
            return new CommandResult(MESSAGE_CLEAR_ALL_SUCCESS);
        }

        model.updateFilteredPersonList(predicate);
        List<Person> filteredPersonList = model.getFilteredPersonList().stream().toList();
        int numPeopleCleared = filteredPersonList.size();
        filteredPersonList.forEach(model::deletePerson);

        model.updateFilteredPersonList(Model.PREDICATE_SHOW_ALL_PERSONS);
        return new CommandResult(String.format(Messages.MESSAGE_PERSONS_CLEARED_OVERVIEW, numPeopleCleared));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ClearCommand otherClearCommand)) {
            return false;
        }

        if (predicate == null) {
            return otherClearCommand.predicate == null;
        }
        return predicate.equals(otherClearCommand.predicate);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("predicate", predicate)
                .toString();
    }
}
