# jumo-coding-assignment
Project: Every two months, obtain Loan information and process the file to obtain aggregated information of the loan amount and the number of loans by a tuple comprising of Network,Product and Month. 

Usage: Place multiple files obtained inside the loans folder in src/resources/loans and run the application from main file (Application.java). The program would then output the relevant information in the Output.csv file in the root folder.

Assumptions: Following assumptions are made
1. Since file is provided every two months, there may be chances that historic data for month might contain for both current and next year, as a result, the tuple for month is composed of Month and Year together to provide correct statistics
2. The template of the file would always follow pattern specified in the sample csv of Msidn, Network, Month, Product and Amount
3. For the nature of the project and to keep simplicity, complex batch operations (like chunking, multi-tasking) are not implemented. However, application architecture is in place such that, if needed it can be configured to tackle concurrency.

Technology:
1. The program is written in Java using version 8
2. The third party libraries compose of using the Spring Batch framework (for maintainability, extensibility, configurability)
3. Lombok plugin is used within the Elipse IDE and the project, due to it's nature of providing clean code in the application, with unecessary boiler plate. (See https://projectlombok.org/setup/eclipse or project page for IDE installation)
4. The program also uses apache IO for writing to CSV

Performance Considerations:
1. Multiple csv files can be read and processed together, in chunks.  (This is easily extendible to concurrent processing through mere change of configuration rather than a typical code change). 
2. Due to the nature of the batch operation, memory foot-prints would not overflow, leak in resources is maintained, and if desired through change of configuration, application's performance can be adjusted by adjusting the chunk size. 
3. Achieving concurrency is mere through a configuration rather a full blown code change.

Scalability:
1. The application, can be further bundled into a containerized application using Docker
2. Through DevOps, scaling can be achieved by net result of using container orchestration, load balancer.
3. The choice of Spring framework for the application makes it easy to integrated with other tools in the Spring framework that provide integration options for plethora of systems.
4. On its own, it can also serve as a micro-service which can be used by other services within the companie's eco-system.

Quality Control:
1. Unit testing is performed with tests in test folder


