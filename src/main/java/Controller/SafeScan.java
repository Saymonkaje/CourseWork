package Controller;

import logger.MyLogger;

import java.io.InputStream;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;

public class SafeScan {

    private Scanner scanner;
    private static SafeScan instance;

    private SafeScan(){
        scanner = new Scanner(System.in);
    }

    public static SafeScan getInstance() {
        if(instance == null)
            instance = new SafeScan();
        return instance;
    }


    public int scanIntWithoutNextLine()
    {
        int choose = 0;
        try {
            choose = scanner.nextInt();
        } catch (InputMismatchException e) {
            MyLogger.getLogger().log(Level.SEVERE,"Was input incorrect integer",e);
            System.out.println("Ви вчинили неправильно, бо ввели букви замість чисел, подумайте над своїми діями");
            System.exit(1);
        }
        return choose;
    }
    public int safeScanInt() {
        int choose = 0;
        try {
            choose = scanner.nextInt();
            scanner.nextLine();
        } catch (InputMismatchException e) {
            MyLogger.getLogger().log(Level.SEVERE,"Was input incorrect integer",e);
            System.out.println("Ви вчинили неправильно, бо ввели букви замість чисел, подумайте над своїми діями");
            System.exit(1);
        }
        return choose;
    }

    public int safeScanIntInTheRange(int lowBound, int upperBound)//inclusive
    {
        int choose;
        choose = safeScanInt();
        if(choose<lowBound||choose>upperBound)
            throw new IllegalArgumentException();
        return choose;
    }
    public double safeScanDouble() {
        double choose = 0;
        try {
            choose = scanner.nextDouble();
            scanner.nextLine();
        } catch (InputMismatchException e) {
            MyLogger.getLogger().log(Level.SEVERE,"Was input incorrect double",e);
            System.out.println("Ви вчинили неправильно, бо ввели букви замість чисел, подумайте над своїми діями");
            System.exit(1);
        }
        return choose;
    }

    public double safeScanDoubleInTheRange(double lowBound, double upperBound)//inclusive
    {
        double choose;
        do {
             choose = safeScanDouble();
        }while (choose<lowBound||choose>upperBound);
        return choose;
    }

    public void setScannerOut(InputStream inputStream)
    {
        scanner = new Scanner(inputStream);
    }

    public String nextLine()
    {
       return scanner.nextLine();
    }
}
