package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LOG;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.LogCommand;
import seedu.address.model.person.Log;

public class LogCommandParserTest {
    private LogCommandParser parser = new LogCommandParser();
    private final String nonEmptyLog = "Non empty log.";

    @Test
    public void parse_indexSpecified_success() {
        Index index = INDEX_FIRST_PERSON;
        String userInput = index.getOneBased() + " " + PREFIX_LOG + nonEmptyLog;
        LogCommand expectedCommand = new LogCommand(INDEX_FIRST_PERSON, new Log(nonEmptyLog));
        assertParseSuccess(parser, userInput, expectedCommand);

        userInput = index.getOneBased() + " " + PREFIX_LOG;
        expectedCommand = new LogCommand(INDEX_FIRST_PERSON, new Log(""));
        assertParseSuccess(parser, userInput, expectedCommand);

    }

    @Test
    public void parse_missingCompulsoryField_failure() {
        String failMsg = String.format(MESSAGE_INVALID_COMMAND_FORMAT, LogCommand.MESSAGE_USAGE);

        assertParseFailure(parser, LogCommand.COMMAND_WORD, failMsg);
        assertParseFailure(parser, LogCommand.COMMAND_WORD + " " + nonEmptyLog, failMsg);
    }
}
