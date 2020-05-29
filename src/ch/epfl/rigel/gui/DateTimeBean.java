package ch.epfl.rigel.gui;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

/**
 * JavaFx bean containing the zone, date and time in wich the observer is
 *
 * @author Michael Freeman (313215)
 * @author Maxime Zammit (310251)
 */
public final class DateTimeBean {

    private final ObjectProperty<LocalDate> date = new SimpleObjectProperty<>(null);
    private final ObjectProperty<LocalTime> time = new SimpleObjectProperty<>(null);
    private final ObjectProperty<ZoneId> zone = new SimpleObjectProperty<>(null);

    /**
     * Getter for the date property
     *
     * @return date property
     */
    public ObjectProperty<LocalDate> dateProperty(){
        return date;
    }

    /**
     * Getter for the date
     *
     * @return date
     */
    public LocalDate getDate(){
        return date.get();
    }

    /**
     * Setter for the date
     *
     * @param date given date
     */
    public void setDate(LocalDate date){
        this.date.set(date);
    }

    /**
     * Getter for the time property
     *
     * @return time property
     */
    public ObjectProperty<LocalTime> timeProperty(){
        return time;
    }

    /**
     * Getter for the time
     *
     * @return time
     */
    public LocalTime getTime(){
        return time.get();
    }

    /**
     * Setter for the time
     *
     * @param time given time
     */
    public void setTime(LocalTime time){
        this.time.set(time);
    }

    /**
     * Getter for the zone property
     *
     * @return zone property
     */
    public ObjectProperty<ZoneId> zoneProperty(){
        return zone;
    }

    /**
     * Getter for the zone
     *
     * @return zone
     */
    public ZoneId getZone(){
        return zone.get();
    }

    /**
     * Setter for the zone
     *
     * @param zoneId given zone
     */
    public void setZone(ZoneId zoneId){
        this.zone.set(zoneId);
    }

    /**
     * Gets a ZonedDateTime constituted of the zone, date and time of the bean
     *
     * @return a ZonedDateTime
     */
    public ZonedDateTime getZonedDateTime(){
        return ZonedDateTime.of(getDate(), getTime(), getZone());
    }

    /**
     * Sets the zone, date and time of the bean with the given ZonedDateTime
     *
     * @param zdt given ZonedDateTime
     */
    public void setZonedDateTime(ZonedDateTime zdt){
        setDate(zdt.toLocalDate());
        setTime(zdt.toLocalTime());
        setZone(zdt.getZone());
    }
}
