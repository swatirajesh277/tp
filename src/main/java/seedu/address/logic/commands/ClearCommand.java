package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import javafx.stage.Stage;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.ui.ClearConfirmationWindow;

/**
 * Clears the address book.
 */
public class ClearCommand extends Command {

    public static final String COMMAND_WORD = "clear";
    public static final String MESSAGE_SUCCESS = "Prof-iler data has been cleared!";
    public static final String MESSAGE_CANCELLED = "Clearing data has been cancelled.";
    public static final String MESSAGE_REPEATED = "Repeated clear command, please respond on pop up window.";


    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        ClearConfirmationWindow clearConfirmationWindow = ClearConfirmationWindow.getInstance();

        // Ignore the command if the confirmation window is already open
        if (clearConfirmationWindow.isShowing()) {
            clearConfirmationWindow.focus();
            return new CommandResult(MESSAGE_REPEATED);
        }

        boolean isConfirmed = clearConfirmationWindow.showAndWait();

        if (isConfirmed) {
            model.setAddressBook(new AddressBook());
            return new CommandResult(MESSAGE_SUCCESS);
        } else {
            return new CommandResult(MESSAGE_CANCELLED);
        }
    }
}
