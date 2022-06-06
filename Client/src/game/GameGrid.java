package game;

import java.util.Random;

public class GameGrid {
    Direction d;
    private  int highestTile = 0;
    private final Random random;
    private Tile[][] tiles;
    public boolean moved;

    public GameGrid() {
        this.tiles = new Tile[4][4];
        this.random = new Random();
        this.moved = false;

    }

    public int move(Direction d) {
        this.d = d;
        if (d.getRotations() < 4)
            for (int i = 0; i < d.getRotations(); i++)
                rotateBoard();
        int score = moveLeft();
        for (int i = 4 - d.getRotations(); i > 0; i--)
            rotateBoard();
        return score;
    }


    public int moveLeft() {
        moved = false;
        int score = 0;
        for (int x = 0; x < 4; x++) {
            int max = 0;
            for (int y = 1; y < 4; y++) {
                int j = y;
                while (j > max) {
                    if (tiles[x][j] != null) {
                        int shiftScore = shiftTiles(x, j, x, j - 1);
                        // We check if the tile in front of the current tile was merged
                        // in the process of shifting the tiles - if so we can't merge nor
                        // move any tiles further than that, so we set the cap to the merged tile.
                        if (tiles[x][j - 1] != null && tiles[x][j - 1].isMerged())
                            max = j - 1;
                        score += shiftScore;
                    }
                    j--;
                }
            }
        }
        return score;
    }

    public void rotateBoard() {
        Tile[][] newBoard = new Tile[4][4];
        for (int y = 0; y < 4; y++)
            for (int x = 0; x < 4; x++)
                newBoard[y][x] = tiles[4 - x - 1][y];
        tiles = newBoard;
    }

    public int shiftTiles(int fromX, int fromY, int toX, int toY) {
        int score = 0;
        int value = tiles[fromX][fromY].getValue();
        if (tiles[toX][toY] == null) {
            tiles[toX][toY] = tiles[fromX][fromY];
            tiles[fromX][fromY] = null;
            tiles[toX][toY].setX(toX);
            tiles[toX][toY].setY(toY);
            moved = true;
        } else if (tiles[toX][toY].getValue() == tiles[fromX][fromY].getValue()) {
            tiles[toX][toY] = tiles[fromX][fromY];
            tiles[fromX][fromY] = null;
            tiles[toX][toY].setValue(value * 2);
            score += value * 2;
            tiles[toX][toY].setX(toX);
            tiles[toX][toY].setY(toY);
            tiles[toX][toY].setMerged(true);
            moved = true;
        }
        return score;
    }

    public boolean canMove(int x, int y) {
        return x < 3 && x > 0 && y > 0 && y < 3;
    }

    public void randomNewTile() {
        int x = random.nextInt(4);
        int y = random.nextInt(4);
        while (tiles[x][y] != null) {
            x = random.nextInt(4);
            y = random.nextInt(4);
        }
        if (highestTile > 4) {
            double odds = random.nextDouble();
            if (odds < 0.3) {
                tiles[x][y] = new Tile(4);
                tiles[x][y].setValue(4);
                tiles[x][y].update();
                tiles[x][y].setX(x);
                tiles[x][y].setY(y);
                return;
            }
        }
        tiles[x][y] = new Tile(2);
        tiles[x][y].setX(x);
        tiles[x][y].setY(y);
        tiles[x][y].setValue(2);
        tiles[x][y].update();
    }

    public boolean isGameOver() {
        return hasEmptySpots() || canMerge();
    }

    public boolean canMerge() {

        for (int y = 0; y < tiles.length; y++)
            for (int x = 0; x < tiles[y].length; x++)
                if (canMove(y, x))
                    if (tiles[y][x + 1] == tiles[y][x])
                        return true;
                    else if (tiles[y][x - 1] == tiles[y][x])
                        return true;
                    else if (tiles[y + 1][x] == tiles[y][x])
                        return true;
                    else if (tiles[y - 1][x] == tiles[y][x])
                        return true;

        return false;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int x = 0; x < 4; x++) {
            for (int y = 0; y < tiles[x].length; y++) {
                if (tiles[x][y] == null) {
                    sb.append("0").append(" ");
                } else {
                    sb.append(tiles[x][y].getValue()).append(" ");
                }

            }
            sb.append(("\n"));
        }
        return sb.toString();
    }

    public boolean hasEmptySpots() {
        for (Tile[] tile : tiles)
            for (Tile value : tile)
                if (value == null)
                    return true;
        return false;
    }

    public int getHighestTile() {
        return this.highestTile;
    }

    public void setHighestTile(int highestTile) {
        this.highestTile = highestTile;
    }

    public Tile[][] getTiles() {
        return this.tiles;
    }
}
