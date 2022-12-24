# FCFS JobScheduling with I/O
This program is a scheduler that runs a list of processes using a First-Come-First-Served (FCFS) scheduling algorithm. The program takes a single command line argument, which is the name of a file containing the list of processes to be run. Each line in the file represents a process and has the following format:

```id: (burst1_cpu, burst1_io); (burst2_cpu, burst2_io); ... (burstN_cpu, burstN_io)```

# Requirements
Java SE Development Kit (JDK) 8 veya üstü

# Summary
This code reads transactions from a file and runs the transactions using a "First-Come, First-Served" (FCFS) scheduler. The planner calculates the waiting time and turnaround time for each operation and prints the results to the screen.

# Usage
1. Open and compile the code as a Java project.
2. When running the project, give a filename from the command line. For example:

``` java Scheduler jobs.txt ```
3. Waiting times and turnaround times of the transactions will be printed on the screen.

# File format
File represents one operation in one line. Each line is two sections separated by ":". The first part is an identifier of the process (not used in this code) and the second part is an array representing the load time of the process. For example:

``` 
1: (3, 4); (2, 1); (1, -1)
2: (2, 3); (1, -1)
3: (1, -1)
 ```
