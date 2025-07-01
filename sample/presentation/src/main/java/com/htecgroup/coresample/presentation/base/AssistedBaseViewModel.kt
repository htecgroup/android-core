package com.htecgroup.coresample.presentation.base

abstract class AssistedBaseViewModel<K : Any, T : Any>(val navKey: K) :
    BaseViewModel<T>()
