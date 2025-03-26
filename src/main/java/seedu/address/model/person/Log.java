package seedu.address.model.person;

import static java.util.Objects.requireNonNull;

/**
 * Represents a Person's log.
 * Guarantees: immutable; is always valid
 */
public class Log {
    public final String value;

    /**
     * Constructs a {@code Log}.
     *
     * @param log A valid log.
     */
    public Log(String log) {
        requireNonNull(log);
        value = log;
    }
    @Override
    public String toString() {
        return value;
    }
    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof Log
                && value.equals(((Log) other).value));
    }
    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
