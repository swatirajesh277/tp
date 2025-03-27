package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.PersonBuilder;

public class TagContainsKeywordsPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("first");
        List<String> secondPredicateKeywordList = Arrays.asList("first", "second");

        TagContainsKeywordsPredicate firstPredicate = new TagContainsKeywordsPredicate(firstPredicateKeywordList);
        TagContainsKeywordsPredicate secondPredicate = new TagContainsKeywordsPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same value -> returns true
        TagContainsKeywordsPredicate firstPredicateCopy = new TagContainsKeywordsPredicate(firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false;
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different project -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_tagContainsKeywords_returnsTrue() {
        // One keyword
        TagContainsKeywordsPredicate predicate = new TagContainsKeywordsPredicate(Collections.singletonList("CS2103T"));
        assertTrue(predicate.test(new PersonBuilder().withTags("CS2103T").build()));

        // Multiple keywords
        predicate = new TagContainsKeywordsPredicate(Arrays.asList("CS2103T", "CS2101"));
        assertTrue(predicate.test(new PersonBuilder().withTags("CS2103T", "CS2101").build()));

        // Only one matching keyword
        predicate = new TagContainsKeywordsPredicate(Arrays.asList("CS2103T"));
        assertTrue(predicate.test(new PersonBuilder().withTags("CS2103T", "CS2101").build()));

        // Mixed-case keywords
        predicate = new TagContainsKeywordsPredicate(Arrays.asList("cS2103t", "cS2101"));
        assertTrue(predicate.test(new PersonBuilder().withTags("CS2103T", "CS2101").build()));
    }

    @Test
    public void test_tagDoesNotContainKeywords_returnFalse() {
        // Zero keywords
        TagContainsKeywordsPredicate predicate = new TagContainsKeywordsPredicate(Collections.emptyList());
        assertFalse(predicate.test(new PersonBuilder().withTags("CS2103T").build()));

        // Non-matching keyword
        predicate = new TagContainsKeywordsPredicate(Arrays.asList("CS2105"));
        assertFalse(predicate.test(new PersonBuilder().withTags("CS2103T", "CS2101").build()));

        // Keywords match name, email, phone and project but does not match tag
        predicate = new TagContainsKeywordsPredicate(Arrays.asList("12345", "CS2103T"));
        assertFalse(predicate.test(new PersonBuilder().withName("CS2103T").withPhone("12345")
                .withEmail("CS2103T@email.com").withProject("CS2103T").withTags("Y4").build()));
    }

    @Test
    public void toStringMethod() {
        List<String> keywords = List.of("keyword1", "keyword2");
        TagContainsKeywordsPredicate predicate = new TagContainsKeywordsPredicate(keywords);
        String expected = TagContainsKeywordsPredicate.class.getCanonicalName() + "{Tag=" + keywords + "}";
        assertEquals(expected, predicate.toString());
    }
}
