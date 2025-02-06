package devices.configuration.device;

import devices.configuration.JsonAssert;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ViolationsTest {

    @Test
    void testViolationsBuilder() {

        // given - prepare state of system before test
        Violations violations = Violations.builder()
                .operatorNotAssigned(true)
                .providerNotAssigned(false)
                .locationMissing(true)
                .showOnMapButMissingLocation(false)
                .showOnMapButNoPublicAccess(true)
                .build();

        // when
        // then - verify state of the system after test
        assertTrue(violations.operatorNotAssigned());
        assertFalse(violations.providerNotAssigned());
        assertTrue(violations.locationMissing());
        assertFalse(violations.showOnMapButMissingLocation());
        assertTrue(violations.showOnMapButNoPublicAccess());
    }
}
