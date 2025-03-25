package seedu.address.ui;

import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import seedu.address.commons.core.LogsCenter;

/**
 * Controller for clearing confirmation page.
 */
public class ClearConfirmationWindow extends UiPart<Stage> {


    public static final String CLEAR_MESSAGE = "Confirm to clear all records?";

    private static final Logger logger = LogsCenter.getLogger(ClearConfirmationWindow.class);
    private static final String FXML = "ClearConfirmationWindow.fxml";

    @FXML
    private Button yesButton;

    @FXML
    private Button noButton;

    @FXML
    private Label clearMessage;

    /**
     * Creates a new ClearConfirmationWindow.
     *
     * @param root Stage to use as the root of the ClearConfirmation.
     */
    public ClearConfirmationWindow(Stage root) {
        super(FXML, root);
        clearMessage.setText(CLEAR_MESSAGE);
    }

    /**
     * Creates a new ClearConfirmationWindow.
     */
    public ClearConfirmationWindow() {
        this(new Stage());
    }

    /**
     * Shows the clear confirmation window.
     * @throws IllegalStateException
     *     <ul>
     *         <li>
     *             if this method is called on a thread other than the JavaFX Application Thread.
     *         </li>
     *         <li>
     *             if this method is called during animation or layout processing.
     *         </li>
     *         <li>
     *             if this method is called on the primary stage.
     *         </li>
     *         <li>
     *             if {@code dialogStage} is already showing.
     *         </li>
     *     </ul>
     */
    public void show() {
        logger.fine("Showing message to confirm clearing all records.");
        getRoot().show();
        getRoot().centerOnScreen();
    }

    /**
     * Returns true if the clear confirmation window is currently being shown.
     */
    public boolean isShowing() {
        return getRoot().isShowing();
    }

    /**
     * Hides the clear confirmation window.
     */
    public void hide() {
        getRoot().hide();
    }

    /**
     * Focuses on the clear confirmation window.
     */
    public void focus() {
        getRoot().requestFocus();
    }

    @FXML
    private void handleYesButton() {


    }

    @FXML
    private void handleNoButton() {
        assert isShowing() : "Clear confirmation message is not showing when it should be.";
        this.hide();


    }

}
