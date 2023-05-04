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
#set( $screenToSnakeCase = $SCREEN.replaceAll($regex, $replacement).toLowerCase())

#set( $nv = $NAV_CONTROLLER)

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.navArgs
import ${rootPackage}.presentation.R
import ${rootPackage}.presentation.base.BaseToolbarFragment
import ${rootPackage}.presentation.databinding.Fragment${SCREEN}Binding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ${SCREEN}Fragment :
    BaseToolbarFragment<Fragment${SCREEN}Binding, ${SCREEN}ViewModel, ${SCREEN}Routes>() {

    public override fun provideLayoutId() = R.layout.fragment_${screenToSnakeCase}

    public override fun provideViewModelClass() = ${SCREEN}ViewModel::class.java

    override fun provideToolbarTitleId(): Int = R.string.title_${screenToSnakeCase}

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
    }

}
