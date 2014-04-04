package chalkup.exceptions

import org.springframework.http.HttpStatus


class InvalidModificationException extends RuntimeException {

    private String reason

    // required by restful-api
    int getHttpStatusCode() {
        return HttpStatus.UNPROCESSABLE_ENTITY.value();
    }

    def returnMap = {
        return [message: reason]
    }

}
