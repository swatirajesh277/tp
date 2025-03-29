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
    public void constructor_invalidProject_throwsIllegalArgumentException() {
        String invalidProject = "";
        assertThrows(IllegalArgumentException.class, () -> new Project(invalidProject));
    }

    @Test
    public void isValidProject() {
        // null project
        assertThrows(NullPointerException.class, () -> Project.isValidProject(null));

        // invalid projects
        assertFalse(Project.isValidProject("")); // empty string
        assertFalse(Project.isValidProject(" ")); // spaces only

        // valid projects
        assertTrue(Project.isValidProject("AlgoMax"));
        assertTrue(Project.isValidProject("-")); // one character
        assertTrue(Project.isValidProject("This is a very long project name")); // long project
    }

    @Test
    public void equals() {
        Project project = new Project("Valid Project");

        // same values -> returns true
        assertTrue(project.equals(new Project("Valid Project")));

        // same object -> returns true
        assertTrue(project.equals(project));

        // null -> returns false
        assertFalse(project.equals(null));

        // different types -> returns false
        assertFalse(project.equals(5.0f));

        // different values -> returns false
        assertFalse(project.equals(new Project("Other Valid Project")));
    }
}
