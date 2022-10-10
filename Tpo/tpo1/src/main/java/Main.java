import hashMap.HashMap;
import text.*;

public class Main {
    public static void main(String[] args) {
//        FunctionDecomposition f = new FunctionDecomposition();
//        ArrayList<Person> crew = new ArrayList<>();

        // final double x = 3. * (PI / 180);
        // System.out.println(f.tryFunctionDecomposition(x, 3L));

        Engine engine = new Engine(200, EngineType.SOLAR, 10, 1000);
        Crew crew = new Crew();
        SpaceShip spaceShip = new SpaceShip("AAA", engine, crew, 1000, 25, -25, 400,
                MovementType.RIDE);
        spaceShip.move(1, 1);
        System.out.println(spaceShip.getFuel());

    }
}
