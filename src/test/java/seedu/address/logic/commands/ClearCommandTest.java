package seedu.address.logic.commands;

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

}


