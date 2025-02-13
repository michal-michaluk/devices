package devices.configuration.sat;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record Question(
   @NotNull String id,
   QuestionType type,
   @JsonProperty("question")
   String questionDesc,
   boolean required,
   @JsonInclude(JsonInclude.Include.NON_NULL)
   Condition condition
) {}
