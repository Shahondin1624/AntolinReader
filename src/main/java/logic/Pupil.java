package logic;


import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

import java.util.Objects;

public class Pupil {

    private final SimpleStringProperty forename = new SimpleStringProperty();
    private final SimpleStringProperty name = new SimpleStringProperty();
    private final SimpleIntegerProperty points = new SimpleIntegerProperty();
    private final SimpleIntegerProperty newPoints = new SimpleIntegerProperty();
    private final SimpleIntegerProperty difference = new SimpleIntegerProperty();

    public Pupil(String forename, String name) {
        this.forename.set(forename);
        this.name.set(name);
        this.points.set(0);
        this.newPoints.set(0);
        this.difference.set(0);
    }

    public String getForename() {
        return forename.get();
    }

    public SimpleStringProperty forenameProperty() {
        return forename;
    }

    public void setForename(String forename) {
        this.forename.set(forename);
    }

    public String getName() {
        return name.get();
    }

    public SimpleStringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public int getPoints() {
        return points.get();
    }

    public SimpleIntegerProperty pointsProperty() {
        return points;
    }

    public void setPoints(int points) {
        this.points.set(points);
    }

    public int getNewPoints() {
        return newPoints.get();
    }

    public SimpleIntegerProperty newPointsProperty() {
        return newPoints;
    }

    public void setNewPoints(int newPoints) {
        this.newPoints.set(newPoints);
    }

    public int getDifference() {
        return difference.get();
    }

    public SimpleIntegerProperty differenceProperty() {
        return difference;
    }

    public void setDifference(int difference) {
        this.difference.set(difference);
    }

    @Override
    public int hashCode() {
        return Objects.hash(forename.get(), name.get());
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Pupil) {
            return getForename().equals(((Pupil) obj).getForename()) && getName().equals(((Pupil) obj).getName());
        }
        return false;
    }

    public boolean isSamePupil(Pupil probablyPreviousPupil) {
        if (this.equals(probablyPreviousPupil)) {
            int tmp = this.getPoints();
            this.setPoints(probablyPreviousPupil.getPoints());
            this.setNewPoints(tmp);
            this.setDifference(getNewPoints() - getPoints());
            return true;
        } else return false;
    }


}
