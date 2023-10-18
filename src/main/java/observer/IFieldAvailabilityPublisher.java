package observer;

public interface IFieldAvailabilityPublisher {
  void addObserver(IFieldAvailabilityObserver observer);

  void removeObserver(IFieldAvailabilityObserver observer);
}
