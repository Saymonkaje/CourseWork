package coffee;

import java.util.Objects;

public class Coffee{
    private final int id;
    private double weight;
    private final String  sort;
    private final double pricePerKilogram;
    private final Pack pack;
    private final String physicalCondition;

    @Override
    public String toString() {
        return  "id: " + id+
                ", вага: " + weight +
                ", сорт: " + sort +
                ", ціна за кілограм: " + pricePerKilogram +
                ", упаковка: " + pack.toStringWithoutId() +
                ", фізичний стан: " + physicalCondition;
    }

    public Coffee(String sort, double pricePerKilogram, Pack pack, String physicalCondition,int id, double weight) {
        this.sort = sort;
        this.pricePerKilogram = pricePerKilogram;
        this.pack = pack;
        this.physicalCondition = physicalCondition;
        this.weight = weight;
        this.id = id;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public double getPricePerKilogram() {
        return pricePerKilogram;
    }

    public double getWeight() {
        return weight;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Coffee coffee)) return false;
        return pricePerKilogram == coffee.pricePerKilogram
                && Objects.equals(sort, coffee.sort)
                && Objects.equals(pack, coffee.pack)
                && physicalCondition == coffee.physicalCondition;
    }

    @Override
    public int hashCode() {
        return Objects.hash(sort, pricePerKilogram, pack, physicalCondition);
    }

    public int getId() {
        return id;
    }
}
