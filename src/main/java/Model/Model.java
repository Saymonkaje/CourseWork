package Model;

import DAO.ColumnConstant;
import DAO.DBManager;
import DAO.QueryConstant;
import View.View;
import coffee.Coffee;
import coffee.Pack;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import Controller.SafeScan;
import logger.MyLogger;

public class Model {

    DBManager database;

    View view;
    public Model(DBManager database)
    {
        this.database = database;
        view = new View();
    }
    private Map<Integer,String> getAndShowSortMap ()
    {
        MyLogger.getLogger().log(Level.INFO,"getting sort map");
        System.out.println("Оберіть сорт кави (введіть id)");
        Map<Integer, String> map = database.selectSort();
        view.showMap(map,"назва сорту");
        return map;
    }

    private Map<Integer,String> getAndShowConditionMap ()
    {
        MyLogger.getLogger().log(Level.INFO,"getting condition map");
        System.out.println("Оберіть фізичний стан кави (введіть id)");
        Map<Integer, String> map = database.selectCondition();
        view.showMap(map,"фізичний стан");
        return map;
    }
    private Coffee checkCoffeeId (List<Coffee> list, int id)
    {
        MyLogger.getLogger().log(Level.INFO,"coffee id check");
        for(Coffee entity:list)
        {
         if(entity.getId()==id)
             return entity;
        }
        return null;
    }

    private boolean checkPackId(List<Pack> list, int id)
    {
        MyLogger.getLogger().log(Level.INFO,"pack id check");
        for(Pack pack:list)
        {
            if(pack.getId()==id)
                return true;
        }
        return false;
    }

    private boolean checkId(Map<Integer,String> map,int id )
    {
        MyLogger.getLogger().log(Level.INFO,"map id check");
        for(Integer key:map.keySet())
        {
            if(key==id)
                return false;
        }
        return true;
    }


    public void loadCoffee() {
        System.out.println("Оберіть тип кави, який хочете завантажити");
        List<Coffee> list = getAndShowCoffee(QueryConstant.selectAllCoffee);
        System.out.println("\nВведіть id завантажуваного виду кави, або 0, якщо потрібного виду нема в переліку");
        int choose;
        do {
             choose = SafeScan.getInstance().safeScanInt();
            if (choose == 0) {
                loadNewCoffee();
                return;
            }
        } while (checkCoffeeId(list, choose)==null);
        System.out.println("Введіть скільки кілограмів кави хочете завантажити");
        double weight = SafeScan.getInstance().safeScanDoubleInTheRange(0, Double.MAX_VALUE);
        database.updateCoffeeWeight(choose, weight);
    }

    private void loadNewCoffee()
    {
        MyLogger.getLogger().log(Level.INFO,"loading new coffee");
        int pack_id = choosePack();
        int sort_id = chooseSort();
        int condition_id = chooseCondition();
        System.out.println("Введіть скільки кілограмів кави хочете завантажити");
        double weight = SafeScan.getInstance().safeScanDoubleInTheRange(0,Double.MAX_VALUE);
        System.out.println("Введіть ціну за кілограм");
        double price_per_kilo = SafeScan.getInstance().safeScanDoubleInTheRange(0,Double.MAX_VALUE);
        database.insertCoffee(pack_id,sort_id,condition_id,weight,price_per_kilo);
    }

    private Coffee inputCoffeeId(List<Coffee> list)
    {
        MyLogger.getLogger().log(Level.INFO,"input coffee id");
        int choose = SafeScan.getInstance().safeScanInt();
        Coffee coffee;
        if((coffee=checkCoffeeId(list,choose))==null)
        {
            System.out.println("Ви ввели неіснуючий id, подумайте над своєю поведінкою");
            return null;
        }
        return coffee;
    }

    private String inputSort()
    {
        MyLogger.getLogger().log(Level.INFO,"input coffee sort");
        Map<Integer,String> map = getAndShowSortMap();
        int sort_id;
        sort_id = SafeScan.getInstance().safeScanInt();
        if(checkId(map,sort_id)) {
            System.out.println("Ви ввели неіснуючий Id, подумайте над своєю поведінкою");
            return null;
        }
        return map.get(sort_id);
    }
    private String inputCondition()
    {
        MyLogger.getLogger().log(Level.INFO,"input coffee condition");
        Map<Integer,String> map = getAndShowConditionMap();
        int condition_id;
        condition_id = SafeScan.getInstance().safeScanInt();
        if(checkId(map,condition_id)) {
            MyLogger.getLogger().log(Level.INFO,"was input incorrect id");
            System.out.println("Ви ввели неіснуючий Id, подумайте над своєю поведінкою");
            return null;
        }
        return map.get(condition_id);
    }
    private int choosePack()
    {
        MyLogger.getLogger().log(Level.INFO,"choosing pack");
        System.out.println("Оберіть упаковку, в якій буде кава");
        List<Pack> list = database.selectPack();
        for(Pack pack: list )
            System.out.println(pack);
        System.out.println("Введіть 0, якщо потрібної упаковки немає в перерахованих");
        int pack_id;
        do {
             pack_id = SafeScan.getInstance().safeScanInt();
            if (pack_id == 0) {
                pack_id = insertPack();
                break;
            }
        }while (!checkPackId(list,pack_id));
        return pack_id;
    }
    private int chooseSort() {
        MyLogger.getLogger().log(Level.INFO,"choosing sort");
        Map<Integer,String> map = getAndShowSortMap();
        System.out.println("Введіть 0, якщо потрібного сорту кави немає в перерахованих");
        int sort_id;
        do {
            sort_id = SafeScan.getInstance().safeScanInt();
            if (sort_id == 0) {
                sort_id = insertSort();
                break;
            }
        } while (checkId(map, sort_id));
        return sort_id;
    }

    private int chooseCondition() {
        MyLogger.getLogger().log(Level.INFO,"choosing condition");
        System.out.println("Оберіть фізичний стан кави");
        Map<Integer, String> map = database.selectCondition();
        map.entrySet().forEach(x -> System.out.println(x.toString()));
        System.out.println("Введіть 0, якщо потрібного фізичного стану немає в перерахованих");
        int phys;
        do {
            phys = SafeScan.getInstance().safeScanInt();
            if (phys == 0) {
                phys = insertCondition();
                break;
            }
        } while (checkId(map, phys));
        return phys;
    }
    public int insertPack()
    {
        MyLogger.getLogger().log(Level.INFO,"inserting pack");
        System.out.println("Введіть назву упаковки в якій буде кава");
        String pack_name = SafeScan.getInstance().nextLine();
        System.out.println("Введіть об'єм упаковки");
        double volume = SafeScan.getInstance().safeScanDoubleInTheRange(0.0001,Double.MAX_VALUE);
        return database.insertPack(volume,pack_name);
    }

    public int insertSort()
    {
        MyLogger.getLogger().log(Level.INFO,"inserting sort");
        System.out.println("Введіть назву сорту кави");
        String sort = SafeScan.getInstance().nextLine();
        return database.insertSort(sort);
    }
    public int insertCondition()
    {
        MyLogger.getLogger().log(Level.INFO,"inserting condition");
        System.out.println("Введіть фізичний стан кави");
        String condition = SafeScan.getInstance().nextLine();
        return database.insertCondition(condition);
    }

    public boolean consumptionCoffee()
    {
        MyLogger.getLogger().log(Level.INFO,"adding info about coffee consumption");
        System.out.println("Оберіть id кави, яка була витрачена");
        List<Coffee> list = getAndShowCoffee(QueryConstant.selectAllCoffee);
        Coffee coffee = inputCoffeeId(list);
        if(coffee==null)
            return false;
        System.out.println("Введіть скільки грамів кави було витрачено");
        double consumedWeight = SafeScan.getInstance().safeScanDoubleInTheRange(0,Double.MAX_VALUE);
        double newWeight = coffee.getWeight() - consumedWeight;
        if (newWeight<0)
        {
            MyLogger.getLogger().log(Level.INFO,"input incorrect consumed weight");
            System.out.println("Ви витратили кави більше ніж було, ви розумієте, що ви накоїли?\n" +
                    "ВІДХИЛЕННЯ ОПЕРАЦІЇ");
            return false;
        }
        if(newWeight > 0) {
            database.updateCoffeeWeight(coffee.getId(),-consumedWeight);
            return true;
        }
        database.deleteCoffee(coffee.getId());
        System.out.println("Кава вичерпана");
        coffee.setWeight(newWeight);
        System.out.println(coffee);
        return true;
    }

    public void setNewPrice()
    {
        MyLogger.getLogger().log(Level.INFO,"setting new price");
        System.out.println("Оберіть id кави, ціну якої ви хочете змінити");
        List<Coffee> list = getAndShowCoffee(QueryConstant.selectAllCoffee);
        Coffee coffee = inputCoffeeId(list);
        if(coffee==null)
            return;
        System.out.println("Введіть нову ціну");
        double newPrice = SafeScan.getInstance().safeScanDoubleInTheRange(0,Double.MAX_VALUE);
        database.updateCoffeePrice(coffee.getId(),newPrice);
    }
    public List<Coffee> getAndShowCoffee(String select)
    {
        List<Coffee> list =database.selectCoffee(select);
        view.showCoffee(list);
        return list;
    }


    public List<Coffee> showCoffeeThatWillSoonRunOut()
    {
        return getAndShowCoffee(QueryConstant.selectAllCoffee+" where "+ColumnConstant.weight+"<50");

    }

    public List<Coffee> showAndSortCoffee()
    {
        MyLogger.getLogger().log(Level.INFO,"showing coffee");
        System.out.println("Оберіть по чому хочете посортувати");
        System.out.println("""
                1 - по вазі
                2 - по ціні за кілограм
                3 - по сорту
                4 - по об'єму пакетика
                5 - по фізичному стану""");
        int choose = SafeScan.getInstance().safeScanIntInTheRange(1,5);
        String[] variants = {ColumnConstant.weight,ColumnConstant.pricePerKilo,
                ColumnConstant.sort,ColumnConstant.packVolume,ColumnConstant.physCondition};
        return getAndShowCoffee(QueryConstant.selectAllCoffee+QueryConstant.orderBy +variants[choose-1]);

    }

    public List<Coffee> searchCoffee()
    {
        MyLogger.getLogger().log(Level.INFO,"searching coffee");
        System.out.println("Введіть через пробіл номери потрібних критеріїв пошуку");
        System.out.println("""
                1 - вага
                2 - ціна за кілограм
                3 - сорт
                4 - об'єм упаковки
                5 - фізичний стан""");
        String input = SafeScan.getInstance().nextLine();
        List<Integer> chooses = Arrays.stream(input.split(" ")).map(Integer::parseInt).toList();
        StringBuilder query = new StringBuilder(" where ");
        for(int i = 0;i<chooses.size();i++)
        {
            switch (chooses.get(i)) {
                case 1 -> {
                    query.append(ColumnConstant.weight);
                    query.append(setNumComparison("Вага"));
                }
                case 2 -> {
                    query.append(ColumnConstant.pricePerKilo);
                    query.append(setNumComparison("Ціна за кілограм"));
                }
                case 3 -> {
                    query.append(ColumnConstant.sort + "=");
                    query.append("'").append(setSortComparison()).append("'");
                }
                case 4 -> {
                    query.append(ColumnConstant.packVolume);
                    query.append(setNumComparison("Об'єм упаковки"));
                }
                case 5 -> {
                    query.append(ColumnConstant.physCondition + "=");
                    query.append("'").append(setConditionComparison()).append("'");
                }
            }
            if(i!=chooses.size()-1)
                query.append(" and ");
        }
        return getAndShowCoffee(QueryConstant.selectAllCoffee+query);
    }

    private String setConditionComparison()
    {
        MyLogger.getLogger().log(Level.INFO,"setting condition comparison");
        System.out.println("Фізичний стан кави:");
        String condition = inputCondition();
        while (condition == null)
        {
            condition= inputCondition();
        }
        return condition;
    }
    private String setSortComparison()
    {
        MyLogger.getLogger().log(Level.INFO,"setting sort comparison");
        System.out.println("Сорт кави:");
        String sort = inputSort();
        while (sort == null)
        {
            sort= inputSort();
        }
        return sort;
    }

    private String setNumComparison(String criteria)
    {
        MyLogger.getLogger().log(Level.INFO,"setting number comparison");
        System.out.println(criteria+":");
        String symbol = chooseNumComparison();
        System.out.println("Введіть число з яким повірнюється критерій");
        double num = SafeScan.getInstance().safeScanDouble();
        return symbol+" "+num;
    }

    private String  chooseNumComparison()
    {
        MyLogger.getLogger().log(Level.INFO,"choosing symbol for number comparison");
        System.out.println("""
                Оберіть потрібний символ порівняння
                1 - =
                2 - >
                3 - >=
                4 - <
                5 - <=""");
        String [] compare = {"=", ">", ">=","<","<="};
        int choose = SafeScan.getInstance().safeScanIntInTheRange(1,5);
        return compare[choose-1];
    }
    public double showTotalPrice()
    {
        MyLogger.getLogger().log(Level.INFO,"showing total price");
        double totalPrice = database.selectTotalPrice();
        System.out.println(totalPrice);
        return totalPrice;
    }
    public double showTotalWeight()
    {
        MyLogger.getLogger().log(Level.INFO,"showing total weight");
        double totalWeight = database.selectTotalWeight();
        System.out.println(totalWeight);
        return totalWeight;
    }


    public Map<Coffee,Integer> showTimeToRunOutOfCoffee()
    {
        Map<Integer,Double> map = database.selectAverageConsumption();
        List<Coffee> coffeeList = database.selectCoffee(QueryConstant.selectAllCoffee);
        Map<Coffee,Integer> coffeeRunOut = coffeeList.stream().
                filter(x->map.get(x.getId())!=null).
                collect(Collectors.
                        toMap(x->x,x-> (int) (x.getWeight() / map.get(x.getId()))));
        view.showTimeToRunOutCoffee(coffeeRunOut);
        return coffeeRunOut;
    }
}
