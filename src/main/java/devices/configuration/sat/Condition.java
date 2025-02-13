package devices.configuration.sat;

import lombok.Builder;

@Builder
public record Condition(
        ConditionType type,
        boolean ifAnswerIs
) {}
