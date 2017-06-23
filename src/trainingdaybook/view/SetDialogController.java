
package trainingdaybook.view;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import trainingdaybook.model.Exercise;
import trainingdaybook.model.Set;
import trainingdaybook.model.dao.DaoFactory;
import trainingdaybook.model.dao.SetDao;

/**
 * FXML Controller class
 *
 * @author YARUS
 */
public class SetDialogController implements Initializable {

    @FXML
    private TextField count;
    
    @FXML
    private TextField wheight;
    
    @FXML
    private ComboBox minutes;
    
    @FXML
    private ComboBox seconds;
            
    private Stage dialogStage;
    private Exercise exercise;
    private Set set;
    private boolean okClicked = false;    
    
    DaoFactory daoFactory = DaoFactory.getDAOFactory(DaoFactory.BinaryFileDaoFactory);  
    private SetDao setDao = daoFactory.getSetDao();
    
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }
        
    public void setSet(Exercise exercise, Set set) {
        this.exercise = exercise;
        this.set = set;
        
        if(set != null && set.getId() != null && set.getId() != 0){
            count.setText(Integer.toString(set.getCount()));
            wheight.setText(Integer.toString(set.getWheight()));
            minutes.getSelectionModel().select(set.getMinutes());
            seconds.getSelectionModel().select(set.getSeconds());
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
            set.setCount(Integer.parseInt(count.getText()));                      
            set.setWheight(Integer.parseInt(wheight.getText()));            
            set.setMinutes((Integer)minutes.getValue());
            set.setSeconds((Integer)seconds.getValue());
            set.setExercise(exercise);
            if(set.getId() == null || set.getId() == 0)
                setDao.create(set);            
            else
                setDao.update(set);
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
        
        if (count.getText() == null || count.getText().length() == 0) 
            errorMessage += "Ви не вказали кількість повторів!\n"; 
        
        if (wheight.getText() == null || wheight.getText().length() == 0) 
            errorMessage += "Ви не вказали вагу снаряду!\n"; 
        
        try{
            Integer.parseInt(count.getText());
            Integer.parseInt(wheight.getText());
        }catch(Exception e){
            errorMessage += "Всі поля мають бути числовими!\n";         
        }
                
        
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
        for(int i = 0 ; i < 20; i++)
                minutes.getItems().add(i);        
        minutes.getSelectionModel().select(0);
        
        for(int i = 0 ; i < 60; i++)
                seconds.getItems().add(i);
        seconds.getSelectionModel().select(0);        
    }    
    
}
