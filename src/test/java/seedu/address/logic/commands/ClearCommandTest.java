package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.getTypicalProfiler;

import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.model.Profiler;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;
import seedu.address.model.person.ProjectEqualsTargetPredicate;
import seedu.address.ui.ClearConfirmationWindowStub;

public class ClearCommandTest {

    private final Model model = new ModelManager(getTypicalProfiler(), new UserPrefs());

    @Test
    public void execute_noPredicateEmptyProfiler_success() {
        Model model = new ModelManager();
        Model expectedModel = new ModelManager();

        // Use the stub and simulate user confirming the clear action
        ClearConfirmationWindowStub stub = ClearConfirmationWindowStub.getInstance();
        stub.setConfirmationResult(true); //Simulate clicking "Yes"

        assertCommandSuccess(new ClearCommand(stub), model, ClearCommand.MESSAGE_CLEAR_ALL_SUCCESS, expectedModel);
    }

    @Test
    public void execute_noPredicateNonEmptyProfiler_success() {
        Model expectedModel = new ModelManager(getTypicalProfiler(), new UserPrefs());
        expectedModel.setProfiler(new Profiler());

        // Use the stub and simulate user confirming the clear action
        ClearConfirmationWindowStub stub = ClearConfirmationWindowStub.getInstance();
        stub.setConfirmationResult(true); //Simulate clicking "Yes"


        assertCommandSuccess(new ClearCommand(stub), model, ClearCommand.MESSAGE_CLEAR_ALL_SUCCESS, expectedModel);
    }

    @Test
    public void execute_withPredicateWithMatchingPeople_success() {
        Model expectedModel = new ModelManager(getTypicalProfiler(), new UserPrefs());
        Person targetPerson = model.getFilteredPersonList().get(0);
        expectedModel.deletePerson(targetPerson);

        String projectToFind = targetPerson.getProject().value;
        ProjectEqualsTargetPredicate predicate = new ProjectEqualsTargetPredicate(projectToFind);

        // Use the stub and simulate user confirming the clear action
        ClearConfirmationWindowStub stub = ClearConfirmationWindowStub.getInstance();
        stub.setConfirmationResult(true); //Simulate clicking "Yes"

        assertCommandSuccess(new ClearCommand(predicate, stub), model,
                String.format(Messages.MESSAGE_PERSONS_CLEARED_OVERVIEW, 1), expectedModel);
    }

    @Test
    public void execute_withPredicateNoMatchingPeople_success() {
        Model expectedModel = new ModelManager(getTypicalProfiler(), new UserPrefs());
        ProjectEqualsTargetPredicate predicate = new ProjectEqualsTargetPredicate("NonExistentProject");

        // Use the stub and simulate user confirming the clear action
        ClearConfirmationWindowStub stub = ClearConfirmationWindowStub.getInstance();
        stub.setConfirmationResult(true); //Simulate clicking "Yes"

        assertCommandSuccess(new ClearCommand(predicate, stub), model,
                String.format(Messages.MESSAGE_PERSONS_CLEARED_OVERVIEW, 0), expectedModel);

    }

    @Test
    public void execute_emptyProfiler_cancel() {
        Model model = new ModelManager();
        Model expectedModel = new ModelManager();

        // Use the stub and simulate user cancelling the clear action
        ClearConfirmationWindowStub stub = ClearConfirmationWindowStub.getInstance();
        stub.setConfirmationResult(false); //Simulate clicking "No"

        assertCommandSuccess(new ClearCommand(stub), model, ClearCommand.MESSAGE_CANCELLED, expectedModel);
    }

    @Test
    public void execute_nonEmptyProfiler_cancel() {
        Model model = new ModelManager(getTypicalProfiler(), new UserPrefs());
        Model expectedModel = new ModelManager(getTypicalProfiler(), new UserPrefs());

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
        Model model = new ModelManager(getTypicalProfiler(), new UserPrefs());
        Model expectedModel = new ModelManager(getTypicalProfiler(), new UserPrefs());

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

    @Test
    public void equals() {
        ProjectEqualsTargetPredicate firstPredicate =
                new ProjectEqualsTargetPredicate("first");
        ProjectEqualsTargetPredicate secondPredicate =
                new ProjectEqualsTargetPredicate("second");

        // Stub that won't get opened
        ClearConfirmationWindowStub stub = ClearConfirmationWindowStub.getInstance();

        ClearCommand clearFirstCommand = new ClearCommand(firstPredicate, stub);
        ClearCommand clearSecondCommand = new ClearCommand(secondPredicate, stub);
        ClearCommand clearCommandEmpty = new ClearCommand(stub);

        // same object -> returns true
        assertEquals(clearFirstCommand, clearFirstCommand);
        assertEquals(clearCommandEmpty, clearCommandEmpty);

        // same values -> returns true
        assertEquals(new ClearCommand(firstPredicate, stub), clearFirstCommand);

        // both null values -> returns true
        assertEquals(new ClearCommand(stub), clearCommandEmpty);

        // different types -> returns false
        assertFalse(clearFirstCommand.equals(1));

        // null -> returns false
        assertFalse(clearFirstCommand.equals(null));

        // different project -> returns false
        assertFalse(clearFirstCommand.equals(clearSecondCommand));

        // only one predicate is null -> returns false
        assertFalse(clearCommandEmpty.equals(clearFirstCommand));
    }

    @Test
    public void toStringMethod() {
        // Stub that won't get opened
        ClearConfirmationWindowStub stub = ClearConfirmationWindowStub.getInstance();

        // No predicate
        ClearCommand clearCommand = new ClearCommand(stub);
        String expected = ClearCommand.class.getCanonicalName() + "{predicate=" + null + "}";
        assertEquals(expected, clearCommand.toString());

        // With predicate
        ProjectEqualsTargetPredicate predicate = new ProjectEqualsTargetPredicate("Prof-iler");
        clearCommand = new ClearCommand(predicate, stub);
        expected = ClearCommand.class.getCanonicalName() + "{predicate=" + predicate + "}";
        assertEquals(expected, clearCommand.toString());
    }
}


