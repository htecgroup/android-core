#if (${PACKAGE_NAME} && ${PACKAGE_NAME} != "")package ${PACKAGE_NAME}
    #set( $presentationIndex = $PACKAGE_NAME.indexOf(".presentation") )
    #if( $presentationIndex >= 0 )
        #set( $rootPackage = $PACKAGE_NAME.substring(0, $presentationIndex) )
    #end
#end
#parse("File Header.java")

#set ($entityLower = $ENTITY.toLowerCase())

import android.os.Parcelable
import ${rootPackage}.domain.${entityLower}.${ENTITY}
import kotlinx.parcelize.Parcelize

@Parcelize
data class ${ENTITY}View(
    val id: Int = 0,
    
) : Parcelable

fun ${ENTITY}View.to${ENTITY}() = ${ENTITY}(
    id,
)

fun ${ENTITY}.to${ENTITY}View() = ${ENTITY}View(
    id,
)