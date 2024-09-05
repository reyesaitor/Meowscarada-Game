import java.awt.*;

public class Proyectile {
    private int x, y;
    private final int ANCHO = 5, ALTO = 10;
    private final int VELOCIDAD = 5;

    public Proyectile(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void mover() {
        y -= VELOCIDAD;
    }

    public void dibujar(Graphics g) {
        g.setColor(Color.YELLOW);
        g.fillRect(x, y, ANCHO, ALTO);
    }

    public Rectangle getRect() {
        return new Rectangle(x, y, ANCHO, ALTO);
    }
}
