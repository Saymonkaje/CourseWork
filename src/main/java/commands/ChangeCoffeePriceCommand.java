package commands;

import Model.Model;

public class ChangeCoffeePriceCommand extends  Command{
    public ChangeCoffeePriceCommand(Model model) {
        super(model);
    }

    @Override
    public boolean execute() {
        model.setNewPrice();
        System.out.println("Ціна успішно змінена!");
        return false;
    }

    @Override
    public String toString() {
        return "Оновити ціну кави";
    }
}
