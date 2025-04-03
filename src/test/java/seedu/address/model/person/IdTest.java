package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class IdTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Id(null));
    }

    @Test
    public void constructor_invalidId_throwsIllegalArgumentException() {
        String invalidId = "";
        assertThrows(IllegalArgumentException.class, () -> new Id(invalidId));
    }

    @Test
    public void isValidId() {
        // null id
        assertThrows(NullPointerException.class, () -> Id.isValidId(null));

        // invalid id
        assertFalse(Id.isValidId("")); // empty string
        assertFalse(Id.isValidId("F0227716K")); // should start with A
        assertFalse(Id.isValidId("A038K")); // should contain 7 digits
        assertFalse(Id.isValidId("A0238472*")); // should end with an alphabet

        // valid Id
        assertTrue(Id.isValidId("A0282883E")); // starts with A, followed by 7 digits and ends with an alphabet
        assertTrue(Id.isValidId("A0383847D"));
        assertTrue(Id.isValidId("A0002732B"));
        assertTrue(Id.isValidId("a0002732b")); // starts and ends with lowercase
    }

    @Test
    public void equals() {
        Id id = new Id("A1234567K");
        Id sameId = new Id("A1234567K");
        Id sameIdLowercase = new Id("a1234567k");
        Id differentId = new Id("A7654321H");

        // same values -> returns true
        assertEquals(id, sameId);

        // same case-insensitive values -> returns true
        assertEquals(id, sameIdLowercase);

        // same object -> returns true
        assertEquals(id, id);

        // null -> returns false
        assertNotEquals(null, id);

        // different types -> returns false
        assertNotEquals(5.0f, id);

        // different values -> returns false
        assertNotEquals(id, differentId);
    }

    @Test
    public void hashCode_consistency() {
        Id id = new Id("A1234567K");
        assertEquals(id.hashCode(), id.hashCode());
    }

    @Test
    public void hashCode_differentObjects() {
        Id id1 = new Id("A1234567K");
        Id id2 = new Id("A7654321H");

        assertNotEquals(id1.hashCode(), id2.hashCode());
    }
}
