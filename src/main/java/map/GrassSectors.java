package map;

import observer.IFieldAvailabilityObserver;
import observer.IFieldAvailabilityPublisher;
import vector2d.Vector2d;

import java.util.LinkedList;
import java.util.Map;

public class GrassSectors implements IFieldAvailabilityPublisher, IFieldAvailabilityObserver{
  private final Vector2d leftBottomCorner;
  private final Vector2d rightTopCorner;
  private final FreeFields freeFields;
  LinkedList<IFieldAvailabilityObserver> subscribers = new LinkedList<>();

  public GrassSectors(Vector2d bottomCorner, Vector2d topCorner, WorldMap map) {
    leftBottomCorner = bottomCorner;
    rightTopCorner = topCorner;
    freeFields = new FreeFields(this);
    markFreeFields();
    map.addObserver(this);
  }

  public void plantGrass(Map<Vector2d, Field> fields, int n) {
    for(int i = 0; i<n; i++) {
      if(freeFields.isFreeFieldAvailable()) {
        Vector2d position = freeFields.placeGrass();
        Field newField = new Field(position);
        newField.plantGrass();
        fields.put(position, newField);
      }
    }
  }

  @Override
  public void addObserver(IFieldAvailabilityObserver observer) {
    subscribers.add(observer);
  }

  @Override
  public void removeObserver(IFieldAvailabilityObserver observer) {
    subscribers.remove(observer);
  }

  private void markFreeFields() {
    for(int i = leftBottomCorner.x; i <= rightTopCorner.x; i++) {
      for(int j = leftBottomCorner.y; j <= rightTopCorner.y; j++) {
        newFreePosition(new Vector2d(i, j));
      }
    }
  }

  private void newFreePosition(Vector2d position) {
    for(IFieldAvailabilityObserver observer:subscribers) {
      observer.setPositionAsAvailable(position);
    }
  }

  @Override
  public void setPositionAsUnavailable(Vector2d position) {
      for(IFieldAvailabilityObserver observer:subscribers) {
        observer.setPositionAsUnavailable(position);
      }
  }

  @Override
  public void setPositionAsAvailable(Vector2d position) {
    newFreePosition(position);
  }
}
