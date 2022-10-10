package text;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EngineTest {

    @Test
    void testEngineCreating(){
        Engine engine = new Engine(100, EngineType.PHOTONIC, 100, 100);
        assertEquals(engine.getEngineType(), EngineType.PHOTONIC);
        assertEquals(engine.getPower(), 100);
        assertEquals(engine.getFuelConsumption(), 100);
        assertEquals(engine.getWeight(), 100);
    }

    @Test
    void testEngineSet(){
        Engine engine = new Engine(100, EngineType.PHOTONIC, 100, 100);
        engine.setEngineType(EngineType.GAS);
        engine.setFuelConsumption(10);
        engine.setPower(10);
        engine.setWeight(10);
        assertEquals(engine.getEngineType(), EngineType.GAS);
        assertEquals(engine.getPower(), 10);
        assertEquals(engine.getFuelConsumption(), 10);
        assertEquals(engine.getWeight(), 10);
    }
}
