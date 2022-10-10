package text;

public class SpaceShip implements Move{

    private String name;
    private Engine engine;
    private Crew crew;
    private long fuel;
    private long xCoordinate;
    private long yCoordinate;
    private long weight;
    private MovementType movementType;

    public SpaceShip(String name, Engine engine, Crew crew, long fuel, long xCoordinate, long yCoordinate, long weight,
                     MovementType movementType) {
        this.name = name;
        this.engine = engine;
        this.crew = crew;
        this.fuel = fuel;
        this.xCoordinate = xCoordinate;
        this.yCoordinate = yCoordinate;
        this.weight = weight;
        this.movementType = movementType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Engine getEngine() {
        return engine;
    }

    public void setEngine(Engine engine) {
        this.engine = engine;
    }

    public Crew getCrew() {
        return crew;
    }

    public void setCrew(Crew crew) {
        this.crew = crew;
    }

    public long getFuel() {
        return fuel;
    }

    public void setFuel(long fuel) {
        this.fuel = fuel;
    }

    public double getxCoordinate() {
        return xCoordinate;
    }


    public double getyCoordinate() {
        return yCoordinate;
    }

    public void setxCoordinate(long xCoordinate) {
        this.xCoordinate = xCoordinate;
    }

    public void setyCoordinate(long yCoordinate) {
        this.yCoordinate = yCoordinate;
    }

    public long getWeight() {
        return weight;
    }

    public void setWeight(long weight) {
        this.weight = weight;
    }

    public MovementType getMovementType() {
        return movementType;
    }

    public void setMovementType(MovementType movementType) {
        this.movementType = movementType;
    }

    @Override
    public void move(long x, long y){
        double length = Math.sqrt(Math.pow(x - xCoordinate, 2) + Math.pow(y - yCoordinate, 2));
        if(canGet(length)){
            fuel -= fuelCalculation(length);
            xCoordinate = x;
            yCoordinate = y;
        }
    }

    public long fuelCalculation(double length){
        return (long) length * engine.getFuelConsumption();
    }

    @Override
    public double travelTime(double length){
        return ((double)(weight + engine.getWeight()) * length * 9.8)/ (double) engine.getPower();
    }

    @Override
    public boolean canGet(double length){
        return fuelCalculation(length) <= fuel;
    }


}
