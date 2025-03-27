package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_LOG_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_LOG_BOB;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;

import org.junit.jupiter.api.Test;

import seedu.address.model.person.Log;


/**
 * Contains integration tests and unit tests for Log Command.
 */
public class LogCommandTest {
    private static final String LOG_RANDOM = "Some random log";
    @Test
    public void equals() {
        final LogCommand exampleCommand = new LogCommand(INDEX_FIRST_PERSON, new Log(VALID_LOG_AMY));
        //same values returns true
        LogCommand commandWithSameValues = new LogCommand(INDEX_FIRST_PERSON, new Log(VALID_LOG_AMY));
        assertTrue(exampleCommand.equals(commandWithSameValues));
        //same object returns true
        assertTrue(exampleCommand.equals(exampleCommand));
        //null returns false
        assertFalse(exampleCommand.equals(null));
        //diff index returns false
        assertFalse(exampleCommand.equals(new LogCommand(INDEX_SECOND_PERSON, new Log(VALID_LOG_AMY))));
        //diff log returns false
        assertFalse(exampleCommand.equals(new LogCommand(INDEX_FIRST_PERSON, new Log(VALID_LOG_BOB))));
    }
}
