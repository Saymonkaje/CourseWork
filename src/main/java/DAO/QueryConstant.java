package DAO;

import static DAO.ColumnConstant.*;

public class QueryConstant {
    public static String selectAllCoffee = "select "+sort+", "+packName+
            "            ,"+packVolume+","+physCondition +
            "            ,"+weight+","+pricePerKilo+","+coffeeId+
            "            from coffee\n" +
            "            join coffee_type on coffee.type_id = coffee_type.id\n" +
            "            join pack on pack_id = pack.id\n" +
            "            join physical_condition on condition_id = physical_condition.id";

    public static String orderBy = " order by ";
    public static String selectTotalPrice = "select sum(price_per_kilogram*weight) from coffee";
    public static String selectTotalWeight = "select sum(weight) from coffee";

    public static String insertPack = "insert into pack (volume, pack_name)" +
            " values (?, ?)";
    public static String insertCondition =  "insert into physical_condition (phys_condition) values (?)";

    public static String insertCoffeeSort = "insert into coffee_type (type_name) values (?)";

    public static String insertCoffee = "insert into coffee" +
            "(pack_id,type_id,condition_id,weight,price_per_kilogram) " +
            "values(?,?,?,?,?)";
    public static String updateCoffeeWeight = "update coffee set weight = weight + ?" +
            " where id = ?";
    public static String updateCoffeePrice = "update coffee set "+pricePerKilo+ " = ? where id = ?";

    public static String getLastIdCreated = "select @@identity";
    public static String selectPack = "select * from pack";
    public static String selectSort = "select * from coffee_type";
    public static String selectCondition = "select * from physical_condition";
    public static final String selectAverageConsumptionPerMonth = "SELECT coffee_id, avg(consumedWeight)\n" +
            "FROM course_work.coffee_consumption_history\n" +
            "where datediff(now(), consumptionDate)<30\n" +
            "group by coffee_id";

    public static final String deleteCoffeeById = "delete from coffee where id = ?";
}
