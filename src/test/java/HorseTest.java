import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class HorseTest {
    @Test
    public void nullHorse() {
        assertThrows(IllegalArgumentException.class, () -> new Horse(null, 10, 10));
    }

    @Test
    public void nullHorseMsgText() {
        String errMsg = "";
        try {
            new Horse(null, 10, 10);
        }
        catch(IllegalArgumentException e) {
            errMsg = e.getMessage();
        }

        assertEquals("Name cannot be null.", errMsg);
    }

    @ParameterizedTest
    @ValueSource(strings = {"", " ", "\t"})
    public void emptyHorse(String arg) {
        assertThrows(IllegalArgumentException.class, () -> new Horse(arg, 10, 10));
    }

    @ParameterizedTest
    @ValueSource(strings = {"", " ", "\t"})
    public void emptyHorseMsgTest(String arg) {
        String errMsg = "";
        try {
            new Horse(arg, 10, 10);
        }
        catch(IllegalArgumentException e) {
            errMsg = e.getMessage();
        }

        assertEquals("Name cannot be blank.", errMsg);
    }

    @Test
    public void negativeSpeed() {
        assertThrows(IllegalArgumentException.class, () -> new Horse("test", -10, 10));
    }

    @Test
    public void negativeSpeedMsgText() {
        String errMsg = "";
        try {
            new Horse("test", -10, 10);
        }
        catch(IllegalArgumentException e) {
            errMsg = e.getMessage();
        }

        assertEquals("Speed cannot be negative.", errMsg);
    }

    @Test
    public void negativeDistance() {
        assertThrows(IllegalArgumentException.class, () -> new Horse("test", 10, -10));
    }

    @Test
    public void negativeDistanceMsgText() {
        String errMsg = "";
        try {
            new Horse("test", 10, -10);
        }
        catch(IllegalArgumentException e) {
            errMsg = e.getMessage();
        }

        assertEquals("Distance cannot be negative.", errMsg);
    }

    @Test
    public void getHorseName() {
        String horseName = "test";
        Horse horse = new Horse(horseName, 10);
        assertEquals(horseName, horse.getName());
    }

    @Test
    public void getHorseSpeed() {
        int horseSpeed = 10;
        Horse horse = new Horse("test", horseSpeed);
        assertEquals(horseSpeed, horse.getSpeed());
    }

    @Test
    public void getHorseDistance() {
        int horseDistance = 10;
        Horse horse = new Horse("test",5, horseDistance);
        assertEquals(horseDistance, horse.getDistance());
    }

    @Test
    public void getHorseDefaultDistance() {
        Horse horse = new Horse("test",5);
        assertEquals(0, horse.getDistance());
    }

    @Test
    public void moveUseRandomDouble() {
        try(MockedStatic<Horse> dummyHorse = Mockito.mockStatic(Horse.class)) {
            Horse horse = new Horse("Test", 10);
            horse.move();
            dummyHorse.verify(() -> Horse.getRandomDouble(0.2, 0.9));
        }
    }

    @ParameterizedTest
    @ValueSource(doubles = {0.3, 0.6})
    public void moveCalcDistance(double d) {
        try(MockedStatic<Horse> dummyHorse = Mockito.mockStatic(Horse.class)) {
            dummyHorse.when(() -> Horse.getRandomDouble(0.2, 0.9)).thenReturn(d);
            Horse horse = new Horse("Test", 10, 5);
            horse.move();
            assertEquals(5+10*d, horse.getDistance());
        }
    }
}
