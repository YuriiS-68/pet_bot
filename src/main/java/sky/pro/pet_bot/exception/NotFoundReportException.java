package sky.pro.pet_bot.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class NotFoundReportException extends RuntimeException{
    public NotFoundReportException(String message){
        super();
    }
}
