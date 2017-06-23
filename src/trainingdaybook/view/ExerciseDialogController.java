
package trainingdaybook.view;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import trainingdaybook.model.Exercise;
import trainingdaybook.model.Training;
import trainingdaybook.model.dao.DaoFactory;
import trainingdaybook.model.dao.ExerciseDao;

/**
 * FXML Controller class
 *
 * @author YARUS
 */
public class ExerciseDialogController implements Initializable {

    @FXML
    private TextField titleExercise;
    
    private Stage dialogStage;
    private Training training;
    private Exercise exercise;
    private boolean okClicked = false;
    DaoFactory daoFactory = DaoFactory.getDAOFactory(DaoFactory.BinaryFileDaoFactory);
    private ExerciseDao exerciseDao = daoFactory.getExerciseDao();
    
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }
    
    
    public void setExercise(Training training, Exercise exercise) {
        this.training = training;
        this.exercise = exercise;
        if(exercise != null && exercise.getTitle() != null){
            titleExercise.setText(exercise.getTitle());
        }        
    }
    
    /**
     * Returns true if the user clicked OK, false otherwise.
     * 
     * @return
     */
    public boolean isOkClicked() {
        return okClicked;
    }
    
    /**
     * Called when the user clicks ok.
     */
    @FXML
    private void handleOk() {
        if (isInputValid()) {                       
            exercise.setTitle(titleExercise.getText());                      
            exercise.setTraining(training);
            if(exercise.getId() == null || exercise.getId() == 0)
                exerciseDao.create(exercise);                            
            else
               exerciseDao.update(exercise);
            okClicked = true;
            dialogStage.close();
        }
    }
    
    /**
     * Called when the user clicks cancel.
     */
    @FXML
    private void handleCancel() {
        dialogStage.close();
    }
    
    /**
     * Validates the user input in the text fields.
     * 
     * @return true if the input is valid
     */
    private boolean isInputValid() {
        String errorMessage = "";
                      
        if (titleExercise.getText() == null || titleExercise.getText().length() == 0) 
            errorMessage += "Ви не вказали назву вправи!\n"; 
               
        if (errorMessage.length() == 0) 
            return true;
        else {
            // Show the error message.
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initOwner(dialogStage);
            alert.setTitle("Не вірно заповнені поля");
            alert.setHeaderText("Будь-ласка заповність поля правильно");
            alert.setContentText(errorMessage);
            alert.showAndWait();
            return false;
        }
    } 
    
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }    
    
}
