package logic;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.*;

public class Klasse implements Serializable {

    private Set<Pupil> pupils = new TreeSet<>();
    private Timestamp modified;
    private String fileName;

    public Klasse(Collection<Pupil> pupils){
        this.pupils.addAll(pupils);
        modify();
    }

    public Klasse(){
        modify();
    }

    public void addPupil(Pupil pupil){
        pupils.add(pupil);
        modify();
    }

    public String getFileName(){
        return fileName;
    }

    public void setFileName(String fileName){
        this.fileName = fileName;
    }

    protected void modify(){
        modified = Util.now();
    }

    public void addPupils(Collection<Pupil> pupils){
        this.pupils.addAll(pupils);
        modify();
    }

    public Set<Pupil> getPupils(){
        return pupils;
    }

    public boolean sameKlasse(Klasse klasse){
        if (klasse != null && klasse.getPupils() != null && pupils.size() == klasse.getPupils().size()){
            for (Pupil pupil : pupils){
                if (!klasse.getPupils().contains(pupil)){
                    return false;
                }
            }
        } else return false;
        return true;
    }
}
