package viewer.component;

import org.sqlite.SQLiteDataSource;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.sql.SQLException;

import static java.lang.System.Logger.Level.INFO;

public class DataBaseViewer {
    private static final System.Logger LOGGER = System.getLogger("");
    private static final String SQL_PUBLIC_TABLES =
            "SELECT name FROM sqlite_master WHERE type ='table' AND name NOT LIKE 'sqlite_%';";

    private JPanel mainPanel;
    private JButton openButton;
    private JTextField fileNameTextField;
    private JComboBox<String> tablesComboBox;
    private JTextArea queryTextArea;
    private JButton executeButton;
    private JTable tableData;

    public DataBaseViewer() {
        openButton.addActionListener(actionEvent -> {
            LOGGER.log(INFO, actionEvent);
            final var url = "jdbc:sqlite:" + fileNameTextField.getText();
            LOGGER.log(INFO, "URL: {0}", url);

            final var dataSource = new SQLiteDataSource();
            dataSource.setUrl(url);
            try (final var connection = dataSource.getConnection()) {
                if (connection.isValid(5)) {
                    System.out.println("Connection is valid.");
                    try (final var statement = connection.createStatement()) {
                        try (final var tables = statement.executeQuery(SQL_PUBLIC_TABLES)) {
                            queryTextArea.setText("");
                            tablesComboBox.removeAllItems();
                            while (tables.next()) {
                                final var name = tables.getString("name");
                                tablesComboBox.addItem(name);
                                LOGGER.log(INFO, "Table: {0}", name);
//                                if (queryTextArea.getText().isBlank()) {
//                                    queryTextArea.setText("SELECT * FROM " + name + ";");
//                                }
                            }
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });

        tablesComboBox.addItemListener(e -> {
            LOGGER.log(INFO, "Selected item: {0}", tablesComboBox.getSelectedItem());
            queryTextArea.setText("SELECT * FROM " + tablesComboBox.getSelectedItem() + ";");
        });
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }

    {
// GUI initializer generated by IntelliJ IDEA GUI Designer
// >>> IMPORTANT!! <<<
// DO NOT EDIT OR ADD ANY CODE HERE!
        $$$setupUI$$$();
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        mainPanel = new JPanel();
        mainPanel.setLayout(new GridBagLayout());
        mainPanel.setBorder(BorderFactory.createTitledBorder(null, "Database Viewer", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, null, null));
        fileNameTextField = new JTextField();
        fileNameTextField.setColumns(25);
        fileNameTextField.setName("FileNameTextField");
        fileNameTextField.setText("");
        GridBagConstraints gbc;
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);
        mainPanel.add(fileNameTextField, gbc);
        openButton = new JButton();
        openButton.setName("OpenFileButton");
        openButton.setText("Open");
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);
        mainPanel.add(openButton, gbc);
        tablesComboBox = new JComboBox();
        tablesComboBox.setName("TablesComboBox");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);
        mainPanel.add(tablesComboBox, gbc);
        queryTextArea = new JTextArea();
        queryTextArea.setColumns(25);
        queryTextArea.setLineWrap(false);
        queryTextArea.setMargin(new Insets(2, 2, 2, 2));
        queryTextArea.setName("QueryTextArea");
        queryTextArea.setRows(4);
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(5, 5, 5, 5);
        mainPanel.add(queryTextArea, gbc);
        executeButton = new JButton();
        executeButton.setName("ExecuteQueryButton");
        executeButton.setText("Execute");
        executeButton.setVerticalAlignment(0);
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.NORTH;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);
        mainPanel.add(executeButton, gbc);
        tableData = new JTable();
        tableData.setName("Table");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.BOTH;
        mainPanel.add(tableData, gbc);
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return mainPanel;
    }


}
