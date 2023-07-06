#if (${PACKAGE_NAME} && ${PACKAGE_NAME} != "")package ${PACKAGE_NAME}
    #set( $presentationIndex = $PACKAGE_NAME.indexOf(".presentation") )
    #if( $presentationIndex >= 0 )
        #set( $rootPackage = $PACKAGE_NAME.substring(0, $presentationIndex) )
    #end
#end
#parse("File Header.java")

#set( $entityLowerCase= $ENTITY.toLowerCase())
#set( $entityLowerFirst= $ENTITY.replaceFirst( $ENTITY.substring(0, 1), $ENTITY.substring(0, 1).toLowerCase() ) )

import com.htecgroup.core.presentation.viewmodel.CoreItemViewModel
import ${rootPackage}.presentation.${entityLowerCase}.${ENTITY}View
import javax.inject.Inject

class ${ENTITY}ItemViewModel @Inject constructor(
	${entityLowerFirst}View: ${ENTITY}View,
	listener: ((${entityLowerFirst}View: ${ENTITY}View) -> Unit)?
) : CoreItemViewModel<PostView>(postView, listener)