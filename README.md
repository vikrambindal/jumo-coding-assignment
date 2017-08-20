# jumo-coding-assignment
Project: Every two months, obtain Loan information and process the file to obtain aggregated information of the loan amount and the number of loans by a tuple comprising of Network,Product and Month. 

Usage: Place multiple files obtained inside the loans folder in src/resources/loans and run the application from main file (Application.java). The program would then output the relevant information in the Output.csv file in the root folder.
NOTE: Lombok plugin would have to be setup in the relevant IDE.

Assumptions: Following assumptions are made
1. TUPLE: Since file is provided every two months, there may be chances that historic data be present for months belonging to current and next year. Doing a tuple only on month, would present incorrect aggregate results. As a result, the tuple comprises of Network, Product, Month-Year to provide correct statistics.
2. TEMPLATE: The template of the file would always follow pattern specified in the sample csv of Msidn, Network, Month, Product and Amount
3. ARCHITECTURE SIMPLICITY: For the nature of the project and to keep review simple, complex batch operations (like chunking, multi-tasking) are not implemented. However, through configuration concurrency can be implemented.

Technology:
1. LANGUAGE: The program is written in Java using version 8
2. FRAMEWORK: Spring-Batch framework is used for batch processing of csv files. (Implementation provided is simple, but it offers benefit of maintainability, extensibility, configurability for enhancing application)
3. LIBS: 
a. Lombok plugin is use within the Elipse IDE and the project, due to it's nature of providing clean code in the pojo's removing boiler plate. (See https://projectlombok.org/setup/eclipse or project page for IDE installation)
b. The program also uses apache IO for writing to CSV

Performance Considerations:
1. MULTIPLE FILES: Multiple csv files can be read and processed together, in chunks.  (This is easily extendible to concurrent processing through mere change of configuration rather than a typical code change). 
2. MEMORY: Due to the nature of the batch operation, memory foot-prints would not overflow, leak in resources is maintained, and if desired through change of configuration, application's performance can be adjusted by adjusting the chunk size. 
3. CONCURRENCY CONFIGURATION: Achieving concurrency is mere through a configuration rather a full blown code change.

Scalability:
1. CONTAINERIZATION: The application, can be further bundled into a containerized application using Docker
2. ORCHESTRATION: Through DevOps, scaling can be achieved by net result of using container orchestration, load balancer.
3. SYSTEM ENHANCEMENT: The choice of Spring framework for the application makes it easy to integrated with other tools in the Spring framework that provide integration options for plethora of systems.
4. ARCHITECTURE: On its own, it can also serve as a micro-service in the global system which can be used by other services within the companie's eco-system.

Quality Control:
1. Unit testing is performed with tests in the test folder
2. LIBS: JUnit, Mockito, Hamcrest


