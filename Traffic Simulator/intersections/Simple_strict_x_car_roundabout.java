package com.apd.tema2.intersections;

import com.apd.tema2.Main;
import com.apd.tema2.entities.Car;
import com.apd.tema2.entities.Intersection;

import java.util.concurrent.BrokenBarrierException;

public class Simple_strict_x_car_roundabout implements Intersection {
    private int noOfLanes;
    private int roundaboutPassingTime;
    private int exactNoOfCarsPassingAtOnce;

    public int getNoOfLanes() {
        return noOfLanes;
    }

    public void setNoOfLanes(int noOfLanes) {
        this.noOfLanes = noOfLanes;
    }

    public void setRoundaboutPassingTime(int roundaboutPassingTime) {
        this.roundaboutPassingTime = roundaboutPassingTime;
    }

    public int getExactNoOfCarsPassingAtOnce() {
        return exactNoOfCarsPassingAtOnce;
    }

    public void setExactNoOfCarsPassingAtOnce(int exactNoOfCarsPassingAtOnce) {
        this.exactNoOfCarsPassingAtOnce = exactNoOfCarsPassingAtOnce;
    }

    @Override
    public void resolveIntersection(Car car) throws InterruptedException, BrokenBarrierException {

        /* Masina ajunge la sensul giratoriu. */
        System.out.println("Car " + car.getId() + " has reached the roundabout, now waiting...");

        /* Asiguram faptul ca toate masinile ajung la sensul giratoriu inainte de a se trece mai departe. */
        Main.barrier.await();

        /* Masina verifica daca poate intra in sensul giratoriu si afiseaza un mesaj corespunzator */
        Main.semaphores.get(car.getStartDirection()).acquire();

        System.out.println("Car " + car.getId() + " was selected to enter the roundabout from lane " + car.getStartDirection());

        /*
         Ne asiguram ca toate masinile de pe toate sensurile parasesc sensul giratoriu inainte
         ca o runda noua de masini sa intre in intersectie.
        */
        Main.barrierLanes.await();

        System.out.println("Car "+ car.getId() + " has entered the roundabout from lane " + car.getStartDirection());

        /*
         Masina simuleaza trecerea prin intersectie asteptand T milisecunde dupa care
         iese din sensul giratoriu si afiseaza un mesaj corespunzator.
        */
        Thread.sleep(this.roundaboutPassingTime);

        int seconds = this.roundaboutPassingTime / 1000;
        System.out.println("Car " + car.getId() + " has exited the roundabout after " + seconds + " seconds");

        /*
         Se incrementeaza valoarea semaforului corespunzator directiei masinii pentru a
         semnala iesirea din intersectie.
        */
        Main.semaphores.get(car.getStartDirection()).release();
    }
}
