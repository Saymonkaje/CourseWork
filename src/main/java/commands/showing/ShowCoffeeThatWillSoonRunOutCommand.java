package commands.showing;

import Model.Model;
import commands.Command;

public class ShowCoffeeThatWillSoonRunOutCommand extends Command {

    public ShowCoffeeThatWillSoonRunOutCommand(Model model) {
        super(model);
    }

    @Override
    public boolean execute() {
        model.showCoffeeThatWillSoonRunOut();
        return true;
    }

    @Override
    public String toString() {
        return "Показати каву, що скоро закінчиться";
    }
}
