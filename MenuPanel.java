import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuPanel extends JPanel {
    //Constructor principal
    public MenuPanel(MeowscaradaGame game) {
        //Titulo
        JLabel titulo = new JLabel("Time to play!");
        add(titulo);

        //Nueva partida
        JButton btnNewGame = new JButton("New Game");
        add(btnNewGame);

        //Salir
        JButton btnExit = new JButton("Maybe next time");
        add(btnExit);

        //Action Listener Nueva partida
        btnNewGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("New game...START!");
                game.startGame();
            }
        });

        //Action Listener Exit
        btnExit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
            
        });
    }
}
