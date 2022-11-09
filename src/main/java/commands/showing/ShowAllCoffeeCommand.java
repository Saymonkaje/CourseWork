package commands.showing;

import Model.Model;
import commands.Command;
import DAO.QueryConstant;
public class ShowAllCoffeeCommand extends Command {
    public ShowAllCoffeeCommand(Model model) {
        super(model);
    }

    @Override
    public boolean execute() {
        model.getAndShowCoffee(QueryConstant.selectAllCoffee);
        return false;
    }

    @Override
    public String toString() {
        return "Показати всю каву";
    }
}
