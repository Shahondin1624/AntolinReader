package logic;


import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Objects;

public class Pupil implements Comparable<Pupil>, Serializable {

    private final SimpleStringProperty forename = new SimpleStringProperty();
    private final SimpleStringProperty name = new SimpleStringProperty();
    private final SimpleIntegerProperty points = new SimpleIntegerProperty();
    private final SimpleIntegerProperty newPoints = new SimpleIntegerProperty();
    private final SimpleIntegerProperty difference = new SimpleIntegerProperty();
    private final SimpleIntegerProperty books = new SimpleIntegerProperty();
    private final SimpleStringProperty percentage = new SimpleStringProperty();
    private final SimpleStringProperty userName = new SimpleStringProperty();
    private Timestamp modified;

    public Pupil(String forename, String name) {
        this.forename.set(forename);
        this.name.set(name);
        this.points.set(0);
        this.newPoints.set(0);
        this.difference.set(0);
        this.books.set(0);
        modify();
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

    public int getBooks() {
        return books.get();
    }

    public SimpleIntegerProperty booksProperty() {
        return books;
    }

    public void setBooks(int books) {
        this.books.set(books);
    }

    public String getPercentage() {
        return percentage.get();
    }

    public SimpleStringProperty percentageProperty() {
        return percentage;
    }

    public void setPercentage(String percentage) {
        this.percentage.set(percentage);
    }

    public String getUserName() {
        return userName.get();
    }

    public SimpleStringProperty userNameProperty() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName.set(userName);
    }

    public Timestamp getModified() {
        return modified;
    }

    @Override
    public int hashCode() {
        return Objects.hash(forename.get(), name.get());
    }

    protected void modify() {
        modified = Util.now();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Pupil) {
            Pupil other = (Pupil) obj;
            if (this.getName().equalsIgnoreCase(other.getName())) {
                if (this.getForename().equalsIgnoreCase(other.getForename())) {
                    return true;
                }
            }
        }
        return false;
    }

    @Deprecated
    public boolean isSamePupil(Pupil probablyPreviousPupil) {
        if (this.equals(probablyPreviousPupil)) {
            int tmp = this.getPoints();
            this.setPoints(probablyPreviousPupil.getPoints());
            this.setNewPoints(tmp);
            this.setDifference(getNewPoints() - getPoints());
            return true;
        } else return false;
    }

    public static Pupil merge(Pupil pupil1, Pupil pupil2) {
        if (pupil1.equals(pupil2)) {
            if (pupil1.getPoints() > pupil2.getPoints()) {
                Pupil pupil = new Pupil(pupil1.getForename(), pupil1.getName());
                pupil.setPoints(pupil2.getPoints());
                pupil.setNewPoints(pupil1.getPoints());
                pupil.setDifference(pupil.getNewPoints() - pupil.getPoints());
                return pupil;
            } else {
                Pupil pupil = new Pupil(pupil1.getForename(), pupil1.getName());
                pupil.setPoints(pupil1.getPoints());
                pupil.setNewPoints(pupil2.getPoints());
                pupil.setDifference(pupil.getNewPoints() - pupil.getPoints());
                return pupil;
            }
        }
        throw new IllegalArgumentException("Pupils are not mergeable - Not the same");
    }

    public int isOlderThan(Pupil pupil) {
        if (this.equals(pupil)) {
            return Integer.compare(this.getPoints(), pupil.getPoints());
        } else throw new IllegalArgumentException("Not the same pupil");
    }


    @Override
    public int compareTo(Pupil o) {
        int name = this.getName().compareTo(o.getName());
        if (name == 0) {
            return this.getForename().compareTo(o.getForename());
        } else return name;
    }
}
