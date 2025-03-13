package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class ProjectTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Project(null));
    }

    @Test
    public void constructor_invalidAddress_throwsIllegalArgumentException() {
        String invalidAddress = "";
        assertThrows(IllegalArgumentException.class, () -> new Project(invalidAddress));
    }

    @Test
    public void isValidProject() {
        // null address
        assertThrows(NullPointerException.class, () -> Project.isValidProject(null));

        // invalid addresses
        assertFalse(Project.isValidProject("")); // empty string
        assertFalse(Project.isValidProject(" ")); // spaces only

        // valid addresses
        assertTrue(Project.isValidProject("Blk 456, Den Road, #01-355"));
        assertTrue(Project.isValidProject("-")); // one character
        assertTrue(Project.isValidProject("Leng Inc; 1234 Market St; San Francisco CA 2349879; USA")); // long address
    }

    @Test
    public void equals() {
        Project project = new Project("Valid Address");

        // same values -> returns true
        assertTrue(project.equals(new Project("Valid Address")));

        // same object -> returns true
        assertTrue(project.equals(project));

        // null -> returns false
        assertFalse(project.equals(null));

        // different types -> returns false
        assertFalse(project.equals(5.0f));

        // different values -> returns false
        assertFalse(project.equals(new Project("Other Valid Address")));
    }
}
