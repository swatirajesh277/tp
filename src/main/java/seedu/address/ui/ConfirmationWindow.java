package seedu.address.ui;

/**
 * Interface for a confirmation window. To create relationship for dependency injection.
 */
public interface ConfirmationWindow {

    boolean showAndWait();
    boolean isShowing();
    void focus();
    void setMessage(String message);
}
