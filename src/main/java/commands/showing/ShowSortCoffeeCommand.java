package commands.showing;

import Model.Model;
import commands.Command;

public class ShowSortCoffeeCommand extends Command {

    public ShowSortCoffeeCommand(Model model) {
        super(model);
    }

    @Override
    public boolean execute() {
        model.showAndSortCoffee();
        return false;
    }

    @Override
    public String toString() {
        return "Посортувати та вивести каву";
    }
}
