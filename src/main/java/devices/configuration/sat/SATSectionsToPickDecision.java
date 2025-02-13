package devices.configuration.sat;

import devices.configuration.device.WorkOrder;

import java.util.List;
import java.util.stream.Collectors;

public class SATSectionsToPickDecision {

    private final List<SatSection> satSections;

    public SATSectionsToPickDecision(List<SatSection> satSections) {
        this.satSections = satSections;
    }

    public List<SatSection> selectSectionsFor(WorkOrder workOrder) {
        return satSections.stream()
                .filter(section -> matchesSelectors(section.getSelectors(), workOrder))
                .collect(Collectors.toList());
    }

    private boolean matchesSelectors(Selectors selectors, WorkOrder workOrder) {
        // check customer segment
        boolean matchesCustomerSegment = selectors.customerSegment().isEmpty() ||
                selectors.customerSegment().contains(workOrder.customerSegment());

        // check installation properties
        boolean matchesInstallationProperties = selectors.installationProperties().isEmpty() ||
                selectors.installationProperties().stream().anyMatch(workOrder.installationProperties()::contains);

        /*boolean matchesInstallationProperties;
        if (selectors.installationProperties().isEmpty()) {
            matchesInstallationProperties = true;
        } else {
            matchesInstallationProperties = true;
            for (String installationProperty : selectors.installationProperties()) {
                if (!workOrder.installationProperties().contains(installationProperty)) {
                    matchesInstallationProperties = false;
                    break;
                }
            }
        }

        boolean matchesInstallationProperties = selectors.installationProperties().isEmpty() ||
            workOrder.installationProperties().containsAll(selectors.installationProperties());*/

        return matchesCustomerSegment && matchesInstallationProperties;
    }
}