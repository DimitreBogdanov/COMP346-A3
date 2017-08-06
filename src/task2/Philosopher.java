package task2;

import common.BaseThread;

import static java.lang.System.out;

/**
 * Class Philosopher.
 * Outlines main subrutines of our virtual philosopher.
 *
 * @author Serguei A. Mokhov, mokhov@cs.concordia.ca
 */
public class Philosopher extends BaseThread {
    /**
     * Max time an action can take (in milliseconds)
     */
    public static final long TIME_TO_WASTE = 1000;

    /**
     * The act of eating.
     * - Print the fact that a given phil (their TID) has started eating.
     * - yield
     * - Then sleep() for a random interval.
     * - yield
     * - The print that they are done eating.
     */
    public void eat() {
        try {
            out.println(String.format("Philosopher %d has started eating", getTID()));
            yield();
            sleep((long) (Math.random() * TIME_TO_WASTE));
            yield();
            out.println(String.format("Philosopher %d has finished eating", getTID()));
        } catch (InterruptedException e) {
            System.err.println("Philosopher.eat():");
            DiningPhilosophers.reportException(e);
            System.exit(1);
        }
    }

    /**
     * The act of thinking.
     * - Print the fact that a given phil (their TID) has started thinking.
     * - yield
     * - Then sleep() for a random interval.
     * - yield
     * - The print that they are done thinking.
     */
    public void think() {
        try {
            out.println(String.format("Philosopher %d has started thinking", getTID()));
            yield();
            sleep((long) (Math.random() * TIME_TO_WASTE));
            yield();
            out.println(String.format("Philosopher %d has finished thinking", getTID()));
        } catch (InterruptedException e) {
            System.err.println("Philosopher.eat():");
            DiningPhilosophers.reportException(e);
            System.exit(1);
        }
    }

    /**
     * The act of talking.
     * - Print the fact that a given phil (their TID) has started talking.
     * - yield
     * - Say something brilliant at random
     * - yield
     * - The print that they are done talking.
     */
    public void talk() {
        out.println(String.format("Philosopher %d has started talking", getTID()));
        yield();
        saySomething();
        yield();
        out.println(String.format("Philosopher %d has finished talking", getTID()));
    }

    /**
     * No, this is not the act of running, just the overridden Thread.run()
     */
    public void run() {
        for (int i = 0; i < task3.DiningPhilosophers.DINING_STEPS; i++) {
            try {
                task3.DiningPhilosophers.soMonitor.pickUp(getTID());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            eat();

            task3.DiningPhilosophers.soMonitor.putDown(getTID());

            think();

            try {
                task3.DiningPhilosophers.soMonitor.requestTalk(getTID());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            talk();
            task3.DiningPhilosophers.soMonitor.endTalk(getTID());

            yield();
        }
    } // run()

    /**
     * Prints out a phrase from the array of phrases at random.
     * Feel free to add your own phrases.
     */
    public void saySomething() {
        String[] astrPhrases =
                {
                        "Eh, it's not easy to be a philosopher: eat, think, talk, eat...",
                        "You know, true is false and false is true if you think of it",
                        "2 + 2 = 5 for extremely large values of 2...",
                        "If thee cannot speak, thee must be silent",
                        "My number is " + getTID() + ""
                };

        System.out.println
                (
                        "Philosopher " + getTID() + " says: " +
                                astrPhrases[(int) (Math.random() * astrPhrases.length)]
                );
    }
}

// EOF
