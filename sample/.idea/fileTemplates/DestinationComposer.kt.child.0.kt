#if (${PACKAGE_NAME} && ${PACKAGE_NAME} != "")package ${PACKAGE_NAME}
    #set( $presentationIndex = $PACKAGE_NAME.indexOf(".presentation") )
    #if( $presentationIndex >= 0 )
        #set( $rootPackage = $PACKAGE_NAME.substring(0, $presentationIndex) )
    #end
#end
#parse("File Header.java")

import ${rootPackage}.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ${DESTINATION_NAME}ViewModel @Inject constructor() : BaseViewModel<Unit>() {
    val postTitle: MutableState<String> = mutableStateOf("")
    val postBody: MutableState<String> = mutableStateOf("")
    
    fun onClick() {
      TODO("Not yet implemented")
    }
}