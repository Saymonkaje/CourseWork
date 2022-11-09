
package coffee;

public record Pack(String type, double volume, int id)  {


    @Override
    public String toString() {
        return "Id "+id+ ", Назва: "+type+ ", об'єм=" + volume;
    }

    public String toStringWithoutId()
    {
        return "Назва: "+type+", об'єм: "+ volume;
    }

    public int getId() {
        return id;
    }
}




