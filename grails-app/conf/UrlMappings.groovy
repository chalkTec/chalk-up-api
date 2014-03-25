class UrlMappings {

    static mappings = {
        "/$controller/$action?/$id?(.$format)?" {
            constraints {
                // apply constraints here
            }
        }

        // Mappings supported by resource-specific controllers
        // should be added before the default mapping used for
        // resources handled by the default RestfulApiController.

        // name otherthingRestfulApi: "api/other-things/$id"

        // Default controller to handle RESTful API requests.
        // Place URL mappings to specific controllers BEFORE this mapping.
        //
        "/rest/$pluralizedResourceName/$id"(controller: 'restfulApi') {
            action = [GET: "show", PUT: "update", DELETE: "delete"]
            parseRequest = false
            constraints {
                // to constrain the id to numeric, uncomment the following:
                // id matches: /\d+/
            }
        }
        "/rest/$pluralizedResourceName"(controller: 'restfulApi') {
            action = [GET: "list", POST: "create"]
            parseRequest = false
        }

        // Support for nested resources. You may add additional URL mappings to handle
        // additional nested resource requirements.
        //
        "/rest/$parentPluralizedResourceName/$parentId/$pluralizedResourceName/$id"(controller: 'restfulApi') {
            action = [GET: "show", PUT: "update", DELETE: "delete"]
            parseRequest = false
            constraints {
                // to constrain the id to numeric, uncomment the following:
                // id matches: /\d+/
            }
        }

        "/rest/$parentPluralizedResourceName/$parentId/$pluralizedResourceName"(controller: 'restfulApi') {
            action = [GET: "list", POST: "create"]
            parseRequest = false
        }

    }
}
