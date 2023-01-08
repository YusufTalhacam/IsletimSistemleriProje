import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class Process {
    public int procesId;
    public int priority;
    public int burstTime;
    public int currentBurstTime;
    public int arrivalTime;
    public int waitingTime;
    public Process(int id, int priority, int burstTime,int arrivalTime) {
        this.procesId = id;
        this.priority = priority;
        this.burstTime = burstTime;
        this.currentBurstTime = burstTime;
        this.arrivalTime=arrivalTime;
        this.waitingTime=0;
    }
    public void RuntimeExec (int time) throws IOException, InterruptedException {

        Runtime r = Runtime.getRuntime();
        java.lang.Process p = r.exec("notepad.exe");
        p.waitFor(time, TimeUnit.SECONDS);
        p.destroy();
    }
    @Override
    public String toString() {
        String toString="Process: " +procesId + " (priority: " + priority
                + ", burst time: " + burstTime + " Arrival Time: "+arrivalTime;
        return toString;
    }
}
