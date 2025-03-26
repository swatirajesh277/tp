package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.ui.ConfirmationWindow;
import seedu.address.ui.ConfirmationWindowFactory;

/**
 * Clears the address book.
 */
public class ClearCommand extends Command {

    public static final String COMMAND_WORD = "clear";
    public static final String MESSAGE_SUCCESS = "Prof-iler data has been cleared!";
    public static final String MESSAGE_CANCELLED = "Clearing data has been cancelled.";
    public static final String MESSAGE_REPEATED = "Repeated clear command, please respond on pop up window.";

    private final ConfirmationWindow confirmationWindow;

    /**
     * Creates a {@code ClearCommand} that uses the default {@code ClearConfirmationWindow} instance.
     * This constructor is used in normal execution.
     */
    public ClearCommand() {
        this.confirmationWindow = ConfirmationWindowFactory.getConfirmationWindow();
    }

    /**
     * Creates a {@code ClearCommand} with a specified {@code ClearConfirmationWindow} instance.
     * This constructor is primarily used for testing, allowing dependency injection of a stub.
     *
     * @param confirmationWindow The confirmation window instance to use.
     */
    public ClearCommand(ConfirmationWindow confirmationWindow) {
        this.confirmationWindow = confirmationWindow;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);

        // Ignore the command if the confirmation window is already open
        if (confirmationWindow.isShowing()) {
            assert confirmationWindow.isShowing();
            confirmationWindow.focus();
            return new CommandResult(MESSAGE_REPEATED);
        }

        boolean isConfirmed = confirmationWindow.showAndWait();

        if (isConfirmed) {
            model.setAddressBook(new AddressBook());
            return new CommandResult(MESSAGE_SUCCESS);
        } else {
            return new CommandResult(MESSAGE_CANCELLED);
        }
    }
}
