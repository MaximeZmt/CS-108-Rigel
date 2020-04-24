package ch.epfl.rigel.gui;

import javafx.animation.AnimationTimer;

/**
 * [description text]
 *
 * @author Michael Freeman (313215)
 * @author Maxime Zammit (310251)
 */
public final class TimeAnimator extends AnimationTimer {
    DateTimeBean dateTimeBean;

    TimeAnimator(DateTimeBean dateTimeBean){
        this.dateTimeBean = dateTimeBean;
    }

    @Override
    public void handle(long l) {

    }
}
