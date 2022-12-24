/**
 * @author Necati Boga - 20170808048
 * @since  24/12/2022
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FCFSJobSchedulingwithIO {

  public static void main(String[] args) throws FileNotFoundException {
    // Check the number of command line arguments
    if (args.length != 1) {
      System.out.println("Usage: java Scheduler input file");
      return;
    }

    // Read the input file
    String filename = args[0];
    ArrayList<Process> processes = loadProcesses(filename);
    

    // Create the scheduler and run the processes
    FCFSScheduler scheduler = new FCFSScheduler(processes);
    scheduler.run();

    // Print the results
    scheduler.printResults();
  }

  private static ArrayList<FCFSJobSchedulingwithIO.Process> loadProcesses(String filename) throws FileNotFoundException {
    ArrayList<Process> processes = new ArrayList<>();
  Scanner scanner = new Scanner(new File(filename));
  while (scanner.hasNextLine()) {
    String line = scanner.nextLine();
    String[] parts = line.split(":");
    try {
      int id = Integer.parseInt(parts[0]);
      String[] burstStrings = parts[1].split(";");
      int[][] bursts = new int[burstStrings.length][2];
      for (int i = 0; i < burstStrings.length; i++) {
        String[] burst = burstStrings[i].replace("(", "").replace(")", "").split(",");
        bursts[i][0] = Integer.parseInt(burst[0]);
        bursts[i][1] = Integer.parseInt(burst[1]);
      }
      Process p = new Process(id, bursts);
      processes.add(p);
    } catch (NumberFormatException e) {
      System.out.println("Hatalı veri: " + parts[0]);
    }
  }
  scanner.close();
  return processes;
  }

  static class Process {
    String id;
    int pid;
    int[][] bursts;
    int waitTime;
    int turnaroundTime;

    Process(int pid, int[][] bursts) {
      this.pid = pid;
      this.id = id;
      this.bursts = bursts;
      this.waitTime = 0;
      this.turnaroundTime = 0;
    }
  }

  static class FCFSScheduler {
    List<Process> processes;
    int numProcesses;
    int numIdle;
  
    FCFSScheduler(List<Process> processes) {
      this.processes = processes;
      this.numProcesses = processes.size();
      this.numIdle = 0;
    }
  
    void run() {
      int time = 0;
      int currentProcess = 0;
      int numCompleted = 0;
      while (numCompleted < numProcesses) {
        Process p = processes.get(currentProcess);
  if (p.turnaroundTime >= p.bursts.length) {
    // The process has completed
    numCompleted += 1;
    currentProcess = (currentProcess + 1) % numProcesses;
    continue;
  }
  int[] burst = p.bursts[p.turnaroundTime];
  if (burst[1] == -1) {
    // The process has completed
    numCompleted += 1;
  } else {
    // The process needs I/O
    time += burst[1];
    p.waitTime += time - burst[1];
  }
  time += burst[0];
  p.turnaroundTime += 1;
  if (p.id != "IDLE") {
    // The process is not IDLE, increment the current process index
    currentProcess = (currentProcess + 1) % numProcesses;
  }
  if (currentProcess == 0) {
    // All processes have been processed once, increment time
    time += 1;
    numIdle += 1;
  }
      }
    }
    
  
    void printResults() {
      int totalWaitTime = 0;
      int totalTurnaroundTime = 0;
      for (Process p : processes) {
        totalWaitTime += p.waitTime;
        totalTurnaroundTime += p.turnaroundTime;
      }
      System.out.println("Average wait time: " + (double) totalWaitTime / numProcesses);
      System.out.println("Average turnaround time: " + (double) totalTurnaroundTime / numProcesses);
      System.out.println("Number of times the CPU was idle: " + numIdle);
      System.out.println("HALT");
    }
  }
}  
