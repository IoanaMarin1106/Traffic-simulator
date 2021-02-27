package com.apd.tema2.intersections;

import com.apd.tema2.Main;
import com.apd.tema2.entities.Car;
import com.apd.tema2.entities.Intersection;

import java.util.concurrent.BrokenBarrierException;

public class Simple_max_x_car_roundabout implements Intersection {
    private int noOfLanes;
    private int roundaboutPassingTime;
    private int maxNoOfCarsPassingAtOnce;

    public int getNoOfLanes() {
        return noOfLanes;
    }

    public void setNoOfLanes(int noOfLanes) {
        this.noOfLanes = noOfLanes;
    }

    public void setRoundaboutPassingTime(int roundaboutPassingTime) {
        this.roundaboutPassingTime = roundaboutPassingTime;
    }

    public int getMaxNoOfCarsPassingAtOnce() {
        return maxNoOfCarsPassingAtOnce;
    }

    public void setMaxNoOfCarsPassingAtOnce(int maxNoOfCarsPassingAtOnce) {
        this.maxNoOfCarsPassingAtOnce = maxNoOfCarsPassingAtOnce;
    }

    @Override
    public void resolveIntersection(Car car) throws InterruptedException, BrokenBarrierException {

        /* Masina ajunge la sensul giratoriu si afiseaza un mesaj corespunzator */
        System.out.println("Car " + car.getId() + " has reached the roundabout from lane " + car.getStartDirection());

        /* Masina verifica daca poate intra in sensul giratoriu si afiseaza un mesaj corespunzator */
        Main.semaphores.get(car.getStartDirection()).acquire();
        System.out.println("Car "+ car.getId() + " has entered the roundabout from lane " + car.getStartDirection());

        /* Masina asteapta un timp corespunzator pentru a trece prin intersectie */
        Thread.sleep(this.roundaboutPassingTime);

        /* Masina iese din sensul giratoriu si afiseaza un mesaj corespunzator. */
        int seconds = this.roundaboutPassingTime / 1000;
        System.out.println("Car " + car.getId() + " has exited the roundabout after " + seconds + " seconds");

        /*
         Se incrementeaza valoarea semaforului corespunzator directiei masinii pentru a
         semnala iesirea din intersectie.
        */
        Main.semaphores.get(car.getStartDirection()).release();
    }
}
