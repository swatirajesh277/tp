package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_LOG_BOB;
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
import seedu.address.model.person.Log;
import seedu.address.model.person.Person;
import seedu.address.testutil.PersonBuilder;


/**
 * Contains integration tests and unit tests for Log Command.
 */
public class LogCommandTest {
    private static final String LOG_RANDOM = "Some random log";
    private Model model = new ModelManager(getTypicalProfiler(), new UserPrefs());
    @Test
    public void execute_addLogUnfilteredList_success() {
        Person firstPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person editedPerson = new PersonBuilder(firstPerson).withLog(LOG_RANDOM).build();

        LogCommand logCommand = new LogCommand(INDEX_FIRST_PERSON, new Log(editedPerson.getLog().value));

        String expectedMessage = LogCommand.MESSAGE_ADD_LOG_SUCCESS + Messages.format(editedPerson);

        Model expectedModel = new ModelManager(new Profiler(model.getProfiler()), new UserPrefs());
        expectedModel.setPerson(firstPerson, editedPerson);

        assertCommandSuccess(logCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_deleteLogUnfilteredList_success() {
        Person firstPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person editedPerson = new PersonBuilder(firstPerson).withLog("").build();

        LogCommand logCommand = new LogCommand(INDEX_FIRST_PERSON, new Log(editedPerson.getLog().value));

        String expectedMessage = LogCommand.MESSAGE_DELETE_LOG_SUCCESS + Messages.format(editedPerson);

        Model expectedModel = new ModelManager(new Profiler(model.getProfiler()), new UserPrefs());
        expectedModel.setPerson(firstPerson, editedPerson);

        assertCommandSuccess(logCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_filteredList_success() {
        Person firstPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person editedPerson = new PersonBuilder(model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased()))
                .withLog(LOG_RANDOM).build();

        LogCommand logCommand = new LogCommand(INDEX_FIRST_PERSON, new Log(editedPerson.getLog().value));

        String expectedMessage = LogCommand.MESSAGE_ADD_LOG_SUCCESS + Messages.format(editedPerson);

        Model expectedModel = new ModelManager(new Profiler(model.getProfiler()), new UserPrefs());
        expectedModel.setPerson(firstPerson, editedPerson);

        assertCommandSuccess(logCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidPersonIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        LogCommand logCommand = new LogCommand(outOfBoundIndex, new Log(VALID_LOG_BOB));

        assertCommandFailure(logCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_invalidPersonIndexFilteredList_failure() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);
        Index outOfBoundIndex = INDEX_SECOND_PERSON;

        assertTrue(outOfBoundIndex.getZeroBased() < model.getProfiler().getPersonList().size());

        LogCommand logCommand = new LogCommand(outOfBoundIndex, new Log(VALID_LOG_BOB));
        assertCommandFailure(logCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }
}
