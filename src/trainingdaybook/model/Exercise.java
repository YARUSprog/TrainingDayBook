
package trainingdaybook.model;

import java.io.Serializable;

/**
 * 
 * @author YARUS
 */
public class Exercise implements Serializable{
    private Integer id;
    private String title; 
    private Training training; 
    
    public Exercise(){}
    
    public Exercise(String title, Training training){
        this.title = title;
        this.training = training;
    }
    
    public Integer getId(){
        return id;
    }
            
    public void setId(Integer i){
        id = i;
    }
    
    public String getTitle(){
        return title;
    }
    
    public void setTitle(String title){
        this.title = title;
    }
    
    public Training getTraining() {
        return training;
    }

    public void setTraining(Training training) {
        this.training = training;
    }
}
