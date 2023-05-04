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

import androidx.recyclerview.widget.DiffUtil
import com.htecgroup.core.presentation.adapter.BaseDiffAdapter
import ${rootPackage}.presentation.R
import ${rootPackage}.presentation.${entityLowerCase}.${ENTITY}View
import javax.inject.Inject

class ${ENTITY}Adapter @Inject constructor(): BaseDiffAdapter<${ENTITY}View>(
	object: DiffUtil.ItemCallback<${ENTITY}View>() {
		override fun areItemsTheSame(oldItem: ${ENTITY}View, newItem: ${ENTITY}View) =
			oldItem.id == newItem.id

		override fun areContentsTheSame(oldItem: ${ENTITY}View, newItem: ${ENTITY}View) =
			oldItem.compareTo(newItem) == 0
	}
) {
	var listener: ((${ENTITY}View) -> Unit)? = null
	
	override fun provideLayoutId(position: Int) = R.layout.item_${entityToSnakeCase}

	override fun provideViewModel(position: Int) =
		${ENTITY}ItemViewModel(getItem(position) ?: ${ENTITY}View(), listener)
}