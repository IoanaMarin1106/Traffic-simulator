package com.apd.tema2.entities;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.concurrent.BrokenBarrierException;

/**
 * Utilizata pentru a uniformiza tipul de date ce ajuta la definirea unei intersectii / a unui task.
 * Implementarile acesteia vor contine variabile specifice task-ului, respectiv mecanisme de sincronizare.
 */
public interface Intersection {
    public void resolveIntersection(Car car) throws InterruptedException, BrokenBarrierException;
}
