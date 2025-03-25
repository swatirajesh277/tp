package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.concurrent.CompletableFuture;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.ui.ClearConfirmationWindow;

/**
 * Clears the address book.
 */
public class ClearCommand extends Command {

    public static final String COMMAND_WORD = "clear";
    public static final String MESSAGE_SUCCESS = "Prof-iler data has been cleared!";
    public static final String MESSAGE_CANCELLED = "Clearing data has been cancelled";


    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        ClearConfirmationWindow clearConfirmationWindow = new ClearConfirmationWindow();
        boolean isConfirmed = clearConfirmationWindow.showAndWait();

        if (isConfirmed) {
            model.setAddressBook(new AddressBook());
            return new CommandResult(MESSAGE_SUCCESS);
        } else {
            return new CommandResult(MESSAGE_CANCELLED);
        }
    }
}
