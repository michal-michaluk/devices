package devices.configuration.sat;

import devices.configuration.device.Ownership;
import devices.configuration.device.WorkOrder;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SATSectionsToPickDecisionTest {

    @Test
    void testSelectSectionsFor() {
        // given two selectors
        Selectors selectors1 = Selectors.builder()
                .operators(List.of("charging.al"))
                .providers(List.of())
                .customerSegment(List.of("B2B"))
                .installationProperties(List.of("STAND_ALONE", "GSM_CONNECTION"))
                .build();

        Selectors selectors2 = Selectors.builder()
                .operators(List.of())
                .providers(List.of())
                .customerSegment(List.of("B2C"))
                .installationProperties(List.of("WALL_MONT"))
                .build();

        // creating two sections with different selectors
        SatSection section1 = new SatSection("sec1", "1", Language.EN, 1.0, selectors1, List.of());
        SatSection section2 = new SatSection("sec2", "1", Language.EN, 2.0, selectors2, List.of());

        List<SatSection> allSections = List.of(section1, section2);

        SATSectionsToPickDecision decision = new SATSectionsToPickDecision(allSections);

        WorkOrder workOrder = new WorkOrder("order-1", new Ownership("smooth-operator", "pr2"),
                            "B2B", List.of("STAND_ALONE", "GSM_CONNECTION"));

        // when filtering only the appropriate SAT sections
        List<SatSection> selectedSections = decision.selectSectionsFor(workOrder);

        // then verifying
        assertEquals(1, selectedSections.size());
        assertEquals("sec1", selectedSections.getFirst().getSectionId());
    }

    @Test
    void testSelectSectionsForEmptySelectors() {
        // given selector with customer segment and installation properties empty
        Selectors selectors = Selectors.builder()
                .operators(List.of())
                .providers(List.of())
                .customerSegment(List.of())
                .installationProperties(List.of())
                .build();

        // creating new section which should be applied to all work orders
        SatSection section = new SatSection("sec1", "1", Language.EN, 1.0, selectors, List.of());

        List<SatSection> allSections = List.of(section);

        SATSectionsToPickDecision decision = new SATSectionsToPickDecision(allSections);

        WorkOrder workOrder = new WorkOrder("order-1", new Ownership("smooth-operator", "pr2"),
                            "B2B", List.of("STAND_ALONE", "GSM_CONNECTION"));

        // when we get the decision
        List<SatSection> selectedSections = decision.selectSectionsFor(workOrder);

        // then - verifying the section should be applied
        assertEquals(1, selectedSections.size());
        assertEquals("sec1", selectedSections.getFirst().getSectionId());
    }

    @Test
    void testSelectSectionsForMultipleMatches() {
        // Given
        Selectors selectors1 = Selectors.builder()
                .operators(List.of("charging.al"))
                .providers(List.of())
                .customerSegment(List.of("B2B"))
                .installationProperties(List.of("STAND_ALONE", "GSM_CONNECTION"))
                .build();

        Selectors selectors2 = Selectors.builder()
                .operators(List.of())
                .providers(List.of())
                .customerSegment(List.of("B2B"))
                .installationProperties(List.of("STAND_ALONE"))
                .build();

        SatSection section1 = new SatSection("sec1", "1", Language.EN, 1.0, selectors1, List.of());
        SatSection section2 = new SatSection("sec2", "1", Language.EN, 2.0, selectors2, List.of());

        List<SatSection> allSections = List.of(section1, section2);

        SATSectionsToPickDecision decision = new SATSectionsToPickDecision(allSections);

        WorkOrder workOrder = new WorkOrder("order-1", new Ownership("smooth-operator", "pr2"),
                "B2B", List.of("STAND_ALONE", "GSM_CONNECTION"));

        // When
        List<SatSection> selectedSections = decision.selectSectionsFor(workOrder);

        // Then
        assertEquals(2, selectedSections.size());
        assertEquals("sec1", selectedSections.get(0).getSectionId());
        assertEquals("sec2", selectedSections.get(1).getSectionId());
    }

    @Test
    void testSelectSectionsForNoMatches() {
        // Given
        Selectors selectors = Selectors.builder()
                .operators(List.of("charging.al"))
                .providers(List.of())
                .customerSegment(List.of("B2C"))
                .installationProperties(List.of("WALL_MONT"))
                .build();

        SatSection section = new SatSection("sec1", "1", Language.EN, 1.0, selectors, List.of());

        List<SatSection> allSections = List.of(section);

        SATSectionsToPickDecision decision = new SATSectionsToPickDecision(allSections);

        WorkOrder workOrder = new WorkOrder("order-1", new Ownership("smooth-operator", "pr2"),
                "B2B", List.of("STAND_ALONE", "GSM_CONNECTION"));

        // When
        List<SatSection> selectedSections = decision.selectSectionsFor(workOrder);

        // Then
        assertEquals(0, selectedSections.size());
    }
}