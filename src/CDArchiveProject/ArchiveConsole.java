package CDArchiveProject;

import Lists.DoubleLinkedList;
import Sockets.AutomationConsole;
import Sockets.Client;
import Sockets.MessageListener;
import Sockets.MessageSender;
import Sorting.Bubble;
import Sorting.Insertion;
import Sorting.Selection;
import Trees.BinaryTree;
import org.apache.commons.lang3.RandomStringUtils;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.awt.event.*;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Fields declared in the class
 */
public class ArchiveConsole {
    JFrame window;
    List<CDRecord> records;
    CDRecordTableModel tableData;
    CDRecord focusedRecord;
    JTable cdRecordTable;
    BinaryTree tree = new BinaryTree();
    Client client;
    DoubleLinkedList processLogList = new DoubleLinkedList();
    HashMap map;

    JTextField titleText;
    JTextField authorText;
    JTextField sectionText;
    JTextField xText;
    JTextField yText;
    JTextField barcodeText;
    JTextArea descText, processLog;

    /**
     * Loads all the data from the records.data, Client settings
     * and Window settings
     */
    //Application Settings
    public ArchiveConsole () {
        records = RecordStorage.loadRecordList("records.data");

        //Connection to the server
        client = new Client("localhost:20000", new MessageListener() {
            @Override
            public void message(String msg, MessageSender sender) {
                processLogList.append(new DoubleLinkedList.Node(msg));
                processLog.setText(processLogList.toString());
            }
        });

        window = new JFrame("Archive Console");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Window Settings
        window.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {

            }

            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);

            }

            @Override
            public void windowClosed(WindowEvent e) {

            }

            @Override
            public void windowIconified(WindowEvent e) {

            }

            @Override
            public void windowDeiconified(WindowEvent e) {

            }

            @Override
            public void windowActivated(WindowEvent e) {

            }

            @Override
            public void windowDeactivated(WindowEvent e) {

            }
        });
        window.getContentPane().setLayout(new GridBagLayout());

        createUI();

        window.pack();
        window.setMinimumSize(new Dimension(1100, 600));
        window .setSize(new Dimension(1100, 600));
        window.setVisible(true);
        window.getContentPane().setBackground(new Color(255,254,233));
    }

    /**
     * Search fields and exit button
     */
    //Main window Search and Exit
    private void createUI() {
        JLabel searchLabel = new JLabel("Search String:");
                addComponent(
                        window.getContentPane(), searchLabel,
                        GridBagConstraints.BOTH,
                        0,0,1,1,0.0f,0.0f,
                        new Insets(5,2,5,2), GridBagConstraints.WEST
                );

                JTextField searchText = new JTextField();
                addComponent(
                        window.getContentPane(), searchText,
                        GridBagConstraints.BOTH,
                        1,0,1,1,40.0f,0.0f,
                        new Insets(5,2,5,2), GridBagConstraints.WEST
                );

                        tableData = new CDRecordTableModel(records);
                        //Search Box
                        JButton searchButton = new JButton("Search");
                        searchButton.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                String value = searchText.getText();

                                for (int row = 0; row <= tableData.getColumnCount() -1; row++)
                                {
                                    for (int col = 0; col <= tableData.getColumnCount() - 1; col++) {
                                        if (value.equals(tableData.getValueAt(row, col))) {
                                            cdRecordTable.scrollRectToVisible(cdRecordTable.getCellRect(row, 0, true));

                                            cdRecordTable.setRowSelectionInterval(row, row);

                                            for (int i = 0; i <= tableData.getColumnCount() - 1; i++) {
                                                cdRecordTable.getColumnModel().getColumn(i).setCellRenderer(new HighlightRenderer());
                                            }
                                        }
                                    }
                                }
                            }
                        });
                addComponent(
                        window.getContentPane(), searchButton,
                        GridBagConstraints.BOTH,
                        2,0,1,1,0.0f,0.0f,
                        new Insets(5,2,5,2), GridBagConstraints.WEST
                );



                JLabel messageLabel = new JLabel("Built by AP: V:0.3");
                addComponent(
                        window.getContentPane(), messageLabel,
                        GridBagConstraints.BOTH,
                        0,3,1,1,0.0f,0.0f,
                        new Insets(5,2,5,2), GridBagConstraints.WEST
                );

        JButton exitButton = new JButton("Click Here to Win!");
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        addComponent(
                window.getContentPane(), exitButton,
                GridBagConstraints.HORIZONTAL,
                3,3,2,1,50.0f,0.0f

        );

        JPanel archiveListPanel = createArchiveListPanel();
        addComponent(
                window.getContentPane(), archiveListPanel,
                GridBagConstraints.BOTH,
                0,1,3,1,70.0f,40.0f,
                new Insets(5,2,5,2), GridBagConstraints.WEST
        );

        JPanel processLogPanel = createProcessLogPanel();
        addComponent(
                window.getContentPane(), processLogPanel,
                GridBagConstraints.BOTH,
                0,2,3,1,70.0f,40.0f,
                new Insets(5,2,5,2), GridBagConstraints.WEST
        );

        JPanel recordPanel = createRecordPanel();
        addComponent(
                window.getContentPane(), recordPanel,
                GridBagConstraints.BOTH,
                3,0,1,2,30.0f,40.f,
                new Insets(5,2,5,2), GridBagConstraints.WEST
        );

        JPanel actionRequestPanel = createActionRequestPanel();
        addComponent(
                window.getContentPane(), actionRequestPanel,
                GridBagConstraints.BOTH,
                3,2,1,1,30.0f,40.0f,
                new Insets(5,2,5,2), GridBagConstraints.WEST
        );
    }

    /**
     * By Title, Author and Barcode Buttons
     * and JTable settings
     * @return
     */
    //JTable By Title, Author, Barcode
    private JPanel createArchiveListPanel () {
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        panel.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        JLabel titleLabel = new JLabel("Archive CDs", SwingConstants.CENTER);
        addComponent(
                panel, titleLabel,
                GridBagConstraints.HORIZONTAL,
                0,0,3,1,1.0f,1.0f
        );

        //Table Data
        cdRecordTable = new JTable();
        cdRecordTable.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int selectedIndex = cdRecordTable.getSelectedRow();
                focusedRecord = records.get(selectedIndex);

                titleText.setText(focusedRecord.getTitle());
                authorText.setText(focusedRecord.getAuthor());
                sectionText.setText(focusedRecord.getSection());
                xText.setText(Integer.toString(focusedRecord.getX()));
                yText.setText(Integer.toString(focusedRecord.getY()));
                barcodeText.setText(Integer.toString(focusedRecord.getBarcode()));
                descText.setText(focusedRecord.getDescription());
            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });

        //Set the table model here
        cdRecordTable.setModel(tableData);

        cdRecordTable.setFillsViewportHeight(true);
        JScrollPane cdRecordTableScrollPane = new JScrollPane(cdRecordTable);
        addComponent(
                panel, cdRecordTableScrollPane,
                GridBagConstraints.BOTH,
                0,1,4,1,100.0f,10.0f
        );

        JLabel sortLabel = new JLabel("Sort:");
        addComponent(
                panel, sortLabel,
                GridBagConstraints.BOTH,
                0,2,1,1,0.0f,0.0f,
                new Insets(5,5,5,5), GridBagConstraints.WEST
        );

        JButton byTitleButton = new JButton("By Title");
        byTitleButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Bubble.sort(records);
                tableData.fireTableDataChanged();

                for (int cdCounter = 0; cdCounter <records.size(); cdCounter++) {
                    tree.insert(new BinaryTree.Node(records.get(cdCounter).getBarcode(),records.get(cdCounter)));
                }
            }
        });
        addComponent(
                panel, byTitleButton,
                GridBagConstraints.VERTICAL,
                1,2,1,1,0.0f,0.0f,
                new Insets(5,2,5,2), GridBagConstraints.WEST
        );

        JButton byAuthorButton = new JButton("By Author");
        byAuthorButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Insertion.sort(records);
                tableData.fireTableDataChanged();

                for (int cdCounter = 0; cdCounter <records.size(); cdCounter++) {
                    tree.insert(new BinaryTree.Node(records.get(cdCounter).getBarcode(),records.get(cdCounter)));
                }
            }
        });
        addComponent(
                panel, byAuthorButton,
                GridBagConstraints.VERTICAL,
                2,2,1,1,0.0f,0.0f,
                new Insets(5,2,5,2), GridBagConstraints.WEST
        );

        JButton byBarcodeButton = new JButton("By Barcode");
        byBarcodeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Selection.sort(records);
                tableData.fireTableDataChanged();

            }
        });
        addComponent(
                panel, byBarcodeButton,
                GridBagConstraints.VERTICAL,
                3,2,1,1,0.0f,0.0f,
                new Insets(5,5,5,5), GridBagConstraints.EAST
        );

        return panel;
    }

    /**
     * New item and Save/Update Button
     * Text fields that connect to the JTable
     * @return
     */
    //New Item and Save/Update
    private JPanel createRecordPanel () {
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        panel.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        JLabel titleLabel = new JLabel("Title:");
        addComponent(
                panel, titleLabel,
                GridBagConstraints.BOTH,
                0,0,1,1,0.05f,0.05f,
                new Insets(5,2,5,2), GridBagConstraints.WEST
        );

        JLabel authorLabel = new JLabel("Author:");
        addComponent(
                panel, authorLabel,
                GridBagConstraints.BOTH,
                0,1,1,1,0.05f,0.05f,
                new Insets(5,2,5,2), GridBagConstraints.WEST
        );

        JLabel sectionLabel = new JLabel("Section:");
        addComponent(
                panel, sectionLabel,
                GridBagConstraints.NONE,
                0,2,1,1,0.05f,0.05f,
                new Insets(5,2,5,2), GridBagConstraints.WEST
        );

        JLabel xLabel = new JLabel("X:");
        addComponent(
                panel, xLabel,
                GridBagConstraints.NONE,
                0,3,1,1,0.05f,0.05f,
                new Insets(5,2,5,2), GridBagConstraints.WEST
        );

        JLabel yLabel = new JLabel("Y:");
        addComponent(
                panel, yLabel,
                GridBagConstraints.NONE,
                0,4,1,1,0.05f,0.05f,
                new Insets(5,2,5,2), GridBagConstraints.WEST
        );

        JLabel barcodeLabel = new JLabel("Barcode:");
        addComponent(
                panel, barcodeLabel,
                GridBagConstraints.BOTH,
                0,5,1,1,0.05f,0.05f,
                new Insets(5,2,5,2), GridBagConstraints.WEST
        );

        JLabel descLabel = new JLabel("Description:");
        addComponent(
                panel, descLabel,
                GridBagConstraints.BOTH,
                0,6,1,1,0.05f,0.05f,
                new Insets(5,2,5,2), GridBagConstraints.WEST
        );

        titleText = new JTextField();
        addComponent(
                panel, titleText,
                GridBagConstraints.BOTH,
                1,0,1,1,1f,0.0f,
                new Insets(5,2,5,2), GridBagConstraints.WEST
        );

        authorText = new JTextField();
        addComponent(
                panel, authorText,
                GridBagConstraints.BOTH,
                1,1,1,1,1f,0.0f,
                new Insets(5,2,5,2), GridBagConstraints.WEST
        );

        sectionText = new JTextField();
        addComponent(
                panel, sectionText,
                GridBagConstraints.HORIZONTAL,
                1,2,1,1,0.05f,0.0f,
                new Insets(5,2,5,2), GridBagConstraints.WEST
        );

        xText = new JTextField();
        addComponent(
                panel, xText,
                GridBagConstraints.HORIZONTAL,
                1,3,1,1,1f,0.0f,
                new Insets(5,2,5,2), GridBagConstraints.WEST
        );

        yText = new JTextField();
        addComponent(
                panel, yText,
                GridBagConstraints.HORIZONTAL,
                1,4,1,1,1f,0.0f,
                new Insets(5,2,5,2), GridBagConstraints.WEST
        );

        barcodeText = new JTextField();
        addComponent(
                panel, barcodeText,
                GridBagConstraints.BOTH,
                1,5,1,1,1f,0.0f,
                new Insets(5,2,5,2), GridBagConstraints.WEST
        );

        descText = new JTextArea(2,20);
        descText.setLineWrap(true);
        addComponent(
                panel, descText,
                GridBagConstraints.BOTH,
                1,6,1,1,1f,1.0f,
                new Insets(5,2,5,2), GridBagConstraints.WEST
        );

        //Clears the fields for a New item to be entered
        JButton newItem = new JButton("New Item");
        newItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                focusedRecord = null;
                titleText.setText("");
                authorText.setText("");
                sectionText.setText("");
                xText.setText("");
                yText.setText("");
                String barcodeRan = RandomStringUtils.randomNumeric(8);
                barcodeText.setText(barcodeRan);
                descText.setText("");
            }
        });
        addComponent(
                panel, newItem,
                GridBagConstraints.VERTICAL,
                0,7,1,1,0.0f,0.0f,
                new Insets(5,2,5,2), GridBagConstraints.WEST
        );

        //Saves the entry to the table or Updates an existing entry
        JButton saveUpdate = new JButton("Save/Update");
        saveUpdate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (focusedRecord != null) {
                    focusedRecord.setTitle(titleText.getText());
                    focusedRecord.setAuthor(authorText.getText());
                    focusedRecord.setSection(sectionText.getText());
                    focusedRecord.setX(Integer.parseInt(xText.getText()));
                    focusedRecord.setY(Integer.parseInt(yText.getText()));
                    focusedRecord.setBarcode(Integer.parseInt(barcodeText.getText()));
                    focusedRecord.setDescription(descText.getText());

                    tableData.fireTableDataChanged();
                    RecordStorage.saveRecordList("records.data", records);

                } else {
                   CDRecord newRecord = new CDRecord(
                           titleText.getText(), authorText.getText(),
                           sectionText.getText(), (Integer.parseInt(xText.getText())),
                           (Integer.parseInt(yText.getText())),
                           (Integer.parseInt(barcodeText.getText())),
                           descText.getText());

                   records.add(newRecord);
                   tableData.fireTableDataChanged();
                }
            }
        });
        addComponent(
                panel, saveUpdate,
                GridBagConstraints.VERTICAL,
                2,7,1,1,0.0f,0.0f,
                new Insets(5,2,5,2), GridBagConstraints.WEST
        );

        return panel;
    }

    /**
     * Process log text area
     * Binary tree and HashMaps
     * @return
     */
    //Process Log and Binary Trees
    private JPanel createProcessLogPanel () {
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        panel.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        JLabel processLabel = new JLabel("Process Log:");
        addComponent(
                panel, processLabel,
                GridBagConstraints.BOTH,
                0,0,1,1,0.0f,0.0f,
                new Insets(5,5,5,5), GridBagConstraints.WEST
        );

        //Sends the text area message to the robot
        JButton processButton = new JButton("Process Log:");
        processButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                processLog.setText(processLogList.toString());
            }
        });
        addComponent(
                panel, processButton,
                GridBagConstraints.HORIZONTAL,
                4,0,1,1,0.0f,0.0f,
                new Insets(5,5,5,5), GridBagConstraints.EAST
        );

        //Displays all the messages that come and go to the robot
        processLog = new JTextArea();
        JScrollPane processScroll= new JScrollPane(processLog);
        processLog.setLineWrap(true);
        addComponent(
                panel, processScroll,
                GridBagConstraints.BOTH,
                0,1,5,1,100.0f,10.0f
        );

        JLabel binaryLabel = new JLabel("Display Binary Tree:");
        addComponent(
                panel, binaryLabel,
                GridBagConstraints.BOTH,
                0,2,1,1,0.0f,0.0f,
                new Insets(5,2,5,2), GridBagConstraints.WEST
        );

        //Displays the By Title info in a Pre-Order Binary Tree
        JButton preorderButton = new JButton("Pre-Order:");
        preorderButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ArrayList<BinaryTree.Node> treeArray = tree.traversePreOrder();

               for (int i = 0; i < treeArray.size(); i++) {
                   processLog.append(treeArray.get(i).toString() + "\n");
               }
            }
        });
        addComponent(
                panel, preorderButton,
                GridBagConstraints.BOTH,
                1,2,1,1,0.0f,0.0f,
                new Insets(5,2,5,2), GridBagConstraints.WEST
        );

        //Displays the By Title info in a In-Order Binary Tree
        JButton inorderButton = new JButton("In-Order:");
        inorderButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ArrayList<BinaryTree.Node> treeArray = tree.traverseInOrder();

                for (int i = 0; i < treeArray.size(); i++) {
                    processLog.append(treeArray.get(i).toString() + "\n");
                }
            }
        });
        addComponent(
                panel, inorderButton,
                GridBagConstraints.BOTH,
                2,2,1,1,0.0f,0.0f,
                new Insets(5,2,5,2), GridBagConstraints.WEST
        );

        //Displays the By Title info in a Post-Order Binary Tree
        JButton postorderButton = new JButton("Post-Order:");
        postorderButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ArrayList<BinaryTree.Node> treeArray = tree.traversePostOrder();

                for (int i = 0; i < treeArray.size(); i++) {
                    processLog.append(treeArray.get(i).toString() + "\n");
                }
            }
        });
        addComponent(
                panel, postorderButton,
                GridBagConstraints.BOTH,
                3,2,1,1,0.0f,0.0f,
                new Insets(5,2,5,2), GridBagConstraints.WEST
        );

        JButton graphicalButton = new JButton("Graphical:");
        addComponent(
                panel, graphicalButton,
                GridBagConstraints.BOTH,
                4,2,1,1,0.0f,0.0f,
                new Insets(5,2,5,2), GridBagConstraints.WEST
        );

        JLabel hashmapLabel = new JLabel("HashMap/Set:");
        addComponent(
                panel, hashmapLabel,
                GridBagConstraints.BOTH,
                0,3,1,1,0.0f,0.0f,
                new Insets(5,2,5,2), GridBagConstraints.WEST
        );

        //Saves the HashMap as a Text File
        JButton saveButton = new JButton("Save:");
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                try {
                    map = new HashMap<>();
                    for (int i = 0; i < records.size(); i++ ) {
                        map.put(records.get(i).getBarcode(), records.get(i).getTitle());
                    }

                    FileWriter fw = new FileWriter("Map.txt");
                    fw.write(map.toString());
                    fw.close();
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
        });
        addComponent(
                panel, saveButton,
                GridBagConstraints.BOTH,
                1,3,1,1,0.0f,0.0f,
                new Insets(5,2,5,2), GridBagConstraints.WEST
        );

        //Displays the HashMap of the Barcodes
        JButton displayButton = new JButton("Display:");
        displayButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               map = new HashMap<>();
               for (int i = 0; i < records.size(); i++ ) {
                   map.put(records.get(i).getBarcode(), records.get(i).getTitle());
               }
                processLog.append(map.toString() + "\n");
            }
        });
        addComponent(
                panel, displayButton,
                GridBagConstraints.BOTH,
                2,3,1,1,0.0f,0.0f,
                new Insets(5,2,5,2), GridBagConstraints.WEST
        );

        return panel;
    }

    /**
     * Actions that send messages to the automation console
     * Retrieve, Remove, Return, Add and Sorts
     * @return
     */
    //Automation Console and DLL
    private JPanel createActionRequestPanel () {
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        panel.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        JLabel actionRequestLabel = new JLabel("Automation Action Request for the Item Above:", SwingConstants.CENTER);
        addComponent(
                panel, actionRequestLabel,
                GridBagConstraints.HORIZONTAL,
                0,0,2,1,1.0f,1.0f

        );
        //Sends the Retrieve CD message to the robot
        JButton retrieveButton = new JButton("Retrieve");
        retrieveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                client.sendMessage("Retrieve_" + focusedRecord.getBarcode() + "_" + focusedRecord.getSection());
            }
        });
        addComponent(
                panel, retrieveButton,
                GridBagConstraints.HORIZONTAL,
                0,1,1,1,1.0f,0.0f,
                new Insets(5,5,5,5), GridBagConstraints.WEST

        );
        //Sends the Remove CD message to the robot
        JButton removeButton = new JButton("Remove");
        removeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                client.sendMessage("Remove_" + focusedRecord.getBarcode() + "_" + focusedRecord.getSection());
            }
        });
        addComponent(
                panel, removeButton,
                GridBagConstraints.HORIZONTAL,
                1,1,1,1,1.0f,0.0f,
                new Insets(5,5,5,5), GridBagConstraints.WEST

        );
        //Sends the Return CD message to the robot
        JButton returnButton = new JButton("Return");
        returnButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                client.sendMessage("Return_" + focusedRecord.getBarcode() + "_" + focusedRecord.getSection());
            }
        });
        addComponent(
                panel, returnButton,
                GridBagConstraints.HORIZONTAL,
                0,2,1,1,1.0f,0.0f,
                new Insets(5,5,5,5), GridBagConstraints.WEST

        );
        //Sends the Add CD message to the robot
        JButton addButton = new JButton("Add to Collection");
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                client.sendMessage("Add to Collection_" + focusedRecord.getBarcode() + "_" + focusedRecord.getSection());

            }
        });
        addComponent(
                panel, addButton,
                GridBagConstraints.HORIZONTAL,
                1,2,1,1,1.0f,0.0f,
                new Insets(5,5,5,5), GridBagConstraints.WEST

        );

        JLabel sortLabel = new JLabel("Sort Section");
        addComponent(
                panel, sortLabel,
                GridBagConstraints.BOTH,
                0,3,1,1,0.0f,0.0f,
                new Insets(5,5,5,5), GridBagConstraints.CENTER
        );

        JTextField sortText = new JTextField();
        addComponent(
                panel, sortText,
                GridBagConstraints.BOTH,
                1,3,1,1,0.5f,0.0f,
                new Insets(5,5,5,5), GridBagConstraints.CENTER
        );
        //Sends the Random sort CD message to the robot
        JButton randomButton = new JButton("Random Collection Sort");
        randomButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                client.sendMessage("Random Sort_ " + sortText.getText());
            }
        });
        addComponent(
                panel, randomButton,
                GridBagConstraints.VERTICAL,
                1,4,1,1,1.0f,0.0f,
                new Insets(5,5,5,5), GridBagConstraints.EAST

        );
        //Sends the Mostly Sort CD message to the robot
        JButton mostlyButton = new JButton("Mostly Sorted Sort");
        mostlyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                client.sendMessage("Mostly Sorted Sort_ " + sortText.getText());
            }
        });
        addComponent(
                panel, mostlyButton,
                GridBagConstraints.VERTICAL,
                1,5,1,1,1.0f,0.0f,
                new Insets(5,5,5,5), GridBagConstraints.EAST

        );
        //Sends the Reverse Sort CD message to the robot
        JButton reverseButton = new JButton("Reverse Order Sort");
        reverseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                client.sendMessage("Reverse Order Sort_ " + sortText.getText());
            }
        });
        addComponent(
                panel, reverseButton,
                GridBagConstraints.BOTH,
                1,6,1,1,1.0f,0.0f,
                new Insets(5,5,5,5), GridBagConstraints.EAST
        );

        return panel;
    }

    //Adding function to a button is buttonName.addActionLister(new Action Lister)

    /**
     * Gridbag layout components
     * @param contentPane
     * @param component
     * @param fill
     * @param gridx
     * @param gridy
     * @param gridweight
     * @param gridheight
     * @param weightx
     * @param weighty
     * @param <C>
     */
    private <C extends Component> void addComponent(
            Container contentPane, C component,
            int fill, int gridx, int gridy,
            int gridweight, int gridheight,
            float weightx, float weighty
    ) {
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = fill;
        constraints.gridx = gridx; //Across
        constraints.gridy = gridy; //Down
        constraints.gridwidth = gridweight; //Size Across
        constraints.gridheight = gridheight; //Size Down
        constraints.weightx = weightx; //Thickness Across
        constraints.weighty = weighty; //Thickness Down

        contentPane.add(component, constraints);
    }

    private <C extends Component> void addComponent(
            Container contentPane, C component,
            int fill, int gridx, int gridy,
            int gridweight, int gridheight,
            float weightx, float weighty,
            Insets insets, int anchor
    ) {
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = fill;
        constraints.gridx = gridx; //Across
        constraints.gridy = gridy; //Down
        constraints.gridwidth = gridweight; //Size Across
        constraints.gridheight = gridheight; //Size Down
        constraints.weightx = weightx; //Thickness Across
        constraints.weighty = weighty; //Thickness Down
        constraints.insets = insets;
        constraints.anchor = anchor;

        contentPane.add(component, constraints);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new ArchiveConsole();
            }
        });
    }
    private class HighlightRenderer extends DefaultTableCellRenderer
    {

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column)
        {

            super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

            if(row == table.getSelectedRow())
            {

                setBorder(BorderFactory.createMatteBorder(2, 1, 2, 1, (new Color(255,254,233))));
            }

            return this;
        }
    }

}
