package Sorting;

import CDArchiveProject.CDRecord;

import java.util.ArrayList;
import java.util.List;

/**
 * Sorts a random string of numbers
 * using the bubble sort method
 */
public class Insertion {
    public static void main(String[] args) {
        List<CDRecord> records = new ArrayList<>();

        for (int i= 0; i < 20; i++) {
            int randomBarcode = (int) (Math.random() * 100);
            records.add(new CDRecord(randomBarcode));
        }

        System.out.println("Before sort: " + records.toString());
        Insertion.sort(records);
        System.out.println("After sort: " + records.toString());

    }

    /**
     * Sorts the records by
     * the Insertion sort
     * @param records
     */
    public static void sort(List<CDRecord> records) {
        for (int index = 1; index < records.size(); index++) {
            CDRecord indexRecord = records.get(index);
            int previousIndex = index - 1;

            while(previousIndex >=0 &&
                    records.get(previousIndex).getAuthor().compareToIgnoreCase(indexRecord.getAuthor()) > 0) {

                CDRecord previousRecord = records.get(previousIndex);
                records.set(previousIndex + 1, previousRecord);
                previousIndex--;
            }
            records.set(previousIndex + 1, indexRecord);
        }
    }
}
