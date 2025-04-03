package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.PROGRESS_AMY;
import static seedu.address.logic.commands.CommandTestUtil.PROGRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PROGRESS_AMY;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showPersonAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalProfiler;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.Profiler;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;
import seedu.address.testutil.PersonBuilder;
import seedu.address.ui.ClearConfirmationWindowStub;

/**
 * Contains integration tests (interaction with the Model) and unit tests for ProgressCommand.
 */
public class ProgressCommandTest {

    private final Model model = new ModelManager(getTypicalProfiler(), new UserPrefs());

    @Test
    public void execute_progressSpecifiedUnfilteredList_success() {
        Index indexLastPerson = Index.fromOneBased(model.getFilteredPersonList().size());
        Person lastPerson = model.getFilteredPersonList().get(indexLastPerson.getZeroBased());
        Person editedPerson = new PersonBuilder(lastPerson).withProgress(VALID_PROGRESS_AMY).build();

        ProgressCommand progressCommand = new ProgressCommand(indexLastPerson, PROGRESS_AMY);

        String expectedMessage = String.format(
                ProgressCommand.MESSAGE_EDIT_PROGRESS_SUCCESS, Messages.format(editedPerson), PROGRESS_AMY);

        Model expectedModel = new ModelManager(new Profiler(model.getProfiler()), new UserPrefs());
        expectedModel.setPerson(lastPerson, editedPerson);

        assertCommandSuccess(progressCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_progressSpecifiedFilteredList_success() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        Person personInFilteredList = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person editedPerson = new PersonBuilder(personInFilteredList).withProgress(VALID_PROGRESS_AMY).build();

        ProgressCommand progressCommand = new ProgressCommand(INDEX_FIRST_PERSON, PROGRESS_AMY);

        String expectedMessage = String.format(
                ProgressCommand.MESSAGE_EDIT_PROGRESS_SUCCESS, Messages.format(editedPerson), PROGRESS_AMY);

        Model expectedModel = new ModelManager(new Profiler(model.getProfiler()), new UserPrefs());
        expectedModel.setPerson(model.getFilteredPersonList().get(0), editedPerson);

        assertCommandSuccess(progressCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidPersonIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        ProgressCommand progressCommand = new ProgressCommand(outOfBoundIndex, PROGRESS_AMY);

        assertCommandFailure(progressCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    /**
     * Progress filtered list where index is larger than size of filtered list,
     * but smaller than size of profiler
     */
    @Test
    public void execute_invalidPersonIndexFilteredList_failure() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);
        Index outOfBoundIndex = INDEX_SECOND_PERSON;
        // ensures that outOfBoundIndex is still in bounds of profiler list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getProfiler().getPersonList().size());

        ProgressCommand progressCommand = new ProgressCommand(outOfBoundIndex, PROGRESS_AMY);

        assertCommandFailure(progressCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        final ProgressCommand standardCommand = new ProgressCommand(INDEX_FIRST_PERSON, PROGRESS_AMY);

        // same values -> returns true
        assertEquals(new ProgressCommand(INDEX_FIRST_PERSON, PROGRESS_AMY), standardCommand);

        // same object -> returns true
        assertEquals(standardCommand, standardCommand);

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand(ClearConfirmationWindowStub.getInstance())));

        // different index -> returns false
        assertNotEquals(new ProgressCommand(INDEX_SECOND_PERSON, PROGRESS_AMY), standardCommand);

        // different descriptor -> returns false
        assertNotEquals(new ProgressCommand(INDEX_FIRST_PERSON, PROGRESS_BOB), standardCommand);
    }

}
