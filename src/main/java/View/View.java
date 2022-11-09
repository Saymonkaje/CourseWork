package View;

import coffee.Coffee;
import logger.MyLogger;

import java.util.List;
import java.util.Map;
import java.util.logging.Level;

public class View {
    public void showMap(Map<Integer,String> map, String name)
    {
        MyLogger.getLogger().log(Level.INFO,"showing some map");
        for(Map.Entry<Integer,String> entry: map.entrySet())
            System.out.println("id: "+ entry.getKey()+", "+name+": "+entry.getValue());
    }
    public void showCoffee(List<?> list )
    {
        MyLogger.getLogger().log(Level.INFO,"showing coffee");
        list.forEach(x-> System.out.println(x.toString()));
    }

    public void showTimeToRunOutCoffee(Map<Coffee,Integer> coffeeIntegerMap)
    {
        MyLogger.getLogger().log(Level.INFO,"showing time to run out of coffee");
        coffeeIntegerMap.
                forEach((coffee,days)->
                        System.out.println(coffee+", кава закінчиться через "+ days + " днів"));
    }

}
