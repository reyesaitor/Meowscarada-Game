import java.awt.*;

public class Player {
    private int x, y;
    private final int ANCHO = 50, ALTO = 10;
    private int VELOCIDAD = 5;

    public Player(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void moverIzquierda() {
        if (x > 0) {
            x -= VELOCIDAD;
        }
    }

    public void moverDerecha() {
        if (x < 750) {
            x += VELOCIDAD;
        }
    }

    public void dibujar(Graphics g, Color color) {
        g.setColor(color);
        g.fillRect(x, y, ANCHO, ALTO);
    }
    
    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setX(int x) {
        this.x = x;
    }
    
    public void setY(int y) {
        this.y = y;
    }
}
