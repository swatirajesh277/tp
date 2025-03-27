package seedu.address.logic.parser;

import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.ClearCommand;
import seedu.address.model.person.ProjectEqualsTargetPredicate;
import seedu.address.ui.ClearConfirmationWindowStub;

public class ClearCommandParserTest {
    private final ClearConfirmationWindowStub stub = ClearConfirmationWindowStub.getInstance();
    private final ClearCommandParser parser = new ClearCommandParser(stub);

    @Test
    public void parse_emptyArgs_returnsClearCommand() {
        assertParseSuccess(parser, "    ", new ClearCommand(stub));
    }

    @Test
    public void parse_validArgs_returnsClearCommand() {
        ClearCommand expectedClearCommand = new ClearCommand(
                new ProjectEqualsTargetPredicate("Prof-iler"), stub);
        assertParseSuccess(parser, "Prof-iler", expectedClearCommand);

        // whitespace before and after project name
        assertParseSuccess(parser, "\n Prof-iler \t", expectedClearCommand);
    }
}
