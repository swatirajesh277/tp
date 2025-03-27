package seedu.address.model.person;

import java.util.function.Predicate;

import seedu.address.commons.util.ToStringBuilder;

/**
 * Tests that a {@code Student}'s {@code Project} matches any of the keywords given.
 */
public class ProjectEqualsTargetPredicate implements Predicate<Person> {

    private final String targetProject;

    public ProjectEqualsTargetPredicate(String targetProject) {
        this.targetProject = targetProject;
    }

    @Override
    public boolean test(Person person) {
        return person.getProject().value.equalsIgnoreCase(targetProject);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ProjectEqualsTargetPredicate otherProjectEqualsTargetPredicate)) {
            return false;
        }

        return targetProject.equalsIgnoreCase(otherProjectEqualsTargetPredicate.targetProject);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("Project", targetProject).toString();
    }
}
