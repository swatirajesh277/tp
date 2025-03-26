package seedu.address.logic.parser;

import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.ClearCommand;
import seedu.address.model.person.ProjectEqualsTargetPredicate;

public class ClearCommandParserTest {
    private final ClearCommandParser parser = new ClearCommandParser();

    @Test
    public void parse_emptyArgs_returnsClearCommand() {
        assertParseSuccess(parser, "    ", new ClearCommand());
    }

    @Test
    public void parse_validArgs_returnsClearCommand() {
        ClearCommand expectedClearCommand = new ClearCommand(new ProjectEqualsTargetPredicate("Prof-iler"));
        assertParseSuccess(parser, "Prof-iler", expectedClearCommand);

        // whitespace before and after project name
        assertParseSuccess(parser, "\n Prof-iler \t", expectedClearCommand);
    }
}
