package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BENSON;
import static seedu.address.testutil.TypicalPersons.CARL;
import static seedu.address.testutil.TypicalPersons.DANIEL;
import static seedu.address.testutil.TypicalPersons.ELLE;
import static seedu.address.testutil.TypicalPersons.FIONA;
import static seedu.address.testutil.TypicalPersons.GEORGE;
import static seedu.address.testutil.TypicalPersons.getTypicalProfiler;

import java.util.Arrays;
import java.util.Comparator;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;

public class SortCommandTest {
    private Model model = new ModelManager(getTypicalProfiler(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalProfiler(), new UserPrefs());

    @Test
    public void equals() {
        SortCommand firstSortCommand = new SortCommand(true);
        SortCommand secondSortCommand = new SortCommand(true);

        // same object -> returns true
        assertTrue(firstSortCommand.equals(firstSortCommand));

        // same values -> returns true
        assertTrue(firstSortCommand.equals(secondSortCommand));

        // different types -> returns false
        assertFalse(firstSortCommand.equals(1));

        // null -> returns false
        assertFalse(firstSortCommand.equals(null));

        // different order -> returns false
        secondSortCommand = new SortCommand(false);
        assertFalse(firstSortCommand.equals(secondSortCommand));
    }

    @Test
    public void execute_ascending_sorted() {
        String expectedMessage = String.format("Sorted in ascending order.");
        SortCommand command = new SortCommand(true);
        Comparator<Person> comparator = Comparator.comparingInt(
                person -> Integer.parseInt(person.getProgress().getValue())
        );
        expectedModel.updateSortedPersonList(comparator);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(GEORGE, FIONA, ELLE, CARL, ALICE, DANIEL, BENSON),
                model.getSortedPersonList());
    }

    @Test
    public void execute_descending_sorted() {
        String expectedMessage = String.format("Sorted in descending order.");
        SortCommand command = new SortCommand(false);
        Comparator<Person> comparator = Comparator.comparingInt(
                person -> Integer.parseInt(person.getProgress().getValue())
        );
        comparator = comparator.reversed();
        expectedModel.updateSortedPersonList(comparator);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(BENSON, DANIEL, ALICE, CARL, ELLE, FIONA, GEORGE),
                model.getSortedPersonList());
    }

    @Test
    public void toStringMethod() {
        SortCommand sortCommand = new SortCommand(true);
        String expected = SortCommand.class.getCanonicalName() + "{isAscending=" + "true" + "}";
        assertEquals(expected, sortCommand.toString());
    }
}
