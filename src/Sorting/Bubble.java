package Sorting;
import CDArchiveProject.CDRecord;

import java.util.ArrayList;
import java.util.List;

public class Bubble {

    /**
     * Sorts a random string of numbers
     * using the bubble sort method
     * @param args
     */
    public static void main(String[] args) {
        //Object[] items = null;
        List<CDRecord> records = new ArrayList<>();

        for (int i = 0; i < 20; i++ ) {
            int randomBarcode = (int)(Math.random() *100);
            records.add(new CDRecord(randomBarcode));
        }

        System.out.println("Before sort " + records.toString());
        Bubble.sort(records);
        System.out.println("After sort " + records.toString());


        System.out.println(records);
    }

    /**
     * Sorts the records with the bubble
     * sort method
     * @param records
     */
    public static void sort(List<CDRecord> records) {
        boolean swapped = true;

        //The list is sorted once it can be traversed

        while (swapped) {
            swapped = false;
            for (int i = 1; i < records.size(); i++) {
                CDRecord left = records.get(i-1);
                CDRecord right = records.get(i);
                if (left.getTitle().compareTo(right.getTitle()) > 0) {
                    records.set(i, left);
                    records.set(i-1, right);
                    swapped = true;
                }
            }
        }
    }

}
