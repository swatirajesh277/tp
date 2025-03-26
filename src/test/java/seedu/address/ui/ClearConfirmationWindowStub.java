package seedu.address.ui;

/**
 * Stub class for testing ClearCommand class, as a Confirmation Window is implemented.
 */
public class ClearConfirmationWindowStub implements ConfirmationWindow {
    private static ClearConfirmationWindowStub instance; // Singleton instance

    private boolean confirmationResult;
    private boolean isShowing; // Default behavior: window is not open
    private boolean wasFocusCalled;

    private ClearConfirmationWindowStub() {
    }

    /**
     * Sets simulated user result.
     *
     * @param result true if confirmed, false if cancelled.
     */
    public void setConfirmationResult(boolean result) {
        this.confirmationResult = result;
    }

    /**
     * Returns the single instance of ClearConfirmationWindowStub.
     */
    public static ClearConfirmationWindowStub getInstance() {
        if (instance == null) {
            instance = new ClearConfirmationWindowStub();
            instance.resetValues();
        }
        return instance;
    }

    /**
     * Sets the result that showAndWait() should return
     */
    @Override
    public boolean showAndWait() {
        return confirmationResult;
    }
    
    @Override
    public boolean isShowing() {
        return isShowing;
    }

    public void setShowing(boolean isShowing) {
        this.isShowing = isShowing;
    }

    @Override
    public void focus() {
        wasFocusCalled = true;
    }

    public boolean wasFocusCalled() {
        return this.wasFocusCalled;
    }

    public void setWasFocusCalled(boolean wasFocusCalled) {
        this.wasFocusCalled = wasFocusCalled;
    }

    /**
     * Resets values to original to simulate fresh instantiation
     */
    public void resetValues() {
        confirmationResult = false;
        isShowing = false;
    }

}
