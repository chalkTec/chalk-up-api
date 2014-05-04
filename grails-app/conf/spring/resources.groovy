import chalkup.security.UserappTokenStorageService

// Place your Spring DSL code here
beans = {

    tokenStorageService(UserappTokenStorageService) {
        userappService = ref("userappService")
    }

}
