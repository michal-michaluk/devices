package devices.configuration.device;

import lombok.Builder;

@Builder
public record Violations(
        boolean operatorNotAssigned,
        boolean providerNotAssigned,
        boolean locationMissing,
        boolean showOnMapButMissingLocation,
        boolean showOnMapButNoPublicAccess
) {}
