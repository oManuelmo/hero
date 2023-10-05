import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextCharacter;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;

import java.io.IOException;

public class Application {
    public static void main(String[] args) throws IOException {
        try {
            // Create a new Game object and call run()
            Game game = new Game();
            game.run();
        } catch (IOException e) {
            // Handle or log the exception at the application level
            e.printStackTrace();
        }
    }
}
