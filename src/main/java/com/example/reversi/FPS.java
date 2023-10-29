package com.example.reversi;

public class FPS {
    public final int FPS = 60;
    private double draw_interval;
    private double next_draw_time;
    private long timer;
    private int fps_counter;

    public static FPS instance;

    private FPS() {
        draw_interval = 1000000000 / FPS;
        timer = 0;
        fps_counter = 0;
    }

    public static FPS getInstance() {
        if(instance == null)
            instance = new FPS();
        return instance;
    }

    void init() {
        next_draw_time = System.nanoTime() + draw_interval;
    }

    void tick() {
        double remaining_time = next_draw_time - System.nanoTime();
        timer += remaining_time;
        remaining_time = remaining_time / 1000000;
        if(remaining_time < 0)
            remaining_time = 0;

        try {
            Thread.sleep((long)remaining_time);
        } catch (InterruptedException ignored) {}
        next_draw_time += draw_interval;
        fps_counter++;
        if(timer >= 1000000000) {
            System.out.println(fps_counter);
            fps_counter = 0;
            timer = 0;
        }
    }
}
