package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.model.person.ProjectEqualsTargetPredicate;
import seedu.address.ui.ClearConfirmationWindow;
import seedu.address.ui.ConfirmationWindow;

/**
 * Clears the address book, or clears by project.
 */
public class ClearCommand extends Command {

    public static final String COMMAND_WORD = "clear";

    public static final String MESSAGE_CLEAR_ALL_SUCCESS = "Prof-iler data has been cleared!";
    public static final String MESSAGE_CANCELLED = "Clearing data has been cancelled.";
    public static final String MESSAGE_REPEATED = "Repeated clear command, please respond on pop up window.";

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

    }

    /**
     * Returns true if this command has a predicate (project-specific clear).
     */
    public boolean hasPredicate() {
        return predicate != null;
    }


    // Replace the execute method with this SLAP-friendly version:
    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);

        if (!prepareConfirmation()) {
            return new CommandResult(MESSAGE_REPEATED);
        }

        boolean isConfirmed = confirmationWindow.showAndWait();
        if (!isConfirmed) {
            return new CommandResult(MESSAGE_CANCELLED);
        }

        return performClear(model);
    }

    /**
     * Prepares the confirmation window for showing.
     * Returns false if the existing window should be kept (same command type).
     */
    private boolean prepareConfirmation() {
        if (!confirmationWindow.isShowing()) {
            if (confirmationWindow instanceof ClearConfirmationWindow) {
                ((ClearConfirmationWindow) confirmationWindow).setCurrentCommand(this);
            }
            return true;
        }

        if (shouldReplaceExistingConfirmation()) {
            replaceExistingConfirmation();
            return true;
        } else {
            confirmationWindow.focus();
            return false;
        }
    }

    /**
     * Determines if the current confirmation should replace an existing one.
     */
    private boolean shouldReplaceExistingConfirmation() {
        if (!(confirmationWindow instanceof ClearConfirmationWindow)) {
            return true;
        }

        ClearCommand existingCommand = ((ClearConfirmationWindow) confirmationWindow).getCurrentCommand();

        return existingCommand == null ||
                (existingCommand.hasPredicate() != this.hasPredicate()) ||
                (existingCommand.hasPredicate() && this.hasPredicate() &&
                        !existingCommand.predicate.equals(this.predicate));
    }

    /**
     * Replaces the existing confirmation window with a new one.
     */
    private void replaceExistingConfirmation() {
        confirmationWindow.hide();
        confirmationWindow.reset();

        if (confirmationWindow instanceof ClearConfirmationWindow) {
            ((ClearConfirmationWindow) confirmationWindow).setCurrentCommand(this);
        }

        // Allow UI to update
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    /**
     * Performs the actual clear operation on the model.
     */
    private CommandResult performClear(Model model) {
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
