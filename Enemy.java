import java.awt.*;

public class Enemy {
    private double x; 
    private double y; 
    private double velocidadX;
    private double velocidadY = 0.05;
    private final double ANCHO_RATIO = 0.04;
    private final double ALTO_RATIO = 0.04; 

    public Enemy(double x, double y, int velocidadEnemigosBase) {
        this.x = x;
        this.y = y;
        this.velocidadX = 0.002 * velocidadEnemigosBase;
    }

    public void mover() {
        x += velocidadX;
        if (x <= 0 || x >= 1 - ANCHO_RATIO) {
            velocidadX = -velocidadX;
            y += velocidadY;
        }
    }

    public void dibujar(Graphics g, int ancho, int alto) {
        g.setColor(Color.RED);
        int enemigoAncho = (int) (ancho * ANCHO_RATIO);
        int enemigoAlto = (int) (alto * ALTO_RATIO);
        int enemigoX = (int) (x * ancho);
        int enemigoY = (int) (y * alto);
        g.fillRect(enemigoX, enemigoY, enemigoAncho, enemigoAlto);
    }

    public Rectangle getRect(int ancho, int alto) {
        int enemigoAncho = (int) (ancho * ANCHO_RATIO);
        int enemigoAlto = (int) (alto * ALTO_RATIO);
        int enemigoX = (int) (x * ancho);
        int enemigoY = (int) (y * alto);
        return new Rectangle(enemigoX, enemigoY, enemigoAncho, enemigoAlto);
    }

    public double getY() {
        return y;
    }

    public double getAlto() {
        return ALTO_RATIO;
    }
}
