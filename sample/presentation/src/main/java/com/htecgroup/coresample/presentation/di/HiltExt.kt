package com.htecgroup.coresample.presentation.di

import androidx.compose.runtime.Composable
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.ViewModel

@Composable
inline fun <reified VM : ViewModel, reified F : Any, K : Any> hiltAssistedViewModel(
    @Suppress("unused") navKey: K,
    noinline creationCallback: (F) -> VM
): VM {
    return hiltViewModel<VM, F>(
        creationCallback = creationCallback
    )
}
