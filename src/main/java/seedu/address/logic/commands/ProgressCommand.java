package seedu.address.logic.commands;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;

/**
 * Updates the project progress of an existing person.
 */
public class ProgressCommand extends Command {

    public static final String COMMAND_WORD = "progress";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Edits the progress of the person identified "
            + "by the index number used in the displayed person list. "
            + "Existing progress will be overwritten by the input.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + PREFIX_PHONE + "PROGRESS\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_PHONE + "45";

    public static final String MESSAGE_EDIT_PROGRESS_SUCCESS = "Updated progress of Person: %1$s";

    private final Index index;
    private final Phone progress; //TODO: Change to Progress

    /**
     * @param index index of the person in the filtered person list
     * @param progress progress of the person to be updated to
     */
    public ProgressCommand(Index index, Phone progress) {
        requireAllNonNull(index, progress);

        this.index = index;
        this.progress = progress;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        List<Person> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToEdit = lastShownList.get(index.getZeroBased());
        Person editedPerson = new Person(
                personToEdit.getName(), progress, personToEdit.getEmail(),
                personToEdit.getProject(), personToEdit.getTags());

        model.setPerson(personToEdit, editedPerson);
        model.updateFilteredPersonList(Model.PREDICATE_SHOW_ALL_PERSONS);

        return new CommandResult(String.format(MESSAGE_EDIT_PROGRESS_SUCCESS, Messages.format(editedPerson)));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof ProgressCommand otherProgressCommand)) {
            return false;
        }

        return index.equals(otherProgressCommand.index)
                && progress.equals(otherProgressCommand.progress);
    }
}
