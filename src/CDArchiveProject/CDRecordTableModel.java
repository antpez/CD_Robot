package CDArchiveProject;

import CDArchiveProject.CDRecord;

import javax.swing.table.AbstractTableModel;
import java.util.List;

//alt enter after writing this line
public class CDRecordTableModel extends AbstractTableModel {

    private String[] coloumnNames = new String[] {
            "Title", "Author", "Section", "X", "Y", "Barcode", "Description", "On-Loan"
    };

    List<CDRecord> records;

    public CDRecordTableModel(List<CDRecord> records) {
        this.records = records;
    }

    @Override
    public String getColumnName (int col) {
        return coloumnNames[col];
    }

    @Override
    public int getRowCount() {
        return records.size();
    }

    @Override
    public int getColumnCount() {
        return coloumnNames.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        CDRecord record = this.records.get(rowIndex);

        switch(columnIndex) {
            case 0: return record.getTitle();
            case 1: return record.getAuthor();
            case 2: return record.getSection();
            case 3: return record.getX();
            case 4: return record.getY();
            case 5: return record.getBarcode();
            case 6: return record.getDescription();
            case 7: return record.isOnLoan();
        }

        return null;
    }
}
