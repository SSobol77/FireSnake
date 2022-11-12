import javax.swing.*;

/**
 * @SSobolewski
 */
public class MainWindow extends JFrame {


    public MainWindow(){
        setTitle("Fire Snake");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(420,445);
        setLocation(400,400);
        add(new GameField());
        setVisible(true);
    }

    public static void main(String[] args) {
        MainWindow mainWindow = new MainWindow();
    }
}