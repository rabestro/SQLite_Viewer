package viewer.component;

import javax.swing.*;
import java.awt.*;

public class FileName extends JPanel {
    private static final System.Logger LOGGER = System.getLogger("");

    private final JTextField fileName = new JTextField(30);
    private final JButton openButton = new JButton("Open");

    {
        fileName.setName("FileNameTextField");
        openButton.setName("OpenFileButton");
        setLayout(new FlowLayout(FlowLayout.LEFT));
        add(fileName, BorderLayout.NORTH);
        add(openButton, BorderLayout.EAST);
        setVisible(true);
    }
}
