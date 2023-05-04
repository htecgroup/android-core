#if (${PACKAGE_NAME} && ${PACKAGE_NAME} != "")package ${PACKAGE_NAME}
    #set( $presentationIndex = $PACKAGE_NAME.indexOf(".presentation") )
    #if( $presentationIndex >= 0 )
        #set( $rootPackage = $PACKAGE_NAME.substring(0, $presentationIndex) )
    #end
#end
#parse("File Header.java")

import androidx.navigation.NavDirections
import androidx.navigation.fragment.NavHostFragment
import com.htecgroup.core.presentation.routes.NavigationController
import ${rootPackage}.presentation.R
import javax.inject.Inject

class ${NAV_NAME}NavigationController @Inject constructor() : NavigationController {
    @Inject
    lateinit var activity: ${ACTIVITY_NAME}

    override val navController by lazy {
        val navHostFragment =
            activity.supportFragmentManager.findFragmentById(R.id.navHostFragmentMain) as NavHostFragment
        navHostFragment.navController
    }

    fun navigate(destination: NavDirections) = with(navController) {
        currentDestination?.getAction(destination.actionId)
            ?.let { navigate(destination) }
    }
}
