package devices.configuration.sat;

import lombok.Builder;

import java.util.List;

@Builder
public record Selectors(
   List<String> operators,
   List<String> providers,
   List<String> customerSegment,
   List<String> installationProperties
) {}
