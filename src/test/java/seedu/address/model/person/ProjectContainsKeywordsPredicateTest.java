package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.PersonBuilder;

public class ProjectContainsKeywordsPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("first");
        List<String> secondPredicateKeywordList = Arrays.asList("first", "second");

        ProjectContainsKeywordsPredicate firstPredicate = new
                ProjectContainsKeywordsPredicate(firstPredicateKeywordList);
        ProjectContainsKeywordsPredicate secondPredicate = new
                ProjectContainsKeywordsPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same value -> returns true
        ProjectContainsKeywordsPredicate firstPredicateCopy = new
                ProjectContainsKeywordsPredicate(firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false;
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different project -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_projectContainsKeywords_returnsTrue() {
        // One keyword
        ProjectContainsKeywordsPredicate predicate = new
                ProjectContainsKeywordsPredicate(Collections.singletonList("Prof-iler"));
        assertTrue(predicate.test(new PersonBuilder().withProject("Prof-iler").build()));

        // Multiple keywords
        predicate = new ProjectContainsKeywordsPredicate(Arrays.asList("Project", "Rex"));
        assertTrue(predicate.test(new PersonBuilder().withProject("Project Rex").build()));

        // Only one matching keyword
        predicate = new ProjectContainsKeywordsPredicate(Arrays.asList("Project", "Bob"));
        assertTrue(predicate.test(new PersonBuilder().withProject("Bob Builder").build()));

        // Mix-case keywords
        predicate = new ProjectContainsKeywordsPredicate(Arrays.asList("pRoJECt", "bOB"));
        assertTrue(predicate.test(new PersonBuilder().withProject("Alice Bob").build()));
    }

    @Test
    public void test_projectDoesNotContainKeywords_returnFalse() {
        // Zero keywords
        ProjectContainsKeywordsPredicate predicate = new
                ProjectContainsKeywordsPredicate(Collections.emptyList());
        assertFalse(predicate.test(new PersonBuilder().withProject("Prof-iler").build()));

        // Non-matching keyword
        predicate = new ProjectContainsKeywordsPredicate(Arrays.asList("Project", "Rex"));
        assertFalse(predicate.test(new PersonBuilder().withProject("Prof-iler").build()));

        // Keywords match name,phone, email but does not match project
        predicate = new ProjectContainsKeywordsPredicate(Arrays.asList("Project", "12345"));
        assertFalse(predicate.test(new PersonBuilder().withName("Project").withPhone("12345")
                .withEmail("Project@email.com").withProject("Bob").build()));
    }

    @Test
    public void toStringMethod() {
        List<String> keywords = List.of("keyword1", "keyword2");
        ProjectContainsKeywordsPredicate predicate = new ProjectContainsKeywordsPredicate(keywords);

        String expected = ProjectContainsKeywordsPredicate.class.getCanonicalName() + "{Project=" + keywords + "}";
        assertEquals(expected, predicate.toString());
    }
}
