# Contributing Guidelines

Thank you for considering contributing to our project! We welcome contributions from anyone, whether you're a seasoned developer or just starting out. Before you start contributing, please take a moment to review these guidelines to ensure a smooth and efficient contribution process.

When making contributions to this project, please keep the following guidelines in mind:
- All contributions must be submitted under the [Apache License 2.0][licence].
- Contributions should be focused on improving the Core part of the project, so please do not make any changes to the Sample app.
- Before submitting a pull request, please ensure that your code is well-documented and easily readable.
- For your PR to be merged, you must agree to the [Contributor License Agreement (CLA)][cla].

## Getting Started

To get started with contributing, please follow these steps:
1. Fork the repository and clone it to your local machine.
2. Create a new branch for your contributions following the naming:
    - feature/{description},
    - bug/{description},
    - dependencies/{description},
    - other/{description}.
3. Make your changes and push them to your forked repository.
4. Create a pull request to merge your changes into the main repository using the [PR template][pr-template].

## Code Style

Code style configuration is included in the project, and for enforcing it we use [detekt][detekt].

Before pushing your code, please run the following command to to ensure your changes adhere to the code style guidelines:
```
./gradlew detekt
```

## Reporting Bugs

If you encounter a bug or issue, please open a new issue on the repository using the [Bug/Issue template][bug-template]. Ensure the bug was not already reported by searching on GitHub under [Issues][issues].

## Contact

If you have any questions or concerns about contributing to this project, please contact us at `android-support@htecgroup.com`.

We appreciate your contributions and look forward to working with you!

[licence]: https://github.com/htecgroup/android-core/blob/main/LICENSE
[cla]: https://github.com/htecgroup/android-core/blob/main/.github/CLA.md
[pr-template]: https://github.com/htecgroup/android-core/blob/main/.github/pull_request_template.md
[detekt]: https://github.com/detekt/detekt
[issues]: https://github.com/htecgroup/android-core/issues
[bug-template]: https://github.com/htecgroup/android-core/blob/main/.github/ISSUE_TEMPLATE/BUG_REPORT.yml