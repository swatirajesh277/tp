package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

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
//        ClearConfirmationWindow clearConfirmationWindow = new ClearConfirmationWindow();
//        clearConfirmationWindow.show();
//
//
//        model.setAddressBook(new AddressBook());
//        return new CommandResult(MESSAGE_SUCCESS);
        // Show confirmation dialog
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to clear all data?", ButtonType.YES, ButtonType.NO);
        alert.setTitle("Clear Confirmation");
        alert.setHeaderText(null);
        alert.showAndWait();

        if (alert.getResult() == ButtonType.YES) {
            model.setAddressBook(new AddressBook());
            return new CommandResult(MESSAGE_SUCCESS);
        } else {
            return new CommandResult("Clearing data has been cancelled.");
        }
    }
}
