package seedu.address.model.person;

import java.util.List;
import java.util.Set;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.tag.Tag;

/**
 * Tests that a {@code Person}'s {@code Project} matches any of the keywords given.
 */
public class TagContainsKeywordsPredicate implements Predicate<Person> {

    private final List<String> keywords;

    public TagContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Person person) {
        Set<Tag> tags = person.getTags();
        boolean tagContainsKeywords = false;
        for (Tag tag : tags) {
            String tagName = tag.tagName;
            tagContainsKeywords = keywords.stream().anyMatch(
                    keyword -> StringUtil.containsWordIgnoreCase(tagName, keyword));
            if (tagContainsKeywords) {
                break;
            }
        }
        return tagContainsKeywords;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instaceof handles nulls
        if (!(other instanceof TagContainsKeywordsPredicate)) {
            return false;
        }

        TagContainsKeywordsPredicate otherTagContainsKeywordsPredicate = (TagContainsKeywordsPredicate) other;
        return keywords.equals(otherTagContainsKeywordsPredicate.keywords);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("Tag", keywords).toString();
    }

}
