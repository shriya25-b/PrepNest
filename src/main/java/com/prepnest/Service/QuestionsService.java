package com.prepnest.Service;

import com.prepnest.Entity.Questions;
import com.prepnest.Repository.QuestionsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.HashMap;

@Service
public class QuestionsService {

    @Autowired
    private QuestionsRepository questionsRepository;

    // Method to fetch all questions
    public List<Questions> getAllQuestions() {
        return questionsRepository.findAll(); // Fetching all questions from the database
    }

    // Method to check the submitted answers against the correct answers
    public Map<Long, Boolean> checkAnswers(Map<Long, String> submittedAnswers) {
        Map<Long, Boolean> results = new HashMap<>();

        // Retrieve all questions from the database
        List<Questions> questions = questionsRepository.findAll();

        // Loop through each question to check submitted answers
        for (Questions question : questions) {
            String correctAnswer = question.getCorrectAnswer(); // Get the correct answer from the question entity
            String submittedAnswer = submittedAnswers.get(question.getId()); // Get the submitted answer

            // Check if a submitted answer exists and compare with the correct answer
            if (submittedAnswer != null) {
                // Store the result of the comparison
                results.put(question.getId(), correctAnswer.equals(submittedAnswer));
            } else {
                // If no answer was submitted for a question, mark it as false
                results.put(question.getId(), false);
            }
        }

        return results; // Return the map of results
    }
}
