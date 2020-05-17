package other;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CheckRestrTest {

    @Test
    void checkInput() {
        int n = 6;
        List<Pair> restrictions = Arrays.asList(new Pair(1, 2), new Pair(4, 5));
        List<Pair> input = Arrays.asList(
                new Pair(1, 6),
                new Pair(1, 2),
                new Pair(6, 4),
                new Pair(2, 4),
                new Pair (3, 2),
                new Pair (2, 5),
                new Pair (2, 6),
                new Pair(5, 4)
        );
        List<Pair> result = CheckRestr.checkInput(n, restrictions, input);
        assertEquals("[(1, 6), (6, 4), (3, 2), (2, 5)]", result.toString());
    }
}