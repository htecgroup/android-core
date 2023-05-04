![HTEC Group](htec_logo.png)

# Android Core Sample

Android app showcasing the usage of Android Core Lib.

## Usage

This sample application is using the Android Core Lib from the maven-local. In order to run the sample follow next steps:
- Clone the [Android Core Lib][core],
- Set BOM_VERSION_SUFFIX environment variable:
    - Mac/Linux: `export BOM_VERSION_SUFFIX=-LOCAL`
    - Windows: `setx BOM_VERSION_SUFFIX=-LOCAL`
- Run `./gradlew publishToMavenLocal` inside the Core project,
- Open the Sample project and run the app.

NOTE: Use locally built Android Core Lib only for testing the Sample app. For other projects it is recommended to use the official version from Maven Central.

## License

Android Core Lib is released under the [Apache 2.0 license][license].

```
Copyright 2023 HTEC Group Inc.

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```

## Disclaimer

This is not an officially supported HTEC Group product.

[core]: https://github.com/htecgroup/android-core
[license]: https://github.com/htecgroup/android-core/blob/main/LICENSE
