import com.googlecode.lanterna.*;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.screen.Screen;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Arena {
    private int width;
    private int height;
    private Hero hero;
    private List<Wall> walls;
    public List<Coin> coins;
    private List<Monster> monsters;

    Arena(int width, int height) {
        hero = new Hero(10, 10);
        this.width = width;
        this.height = height;
        this.walls = createWalls();
        this.coins = createCoins();
        this.monsters = createMonsters();
    }
    private List<Wall> createWalls() {
        List<Wall> walls = new ArrayList<>();
        for (int c = 0; c < width; c++) {
            walls.add(new Wall(c, 0));
            walls.add(new Wall(c, height - 1));
        }
        for (int r = 1; r < height - 1; r++) {
            walls.add(new Wall(0, r));
            walls.add(new Wall(width - 1, r));
        }
        return walls;
    }
    private List<Coin> createCoins() {
        Random random = new Random();
        ArrayList<Coin> coins = new ArrayList<>();
        for (int i = 0; i < 5; i++) coins.add(new Coin(random.nextInt(width - 2) + 1, random.nextInt(height - 2) + 1));
        return coins;
    }
    private List<Monster> createMonsters() {
        Random random = new Random();
        ArrayList<Monster> monsters = new ArrayList<>();
        for (int i = 0; i < 5; i++) monsters.add(new Monster(random.nextInt(width - 2) + 1, random.nextInt(height - 2) + 1));
        return monsters;
    }
    void processKey(KeyStroke key) throws IOException {
        System.out.println(key);
        switch(key.getKeyType()) {
            case ArrowUp:
                moveHero(hero.moveUp());
                break;
            case ArrowDown:
                moveHero(hero.moveDown());
                break;
            case ArrowLeft:
                moveHero(hero.moveLeft());
                break;
            case ArrowRight:
                moveHero(hero.moveRight());
                break;
        }
        retrieveCoins();
        verifyMonsterCollisions();
        moveMonsters();
        verifyMonsterCollisions();
    }
    public void draw(Screen screen) {
        TextGraphics graphics = screen.newTextGraphics();
        graphics.setBackgroundColor(TextColor.Factory.fromString("#336699"));
        graphics.fillRectangle(new TerminalPosition(0, 0), new TerminalSize(width, height), ' ');
        hero.draw(graphics);
        for (Wall wall : walls) wall.draw(graphics);
        for (Coin coin: coins) coin.draw(graphics);
        for (Monster monster: monsters) monster.draw(graphics);
    }
    public void moveHero(Position position) {
        if (canHeroMove(position)) {
            hero.setPosition(position);
        }

    }
    private boolean canHeroMove(Position position) {
        return position.getX() < width-1 && position.getY() < height-1 && position.getX() > 0 && position.getY() > 0;
    }
    private void retrieveCoins() {
        for (Coin coin : coins)
            if (hero.getPosition().equals(coin.getPosition())) {
                coins.remove(coin);
                break;
            }
    }
    private void moveMonsters() {
        for (Monster monster : monsters) {
            Position monsterPosition = monster.move();
            if (canHeroMove(monsterPosition))
                monster.setPosition(monsterPosition);
        }
    }
    private void verifyMonsterCollisions() {
        for (Monster monster: monsters)
            if (hero.getPosition().equals(monster.getPosition())) {
                System.out.println("Game over.");
                System.exit(0);
            }
    }
    public int getWidth() {return width;}
    public int getHeight() {return height;}

}
