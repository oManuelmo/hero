import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextCharacter;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.screen.Screen;

import java.io.IOException;
public class Game {
    private Screen screen;
    private Hero hero;
    boolean flag;

    Game() throws IOException {
        try {
            TerminalSize terminalSize = new TerminalSize(40, 20);
            DefaultTerminalFactory terminalFactory = new DefaultTerminalFactory().setInitialTerminalSize(terminalSize);
            Terminal terminal = terminalFactory.createTerminal();
            screen = new TerminalScreen(terminal);
            screen.setCursorPosition(null); // we don't need a cursor
            screen.startScreen(); // screens must be started
            screen.doResizeIfNecessary(); // resize screen if necessary
            hero = new Hero(10, 10);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void draw() throws IOException {
        try {
            screen.clear();
            hero.draw(screen);
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
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void processKey(KeyStroke key) throws IOException {
        System.out.println(key);
        switch(key.getKeyType()) {
            case ArrowUp:
                hero.moveUp();
                break;
            case ArrowDown:
                hero.moveDown();
                break;
            case ArrowLeft:
                hero.moveLeft();
                break;
            case ArrowRight:
                hero.moveRight();
                break;
            case Character:
                if(key.getCharacter() == 'q') {
                    screen.close();
                }
                break;
            case EOF:
                flag = false;
                break;

        }
    }
}
