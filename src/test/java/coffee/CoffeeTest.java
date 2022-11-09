package coffee;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CoffeeTest {

    static Coffee coffee;
    @BeforeAll
    static void init()
    {
         coffee = new Coffee("test",100,new Pack("test",10,0)
         ,"test",0,1);
    }

    @Test
    void setWeight() {
        int weight = 100;
        coffee.setWeight(weight);
        assertEquals(weight,coffee.getWeight());
        weight += 100;
        coffee.setWeight(weight);
        assertEquals(weight,coffee.getWeight());
    }

}