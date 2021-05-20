package viewer.component;

import org.sqlite.SQLiteDataSource;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.SQLException;
import java.util.Vector;

import static java.lang.System.Logger.Level.*;

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

    public DataBaseViewer() {
        openButton.addActionListener(this::openDatabase);
        tablesComboBox.addActionListener(this::selectTable);
        executeButton.addActionListener(this::executeQuery);
    }

    private void openDatabase(ActionEvent actionEvent) {
        LOGGER.log(DEBUG, actionEvent);
        if (!Files.isReadable(Path.of(fileNameTextField.getText()))) {
            JOptionPane.showMessageDialog(new Frame(), "File doesn't exist!");
            queryTextArea.setEnabled(false);
            executeButton.setEnabled(false);
            return;
        }
        final var url = "jdbc:sqlite:" + fileNameTextField.getText();
        LOGGER.log(INFO, "URL: {0}", url);
        dataSource = new SQLiteDataSource();
        dataSource.setUrl(url);

        try (final var connection = dataSource.getConnection();
             final var statement = connection.createStatement();
             final var tables = statement.executeQuery(SQL_PUBLIC_TABLES)) {

            queryTextArea.setText("");
            tablesComboBox.removeAllItems();
            while (tables.next()) {
                final var name = tables.getString("name");
                tablesComboBox.addItem(name);
                LOGGER.log(DEBUG, "Table: {0}", name);
            }
            queryTextArea.setEnabled(true);
            executeButton.setEnabled(true);
        } catch (SQLException e) {
            LOGGER.log(ERROR, e::getMessage);
        }
    }

    private void selectTable(ActionEvent actionEvent) {
        LOGGER.log(INFO, "Selected Table: {0}", tablesComboBox.getSelectedItem());
        queryTextArea.setText("SELECT * FROM " + tablesComboBox.getSelectedItem() + ";");
    }

    private void executeQuery(ActionEvent actionEvent) {
        LOGGER.log(DEBUG, "Execute SQL: " + queryTextArea.getText());
        try (final var connection = dataSource.getConnection();
             final var statement = connection.prepareStatement(queryTextArea.getText());
             final var result = statement.executeQuery()) {
            LOGGER.log(DEBUG, "Columns: {0}, First: {1}",
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
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }

}
