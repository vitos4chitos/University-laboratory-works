package text;

public class Engine {
    private long power;
    private EngineType engineType;
    private long fuelConsumption;
    private long weight;

    public Engine(long power, EngineType engineType, long fuelConsumption, long weight) {
        this.power = power;
        this.engineType = engineType;
        this.fuelConsumption = fuelConsumption;
        this.weight = weight;
    }

    public long getPower() {
        return power;
    }

    public void setPower(long power) {
        this.power = power;
    }

    public EngineType getEngineType() {
        return engineType;
    }

    public void setEngineType(EngineType engineType) {
        this.engineType = engineType;
    }

    public long getFuelConsumption() {
        return fuelConsumption;
    }

    public void setFuelConsumption(long fuelConsumption) {
        this.fuelConsumption = fuelConsumption;
    }

    public long getWeight() {
        return weight;
    }

    public void setWeight(long weight) {
        this.weight = weight;
    }
}
