package com.apd.tema2;

import com.apd.tema2.entities.Car;
import com.apd.tema2.entities.Intersection;
import com.apd.tema2.entities.Pedestrians;
import com.apd.tema2.io.Reader;

import java.util.ArrayList;
import java.util.Set;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Semaphore;

public class Main {
    public static Pedestrians pedestrians = null;
    public static Intersection intersection;
    public static int carsNo;
    public static Semaphore sem;
    public static CyclicBarrier barrier;
    public static CyclicBarrier barrierLanes;
    public static ArrayList<Semaphore> semaphores = new ArrayList<>();
    public static ArrayBlockingQueue<Integer> blockingQueue;
    public static ArrayList<ArrayBlockingQueue> senses = new ArrayList<>();
    public static ArrayBlockingQueue<Car> carQueue;

    public static void main(String[] args) {
        Reader fileReader = Reader.getInstance(args[0]);
        Set<Thread> cars = fileReader.getCarsFromInput();

        for(Thread car : cars) {
            car.start();
        }

        if(pedestrians != null) {
            try {
                Thread p = new Thread(pedestrians);
                p.start();
                p.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        for(Thread car : cars) {
            try {
                car.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
