package commands.showing;

import Model.Model;
import commands.Command;

public class ShowTotalPriceCommand extends Command {
    public ShowTotalPriceCommand(Model model) {
        super(model);
    }

    @Override
    public boolean execute() {
        model.showTotalPrice();
        return false;
    }

    @Override
    public String toString() {
        return "Показати загальну ціну усієї кави";
    }

}
