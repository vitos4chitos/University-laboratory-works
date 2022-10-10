package text;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PersonTest {

    public static Stream<Arguments> testTestForPersonFeelings() {
        Long[] sourceArray = { 1L, 1L, 2L, 3L, 5L, 8L, 13L };
        Long[] sourceArrayTwo = { 1L, 2L, 4L, 8L, 16L, 32L, 64L };
        Long[] sourceArrayThree = { 1L, 2L, 3L, 4L, 5L, 6L, 7L };
        List<Long> longList = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            long x = ThreadLocalRandom.current().nextLong();
            longList.add(x);
        }

        return Stream.of(
                Arguments.of(
                        new ArrayList<>(Arrays.asList(sourceArray)),
                new ArrayList<>(Arrays.asList(sourceArrayTwo)),
                new ArrayList<>(Arrays.asList(sourceArrayThree)),
                longList)
        );
    }

    @ParameterizedTest
    @MethodSource
    void testTestForPersonFeelings(List<Long> longs, List<Long> longsTwo, List<Long> longsThree, List<Long> longsFour) {
        Person personOne = new Person("Nikita", "Mishanin", 20, 1, longs);
        assertEquals(FeelCategory.BEST, personOne.howAreYouFeeling(longs));
        assertEquals(FeelCategory.GOOD, personOne.howAreYouFeeling(longsTwo));
        assertEquals(FeelCategory.NORMAL, personOne.howAreYouFeeling(longsThree));
        assertEquals(FeelCategory.UNCOMFORTABLE, personOne.howAreYouFeeling(longsFour));
    }

}