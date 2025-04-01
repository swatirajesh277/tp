package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;
import seedu.address.model.person.ProjectEqualsTargetPredicate;
import seedu.address.ui.ClearConfirmationWindowStub;

public class ClearCommandTest {

    private final Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_noPredicateEmptyAddressBook_success() {
        Model model = new ModelManager();
        Model expectedModel = new ModelManager();

        // Use the stub and simulate user confirming the clear action
        ClearConfirmationWindowStub stub = ClearConfirmationWindowStub.getInstance();
        stub.setConfirmationResult(true); //Simulate clicking "Yes"

        assertCommandSuccess(new ClearCommand(stub), model, ClearCommand.MESSAGE_CLEAR_ALL_SUCCESS, expectedModel);
    }

    @Test
    public void execute_noPredicateNonEmptyAddressBook_success() {
        Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel.setAddressBook(new AddressBook());

        // Use the stub and simulate user confirming the clear action
        ClearConfirmationWindowStub stub = ClearConfirmationWindowStub.getInstance();
        stub.setConfirmationResult(true); //Simulate clicking "Yes"


        assertCommandSuccess(new ClearCommand(stub), model, ClearCommand.MESSAGE_CLEAR_ALL_SUCCESS, expectedModel);
    }

    @Test
    public void execute_withPredicateWithMatchingPeople_success() {
        Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());
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
        Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        ProjectEqualsTargetPredicate predicate = new ProjectEqualsTargetPredicate("NonExistentProject");

        // Use the stub and simulate user confirming the clear action
        ClearConfirmationWindowStub stub = ClearConfirmationWindowStub.getInstance();
        stub.setConfirmationResult(true); //Simulate clicking "Yes"

        assertCommandSuccess(new ClearCommand(predicate, stub), model,
                String.format(Messages.MESSAGE_PERSONS_CLEARED_OVERVIEW, 0), expectedModel);

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


