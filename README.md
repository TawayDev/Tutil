- **Runtime Configuration**: Allows for project-wide configuration changes with ease and consistency.

- **Logging**: An efficient logging class that fulfills essential logging needs.

- **IO (Input/Output)**: Robust file and directory handling along with easy-to-use JSON conversion utilities.
    - **File Management**: Each file is wrapped into an easily manipulatable object.
    - **Directory Management**: Directories are also handled as objects, providing simplicity and efficiency.
    - **JSON Handling**: Hassle-free conversions to and from JSON files for quick handling of data.

- **NET**: Provides an intuitive interface for sending network requests.
    - **Sending requests**: Utilizes a RequestObject for sending network requests. Responses are encapsulated into a ResponseObject for simplicity.

- **TIME**: Offers convenient tools for time formatting and measurement.
    - **Time Formatting**: Converts timestamps into more human-friendly formats.
    - **Stopwatch**: A simple yet effective stopwatch tool for time-tracking needs.

- **SORTING**: Currently provides a Bubble Sort implementation for sorting numeric data.

- **CRYPTO**:
    - **Caesar Cipher**: Simple symmetric encryption ... NO! I'm not thinking about the Roman Empire on a daily basis why do you ask?
  - **RSA**: An implementation of the RSA asymmetric encryption algorithm.

## Download
Add this to your pom.xml (Maven). The repo is being hosted at [repsy.io](https://repsy.io/) so you may need to add it to maven repos.
```pom
<dependency>
  <groupId>dev.taway</groupId>
  <artifactId>Tutil</artifactId>
  <version>0.1.8</version>
</dependency>
```