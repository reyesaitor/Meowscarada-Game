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

    private final double LIMITE_Y = 0.1;

    private int nivelActual;
    private int velocidadEnemigosBase = 5;

    public GamePanel() {
        setBackground(Color.BLACK);
        setFocusable(true);
        requestFocusInWindow();
        addKeyListener(this);

        player = new Player(0.5,0.95); //Initial Position
        enemies = new ArrayList<>();
        proyectiles = new ArrayList<>();

        //Inicializar enemigos
        nivelActual = 1;
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
        enemies.clear();
        int numeroEnemigos = 5 + (nivelActual - 1) * 2;
        int enemigosPorFila = 5;
        double distanciaX = 0.15;
        double posicionYInicial = 0.1;
        for(int i = 0; i < numeroEnemigos; i++) {
            double posicionX = 0.1 + (i % enemigosPorFila) * distanciaX;
            double posicionY = posicionYInicial + (i / enemigosPorFila) * 0.1;
            int velocidadEnemigos = velocidadEnemigosBase + nivelActual;
            enemies.add(new Enemy(posicionX, posicionY, velocidadEnemigos));
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
            if (en.getY() + en.getAlto() >= getHeight() *(1 - LIMITE_Y)) {
                perderVida();
                break;
            }
        }
        colisionDetect();

        if (enemies.isEmpty()) {
            nivelActual++;
            generarEnemigos();
        }

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
        proyectiles.clear();
        generarEnemigos();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        int ancho = getWidth();
        int alto = getHeight();

        if (playerMuriendo) {
            if ((System.currentTimeMillis() / 100) % 2 == 0) {
                player.dibujar(g, Color.RED, ancho, alto);
            } else {
                player.dibujar(g, Color.CYAN, ancho, alto);
            }
        } else {
            player.dibujar(g, Color.GREEN, ancho, alto);
        }

        for (Enemy en : enemies) {
            en.dibujar(g, ancho, alto);
        }

        for (Proyectile p : proyectiles) {
            p.dibujar(g, ancho, alto);
        }

        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 20));
        g.drawString("SCORE: " + puntuacion, 10, 20);
        g.drawString("LIFE: " + vidas, 10, 40);
        g.drawString("LV: " + nivelActual, 10, 60);


        if (gameOver) {
            g.setFont(new Font("Arial", Font.BOLD, 36));
            g.drawString("GAME OVER", ancho / 2 - 100, alto / 2);
        }

        g.setColor(Color.YELLOW);
        g.drawLine(0, alto - (int) (alto * LIMITE_Y), ancho, alto - (int) (alto * LIMITE_Y));


        Toolkit.getDefaultToolkit().sync();
    }

    private void colisionDetect() {
        List<Proyectile> proyectilesAEliminar = new ArrayList<>();
        List<Enemy> enemiesAEliminar = new ArrayList<>();

        for (Proyectile p : proyectiles) {
            for (Enemy en : enemies) {
               if (p.getRect(getWidth(), getHeight()).intersects(en.getRect(getWidth(), getHeight()))) {
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
        proyectiles.add(new Proyectile(player.getX() + 0.02, player.getY() - 0.02));
    }

    @Override
    public void keyReleased(KeyEvent e) {}

    @Override
    public void keyTyped(KeyEvent e) {}
}
