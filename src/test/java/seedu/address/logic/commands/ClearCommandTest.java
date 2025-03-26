package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
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

public class ClearCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_noPredicateEmptyAddressBook_success() {
        Model model = new ModelManager();
        Model expectedModel = new ModelManager();

        assertCommandSuccess(new ClearCommand(), model, ClearCommand.MESSAGE_CLEAR_ALL_SUCCESS, expectedModel);
    }

    @Test
    public void execute_noPredicateNonEmptyAddressBook_success() {
        Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel.setAddressBook(new AddressBook());

        assertCommandSuccess(new ClearCommand(), model, ClearCommand.MESSAGE_CLEAR_ALL_SUCCESS, expectedModel);
    }

    @Test
    public void execute_withPredicateWithMatchingPeople_success() {
        Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        Person targetPerson = model.getFilteredPersonList().get(0);
        expectedModel.deletePerson(targetPerson);

        String projectToFind = targetPerson.getProject().value;
        ProjectEqualsTargetPredicate predicate = new ProjectEqualsTargetPredicate(projectToFind);

        assertCommandSuccess(new ClearCommand(predicate), model,
                String.format(Messages.MESSAGE_PERSONS_CLEARED_OVERVIEW, 1), expectedModel);
    }

    @Test
    public void execute_withPredicateNoMatchingPeople_success() {
        Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        ProjectEqualsTargetPredicate predicate = new ProjectEqualsTargetPredicate("NonExistentProject");

        assertCommandSuccess(new ClearCommand(predicate), model,
                String.format(Messages.MESSAGE_PERSONS_CLEARED_OVERVIEW, 0), expectedModel);
    }

    @Test
    public void equals() {
        ProjectEqualsTargetPredicate firstPredicate =
                new ProjectEqualsTargetPredicate("first");
        ProjectEqualsTargetPredicate secondPredicate =
                new ProjectEqualsTargetPredicate("second");

        ClearCommand clearFirstCommand = new ClearCommand(firstPredicate);
        ClearCommand clearSecondCommand = new ClearCommand(secondPredicate);
        ClearCommand clearCommandEmpty = new ClearCommand();

        // same object -> returns true
        assertEquals(clearFirstCommand, clearFirstCommand);
        assertEquals(clearCommandEmpty, clearCommandEmpty);

        // same values -> returns true
        assertEquals(new ClearCommand(firstPredicate), clearFirstCommand);

        // both null values -> returns true
        assertEquals(new ClearCommand(), clearCommandEmpty);

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
        // No predicate
        ClearCommand clearCommand = new ClearCommand();
        String expected = ClearCommand.class.getCanonicalName() + "{predicate=" + null + "}";
        assertEquals(expected, clearCommand.toString());

        // With predicate
        ProjectEqualsTargetPredicate predicate = new ProjectEqualsTargetPredicate("Prof-iler");
        clearCommand = new ClearCommand(predicate);
        expected = ClearCommand.class.getCanonicalName() + "{predicate=" + predicate + "}";
        assertEquals(expected, clearCommand.toString());
    }
}
