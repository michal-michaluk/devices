package devices.configuration.sat;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Getter
@AllArgsConstructor
public class SatSection {

    @JsonProperty("id")
    private String sectionId;
    private String version;
    private Language language;
    private double order;
    Selectors selectors;
    List<Question> questions;

    public void addQuestion(Question question) {

        if (question.id() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        boolean found = false;
        for (Question value : questions) {
            if (value.id().equals(question.id())) {
                found = true;
                break;
            }
        }
        if (found) {
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        }
        this.questions.add(question);
    }

    public void updateQuestion(Question newQuestion) {

        boolean found = false;
        for (int i = 0; i < questions.size(); i++) {
            if (questions.get(i).id().equals(newQuestion.id())) {
                questions.set(i, newQuestion);
                found = true;
                break;
            }
        }
        if (!found) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    public void removeQuestion(String questionId) {
        questions.removeIf(q -> q.id().equals(questionId));
    }
}
