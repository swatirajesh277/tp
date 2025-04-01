package seedu.address.testutil;

import seedu.address.model.Profiler;
import seedu.address.model.person.Person;

/**
 * A utility class to help with building Profiler objects.
 * Example usage: <br>
 *     {@code Profiler ab = new ProfilerBuilder().withPerson("John", "Doe").build();}
 */
public class ProfilerBuilder {

    private Profiler profiler;

    public ProfilerBuilder() {
        profiler = new Profiler();
    }

    public ProfilerBuilder(Profiler profiler) {
        this.profiler = profiler;
    }

    /**
     * Adds a new {@code Person} to the {@code Profiler} that we are building.
     */
    public ProfilerBuilder withPerson(Person person) {
        profiler.addPerson(person);
        return this;
    }

    public Profiler build() {
        return profiler;
    }
}
