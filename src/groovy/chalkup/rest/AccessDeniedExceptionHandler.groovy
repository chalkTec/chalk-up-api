package chalkup.rest

import net.hedtech.restfulapi.ErrorResponse
import net.hedtech.restfulapi.ExceptionHandler
import net.hedtech.restfulapi.ExceptionHandlerContext
import net.hedtech.restfulapi.Inflector
import org.springframework.security.access.AccessDeniedException

class AccessDeniedExceptionHandler implements ExceptionHandler {

    boolean supports(Throwable e) {
        (e instanceof AccessDeniedException)
    }

    ErrorResponse handle(Throwable e, ExceptionHandlerContext context) {
        new ErrorResponse(
                httpStatusCode: 403,
                message: context.localizer.message(
                        code: "default.access.denied.failure",
                        args: [Inflector.singularize(context.pluralizedResourceName)]),
                content: ['originatingErrorMessage': e.getMessage()]
        )
    }
}