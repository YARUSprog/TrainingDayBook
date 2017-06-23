
package trainingdaybook;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
 *
 * @author YARUS
 */
public class TrainingDaybook extends Application {
    
    private static Stage primaryStage;
    private BorderPane rootLayout;
    
    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Training Day Book");
        initRootLayout();
        showOverview();        
        this.primaryStage.setResizable(false);
        this.primaryStage.show(); 
    }

    public void initRootLayout() {
        try {            
            // Load root layout from fxml file.
            FXMLLoader loader = new FXMLLoader();            
            loader.setLocation(TrainingDaybook.class.getResource("view/RootLayout.fxml"));            
            rootLayout = (BorderPane) loader.load();
            
            // Show the scene containing the root layout.            
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);                       
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void showOverview() {
        try {            
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(TrainingDaybook.class.getResource("view/MainWindow.fxml"));
            AnchorPane MainWindow = (AnchorPane) loader.load();
            rootLayout.setCenter(MainWindow);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {        
        launch(args);        
    }
    
    public static Stage getPrimaryStage() {
        return primaryStage;
    }    
}
