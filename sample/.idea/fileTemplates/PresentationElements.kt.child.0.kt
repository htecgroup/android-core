#if (${PACKAGE_NAME} && ${PACKAGE_NAME} != "")package ${PACKAGE_NAME}
    #set( $presentationIndex = $PACKAGE_NAME.indexOf(".presentation") )
    #if( $presentationIndex >= 0 )
        #set( $rootPackage = $PACKAGE_NAME.substring(0, $presentationIndex) )
    #end
#end
#parse("File Header.java")

## Convert to lower case only first char:**
#set( $navControllerLowerFirst= $NAV_CONTROLLER.replaceFirst( $NAV_CONTROLLER.substring(0, 1), $NAV_CONTROLLER.substring(0, 1).toLowerCase() ) )

import ${rootPackage}.presentation.base.BaseRoutes
import javax.inject.Inject

class ${SCREEN}Routes @Inject constructor(
    private val ${navControllerLowerFirst}NavigationController: ${NAV_CONTROLLER}NavigationController
) : BaseRoutes(${navControllerLowerFirst}NavigationController.activity) {

    override val navController by lazy { ${navControllerLowerFirst}NavigationController.navController }
}
