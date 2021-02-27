package com.apd.tema2.intersections;

import com.apd.tema2.entities.Car;
import com.apd.tema2.entities.Intersection;

public class Simple_semaphore implements Intersection {

    @Override
    public void resolveIntersection(Car car) throws InterruptedException {

        /* Masina ajunge la semafor si afiseaza un mesaj corespunzator. */
        System.out.println("Car " + car.getId() + " has reached the semaphore, now waiting...");

        /* Masina asteapta timpul specific al sau la semafor */
        Thread.sleep(car.getWaitingTime());

        /* Masina poate pleca de la semafor. */
        System.out.println("Car " + car.getId() + " has waited enough, now driving...");
    }
}
