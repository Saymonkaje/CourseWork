package commands.searching;

import Model.Model;
import commands.Command;
public class SearchCommand extends Command {

    public SearchCommand(Model model) {
        super(model);
    }

    @Override
    public boolean execute() {
        model.searchCoffee();
        return false;
    }

    @Override
    public String toString() {
        return "Пошук кави за заданими критеріями";
    }
}
