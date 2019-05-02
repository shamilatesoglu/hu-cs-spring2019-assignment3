package game.properties;

public class Land extends Property {

    public Land(String name, double cost) {
        super(name, cost);
    }

    @Override
    public double getRent() {
        return (getCost() <= 2000) ? getCost() * .4 : (getCost() > 2000 && getCost() <= 3000) ? getCost() * .3 : getCost() * .35;
    }

}
