import com.switchcase.games.model.Game;
import com.switchcase.games.model.TicTacToeGame;

public class StartGame {
    public static void main(String[] args) {
        try {
            Game game = new TicTacToeGame();
            game.startGame();
        } catch (Exception e) {
            System.out.print(e.getMessage());
        }
    }
}
