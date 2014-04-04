package chalkup.exceptions

import org.springframework.http.HttpStatus


public class NotFoundException extends RuntimeException {

    private String objectName
    private def objectId

    // required by restful-api
    int getHttpStatusCode() {
        return HttpStatus.NOT_FOUND.value();
    }

    def returnMap = {
        return [message: "$objectName with ID $objectId not found"]
    }

}
