package viewer;

public class ApplicationRunner {
    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(SQLiteViewer::new);
    }
}
