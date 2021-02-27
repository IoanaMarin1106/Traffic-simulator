package com.apd.tema2.factory;

import com.apd.tema2.Main;
import com.apd.tema2.entities.Pedestrians;
import com.apd.tema2.entities.ReaderHandler;
import com.apd.tema2.intersections.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Returneaza sub forma unor clase anonime implementari pentru metoda de citire din fisier.
 */
public class ReaderHandlerFactory {

    public static ReaderHandler getHandler(String handlerType) {
        // simple semaphore intersection
        // max random N cars roundabout (s time to exit each of them)
        // roundabout with exactly one car from each lane simultaneously
        // roundabout with exactly X cars from each lane simultaneously
        // roundabout with at most X cars from each lane simultaneously
        // entering a road without any priority
        // crosswalk activated on at least a number of people (s time to finish all of them)
        // road in maintenance - 1 lane 2 ways, X cars at a time
        // road in maintenance - N lanes 2 ways, X cars at a time
        // railroad blockage for T seconds for all the cars
        // unmarked intersection
        // cars racing
        switch (handlerType) {
            case "simple_semaphore":
                return new ReaderHandler() {
                    @Override
                    public void handle(final String handlerType, final BufferedReader br) throws IOException {
                    }
                };
            case "simple_n_roundabout":
                return new ReaderHandler() {
                    @Override
                    public void handle(final String handlerType, final BufferedReader br) throws IOException {
                        String[] line = br.readLine().split(" ");
                        Main.intersection = IntersectionFactory.getIntersection("simple_n_roundabout");

                        /* Se seteaza atributele clasei. */
                        ((Simple_n_roundabout)(Main.intersection)).setMaxNoOfCarsPassingAtOnce(Integer.parseInt(line[0]));
                        ((Simple_n_roundabout)(Main.intersection)).setRoundaboutTime(Integer.parseInt(line[1]));

                        /*
                         Se initializeaza semaforul folosit pentru a asigura faptul ca in
                         intersectie sunt maxim n masini la un moment dat.
                        */
                        Main.sem = new Semaphore(((Simple_n_roundabout)(Main.intersection)).getMaxNoOfCarsPassingAtOnce());
                    }
                };
            case "simple_strict_1_car_roundabout":
                return new ReaderHandler() {
                    @Override
                    public void handle(final String handlerType, final BufferedReader br) throws IOException {
                        String[] line = br.readLine().split(" ");
                        Main.intersection = IntersectionFactory.getIntersection("simple_strict_1_car_roundabout");

                        /* Se seteaza atributele clasei. */
                        ((Simple_strict_1_car_roundabout)Main.intersection).setNoOfLanes(Integer.parseInt(line[0]));
                        ((Simple_strict_1_car_roundabout)Main.intersection).setRoundaboutPassingTime(Integer.parseInt(line[1]));

                        /* Se initializeaza ArrayList-ul de semafoare pentru fiecare sens. */
                        int maxCarsInRoundabout = 1;
                        for (int i = 0; i < ((Simple_strict_1_car_roundabout)Main.intersection).getNoOfLanes(); i++) {
                            Main.semaphores.add(new Semaphore(maxCarsInRoundabout));
                        }
                    }
                };
            case "simple_strict_x_car_roundabout":
                return new ReaderHandler() {
                    @Override
                    public void handle(final String handlerType, final BufferedReader br) throws IOException {
                        String[] line = br.readLine().split(" ");
                        Main.intersection = IntersectionFactory.getIntersection("simple_strict_x_car_roundabout");

                        /* Se seteaza atributele clasei. */
                        ((Simple_strict_x_car_roundabout)Main.intersection).setNoOfLanes(Integer.parseInt(line[0]));
                        ((Simple_strict_x_car_roundabout)Main.intersection).setRoundaboutPassingTime(Integer.parseInt(line[1]));
                        ((Simple_strict_x_car_roundabout)Main.intersection).setExactNoOfCarsPassingAtOnce(Integer.parseInt(line[2]));

                        /* Se initializeaza cele doua bariere ce vor asigura respectarea conditiei impuse de cerinta. */
                        Main.barrier = new CyclicBarrier(Main.carsNo);

                        int maxCarsInRoundabout = ((Simple_strict_x_car_roundabout)Main.intersection).getExactNoOfCarsPassingAtOnce();
                        Main.barrierLanes = new CyclicBarrier(((Simple_strict_x_car_roundabout)Main.intersection).getNoOfLanes() * maxCarsInRoundabout);

                        /* Se initializeaza ArrayList-ul de semafoare pentru fiecare sens. */
                        for (int i = 0; i < ((Simple_strict_x_car_roundabout)Main.intersection).getNoOfLanes(); i++) {
                            Main.semaphores.add(new Semaphore(maxCarsInRoundabout));
                        }
                    }
                };
            case "simple_max_x_car_roundabout":
                return new ReaderHandler() {
                    @Override
                    public void handle(final String handlerType, final BufferedReader br) throws IOException {
                        String[] line = br.readLine().split(" ");
                        Main.intersection = IntersectionFactory.getIntersection("simple_max_x_car_roundabout");

                        /* Se seteaza atributele clasei. */
                        ((Simple_max_x_car_roundabout)Main.intersection).setNoOfLanes(Integer.parseInt(line[0]));
                        ((Simple_max_x_car_roundabout)Main.intersection).setRoundaboutPassingTime(Integer.parseInt(line[1]));
                        ((Simple_max_x_car_roundabout)Main.intersection).setMaxNoOfCarsPassingAtOnce(Integer.parseInt(line[2]));

                        int maxCarsInRoundabout = ((Simple_max_x_car_roundabout)Main.intersection).getMaxNoOfCarsPassingAtOnce();

                        /* Se initializeaza ArrayList-ul de semafoare pentru fiecare sens. */
                        for (int i = 0; i < ((Simple_max_x_car_roundabout)Main.intersection).getNoOfLanes(); i++) {
                            Main.semaphores.add(new Semaphore(maxCarsInRoundabout));
                        }

                    }
                };
            case "priority_intersection":
                return new ReaderHandler() {
                    @Override
                    public void handle(final String handlerType, final BufferedReader br) throws IOException {
                        String[] line = br.readLine().split(" ");
                        Main.intersection = IntersectionFactory.getIntersection("priority_intersection");

                        /* Se vor seta atributele clasei. */
                        ((Priority_intersection)Main.intersection).setNoOfCarsWithHighPriority(Integer.parseInt(line[0]));
                        ((Priority_intersection)Main.intersection).setNoOfCarsWithLowPriority(Integer.parseInt(line[1]));
                        ((Priority_intersection)Main.intersection).setCarsInIntersection(0);

                        /* Se initializeaza coada ce va stoca masinile cu prioritate mica. */
                        Main.blockingQueue = new ArrayBlockingQueue<>(((Priority_intersection)Main.intersection).getNoOfCarsWithLowPriority());
                    }
                };
            case "crosswalk":
                return new ReaderHandler() {
                    @Override
                    public void handle(final String handlerType, final BufferedReader br) throws IOException {
                        String[] line = br.readLine().split(" ");

                        Main.intersection = IntersectionFactory.getIntersection("crosswalk");

                        String message = "";
                        ((Crosswalk)Main.intersection).setMessage(message);

                        /*
                        Se va construi instanta pietonilor cu ajutorul datelor
                        citite suplimentar din fisier .
                        */
                        int executeTime = Integer.parseInt(line[0]);
                        int maxNoOfPedestrians = Integer.parseInt(line[1]);

                        Main.pedestrians = new Pedestrians(executeTime, maxNoOfPedestrians);
                    }
                };
            case "simple_maintenance":
                return new ReaderHandler() {
                    @Override
                    public void handle(final String handlerType, final BufferedReader br) throws IOException {
                        String[] line = br.readLine().split(" ");
                        Main.intersection = IntersectionFactory.getIntersection("simple_maintenance");

                        /* Se vor seta atributele clasei cu datele citite suplimantar din fisier. */
                        int noOfCarPassingAtOnce = Integer.parseInt(line[0]);
                        ((Simple_maintenance)Main.intersection).setNoOfCarPassingAtOnce(noOfCarPassingAtOnce);
                        ((Simple_maintenance)Main.intersection).setCarsCurrentNumber(new AtomicInteger(0));

                        /* Se vor initializa cele doua cozi specifice celor doua sensuri de mers. */
                        int noOfSenses = 2;
                        for (int i = 0; i < noOfSenses; i++) {
                            Main.senses.add(new ArrayBlockingQueue(Main.carsNo));
                        }
                    }
                };
            case "complex_maintenance":
                return new ReaderHandler() {
                    @Override
                    public void handle(final String handlerType, final BufferedReader br) throws IOException {
                    }
                };
            case "railroad":
                return new ReaderHandler() {
                    @Override
                    public void handle(final String handlerType, final BufferedReader br) throws IOException {

                        /*
                         Se initializeaza bariera si coada ce va stoca ID-urile masinilor ce vor
                         astepta sa treaca trenul.
                        */
                        Main.barrier = new CyclicBarrier(Main.carsNo);
                        Main.carQueue = new ArrayBlockingQueue<>(Main.carsNo);
                    }
                };
            default:
                return null;
        }
    }

}
