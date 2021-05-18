import org.assertj.swing.fixture.JButtonFixture;
import org.assertj.swing.fixture.JTextComponentFixture;
import org.hyperskill.hstest.dynamic.DynamicTest;
import org.hyperskill.hstest.stage.SwingTest;
import org.hyperskill.hstest.testcase.CheckResult;
import org.hyperskill.hstest.testing.swing.SwingComponent;
import viewer.SQLiteViewer;

import static org.hyperskill.hstest.testcase.CheckResult.correct;

public class ViewerTest extends SwingTest {

    public ViewerTest() {
        super(new SQLiteViewer());
    }

    @SwingComponent(name = "FileNameTextField")
    private JTextComponentFixture fileNameTextField;

    @SwingComponent(name = "OpenFileButton")
    private JButtonFixture openFileButton;

    @DynamicTest(order = 1)
    CheckResult test1() {

        requireEditable(fileNameTextField);
        requireEmpty(fileNameTextField);
        requireEnabled(fileNameTextField);
        requireVisible(fileNameTextField);

        requireEnabled(openFileButton);
        requireVisible(openFileButton);

        return correct();
    }
}
