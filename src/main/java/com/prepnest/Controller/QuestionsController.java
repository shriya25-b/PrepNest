package com.prepnest.Controller;

import com.prepnest.Service.QuestionsService;
import com.prepnest.Entity.Questions;
import com.prepnest.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class QuestionsController {

    @Autowired
    private QuestionsService questionsService;
    private UserService userService;

    // Get all questions
    @GetMapping("/questions")
    public List<Questions> getAllQuestions() {
        return questionsService.getAllQuestions();
    }

    // Submit all answers
    @PostMapping("/submitAllAnswers")
    public ResponseEntity<Map<String, Object>> submitAllAnswers(@RequestBody List<Map<String, String>> submissions) {
        Map<Long, String> feedback = new HashMap<>();
        int score = 0;
        int totalQuestions = submissions.size();

        // Log received submissions for debugging
        System.out.println("Received submissions: " + submissions);

        // Collect submitted answers into a map
        for (Map<String, String> submission : submissions) {
            Long questionId = Long.valueOf(submission.get("questionId"));
            String selectedAnswer = submission.get("selectedAnswer");

            // Check the answer
            boolean isCorrect = questionsService.checkAnswers(Map.of(questionId, selectedAnswer)).get(questionId);
            feedback.put(questionId, isCorrect ? "Correct!" : "Wrong!");
            if (isCorrect) score++;
        }

        // Create a response object to return both feedback and score
        Map<String, Object> response = new HashMap<>();
        response.put("feedback", feedback);
        response.put("score", score);
        response.put("totalQuestions", totalQuestions);

        return ResponseEntity.ok(response);


    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred: " + ex.getMessage());
    }
}
