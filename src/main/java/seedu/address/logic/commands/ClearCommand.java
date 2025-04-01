package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.model.person.ProjectEqualsTargetPredicate;
import seedu.address.ui.ConfirmationWindow;

/**
 * Clears the address book, or clears by project.
 */
public class ClearCommand extends Command {

    public static final String COMMAND_WORD = "clear";

    public static final String MESSAGE_CLEAR_ALL_SUCCESS = "Prof-iler data has been cleared!";
    public static final String MESSAGE_CANCELLED = "Clearing data has been cancelled.";
    public static final String MESSAGE_REPEATED = "Repeated clear command, please respond on pop up window.";
    public static final String CLEAR_MESSAGE = "Confirm to clear all records?";
    public static final String CLEAR_MATCHING_MESSAGE = "Confirm to clear matching records?";

    private final ProjectEqualsTargetPredicate predicate;
    private final ConfirmationWindow confirmationWindow;

    /**
     * Creates a {@code ClearCommand} with a specified {@code ClearConfirmationWindow} instance.<br>
     * Clears the whole address book.
     *
     * @param confirmationWindow The confirmation window instance to use.
     */
    public ClearCommand(ConfirmationWindow confirmationWindow) {
        this(null, confirmationWindow);
        confirmationWindow.setMessage(CLEAR_MESSAGE);
    }

    /**
     * Creates a {@code ClearCommand} with a specified {@code ClearConfirmationWindow} instance.<br>
     * Clears people according to predicate.
     *
     * @param predicate The predicate to filter the people to clear.
     * @param confirmationWindow The confirmation window instance to use.
     */
    public ClearCommand(ProjectEqualsTargetPredicate predicate, ConfirmationWindow confirmationWindow) {
        this.predicate = predicate;
        this.confirmationWindow = confirmationWindow;
        confirmationWindow.setMessage(CLEAR_MATCHING_MESSAGE);
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);

        boolean isConfirmed = confirmationWindow.showAndWait();
        if (!isConfirmed) {
            return new CommandResult(MESSAGE_CANCELLED);

        }

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
