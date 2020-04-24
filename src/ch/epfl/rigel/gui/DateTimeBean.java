package ch.epfl.rigel.gui;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

/**
 * [fillTxt]
 *
 * @author Michael Freeman (313215)
 * @author Maxime Zammit (310251)
 */
public final class DateTimeBean {

    private final ObjectProperty<LocalDate> date = new SimpleObjectProperty<>(null);
    private final ObjectProperty<LocalTime> time = new SimpleObjectProperty<>(null);
    private final ObjectProperty<ZoneId> zone = new SimpleObjectProperty<>(null);

    public ObjectProperty<LocalDate> dateProperty(){
        return date;
    }
    public LocalDate getDate(){
        return date.get();
    }
    public void setDate(LocalDate date){
        this.date.set(date);
    }

    public ObjectProperty<LocalTime> timeProperty(){
        return time;
    }
    public LocalTime getTime(){
        return time.get();
    }
    public void setTime(LocalTime time){
        this.time.set(time);
    }

    public ObjectProperty<ZoneId> zoneProperty(){
        return zone;
    }
    public ZoneId getZone(){
        return zone.get();
    }
    public void setZone(ZoneId zoneId){
        this.zone.set(zoneId);
    }

    public ZonedDateTime getZonedDateTime(){
        return ZonedDateTime.of(getDate(),getTime(),getZone());
    }

    public void setZonedDateTime(ZonedDateTime zdt){
        setDate(zdt.toLocalDate());
        setTime(zdt.toLocalTime());
        setZone(zdt.getZone());
    }

}
