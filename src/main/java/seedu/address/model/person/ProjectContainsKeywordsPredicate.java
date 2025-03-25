package seedu.address.model.person;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;
import seedu.address.commons.util.ToStringBuilder;

/**
 * Tests that a {@code Student}'s {@code Project} matches any of the keywords given.
 */
public class ProjectContainsKeywordsPredicate implements Predicate<Person> {

    private final List<String> keywords;

    public ProjectContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Person person) {
        return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(person.getProject().value, keyword));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instaceof handles nulls
        if (!(other instanceof ProjectContainsKeywordsPredicate)) {
            return false;
        }

        ProjectContainsKeywordsPredicate otherProjectContainsKeywordsPredicate = (ProjectContainsKeywordsPredicate) other;
        return keywords.equals(otherProjectContainsKeywordsPredicate.keywords);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("Project", keywords).toString();
    }
}
