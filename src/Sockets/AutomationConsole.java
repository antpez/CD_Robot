package Sockets;
import CDArchiveProject.CDRecord;
import CDArchiveProject.CDRecordTableModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

/**
 * Fields declared and Client settings
 */
public class AutomationConsole {
    JFrame window;
    List<CDRecord> records;
    CDRecordTableModel tableData;

    JTextField barcodeText, sectionText;
    JButton exitButton, addButton, processButton;
    JComboBox requestedBox;

    String[] sortStrings = { "Add", "Remove", "Retrieve", "Return", "Sort" };

    //Connection to the Server and Message Listener settings
    Client client = new Client("localhost:20000", new MessageListener() {
        @Override
        public void message(String msg, MessageSender sender) {
            System.out.println(msg);
            if (msg.contains("_")) {
                if (msg.contains("Sort_")) {
                    String sectionText = msg.split("_")[1];
                    JOptionPane.showMessageDialog(window, "Sorting Section " + sectionText);
                } else {
                    String[] msgSpilt = msg.split("_");
                    barcodeText.setText(msgSpilt[1]);
                    sectionText.setText(msgSpilt[2]);
                    switch (msgSpilt[0]) {
                        case "Retrieve":
                            requestedBox.setSelectedIndex(2);
                            break;

                        case "Remove":
                            requestedBox.setSelectedIndex(1);
                            break;

                        case "Return":
                            requestedBox.setSelectedIndex(3);
                            break;

                        case "Add to Collection":
                            requestedBox.setSelectedIndex(0);
                            break;
                    }
                }
            }else{

            }
        }
    });

    /**
     * Window settings for automation console
     */
    //Application Settings
    public AutomationConsole() {
        window = new JFrame("Automation Console");
        window.setMinimumSize(new Dimension(500,500));
        window .setSize(new Dimension(500, 500));
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Container content = window.getContentPane();
        content.setLayout(new GridBagLayout());

        createUI();

        window.pack();
        window.setVisible(true);
        window.getContentPane().setBackground(new Color(255,254,233));
    }

    /**
     * ComboBox, Process button, Barcode text
     * and Section text fields. Also the settings
     * for messages to and from
     */
    //Buttons, Text Fields and Messages
    private void createUI() {
        tableData = new CDRecordTableModel(records);

        JLabel requestedLabel = new JLabel("Current Requested Action: ");
        addComponent(requestedLabel, 0,0,1,1,0.05f,0f,
                new Insets(1,1,1,1), GridBagConstraints.WEST);

        requestedBox = new JComboBox(sortStrings);
        requestedBox.getSelectedIndex();
        addComponent(requestedBox, 1, 0, 1, 1, 0.05f, 0f,
                new Insets(1,1,1,1), GridBagConstraints.WEST);

        //Sends the action of the combo box to the robot for processing
        processButton = new JButton("Process");
        processButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               //send reply to archive console
                String outMessage = "";
                switch (requestedBox.getSelectedIndex()) {
                    case 0: outMessage += "Add: "; break;

                    case 1: outMessage += "Remove: "; break;

                    case 2: outMessage += "Retrieve: "; break;

                    case 3: outMessage += "Return: "; break;

                    case 4: outMessage += "Sort: "; break;
                }
                outMessage += barcodeText.getText() + " " + sectionText.getText();
               //outMessage += tableData.getValueAt(cdRecordTable.getSelectedRow(), 0);
               if(client !=  null) {
                  client.sendMessage(outMessage);
                }
               barcodeText.getText();
               sectionText.getText();
               requestedBox.setSelectedIndex(0);
            }
        });
        addComponent(processButton, 4, 0, 1, 1, 0.05f, 0f,
                new Insets(1,1,1,1), GridBagConstraints.EAST);

        JLabel barcodeLabel = new JLabel("Barcode of Selected Item: ");
        addComponent(barcodeLabel, 0,1,1,1,0.05f,0f,
                new Insets(1,1,1,1), GridBagConstraints.WEST);

        //Displays the barcode of the CD
        barcodeText = new JTextField();
        addComponent(barcodeText, 1,1,1,1,0.05f,0f,
                new Insets(1,1,1,1), GridBagConstraints.WEST);

        JLabel sectionLabel = new JLabel("Section: ");
        addComponent(sectionLabel, 2,1,1,1,0.05f,0f,
                new Insets(1,1,1,1), GridBagConstraints.WEST);

        //Displays the section of the CD
        sectionText = new JTextField();
        addComponent(sectionText, 3,1,1,1,0.05f,0f,
                new Insets(1,1,1,1), GridBagConstraints.WEST);

        //Not in use
        addButton = new JButton("Add Item");
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        addComponent(addButton, 4, 1, 1, 1, 0.05f, 0f,
                new Insets(1,1,1,1), GridBagConstraints.EAST);

        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        panel.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        JLabel titleLabel = new JLabel("Archive CDs", SwingConstants.CENTER);
        addComponent(titleLabel,
                1,2, 3,1,0.05f,0.02f,
                new Insets(1,1,1,1), GridBagConstraints.CENTER

        );

        //Exits the Application
        exitButton = new JButton("Click Here to Win!");
        addComponent(exitButton, 4, 5, 1, 1, 0.2f, 0f,
                new Insets(1,1,1,1), GridBagConstraints.EAST);
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                System.exit(0);
            }
        });

    }

    private <C extends Component> C addComponent(
            C component,
            int gridX,
            int gridY,
            int gridWidth,
            int gridHeight,
            float weightX,
            float weightY) {

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.BOTH;
        constraints.gridx = gridX;
        constraints.gridy = gridY;
        constraints.gridwidth = gridWidth;
        constraints.gridheight = gridHeight;
        constraints.weightx = weightX;
        constraints.weighty = weightY;

        window.getContentPane().add(component, constraints);

        return component;
    }

    private <C extends Component> C addComponent(
            C component,
            int gridX,
            int gridY,
            int gridWidth,
            int gridHeight,
            float weightX,
            float weightY,
            Insets insets,
            int anchor
    ) {

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.BOTH;
        constraints.gridx = gridX;
        constraints.gridy = gridY;
        constraints.gridwidth = gridWidth;
        constraints.gridheight = gridHeight;
        constraints.weightx = weightX;
        constraints.weighty = weightY;
        constraints.insets = insets;
        constraints.anchor = anchor;

        window.getContentPane().add(component, constraints);

        return component;
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                Sockets.AutomationConsole client = new Sockets.AutomationConsole();
            }
        });
    }


}
