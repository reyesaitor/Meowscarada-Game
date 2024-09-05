import javax.swing.JFrame;
import java.awt.BorderLayout;


public class MeowscaradaGame extends JFrame {

    private MenuPanel menuPanel;
    private GamePanel gamePanel;

    public MeowscaradaGame() {
        //Ventana principal
        setTitle("Meowscarada Game");
        setSize(800,600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        Menu();
        setVisible(true);

    }

    //Metodo mostrar Menu
    public void Menu() {
        if (gamePanel != null) {
            remove(gamePanel);
            gamePanel = null;
        }

        menuPanel = new MenuPanel(this);
        add(menuPanel, BorderLayout.CENTER);
        revalidate();
        repaint();
    }



    //Metodo iniciar juego
    public void startGame() {
        if (menuPanel != null) {
            remove(menuPanel);
            menuPanel = null;
        }

        gamePanel = new GamePanel();
        add(gamePanel, BorderLayout.CENTER);
        revalidate();
        repaint();
    }

    public static void main(String[] args) {
        //Crear e iniciar la ventana
        new MeowscaradaGame();
    }

}