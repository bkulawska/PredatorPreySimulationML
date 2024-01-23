package animal;

public class DayReport {
    private boolean eat = false;
    private boolean mate = false;

    public boolean didEat() {
        return eat;
    }

    public void recordEat() {
        this.eat = true;
    }

    public boolean didMate() {
        return mate;
    }

    public void recordMate() {
        this.mate = true;
    }
}
