package rpg.gui;

public class GameLoop implements Runnable {
    private final GameScreen gameScreen;
    private boolean running = false;
    private final int FPS = 60;
    private Thread thread;

    public GameLoop(GameScreen gameScreen) {
        this.gameScreen = gameScreen;
    }

    public void start() {
        if (running) return;
        running = true;
        thread = new Thread(this, "GameLoopThread");
        thread.start();
    }

    public void stop() {
        running = false;
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        double drawInterval = 1000000000.0 / FPS; // 0.01666 seconds
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;

        while (running) {
            currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / drawInterval;
            lastTime = currentTime;

            if (delta >= 1) {
                // 1. Update logic (physics, health checks, animations)
                update();
                // 2. Render graphics
                render();
                delta--;
            }
        }
    }

    private void update() {
        gameScreen.update();
    }

    private void render() {
        // We call repaint, which triggers paintComponent on the EDT
        gameScreen.repaint();
    }
}