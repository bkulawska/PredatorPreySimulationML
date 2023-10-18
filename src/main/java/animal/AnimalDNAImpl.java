package animal;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public abstract class AnimalDNAImpl implements AnimalDNA{
    private final static int DNA_LENGTH = 8;
    private List<Integer> firstHelix = new ArrayList<>();
    private List<Integer> secondHelix = new ArrayList<>();
    private String objectToString = null;

    public AnimalDNAImpl() {
        for(int i = 0; i < DNA_LENGTH; i++) {
            firstHelix.add((int)(Math.random()*8));
            secondHelix.add((int)(Math.random()*8));
        }
    }

    public AnimalDNAImpl(List<Integer> dnaToMultiply, List<Integer> dnaToMultiply1) {
        firstHelix = dnaToMultiply;
        secondHelix = dnaToMultiply1;
    }

    public List<Integer> getDNAToMultiply(double mutationChance) {
        List<Integer> newDNA = new ArrayList<>();
        for(int i = 0; i < firstHelix.size(); i++) {
            if(Math.random() < 0.5) {
                newDNA.add(firstHelix.get(i));
            } else{
                newDNA.add(secondHelix.get(i));
            }
        }
        return newDNA.stream().map(element -> {
            if(Math.random() < mutationChance) {
                if(element == 7) {
                    return 6;
                }
                if(element == 0) {
                    return 1;
                }
                return element + getMutation();
            }
            return element;
        }).toList();
    }

    private int getMutation() {
        return (int)(((int)(Math.random()*2) - 0.5) * 2);
    }

   public double calculateValue(double multiplier1, double multiplier2, double multiplier3) {
        return getValue(1) * multiplier1 + getValue(3) * multiplier2 + getValue(6) * multiplier3;
    }

    private double getValue(int index) {
        double value1 = firstHelix.get(index);
        double value2 = secondHelix.get(index);
        return Math.max(value1, value2) + 1;
    }

   @Override
   public String toString() {
       if(objectToString == null) {
           String first = firstHelix.stream().map(element -> (char)('A' + element)).reduce(new StringBuilder(), StringBuilder::append, StringBuilder::append).toString();
           String second = secondHelix.stream().map(element -> (char)('A' + element)).reduce(new StringBuilder(), StringBuilder::append, StringBuilder::append).toString();
           objectToString = first + "\n" + second;
       }
       return objectToString;
   }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        return this.toString().equals(o.toString());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.toString());
    }
}
