import javax.swing.*;
import java.awt.*;

/**
 * Начинаем работу над нашим первым проектом. Не будем ограничиваться консолью, поэтому нам понадобиться окно,
 * в котором и будут происходить все действия. Будем использовать swing.
 */
public class MainWindow extends JFrame {
    public MainWindow(){ // создаем конструктор нашего класса

        setTitle("Fire Snake"); // Титул названия окна
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);    //когда нажмем красный крестик программа завершит работу,
                                                                    // если не прописать? то программа зависала бы в памяти компа
        setSize(640,665);  // размер поля окна в пикселях этот размер 320х320+25pix для заголовка окна "Fire Snake"
        setLocation(400, 400); //координаты появления змейки относительно верхнего левого угла
        setVisible(true);    // окно появляется при запуске
    }
    //..точка входа в программу :
    public static void main(String[] args) {
        MainWindow mainWindow = new MainWindow(); // создаем обьект mainWindow, по сути просто вызываем созданный ранее конструктор
    }
}
