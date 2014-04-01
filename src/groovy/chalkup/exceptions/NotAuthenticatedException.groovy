package chalkup.exceptions

import org.springframework.http.HttpStatus
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException


public class NotAuthenticatedException extends AuthenticationCredentialsNotFoundException {

    NotAuthenticatedException() {
        super("authentication required")
    }

    // required by restful-api
    int getHttpStatusCode() {
        return HttpStatus.UNAUTHORIZED.value();
    }

    def returnMap = {
        return [
                message: "authentication is required",
                headers: ["WWW-Authenticate" : "Basic realm=\"chalkUp!\""]
        ]
    }

}
