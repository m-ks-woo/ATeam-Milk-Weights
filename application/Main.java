
package application;

import java.io.File;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
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

  /**
   * Displays an error when thrown by the program to user
   * 
   * @param errorText to be thrown
   */
  public void errorPopup(String errorText) {
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
    Button range = new Button("Range");
    reportRow.getChildren().add(reportLabel);
    reportRow.getChildren().add(farm);
    reportRow.getChildren().add(annual);
    reportRow.getChildren().add(monthly);
    reportRow.getChildren().add(range);

    farm.setOnAction(e -> reportGenerationIntermediaryFarm(primaryStage));
    annual.setOnAction(e -> reportGenerationIntermediaryYear(primaryStage));
    monthly.setOnAction(e -> reportGenerationIntermediaryMonth(primaryStage));
    range.setOnAction(e -> reportGenerationIntermediaryRange(primaryStage));

    HBox tableRow = new HBox(30);
    tableRow.setAlignment(Pos.CENTER);
    TableView<Entry> tableView = new TableView<>();

    VBox buttons = new VBox(10);
    buttons.setAlignment(Pos.CENTER);
    HBox farmIdBox = new HBox(5);
    farmIdBox.setAlignment(Pos.CENTER_LEFT);
    Label farmIdLabel = new Label("Farm ID:");
    TextField farmIdField = new TextField();
    farmIdBox.getChildren().addAll(farmIdLabel, farmIdField);
    HBox dateBox = new HBox(5);
    dateBox.setAlignment(Pos.CENTER_LEFT);
    Label dateLabel = new Label("Date:");
    TextField dateField = new TextField();
    dateBox.getChildren().addAll(dateLabel, dateField);
    HBox weightBox = new HBox(5);
    weightBox.setAlignment(Pos.CENTER_LEFT);
    Label weightLabel = new Label("Weight:");
    TextField weightField = new TextField();
    weightBox.getChildren().addAll(weightLabel, weightField);

    Button add = new Button("Add");

    add.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent arg0) {
        String farmId = farmIdField.getText();
        LocalDate date;
        int weight;
        try {
          DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-M-d");
          date = LocalDate.parse(dateField.getText(), formatter);
        } catch (DateTimeParseException e) {
          errorPopup("Please enter the date in the form yyyy-M-d.");
          return;
        }
        try {
          weight = Integer.parseInt(weightField.getText());
        } catch (NumberFormatException e) {
          errorPopup("Please enter an integer for the weight.");
          return;
        }
        farmTable.addEntry(date, farmId, weight);
        dataScreen(primaryStage);
      }
    });

    Button del = new Button("Del");

    del.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent arg0) {
        String farmId = farmIdField.getText();
        LocalDate date;
        try {
          DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-M-d");
          date = LocalDate.parse(dateField.getText(), formatter);
        } catch (DateTimeParseException e) {
          errorPopup("Please enter the date in the form yyyy-M-d.");
          return;
        }
        if (farmTable.removeEntry(date, farmId)) {
          dataScreen(primaryStage);
        } else {
          errorPopup("The entry could not be removed");
        }
      }
    });

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

    buttons.getChildren().addAll(farmIdBox, dateBox, weightBox, add, del, uploadButton);


    tableRow.getChildren().add(tableView);
    tableRow.getChildren().add(buttons);

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
      for (Entry e : farmTable.getFarms().get(farmId).getEntries()) {
        tableView.getItems().add(e);
      }
    }
    tableView.getSortOrder().addAll(dateCol, farmCol, weightCol);

    root.getChildren().add(reportRow);
    root.getChildren().add(tableRow);

    Scene dataScene = new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT);

    primaryStage.setScene(dataScene);
    primaryStage.show();
  }

  public void reportGenerationIntermediaryFarm(Stage primaryStage) {
    List<String> choices = new ArrayList<>();
    for (String farmID : farmTable.getFarms().keySet()) {
      choices.add(farmTable.getFarms().get(farmID).getID());
    }

    ChoiceDialog<String> dialog = new ChoiceDialog<>("Select Option", choices);
    dialog.setTitle("Farm Selection");
    dialog.setHeaderText("Please select a farm to generate a report of...");
    dialog.setContentText("Choose your farm: ");

    Optional<String> result = dialog.showAndWait();
    if (result.isPresent()) {
      System.out.println("Your choice: " + result.get());
      reportGeneratedScreen(primaryStage, result.get(), "");
    }
  }

  public void reportGenerationIntermediaryMonth(Stage primaryStage) {
    List<String> choices = new ArrayList<>();
    for (String farmID : farmTable.getFarms().keySet()) {
      for (Entry entries : farmTable.getFarms().get(farmID).getEntries()) {
        if (choices.contains(Integer.toString(entries.getDate().getYear()))) {
          continue;
        }
        choices.add(Integer.toString(entries.getDate().getYear()));
      }
    }

    ChoiceDialog<String> dialog = new ChoiceDialog<>("Select Option", choices);
    dialog.setTitle("Month Selection");
    dialog.setHeaderText("First, please select a single year to genereate a report of...");
    dialog.setContentText("Choose your year: ");

    Optional<String> result = dialog.showAndWait();
    if (result.isPresent()) {
      System.out.println("Your choice: " + result.get());
      monthIntermediary(primaryStage, result.get());
    }
  }

  public void monthIntermediary(Stage primaryStage, String selectedYear) {
    List<String> choices = new ArrayList<>();
    for (String farmID : farmTable.getFarms().keySet()) {
      for (Entry entries : farmTable.getFarms().get(farmID).getEntries()) {
        if (choices.contains(entries.getDate().getMonth().toString())) {
          continue;
        }
        if (entries.getDate().getYear() == Integer.parseInt(selectedYear)) {
          choices.add(entries.getDate().getMonth().toString());
        }
      }
    }

    ChoiceDialog<String> dialog = new ChoiceDialog<>("Select Option", choices);
    dialog.setTitle("Month Selection");
    dialog.setHeaderText(
        "Please select a single month from year " + selectedYear + " to generate a report of...");
    dialog.setContentText("Choose your month: ");

    Optional<String> result = dialog.showAndWait();
    if (result.isPresent()) {
      System.out.println("Your choice: " + result.get());
      reportGeneratedScreen(primaryStage, result.get(), selectedYear);
    }
  }

  public void reportGenerationIntermediaryYear(Stage primaryStage) {
    List<String> choices = new ArrayList<>();
    for (String farmID : farmTable.getFarms().keySet()) {
      for (Entry entries : farmTable.getFarms().get(farmID).getEntries()) {
        if (choices.contains(Integer.toString(entries.getDate().getYear()))) {
          continue;
        }
        choices.add(Integer.toString(entries.getDate().getYear()));
      }
    }

    ChoiceDialog<String> dialog = new ChoiceDialog<>("Select Option", choices);
    dialog.setTitle("Year Selection");
    dialog.setHeaderText("Please select a single year to genereate a report of...");
    dialog.setContentText("Choose your year: ");

    Optional<String> result = dialog.showAndWait();
    if (result.isPresent()) {
      System.out.println("Your choice: " + result.get());
      reportGeneratedScreen(primaryStage, result.get(), "");
    }
  }

  public void reportGenerationIntermediaryRange(Stage primaryStage) {
    List<String> choices = new ArrayList<>();
    for (String farmID : farmTable.getFarms().keySet()) {
      for (Entry entries : farmTable.getFarms().get(farmID).getEntries()) {
        if (choices.contains(entries.getDate().toString())) {
          continue;
        }
        choices.add(entries.getDate().toString());
      }
    }

    ChoiceDialog<String> dialog = new ChoiceDialog<>("Select Option", choices);
    dialog.setTitle("Date Selection");
    dialog.setHeaderText("Please select start date to genereate a report starting from...");
    dialog.setContentText("Choose your day: ");

    Optional<String> result = dialog.showAndWait();
    if (result.isPresent()) {
      System.out.println("Your choice: " + result.get());
      chooseEndDateInRange(primaryStage, result.get());
    }
  }

  public void chooseEndDateInRange(Stage primaryStage, String selectedDate) {
    List<String> choices = new ArrayList<>();
    for (String farmID : farmTable.getFarms().keySet()) {
      for (Entry entries : farmTable.getFarms().get(farmID).getEntries()) {
        if (choices.contains(entries.getDate().toString())) {
          continue;
        }
        choices.add(entries.getDate().toString());
      }
    }

    ChoiceDialog<String> dialog = new ChoiceDialog<>("Select Option", choices);
    dialog.setTitle("Year Selection");
    dialog.setHeaderText("Please select end date to genereate a report till...");
    dialog.setContentText("Choose your day: ");

    Optional<String> result = dialog.showAndWait();
    if (result.isPresent()) {
      System.out.println("Your choice: " + result.get());
      DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-M-d");
      LocalDate endDate = LocalDate.parse(result.get().toString(), formatter);
      if (endDate.compareTo(LocalDate.parse(selectedDate, formatter)) < 0) {
        errorPopup("Please choose date in order correctly");
        chooseEndDateInRange(primaryStage, selectedDate);
      } else {
        reportGeneratedScreen(primaryStage, result.get(), selectedDate);
      }
    }
  }

  public void reportGeneratedScreen(Stage primaryStage, String selectedElement,
      String selectedElement2/* selected date-range, farm or month */) {
    List<Entry> data = new ArrayList<>();
    String typeOfData = "";
    BorderPane root = new BorderPane();

    String cssLayout = "-fx-border-color: black;\n" + "-fx-border-insets: 5;\n"
        + "-fx-border-width: 3;\n" + "-fx-border-style: solid;\n";

    Label reportGeneratedFor =
        new Label("Report Generated For: " + selectedElement /* selected farm or month */);
    if (selectedElement.contains("-") && selectedElement2.contains("-")) {
      reportGeneratedFor
          .setText("Report Generated For: " + selectedElement2 + " to " + selectedElement);
    }
    reportGeneratedFor.setFont(new Font("Cambria", 20));
    reportGeneratedFor.setAlignment(Pos.TOP_CENTER);
    TableView<Entry> tableView = new TableView<>();
    TableColumn<Entry, Entry> dateCol = new TableColumn<>("Date");
    dateCol.setCellValueFactory(new PropertyValueFactory<>("date"));
    tableView.getColumns().add(dateCol);
    TableColumn<Entry, Entry> farmCol = new TableColumn<>("Farm ID");
    farmCol.setCellValueFactory(new PropertyValueFactory<>("farmId"));
    tableView.getColumns().add(farmCol);
    TableColumn<Entry, Entry> weightCol = new TableColumn<>("Weight");
    weightCol.setCellValueFactory(new PropertyValueFactory<>("weight"));
    tableView.getColumns().add(weightCol);

    if (selectedElement.contains("Farm") && selectedElement2.equals("")) {
      typeOfData = "farm";
      for (Entry e : farmTable.getFarms().get(selectedElement).getEntries()) {
        tableView.getItems().add(e);
        data.add(e);
      }
    }

    for (Month month : Month.values()) {
      if (selectedElement.equalsIgnoreCase(month.name())) {
        typeOfData = "month";

        for (String farmID : farmTable.getFarms().keySet()) {
          for (Entry e : farmTable.getFarms().get(farmID).getEntries()) {
            if (e.getDate().getMonth().toString().equals(selectedElement)
                && e.getDate().getYear() == Integer.parseInt(selectedElement2)) {
              tableView.getItems().add(e);
              data.add(e);
            }
          }
        }
      }
    }

    try {
      if (Integer.parseInt(selectedElement) % 1 == 0 && selectedElement2.equals("")) {
        typeOfData = "year";
        for (String farmID : farmTable.getFarms().keySet()) {
          for (Entry e : farmTable.getFarms().get(farmID).getEntries()) {
            if (e.getDate().getYear() == Integer.parseInt(selectedElement)) {
              tableView.getItems().add(e);
              data.add(e);
            }
          }
        }
      }
    } catch (NumberFormatException e) {
    }


    LocalDate date1, date2;

    try {
      DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-M-d");
      date1 = LocalDate.parse(selectedElement, formatter);
      date2 = LocalDate.parse(selectedElement2, formatter);

      LocalDate upperDate = date1, lowerDate = date2;

      for (String farmID : farmTable.getFarms().keySet()) {
        for (Entry e : farmTable.getFarms().get(farmID).getEntries()) {
          if (e.getDate().compareTo(lowerDate) >= 0 && e.getDate().compareTo(upperDate) <= 0) {
            System.out
                .println(e.getDate().compareTo(lowerDate) + " " + e.getDate().compareTo(upperDate));
            tableView.getItems().add(e);
            data.add(e);
          }
        }
      }
    } catch (DateTimeParseException e) {
    }

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
    
    String[] uniqueFarmIds = new String[500];
    int countUniqueFarms = 0;
    for (int i = 0; i < data.size(); i++) {
      if (!Arrays.asList(uniqueFarmIds).contains(data.get(i).getFarmId())) {
        uniqueFarmIds[countUniqueFarms] = data.get(i).getFarmId();
        countUniqueFarms++;
      }
    }
    
    int[] sumOfDataFromFarm = new int[uniqueFarmIds.length];
    for (int i = 0; i < uniqueFarmIds.length; i++) {
      for (Entry d : data) {
        if (d.getFarmId().equals(uniqueFarmIds[i])) {
          sumOfDataFromFarm[i] += d.getWeight();
        }
      }
    }
    
    ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
    for (int i = 0; i < countUniqueFarms; i++) {
      pieChartData.add(new PieChart.Data(uniqueFarmIds[i], sumOfDataFromFarm[i]));
    }
    PieChart pieChart = new PieChart(pieChartData);
    
    pieChart.setTitle("Weight Distribution by Farm");
    pieChart.setClockwise(true);
    pieChart.setLabelLineLength(25);
    pieChart.setLabelsVisible(true);
    pieChart.setStartAngle(180);
    pieChart.setStyle(cssLayout);
    
    barChartBox.getChildren().add(bc);
    barChartBox.setStyle(cssLayout);

    sortOptions.getChildren().add(sortLabel);
    sortOptions.getChildren().add(options);
    tableRow.getChildren().add(tableView);

    VBox statBox = new VBox(10);
    statBox.setStyle(cssLayout);
    Label statBoxHeader = new Label("Descriptive Statistics");
    int meanOfData = 0;
    for (Entry d : data) {
      meanOfData += d.getWeight();
    }
    meanOfData /= data.size();

    Label mean = new Label("Mean: " + meanOfData);

    int medianOfData = 0;
    List<Entry> listSortedByWeight = data;

    for (int i = 1; i < listSortedByWeight.size(); i++) {
      int current = listSortedByWeight.get(i).getWeight();
      int j = i - 1;
      while (j >= 0 && current < listSortedByWeight.get(j).getWeight()) {
        listSortedByWeight.get(j + 1).setWeight(listSortedByWeight.get(j).getWeight());
        listSortedByWeight.get(j + 1).setDate(listSortedByWeight.get(j).getDate());
        listSortedByWeight.get(j + 1).setFarmId(listSortedByWeight.get(j).getFarmId());
        j--;
      }
      listSortedByWeight.get(j + 1).setWeight(listSortedByWeight.get(i).getWeight());
      listSortedByWeight.get(j + 1).setDate(listSortedByWeight.get(i).getDate());
      listSortedByWeight.get(j + 1).setFarmId(listSortedByWeight.get(i).getFarmId());
    }

    if (data.size() % 2 == 0) {
      medianOfData = (int) (data.get((data.size() / 2) - 1).getWeight() / 2
          + data.get(data.size() / 2).getWeight() / 2);
    } else {
      medianOfData = data.get(data.size() / 2).getWeight();
    }

    Label median = new Label("Median: " + medianOfData);

    int modeValue = 0, modeMax = 0, modeMaxCount = 0;
    for (int i = 0; i < data.size(); ++i) {
      int count = 0;
      for (int j = 0; j < data.size(); ++j) {
        if (data.get(j).getWeight() == data.get(i).getWeight()) {
          ++count;
        }
      }

      if (count > modeMaxCount) {
        modeMaxCount = count;
        modeMax = data.get(i).getWeight();
      }
    }

    modeValue = modeMax;

    Label mode = new Label("Mode: " + modeValue);

    int varianceOfData = 0;

    for (Entry d : data) {
      varianceOfData += Math.pow((d.getWeight() - meanOfData), 2);
    }

    varianceOfData /= (data.size() - 1);

    Label variance = new Label("Variance: " + (int) Math.pow(varianceOfData, 0.5));

    int totalWeight = 0;
    for (int i = 0; i < data.size(); i++) {
      totalWeight += data.get(i).getWeight();
    }

    Label total = new Label("Total: " + totalWeight);

    statBoxHeader.setAlignment(Pos.CENTER);

    statBox.getChildren().add(statBoxHeader);
    statBox.getChildren().add(mean);
    statBox.getChildren().add(median);
    statBox.getChildren().add(mode);
    statBox.getChildren().add(variance);
    statBox.getChildren().add(total);

    //sideRow.getChildren().add(sortOptions);
    sideRow.getChildren().add(pieChart);
    sideRow.getChildren().add(statBox);

    HBox bottomRow = new HBox(30);

    Button edit = new Button("Edit");
    Button exit = new Button("Exit");

    exit.setOnAction(e -> Platform.exit());
    edit.setOnAction(e -> dataScreen(primaryStage));

    bottomRow.getChildren().add(edit);
    bottomRow.getChildren().add(exit);

    bottomRow.setAlignment(Pos.BASELINE_RIGHT);
    reportGeneratedFor.setAlignment(Pos.CENTER);

    root.setTop(reportGeneratedFor);
    root.setCenter(tableRow);
    root.setRight(sideRow);
    root.setBottom(bottomRow);

    Scene reportScene = new Scene(root, 1000, 775);

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
