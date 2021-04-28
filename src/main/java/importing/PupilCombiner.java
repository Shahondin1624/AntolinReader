package importing;

import logic.Klasse;
import logic.Pupil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class PupilCombiner {

    private static final Logger LOGGER = LoggerFactory.getLogger(PupilCombiner.class);

    public static Klasse merge(Klasse klasse1, Klasse klasse2) {
        Klasse klasse = new Klasse();
        if (klasse1.sameKlasse(klasse2)) {
            List<Pupil> pupils1 = new ArrayList<>(klasse1.getPupils());
            List<Pupil> pupils2 = new ArrayList<>(klasse2.getPupils());
            for (int i = 0; i < pupils1.size(); i++) {
                Pupil pupil = Pupil.merge(pupils1.get(i), pupils2.get(i));
                klasse.addPupil(pupil);
                LOGGER.info("Pupil {} {} has been merged", pupil.getForename(), pupil.getName());
            }
        }
        return klasse;
    }
}
