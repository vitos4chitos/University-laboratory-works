package text;

import hashMap.HashMap;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class SpaceShipTest {

    @Test
    void testSHCreating(){
        Engine engine = new Engine(100, EngineType.PHOTONIC, 100, 100);
        Crew crew = new Crew();
        SpaceShip spaceShip = new SpaceShip("AAA", engine, crew, 10000, 0, 0, 200,
                MovementType.RIDE);
        assertEquals(spaceShip.getCrew(), crew);
        assertEquals(spaceShip.getEngine(), engine);
        assertEquals(spaceShip.getName(), "AAA");
        assertEquals(spaceShip.getFuel(), 10000);
        assertEquals(spaceShip.getMovementType(), MovementType.RIDE);
        assertEquals(spaceShip.getWeight(), 200);
        assertEquals(spaceShip.getxCoordinate(), 0);
        assertEquals(spaceShip.getyCoordinate(), 0);
    }

    @Test
    void testSHSet(){
        Engine engine = new Engine(100, EngineType.PHOTONIC, 100, 100);
        Crew crew = new Crew();
        SpaceShip spaceShip = new SpaceShip("AAA", engine, crew, 10000, 0, 0, 200,
                MovementType.RIDE);
        Crew setCrew = new Crew(1, new ArrayList<Person>(), "CrewName");
        spaceShip.setCrew(setCrew);
        assertEquals(spaceShip.getCrew(), setCrew);
        Engine setEngine = new Engine(10, EngineType.GAS, 10, 10);
        spaceShip.setEngine(setEngine);
        assertEquals(spaceShip.getEngine(), setEngine);
        spaceShip.setFuel(0);
        spaceShip.setMovementType(MovementType.CRAWL);
        spaceShip.setName("Solar");
        spaceShip.setWeight(400);
        spaceShip.setxCoordinate(1);
        spaceShip.setyCoordinate(1);
        assertEquals(spaceShip.getName(), "Solar");
        assertEquals(spaceShip.getFuel(), 0);
        assertEquals(spaceShip.getMovementType(), MovementType.CRAWL);
        assertEquals(spaceShip.getWeight(), 400);
        assertEquals(spaceShip.getxCoordinate(), 1);
        assertEquals(spaceShip.getyCoordinate(), 1);
    }

    @Test
    void testSHCanGet(){
        Engine engine = new Engine(100, EngineType.PHOTONIC, 100, 100);
        Crew crew = new Crew();
        SpaceShip spaceShip = new SpaceShip("AAA", engine, crew, 10000, 0, 0, 200,
                MovementType.RIDE);
        double length1 = 10.0;
        double length2 = 0;
        double length3 = 10000000000.1;
        assertTrue(spaceShip.canGet(length1));
        assertTrue(spaceShip.canGet(length2));
        assertFalse(spaceShip.canGet(length3));
    }

    @Test
    void testSHFuelCalculation(){
        Engine engine = new Engine(100, EngineType.PHOTONIC, 100, 100);
        Crew crew = new Crew();
        SpaceShip spaceShip = new SpaceShip("AAA", engine, crew, 10000, 0, 0, 200,
                MovementType.RIDE);
        long fuelCalc1 = 100 * 500;
        long fuelCalc2 = 100 * 11;
        long fuelCalc3 = 0L;
        assertEquals(spaceShip.fuelCalculation(500), fuelCalc1);
        assertEquals(spaceShip.fuelCalculation(11), fuelCalc2);
        assertEquals(spaceShip.fuelCalculation(0.1), fuelCalc3);
    }

    @Test
    void testSHTravelTime(){
        Engine engine = new Engine(100, EngineType.PHOTONIC, 100, 100);
        Crew crew = new Crew();
        SpaceShip spaceShip = new SpaceShip("AAA", engine, crew, 10000, 0, 0, 200,
                MovementType.RIDE);
        double time1 = ((double)(100 + 200) * 100 * 9.8)/ (double) 100;
        double time2 = ((double)(100 + 200) * 200 * 9.8)/ (double) 100;
        double time3 = ((double)(100 + 200) * 0.1 * 9.8)/ (double) 100;
        assertEquals(spaceShip.travelTime(100), time1);
        assertEquals(spaceShip.travelTime(200), time2);
        assertEquals(spaceShip.travelTime(0.1), time3);
    }

    @Test
    void testSHMove1(){
        Engine engine = new Engine(100, EngineType.PHOTONIC, 100, 100);
        Crew crew = new Crew();
        SpaceShip spaceShip = new SpaceShip("AAA", engine, crew, 10000, 0, 0, 200,
                MovementType.RIDE);
        spaceShip.move(1, 1);
        assertEquals(spaceShip.getxCoordinate(), 1);
        assertEquals(spaceShip.getyCoordinate(), 1);
        long fuel = 10000 - (long) Math.sqrt(Math.pow(1, 2) + Math.pow(1, 2)) * 100;
        assertEquals(spaceShip.getFuel(), fuel);
    }

    @Test
    void testSHMove2(){
        Engine engine = new Engine(200, EngineType.SOLAR, 10, 1000);
        Crew crew = new Crew();
        SpaceShip spaceShip = new SpaceShip("AAA", engine, crew, 1000, 25, -25, 400,
                MovementType.RIDE);
        spaceShip.move(1, 1);
        assertEquals(spaceShip.getxCoordinate(), 1);
        assertEquals(spaceShip.getyCoordinate(), 1);
        long fuel = 1000 - (long) Math.sqrt(Math.pow(1 - 25 , 2) + Math.pow(1 + 25, 2)) * 10;
        assertEquals(spaceShip.getFuel(), fuel);
    }

    @Test
    void testSHMove3(){
        Engine engine = new Engine(200, EngineType.SOLAR, 100, 1000);
        Crew crew = new Crew();
        SpaceShip spaceShip = new SpaceShip("AAA", engine, crew, 1000, 25, -25, 400,
                MovementType.RIDE);
        spaceShip.move(1000, 1000);
        assertEquals(spaceShip.getFuel(), 1000);
        assertEquals(spaceShip.getyCoordinate(), -25);
        assertEquals(spaceShip.getxCoordinate(), 25);
    }

}
