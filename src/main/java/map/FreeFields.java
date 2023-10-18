package map;

import observer.IFieldAvailabilityObserver;
import vector2d.Vector2d;

import java.util.ArrayList;

public class FreeFields implements IFieldAvailabilityObserver {
  private final ArrayList<Vector2d> free = new ArrayList<>();

  public FreeFields(GrassSectors sector)
  {
    sector.addObserver(this);
  }

  public boolean isFreeFieldAvailable()
  {
    return free.size() > 0;
  }

  public Vector2d placeGrass()
  {
    int number = (int) (Math.random() * free.size());
    Vector2d place = free.get(number);
    free.remove(number);
    return place;
  }

  @Override
  public void setPositionAsUnavailable(Vector2d position) {
    this.remove(position);
  }

  @Override
  public void setPositionAsAvailable(Vector2d position) {
    this.add(position);
  }

  private void add(Vector2d position)
  {
    free.add(position);
  }

  private void remove(Vector2d position)
  {
    free.remove(position);
  }
}
