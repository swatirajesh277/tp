package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.ui.ClearConfirmationWindowStub;

public class ClearCommandTest {

    @Test
    public void execute_emptyAddressBook_success() {
        Model model = new ModelManager();
        Model expectedModel = new ModelManager();

        // Use the stub and simulate user confirming the clear action
        ClearConfirmationWindowStub stub = ClearConfirmationWindowStub.getInstance();
        stub.setConfirmationResult(true); //Simulate clicking "Yes"

        assertCommandSuccess(new ClearCommand(stub), model, ClearCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_nonEmptyAddressBook_success() {
        Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel.setAddressBook(new AddressBook());

        // Use the stub and simulate user confirming the clear action
        ClearConfirmationWindowStub stub = ClearConfirmationWindowStub.getInstance();
        stub.setConfirmationResult(true); //Simulate clicking "Yes"

        assertCommandSuccess(new ClearCommand(stub), model, ClearCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_emptyAddressBook_cancel() {
        Model model = new ModelManager();
        Model expectedModel = new ModelManager();

        // Use the stub and simulate user cancelling the clear action
        ClearConfirmationWindowStub stub = ClearConfirmationWindowStub.getInstance();
        stub.setConfirmationResult(false); //Simulate clicking "No"

        assertCommandSuccess(new ClearCommand(stub), model, ClearCommand.MESSAGE_CANCELLED, expectedModel);
    }

    @Test
    public void execute_nonEmptyAddressBook_cancel() {
        Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());

        // Use the stub and simulate user cancelling the clear action
        ClearConfirmationWindowStub stub = ClearConfirmationWindowStub.getInstance();
        stub.setConfirmationResult(false); //Simulate clicking "No"

        assertCommandSuccess(new ClearCommand(stub), model, ClearCommand.MESSAGE_CANCELLED, expectedModel);
    }

    @Test
    public void execute_repeatedClearCommand_returnsRepeatedMessage() {
        // Get the stub, set showing to be true, and reset focus call tracker
        ClearConfirmationWindowStub stub = ClearConfirmationWindowStub.getInstance();
        stub.setShowing(true);
        stub.setWasFocusCalled(false);

        // Create the clear command with our stub
        ClearCommand clearCommand = new ClearCommand(stub);

        // Execute the command - should return the REPEATED message
        CommandResult result = clearCommand.execute(new ModelManager());

        // Verify the result contains the correct message
        assertEquals(ClearCommand.MESSAGE_REPEATED, result.getFeedbackToUser());

        // Verify that focus was called on the window
        assertTrue(stub.wasFocusCalled(), "The focus() method should be called on the window");

        // Reset stub after test
        stub.resetValues();
    }

    @Test
    public void execute_repeatedClearCommand_modelUnchanged() {
        Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());

        // Create a stub that simulates an already showing confirmation window
        ClearConfirmationWindowStub stub = ClearConfirmationWindowStub.getInstance();
        stub.setShowing(true);

        // Create the clear command with our stub
        ClearCommand clearCommand = new ClearCommand(stub);

        // Execute the command
        clearCommand.execute(model);

        // Verify model was not changed (data should be preserved when popup is already showing)
        assertEquals(expectedModel, model, "Model should not be changed when confirmation window is already showing");
    }

}


