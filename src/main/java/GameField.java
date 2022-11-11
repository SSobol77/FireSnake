import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;

/**
 *  здесь вся логика
 */

public class GameField extends JPanel implements ActionListener {
    private final int SIZE = 640; // константа размерность нашего поля игры
    private final int DOT_SIZE = 16; // константа размерность нашей точки
    private final int ALL_DOTS = 800; // максимальное количество звеньев цепочки элементов

    // проинициализируем картинки:
    private Image dot;
    private Image apple;

    boolean left = false;
    boolean right = true;
    boolean up = false;
    boolean down = false;

    //объявляем два массива наших координат X и Y с размерностью массива ALL_DOTS,
    // т.к. 800 точек, то будет 800 Х-координат:
    private int[] x = new int[ALL_DOTS];
    private int[] y = new int[ALL_DOTS];

    // координаты X и Y яблока:
    private int appleX;
    private int appleY;

    // Колличество очков т.е. количество звеньев змейки:
    private int dots;

    // Timer - класс, который считает время, он будет считать количество кадров в секунду,
    // т.е. чем больше кадров у нас обновляется каждую секунду, тем быстрее движется наша змейка:
    private Timer timer;

    // задаем булевскую переменную, когда будет равна false, то мы выходим из программы:
    private boolean inGame = true;

    // создадим метод загружающий картинки в переменные dot и apple:
    private void loadImage() {

        ImageIcon iid = new ImageIcon("dot.png");
        dot = iid.getImage();

        ImageIcon iia = new ImageIcon("apple.png");
        apple = iia.getImage();
    }

    // создадим метод генерирующий случайные числа, чтобы рандомно генерировать координаты appleX и appleY:
    private void createApple() {
        Random random = new Random();
        appleX = random.nextInt(40) * DOT_SIZE;
        appleY = random.nextInt(40) * DOT_SIZE;
    }

    // создадим метод инициализирующий начало игры
    public void initGame() {
        dots = 3;
        for (int i = 0; i < 5; i++) {
            y[i] = 48; // начало по y
            x[i] = 48 - i * DOT_SIZE; // начало по x пять
        }

        // проинициализируем таймер чем больше число тем медленнее двигается змейка
        timer = new Timer(150, this);
        timer.start(); // запускаем таймер на выполнение чтобы он все обнулял
        createApple(); // создаем яблоко
    }

    // создадим метод проверяющий не съела ли змейка яблоко
    public void checkApple() {
        if (x[0] == appleX && y[0] == appleY) { // если координаты змейки совпадают с яблоком, то:
            dots++; // количество точек змейки увеличиваем на единицу
            createApple(); // вызываем метод, который придумывает новые рандомные координаты и создаем новое яблоко
        }
    }

    // создадим метод , который будет отрисовывать все наши компоненты ябочки и змейки, для этого переопределим метод из класса  JPanel:
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (inGame) { // будем проверять, чтобы змейка была в игре, если переменная inGame возвращает true, то мы будем рисовать, иначе рисуем Game Over

            g.drawImage(apple, appleX, appleY, this); //мы обращаемся к элементу Graphics это какая-то графика в нашем случае это обычные картинки яблочко
            // и его координаты appleX, appleY  и потом передаем this

            for (int i = 0; i < dots; i++) { // запускаем цикл для отрисовки нашей змейки
                g.drawImage(dot, x[i], y[i], this);
            }
        } else { // если переменная inGame возвращает false рисуем Game Over
            String str = "Game Over";
            g.setColor(Color.GREEN);
            g.drawString(str, SIZE / 6, SIZE / 2); // делаем так, чтобы надпись находилась посередине
        }
    }

    // Будем проверять на коллизию змейки cаму с собой и коллизию с бортиком, т.е. врезалась ли наша змейка в бортик:
    public void checkCollision() { //не врезалась ли в себя любимую, если да то inGame = false и конец игры
        for (int i = dots; i > 0 ; i--) { // идем циклом до размерности нашей змейки т.е. до dots
            if (x[0] == x[i] && y[0] == y[i]) { // т.е. если координаты головы совпадают с координатами какого-либо из элементов змейки, то:
                inGame = false;
            }
            // проверяем по x:
            if (x[0] > SIZE)  // если врезалась в правую сторону то выползает из левого верхнего угла из 0
                x[0] = 0;
            if (x[0] < 0)
                x[0] = SIZE;  // если врезалась в левую сторону, то выползает из правой
            // проверяем по y:
            if (y[0] > SIZE) // если врезалась в нижнюю сторону, то завершаем игру
                inGame = false;
            if (y[0] < 0) // если врезалась в верхнюю сторону, то завершаем игру
                inGame = false;
        }


    // научим слушать какие кнопки мы нажимаем, для этого создадим внутренний класс:
    class FieldKeyListener extends KeyAdapter { // KeyAdapter это класс который помагает обрабатывать нажатие наших кнопок @Override
        @Override
        public void keyPressed(KeyEvent k) { // KeyEvent это и есть нажатие на нашу кнопку
            super.keyPressed(k); // вызываем конструктор super класса с методом keyPressed и проинициализируем k
            int key = k.getKeyCode(); // инициализируем key кодом кнопки, каждой кнопке соответствует ее уникальный код


            if (key == KeyEvent.VK_LEFT && !right) { // !right наш right = false
                left = true;
                up = false;
                down = false;
            }
            if (key == KeyEvent.VK_RIGHT && !left) {
                right = true;
                up = false;
                down = false;
            }
            if (key == KeyEvent.VK_UP && !down) {
                left = false;
                right = false;
                up = true;
            }
            if (key == KeyEvent.VK_DOWN && !up) {
                left = false;
                right = false;
                down = true;
            }

            }
        }
    }
    //Логика работы кнопок
    //Чтобы прописать логику кнопок, реализуем в нашем классе следующий интерфейс:
    @Override
    public void actionPerformed(ActionEvent e) {
        if (inGame) {
            checkApple();
            checkCollision();
            move();
        }
        repaint();
    }
    public  GameField(){
        setBackground(Color.BLACK);
        loadImage();
        initGame();
        addKeyListener(new FieldKeyListener());
        setFocusable(true);
    }
    public void move() {
        for (int i = dots; i > 0; i--) {
            x[i] = x[i - 1];
            y[i] = y[i - 1];
        }
        if (left)
            x[0] -= DOT_SIZE;
        if (right)
            x[0] += DOT_SIZE;
        if (up)
            y[0] -= DOT_SIZE;
        if (down)
            y[0] += DOT_SIZE;
    }
}