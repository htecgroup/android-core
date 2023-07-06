#if (${PACKAGE_NAME} && ${PACKAGE_NAME} != "")package ${PACKAGE_NAME}.db.dao
    #set( $dataDirIndex = $PACKAGE_NAME.indexOf(".data") )
    #if( $dataDirIndex >= 0 )
        #set( $rootPackage = $PACKAGE_NAME.substring(0, $dataDirIndex) )
    #end
#end
#parse("File Header.java")

#set ($entityLower = $ENTITY.toLowerCase())
## Convert to lower case only first char:**
#set ($entityLowerFirst = $ENTITY.replaceFirst( $ENTITY.substring(0, 1), $ENTITY.substring(0, 1).toLowerCase() ) )


import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import ${rootPackage}.data.db.entities.${ENTITY}Entity
import kotlinx.coroutines.flow.Flow

@Dao
interface ${ENTITY}Dao : BaseDao<${ENTITY}Entity> {

    @Query("SELECT * FROM ${${ENTITY}Entity.TABLE_NAME}")
    fun getAll(): Flow<List<${ENTITY}Entity>>

    @Query("DELETE FROM ${${ENTITY}Entity.TABLE_NAME}")
    suspend fun deleteAll(): Int

    @Transaction
    suspend fun update${ENTITY}s(${entityLowerFirst}s: List<${ENTITY}Entity>) {
        insertOrUpdate(${entityLowerFirst}s)
        deleteExcept(${entityLowerFirst}s.map { it.id })
    }

    @Transaction
    suspend fun insertOrUpdate(${entityLowerFirst}s: List<${ENTITY}Entity>) {
        insertOrIgnore(${entityLowerFirst}s).also { insertResult ->
            ${entityLowerFirst}s
                .filterIndexed { index, _ ->
                    insertResult[index] == -1L
                }
                .takeIf { it.isNotEmpty() }
                ?.let {
                    update(it)
                }
        }
    }

    /**
     * Inserts list of ${entityLowerFirst}s to database.
     *
     * A method that returns the inserted rows ids will return -1 for rows that are not inserted
     * since this strategy will ignore the row if there is a conflict.
     *
     * @return List of results of insert
     */
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertOrIgnore(${entityLowerFirst}s: List<${ENTITY}Entity>): List<Long>

    /**
     * Deletes ${entityLowerFirst}s from database which ids are not in parameters list.
     */
    @Query("DELETE FROM ${${ENTITY}Entity.TABLE_NAME} WHERE ${${ENTITY}Entity.COLUMN_ID} NOT IN (:ids)")
    suspend fun deleteExcept(ids: List<Int>)
}
