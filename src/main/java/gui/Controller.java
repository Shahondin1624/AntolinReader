package gui;


import importing.Importer;
import importing.PupilCombiner;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import logic.Klasse;
import logic.Pupil;
import logic.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    @FXML
    private TextField textField1;

    @FXML
    private TextField textField2;

    @FXML
    private TableView<Pupil> tableView1;

    @FXML
    private TableView<Pupil> tableView2;

    @FXML
    private TableColumn<Pupil, String> surenameTC1;

    @FXML
    private TableColumn<Pupil, String> nameTC1;

    @FXML
    private TableColumn<Pupil, Integer> pointsTC1;

    @FXML
    private TableColumn<Pupil, String> surenameTC2;

    @FXML
    private TableColumn<Pupil, String> nameTC2;

    @FXML
    private TableColumn<Pupil, Integer> pointsTC2;

    @FXML
    private TableColumn<Pupil, Integer> newPointsTC2;

    @FXML
    private TableColumn<Pupil, Integer> differenceTC2;

    @FXML
    private Label errormessage;

    private final Logger logger = LoggerFactory.getLogger(Controller.class);

    private Klasse klasse1 = null;
    private Klasse klasse2 = null;

    private final ObservableList<Pupil> pupils1 = FXCollections.observableArrayList();
    private final ObservableList<Pupil> pupils2 = FXCollections.observableArrayList();

    private final SortedList<Pupil> sortedList1 = new SortedList<>(pupils1);
    private final SortedList<Pupil> sortedList2 = new SortedList<>(pupils2);

    private final SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss.SSS");

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        tableView1.setPlaceholder(new Label("Keine Schüler in Tabelle"));
        tableView2.setPlaceholder(new Label("Keine Schüler in Tabelle"));

        surenameTC1.setCellValueFactory(new PropertyValueFactory<>("forename"));
        nameTC1.setCellValueFactory(new PropertyValueFactory<>("name"));
        pointsTC1.setCellValueFactory(new PropertyValueFactory<>("points"));

        surenameTC2.setCellValueFactory(new PropertyValueFactory<>("forename"));
        nameTC2.setCellValueFactory(new PropertyValueFactory<>("name"));
        pointsTC2.setCellValueFactory(new PropertyValueFactory<>("points"));
        newPointsTC2.setCellValueFactory(new PropertyValueFactory<>("newPoints"));
        differenceTC2.setCellValueFactory(new PropertyValueFactory<>("difference"));

        pointsTC1.setSortType(TableColumn.SortType.DESCENDING);
        differenceTC2.setSortType(TableColumn.SortType.DESCENDING);

        sortedList1.setComparator(Comparator.comparingInt(Pupil::getPoints));
        sortedList1.comparatorProperty().bind(tableView1.comparatorProperty());

        sortedList2.setComparator(Comparator.comparingInt(Pupil::getDifference));
        sortedList2.comparatorProperty().bind(tableView2.comparatorProperty());

        tableView1.setItems(sortedList1);
        tableView2.setItems(sortedList2);

        tableView1.getSortOrder().add(pointsTC1);
        tableView2.getSortOrder().add(differenceTC2);

        sortedList1.getComparator().reversed();
        sortedList2.getComparator().reversed();

        tableView1.sort();
        tableView2.sort();
    }

    /***
     * Imports the selected file and attempts to create a Klasse-object from it
     * @param event to get button-id
     */
    @FXML
    public void importFile(ActionEvent event) {
        Stage stage = new Stage();
        stage.setTitle("Excel Mappe zum Importieren auswählen");
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Excel-Files", "*xls", "*xlsx"));
        File file = fileChooser.showOpenDialog(stage);
        String id = ((Button) event.getSource()).getId();
        int x = Integer.parseInt(String.valueOf(id.charAt(id.length() - 1)));
        try {
            logger.info("Beginning to import Sheet {}", file.getName());
            Klasse klasse = Importer.getFile(file);
            klasse.setFileName(file.getName());
            if (x == 1) {
                klasse1 = klasse;
            } else if (x == 2 && klasse1 != null) {
                klasse2 = klasse;
            } else {
                klasse1 = klasse;
            }
        } catch (NullPointerException e) {
            errormessage.setText(e.getMessage());
        } catch (IllegalArgumentException iae){
            logger.warn(iae.getMessage());
            errormessage.setText(iae.getMessage());
        }
        assignValuesToUI();
    }

    /***
     * Switches klasse's if they have been imported in the wrong order and displays them
     */
    private void assignValuesToUI() {
        Klasse older = Util.older(klasse1, klasse2);
        Klasse newer = Util.newer(klasse1, klasse2);
        klasse1 = older;
        klasse2 = newer;
        if (klasse1 != null){
            textField1.setText(klasse1.getFileName());
            pupils1.clear();
            pupils1.addAll(klasse1.getPupils());
        }
        if (klasse2 != null) {
            textField2.setText(klasse2.getFileName());
            klasse2 = PupilCombiner.merge(klasse1, klasse2);
            pupils2.clear();
            pupils2.addAll(klasse2.getPupils());
        }
        tableView1.refresh();
        tableView2.refresh();
    }

    /***
     * completely resets UI
     */
    @FXML
    private void reset() {
        textField1.setText("");
        textField2.setText("");
        pupils1.clear();
        pupils2.clear();
        errormessage.setText("");
        klasse1 = null;
        klasse2 = null;
    }


}
