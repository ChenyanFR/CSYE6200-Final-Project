package model;

import java.util.LinkedList;
import java.util.Queue;

public class ApplicationQueue {

    private static final Queue<AppliedApplication> queue = new LinkedList<>();

    public static void enqueue(AppliedApplication app) {
        queue.add(app);
    }

    public static AppliedApplication dequeue() {
        return queue.poll();
    }

    public static Queue<AppliedApplication> getAll() {
        return queue;
    }
    static {
        SubletListing fenwayStudio = new SubletListing("Fenway Studio", "Fenway", 1800.0, "Close to NEU", "short");

        ApplicationQueue.enqueue(new AppliedApplication("Emily Kim", "emily@neu.edu", fenwayStudio));
        ApplicationQueue.enqueue(new AppliedApplication("Brian Park", "brian@bu.edu", fenwayStudio));
    }
}
