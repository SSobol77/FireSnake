import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;

/**
 * @SSobolewski
 */

public class GameField extends JPanel implements ActionListener{
    private final int SIZE = 320;
    private final int DOT_SIZE = 16;
    private final int ALL_DOTS = 400;
    private Image point;
    private Image fire;
    private int fireX;
    private int fireY;
    private int[] x = new int[ALL_DOTS];
    private int[] y = new int[ALL_DOTS];
    private int points;
    private boolean left = false;
    private boolean right = true;
    private boolean up = false;
    private boolean down = false;
    private boolean play = true;



    public GameField(){
        setBackground(Color.BLACK);
        loadImg();
        initGame();
        addKeyListener(new FieldKeyListener());
        setFocusable(true);

    }

    public void initGame(){
        points = 2;
        for (int i = 0; i < points; i++) {
            x[i] = 48 - i*DOT_SIZE;
            y[i] = 48;
        }
        Timer timer = new Timer(230, this);
        timer.start();
        createFire();
    }

    public void createFire(){
        fireX = new Random().nextInt(20)*DOT_SIZE;
        fireY = new Random().nextInt(20)*DOT_SIZE;
    }

    public void loadImg(){
        ImageIcon iifire = new ImageIcon("snake.png");
        fire = iifire.getImage();
        ImageIcon iipoint = new ImageIcon("fire.png");
        point = iipoint.getImage();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if(play){
            g.drawImage(fire,fireX,fireY,this);
            for (int i = 0; i < points; i++) {
                g.drawImage(point,x[i],y[i],this);
            }
        } else{
            String str = "GAME OVER";
            //Font f = new Font("Arial",14,Font.BOLD);
            g.setColor(Color.ORANGE);
            // g.setFont(f);
            g.drawString(str,170,SIZE/2);
        }
    }

    public void moveXY(){
        for (int i = points; i > 0; i--) {
            x[i] = x[i-1];
            y[i] = y[i-1];
        }
        if(left){
            x[0] -= DOT_SIZE;
        }
        if(right){
            x[0] += DOT_SIZE;
        } if(up){
            y[0] -= DOT_SIZE;
        } if(down){
            y[0] += DOT_SIZE;
        }
    }

    public void checkFire(){
        if(x[0] == fireX && y[0] == fireY){
            points++;
            createFire();
        }
    }

    public void checkCollisions(){
        for (int i = points; i >0 ; i--) {
            if(i>4 && x[0] == x[i] && y[0] == y[i]){
                play = false;
            }
        }

        if(x[0]>SIZE){
            x[0] = 0;
        }
        if(x[0]<0){
            x[0] = SIZE;
        }
        if(y[0]>SIZE){
            play = false;
        }
        if(y[0]<0){
            play = false;
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(play){
            checkFire();
            checkCollisions();
            moveXY();

        }
        repaint();
    }

    class FieldKeyListener extends KeyAdapter{
        @Override
        public void keyPressed(KeyEvent e) {
            super.keyPressed(e);
            int key = e.getKeyCode();
            if(key == KeyEvent.VK_LEFT && !right){
                left = true;
                up = false;
                down = false;
            }
            if(key == KeyEvent.VK_RIGHT && !left){
                right = true;
                up = false;
                down = false;
            }

            if(key == KeyEvent.VK_UP && !down){
                right = false;
                up = true;
                left = false;
            }
            if(key == KeyEvent.VK_DOWN && !up){
                right = false;
                down = true;
                left = false;
            }
        }
    }
}