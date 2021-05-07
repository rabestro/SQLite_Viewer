package viewer;

import viewer.component.FileName;

import javax.swing.*;
import java.awt.*;

import static java.lang.System.Logger.Level.INFO;

public class SQLiteViewer extends JFrame {
    private static final System.Logger LOGGER = System.getLogger("");
    private final FileName fileName = new FileName();

    public SQLiteViewer() {
        LOGGER.log(INFO, "SQLiteViewer started");

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 700);
        setResizable(false);
        setLocationRelativeTo(null);
        setTitle("SQLite Viewer");
        add(fileName, BorderLayout.NORTH);

        setVisible(true);
    }

}
