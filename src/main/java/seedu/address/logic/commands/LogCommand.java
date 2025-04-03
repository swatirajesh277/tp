package seedu.address.logic.commands;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LOG;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Log;
import seedu.address.model.person.Person;

/**
 * Changes the log of an existing student.
 */
public class LogCommand extends Command {

    public static final String COMMAND_WORD = "log";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the log of the person identified "
            + "by the index number used in the last person listing. "
            + "Existing log will be overwritten by the input.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + PREFIX_LOG + "[LOG]\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_LOG + "TA for CS2103T.";

    public static final String MESSAGE_ARGUMENTS = "Index: %1$d, Log: %2$s";
    public static final String MESSAGE_ADD_LOG_SUCCESS = "Added log to Person: %1$s";
    public static final String MESSAGE_DELETE_LOG_SUCCESS = "Removed log from Person: %1$s";

    private final Index index;
    private final Log log;

    /**
     * @param index of the person in the list to edit the log
     * @param log of the person to be updated to
     */

    public LogCommand(Index index, Log log) {
        requireAllNonNull(index, log);

        this.index = index;
        this.log = log;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        List<Person> lastShownList = model.getSortedPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToEdit = lastShownList.get(index.getZeroBased());
        Person editedPerson = new Person(
                personToEdit.getName(), personToEdit.getId(), personToEdit.getPhone(),
                personToEdit.getEmail(), personToEdit.getProject(),
                personToEdit.getProgress(), log, personToEdit.getTags());
        model.setPerson(personToEdit, editedPerson);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);

        return new CommandResult(generateSuccessMessage(editedPerson));
    }

    /**
     * Generates a command execution success message based on whether
     * the log is added to or removed from
     * {@code personToEdit}.
     */
    private String generateSuccessMessage(Person personToEdit) {
        String message = !log.value.isEmpty() ? MESSAGE_ADD_LOG_SUCCESS : MESSAGE_DELETE_LOG_SUCCESS;
        return String.format(message, personToEdit);
    }

    @Override
    public boolean equals(Object other) {
        //short circuit if same object
        if (other == this) {
            return true;
        }

        if (!(other instanceof LogCommand)) {
            return false;
        }

        //state check
        LogCommand l = (LogCommand) other;
        return index.equals(l.index)
                && log.equals(l.log);
    }
}
