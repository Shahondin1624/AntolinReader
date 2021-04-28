package logic;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Util {
    public static Timestamp now() {
        return Timestamp.valueOf(LocalDateTime.now());
    }

    public static Klasse older(Klasse klasse1, Klasse klasse2) {
        if (klasse1.sameKlasse(klasse2)) {
            List<Pupil> pupils1 = new ArrayList<>(klasse1.getPupils());
            List<Pupil> pupils2 = new ArrayList<>(klasse2.getPupils());
            for (int i = 0; i < pupils1.size(); i++) {
                Pupil pupil1 = pupils1.get(i);
                Pupil pupil2 = pupils2.get(i);
                if (pupil1.isOlderThan(pupil2) == -1) {
                    return klasse1;
                } else if (pupil1.isOlderThan(pupil2) == 1) {
                    return klasse2;
                }
            }
        }
        return klasse1;
    }

    public static Klasse newer(Klasse klasse1, Klasse klasse2) {
        if (older(klasse1, klasse2) == klasse1) {
            return klasse2;
        } else return klasse1;
    }
}
