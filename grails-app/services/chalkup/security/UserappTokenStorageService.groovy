package chalkup.security

import com.odobo.grails.plugin.springsecurity.rest.token.storage.TokenNotFoundException
import com.odobo.grails.plugin.springsecurity.rest.token.storage.TokenStorageService
import io.userapp.client.exceptions.UserAppException

class UserappTokenStorageService implements TokenStorageService {

    def userappService

    @Override
    Object loadUserByToken(String tokenValue) throws TokenNotFoundException {
        try {
            return userappService.getUserByToken(tokenValue)
        }
        catch (UserAppException exception) {
            log.error(exception)
            throw new TokenNotFoundException(exception.getMessage())
        }
    }

    @Override
    void storeToken(String tokenValue, Object principal) {
        throw new UnsupportedOperationException("cannot store tokens, they are generated with userapp")
    }

    @Override
    void removeToken(String tokenValue) throws TokenNotFoundException {
        throw new UnsupportedOperationException("cannot store tokens, they are generated with userapp")
    }
}
