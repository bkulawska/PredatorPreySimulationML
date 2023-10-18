package observer;

import vector2d.Vector2d;

public interface IFieldAvailabilityObserver {
  void setPositionAsUnavailable(Vector2d position);

  void setPositionAsAvailable(Vector2d position);
}
