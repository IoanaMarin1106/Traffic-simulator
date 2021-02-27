package com.apd.tema2.intersections;

import com.apd.tema2.Main;
import com.apd.tema2.entities.Car;
import com.apd.tema2.entities.Intersection;

import java.util.concurrent.BrokenBarrierException;

public class Crosswalk implements Intersection {
    private String message;

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public void resolveIntersection(Car car) throws InterruptedException, BrokenBarrierException {

        /*
         Verificam daca nu s-au strans numarul de pietoni pentru a fi lasati sa treaca la trecere si
         daca s-au strans, atunci masinile vor putea trece.
        */
        if (!Main.pedestrians.isFinished()) {
            if (!this.message.equals("Car " + car.getId() + " has now green light") || this.message.length() == 0) {
                System.out.println("Car " + car.getId() + " has now green light");
                this.setMessage("Car " + car.getId() + " has now green light");
            }
        }

        /*
         Daca s-au strans destui pietoni la trecere, atunci masinile vor astepta sa treaca si vor avea
         culoarea rosie la semafor.
        */
        if (Main.pedestrians.isFinished()) {
            if (!this.message.equals("Car " + car.getId() + " has now red light") || this.message.length() == 0) {
                System.out.println("Car " + car.getId() + " has now red light");
                this.setMessage("Car " + car.getId() + " has now red light");
            }
        }
    }
}
