package devices.configuration.device;

import jakarta.validation.constraints.NotNull;

import java.util.List;

public record WorkOrder(
        @NotNull String orderId,
        @NotNull Ownership ownership,

        String customerSegment,
        List<String> installationProperties
) {}
