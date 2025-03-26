package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.SortCommand;

public class SortCommandParserTest {

    private SortCommandParser parser = new SortCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "    ", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                SortCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsSortCommand() {
        // no leading and trailing whitespaces ascending sort
        SortCommand expectedSortCommand =
                new SortCommand(true);
        assertParseSuccess(parser, "asc", expectedSortCommand);

        // no leading and trailing whitespaces descending sort
        expectedSortCommand =
                new SortCommand(false);
        assertParseSuccess(parser, "desc", expectedSortCommand);

        // multiple whitespaces descending sort
        assertParseSuccess(parser, "            desc       ", expectedSortCommand);

        // variable case descending sort
        assertParseSuccess(parser, "               dESc     ", expectedSortCommand);

        // multiple whitespaces ascending sort
        expectedSortCommand = new SortCommand(true);
        assertParseSuccess(parser, "            asc       ", expectedSortCommand);

        // variable case ascending sort
        assertParseSuccess(parser, "               ASc     ", expectedSortCommand);

        // Incorrect parameters
        String expectedMessage = "Sorted order must be asc or desc.";
        assertParseFailure(parser, "upwards", expectedMessage);
    }
}
