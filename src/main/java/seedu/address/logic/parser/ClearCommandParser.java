package seedu.address.logic.parser;

import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.ProjectEqualsTargetPredicate;

/**
 * Parses input arguments and creates a new FilterCommand object
 */
public class ClearCommandParser implements Parser<ClearCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FilterCommand
     * and returns a FilterCommand object for execution.
     * @param args the input argument
     * @return  a FilterCommand object for execution
     * @throws ParseException if the user input does not conform the expected format
     */
    public ClearCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            return new ClearCommand();
        }
        return new ClearCommand(new ProjectEqualsTargetPredicate(trimmedArgs));
    }
}

