
package trainingdaybook.view;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import trainingdaybook.TrainingDaybook;
import trainingdaybook.model.Exercise;
import trainingdaybook.model.Set;
import trainingdaybook.model.Training;
import trainingdaybook.model.dao.DaoFactory;
import trainingdaybook.model.dao.ExerciseDao;
import trainingdaybook.model.dao.SetDao;
import trainingdaybook.model.dao.TrainingDao;

/**
 * FXML Controller class
 *
 * @author YARUS
 */
public class MainWindowController implements Initializable {
    
    private ObservableList<Training> trainingData = FXCollections.observableArrayList();
    private ObservableList<Exercise> exercisesData = FXCollections.observableArrayList();
    private ObservableList<Set> setsData = FXCollections.observableArrayList();
    
    DaoFactory daoFactory = DaoFactory.getDAOFactory(DaoFactory.BinaryFileDaoFactory);
    private ExerciseDao exerciseDao = daoFactory.getExerciseDao();
    private TrainingDao trainingDao = daoFactory.getTrainingDao();    
    private SetDao setDao = daoFactory.getSetDao();
    
    @FXML
    private TableView<Training> tableTrainings;        
    
    @FXML
    private TableColumn<Training, Long> idTrainingColumn;
 
    @FXML
    private TableColumn<Training, String> dateTrainingColumn; 
 
    @FXML
    private TableColumn<Training, String> titleTrainingColumn;  
    
    @FXML
    private TableView<Exercise> tableExercises;
    
    @FXML
    private TableColumn<Exercise, Integer> idExerciseColumn;
    
    @FXML
    private TableColumn<Exercise, String> titleExerciseColumn; 
    
    @FXML
    private TableView<Set> tableSets;
    
    @FXML
    private TableColumn<Set, String> titleSetColumn; 
    
    @FXML
    private TableColumn<Set, Integer> setColumn;
    
    @FXML
    private TableColumn<Set, Integer> countInSetColumn;
    
    @FXML
    private TableColumn<Set, Integer> wheightColumn;
    
    @FXML
    private TableColumn<Set, String> timeRelaxColumn;
    
    private void initData() {
        trainingData.addAll(trainingDao.findAll());        
    }
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        initData();
        idTrainingColumn.setCellValueFactory(new PropertyValueFactory<Training, Long>("id"));
        dateTrainingColumn.setCellValueFactory(cellData  -> new SimpleObjectProperty<>(cellData.getValue().getStringDate()));
        titleTrainingColumn.setCellValueFactory(new PropertyValueFactory<Training, String>("title"));
                
        idExerciseColumn.setCellValueFactory(new PropertyValueFactory<Exercise, Integer>("id"));
        titleExerciseColumn.setCellValueFactory(new PropertyValueFactory<Exercise, String>("title"));
                       
        setColumn.setCellValueFactory(new PropertyValueFactory<Set, Integer>("id"));        
        countInSetColumn.setCellValueFactory(new PropertyValueFactory<Set, Integer>("count"));
        wheightColumn.setCellValueFactory(new PropertyValueFactory<Set, Integer>("wheight"));
        timeRelaxColumn.setCellValueFactory(cellData  -> new SimpleObjectProperty<>(cellData.getValue().getStringTimeRelax()));
               
        tableTrainings.setItems(trainingData);        
        UpdateExercisesTable(null);        
        tableTrainings.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> UpdateExercisesTable(newValue));        
        UpdateSetsTable(null);        
        tableExercises.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> UpdateSetsTable(newValue));
        tableTrainings.getSelectionModel().selectFirst();
    }
    
    private void UpdateExercisesTable(Training training) {          
        if (training != null) {           
            exercisesData.clear();
            exercisesData.addAll(exerciseDao.findAllByTrainingId(training.getId()));
            tableExercises.setItems(exercisesData);
            tableExercises.getSelectionModel().selectLast();
        } else {            
            exercisesData.clear();
        }
    }
    
    private void UpdateSetsTable(Exercise ex) {          
        setsData.clear();
        if (ex != null) {            
            if(setDao.findAllByExerciseId(Long.valueOf(ex.getId())) != null){                
                setsData.addAll(setDao.findAllByExerciseId((long)ex.getId()));
                tableSets.setItems(setsData);
            }
            tableSets.getSelectionModel().selectLast();
        }
    }
    
    private void updateTrainingTable() {          
        trainingData.clear();
        trainingData.addAll(trainingDao.findAll());
        tableTrainings.setItems(trainingData);
        tableTrainings.getSelectionModel().selectLast();                    
    }
    
    @FXML
    private void handleDeleteTraining() {        
        long selectedIndex = tableTrainings.getSelectionModel().getSelectedItem().getId();   
        if (selectedIndex >= 0) {            
            trainingDao.delete(selectedIndex);            
            updateTrainingTable();
            
        } else {
            // Nothing selected.
            Alert alert = new Alert(AlertType.WARNING);            
            alert.setTitle("Нічого не обрано");
            alert.setHeaderText("Жодного тренування не обрано");
            alert.setContentText("Будь ласка, виберіть тренування в таблиці.");
            alert.showAndWait();   
        }
    }
    
    @FXML
    private void handleNewTraining() {
        Training training = new Training();
        boolean okClicked = showTrainingDialog(training, 0);
        if(okClicked){            
            updateTrainingTable();            
        }
    }
    
    @FXML
    private void handleEditTraining() {
        Training selectedTraining = tableTrainings.getSelectionModel().getSelectedItem();
        if (selectedTraining != null) {
            boolean okClicked = showTrainingDialog(selectedTraining, 1);
        } else {
            // Nothing selected.
            Alert alert = new Alert(AlertType.WARNING);
            alert.initOwner(TrainingDaybook.getPrimaryStage());
            alert.setTitle("No Selection");
            alert.setHeaderText("No Person Selected");
            alert.setContentText("Please select a person in the table.");
            alert.showAndWait();
        }
        tableTrainings.refresh();
    }
    
    public boolean showTrainingDialog(Training training, int type) {
        try {
            // Load the fxml file and create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(TrainingDaybook.class.getResource("view/TrainingDialog.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            if(type == 0)
                dialogStage.setTitle("Добавление тенировки");
            else
                dialogStage.setTitle("Изменение тренировки");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(TrainingDaybook.getPrimaryStage());
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            // Set the training into the controller.
            TrainingDialogController controller = loader.getController();
            controller.setDialogStage(dialogStage);            
            controller.setTraining(training);

            // Show the dialog and wait until the user closes it
            dialogStage.showAndWait();

            return controller.isOkClicked();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    @FXML
    private void handleDeleteExercise() {
        long selectedIndex = tableExercises.getSelectionModel().getSelectedItem().getId();   
        if (selectedIndex >= 0) {            
            exerciseDao.delete(selectedIndex);            
            Training training = tableTrainings.getSelectionModel().selectedItemProperty().get();
            UpdateExercisesTable(training);
        } else {
            // Nothing selected.
            Alert alert = new Alert(AlertType.WARNING);            
            alert.setTitle("Нічого не обрано");
            alert.setHeaderText("Жодної вправи не обрано");
            alert.setContentText("Будь ласка, виберіть вправу в таблиці.");
            alert.showAndWait();   
        }
    }
    
    @FXML
    private void handleNewExercise() {
        Exercise exercise = new Exercise();
        boolean okClicked = showExerciseDialog(exercise, 0);
        if(okClicked){
            Training training = tableTrainings.getSelectionModel().getSelectedItem();
            UpdateExercisesTable(training);
        }
    }
    
    @FXML
    private void handleEditExercise() {
        Exercise selectedExercise = tableExercises.getSelectionModel().getSelectedItem();
        if (selectedExercise != null) {
            boolean okClicked = showExerciseDialog(selectedExercise, 1);
        } else {
            // Nothing selected.
            Alert alert = new Alert(AlertType.WARNING);
            alert.initOwner(TrainingDaybook.getPrimaryStage());
            alert.setTitle("No Selection");
            alert.setHeaderText("No Person Selected");
            alert.setContentText("Please select a person in the table.");
            alert.showAndWait();
        }
        tableExercises.refresh();
    }
    
    public boolean showExerciseDialog(Exercise exercise, int type){
        try {
            // Load the fxml file and create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(TrainingDaybook.class.getResource("view/ExerciseDialog.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            if(type == 0)
                dialogStage.setTitle("Додання вправи");
            else
                dialogStage.setTitle("Редагування вправи");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(TrainingDaybook.getPrimaryStage());
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            // Set the exercise into the controller.
            ExerciseDialogController controller = loader.getController();
            controller.setDialogStage(dialogStage);            
            controller.setExercise(tableTrainings.getSelectionModel().getSelectedItem(), exercise);

            // Show the dialog and wait until the user closes it
            dialogStage.showAndWait();

            return controller.isOkClicked();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }        
    }
    
    @FXML
    private void handleDeleteSet() {
        long selectedIndex = tableSets.getSelectionModel().getSelectedItem().getId();
             
        if (selectedIndex >= 0) {
            setDao.delete(selectedIndex);            
            Exercise exercise = tableExercises.getSelectionModel().selectedItemProperty().get();
            UpdateSetsTable(exercise);
        } else {
            // Nothing selected.
            Alert alert = new Alert(AlertType.WARNING);            
            alert.setTitle("Нічого не обрано");
            alert.setHeaderText("Жодного підходу не обрано");
            alert.setContentText("Будь ласка, виберіть підхід в таблиці.");
            alert.showAndWait();   
        }
    }
    
    @FXML
    private void handleNewSet(){
        Set set = new Set();
        boolean okClicked = showSetDialog(set, 0);
        if(okClicked){
            Exercise exercise = tableExercises.getSelectionModel().getSelectedItem();
            UpdateSetsTable(exercise);
        }
    }
    
    @FXML
    private void handleEditSet(){
        Set selectedSet = tableSets.getSelectionModel().getSelectedItem();
        if (selectedSet != null) {
            boolean okClicked = showSetDialog(selectedSet, 1);
        } else {
            // Nothing selected.
            Alert alert = new Alert(AlertType.WARNING);
            alert.initOwner(TrainingDaybook.getPrimaryStage());
            alert.setTitle("No Selection");
            alert.setHeaderText("No Person Selected");
            alert.setContentText("Please select a person in the table.");
            alert.showAndWait();
        }
        tableSets.refresh();
    }
    
    public boolean showSetDialog(Set set, int type){
        try {
            // Load the fxml file and create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(TrainingDaybook.class.getResource("view/SetDialog.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            if(type == 0)
                dialogStage.setTitle("Додання вправи");
            else
                dialogStage.setTitle("Редагування вправи");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(TrainingDaybook.getPrimaryStage());
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);
           
            SetDialogController controller = loader.getController();
            controller.setDialogStage(dialogStage);            
            controller.setSet(tableExercises.getSelectionModel().getSelectedItem(), set);

            // Show the dialog and wait until the user closes it
            dialogStage.showAndWait();

            return controller.isOkClicked();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }        
    }
}
