package com.bookingsiemens.BookingSiemens.Services;

import com.bookingsiemens.BookingSiemens.Commons.Feedback;
import com.bookingsiemens.BookingSiemens.CustomExceptions.FieldNullException;
import com.bookingsiemens.BookingSiemens.CustomExceptions.InstanceNotFoundException;
import com.bookingsiemens.BookingSiemens.Repositories.FeedbackRepository;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FeedbackService {

    private final FeedbackRepository feedbackRepository;

    /**
     * Constructor for the Feedback Service
     * @param feedbackRepository the Feedback Repository
     */
    @Autowired
    public FeedbackService(FeedbackRepository feedbackRepository) {
        this.feedbackRepository = feedbackRepository;
    }

    /**
     * Creates a Feedback in the database
     * @param feedback - the Feedback offered for creation
     * @return - the created Feedback
     * @throws RuntimeException - the Feedback already exists in the database or format is not correct
     */
    public Feedback createFeedback(Feedback feedback) throws RuntimeException {
        nullFieldChecker(feedback);
        return feedbackRepository.save(feedback);
    }

    /**
     * Deletes a Feedback from the database
     * @param feedback_id the id of the Feedback to be deleted
     */
    public void deleteFeedback(UUID feedback_id) {
        Feedback feedback = checkFeedbackExistence(feedback_id);
        feedbackRepository.deleteById(feedback.getFeedback_id());
    }

    /**
     * Helper method to check whether a Feedback exists
     * @param feedback_id - the id of the Feedback to be localized
     * @return - the Feedback desired, provided it exists
     * @throws RuntimeException - Feedback was not found or the id is null
     */
    public Feedback checkFeedbackExistence (UUID feedback_id) throws RuntimeException {
        if(feedback_id == null) {
            throw new FieldNullException("Null id not accepted.");
        }
        Optional<Feedback> o = feedbackRepository.findById(feedback_id);
        if(o.isEmpty()) {
            throw new InstanceNotFoundException("No such room could be found.");
        }
        return o.get();
    }

    /**
     * Helper method to check the fields of a Feedback for nullability
     * @param feedback - the Feedback to verify
     * @throws RuntimeException - A field is null
     */
    public void nullFieldChecker (Feedback feedback) throws RuntimeException {
        if(feedback == null || feedback.getComment() == null || !(feedback.getStars()>=0 && feedback.getStars()<=5) || feedback.getHotel() == null) {
            throw new FieldNullException("Null fields are not valid.");
        }
    }
}
