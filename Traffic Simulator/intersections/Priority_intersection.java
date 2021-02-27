package com.apd.tema2.intersections;

import com.apd.tema2.Main;
import com.apd.tema2.entities.Car;
import com.apd.tema2.entities.Intersection;

import java.util.Iterator;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BrokenBarrierException;

public class Priority_intersection implements Intersection {
    private int noOfCarsWithHighPriority;
    private int noOfCarsWithLowPriority;
    private int carsInIntersection;

    public void setCarsInIntersection(int carsInIntersection) {
        this.carsInIntersection = carsInIntersection;
    }

    public void setNoOfCarsWithHighPriority(int noOfCarsWithHighPriority) {
        this.noOfCarsWithHighPriority = noOfCarsWithHighPriority;
    }

    public int getNoOfCarsWithLowPriority() {
        return noOfCarsWithLowPriority;
    }

    public void setNoOfCarsWithLowPriority(int noOfCarsWithLowPriority) {
        this.noOfCarsWithLowPriority = noOfCarsWithLowPriority;
    }

    @Override
    public void resolveIntersection(Car car) throws InterruptedException, BrokenBarrierException {
        /*
         Masinile cu prioritate vor intra in intersectie la orice moment si au nevoie de 2 secunde
         paraseasca intersectia.
        */
        if (car.getPriority() != 1) {
            System.out.println("Car " + car.getId() + " with high priority has entered the intersection");
            this.carsInIntersection += 1;

            int waitingTime = 2000;
            Thread.sleep(waitingTime);

            System.out.println("Car " + car.getId() + " with high priority has exited the intersection");
            this.carsInIntersection -= 1;
        }

        /*
         Daca avem masini ce asteapta sa intre in intersectie cu prioritate mica, verificam si le lasam
         sa intre.
        */
        if (this.carsInIntersection == 0 && Main.blockingQueue.size() != 0) {
            Iterator<Integer> iteratorValues = Main.blockingQueue.iterator();

            while (iteratorValues.hasNext()) {
                System.out.println("Car " + iteratorValues.next() + " with low priority has entered the intersection");
            }

            //Resetam coada de masini.
            Main.blockingQueue = new ArrayBlockingQueue<>(this.noOfCarsWithLowPriority);
        }

        /*
         Daca masina are prioritate mica, atunci se verifica daca se poate intra in intersectie sau daca
         se asteapta pana cand se poate intra in intersectie, adica pana cand nu mai sunt masini in intersectie.
        */
        if (car.getPriority() == 1) {
            System.out.println("Car " + car.getId() + " with low priority is trying to enter the intersection...");

            if (this.carsInIntersection == 0) {
                System.out.println("Car " + car.getId() + " with low priority has entered the intersection");
            } else {
                Main.blockingQueue.add(car.getId());
            }
        }
    }
}
