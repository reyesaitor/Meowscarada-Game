import java.awt.*;

public class Enemy {
    private int x; 
    private int y; 
    private int velocidadX;
    private int velocidadY;
    private int ancho = 30;
    private int alto = 30; 

    public Enemy(int x, int y, int velocidadEnemigosBase) {
        this.x = x;
        this.y = y;
        this.velocidadX = velocidadEnemigosBase;
        this.velocidadY = 40;
    }

    public void mover() {
        x += velocidadX;
        if (x <= 0 || x >= 760) {
            velocidadX = -velocidadX;
            y += velocidadY;
        }
    }

    public void dibujar(Graphics g) {
        g.setColor(Color.RED);
        g.fillRect(x, y, ancho, alto);
    }

    public Rectangle getRect() {
        return new Rectangle(x, y, ancho, alto);
    }

    public int getY() {
        return y;
    }

    public int getAlto() {
        return alto;
    }
}
