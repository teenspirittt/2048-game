package TwentyFortyEight.game;

public enum Direction {

    UP(-130, 3),
    DOWN(130, 1),
    RIGHT(130, 2),
    LEFT(-130, 4);

    private final int amountOfMovement;
    private final int rotations; // number of time the board has to rotate to move

    Direction(int amountOfMovement, int rotations) {
        this.amountOfMovement = amountOfMovement;
        this.rotations = rotations;
    }

    public int getRotations() {
        return rotations;
    }
}
