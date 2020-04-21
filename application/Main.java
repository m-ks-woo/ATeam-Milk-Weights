package application;

import java.io.File;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.stage.FileChooser;
import javafx.stage.Popup;
import javafx.stage.Stage;

/**
 * Main class for GUI for Milk Weights ATEAM project
 *
 * @author Matthew Woo, Param Bhandare, Russell Cheng
 */
public class Main extends Application {

    private static final int WINDOW_WIDTH = 650;
    private static final int WINDOW_HEIGHT = 470;
    private static final String APP_TITLE = "Milk Weights";
    private static FarmTable farmTable = new FarmTable();
    
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
      FileChooser fileChooser = new FileChooser();
      fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSV Files", "*.csv"));
      Button uploadButton = new Button("Upload Here");
      uploadButton.setOnAction(new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent arg0) {
          File selected = fileChooser.showOpenDialog(primaryStage);
          try {
            farmTable.loadData(selected);
            System.out.println("loaded");
            dataScreen(primaryStage);
            System.out.println("gamer");
          } catch (Exception ex) {
            System.out.println("error");
            ex.printStackTrace();
            errorPopup("There was an error reading the file.", primaryStage);
          }
        }
      });
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
    
    public void errorPopup(String errorText, Stage primaryStage) {
      // display error popup
    }
    
    /**
     * Displays the data loaded in from the home screen.
     * @param primaryStage
     */
    public void dataScreen(Stage primaryStage) {
      VBox root = new VBox(10);
      root.setAlignment(Pos.CENTER);
      
      HBox reportRow = new HBox(20);
      reportRow.setAlignment(Pos.CENTER);
      Label reportLabel = new Label("Generate Report: ");
      reportLabel.setFont(new Font("Cambria", 20));
      Button farm = new Button("Farm");
      Button annual = new Button("Annual");
      Button monthly = new Button("Monthly");
      reportRow.getChildren().add(reportLabel);
      reportRow.getChildren().add(farm);
      reportRow.getChildren().add(annual);
      reportRow.getChildren().add(monthly);
      
      HBox tableRow = new HBox(30);
      tableRow.setAlignment(Pos.CENTER);
      TableView<String> tableView = new TableView<>();
      VBox sortOptions = new VBox();
      sortOptions.setAlignment(Pos.TOP_CENTER);
      Label sortLabel = new Label("Sort By:");
      sortLabel.setFont(new Font("Cambria", 20));
      ComboBox<String> options = new ComboBox<>();
      sortOptions.getChildren().add(sortLabel);
      sortOptions.getChildren().add(options);
      tableRow.getChildren().add(tableView);
      tableRow.getChildren().add(sortOptions);
      
      Button add = new Button("Add");
      Button del = new Button("Del");
      root.getChildren().add(reportRow);
      root.getChildren().add(tableRow);
      root.getChildren().add(add);
      root.getChildren().add(del);
      
      Scene dataScene = new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT);
      
      primaryStage.setScene(dataScene);
      primaryStage.show();
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
      launch(args);
    }
}
