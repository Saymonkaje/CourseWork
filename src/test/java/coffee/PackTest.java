package coffee;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PackTest {

    @Test
    void toStringWithoutId() {
        Pack pack = new Pack("test1",0.32,1);
        assertEquals("Назва: test1, об'єм: 0.32",pack.toStringWithoutId());
    }
}