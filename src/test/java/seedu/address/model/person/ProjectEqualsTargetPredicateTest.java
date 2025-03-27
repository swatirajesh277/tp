package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.PersonBuilder;

public class ProjectEqualsTargetPredicateTest {

    @Test
    public void equals() {
        String firstPredicateKeyword = "first";
        String secondPredicateKeyword = "second";

        ProjectEqualsTargetPredicate firstPredicate = new ProjectEqualsTargetPredicate(firstPredicateKeyword);
        ProjectEqualsTargetPredicate secondPredicate = new ProjectEqualsTargetPredicate(secondPredicateKeyword);

        // same object -> returns true
        assertEquals(firstPredicate, firstPredicate);

        // same values -> returns true
        ProjectEqualsTargetPredicate firstPredicateCopy = new ProjectEqualsTargetPredicate(firstPredicateKeyword);
        assertEquals(firstPredicate, firstPredicateCopy);

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different project -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_projectEqualsKeyword_returnsTrue() {
        ProjectEqualsTargetPredicate predicate = new ProjectEqualsTargetPredicate("Prof-iler");

        // Exact match
        assertTrue(predicate.test(new PersonBuilder().withProject("Prof-iler").build()));

        // Mixed-case keyword
        assertTrue(predicate.test(new PersonBuilder().withProject("pRoF-ilER").build()));
    }

    @Test
    public void test_projectNotEqualsKeyword_returnsFalse() {
        ProjectEqualsTargetPredicate predicate = new ProjectEqualsTargetPredicate("Prof-iler");

        // Project contains keyword
        assertFalse(predicate.test(new PersonBuilder().withProject("Project Prof-iler").build()));

        // Keyword contains Project
        assertFalse(predicate.test(new PersonBuilder().withProject("Prof").build()));

        // Keyword matches name but not project
        predicate = new ProjectEqualsTargetPredicate("Alice");
        assertFalse(predicate.test(new PersonBuilder()
                .withName("Alice").withProject("Prof-iler").build()));
    }

    @Test
    public void toStringMethod() {
        String project = "Prof-iler";
        ProjectEqualsTargetPredicate predicate = new ProjectEqualsTargetPredicate(project);

        String expected = ProjectEqualsTargetPredicate.class.getCanonicalName() + "{Project=" + project + "}";
        assertEquals(expected, predicate.toString());
    }
}
