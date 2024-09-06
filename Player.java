import java.awt.*;

public class Player {
    private double x, y;
    private final double ANCHO_RATIO = 0.05, ALTO_RATIO = 0.02;
    private double VELOCIDAD_RATIO = 0.01;

    public Player(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public void moverIzquierda() {
        if (x > 0) {
            x = Math.max(0, x - VELOCIDAD_RATIO);
        }
    }

    public void moverDerecha() {
        if (x < 750) {
            x = Math.min(1 - ANCHO_RATIO, x + VELOCIDAD_RATIO);
        }
    }

    public void dibujar(Graphics g, Color color, int ancho, int alto) {
        g.setColor(color);
        int playerAncho = (int) (ancho * ANCHO_RATIO);
        int playerAlto = (int) (alto * ALTO_RATIO);
        int playerX = (int) (x * ancho);
        int playerY = (int) (y * alto);
        g.fillRect(playerX, playerY, playerAncho, playerAlto);
    }
    
    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public void setX(int x) {
        this.x = x;
    }
    
    public void setY(int y) {
        this.y = y;
    }
}
