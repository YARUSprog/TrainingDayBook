
package trainingdaybook.view;

import java.net.URL;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import trainingdaybook.model.Training;
import trainingdaybook.model.dao.DaoFactory;
import trainingdaybook.model.dao.TrainingDao;

/**
 * FXML Controller class
 *
 * @author YARUS
 */
public class TrainingDialogController implements Initializable {

    @FXML
    private DatePicker dateTraining;
    
    @FXML
    private TextField titleTraining;
    
    private Stage dialogStage;
    private Training training;
    private boolean okClicked = false;
    
    DaoFactory daoFactory = DaoFactory.getDAOFactory(DaoFactory.BinaryFileDaoFactory);    
    private TrainingDao trainingDao = daoFactory.getTrainingDao();        
    
    /**
     * Sets the stage of this dialog.
     * 
     * @param dialogStage
     */
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }
    
    
    public void setTraining(Training training) {
        this.training = training;        
        if(training.getId() != null && training.getId() != 0){        
            LocalDate ld = LocalDate.of(training.getDate().get(Calendar.YEAR), training.getDate().get(Calendar.MONTH)+1, training.getDate().get(Calendar.DAY_OF_MONTH));
            dateTraining.setValue(ld);
            titleTraining.setText(training.getTitle());
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
            Instant instant =  dateTraining.getValue().atStartOfDay().atZone(ZoneId.systemDefault()).toInstant();
            Date d = Date.from(instant);
            GregorianCalendar gr = new GregorianCalendar();
            gr.setTime(d);
            training.setDate(gr);
            training.setTitle(titleTraining.getText());
            if(training.getId() == null || training.getId() == 0)
                trainingDao.create(training);
            else
               trainingDao.update(training);
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
        
        if (dateTraining.getValue() == null) 
            errorMessage += "Ви не вказали дату !\n"; 
               
        if (titleTraining.getText() == null || titleTraining.getText().length() == 0) 
            errorMessage += "Ви не вказали тип тренування!\n"; 
               
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
        dateTraining.setValue(LocalDate.now());
    }    
    
}
