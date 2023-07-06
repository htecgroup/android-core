#if (${PACKAGE_NAME} && ${PACKAGE_NAME} != "")package ${PACKAGE_NAME}.network.entities
    #set( $dataDirIndex = $PACKAGE_NAME.indexOf(".data") )
    #if( $dataDirIndex >= 0 )
        #set( $rootPackage = $PACKAGE_NAME.substring(0, $dataDirIndex) )
    #end
#end
#parse("File Header.java")

#set ($entityLower = $ENTITY.toLowerCase())
## Convert to lower case only first char:**
#set ($entityLowerFirst = $ENTITY.replaceFirst( $ENTITY.substring(0, 1), $ENTITY.substring(0, 1).toLowerCase() ) )

import ${rootPackage}.data.db.entities.${ENTITY}Entity
import ${rootPackage}.domain.${entityLower}.${ENTITY}
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ${ENTITY}Raw(

    @Json(name = "id")
    val id: Int,
)

fun ${ENTITY}Raw.to${ENTITY}() = ${ENTITY}(id,)

fun ${ENTITY}Raw.to${ENTITY}Entity() = ${ENTITY}Entity(id = id,)

fun ${ENTITY}.to${ENTITY}Raw() = ${ENTITY}Raw(id = id,)
