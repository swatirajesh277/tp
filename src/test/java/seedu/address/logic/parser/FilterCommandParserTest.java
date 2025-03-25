package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Arrays;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.FilterCommand;
import seedu.address.model.person.ProjectContainsKeywordsPredicate;
import seedu.address.model.person.TagContainsKeywordsPredicate;

public class FilterCommandParserTest {
    private FilterCommandParser parser = new FilterCommandParser();

    @Test
    public void parse_emptyArgs_throwsParseException() {
        // empty input
        assertParseFailure(parser, "    ", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                FilterCommand.MESSAGE_USAGE));

        // missing prefix
        assertParseFailure(parser, "filter test", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                FilterCommand.MESSAGE_USAGE));

        // missing value for project
        assertParseFailure(parser, "filter pr/ ", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                FilterCommand.MESSAGE_USAGE));

        // missing value for tag
        assertParseFailure(parser, "filter t/ ", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                FilterCommand.MESSAGE_USAGE));

        // having both prefix for tag and project
        assertParseFailure(parser, "filter t/ pr/ ", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                FilterCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgsForProject_returnsFilterCommand() {
        FilterCommand expectedFilterCommand =
                new FilterCommand(new ProjectContainsKeywordsPredicate(Arrays.asList("Project", "Profiler")));
        assertParseSuccess(parser, " pr/ Project Profiler", expectedFilterCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " pr/ \n Project \n \t Profiler \t", expectedFilterCommand);
    }

    @Test
    public void parse_validArgsForTag_returnsFilterCommand() {
        FilterCommand expectedFilterCommand =
                new FilterCommand(new TagContainsKeywordsPredicate(Arrays.asList("CS2103T", "CS2101")));
        assertParseSuccess(parser, " t/ CS2103T CS2101", expectedFilterCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " t/ \n CS2103T \n \t CS2101 \t", expectedFilterCommand);
    }
}
