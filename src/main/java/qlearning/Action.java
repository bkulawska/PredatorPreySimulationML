package qlearning;

import vector2d.Vector2d;

public enum Action {
    UP (0, 1),
    UP_RIGHT(1, 1),
    RIGHT(1, 0),
    DOWN_RIGHT(1, -1),
    DOWN(0, -1),
    DOWN_LEFT(-1, -1),
    LEFT(-1, 0),
    UP_LEFT(-1, 1);

    private final int x;
    private final int y;

    Action(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Vector2d getNewPosition(Vector2d vector2d) {
        return new Vector2d(vector2d.x + this.x, vector2d.y + this.y);
    }

    public static Action getRandomAction() {
        Action[] actions = Action.values();
        int randomIndex = (int) (Math.random() * actions.length);
        return actions[randomIndex];
    }
}
