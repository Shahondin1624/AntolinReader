package gui;


import importing.Importer;
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
import logic.Pupil;

import java.io.File;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    @FXML
    private TextField textField1;

    @FXML
    private TextField textField2;

    @FXML
    private Button import1;

    @FXML
    private Button import2;

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

    private final ObservableList<Pupil> pupils1 = FXCollections.observableArrayList();
    private final ObservableList<Pupil> pupils2 = FXCollections.observableArrayList();

    private final SortedList<Pupil> sortedList1 = new SortedList<>(pupils1);
    private final SortedList<Pupil> sortedList2 = new SortedList<>(pupils2);

    private final SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss.SSS");


    @FXML
    public void importFile(ActionEvent event) {
        Stage stage = new Stage();
        stage.setTitle("Excel Mappe zum Importieren auswählen");
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Excel-Files", "*xls", "*xlsx"));
        File file = fileChooser.showOpenDialog(stage);
        String id = ((Button) event.getSource()).getId();
        int x = Integer.parseInt(String.valueOf(id.charAt(id.length() - 1)));
        logTime("Beginning to import Sheet " + x);
        try {
            List<Pupil> pupils = Importer.getFile(file);
            if (x == 1) {
                doListStuff(pupils1, pupils, textField1, file.getName());
            } else if (x == 2 && !pupils1.isEmpty()) {
                doListStuff(pupils2, pupils, textField2, file.getName());
                assignValuesToList2(pupils, pupils1);
            } else {
                doListStuff(pupils1, pupils, textField1, file.getName());
            }
        } catch (NullPointerException ignored) {
        }
        tableView1.refresh();
        tableView2.refresh();
        errormessage.setText("");
        logTime("Finished importing Sheet " + x);
    }

    @FXML
    private void reset() {
        textField1.setText("");
        textField2.setText("");
        pupils1.clear();
        pupils2.clear();
        errormessage.setText("");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        logTime("Beginning to initialize");
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
        logTime("Finished Initialization");
    }

    private void doListStuff(List<Pupil> list, List<Pupil> imported, TextField textField, String filename) {
        list.clear();
        list.addAll(imported);
        textField.setText(filename);
    }

    private void assignValuesToList2(List<Pupil> newPupils, List<Pupil> oldPupils) {
        logTime("Beginning to import second list");
        for (Pupil newPupil : newPupils) {
            for (Pupil previousPupil : oldPupils) {
                if (newPupil.isSamePupil(previousPupil)) {
                    if (newPupil.getDifference() < 0) {
                        errormessage.setText("Möglicherweise Excel Dateien in vertauschter Reihenfolge importiert");
                    }
                    break;
                }
            }
        }
        logTime("Finished importing");
    }

    private void logTime(String s) {
        Date date = new Date(System.currentTimeMillis());
        System.out.println(format.format(date) + "\t" + s);
    }
}
