package ch.epfl.rigel.gui;

import javafx.animation.AnimationTimer;
import javafx.beans.property.*;

import java.time.ZonedDateTime;

/**
 * [description text]
 *
 * @author Michael Freeman (313215)
 * @author Maxime Zammit (310251) //TODO Check cause in average it is 50/2
 */
public final class TimeAnimator extends AnimationTimer {
    private final DateTimeBean dateTimeBean;
    private final BooleanProperty running = new SimpleBooleanProperty(false);
    private final ObjectProperty<TimeAccelerator> timeAccelerator = new SimpleObjectProperty<>(null);
    private long timeStamp = 0;
    private boolean firstTime = true;

    public TimeAnimator(DateTimeBean dateTimeBean){
        this.dateTimeBean = dateTimeBean;
    }

    @Override
    public void handle(long l) { //TODO boolean is better
        if (firstTime){
            firstTime = false;
            timeStamp = l;
        }else{
            long deltaT = l- timeStamp;
            timeStamp = l;
            ZonedDateTime zdt = getTimeAccelerator().adjust(dateTimeBean.getZonedDateTime(),deltaT);
            dateTimeBean.setZonedDateTime(zdt);
        }
    }

    @Override
    public void start() {
        super.start();
        running.set(true);
        firstTime = true;
    }

    @Override
    public void stop() {
        super.stop();
        running.set(false);
    }

    public ReadOnlyBooleanProperty runningProperty(){
        return running;
    }

    public boolean isRunning(){
        return running.get();
    }

    public ObjectProperty<TimeAccelerator> timeAcceleratorProperty(){
        return timeAccelerator;
    }

    public TimeAccelerator getTimeAccelerator(){
        return timeAccelerator.get();
    }

    public void setTimeAccelerator(TimeAccelerator timeAccelerator){
        this.timeAccelerator.set(timeAccelerator);
    }


}
