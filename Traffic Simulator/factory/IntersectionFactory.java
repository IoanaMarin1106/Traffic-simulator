package com.apd.tema2.factory;

import com.apd.tema2.entities.Intersection;
import com.apd.tema2.intersections.*;

import java.util.HashMap;
import java.util.Map;

/**
 * Prototype Factory: va puteti crea cate o instanta din fiecare tip de implementare de Intersection.
 */
public class IntersectionFactory {
    private static Map<String, Intersection> cache = new HashMap<>();

    static {
            cache.put("simple_semaphore", new Simple_semaphore() {
        });
            cache.put("simple_n_roundabout", new Simple_n_roundabout() {
        });
            cache.put("simple_strict_1_car_roundabout", new Simple_strict_1_car_roundabout() {
        });
            cache.put("simple_strict_x_car_roundabout", new Simple_strict_x_car_roundabout() {
        });
            cache.put("simple_max_x_car_roundabout", new Simple_max_x_car_roundabout() {
        });
            cache.put("priority_intersection", new Priority_intersection() {
        });
            cache.put("crosswalk", new Crosswalk() {
        });
            cache.put("simple_maintenance", new Simple_maintenance() {
        });
            cache.put("railroad", new Railroad() {
        });
    }

    public static Intersection getIntersection(String handlerType) {
        return cache.get(handlerType);
    }

}
