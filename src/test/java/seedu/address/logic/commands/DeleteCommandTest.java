package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showPersonAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalProfiler;

import java.util.Comparator;
import java.util.List;
import java.util.StringJoiner;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;

/**
 * Contains integration tests (interaction with the Model) and unit tests for
 * {@code DeleteCommand}.
 */
public class DeleteCommandTest {

    private Model model = new ModelManager(getTypicalProfiler(), new UserPrefs());

    @Test
    public void execute_validIndexUnfilteredList_success() {
        List<Index> indexesToDelete = List.of(
                Index.fromOneBased(3),
                Index.fromOneBased(1)
        );
        List<Index> sortedIndexes = indexesToDelete.stream()
                .sorted(Comparator.comparingInt(Index::getZeroBased).reversed())
                .collect(Collectors.toList());

        List<Person> personsToDelete = sortedIndexes.stream()
                .map(index -> model.getFilteredPersonList().get(index.getZeroBased()))
                .collect(Collectors.toList());

        DeleteCommand deleteCommand = new DeleteCommand(sortedIndexes);

        String expectedMessage = "Deleted Student(s): \n";
        List<String> personDescriptions = personsToDelete.stream()
                .map(Messages::format)
                .collect(Collectors.toList());

        expectedMessage += String.join("\n", personDescriptions);

        ModelManager expectedModel = new ModelManager(model.getProfiler(), new UserPrefs());
        personsToDelete.forEach(expectedModel::deletePerson);

        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        DeleteCommand deleteCommand = new DeleteCommand(List.of(outOfBoundIndex));

        assertCommandFailure(deleteCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        List<Index> indexesToDelete = List.of(INDEX_FIRST_PERSON);
        List<Person> personsToDelete = indexesToDelete.stream()
                .map(Index::getZeroBased)
                .map(model.getFilteredPersonList()::get)
                .collect(Collectors.toList());

        DeleteCommand deleteCommand = new DeleteCommand(indexesToDelete);

        StringJoiner deletedDescriptions = new StringJoiner("\n");
        personsToDelete.forEach(person -> deletedDescriptions.add(Messages.format(person)));
        String expectedMessage = String.format("Deleted Student(s): " + "\n%s", deletedDescriptions);

        ModelManager expectedModel = new ModelManager(model.getProfiler(), new UserPrefs());
        personsToDelete.forEach(expectedModel::deletePerson);
        showNoPerson(expectedModel);

        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        Index outOfBoundIndex = INDEX_SECOND_PERSON;
        assertTrue(outOfBoundIndex.getZeroBased() < model.getProfiler().getPersonList().size());

        DeleteCommand deleteCommand = new DeleteCommand(List.of(outOfBoundIndex));

        assertCommandFailure(deleteCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        DeleteCommand deleteFirstCommand = new DeleteCommand(List.of(INDEX_FIRST_PERSON));
        DeleteCommand deleteSecondCommand = new DeleteCommand(List.of(INDEX_SECOND_PERSON));

        // same object -> returns true
        assertTrue(deleteFirstCommand.equals(deleteFirstCommand));

        // same values -> returns true
        DeleteCommand deleteFirstCommandCopy = new DeleteCommand(List.of(INDEX_FIRST_PERSON));
        assertTrue(deleteFirstCommand.equals(deleteFirstCommandCopy));

        // different types -> returns false
        assertFalse(deleteFirstCommand.equals(1));

        // null -> returns false
        assertFalse(deleteFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(deleteFirstCommand.equals(deleteSecondCommand));
    }

    @Test
    public void toStringMethod() {
        List<Index> indexes = List.of(Index.fromOneBased(1), Index.fromOneBased(2));
        DeleteCommand deleteCommand = new DeleteCommand(indexes);
        String expected = DeleteCommand.class.getCanonicalName() + "{targetIndexes=" + indexes + "}";
        assertEquals(expected, deleteCommand.toString());
    }

    /**
     * Updates {@code model}'s filtered list to show no one.
     */
    private void showNoPerson(Model model) {
        model.updateFilteredPersonList(p -> false);

        assertTrue(model.getFilteredPersonList().isEmpty());
    }
}
