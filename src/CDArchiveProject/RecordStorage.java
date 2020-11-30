package CDArchiveProject;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class RecordStorage {

    public static void main(String[] args) {
        List<CDRecord> records = loadRecordList("records.data");
        System.out.println(records);
    }

    public static List<CDRecord> loadRecordList(String filepath) {

        List<CDRecord> records = new ArrayList<>();

        try {
            FileReader fr = new FileReader(filepath);
            BufferedReader br = new BufferedReader(fr);

            String line;
            while ((line = br.readLine()) != null) {
                String[] dataColumns = line.split(";");
                CDRecord record = new CDRecord (
                        //You might need an ID column
                        //dataColumns[0],
                        dataColumns[1],
                        dataColumns[2],
                        dataColumns[3],
                        Integer.parseInt(dataColumns[4]),
                        Integer.parseInt(dataColumns[5]),
                        Integer.parseInt(dataColumns[6]),
                        dataColumns[7],
                        dataColumns[8].equalsIgnoreCase("yes")
                );

                records.add(record);
            }
        } catch (Exception e) {
            System.err.println("Failed " + e.toString());
        }
        return records;
    }

    public static void saveRecordList(String filepath, List<CDRecord> records) {

        try {
            FileWriter fw = new FileWriter(filepath);
            BufferedWriter bw = new BufferedWriter(fw);

            for (int i = 0; i < records.size(); i++)
            {
                String onLoanString = "";
                if (records.get(i).getOnLoan())
                {
                    onLoanString = "yes";
                }
                else {
                    onLoanString = "no";
                }

                bw.write (Integer.toString(i) + ";"
                        + records.get(i).getTitle() + ";"
                        + records.get(i).getAuthor() + ";"
                        + records.get(i).getSection() + ";"
                        + records.get(i).getX() + ";"
                        + records.get(i).getY() + ";"
                        + records.get(i).getBarcode() + ";"
                        + records.get(i).getDescription() + ";"
                        + onLoanString + ";"
                );
                bw.newLine();
            }
            bw.close();

        } catch (Exception e) {
            System.err.println("Failed " + e.toString());
        }
    }
}

    

