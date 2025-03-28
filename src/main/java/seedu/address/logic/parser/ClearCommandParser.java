package seedu.address.logic.parser;

import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.ProjectEqualsTargetPredicate;
import seedu.address.ui.ConfirmationWindow;
import seedu.address.ui.ConfirmationWindowFactory;

/**
 * Parses input arguments and creates a new FilterCommand object
 */
public class ClearCommandParser implements Parser<ClearCommand> {

    private final ConfirmationWindow confirmationWindow;

    /**
     * Used in normal runtime.
     */
    public ClearCommandParser() {
        this.confirmationWindow = ConfirmationWindowFactory.getConfirmationWindow();
        assert confirmationWindow != null;
    }

    /**
     * Used in testing, to give a stub window.
     * @param confirmationWindow The stub window
     */
    public ClearCommandParser(ConfirmationWindow confirmationWindow) {
        this.confirmationWindow = confirmationWindow;
        assert confirmationWindow != null;
    }

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
            return new ClearCommand(confirmationWindow);
        }
        return new ClearCommand(new ProjectEqualsTargetPredicate(trimmedArgs), confirmationWindow);
    }
}

