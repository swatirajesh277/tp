package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
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
    }

    @Test
    public void equals() {
        Id idOne = new Id("A1234567Z");
        Id idTwo = new Id("A1234567Z");

        assertTrue(idOne.equals(idTwo)); // Test for equality on same value
        assertTrue(idOne.equals(idOne)); // Test same object reference

        Id idDifferent = new Id("A7654321X");
        assertFalse(idOne.equals(idDifferent)); // Test different values
    }
}
