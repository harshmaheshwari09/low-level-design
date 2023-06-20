import com.switchcase.games.model.Game;
import com.switchcase.games.model.TicTacToeGame;

public class StartGame {
    public static void main(String[] args) {
        Game game = new TicTacToeGame();
        try {
            game.startGame();
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        }
    }
}
