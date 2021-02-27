package com.apd.tema2.factory;

import com.apd.tema2.Main;
import com.apd.tema2.entities.*;
import java.util.concurrent.BrokenBarrierException;

import static java.lang.Thread.sleep;

/**
 * Clasa Factory ce returneaza implementari ale InterfaceHandler sub forma unor
 * clase anonime.
 */
public class IntersectionHandlerFactory {

    public static IntersectionHandler getHandler(String handlerType) {
        // simple semaphore intersection
        // max random N cars roundabout (s time to exit each of them)
        // roundabout with exactly one car from each lane simultaneously
        // roundabout with exactly X cars from each lane simultaneously
        // roundabout with at most X cars from each lane simultaneously
        // entering a road without any priority
        // crosswalk activated on at least a number of people (s time to finish all of
        // them)
        // road in maintenance - 2 ways 1 lane each, X cars at a time
        // road in maintenance - 1 way, M out of N lanes are blocked, X cars at a time
        // railroad blockage for s seconds for all the cars
        // unmarked intersection
        // cars racing
        switch (handlerType) {
            case "simple_semaphore":
                return new IntersectionHandler() {
                    @Override
                    public void handle(Car car) throws InterruptedException, BrokenBarrierException {
                        Main.intersection = IntersectionFactory.getIntersection("simple_semaphore");
                        Main.intersection.resolveIntersection(car);
                    }
                };
            case "simple_n_roundabout":
                return new IntersectionHandler() {
                    @Override
                    public void handle(Car car) throws InterruptedException, BrokenBarrierException {
                        Main.intersection = IntersectionFactory.getIntersection("simple_n_roundabout");
                        Main.intersection.resolveIntersection(car);
                    }
                };
            case "simple_strict_1_car_roundabout":
                return new IntersectionHandler() {
                    @Override
                    public void handle(Car car) throws BrokenBarrierException, InterruptedException {
                        Main.intersection = IntersectionFactory.getIntersection("simple_strict_1_car_roundabout");
                        Main.intersection.resolveIntersection(car);
                    }
                };
            case "simple_strict_x_car_roundabout":
                return new IntersectionHandler() {
                    @Override
                    public void handle(Car car) throws BrokenBarrierException, InterruptedException {
                        Main.intersection = IntersectionFactory.getIntersection("simple_strict_x_car_roundabout");
                        Main.intersection.resolveIntersection(car);
                    }
                };
            case "simple_max_x_car_roundabout":
                return new IntersectionHandler() {
                    @Override
                    public void handle(Car car) throws InterruptedException, BrokenBarrierException {
                        // Get your Intersection instance
                        Main.intersection = IntersectionFactory.getIntersection("simple_max_x_car_roundabout");

                        try {
                            sleep(car.getWaitingTime());
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        } // NU MODIFICATI

                        Main.intersection.resolveIntersection(car);
                    }
                };
            case "priority_intersection":
                return new IntersectionHandler() {
                    @Override
                    public void handle(Car car) throws BrokenBarrierException, InterruptedException {
                        // Get your Intersection instance
                        Main.intersection = IntersectionFactory.getIntersection("priority_intersection");

                        try {
                            sleep(car.getWaitingTime());
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        } // NU MODIFICATI

                        // Continuati de aici
                        Main.intersection.resolveIntersection(car);
                    }
                };
            case "crosswalk":
                return new IntersectionHandler() {
                    @Override
                    public void handle(Car car) throws BrokenBarrierException, InterruptedException {
                        Main.intersection = IntersectionFactory.getIntersection("crosswalk");
                        Main.intersection.resolveIntersection(car);
                    }
                };
            case "simple_maintenance":
                return new IntersectionHandler() {
                    @Override
                    public void handle(Car car) throws BrokenBarrierException, InterruptedException {
                        Main.intersection = IntersectionFactory.getIntersection("simple_maintenance");
                        Main.intersection.resolveIntersection(car);
                    }
                };
            case "complex_maintenance":
                return new IntersectionHandler() {
                    @Override
                    public void handle(Car car) {

                    }
                };
            case "railroad":
                return new IntersectionHandler() {
                    @Override
                    public void handle(Car car) throws BrokenBarrierException, InterruptedException {
                        Main.intersection = IntersectionFactory.getIntersection("railroad");
                        Main.intersection.resolveIntersection(car);
                    }
                };
            default:
                return null;
        }
    }
}
