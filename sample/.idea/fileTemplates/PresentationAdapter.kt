#if (${PACKAGE_NAME} && ${PACKAGE_NAME} != "")package ${PACKAGE_NAME}
    #set( $presentationIndex = $PACKAGE_NAME.indexOf(".presentation") )
    #if( $presentationIndex >= 0 )
        #set( $rootPackage = $PACKAGE_NAME.substring(0, $presentationIndex) )
    #end
#end
#parse("File Header.java")

## Convert to snake case **
#set( $regex = "([a-z])([A-Z]+)")
#set( $replacement = "$1_$2")
#set( $entityToSnakeCase = $ENTITY.replaceAll($regex, $replacement).toLowerCase())
#set( $entityLowerCase= $ENTITY.toLowerCase())

import com.htecgroup.core.presentation.adapter.BaseAdapter
import ${rootPackage}.presentation.R
import ${rootPackage}.presentation.${entityLowerCase}.${ENTITY}View
import javax.inject.Inject

class ${ENTITY}Adapter @Inject constructor() : BaseAdapter<${ENTITY}View>() {

	var listener: ((${ENTITY}View) -> Unit)? = null

	override fun provideLayoutId(position: Int) = R.layout.item_${entityToSnakeCase}

	override fun provideViewModel(position: Int) =
		${ENTITY}ItemViewModel(get(position) ?: ${ENTITY}View(), listener)
}