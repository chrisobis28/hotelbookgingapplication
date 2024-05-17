package com.bookingsiemens.BookingSiemens.Controllers;

import com.bookingsiemens.BookingSiemens.Commons.Feedback;
import com.bookingsiemens.BookingSiemens.CustomExceptions.DuplicatedInstanceException;
import com.bookingsiemens.BookingSiemens.CustomExceptions.FieldNullException;
import com.bookingsiemens.BookingSiemens.CustomExceptions.InstanceNotFoundException;
import com.bookingsiemens.BookingSiemens.Routes;
import com.bookingsiemens.BookingSiemens.Services.FeedbackService;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(Routes.FEEDBACKS)
public class FeedbackController {

    private final FeedbackService feedbackService;

    /**
     * Constructor for the Feedback Controller
     * @param feedbackService the given Feedback Service
     */
    @Autowired
    public FeedbackController(FeedbackService feedbackService) {
        this.feedbackService = feedbackService;
    }

    /**
     * Adds a feedback to the database
     * @param feedback the Feedback to be added
     * @return - the Feedback that was added
     */
    @PostMapping("")
    public ResponseEntity<Feedback> addFeedback(@RequestBody Feedback feedback) {
        try {
            return ResponseEntity.ok(feedbackService.createFeedback(feedback));
        }
        catch(FieldNullException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        catch(DuplicatedInstanceException e){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }

    /**
     * Deletes a Feedback from the database
     * @param feedback_id - the UUID of the Feedback to be deleted
     * @return - status of the deletion
     */
    @DeleteMapping("/feedbackId")
    public ResponseEntity<String> deleteFeedback(@PathVariable("feedbackId") UUID feedback_id) {
        try {
            feedbackService.deleteFeedback(feedback_id);
            return ResponseEntity.status(HttpStatus.OK).body("Success.");
        }
        catch(FieldNullException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
        catch(InstanceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
