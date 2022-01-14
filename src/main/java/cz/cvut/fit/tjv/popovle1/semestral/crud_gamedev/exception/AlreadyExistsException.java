package cz.cvut.fit.tjv.popovle1.semestral.crud_gamedev.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CREATED)
public class AlreadyExistsException extends Exception {
    public AlreadyExistsException(String message) {
        super(message);
    }
}
