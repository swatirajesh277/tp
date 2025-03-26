package seedu.address.logic.commands;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LOG;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Log;

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
        throw new CommandException(String.format(MESSAGE_ARGUMENTS, index.getOneBased(), log));
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
