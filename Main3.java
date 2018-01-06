/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 *
 * @author HERLEY
 */
public class Main3 {

    public static void main(String[] args) {

        int batasLoop = 5;
        final List<Integer> adder = new ArrayList();
        final ExecutorService executor = Executors.newFixedThreadPool(5);
        final List<Future<Integer>> fs = new ArrayList<>();

        for (int i = 0; i < batasLoop; i++) {
            Future<Integer> submit = executor.submit(() -> {
                final Random random = new Random();
                final int number = random.nextInt(100);
                System.out.println("Number: "+number+", Executed By: "+Thread.currentThread().getName());
                return number;
            });
            fs.add(submit);
        }
        try {
            for (Future<Integer> fusion : fs) {
                int a = fusion.get();
                adder.add(a);
                System.out.println("Added: "+a+" to List.");
            }
            executor.shutdown();
            
            System.out.println(adder);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void parallelAdder() {
        try {
            final class CallableAdder implements Callable<Integer> {

                Integer operand1;
                Integer operand2;

                CallableAdder(Integer operand1, Integer operand2) {
                    this.operand1 = operand1;
                    this.operand2 = operand2;
                }

                public Integer call() throws Exception {
                    // TODO Auto-generated method stub
                    System.out.println(Thread.currentThread().getName() + " says : partial Sum for " + operand1 + " and " + operand2 + " is " + (operand1 + operand2));
                    return operand1 + operand2;
                }

            }

            //long t1 = System.currentTimeMillis();
            ExecutorService executor = Executors.newFixedThreadPool(10);
            List<Future<Integer>> list = new ArrayList<Future<Integer>>();
            int count = 1;
            int prev = 0;
            for (int i = 0; i < 100; i++) {
                if (count % 2 == 0)//grouping
                {
                    System.out.println("Prev :" + prev + " current: " + i);
                    Future<Integer> future = executor.submit(new CallableAdder(prev, i));
                    list.add(future);
                    count = 1;
                    continue;
                }
                prev = i;
                count++;
            }
            int totsum = 0;

            for (Future<Integer> fut : list) {
                totsum = totsum + fut.get();

            }
            System.out.println("Size: " + list.size());
            System.out.println("Total Sum is " + totsum);
            //long t2 = System.currentTimeMillis();
            //System.out.println("Time taken by parallelSum " + (t2-t1));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
