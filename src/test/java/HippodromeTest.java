import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class HippodromeTest {
    @Test
    public void nullHorses() {
        assertThrows(IllegalArgumentException.class, () -> new Hippodrome(null));
    }

    @Test
    public void nullHorsesMsgText() {
        String errMsg = "";
        try {
            new Hippodrome(null);
        }
        catch(IllegalArgumentException e) {
            errMsg = e.getMessage();
        }

        assertEquals("Horses cannot be null.", errMsg);
    }

    @Test
    public void emptyHorses() {
        assertThrows(IllegalArgumentException.class, () -> new Hippodrome(new ArrayList<>()));
    }

    @Test
    public void emptyHorsesMsgText() {
        String errMsg = "";
        try {
            new Hippodrome(new ArrayList<>());
        }
        catch(IllegalArgumentException e) {
            errMsg = e.getMessage();
        }

        assertEquals("Horses cannot be empty.", errMsg);
    }

    @Test
    public void getHorsesResult() {
        List<Horse> horses = new ArrayList<>();

        for (int i = 0; i < 30; i++) {
            horses.add(new Horse(String.valueOf(i), i, i));
        }

        assertIterableEquals(horses, new Hippodrome(horses).getHorses());
    }

    @Test
    public void moveHorses() {
        List<Horse> horses = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            horses.add(Mockito.mock(Horse.class));
        }

        new Hippodrome(horses).move();

        for (var horse:horses) {
            Mockito.verify(horse).move();
        }
    }

    @Test
    public void getWinnerResult() {
        List<Horse> horses = new ArrayList<>();

        for (int i = 0; i < 30; i++) {
            horses.add(new Horse(String.valueOf(i), i, i));
        }

        Hippodrome hippodrome = new Hippodrome(horses);
        hippodrome.move();

        assertEquals(horses.stream().max(Comparator.comparing(Horse::getDistance)).get(), hippodrome.getWinner());
    }
}
