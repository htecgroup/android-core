#if (${PACKAGE_NAME} && ${PACKAGE_NAME} != "")package ${PACKAGE_NAME}.db.entities
    #set( $dataDirIndex = $PACKAGE_NAME.indexOf(".data") )
    #if( $dataDirIndex >= 0 )
        #set( $rootPackage = $PACKAGE_NAME.substring(0, $dataDirIndex) )
    #end
#end
#parse("File Header.java")

#set ($entityLower = $ENTITY.toLowerCase())

## Convert to snake case **
#set( $regex = "([a-z])([A-Z]+)")
#set( $replacement = "$1_$2")
#set( $entityToSnakeCase = $ENTITY.replaceAll($regex, $replacement).toLowerCase())

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import ${rootPackage}.data.network.entities.${ENTITY}Raw
import ${rootPackage}.domain.$entityLower.${ENTITY}

@Entity(tableName = ${ENTITY}Entity.TABLE_NAME)
data class ${ENTITY}Entity(
    @ColumnInfo(name = COLUMN_ID)
    @PrimaryKey
    val id: Int,
) : Comparable<${ENTITY}Entity> {

    companion object {
        const val TABLE_NAME = "${entityToSnakeCase}"
        const val COLUMN_ID = "id"
    }

    @Ignore
    override fun compareTo(other: ${ENTITY}Entity): Int {
        if (id == other.id) {
            TODO("ADD OTHER CASES")
            return 0
        }
        return if (id < other.id) -1 else 1
    }
}

fun ${ENTITY}Entity.to${ENTITY}() = ${ENTITY}(id,)

fun ${ENTITY}Entity.to${ENTITY}Raw() =
    ${ENTITY}Raw(id = id, )

fun ${ENTITY}.to${ENTITY}Entity() = ${ENTITY}Entity(id, )
