package boi;

public class Debug {

    public static void debug_lambda(Runnable run) {
        try {
            run.run();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
