package application;

import java.io.FileInputStream;
import java.util.List;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

/**
 * Main class for GUI for Milk Weights ATEAM project
 *
 * @author Matthew Woo, Param Bhandare, Russell Cheng
 */
public class Main extends Application {
    // store any command-line arguments that were entered.
    // NOTE: this.getParameters().getRaw() will get these also
    private List<String> args;

    private static final int WINDOW_WIDTH = 650;
    private static final int WINDOW_HEIGHT = 470;
    private static final String APP_TITLE = "Milk Weights";
    
    /**
     * Starts the program and adds all components to the screen
     * @param primaryStage
     * @throws Exception
     */
    @Override
    public void start(Stage primaryStage) throws Exception {

        VBox root = new VBox();
        
        root.setAlignment(Pos.CENTER);

        // Add the vertical box to the center of the root pane
        Label titleLabel = new Label("Welcome to Milk Weights");
        titleLabel.setTextAlignment(TextAlignment.CENTER);
        titleLabel.setFont(new Font("Cambria", 40));
        
        Label promptLabel = new Label("Please upload a data file to begin parsing:");
        promptLabel.setTextAlignment(TextAlignment.CENTER);
        promptLabel.setFont(new Font("Cambria", 28));
        promptLabel.setMaxWidth(350);
        promptLabel.setWrapText(true);
        Button uploadButton = new Button("Upload Here");
        Region spacer = new Region();
        spacer.setMinHeight(100);
        root.getChildren().add(titleLabel);
        root.getChildren().add(spacer);
        root.getChildren().add(promptLabel);
        root.getChildren().add(uploadButton);
        Scene mainScene = new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT);

        // Add the stuff and set the primary stage
            primaryStage.setTitle(APP_TITLE);
            primaryStage.setScene(mainScene);
            primaryStage.show();
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
           launch(args);
    }
}
