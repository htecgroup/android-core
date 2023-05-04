#if (${PACKAGE_NAME} && ${PACKAGE_NAME} != "")package ${PACKAGE_NAME}.network.api
    #set( $dataDirIndex = $PACKAGE_NAME.indexOf(".data") )
    #if( $dataDirIndex >= 0 )
        #set( $rootPackage = $PACKAGE_NAME.substring(0, $dataDirIndex) )
    #end
#end
#parse("File Header.java")

#set ($entityLowerFirst = $ENTITY.replaceFirst( $ENTITY.substring(0, 1), $ENTITY.substring(0, 1).toLowerCase() ) )

import ${rootPackage}.data.network.entities.${ENTITY}Raw
import retrofit2.Response
import retrofit2.http.GET

interface ${ENTITY}Api {

    @GET("${entityLowerFirst}s")
    suspend fun get${ENTITY}s(): Response<List<${ENTITY}Raw>>

}