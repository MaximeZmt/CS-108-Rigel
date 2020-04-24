package ch.epfl.rigel.gui;

import java.time.Duration;

/**
 * [description text]
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
    //TODO parse working ?
    SIDERAL_DAY("jour sidéral", TimeAccelerator.discrete(60, Duration.parse("PT23H56M4S")));

    private final String name;
    private final TimeAccelerator accelerator;

    NamedTimeAccelerator(String name, TimeAccelerator accelerator){
        this.name = name;
        this.accelerator = accelerator;
    }

    public String getName(){
        return name;
    }

    public TimeAccelerator getAccelerator(){
        return accelerator;
    }

    @Override
    public String toString() {
        return name;
    }
}
