import java.awt.*;

public class Proyectile {
    private double x, y;
    private final double ANCHO_RATIO = 0.01, ALTO_RATIO = 0.02;
    private final double VELOCIDAD = 0.02;

    public Proyectile(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public void mover() {
        y -= VELOCIDAD;
    }

    public void dibujar(Graphics g, int ancho, int alto) {
        g.setColor(Color.YELLOW);
        int proyectilAncho = (int) (ancho * ANCHO_RATIO);
        int proyectilAlto = (int) (alto * ALTO_RATIO);
        int proyectilX = (int) (x * ancho);
        int proyectilY = (int) (y * alto);
        g.fillRect(proyectilX, proyectilY, proyectilAncho, proyectilAlto);
    }

    public Rectangle getRect(int ancho, int alto) {
        int proyectilAncho = (int) (ancho * ANCHO_RATIO);
        int proyectilAlto = (int) (alto * ALTO_RATIO);
        int proyectilX = (int) (x * ancho);
        int proyectilY = (int) (y * alto);
        return new Rectangle(proyectilX, proyectilY, proyectilAncho, proyectilAlto);
    }
}
