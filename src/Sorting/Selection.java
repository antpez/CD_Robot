package Sorting;

import CDArchiveProject.CDRecord;

import java.util.ArrayList;
import java.util.List;

public class Selection {

    /**
     * Sorts a random string of numbers
     * using the bubble sort method
     * @param args
     */
    public static void main(String[] args) {
        List<CDRecord> records = new ArrayList<>();

        for (int i = 0; i < 20; i++) {
            int randomBarcode = (int)(Math.random() * 100);
            records.add(new CDRecord(randomBarcode));
        }

        System.out.println("Before sort: " + records.toString());
        Selection.sort(records);
        System.out.println("After sort: " + records.toString());
    }

    /**
     * Sorts the records by the
     * Selection sort method
     * @param records
     */
    public static void sort(List<CDRecord> records) {
        for (int index = 0; index < records.size() -1; index++) {
            int smallestIndex = index;

            for(int currentIndex = index+1; currentIndex < records.size(); currentIndex++) {
                if (records.get(currentIndex).getBarcode() <
                records.get(smallestIndex).getBarcode()) {
                    smallestIndex = currentIndex;
                }
            }

            //swap
            CDRecord smallestRecord = records.get(smallestIndex);
            CDRecord indexRecord = records.get(index);
            records.set(index, smallestRecord);
            records.set(smallestIndex, indexRecord);
        }
    }
}

