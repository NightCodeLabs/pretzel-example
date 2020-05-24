package com.github.nightcodelabs.pretzelExample;

import java.util.concurrent.TimeUnit;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        while (true) {
            TimeUnit.SECONDS.sleep(1);
            System.out.println("App is Running ...");
        }
    }
}
