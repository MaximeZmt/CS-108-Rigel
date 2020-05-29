package ch.epfl.rigel.gui;

import java.time.Duration;

/**
 * Represents a named TimeAccelerator, i.e. an accelerator paired with it's string representation
 *
 * @author Michael Freeman (313215)
 * @author Maxime Zammit (310251)
 */
public enum NamedTimeAccelerator {
    TIMES_1("1×", TimeAccelerator.continuous(1)),
    TIMES_30("30×", TimeAccelerator.continuous(30)),
    TIMES_300("300x", TimeAccelerator.continuous(300)),
    TIMES_3000("3000×", TimeAccelerator.continuous(3000)),
    DAY("jour", TimeAccelerator.discrete(60, Duration.ofHours(24))),
    SIDERAL_DAY("jour sidéral", TimeAccelerator.discrete(60, Duration.parse("PT23H56M4S")));

    private final String name;
    private final TimeAccelerator accelerator;

    /**
     * Constructor
     *
     * @param name name
     * @param accelerator TimeAccelerator
     */
    NamedTimeAccelerator(String name, TimeAccelerator accelerator){
        this.name = name;
        this.accelerator = accelerator;
    }

    /**
     * Getter for the name
     *
     * @return name
     */
    public String getName(){
        return name;
    }

    /**
     * Getter for the accelerator
     *
     * @return accelerator
     */
    public TimeAccelerator getAccelerator(){
        return accelerator;
    }

    /**
     * @see Object#toString()
     *
     * @return name
     */
    @Override
    public String toString() {
        return name;
    }
}
