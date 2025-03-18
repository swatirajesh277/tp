package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.ProgressCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Phone;

/**
 * Parses input arguments and creates a new ProgressCommand object
 */
public class ProgressCommandParser implements Parser<ProgressCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the ProgressCommand
     * and returns a ProgressCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public ProgressCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_PHONE); //TODO: Change all to Progress

        if (argMultimap.getValue(PREFIX_PHONE).isEmpty() || argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ProgressCommand.MESSAGE_USAGE));
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_PHONE);
        Index index = ParserUtil.parseIndex(argMultimap.getPreamble());
        Phone progress = ParserUtil.parsePhone(argMultimap.getValue(PREFIX_PHONE).get());

        return new ProgressCommand(index, progress);
    }
}
