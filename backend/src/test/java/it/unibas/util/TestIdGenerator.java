package it.unibas.util;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;
import java.util.List;

public class TestIdGenerator {

    private static final Logger logger = LoggerFactory.getLogger(TestIdGenerator.class);

    @Test
    void testIdGenerator() {
        List<String> idsList = new ArrayList<>();
        for (int i = 0; i < 1000; i++) {
            String id = IdGenerator.generate(15);
            assertFalse(idsList.contains(id), "ID duplicato: " + id);
            idsList.add(id);
        }
    }

}
