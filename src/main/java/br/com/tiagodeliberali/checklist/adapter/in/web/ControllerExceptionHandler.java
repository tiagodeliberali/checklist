package br.com.tiagodeliberali.checklist.adapter.in.web;

import br.com.tiagodeliberali.checklist.core.application.port.out.FailedToLoadException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.hateoas.mediatype.problem.Problem;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class ControllerExceptionHandler {
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    Problem handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        List<FieldValidationError> fields = new ArrayList<>();

        for (FieldError fieldError : e.getBindingResult().getFieldErrors()) {
            fields.add(new FieldValidationError(fieldError.getField(), fieldError.getDefaultMessage()));
        }

        String detailFields =
                fields.stream().map(FieldValidationError::toString).collect(Collectors.joining("; "));

        return Problem.create()
                .withType(getTypeUri("invalid-input-fields"))
                .withTitle("Input data is invalid")
                .withDetail(String.format("Please fix the field values: %s", detailFields))
                .withProperties(map -> map.put("fields", fields));
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(FailedToLoadException.class)
    @ResponseBody
    Problem handleFailedToLoadException(FailedToLoadException e) {
        return Problem.create()
                .withType(getTypeUri("failed-to-load"))
                .withTitle("Failed to load resource")
                .withDetail(e.getMessage());
    }

    private URI getTypeUri(String type) {
        URI typeUri;
        try {
            typeUri = new URI("https://tiagodeliberali.com.br/checklist/" + type);
        } catch (URISyntaxException uriSyntaxException) {
            return null;
        }
        return typeUri;
    }

    @Getter
    @AllArgsConstructor
    class FieldValidationError {
        private String field;
        private String message;

        @Override
        public String toString() {
            return field + ": " + message;
        }
    }
}
