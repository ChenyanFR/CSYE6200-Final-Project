package model;

import java.util.LinkedList;
import java.util.Queue;

public class ApplicationQueue {
	// static queue for FIFO appliedapplication management
    private static final Queue<AppliedApplication> queue = new LinkedList<>();

    //add new application
    public static void enqueue(AppliedApplication app) {
        queue.add(app);
    }
    //add new application
    public static AppliedApplication dequeue() {
        return queue.poll();
    }
    //return a list of all applications
    public static Queue<AppliedApplication> getAll() {
        return queue;
    }
    // samplers to test queue
    static {
        SubletListing fenwayStudio = new SubletListing("Fenway Studio", "Fenway", 1800.0, "Close to NEU", "short");

        ApplicationQueue.enqueue(new AppliedApplication("Emily Kim", "emily@neu.edu", fenwayStudio));
        ApplicationQueue.enqueue(new AppliedApplication("Brian Park", "brian@bu.edu", fenwayStudio));
    }
}
