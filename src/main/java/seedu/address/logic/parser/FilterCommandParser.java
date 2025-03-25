package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PROJECT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Arrays;
import java.util.function.Predicate;

import seedu.address.logic.commands.FilterCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.Person;
import seedu.address.model.person.ProjectContainsKeywordsPredicate;
import seedu.address.model.person.TagContainsKeywordsPredicate;

/**
 * Parses input arguments and creates a new FilterCommand object
 */
public class FilterCommandParser implements Parser<FilterCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FilterCommand
     * and returns a FilterCommand object for execution.
     * @param args the input argument
     * @return  a FilterCommand object for execution
     * @throws ParseException if the user input does not conform the expected format
     */
    public FilterCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argumentMultimap = ArgumentTokenizer.tokenize(args, PREFIX_PROJECT, PREFIX_TAG);


        if (argumentMultimap.getAllValues(PREFIX_TAG).isEmpty()
            && argumentMultimap.getAllValues(PREFIX_PROJECT).isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FilterCommand.MESSAGE_USAGE));
        }

        argumentMultimap.verifyNoDuplicatePrefixesFor(PREFIX_PROJECT, PREFIX_TAG);

        String[] projectKeywords = new String[0];
        Predicate<Person> filteredStudent = null;
        if (argumentMultimap.getValue(PREFIX_PROJECT).isPresent()) {
            if (argumentMultimap.getValue(PREFIX_PROJECT).isEmpty()
                || !argumentMultimap.getPreamble().isEmpty()
                || argumentMultimap.getValue(PREFIX_PROJECT).get().isEmpty()) {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FilterCommand.MESSAGE_USAGE));
            }
            projectKeywords = argumentMultimap.getValue(PREFIX_PROJECT).get().split("\\s+");
            filteredStudent = new ProjectContainsKeywordsPredicate(Arrays.asList(projectKeywords));
        }

        if (argumentMultimap.getValue(PREFIX_TAG).isPresent()) {
            if (argumentMultimap.getValue(PREFIX_TAG).isEmpty()
                    || !argumentMultimap.getPreamble().isEmpty()
                    || argumentMultimap.getValue(PREFIX_TAG).get().isEmpty()) {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FilterCommand.MESSAGE_USAGE));
            }
            projectKeywords = argumentMultimap.getValue(PREFIX_TAG).get().split("\\s+");
            filteredStudent = new TagContainsKeywordsPredicate(Arrays.asList(projectKeywords));
        }
        
        return new FilterCommand(filteredStudent);
    }
}

