package game.player;

import game.exceptions.OutOfMoneyException;

public abstract class Person {
    private String mName;
    private double mMoney;

    public Person(String name, double money) {
        setName(name);
        setMoney(money);
    }

    public String getName() {
        return mName;
    }

    private void setName(String name) {
        mName = name;
    }

    public double getMoney() {
        return mMoney;
    }

    public void setMoney(double money) {
        mMoney = money;
    }

    public void decreaseMoney(double amount) throws OutOfMoneyException {
        if (getMoney() - amount < 0)
            throw new OutOfMoneyException(getName());
        setMoney(getMoney() - amount);
    }

    public void increaseMoney(double amount) {
        setMoney(getMoney() + amount);
    }

    public void requestMoneyFrom(Person person, double amount) {
        person.decreaseMoney(amount);
        increaseMoney(amount);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Person)
            return ((Person) obj).getName().equals(getName());
        return false;
    }
}
