/*
 * Copyright 2023 HTEC Group Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.htecgroup.core.presentation.compose.navigation

import androidx.navigation.NamedNavArgument
import androidx.navigation.NavType
import androidx.navigation.navArgument

/**
 * [Destination] represents a navigation path following best practise creating
 * destinations on the web, where [name] represents the path, and [args] list of arguments.
 */
interface Destination {
    val name: String
    val args: List<NamedNavArgument>? get() = null
}

/**
 * Extension property for generating [Destination] route
 * combining [Destination.name] and [routeArgs].
 */
val Destination.route: String get() = name + routeArgs

/**
 * Extension property for generating [Destination] route arguments
 * by formatting [Destination.args] name property.
 */
@Suppress("SpreadOperator")
val Destination.routeArgs: String
    get() = args?.let { arguments ->
        String.format(routeArgsFormat, *arguments.map { "{${it.name}}" }.toTypedArray())
    } ?: ""

/**
 * Extension property for formatting list of arguments.
 * Arguments are separated with '&' symbol and joined into String.
 */
val Destination.routeArgsFormat: String
    get() = args
        ?.joinToString("&") { "${it.name}=%s" }
        ?.let { "?$it" } ?: ""

/**
 * Validates [routeArgs] and returns formatted destination path.
 */
@Suppress("SpreadOperator")
fun Destination.routeArgs(args: List<Int>): String {
    if (args.isEmpty()) return ""
    require(args.size == (this.args?.size ?: 0)) { "Illegal arg size" }
    return String.format(routeArgsFormat, *args.toTypedArray())
}

/**
 * Helper function for creating default [NavType.IntType] named argument named [argName].
 */
fun intArg(argName: String) =
    navArgument(argName) {
        type = NavType.IntType
        nullable = false
        defaultValue = 0
    }
