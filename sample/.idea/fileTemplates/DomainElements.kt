#set ($entityLower = $ENTITY.toLowerCase())
#if (${PACKAGE_NAME} && ${PACKAGE_NAME} != "")package ${PACKAGE_NAME}.${entityLower}

#end
#parse("File Header.java")

#set ($useCase = $USE_CASE)

data class ${ENTITY}(
    val id: Int = 0,
)
