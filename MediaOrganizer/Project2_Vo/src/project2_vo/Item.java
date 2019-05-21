package project2_vo;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author Antonio Quang
 */
public class Item implements Comparable<Item>, Serializable {

    private String title;
    private String format;
    private boolean onLoan;
    private String loanedTo;
    private Date dateLoaned;


    public Item(String title, String format) {
        this.title = title;
        this.format = format;
        this.onLoan = false;
        this.loanedTo = null;
        this.dateLoaned = null;
    }
    
    public Item(String title, String format, boolean onLoan) {
        this.title = title;
        this.format = format;
        this.onLoan = onLoan;
        this.loanedTo = null;
        this.dateLoaned = null;
    }

    public Date getDateLoaned() {
        return dateLoaned;
    }

    public boolean isOnLoan() {
        return onLoan;
    }
    
    
    
    public void setOnLoan(boolean onLoan) {
        this.onLoan = onLoan;
    }
    
    public boolean loanItem(String person, Date date) {
        if (this.onLoan) {
            return false;
        }
        this.onLoan = true;
        this.loanedTo = person;
        this.dateLoaned = date;
        
        return true;
    }
    
    public boolean returnItem() {
        if(!(this.onLoan)){
            return false;
        }
        this.onLoan = false;
        
        return true;
    }

    public String getTitle() {
        return title;
    }
    
    @Override
    public int compareTo(Item object) {
        return this.title.compareToIgnoreCase(object.getTitle());

    }
    
    @Override
    public String toString() {
        if(this.onLoan){
            return String.format("%s-%s  (%s On %s)",this.title, this.format, this.loanedTo, this.dateLoaned);
        }
        return String.format("%s-%s", this.title, this.format);
    }

}
