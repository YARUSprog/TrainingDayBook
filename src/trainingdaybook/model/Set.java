
package trainingdaybook.model;

import java.io.Serializable;

/**
 * 
 * @author YARUS
 */
public class Set implements Serializable{
    
    private Long id;
    private int count; 
    private int wheight; 
    private int minutes;
    private int seconds;
    private Exercise exercise;
    //private int[] timeRelax; 
        
    public Set(){
        id = 0L;
    }
    
    public Set(int count, int wheight, int minutes, int seconds){
        this.count = count;
        this.wheight = wheight;
        this.minutes = minutes;
        this.seconds = seconds;     
    }
    
    public Long getId(){
        return id;
    }
    
    public void setId(Long id){
        this.id = id;
    }
    
    public int getCount(){
        return count;
    }
    
    public void setCount(int c){
        count = c;
    }
    
    public int getWheight(){
        return wheight;
    }
    
    public void setWheight(int w){
        wheight = w;
    }
    
    public String getStringTimeRelax(){        
        return "" + minutes + "." + seconds;
    }
    
    public int getMinutes() {
        return minutes;
    }

    public void setMinutes(int minutes) {
        this.minutes = minutes;
    }

    public int getSeconds() {
        return seconds;
    }

    public void setSeconds(int seconds) {
        this.seconds = seconds;
    }

    public Exercise getExercise() {
        return exercise;
    }

    public void setExercise(Exercise exercise) {
        this.exercise = exercise;
    }
    
//    @Override
//    public String toString(){
//        return "Подход " + id + ", Повторений " + count + ", вес " + wheight + ", отдых " + timeRelax[0] + "." + timeRelax[1] + "c";
//    }
    
    /*
    private class TimeRelax{
        private int minutes;
        private int seconds;
        
        public TimeRelax(int m, int s){
            minutes = m;
            seconds = s;
        }
        
        public int getMinutes(){
            return minutes;
        }
        
        public void setMinutes(int m){
            minutes = m;
        }
        
        public int getSeconds(){
            return seconds;
        }
        
        public void setSeconds(int s){
            seconds = s;
        }        
    }    
    */   
}
