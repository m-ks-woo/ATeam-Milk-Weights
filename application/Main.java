package application;

import java.io.File;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.stage.FileChooser;
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
   * 
   * @param primaryStage
   * @throws Exception
   */
  @Override
  public void start(Stage primaryStage) throws Exception {
    VBox root = new VBox(10);

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
          dataScreen(primaryStage);
        } catch (Exception ex) {
          ex.printStackTrace();
          errorPopup("There was an error reading the file.");
        }
      }
    });
    Button skipButton = new Button("Skip");
    skipButton.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent arg0) {
        dataScreen(primaryStage);
      }
    });
    Region spacer = new Region();
    spacer.setMinHeight(100);
    root.getChildren().add(titleLabel);
    root.getChildren().add(spacer);
    root.getChildren().add(promptLabel);
    root.getChildren().add(uploadButton);
    root.getChildren().add(skipButton);
    Scene mainScene = new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT);

    // Add the stuff and set the primary stage
    primaryStage.setTitle(APP_TITLE);
    primaryStage.setScene(mainScene);
    primaryStage.show();
  }

  public void errorPopup(String errorText) { // Didn't use primaryStage, please
                                                                 // advise
    // display error popup
    Alert error = new Alert(AlertType.ERROR);
    error.setTitle("Error");
    error.setHeaderText("Error occured during...");
    error.setContentText(errorText);

    error.showAndWait();
  }

  /**
   * Displays the data loaded in from the home screen.
   * 
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

    farm.setOnAction(e -> reportGeneratedScreen(
        primaryStage /* load intermediate window to get selected farm */));
    annual.setOnAction(e -> reportGeneratedScreen(
        primaryStage /* load intermediate window to get selected year */));
    monthly.setOnAction(e -> reportGeneratedScreen(
        primaryStage /* load intermediate window to get selected month */));

    HBox tableRow = new HBox(30);
    tableRow.setAlignment(Pos.CENTER);
    TableView<Entry> tableView = new TableView<>();
    
    VBox sortOptions = new VBox();
    sortOptions.setAlignment(Pos.TOP_CENTER);
    Label sortLabel = new Label("Sort By:");
    sortLabel.setFont(new Font("Cambria", 20));
    ComboBox<String> options = new ComboBox<>();
    sortOptions.getChildren().add(sortLabel);
    sortOptions.getChildren().add(options);
    tableRow.getChildren().add(tableView);
    tableRow.getChildren().add(sortOptions);
    
    TableColumn<Entry, Entry> dateCol = new TableColumn<>("Date");
    dateCol.setCellValueFactory(new PropertyValueFactory<>("date"));
    tableView.getColumns().add(dateCol);
    // change to have each column be a farm
    TableColumn<Entry, Entry> farmCol = new TableColumn<>("Farm ID");
    farmCol.setCellValueFactory(new PropertyValueFactory<>("farmId"));
    tableView.getColumns().add(farmCol);
    TableColumn<Entry, Entry> weightCol = new TableColumn<>("Weight");
    weightCol.setCellValueFactory(new PropertyValueFactory<>("weight"));
    tableView.getColumns().add(weightCol);
    for (String farmId : farmTable.getFarms().keySet()) {
//      TableColumn<Entry, Entry> farmCol = new TableColumn<>(farmId);
//      farmCol.setCellValueFactory(new PropertyValueFactory<>("weight"));
//      tableView.getColumns().add(farmCol);
      for (Entry e : farmTable.getFarms().get(farmId).getEntries()) {
        tableView.getItems().add(e);
      }
    }

    Button add = new Button("Add");
    Button del = new Button("Del");
    
    FileChooser fileChooser = new FileChooser();
    fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSV Files", "*.csv"));
    Button uploadButton = new Button("Upload Files Here");
    uploadButton.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent arg0) {
        File selected = fileChooser.showOpenDialog(primaryStage);
        try {
          farmTable.loadData(selected);
          dataScreen(primaryStage);
        } catch (Exception ex) {
          ex.printStackTrace();
          errorPopup("There was an error reading the file.");
        }
      }
    });
    root.getChildren().add(reportRow);
    root.getChildren().add(tableRow);
    root.getChildren().add(uploadButton);
    root.getChildren().add(add);
    root.getChildren().add(del);

    Scene dataScene = new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT);

    primaryStage.setScene(dataScene);
    primaryStage.show();
  }

  public void reportGeneratedScreen(Stage primaryStage /* selected farm or month */) {
    BorderPane root = new BorderPane();

    String cssLayout = "-fx-border-color: black;\n" + "-fx-border-insets: 5;\n"
        + "-fx-border-width: 3;\n" + "-fx-border-style: solid;\n";

    Label reportGeneratedFor =
        new Label("Report Generated For: " + "sample option" /* selected farm or month */);
    reportGeneratedFor.setFont(new Font("Cambria", 20));
    reportGeneratedFor.setAlignment(Pos.TOP_CENTER);
    TableView<String> tableView = new TableView<>();
    HBox tableRow = new HBox(10);
    VBox sideRow = new VBox(10);
    tableRow.setAlignment(Pos.CENTER);
    sideRow.setAlignment(Pos.CENTER_RIGHT);
    VBox sortOptions = new VBox();
    sortOptions.setAlignment(Pos.TOP_LEFT);
    Label sortLabel = new Label("Sort By:");
    sortLabel.setFont(new Font("Cambria", 20));
    sortLabel.setAlignment(Pos.TOP_LEFT);
    ComboBox<String> options = new ComboBox<>();
    VBox barChartBox = new VBox(10);
    CategoryAxis xAxis = new CategoryAxis();
    NumberAxis yAxis = new NumberAxis();
    BarChart<String, Number> bc = new BarChart<String, Number>(xAxis, yAxis);
    bc.setTitle("Visual Statistics");
    xAxis.setLabel("Category");
    yAxis.setLabel("Value");
    barChartBox.getChildren().add(bc);
    barChartBox.setStyle(cssLayout);

    sortOptions.getChildren().add(sortLabel);
    sortOptions.getChildren().add(options);
    tableRow.getChildren().add(tableView);

    VBox statBox = new VBox(10);
    statBox.setStyle(cssLayout);
    Label statBoxHeader = new Label("Descriptive Statistics");
    Label mean = new Label("Mean: 0");
    Label median = new Label("Median: 0");
    Label mode = new Label("Mode: 0");
    Label variance = new Label("Variance: 0");

    statBoxHeader.setAlignment(Pos.CENTER);

    /* mean.setText("Mean: " + new calculated mean); */ // Same format for other statistics

    statBox.getChildren().add(statBoxHeader);
    statBox.getChildren().add(mean);
    statBox.getChildren().add(median);
    statBox.getChildren().add(mode);
    statBox.getChildren().add(variance);

    sideRow.getChildren().add(sortOptions);
    sideRow.getChildren().add(barChartBox);
    sideRow.getChildren().add(statBox);

    HBox bottomRow = new HBox(30);

    Button edit = new Button("Edit");
    Button print = new Button("Print");
    Button exit = new Button("Exit");

    exit.setOnAction(e -> Platform.exit());
    edit.setOnAction(e -> dataScreen(primaryStage));

    bottomRow.getChildren().add(edit);
    bottomRow.getChildren().add(print);
    bottomRow.getChildren().add(exit);

    bottomRow.setAlignment(Pos.BASELINE_RIGHT);
    reportGeneratedFor.setAlignment(Pos.CENTER);

    root.setTop(reportGeneratedFor);
    root.setCenter(tableRow);
    root.setRight(sideRow);
    root.setBottom(bottomRow);

    Scene reportScene = new Scene(root, 1000, 775); // Enlarged this

    primaryStage.setScene(reportScene);
    primaryStage.show();
  }

  /**
   * @param args
   */
  public static void main(String[] args) {
    launch(args);
  }
}
