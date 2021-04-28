import importing.Importer;
import logic.Klasse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;

public class TestImport {
    private final String path = "";

    @Test
    public void importFile(){
        Klasse klasse = Importer.getFile(new File(path));
        Assertions.assertFalse(klasse.getPupils().isEmpty());
    }

    @Test
    public void sameKlasse() {
        Klasse klasse1 = Importer.getFile(new File(path));
        Klasse klasse2 = Importer.getFile(new File(path));
        Assertions.assertTrue(klasse1.sameKlasse(klasse2));
    }
}
