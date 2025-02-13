package devices.configuration.sat;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

import static devices.configuration.JsonAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class SatSectionTest {

    @Test
    void testAddQuestionWithNullId() {
        // given
        // create new question with null id
        Question question = Question.builder()
                .id(null)
                .type(QuestionType.BOOLEAN)
                .questionDesc("Is there any object nearby installation site?")
                .required(true)
                .condition(null)
                .build();

        // create new selector
        Selectors selectors = Selectors.builder()
                .operators(new ArrayList<>())
                .providers(new ArrayList<>())
                .customerSegment(new ArrayList<>())
                .installationProperties(new ArrayList<>())
                .build();

        // creating new section to store related questions
        SatSection section = new SatSection("sec1", "1", Language.EN, 1, selectors, new ArrayList<>());

        // when addQuestion is invoked with a question that has a null id,
        // a ResponseStatusException with BAD_REQUEST should be thrown.
        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> section.addQuestion(question));

        // then - verify that the exception has the expected HTTP status code.
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatusCode());
    }


    @Test
    void testAddQuestionToSection() {
        // given - new properly created question
        Question question = Question.builder()
                .id("ca5173f8-bf02-4967-94d2-e850f8a985f4")
                .type(QuestionType.BOOLEAN)
                .questionDesc("Is there any object nearby installation site, prone to damage during installation?")
                .required(true)
                .condition(Condition.builder()
                        .type(ConditionType.SKIP_REST_OF_SECTION)
                        .ifAnswerIs(false)
                        .build())
                .build();

        // create new selector
        Selectors selectors = Selectors.builder()
                .operators(new ArrayList<>())
                .providers(new ArrayList<>())
                .customerSegment(new ArrayList<>())
                .installationProperties(new ArrayList<>())
                .build();

        // creating new section to store related questions
        SatSection section = new SatSection("sec1", "1", Language.EN, 1, selectors, new ArrayList<>());

        // when - adding the question to section
        section.addQuestion(question);

        // then - verify the question was added
        assertEquals(1, section.questions.size());
        // then verify the actual value
        assertThat(section.questions.getFirst())
                .isExactlyLike("""
                        {
                                   "id": "ca5173f8-bf02-4967-94d2-e850f8a985f4",
                                   "type": "BOOLEAN",
                                   "question": "Is there any object nearby installation site, prone to damage during installation?",
                                   "required": true,
                                   "condition": {
                                     "type": "SKIP_REST_OF_SECTION",
                                     "ifAnswerIs": false
                                   }
                                 }
                        """);
    }

    @Test
    void testAddDuplicateQuestion() {
        // given - new properly created question
        Question question1 = Question.builder()
                .id("question-1")
                .type(QuestionType.BOOLEAN)
                .questionDesc("Is there any object nearby installation site?")
                .required(true)
                .condition(null)
                .build();

        Question question2 = Question.builder()
                .id("question-1")
                .type(QuestionType.BOOLEAN)
                .questionDesc("Is there any object nearby installation site, prone to damage during installation?")
                .required(true)
                .condition(null)
                .build();

        // create new selector
        Selectors selectors = Selectors.builder()
                .operators(new ArrayList<>())
                .providers(new ArrayList<>())
                .customerSegment(new ArrayList<>())
                .installationProperties(new ArrayList<>())
                .build();

        // creating new section to store related questions
        SatSection section = new SatSection("sec1", "1", Language.EN, 1, selectors, new ArrayList<>());

        // when - adding both question with same id to section
        assertDoesNotThrow(() -> section.addQuestion(question1));
        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> section.addQuestion(question2));

        // then - verify that the exception has the expected HTTP status code.
        assertEquals(HttpStatus.CONFLICT, exception.getStatusCode());
    }

    @Test
    void testCreateFullySection() {
        // given - new properly created question
        Question question1 = Question.builder()
                .id("ca5173f8-bf02-4967-94d2-e850f8a985f4")
                .type(QuestionType.BOOLEAN)
                .questionDesc("Is there any object nearby installation site, prone to damage during installation?")
                .required(true)
                .condition(Condition.builder()
                        .type(ConditionType.SKIP_REST_OF_SECTION)
                        .ifAnswerIs(false)
                        .build())
                .build();

        Question question2 = Question.builder()
                .id("1356d972-5bd1-4cd3-80c7-9f43310fe002")
                .type(QuestionType.PHOTO)
                .questionDesc("Please upload photo of na object")
                .required(true)
                .condition(null)
                .build();

        Question question3 = Question.builder()
                .id("7578a0bd-6d75-4c61-81ad-31f5277287b1")
                .type(QuestionType.TEXT)
                .questionDesc("Describe actions taken to solve above issue")
                .required(false)
                .condition(null)
                .build();

        Question question4 = Question.builder()
                .id("9de3666f-90e7-4aea-8f14-44796696acef")
                .type(QuestionType.DATE)
                .questionDesc("Is installation postponed due to issue")
                .required(false)
                .condition(null)
                .build();

        // create new selector
        Selectors selectors = Selectors.builder()
                .operators(List.of("charging.al"))
                .providers(new ArrayList<>())
                .customerSegment(List.of("B2B"))
                .installationProperties(List.of("STAND_ALONE", "GSM_CONNECTION"))
                .build();

        // creating new section to store related questions
        SatSection section = new SatSection("67adf980-d4d0-4cbd-915d-347eec00b296", "1", Language.EN,
                1.0, selectors, new ArrayList<>());

        // when adding the questions to section
        section.addQuestion(question1);
        section.addQuestion(question2);
        section.addQuestion(question3);
        section.addQuestion(question4);

        // then
        assertThat(section)
                .isExactlyLike("""
                        
                        {
                          "id": "67adf980-d4d0-4cbd-915d-347eec00b296",
                          "version": "1",
                          "language": "EN",
                          "order": 1.0,
                          "selectors": {
                            "operators": [
                              "charging.al"
                            ],
                            "providers": [],
                            "customerSegment": [
                              "B2B"
                            ],
                            "installationProperties": [
                              "STAND_ALONE", "GSM_CONNECTION"
                            ]
                          },
                          "questions": [
                            {
                              "id": "ca5173f8-bf02-4967-94d2-e850f8a985f4",
                              "type": "BOOLEAN",
                              "question": "Is there any object nearby installation site, prone to damage during installation?",
                              "required": true,
                              "condition": {
                                "type": "SKIP_REST_OF_SECTION",
                                "ifAnswerIs": false
                              }
                            },
                            {
                              "id": "1356d972-5bd1-4cd3-80c7-9f43310fe002",
                              "type": "PHOTO",
                              "question": "Please upload photo of na object",
                              "required": true
                            },
                            {
                              "id": "7578a0bd-6d75-4c61-81ad-31f5277287b1",
                              "type": "TEXT",
                              "question": "Describe actions taken to solve above issue",
                              "required": false
                            },
                            {
                              "id": "9de3666f-90e7-4aea-8f14-44796696acef",
                              "type": "DATE",
                              "question": "Is installation postponed due to issue",
                              "required": false
                            }
                          ]
                        }
                        """);
    }

    @Test
    void testUpdateNonExistingQuestion() {
        // given
        Question question = Question.builder()
                .id("question-1")
                .type(QuestionType.BOOLEAN)
                .questionDesc("Is there any object nearby installation site, prone to damage during installation?")
                .required(true)
                .condition(null)
                .build();

        // create new selector
        Selectors selectors = Selectors.builder()
                .operators(new ArrayList<>())
                .providers(new ArrayList<>())
                .customerSegment(new ArrayList<>())
                .installationProperties(new ArrayList<>())
                .build();

        // creating new section to store related questions
        SatSection section = new SatSection("sec1", "1", Language.EN, 1, selectors, new ArrayList<>());

        // when - adding updating question whn it does not exist
        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> section.updateQuestion(question));

        // then - verify that the exception has the expected HTTP status code.
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
    }

    @Test
    void testUpdateExistingQuestion() {
        // given
        Question question = Question.builder()
                .id("1356d972-5bd1-4cd3-80c7-9f43310fe002")
                .type(QuestionType.BOOLEAN)
                .questionDesc("Is there any object nearby installation site, prone to damage during installation?")
                .required(true)
                .condition(null)
                .build();

        // create new selector
        Selectors selectors = Selectors.builder()
                .operators(new ArrayList<>())
                .providers(new ArrayList<>())
                .customerSegment(new ArrayList<>())
                .installationProperties(new ArrayList<>())
                .build();

        // creating new section to store related questions
        SatSection section = new SatSection("sec1", "1", Language.EN, 1, selectors, new ArrayList<>());

        // when - adding and updating question
        section.addQuestion(question);
        section.updateQuestion(Question.builder()
                .id("1356d972-5bd1-4cd3-80c7-9f43310fe002")
                .type(QuestionType.PHOTO)
                .questionDesc("Please upload photo of na object")
                .required(true)
                .condition(null)
                .build());

        // then - verify the question properly
        assertThat(section.questions.getFirst())
                .isExactlyLike("""
                        {
                              "id": "1356d972-5bd1-4cd3-80c7-9f43310fe002",
                              "type": "PHOTO",
                              "question": "Please upload photo of na object",
                              "required": true
                            }
                        """);
    }

    @Test
    void testRemovingQuestion() {
        // given
        Question question = Question.builder()
                .id("1356d972-5bd1-4cd3-80c7-9f43310fe002")
                .type(QuestionType.BOOLEAN)
                .questionDesc("Is there any object nearby installation site, prone to damage during installation?")
                .required(true)
                .condition(null)
                .build();

        // create new selector
        Selectors selectors = Selectors.builder()
                .operators(new ArrayList<>())
                .providers(new ArrayList<>())
                .customerSegment(new ArrayList<>())
                .installationProperties(new ArrayList<>())
                .build();

        // creating new section to store related questions
        SatSection section = new SatSection("sec1", "1", Language.EN, 1, selectors, new ArrayList<>());

        // when - adding and removing question
        section.addQuestion(question);
        assertEquals(1, section.getQuestions().size());

        section.removeQuestion("1356d972-5bd1-4cd3-80c7-9f43310fe002");
        assertTrue(section.getQuestions().isEmpty());
    }
}