import unils.Calc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        int[] ints = new int[999999999];
        int processCount = Runtime.getRuntime().availableProcessors();

        List<Calc> calcs = new ArrayList<>(processCount);
        Calc calc;
        int startposition = 0;
        System.out.println(ints.length);
        int partOfints = ints.length / processCount;
        // этот костыль просто чтоб программа корректно работала на значениях массива меньше количества процессоров,
        // хотя это и нафиг ни нужно, потому что при маленьком размере массива выгода многопоточности не видна
        if (partOfints < 8) {
            partOfints = ints.length;
            processCount = 1;
        }

        // #1  способ через несколько потоков
        Arrays.fill(ints, 1);
        long tm1 = System.currentTimeMillis();

        for (int i = 0; i < processCount; i++) {
            // тут  костыль для того чтобы корректно считалась сумма для последнего кусочка
            // когда длинна массива на кол-во процессоров не делится без остатка
            if (i == processCount - 1) {
                partOfints += ints.length % processCount;
            }
            calc = new Calc(ints, startposition, startposition += partOfints);
            calcs.add(calc);
            calc.start();
        }

        long sum = 0;
        for (Calc c : calcs) {
            c.join();
            sum += c.get();
        }

        System.out.println("многопоточный подсчёт Sum1 = " + sum);
        System.out.println("Time1 = " + (System.currentTimeMillis() - tm1));

        // #2  простой способ подсчёта однопоточный
        tm1 = System.currentTimeMillis();
        sum = 0;

        for (int i = 0; i < ints.length; i++)
            sum += ints[i];

        System.out.println("однопоточный подсчёт Sum2 = " + sum);
        System.out.println("Time2 = " + (System.currentTimeMillis() - tm1));
    }
}
