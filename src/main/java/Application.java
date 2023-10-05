
import java.io.IOException;

public class Application {
    public static void main(String[] args) throws IOException {
        try {
            // Create a new Game object and call run()
            Game game;
            game = new Game();
            game.run();
        } catch (IOException e) {
            // Handle or log the exception at the application level
            e.printStackTrace();
        }
    }
}
