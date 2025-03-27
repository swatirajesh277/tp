package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class LogTest {
    @Test
    public void equals() {
        Log log = new Log("Hi");
        // same object -> returns true
        assertTrue(log.equals(log));

        // same values -> returns true
        Log logCopy = new Log(log.value);
        assertTrue(log.equals(logCopy));

        // null -> returns false
        assertFalse(log.equals(null));

        // different types -> returns false
        assertFalse(log.equals(1));

        // different values -> returns false
        Log newLog = new Log("bye");
        assertFalse(log.equals(newLog));
    }
}
