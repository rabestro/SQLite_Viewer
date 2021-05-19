package viewer.component;

import org.sqlite.SQLiteDataSource;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.plaf.FontUIResource;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.StyleContext;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.Locale;
import java.util.Vector;

import static java.lang.System.Logger.Level.ERROR;
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
    private JScrollPane tableScrollPane;

    private SQLiteDataSource dataSource;

//    private DataSource dataSource = new SQLiteDataSource();

    public DataBaseViewer() {
        openButton.addActionListener(actionEvent -> {
            LOGGER.log(INFO, actionEvent);
            final var url = "jdbc:sqlite:" + fileNameTextField.getText();
            LOGGER.log(INFO, "URL: {0}", url);
            dataSource = new SQLiteDataSource();
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
                        LOGGER.log(ERROR, e::getMessage);
                    }
                }
            } catch (SQLException e) {
                LOGGER.log(ERROR, e::getMessage);
            }
        });

        tablesComboBox.addItemListener(e -> {
            LOGGER.log(INFO, "Selected item: {0}", tablesComboBox.getSelectedItem());
            queryTextArea.setText("SELECT * FROM " + tablesComboBox.getSelectedItem() + ";");
        });
        executeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                try (final var connection = dataSource.getConnection()) {
                    LOGGER.log(INFO, "Execute SQL: " + queryTextArea.getText());
                    try (final var statement = connection.prepareStatement(queryTextArea.getText())) {
                        final var result = statement.executeQuery();
                        LOGGER.log(INFO, "Columns: {0}, First: {1}",
                                result.getMetaData().getColumnCount(),
                                result.getMetaData().getColumnName(1));
                        final var metaData = result.getMetaData();

                        final var columns = new Vector<String>(metaData.getColumnCount());
                        for (int i = 1; i <= metaData.getColumnCount(); i++) {
                            columns.add(metaData.getColumnName(i));
                        }
                        final var data = new Vector<Vector<Object>>();
                        while (result.next()) {
                            final var row = new Vector<>(metaData.getColumnCount());
                            for (int i = 1; i <= metaData.getColumnCount(); ++i) {
                                row.add(result.getObject(i));
                            }
                            data.add(row);
                        }
                        final var tableModel = new DefaultTableModel(data, columns);
                        tableData.setModel(tableModel);
                    } catch (SQLException e) {
                        LOGGER.log(ERROR, e::getMessage);
                    }
                } catch (SQLException e) {
                    LOGGER.log(ERROR, e::getMessage);
                }
            }
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
        mainPanel.setAutoscrolls(true);
        mainPanel.setName("MainPanel");
        mainPanel.setToolTipText("");
        mainPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(new Color(-4473925)), "Database Viewer", TitledBorder.LEFT, TitledBorder.DEFAULT_POSITION, null, null));
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
        tableScrollPane = new JScrollPane();
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.BOTH;
        mainPanel.add(tableScrollPane, gbc);
        tableScrollPane.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(new Color(-4473925)), "Table data", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, this.$$$getFont$$$(null, -1, -1, tableScrollPane.getFont()), null));
        tableData = new JTable();
        tableData.setName("Table");
        tableData.setToolTipText("the data from a table");
        tableScrollPane.setViewportView(tableData);
    }

    /**
     * @noinspection ALL
     */
    private Font $$$getFont$$$(String fontName, int style, int size, Font currentFont) {
        if (currentFont == null) return null;
        String resultName;
        if (fontName == null) {
            resultName = currentFont.getName();
        } else {
            Font testFont = new Font(fontName, Font.PLAIN, 10);
            if (testFont.canDisplay('a') && testFont.canDisplay('1')) {
                resultName = fontName;
            } else {
                resultName = currentFont.getName();
            }
        }
        Font font = new Font(resultName, style >= 0 ? style : currentFont.getStyle(), size >= 0 ? size : currentFont.getSize());
        boolean isMac = System.getProperty("os.name", "").toLowerCase(Locale.ENGLISH).startsWith("mac");
        Font fontWithFallback = isMac ? new Font(font.getFamily(), font.getStyle(), font.getSize()) : new StyleContext().getFont(font.getFamily(), font.getStyle(), font.getSize());
        return fontWithFallback instanceof FontUIResource ? fontWithFallback : new FontUIResource(fontWithFallback);
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return mainPanel;
    }


}
