import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.Timer;

public class GamePanel extends JPanel implements ActionListener, KeyListener {
    
    private Timer timer;
    private Player player;
    private List<Enemy> enemies;
    private List<Proyectile> proyectiles;

    private final int cdDisparo = 500;
    private long tultDisparo;

    private int puntuacion;
    private int vidas;
    private boolean gameOver;

    private boolean playerMuriendo;
    private long tiempoMueriendo;
    private final int DURACION_MUERTE = 1000;

    private final int LIMITE_Y = 50;

    public GamePanel() {
        setBackground(Color.BLACK);
        setFocusable(true);
        requestFocusInWindow();
        addKeyListener(this);

        player = new Player(375,540); //Initial Position
        enemies = new ArrayList<>();
        proyectiles = new ArrayList<>();

        //Inicializar enemigos
        generarEnemigos();
       
        timer = new Timer(16, this); //60 fps aprox
        timer.start();

        puntuacion = 0;
        vidas = 3;
        gameOver = false;
        tultDisparo = System.currentTimeMillis();
        playerMuriendo = false;

        SwingUtilities.invokeLater(() -> requestFocusInWindow());

        
    }

    private void generarEnemigos() {
        for(int i = 0; i < 5; i++) {
            enemies.add(new Enemy(50 + i * 100, 50));
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (gameOver) {
            return;
        }

        if (playerMuriendo) {
            if(System.currentTimeMillis() - tiempoMueriendo > DURACION_MUERTE) {
                playerMuriendo = false;
                reiniciarNivel();
            } else {
                repaint();
                return;
            }
        }

        for (Proyectile p : proyectiles) {
            p.mover();
        }
        for (Enemy en : enemies) {
            en.mover();
            if (en.getY() + en.getAlto() >= getHeight() - LIMITE_Y) {
                perderVida();
                break;
            }
        }
        colisionDetect();
        repaint();
    }

    private void perderVida() {
        vidas--;

        if (vidas <= 0) {
            gameOver = true;
            timer.stop();
        } else {
            playerMuriendo = true;
            tiempoMueriendo = System.currentTimeMillis();
            reiniciarNivel();
        }
    }

    private void reiniciarNivel() {
        player.setX(375);
        player.setY(550);

        proyectiles.clear();
        enemies.clear();
        generarEnemigos();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (playerMuriendo) {
            if ((System.currentTimeMillis() / 100) % 2 == 0) {
                player.dibujar(g, Color.RED);
            } else {
                player.dibujar(g, Color.CYAN);
            }
        } else {
            player.dibujar(g, Color.GREEN);
        }

        for (Enemy en : enemies) {
            en.dibujar(g);
        }

        for (Proyectile p : proyectiles) {
            p.dibujar(g);
        }

        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 20));
        g.drawString("SCORE: " + puntuacion, 10, 20);
        g.drawString("LIFE: " + vidas, 10, 40);

        if (gameOver) {
            g.setFont(new Font("Arial", Font.BOLD, 36));
            g.drawString("GAME OVER", getWidth() / 2 - 100, getHeight() / 2);
        }

        g.setColor(Color.YELLOW);
        g.drawLine(0, getHeight() - LIMITE_Y, getWidth(), getHeight() - LIMITE_Y);

        Toolkit.getDefaultToolkit().sync();
    }

    private void colisionDetect() {
        List<Proyectile> proyectilesAEliminar = new ArrayList<>();
        List<Enemy> enemiesAEliminar = new ArrayList<>();

        for (Proyectile p : proyectiles) {
            for (Enemy en : enemies) {
                if (p.getRect().intersects(en.getRect())) {
                    proyectilesAEliminar.add(p);
                    enemiesAEliminar.add(en);
                    puntuacion += 100;
                }
            }
        }

        proyectiles.removeAll(proyectilesAEliminar);
        enemies.removeAll(enemiesAEliminar);
        
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();

        if (!gameOver && !playerMuriendo) {
            if (key == KeyEvent.VK_LEFT) {
                player.moverIzquierda();
            }
    
            if (key == KeyEvent.VK_RIGHT) {
                player.moverDerecha();
            }
    
            if (key == KeyEvent.VK_SPACE) {
                long tActual = System.currentTimeMillis();
                if (tActual - tultDisparo >= cdDisparo) {
                    disparar();
                    tultDisparo = tActual;
                }
                
            }
        }

        repaint();
    }

    private void disparar() {
        proyectiles.add(new Proyectile(player.getX() + 15, player.getY() - 10));
    }

    @Override
    public void keyReleased(KeyEvent e) {}

    @Override
    public void keyTyped(KeyEvent e) {}
}
