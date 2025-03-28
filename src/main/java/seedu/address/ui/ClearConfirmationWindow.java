package seedu.address.ui;

import java.util.logging.Logger;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import seedu.address.commons.core.LogsCenter;

/**
 * Controller for clearing confirmation page.
 */
public class ClearConfirmationWindow extends UiPart<Stage> implements ConfirmationWindow {

    public static final String CLEAR_MESSAGE = "Confirm to clear all records?";

    private static final Logger logger = LogsCenter.getLogger(ClearConfirmationWindow.class);
    private static final String FXML = "ClearConfirmationWindow.fxml";

    private static ClearConfirmationWindow instance; // Singleton instance

    @FXML
    private Button noButton;

    @FXML
    private Label clearMessage;

    /**
     * Stores the user's response.
     */
    private boolean isConfirmed = false;

    /**
     * Creates a new ClearConfirmationWindow.
     *
     * @param root Stage to use as the root of the ClearConfirmation.
     */
    private ClearConfirmationWindow(Stage root) {
        super(FXML, root);
        assert root != null;
        clearMessage.setText(CLEAR_MESSAGE);
    }

    /**
     * Creates a new ClearConfirmationWindow.
     */
    private ClearConfirmationWindow() {
        this(new Stage());
    }

    /**
     * Returns the single instance of ClearConfirmationWindow.
     */
    public static ClearConfirmationWindow getInstance() {
        if (instance == null) {
            instance = new ClearConfirmationWindow();
        }
        return instance;
    }

    /**
     * Shows the clear confirmation window and waits for user response.
     *
     * @throws IllegalStateException <ul>
     *                               <li>
     *                               if this method is called on a thread other than the JavaFX Application Thread.
     *                               </li>
     *                               <li>
     *                               if this method is called during animation or layout processing.
     *                               </li>
     *                               <li>
     *                               if this method is called on the primary stage.
     *                               </li>
     *                               <li>
     *                               if {@code dialogStage} is already showing.
     *                               </li>
     *                               </ul>
     */
    @Override
    public boolean showAndWait() {
        logger.fine("Showing message to confirm clearing all records.");

        Platform.runLater(() -> noButton.requestFocus());

        getRoot().showAndWait();
        getRoot().centerOnScreen();
        return isConfirmed;
    }

    /**
     * Returns true if the clear confirmation window is currently being shown.
     */
    @Override
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
    @Override
    public void focus() {
        getRoot().requestFocus();
    }


    @FXML
    private void handleYesButton() {
        isConfirmed = true;
        assert getRoot() != null;
        this.getRoot().close();
    }

    @FXML
    private void handleNoButton() {
        isConfirmed = false;
        assert getRoot() != null;
        this.getRoot().close();
    }


}
