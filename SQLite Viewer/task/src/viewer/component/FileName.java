package viewer.component;

import javax.swing.*;

public class FileName extends JPanel {
    private static final System.Logger LOGGER = System.getLogger("");

    private final JTextField fileName = new JTextField(15);
    private final JButton openButton = new JButton("Open");

    {
        fileName.setName("FileNameTextField");
        openButton.setName("OpenFileButton");
    }
}
