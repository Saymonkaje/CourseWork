package Model;

import Controller.SafeScan;
import DAO.ColumnConstant;
import DAO.DBManager;
import DAO.QueryConstant;
import coffee.Coffee;
import coffee.Pack;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.params.provider.Arguments.arguments;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ModelTest {

    static String filename = "src/main/resources/test2.txt";
    static FileWriter fileWriter;

    @Mock
    DBManager databaseMock;
    static List<Coffee> coffeeList = new ArrayList<>();
    static Map<Integer,Double> consumption = new HashMap<>();
    static Coffee coffeeId3;
    @BeforeAll
    static void setUp() throws IOException {
        coffeeId3 = new Coffee
                ("2",10,new Pack("test",10,1),"melena",3,50);
        coffeeList.add(coffeeId3);
        coffeeList.add(new Coffee
                ("arabica",10,new Pack("test",10,1),"melena",1,10));
     coffeeList.add(new Coffee
                ("arabica",10,new Pack("test",10,1),"z",2,100));



        consumption.put(3,10.0);
        fileWriter = new FileWriter(filename);
        InputStream inputStream = new FileInputStream(filename);
        System.setIn(inputStream);
        SafeScan.getInstance().setScannerOut(inputStream);
    }


    @Test
    void getAndShowCoffee() {
        assertNotNull(databaseMock);
        when(databaseMock.selectCoffee(QueryConstant.selectAllCoffee)).thenReturn(coffeeList);
        Model model = new Model(databaseMock);
        assertEquals(coffeeList,model.getAndShowCoffee(QueryConstant.selectAllCoffee));
    }

    @Test
    void showCoffeeThatWillSoonRunOut() {
        assertNotNull(databaseMock);
        when(databaseMock
                .selectCoffee(QueryConstant.selectAllCoffee+" where "+ ColumnConstant.weight+"<50"))
                .thenReturn(coffeeList.stream().filter(x-> x.getWeight()<50).toList());
        Model model = new Model(databaseMock);
        assertEquals(coffeeList.stream().filter(x-> x.getWeight()<50).toList(),
                model.showCoffeeThatWillSoonRunOut());
    }

    @Test
    void showAndSortCoffee() throws IOException {
        assertNotNull(databaseMock);
        when(databaseMock
                .selectCoffee(QueryConstant.selectAllCoffee+QueryConstant.orderBy +ColumnConstant.weight))
                .thenReturn(coffeeList.stream().sorted((x1,x2)-> (int) (x1.getWeight()-x2.getWeight())).toList());
        Model model = new Model(databaseMock);
        fileWriter.write("1\n");
        fileWriter.flush();
        assertEquals(coffeeList.stream().sorted((x1,x2)-> (int) (x1.getWeight()-x2.getWeight())).toList(),
                model.showAndSortCoffee());
    }

    @Test
    void searchCoffee() throws IOException {
        double weight = 50;
        int searchingType = 1;
        int symbolForNumComparison = 1;
        assertNotNull(databaseMock);
        when(databaseMock
                .selectCoffee(QueryConstant.selectAllCoffee+" where " +ColumnConstant.weight + "= " +weight))
                .thenReturn(coffeeList.stream().filter((x)-> x.getWeight()==50).toList());
        Model model = new Model(databaseMock);
        fileWriter.write(searchingType+"\n"+symbolForNumComparison+"\n50,0\n");
        fileWriter.flush();
        assertEquals(coffeeList.stream().filter((x)-> x.getWeight()==50).toList(),
                model.searchCoffee());
    }

    @Test
    void showTotalPrice() {
        assertNotNull(databaseMock);
        when(databaseMock
                .selectTotalPrice())
                .thenReturn(coffeeList.stream()
                        .mapToDouble(x->x.getPricePerKilogram()*x.getWeight())
                        .sum());

        Model model = new Model(databaseMock);
        assertEquals(coffeeList.stream()
                .mapToDouble(x->x.getPricePerKilogram()*x.getWeight())
                .sum(),model.showTotalPrice());
    }

    @Test
    void showTotalWeight() {
        assertNotNull(databaseMock);
        when(databaseMock
                .selectTotalWeight())
                .thenReturn(coffeeList.stream()
                        .mapToDouble(Coffee::getWeight)
                        .sum());

        Model model = new Model(databaseMock);
        assertEquals(coffeeList.stream()
                .mapToDouble(Coffee::getWeight)
                .sum(),model.showTotalWeight());
    }

    @Test
    void showTimeToRunOutOfCoffee() {

        assertNotNull(databaseMock);
        when(databaseMock.selectCoffee(QueryConstant.selectAllCoffee)).thenReturn(coffeeList);
        when(databaseMock
                .selectAverageConsumption())
                .thenReturn(consumption);
        Map<Coffee,Integer> coffeeIntegerMap = new HashMap<>();
        coffeeIntegerMap.put(coffeeId3,5);
        Model model = new Model(databaseMock);
        assertEquals(coffeeIntegerMap,model.showTimeToRunOutOfCoffee());
    }


    static Stream<Arguments> testCases()
    {
        return Stream.of(
                arguments(null,"1"),
                arguments(coffeeId3,"2"));
    }
}