package ch.epfl.rigel.gui;

import javafx.animation.AnimationTimer;
import javafx.beans.property.*;

import java.time.ZonedDateTime;

/**
 * Represents a Time animator
 *
 * @author Michael Freeman (313215)
 * @author Maxime Zammit (310251) //TODO Check cause in average it is 50/2
 */
public final class TimeAnimator extends AnimationTimer {
    private final DateTimeBean dateTimeBean;
    private final BooleanProperty running = new SimpleBooleanProperty(false);
    private final ObjectProperty<TimeAccelerator> accelerator = new SimpleObjectProperty<>(null);
    private long timeStamp = 0;
    private boolean firstTime = true;

    /**
     * Constructor
     *
     * @param dateTimeBean date time bean
     */
    public TimeAnimator(DateTimeBean dateTimeBean){
        this.dateTimeBean = dateTimeBean;
    }

    /**
     * @see AnimationTimer#handle(long)
     * @param l time
     */
    @Override
    public void handle(long l) { //TODO boolean is better
        if (firstTime){
            firstTime = false;
            timeStamp = l; 
        }else{
            long deltaT = l- timeStamp;
            timeStamp = l;
            ZonedDateTime zdt = accelerator.get().adjust(dateTimeBean.getZonedDateTime(),deltaT);
            dateTimeBean.setZonedDateTime(zdt);
        }
    }

    /**
     * @see AnimationTimer#start()
     */
    @Override
    public void start() {
        super.start();
        running.set(true);
        firstTime = true;
    }

    /**
     * @see AnimationTimer#stop()
     */
    @Override
    public void stop() {
        super.stop();
        running.set(false);
    }

    /**
     * checks if running
     *
     * @return boolean
     */
    public boolean isRunning(){
        return running.get();
    }

    /**
     * Getter for the accelerator property
     *
     * @return accelerator property
     */
    public ObjectProperty<TimeAccelerator> acceleratorProperty(){
        return accelerator;
    }

    /**
     * Setter for for the accelerator
     *
     * @param timeAccelerator time accelerator
     */
    public void setAccelerator(TimeAccelerator timeAccelerator){
        this.accelerator.set(timeAccelerator);
    }


}
