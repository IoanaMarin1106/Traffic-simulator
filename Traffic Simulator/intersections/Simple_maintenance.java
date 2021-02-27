package com.apd.tema2.intersections;

import com.apd.tema2.Main;
import com.apd.tema2.entities.Car;
import com.apd.tema2.entities.Intersection;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.atomic.AtomicInteger;

public class Simple_maintenance implements Intersection {
    private static final int SENSE = 0;
    private static final int OTHER_SENSE = 1;

    private int noOfCarPassingAtOnce;
    private AtomicInteger carsCurrentNumber;

    public void setCarsCurrentNumber(AtomicInteger carsCurrentNumber) {
        this.carsCurrentNumber = carsCurrentNumber;
    }

    public void setNoOfCarPassingAtOnce(int noOfCarPassingAtOnce) {
        this.noOfCarPassingAtOnce = noOfCarPassingAtOnce;
    }

    @Override
    public void resolveIntersection(Car car) throws InterruptedException, BrokenBarrierException {

        synchronized (this) {

            /*
             Masina ajunge la drumul in lucru si se incrementeaza numarul curent de masini si
             se va introduce in coada de masini de pe sensul sau.
            */
            System.out.println("Car " + car.getId() + " from side number " + car.getStartDirection() + " has reached the bottleneck");
            this.carsCurrentNumber.addAndGet(1);

            Main.senses.get(car.getStartDirection()).add(car.getId());

            /*
             Daca numarul de masini din fiecare coada este egal cu numarul de masini ce trebuie sa treaca, atunci
             se vor lasa masinile de pe ambele sensuri sa treaca.
            */
            if (Main.senses.get(SENSE).size() == this.noOfCarPassingAtOnce &&
               Main.senses.get(OTHER_SENSE).size() == this.noOfCarPassingAtOnce) {

                /* Trec masinile de pe sensul 0. */
                while (!Main.senses.get(SENSE).isEmpty()) {
                    System.out.println("Car " + Main.senses.get(SENSE).poll() + " from side number 0 has passed the bottleneck");
                }

                /* Trec masinile de pe sensul 1. */
                while (!Main.senses.get(OTHER_SENSE).isEmpty()) {
                    System.out.println("Car " + Main.senses.get(OTHER_SENSE).poll() + " from side number 1 has passed the bottleneck");
                }
            }

            /*
             Daca nu mai este nicio masina ce trebuie sa ajunga la drumul in lucru,
             atunci vor trece si masinile care au mai ramas in fiecare coada.
             Masinile de pe sensul 0.
            */
            if (this.carsCurrentNumber.intValue() == Main.carsNo &&
                !Main.senses.get(SENSE).isEmpty()) {
                while (!Main.senses.get(SENSE).isEmpty()) {
                    System.out.println("Car " + Main.senses.get(SENSE).poll() + " from side number 0 has passed the bottleneck");
                }
            }

            /* Masinile de pe sensul 1 */
            if (this.carsCurrentNumber.intValue() == Main.carsNo &&
                !Main.senses.get(OTHER_SENSE).isEmpty()) {
                while (!Main.senses.get(OTHER_SENSE).isEmpty()) {
                    System.out.println("Car " + Main.senses.get(OTHER_SENSE).poll() + " from side number 1 has passed the bottleneck");
                }
            }
        }
    }
}
