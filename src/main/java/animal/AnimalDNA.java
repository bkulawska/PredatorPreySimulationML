package animal;

import java.util.ArrayList;
import java.util.List;

public interface AnimalDNA {
    List<Integer> getDNAToMultiply();

    double getSpeed();

    double getEnergyConsumption();

    @Override
    boolean equals(Object o);

    @Override
    int hashCode();

    @Override
    String toString();
}
