#if (${PACKAGE_NAME} && ${PACKAGE_NAME} != "")package ${PACKAGE_NAME}
    #set( $presentationIndex = $PACKAGE_NAME.indexOf(".presentation") )
    #if( $presentationIndex >= 0 )
        #set( $rootPackage = $PACKAGE_NAME.substring(0, $presentationIndex) )
    #end
#end
#parse("File Header.java")

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.htecgroup.core.presentation.compose.AnimateSlide
import com.htecgroup.core.presentation.compose.composer.DestinationComposer
import com.htecgroup.core.presentation.compose.navigation.Destination
import ${rootPackage}.presentation.R
import dagger.hilt.android.scopes.ActivityRetainedScoped
import javax.inject.Inject

@ActivityRetainedScoped
class ${DESTINATION_NAME}DestinationComposer @Inject constructor() : DestinationComposer<${DESTINATION_NAME}ViewModel>() {

    override val titleResId = "title_${DESTINATION_NAME}"
    override val destination: Destination = "" //TODO: Add destination screen
    override val viewModelClass: Class<${DESTINATION_NAME}ViewModel> = ${DESTINATION_NAME}ViewModel::class.java

    @Composable
    override fun Content(navController: NavHostController, viewModel: ${DESTINATION_NAME}ViewModel) {

        AnimateSlide(navController.isScreenVisible, -1) {
            ${DESTINATION_NAME}Screen(viewModel.postTitle, viewModel.postBody, viewModel::onClick)
        }
    }
}