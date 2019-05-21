
package project2_vo;

import java.util.ArrayList;
import java.util.Date;
import javafx.collections.ObservableList;

/**
 *
 * @author Antonio Quang
 */
public class Library {

    private ObservableList<Item> collection;

    public Library(ObservableList<Item> collection) {
        this.collection = collection;
    }
    
    public boolean addItem(String title, String format) {
        
        for(Item item: collection){
            if(item.getTitle().equals(title)){
                return false;
            }
        }
        collection.add(new Item(title, format));
        return true;
    }

    public boolean removeItem(String title) {
        Item toRemove = null;
        for(Item item: collection){
            if(item.getTitle().equals(title)){
                toRemove = item;
            }
        }
        if(toRemove != null){
            collection.remove(toRemove);
            return true;
        }
        return false;
    }
        
    public String markLoan(String title, String person, Date date) {
        Item itemToLoan = null;
        for(Item item: collection){
            if(item.getTitle().equals(title)){
                itemToLoan = item;
            }
        }
        if(itemToLoan != null){
            if(!(itemToLoan.loanItem(person, date))){
                return "Error, this item is already on loan.";
            }
        }else if(!(itemToLoan != null)){
            return "Error, no item with that title exists.";
        }
        return "";
    }
    
    public String markReturned(String title) {
        Item itemToReturn = null;
        for(Item item: collection){
            if(item.getTitle().equals(title)){
                itemToReturn = item;
            }
        }
        if(itemToReturn != null){
            if(!(itemToReturn.returnItem())){
                return "Error, this item is not on loan.";
            }
        }else if(!(itemToReturn != null)){
            return "Error, no item with that title exists.";
        }
        return "";
    }
    
}
