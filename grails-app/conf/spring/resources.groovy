import chalkup.security.MyPermissionEvaluator
import chalkup.security.UserappTokenStorageService

// Place your Spring DSL code here
beans = {

    permissionEvaluator(MyPermissionEvaluator)

    tokenStorageService(UserappTokenStorageService)

}
