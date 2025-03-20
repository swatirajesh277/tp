package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_PROGRESS_DESC;
import static seedu.address.logic.commands.CommandTestUtil.PROGRESS_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.PROGRESS_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PROGRESS_AMY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PROGRESS;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_THIRD_PERSON;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.ProgressCommand;
import seedu.address.model.person.Progress;

public class ProgressCommandParserTest {

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, ProgressCommand.MESSAGE_USAGE);

    private final ProgressCommandParser parser = new ProgressCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no index specified
        assertParseFailure(parser, PROGRESS_DESC_AMY, MESSAGE_INVALID_FORMAT);

        // no field specified
        assertParseFailure(parser, "1", MESSAGE_INVALID_FORMAT);

        // no index and no field specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidArguments_failure() {
        // negative index
        assertParseFailure(parser, "-5" + PROGRESS_DESC_AMY, MESSAGE_INVALID_FORMAT);

        // zero index
        assertParseFailure(parser, "0" + PROGRESS_DESC_AMY, MESSAGE_INVALID_FORMAT);

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, "1 some random string", MESSAGE_INVALID_FORMAT);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, "1 i/ string", MESSAGE_INVALID_FORMAT);

        // invalid progress
        assertParseFailure(parser, "1" + INVALID_PROGRESS_DESC, Progress.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_allFieldsSpecified_success() {
        Index targetIndex = INDEX_SECOND_PERSON;
        Progress targetProgress = new Progress(VALID_PROGRESS_AMY);
        String userInput = targetIndex.getOneBased() + PROGRESS_DESC_AMY;
        ProgressCommand expectedCommand = new ProgressCommand(targetIndex, targetProgress);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_multipleRepeatedFields_failure() {
        // multiple valid values
        Index targetIndex = INDEX_THIRD_PERSON;
        String userInput = targetIndex.getOneBased() + PROGRESS_DESC_AMY + PROGRESS_DESC_BOB;
        String duplicateProgressErrorMessage = Messages.getErrorMessageForDuplicatePrefixes(PREFIX_PROGRESS);
        assertParseFailure(parser, userInput, duplicateProgressErrorMessage);

        // invalid followed by valid
        userInput = targetIndex.getOneBased() + INVALID_PROGRESS_DESC + PROGRESS_DESC_BOB;
        assertParseFailure(parser, userInput, duplicateProgressErrorMessage);

        // valid followed by invalid
        userInput = targetIndex.getOneBased() + PROGRESS_DESC_AMY + INVALID_PROGRESS_DESC;
        assertParseFailure(parser, userInput, duplicateProgressErrorMessage);
    }
}
