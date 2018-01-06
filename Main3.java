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
}
