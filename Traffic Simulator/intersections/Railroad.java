package com.apd.tema2.intersections;

import com.apd.tema2.Main;
import com.apd.tema2.entities.Car;
import com.apd.tema2.entities.Intersection;

import java.util.Iterator;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BrokenBarrierException;

public class Railroad implements Intersection {
    public Object lockObject = new Object();

    @Override
    public void resolveIntersection(Car car) throws InterruptedException, BrokenBarrierException {

        /* Masina ajunge la bariera si asteapta trecerea trenului, fiind adaugata in coada de asteptare. */
        System.out.println("Car " + car.getId() + " from side number " + car.getStartDirection() + " has stopped by the railroad");
        Main.carQueue.add(car);

        /* Se asteapta ca toate masinile sa ajunga la bariera. */
        Main.barrier.await();

        /*
         Asiguram faptul ca o singura masina va afisa mesajul si alegem ca aceasta masina
         sa fie cea cu ID-ul egal cu 0.
        */
        if (car.getId() == 0) {
            System.out.println("The train has passed, cars can now proceed");
        }

        synchronized (lockObject) {

            /* Se permite trecerea masinilor, bariera este ridicata. */
            if (Main.carQueue.size() == Main.carsNo) {
                Iterator iteratorValues = Main.carQueue.iterator();

                while (iteratorValues.hasNext()) {
                    Car currentCar = ((Car)iteratorValues.next());
                    System.out.println("Car " + currentCar.getId() + " from side number " + currentCar.getStartDirection() + " has started driving");
                }
                Main.carQueue = new ArrayBlockingQueue<>(1);
            }
        }
    }
}
