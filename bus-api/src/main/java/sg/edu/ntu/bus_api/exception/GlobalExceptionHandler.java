package sg.edu.ntu.bus_api.exception;

import java.time.LocalDateTime;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    
    // Add exception handlers here
    // Look at @ExceptionHandler line to see name of handler

    public static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(BusStopNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleBusStopNotFoundException (BusStopNotFoundException e) {
        ErrorResponse errorResponse = new ErrorResponse(e.getMessage(), LocalDateTime.now());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(BusServiceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleBusServiceNotFoundException (BusServiceNotFoundException e) {
        ErrorResponse errorResponse = new ErrorResponse(e.getMessage(), LocalDateTime.now());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(BusRouteNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleBusRouteNotFoundException (BusRouteNotFoundException e) {
        ErrorResponse errorResponse = new ErrorResponse(e.getMessage(), LocalDateTime.now());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    // Validation exception handler
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationExceptions(MethodArgumentNotValidException e) {
        // Get a list of validation errors
        List<ObjectError> validationErrors = e.getBindingResult().getAllErrors();

        // Create a StringBuilder to store all the messages
        StringBuilder sb = new StringBuilder();

        // Loop through all the errors and append the messages
        for (ObjectError error : validationErrors) {
            sb.append(error.getDefaultMessage() + ". ");
        }
        
        ErrorResponse errorResponse = new ErrorResponse(sb.toString(), LocalDateTime.now());

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    // General Exception handler
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception e) {
        ErrorResponse errorResponse = new ErrorResponse("Something went wrong. This is from General Exception handler.", LocalDateTime.now());

        //you can call logger here
        logger.error("🔴 This is a General error message");

        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // Illegal Argument exception handler
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleIllegalArgumentException (IllegalArgumentException e){
        ErrorResponse errorResponse = new ErrorResponse(e.getMessage(), LocalDateTime.now());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST); 
    }

    // Duplicate Bus stop code exception handler
    @ExceptionHandler(BusStopDuplicateException.class)
    public ResponseEntity<ErrorResponse> handleDuplicateBusStopCodeException (BusStopDuplicateException e) {
        ErrorResponse errorResponse = new ErrorResponse(e.getMessage(), LocalDateTime.now());
        logger.error("🔴🔴🔴 This is a Duplicate Bus Stop Code error message", e);
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
}

// A general exception handler can catch any exception (both checked and
// unchecked) that is not explicitly handled elsewhere