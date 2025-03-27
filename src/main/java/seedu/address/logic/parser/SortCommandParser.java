package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.logic.commands.SortCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and create a new SortCommand object
 */
public class SortCommandParser implements Parser<SortCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the SortCommand
     * and returns a SortCommand object for execution.
     * @param args the input argument
     * @return a SortCommand object for execution
     * @throws ParseException if the user input does not conform the expected format
     */
    public SortCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE)
            );
        }

        boolean isAscending;
        if (trimmedArgs.equalsIgnoreCase("asc")) {
            isAscending = true;
        } else if (trimmedArgs.equalsIgnoreCase("desc")) {
            isAscending = false;
        } else {
            throw new ParseException("Sorted order must be asc or desc.");
        }

        return new SortCommand(isAscending);
    }
}
