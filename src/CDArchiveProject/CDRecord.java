package CDArchiveProject;

import java.security.PublicKey;
import java.util.List;

//right click on CDRecord - generate - Getter and Setter - Shift to select all - OK
public class CDRecord {
    String title;
    String author;
    String section;
    int x, y;
    int barcode;
    String description;
    boolean onLoan;

    public CDRecord(String title, String author, String section, int x, int y, int barcode, String description) {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setBarcode(int barcode) { this.barcode = barcode; }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isOnLoan() {
        return onLoan;
    }

    public void setOnLoan(boolean onLoan) {
        this.onLoan = onLoan;
    }

    public CDRecord (int barcode) {
        this.barcode = barcode;
    }

    public CDRecord(String title, String author, String section, int x, int y,
                    int barcode, String description, boolean onLoan) {
        this.title = title;
        this.author = author;
        this.section = section;
        this.x = x;
        this.y = y;
        this.barcode = barcode;
        this.description = description;
        this.onLoan = onLoan;
    }

    public int getBarcode() {
        return barcode;
    }


    public static List<CDRecord> getTestData() {
        CDRecord[] records = new CDRecord[] {
                new CDRecord("Yogi", "Bear", "A", 1, 1, 1001, "A", true ),
                new CDRecord("Yogi", "Bear", "A", 1, 1, 1001, "A", true ),
                new CDRecord("Yogi", "Bear", "A", 1, 1, 1001, "A", true ),
                new CDRecord("Yogi", "Bear", "A", 1, 1, 1001, "A", true ),
                new CDRecord("Yogi", "Bear", "A", 1, 1, 1001, "A", true ),
                new CDRecord("Yogi", "Bear", "A", 1, 1, 1001, "A", true ),

        };

        return List.of(records);
    }

    @Override
    public String toString() {
        return "CDRecord" +
                "title ='" + title + '\'' +
                ", author ='" + author + '\'' +
                ", section ='" + section + '\'' +
                ", x =" + x +
                ", y =" + y +
                ", barcode =" + barcode +
                ", description ='" + description + '\'' +
                ", onLoan =" + onLoan
                ;
    }

    public boolean getOnLoan() {
        return onLoan;
    }
}
