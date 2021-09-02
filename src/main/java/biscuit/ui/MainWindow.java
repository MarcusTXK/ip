package biscuit.ui;

import biscuit.Biscuit;
import biscuit.commands.Command;
import biscuit.exceptions.BiscuitException;
import biscuit.parser.Parser;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

/**
 * Controller for MainWindow. Provides the layout for the other controls.
 */
public class MainWindow extends AnchorPane {
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox dialogContainer;
    @FXML
    private TextField userInput;
    @FXML
    private Button sendButton;

    private Biscuit biscuit;

    private Image userImage = new Image(this.getClass().getResourceAsStream("/images/DaUser.png"));
    private Image biscuitImage = new Image(this.getClass().getResourceAsStream("/images/DaBiscuit.jpg"));

    /**
     * Initialises MainWindow and outputs welcome message
     */
    @FXML
    public void initialize() {
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
        String logo = "\n  \u2588\u2584\u2584\u2003\u2588\u2003\u2588\u2580\u2003\u2588\u2580"
                + "\u2580\u2003\u2588\u2591\u2588\u2003\u2588\u2003\u2580\u2588\u2580\n"
                + "  \u2588\u2584\u2588\u2003\u2588\u2003\u2584\u2588\u2003\u2588\u2584\u2584"
                + "\u2003\u2588\u2584\u2588\u2003\u2588\u2003\u2591\u2588\u2591\n\n";
        dialogContainer.getChildren().addAll(
                DialogBox.getBiscuitDialog(logo + "Woof! I'm Biscuit.\nWhat can I do for you?", biscuitImage)
        );
    }

    public void setBiscuit(Biscuit b) {
        biscuit = b;
    }

    /**
     * Creates two dialog boxes, one echoing user input and the other containing Biscuit's reply and then appends them
     * to the dialog container. Clears the user input after processing.
     */
    @FXML
    private void handleUserInput() {
        String input = "";
        String response = "";
        try {
            input = userInput.getText();
            Command command = Parser.parse(input);
            response = command.execute(biscuit.getTaskList(), biscuit.getUi(), biscuit.getStorage());
        } catch (BiscuitException be) {
            response = be.getMessage();
        } catch (Exception e) {
            response = "Woof! Looks like something went wrong, please try again.";
        } finally {
            dialogContainer.getChildren().addAll(
                    DialogBox.getUserDialog(input, userImage),
                    DialogBox.getBiscuitDialog(response, biscuitImage)
            );
            userInput.clear();
        }

    }
}
