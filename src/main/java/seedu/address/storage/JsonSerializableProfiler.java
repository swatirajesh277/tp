package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.Profiler;
import seedu.address.model.ReadOnlyProfiler;
import seedu.address.model.person.Person;

/**
 * An Immutable Prof-iler data that is serializable to JSON format.
 */
@JsonRootName(value = "prof-iler")
class JsonSerializableProfiler {

    public static final String MESSAGE_DUPLICATE_PERSON = "Students list contains duplicate student(s).";

    private final List<JsonAdaptedPerson> persons = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializableProfiler} with the given persons.
     */
    @JsonCreator
    public JsonSerializableProfiler(@JsonProperty("persons") List<JsonAdaptedPerson> persons) {
        this.persons.addAll(persons);
    }

    /**
     * Converts a given {@code ReadOnlyProfiler} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableProfiler}.
     */
    public JsonSerializableProfiler(ReadOnlyProfiler source) {
        persons.addAll(source.getPersonList().stream().map(JsonAdaptedPerson::new).collect(Collectors.toList()));
    }

    /**
     * Converts this address book into the model's {@code Profiler} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public Profiler toModelType() throws IllegalValueException {
        Profiler profiler = new Profiler();
        for (JsonAdaptedPerson jsonAdaptedPerson : persons) {
            Person person = jsonAdaptedPerson.toModelType();
            if (profiler.hasPerson(person)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_PERSON);
            }
            profiler.addPerson(person);
        }
        return profiler;
    }

}
