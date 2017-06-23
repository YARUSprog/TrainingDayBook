
package trainingdaybook.model;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;
import java.util.Objects;

/**
 *
 * @author YARUS
 */
public class Training implements Serializable{
    
    private Long id;
    private String title;
    private GregorianCalendar date;        
    
    public Training(){}
    
    public Training(GregorianCalendar date, String title){
        this.date = date;
        this.title = title;
    }
        
    public Long getId(){
        return id;
    }
    
    public void setId(Long i){
        id = i;
    }    
    
    public GregorianCalendar getDate(){
        return date;
    }
    
    public String getStringDate(){
        SimpleDateFormat f1 = new SimpleDateFormat("dd.MM.yyyy");        
        return f1.format(date.getTime()).toString();        
    }
    
    public void setDate(GregorianCalendar d){
        date = d;
    }
    
    public String getTitle(){
        return title;
    }
    
    public void setTitle(String t){
        title = t;
    }
    
    @Override
    public int hashCode() {
        int hash = 5;
        hash = 79 * hash + Objects.hashCode(this.id);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Training other = (Training) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }
}
