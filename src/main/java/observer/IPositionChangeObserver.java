package observer;
import animal.*;
import vector2d.*;

public interface IPositionChangeObserver {
  void positionChanged(Vector2d oldPosition, Vector2d newPosition, Animal animal);
}
