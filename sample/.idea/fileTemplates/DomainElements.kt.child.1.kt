#set ($entityLower = $ENTITY.toLowerCase())
#if (${PACKAGE_NAME} && ${PACKAGE_NAME} != "")package ${PACKAGE_NAME}.${entityLower}

#end
#parse("File Header.java")

## Convert to lower case only first char:**
#set( $useCaseLowerFirst= $USE_CASE.replaceFirst( $USE_CASE.substring(0, 1), $USE_CASE.substring(0, 1).toLowerCase() ) )


interface ${ENTITY}Repository {
    suspend fun ${useCaseLowerFirst}(): Result<Unit>
}