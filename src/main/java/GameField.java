import javax.swing.*;
import java.awt.*;
import java.util.Random;

/**
 *  здесь вся логика
 */

public class GameField extends JPanel {
    private final int SIZE = 640; // константа размерность нашего поля игры
    private final int DOT_SIZE = 16; // константа размерность нашей точки
    private final int ALL_DOTS = 800 ; // максимальное количество звеньев цепочки элементов

    // проинициализируем картинки:
    private Image dot;
    private Image apple;

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
    private void loadImage(){

        ImageIcon iid = new ImageIcon("dot.png");
        dot = iid.getImage();

        ImageIcon iia = new ImageIcon("apple.png");
        apple = iia.getImage();
    }

    // создадим метод генерирующий случайные числа, чтобы рандомно генерировать координаты appleX и appleY:
    private void createApple(){
        Random random = new Random();
        appleX = random.nextInt(40) * DOT_SIZE;
        appleY = random.nextInt(40) * DOT_SIZE;
    }

    // создадим метод инициализирующий начало игры
    public void initGame(){
        dots = 3;
        for (int i = 0; i < 5; i++) {
            y[i] = 48; // начало по y
            x[i] = 48 - i * DOT_SIZE; // начало по x пять
        }

        // проинициализируем таймер чем больше число тем медленнее двигается змейка
        timer = new Timer(150,this);
        timer.start(); // запускаем таймер на выполнение чтобы он все обнулял
        createApple(); // создаем яблоко
    }

    // создадим метод проверяющий не съела ли змейка яблоко
    public void checkApple(){
        if(x[0] == appleX && y[0] == appleY){ // если координаты змейки совпадают с яблоком, то:
            dots++; // количество точек змейки увеличиваем на единицу
            createApple(); // вызываем метод, который придумывает новые рандомные координаты и создаем новое яблоко
        }
    }

    // создадим метод , который будет отрисовывать все наши компоненты ябочки и змейки, для этого переопределим метод из класса  JPanel:
    @Override
    protected void paintComponent(Graphics g){
        super.paintComponent(g);

        if (inGame){ // будем проверять, чтобы змейка была в игре, если переменная inGame возвращает true, то мы будем рисовать, иначе рисуем Game Over

            g.drawImage(apple, appleX, appleY, this); //мы обращаемся к элементу Graphics это какая-то графика в нашем случае это обычные картинки яблочко
            // и его координаты appleX, appleY  и потом передаем this

            for (int i = 0; i < dots ; i++) { // запускаем цикл для отрисовки нашей змейки
                g.drawImage(dot,x[i], y[i], this);
            }
        }else { // если переменная inGame возвращает false рисуем Game Over
            String str = "Game Over";
            g.setColor(Color.GREEN);
            g.drawString(str, SIZE/6,SIZE/2); // делаем так, чтобы надпись находилась посередине
        }

    }
}
