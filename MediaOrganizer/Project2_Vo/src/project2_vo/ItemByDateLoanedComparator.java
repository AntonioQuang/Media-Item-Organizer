package project2_vo;

import java.util.Comparator;

public class ItemByDateLoanedComparator implements Comparator<Item> {
    @Override
    public int compare(Item o1, Item o2) {
        if (o1.isOnLoan() && o2.isOnLoan()) {
            if(o1.getDateLoaned().compareTo(o2.getDateLoaned()) != 0){
                return 0 - o1.getDateLoaned().compareTo(o2.getDateLoaned());
            }else{
            return o1.getTitle().compareToIgnoreCase(o2.getTitle());
            }
        } else if (o2.isOnLoan()) {
            return 2;
        } else{
            return o1.getTitle().compareToIgnoreCase(o2.getTitle());
        }
    }
}
