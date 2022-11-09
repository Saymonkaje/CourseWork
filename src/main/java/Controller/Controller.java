package Controller;

import DAO.DBManager;
import Model.Model;
import commands.*;
import commands.searching.SearchCommand;
import commands.showing.ShowCoffeeCommand;
import logger.MyLogger;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class Controller {
    List<Command> commands;

    Controller(Model model)
    {

        commands = new ArrayList<>();
        commands.add(new LoadCoffeeCommand(model));
        commands.add(new CoffeeConsumptionCommand(model));
        commands.add(new ChangeCoffeePriceCommand(model));
        commands.add(new SeeTimeToRunOutOfCoffeeCommand(model));
        commands.add(new ShowCoffeeCommand(model));
        commands.add(new SearchCommand(model));
       commands.add(new ExitCommand(model));
    }

    public void start()
    {
        MyLogger.getLogger().log(Level.INFO,"Starting app");
        int choose;
        while (true)
        {
            showMenu();
            choose = SafeScan.getInstance().safeScanIntInTheRange(1,commands.size());
            if(choose == commands.size())
                break;
            commands.get(choose-1).execute();
        }
        MyLogger.getLogger().log(Level.INFO,"Ending app");
    }

    private void showMenu()
    {
        System.out.println();
        for(int i = 0;i<commands.size();i++)
            System.out.println((i+1)+" - " + commands.get(i));
        MyLogger.getLogger().log(Level.INFO,"Showing menu");
    }
    public static void main(String[] args){
        Model model1 = new Model(DBManager.getInstance());
        Controller controller = new Controller(model1);
        controller.start();
    }

}
