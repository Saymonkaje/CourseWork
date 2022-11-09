package commands;

import Model.Model;

public class CoffeeConsumptionCommand extends Command{
    public CoffeeConsumptionCommand(Model model) {
        super(model);
    }

    @Override
    public boolean execute() {
        boolean res = model.consumptionCoffee();
        if(res)
            System.out.println("Вага кави успішно оновлена!");
        return res;
    }

    @Override
    public String toString() {
        return "Оновити інформацію про витрати кави";
    }
}
