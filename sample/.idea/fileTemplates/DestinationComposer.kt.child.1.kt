#if (${PACKAGE_NAME} && ${PACKAGE_NAME} != "")package ${PACKAGE_NAME}
    #set( $presentationIndex = $PACKAGE_NAME.indexOf(".presentation") )
    #if( $presentationIndex >= 0 )
        #set( $rootPackage = $PACKAGE_NAME.substring(0, $presentationIndex) )
    #end
#end
#parse("File Header.java")

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ${DESTINATION_NAME}Screen(title: MutableState<String>, body: MutableState<String>, onClick: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
    }
}

@SuppressLint("UnrememberedMutableState")
@Preview
@Composable
private fun ${DESTINATION_NAME}ScreenPreview() {
    ${DESTINATION_NAME}Screen(
        title = mutableStateOf("Title"),
        body = mutableStateOf("Body"),
        onClick = {}
    )
}