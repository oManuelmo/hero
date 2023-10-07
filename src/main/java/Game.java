import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextCharacter;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.screen.Screen;

import java.io.IOException;
public class Game {
    private Screen screen;
    private Arena arena;
    private boolean flag;

    Game() throws IOException {
        try {
            TerminalSize terminalSize = new TerminalSize(80, 40);
            DefaultTerminalFactory terminalFactory = new DefaultTerminalFactory().setInitialTerminalSize(terminalSize);
            Terminal terminal = terminalFactory.createTerminal();
            screen = new TerminalScreen(terminal);
            screen.setCursorPosition(null); // we don't need a cursor
            screen.startScreen(); // screens must be started
            screen.doResizeIfNecessary(); // resize screen if necessary
            arena = new Arena(40, 20);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void draw() throws IOException {
        try {
            screen.clear();
            arena.draw(screen);
            screen.refresh();
        } catch (IOException e) {
            // Handle the exception or rethrow it as needed
            e.printStackTrace();
        }
    }
    public void run() throws IOException {
        try {
            flag = true;
            while (flag) {
                draw();
                KeyStroke key = screen.readInput();
                processKey(key);
                if (arena.coins.isEmpty()) {
                    System.out.println("You won!");
                    System.exit(0);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void processKey(KeyStroke key) throws IOException {
        arena.processKey(key);
        if (key.getKeyType() == KeyType.Character) {
            if (key.getCharacter() == 'q') {
                screen.close();
            }
        } else if (key.getKeyType() == KeyType.EOF) {
            flag = false;
        }
    }
}
