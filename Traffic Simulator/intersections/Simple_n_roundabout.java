package com.apd.tema2.intersections;

import com.apd.tema2.Main;
import com.apd.tema2.entities.Car;
import com.apd.tema2.entities.Intersection;

import java.util.concurrent.BrokenBarrierException;

public class Simple_n_roundabout implements Intersection {
    private int maxNoOfCarsPassingAtOnce;
    private int roundaboutTime;

    public int getMaxNoOfCarsPassingAtOnce() {
        return maxNoOfCarsPassingAtOnce;
    }

    public void setMaxNoOfCarsPassingAtOnce(int maxNoOfCarsPassingAtOnce) {
        this.maxNoOfCarsPassingAtOnce = maxNoOfCarsPassingAtOnce;
    }

    public void setRoundaboutTime(int roundaboutTime) {
        this.roundaboutTime = roundaboutTime;
    }

    @Override
    public void resolveIntersection(Car car) throws InterruptedException, BrokenBarrierException {

        /* Masina ajunge la sensul giratoriu si va verifica daca poate sa intre.*/
        System.out.println("Car "+ car.getId() + " has reached the roundabout, now waiting...");

        /* Masina verifica daca poate sa intre in sensul giratoriu decrementand valoarea semforului */
        Main.sem.acquire();

        /* Masina a intrat in sensul giratoriu si afiseaza un mesaj corespunzator */
        System.out.println("Car " + car.getId() + " has entered the roundabout");

        /* Masina asteapta un anumit numar de secunde pentru a simula trecerea prin sensul giratoriu */
        Thread.sleep(this.roundaboutTime);

        /* Masina iese din sensul giratoriu si afiseaza un mesaj corespunzator */
        int seconds = this.roundaboutTime / 1000;
        System.out.println("Car " + car.getId() + " has exited the roundabout after " + seconds + " seconds");

        /* Se incrementeaza valoarea semaforului pentru a semnala iesirea din intersectie */
        Main.sem.release();
    }
}
