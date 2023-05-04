#if (${PACKAGE_NAME} && ${PACKAGE_NAME} != "")package ${PACKAGE_NAME}.repositories
    #set( $dataDirIndex = $PACKAGE_NAME.indexOf(".data") )
    #if( $dataDirIndex >= 0 )
        #set( $rootPackage = $PACKAGE_NAME.substring(0, $dataDirIndex) )
    #end
#end
#parse("File Header.java")

#set ($entityLower = $ENTITY.toLowerCase())
## Convert to lower case only first char:**
#set ($entityLowerFirst = $ENTITY.replaceFirst( $ENTITY.substring(0, 1), $ENTITY.substring(0, 1).toLowerCase() ) )

import com.htecgroup.core.data.CoreRepository
import ${rootPackage}.data.db.dao.${ENTITY}Dao
import ${rootPackage}.data.network.api.${ENTITY}Api
import ${rootPackage}.domain.${entityLower}.${ENTITY}Repository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ${ENTITY}RepositoryImpl @Inject constructor(
    private val ${entityLowerFirst}Dao: ${ENTITY}Dao,
    private val ${entityLowerFirst}Api: ${ENTITY}Api
) : CoreRepository(), ${ENTITY}Repository {

}