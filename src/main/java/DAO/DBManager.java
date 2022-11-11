package DAO;

import coffee.Coffee;
import coffee.Pack;
import logger.MyLogger;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

public class DBManager {
    private static DBManager instance;
    private static final String URL ="jdbc:mysql://localhost:3306/course_work" ;
    private static final String USERNAME = "root";
    private static final String PASSWORD = "root";
    private final Connection connection;

    private DBManager ()
    {
        MyLogger.getLogger().info("getting connection with database");
        try {
            connection = DriverManager.getConnection(URL,USERNAME,PASSWORD);
        } catch (SQLException e) {
            MyLogger.getLogger().error("error connecting with database",e);
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public static DBManager getInstance() {
        if(instance==null)
            instance = new DBManager();
        return instance;
    }


    public List<Coffee> selectCoffee(String query)
    {
        MyLogger.getLogger().info("selecting coffee");
        List<Coffee> coffeeList= new ArrayList<>();
        try( Statement  statement =connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)){
            int n = 0;
            while (resultSet.next())
            {
                coffeeList.add(new Coffee(resultSet.getString(ColumnConstant.sort.trim()),
                        resultSet.getDouble(ColumnConstant.pricePerKilo.trim()),
                        new Pack(resultSet.getString(ColumnConstant.packName.trim()),
                                resultSet.getDouble(ColumnConstant.packVolume.trim()),0),
                        resultSet.getString(ColumnConstant.physCondition.trim()),
                        resultSet.getInt(ColumnConstant.coffeeId.trim()),
                        resultSet.getDouble(ColumnConstant.weight.trim())));
                n++;
            }
            if(n==0)
                System.out.println("Даних немає");
        } catch (SQLException e) {
            MyLogger.getLogger().error("error selecting coffee",e);
            throw new RuntimeException(e);
        }
        return coffeeList;
    }

    public Map<Integer, Double> selectAverageConsumption()
    {
        MyLogger.getLogger().info("selecting average consumption");
        Map<Integer,Double> map = new HashMap<>();
        try(Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(QueryConstant.selectAverageConsumptionPerMonth)){
            int n = 0;
            while (resultSet.next()) {
                map.put(resultSet.getInt(1), resultSet.getDouble(2));
                n++;
            }
            if(n==0)
                System.out.println("Даних нема");
        } catch (SQLException e) {
            MyLogger.getLogger().error("error average consumption",e);
            throw new RuntimeException(e);
        }
        return map;
    }

    private void updateCoffeeNum(int id, double num, String query)
    {
        MyLogger.getLogger().info("updating some numeric property of coffee");
        try(PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setDouble(1,num);
            preparedStatement.setInt(2,id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            MyLogger.getLogger().error("error updating some numeric property of coffee",e);
            throw new RuntimeException(e);
        }
    }
    public void updateCoffeeWeight(int id, double weight)
    {
        MyLogger.getLogger().info("updating coffee weight");
        updateCoffeeNum(id,weight,QueryConstant.updateCoffeeWeight);
    }
    public void updateCoffeePrice(int id, double newPrice)
    {
        MyLogger.getLogger().info("updating coffee price");
        updateCoffeeNum(id,newPrice,QueryConstant.updateCoffeePrice);
    }


    public Map<Integer,String> selectSort() {
        return selectIdAndName(QueryConstant.selectSort);
    }

    public List<Pack> selectPack() {
        MyLogger.getLogger().info("selecting pack");
        List<Pack> packList = new ArrayList<>();
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(QueryConstant.selectPack)) {
            int n = 0;
            while (resultSet.next()) {
                packList.add(new Pack(resultSet.getString("pack_name"),
                        resultSet.getDouble("volume"),resultSet.getInt("id")));
                n++;
            }
            if(n==0)
                System.out.println("Даних нема");
        } catch (SQLException e) {
            MyLogger.getLogger().error("error selecting pack",e);
            throw new RuntimeException(e);
        }
        return packList;
    }
    public Map<Integer,String> selectCondition() {
        MyLogger.getLogger().info("selecting condition");
        return selectIdAndName(QueryConstant.selectCondition);
    }

    private Map<Integer,String> selectIdAndName(String query)
    {
        MyLogger.getLogger().info("selecting id and name");
        Map<Integer,String> packList = new HashMap<>();
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {
            int n =0;
            while (resultSet.next()) {
                packList.put(resultSet.getInt(1),resultSet.getString(2));
                n++;
            }
            if(n==0)
                System.out.println("Даних нема");
        } catch (SQLException e) {
            MyLogger.getLogger().error("error selecting id and name",e);
            throw new RuntimeException(e);
        }
        return packList;
    }

    public double selectTotalPrice()
    {
        return selectSum(QueryConstant.selectTotalPrice);
    }
    public double selectTotalWeight()
    {
        return selectSum(QueryConstant.selectTotalWeight);
    }

    private double selectSum(String query)
    {
        MyLogger.getLogger().info("selecting sum");
        double sum = 0;
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {
            int n = 0;
            if(resultSet.next()) {
                sum = resultSet.getDouble(1);
                n++;
            }
            if(n==0)
                System.out.println("Даних нема");
        } catch (SQLException e) {
            MyLogger.getLogger().error("error selecting sum",e);
            throw new RuntimeException(e);
        }
        return sum;
    }
    public void insertCoffee (int pack_id,int type_id,
                              int condition_id, double weight,
                              double price_per_kilogram)
    {
        MyLogger.getLogger().info("inserting coffee");
        try(PreparedStatement preparedStatement= connection.prepareStatement(QueryConstant.insertCoffee)) {
            preparedStatement.setInt(1,pack_id);
            preparedStatement.setInt(2,type_id);
            preparedStatement.setInt(3,condition_id);
            preparedStatement.setDouble(4,weight);
            preparedStatement.setDouble(5,price_per_kilogram);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            MyLogger.getLogger().info("error inserting coffee");
            throw new RuntimeException(e);
        }
    }
    public int insertPack (double volume, String name)
    {
        MyLogger.getLogger().info("inserting pack");
        int id=0;
        try(PreparedStatement preparedStatement = connection.prepareStatement(QueryConstant.insertPack);
            Statement statement = connection.createStatement()) {
            preparedStatement.setDouble(1,volume);
            preparedStatement.setString(2,name);
            preparedStatement.executeUpdate();
            ResultSet resultSet = statement.executeQuery(QueryConstant.getLastIdCreated);
            if(resultSet.next())
                id = resultSet.getInt(1);
            resultSet.close();
            if(id==0)
                throw new SQLException("ПАКЕТИК НЕ ВСТАВИВСЯ");
        } catch (SQLException e) {
            MyLogger.getLogger().error("error inserting pack", e);
            e.printStackTrace();
            throw new RuntimeException(e);

        }
        return id;
    }


    public int insertCondition(String condition)
    {
        MyLogger.getLogger().info("inserting condition");
        return insertString(condition,QueryConstant.insertCondition);
    }

    public int insertSort(String sort)
    {
        MyLogger.getLogger().info("inserting sort");
        return insertString(sort,QueryConstant.insertCoffeeSort);
    }

    private int insertString(String stringToBeInserted, String query) {
        int id = 0;
        MyLogger.getLogger().info("inserting some string");
        try(PreparedStatement preparedStatement = connection.prepareStatement(query);
            Statement statement = connection.createStatement()) {
            preparedStatement.setString(1, stringToBeInserted);
            preparedStatement.executeUpdate();
            ResultSet resultSet = statement.executeQuery(QueryConstant.getLastIdCreated);
            if(resultSet.next())
                id = resultSet.getInt(1);
            resultSet.close();
        } catch (SQLException e) {
            MyLogger.getLogger().error("error inserting some string",e);
            MyLogger.getLogger().error("error inserting some string",e);
            throw new RuntimeException(e);
        }
        return id;
    }

    public void deleteCoffee(int id)
    {
        MyLogger.getLogger().info("deleting coffee");
        try(PreparedStatement preparedStatement = connection.prepareStatement(QueryConstant.deleteCoffeeById)) {
            preparedStatement.setInt(1,id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            MyLogger.getLogger().error("error deleting coffee",e);
            throw new RuntimeException(e);
        }
    }
    public void close()
    {
        MyLogger.getLogger().info("closing connection");
        try {
            if(!connection.isClosed())
                connection.close();
        } catch (SQLException e) {
            MyLogger.getLogger().error("error closing connection",e);
            throw new RuntimeException(e);
        }
    }

}
