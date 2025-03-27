package seedu.address.testutil;

import seedu.address.ui.ConfirmationWindow;
import seedu.address.ui.ConfirmationWindowFactory.ConfirmationWindowProvider;

/**
 * A test provider that returns a stub confirmation window
 */
public class TestConfirmationWindowProvider implements ConfirmationWindowProvider {
    private final ConfirmationWindow stub;

    public TestConfirmationWindowProvider(ConfirmationWindow stub) {
        this.stub = stub;
    }

    @Override
    public ConfirmationWindow getConfirmationWindow() {
        return stub;
    }
}
