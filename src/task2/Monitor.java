package task2;

/**
 * Class Monitor
 * To synchronize dining philosophers.
 *
 * @author Serguei A. Mokhov, mokhov@cs.concordia.ca
 */
public class Monitor {
    /*
     * ------------
	 * Data members
	 * ------------
	 */

    private static final int THINKING = 0;
    private static final int TALKING = 1;
    private static final int WAITING = 2;
    private static final int EATING = 3;

    private int philosopherCount;

    private int[] philosophers;

    private int talkingPhilosopher = -1;

    /**
     * Constructor
     */
    public Monitor(int piNumberOfPhilosophers) {
        // TODO: set appropriate number of chopsticks based on the # of philosophers
        this.philosopherCount = piNumberOfPhilosophers;
        philosophers = new int[philosopherCount];
        for (int i = 0; i < philosopherCount; i++) {
            philosophers[i] = THINKING;
        }
    }

	/*
     * -------------------------------
	 * User-defined monitor procedures
	 * -------------------------------
	 */

    /**
     * Grants request (returns) to eat when both chopsticks/forks are available.
     * Else forces the philosopher to wait()
     */
    public synchronized void pickUp(final int piTID) throws InterruptedException {
        int current = piTID - 1;
        int previous = Math.abs((current - 1)) % philosopherCount;
        int next = (current + 1) % philosopherCount;
        while (philosophers[previous] == EATING || philosophers[next] == EATING) {
            philosophers[current] = WAITING;
            wait();
        }
        philosophers[current] = EATING;
    }

    /**
     * When a given philosopher's done eating, they put the chopstiks/forks down
     * and let others know they are available.
     */
    public synchronized void putDown(final int piTID) {
        int current = piTID - 1;
        philosophers[current] = THINKING;
        notifyAll();
    }

    /**
     * Only one philopher at a time is allowed to philosophy
     * (while she is not eating).
     */
    public synchronized void requestTalk(final int piTID) throws InterruptedException {
        while (talkingPhilosopher >= 0) {
            wait();
        }
        talkingPhilosopher = piTID;
        philosophers[piTID - 1] = TALKING;
    }

    /**
     * When one philosopher is done talking stuff, others
     * can feel free to start talking.
     */
    public synchronized void endTalk(final int piTID) {
        philosophers[piTID - 1] = THINKING;
        talkingPhilosopher = -1;
        notifyAll();
    }
}

// EOF
