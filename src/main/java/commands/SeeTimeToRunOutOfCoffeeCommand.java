package commands;

import Model.Model;

public class SeeTimeToRunOutOfCoffeeCommand extends Command{
    public SeeTimeToRunOutOfCoffeeCommand(Model model) {
        super(model);
    }

    @Override
    public boolean execute() {
        model.showTimeToRunOutOfCoffee();
        return false;
    }

    @Override
    public String toString() {
        return "Вивести час до вичерпання видів кави";
    }
}
