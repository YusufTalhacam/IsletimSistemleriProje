import java.io.IOException;
import java.util.*;

public class Commissioner {
    List<Process> processes;
    Queue<Process> realTimeQueue = new LinkedList<>();
    Queue<Process> userJobQueue = new LinkedList<>();
    Queue<Process> highPriorityQueue = new LinkedList<>();
    Queue<Process> mediumPriorityQueue = new LinkedList<>();
    Queue<Process> lowPriorityQueue = new LinkedList<>();
    Queue<Process> zamanAsimi = new LinkedList<>();

    Colors colors = new Colors();
    String renk;
    int silinenProsesSayısı = 0;

    public void mixedSorter(String filePath) throws IOException, InterruptedException {
        int currentTime = 0;
        processes = ProcessReader.readProcessesFromFile(filePath);

        while (silinenProsesSayısı != processes.size()) {
            for (int i = 0; i < processes.size(); i++) {

                if (processes.get(i).arrivalTime == currentTime) {
                    if (processes.get(i).priority == 0) {
                        realTimeQueue.add(processes.get(i));
                    } else {
                        if (processes.get(i).priority == 1) {
                            highPriorityQueue.add(processes.get(i));
                        } else if (processes.get(i).priority == 2) {
                            mediumPriorityQueue.add(processes.get(i));
                        } else {
                            lowPriorityQueue.add(processes.get(i));
                        };
                    }
                }
            }

            timeoutControl(currentTime);
        if (!realTimeQueue.isEmpty()) {
            FCFS(realTimeQueue.peek(), currentTime);
        } else if (!highPriorityQueue.isEmpty()) {
            highPriorityFeedBack(highPriorityQueue.peek(), currentTime);
        } else if (!mediumPriorityQueue.isEmpty()) {
            mediumPriorityFeedBack(mediumPriorityQueue.peek(), currentTime);
        } else {
            roundRobin(lowPriorityQueue.peek(), currentTime);
        }
        currentTime++;
        }
    }

    public void highPriorityFeedBack(Process userProcess, int currentTime) throws IOException, InterruptedException {
        userProcess.waitingTime = 0;
        renk = colors.Renkler(userProcess.procesId);
        if (userProcess.currentBurstTime == userProcess.burstTime) {
            System.out.println(
                    renk + currentTime + ".0000 sn "
                            + "Process basladi. ( id: " + userProcess.procesId
                            + "\tOncelik: " + userProcess.priority
                            + "\tkalan sure: " + userProcess.currentBurstTime + ")"
            );
            //Thread.sleep(500);
            userProcess.RuntimeExec(1);
            userProcess.currentBurstTime = userProcess.currentBurstTime - 1;

            if (userProcess.currentBurstTime == 0) {
                System.out.println(
                        renk + (currentTime + 1) + ".0000 sn "
                                + "Process sonlandi. ( id: " + userProcess.procesId
                                + "\tOncelik: " + userProcess.priority
                                + "\tkalan sure: " + userProcess.currentBurstTime + ")"
                );
                highPriorityQueue.poll();
                silinenProsesSayısı++;
            } else {
                if (userProcess.priority != 3) {
                    userProcess.priority = userProcess.priority + 1;
                }
                System.out.println(
                        renk + (currentTime + 1) + ".0000 sn "
                                + "Askida process (id: " + userProcess.procesId
                                + "\tOncelik: " + userProcess.priority
                                + "\tkalan sure: " + userProcess.currentBurstTime + ")"
                );
                mediumPriorityQueue.add(userProcess);
                highPriorityQueue.poll();
            }
        } else {
            System.out.println(
                    renk + currentTime + ".0000 sn "
                            + "Process calisiyor (id: " + userProcess.procesId
                            + "\tOncelik: " + userProcess.priority
                            + "\tKalan sure:" + userProcess.currentBurstTime + ")"
            );
            userProcess.currentBurstTime = userProcess.currentBurstTime - 1;
            userProcess.RuntimeExec(1);
            //Thread.sleep(500);
            if (userProcess.currentBurstTime == 0) {
           
                System.out.println(
                        renk + (currentTime + 1) + ".0000 sn "
                                + "Process sonlandi ( id: " + userProcess.procesId
                                + "\tOncelik: " + userProcess.priority
                                + "\tKalan sure: " + userProcess.currentBurstTime + ")"
                );

                highPriorityQueue.poll();
                silinenProsesSayısı++;
            } else {
           

                if (userProcess.priority != 3) {
                    userProcess.priority = userProcess.priority + 1;
                }
                System.out.println(
                        renk + (currentTime + 1) + ".0000 sn "
                                + "Askida process (id: " + userProcess.procesId
                                + "\tOncelik: " + userProcess.priority
                                + "\tKalan sure: " + userProcess.currentBurstTime + ")"
                );
                mediumPriorityQueue.add(userProcess);
                highPriorityQueue.poll();

            }
        }
    }

    public void mediumPriorityFeedBack(Process userProcess, int currentTime)throws IOException, InterruptedException {
        userProcess.waitingTime = 0;
        userProcess.RuntimeExec(1);
        renk = colors.Renkler(userProcess.procesId);
        if (userProcess.currentBurstTime == userProcess.burstTime) {
            System.out.println(
                    renk + currentTime + ".0000 sn "
                            + "Process basladi. ( id: " + userProcess.procesId
                            + "\tOncelik: " + userProcess.priority
                            + "\tkalan sure: " + userProcess.currentBurstTime + ")"
            );Thread.sleep(500);
            userProcess.currentBurstTime = userProcess.currentBurstTime - 1;
            //userProcess.RuntimeExec(1);
            if (userProcess.currentBurstTime == 0) {
                System.out.println(renk + (currentTime + 1) + ".0000 sn "
                        + "Process sonlandi. ( id: " + userProcess.procesId
                        + "\tOncelik: " + userProcess.priority
                        + "\tkalan sure: " + userProcess.currentBurstTime + ")"
                );
                mediumPriorityQueue.poll();
                silinenProsesSayısı++;
            } else {
                if (userProcess.priority != 3) {
                    userProcess.priority = userProcess.priority + 1;
                }
                System.out.println(
                        renk + (currentTime + 1) + ".0000 sn "
                                + "Askida process (id: " + userProcess.procesId
                                + "  Oncelik: " + userProcess.priority
                                + " kalan sure : " + userProcess.currentBurstTime + ")"
                );
                lowPriorityQueue.add(userProcess);
                mediumPriorityQueue.poll();
            }
        } else {
            System.out.println(
                    renk + currentTime + ".0000 sn "
                            + "Process calisiyor (id: " + userProcess.procesId
                            + "\tOncelik: " + userProcess.priority
                            + "\tKalan sure:" + userProcess.currentBurstTime + ")"
            );Thread.sleep(500);
            userProcess.currentBurstTime = userProcess.currentBurstTime - 1;
            //userProcess.RuntimeExec(1);
            if (userProcess.currentBurstTime == 0) {
                System.out.println(
                        renk + (currentTime + 1) + ".0000 sn "
                                + "Process sonlandi ( id: " + userProcess.procesId
                                + "\tOncelik: " + userProcess.priority
                                + "\tkalan sure: " + userProcess.currentBurstTime + ")"
                );

                mediumPriorityQueue.poll();
                silinenProsesSayısı++;
            } else {
                if (userProcess.priority != 3) {
                    userProcess.priority = userProcess.priority + 1;
                }
                System.out.println(
                        renk + (currentTime + 1) + ".0000 sn "
                                + "Askida process (id: " + userProcess.procesId
                                + "\tOncelik: " + userProcess.priority
                                + "\tKalan sure: " + userProcess.currentBurstTime + ")"
                );
                lowPriorityQueue.add(userProcess);
                mediumPriorityQueue.poll();

            }
        }
    }

    public void roundRobin(Process userProcess, int currentTime)throws IOException, InterruptedException {
        userProcess.waitingTime = 0;
        userProcess.RuntimeExec(1);
        renk = colors.Renkler(userProcess.procesId);
        if (userProcess.currentBurstTime == userProcess.burstTime) {
            System.out.println(
                    renk + currentTime + ".0000 sn "
                            + "Process basladi. ( id: " + userProcess.procesId
                            + "\tOncelik: " + userProcess.priority
                            + "\tkalan sure: " + userProcess.currentBurstTime + ")"
            );
            Thread.sleep(500);
            userProcess.currentBurstTime = userProcess.currentBurstTime - 1;
            //userProcess.RuntimeExec(1);
            if (userProcess.currentBurstTime == 0) {
                System.out.println(renk + (currentTime + 1) + ".0000 sn "
                        + "Process sonlandi. ( id: " + userProcess.procesId
                        + "\tOncelik: " + userProcess.priority
                        + "\tkalan sure: " + userProcess.currentBurstTime + ")"
                );
                mediumPriorityQueue.poll();
                silinenProsesSayısı++;
            } else {
                if (userProcess.priority != 3) {
                    userProcess.priority = userProcess.priority + 1;
                }
                System.out.println(
                        renk + (currentTime + 1) + ".0000 sn "
                                + "Askida process (id: " + userProcess.procesId
                                + "  Oncelik: " + userProcess.priority
                                + " kalan sure : " + userProcess.currentBurstTime + ")"
                );
                lowPriorityQueue.add(userProcess);
                lowPriorityQueue.poll();
            }
        } else {
            System.out.println(
                    renk + currentTime + ".0000 sn "
                            + "Process calisiyor (id: " + userProcess.procesId
                            + "\tOncelik: " + userProcess.priority
                            + "\tKalan sure:" + userProcess.currentBurstTime + ")"
            );
            Thread.sleep(500);
            userProcess.currentBurstTime = userProcess.currentBurstTime - 1;
            //userProcess.RuntimeExec(1);
            if (userProcess.currentBurstTime == 0) {
                System.out.println(
                        renk + (currentTime + 1) + ".0000 sn "
                                + "Process sonlandi ( id: " + userProcess.procesId
                                + "\tOncelik: " + userProcess.priority
                                + "\tkalan sure: " + userProcess.currentBurstTime + ")"
                );

                lowPriorityQueue.poll();
                silinenProsesSayısı++;
            } else {
                if (userProcess.priority != 3) {
                    userProcess.priority = userProcess.priority + 1;
                }
                System.out.println(
                        renk + (currentTime + 1) + ".0000 sn "
                                + "Askida process (id: " + userProcess.procesId
                                + "\tOncelik: " + userProcess.priority
                                + "\tKalan sure: " + userProcess.currentBurstTime + ")"
                );
                lowPriorityQueue.add(userProcess);
                lowPriorityQueue.poll();

            }
        }
    }

    public void FCFS(Process realTimeProcess, int currentTime)throws IOException, InterruptedException {
        realTimeProcess.waitingTime = 0;
        
        renk = colors.Renkler(realTimeProcess.procesId);
        if (realTimeProcess.currentBurstTime == realTimeProcess.burstTime) {
        	realTimeProcess.RuntimeExec(1);
            System.out.println(
                    renk + currentTime + ".0000 sn "
                            + "Process basladi. ( id: " + realTimeProcess.procesId
                            + "\tOncelik: " + realTimeProcess.priority
                            + "\tKalan sure: " + realTimeProcess.currentBurstTime + ")"
            );
            Thread.sleep(500);
            realTimeProcess.currentBurstTime = realTimeProcess.currentBurstTime - 1;
            if (realTimeProcess.currentBurstTime == 0) {
                System.out.println(
                        renk + (currentTime + 1) + ".0000 sn "
                                + "Process sonlandi. ( id: " + realTimeProcess.procesId
                                + "\tOncelik: " + realTimeProcess.priority
                                + "\tKalan sure: " + realTimeProcess.currentBurstTime + ")"
                );
                realTimeQueue.poll();
                silinenProsesSayısı++;
            }
        } else {
            System.out.println(
                    renk + currentTime + ".0000 sn "
                            + "Process calisiyor (id: " + realTimeProcess.procesId
                            + "\tOncelik: " + realTimeProcess.priority
                            + "\tkalan sure : " + realTimeProcess.currentBurstTime + ")"
            );
            Thread.sleep(500);
            realTimeProcess.currentBurstTime = realTimeProcess.currentBurstTime - 1;
            if (realTimeProcess.currentBurstTime == 0) {
                System.out.println(
                        renk + (currentTime + 1) + ".0000 sn "
                                + "Process sonlandi ( id: " + realTimeProcess.procesId
                                + "\tOncelik: " + realTimeProcess.priority
                                + "\tkalan sure: " + realTimeProcess.currentBurstTime + ")"
                );
                realTimeQueue.poll();
                silinenProsesSayısı++;

            }
        }
    }

    public void timeoutControl(int currentTime)throws InterruptedException {
        zamanAsimi.addAll(realTimeQueue);
        zamanAsimi.addAll(highPriorityQueue);
        zamanAsimi.addAll(mediumPriorityQueue);
        zamanAsimi.addAll(lowPriorityQueue);
        
        for (var item : zamanAsimi) {
        	renk = colors.Renkler(item.procesId);
            if (item.waitingTime != 20) {
                item.waitingTime++;
            } else {
                if (item.priority == 0) {
                	Thread.sleep(350);
                    realTimeQueue.remove(item);
                    System.out.println(
                            renk + currentTime + ".0000 sn "
                                    + "Process Zaman Asimi (id: " + item.procesId
                                    + "\tOncelik: " + item.priority
                                    + "\tkalan sure: " + item.currentBurstTime + ")"
                    );
                    silinenProsesSayısı++;
                } else if (item.priority == 1) {
                	Thread.sleep(350);
                    highPriorityQueue.remove(item);
                    System.out.println(
                            renk + currentTime + ".0000 sn "
                                    + "Process Zaman Asimi (id: " + item.procesId
                                    + "\tOncelik: " + item.priority
                                    + "\tkalan sure: " + item.currentBurstTime + ")"
                    );
                    silinenProsesSayısı++;
                } else if (item.priority == 2) {
                	Thread.sleep(350);
                    mediumPriorityQueue.remove(item);
                    System.out.println(
                            renk + currentTime + ".0000 sn "
                                    + "Process Zaman Asimi (id: " + item.procesId
                                    + "\tOncelik: " + item.priority
                                    + "\tkalan sure: " + item.currentBurstTime + ")"
                    );
                    silinenProsesSayısı++;
                } else if (item.priority == 3) {
                	Thread.sleep(350);
                    lowPriorityQueue.remove(item);
                    System.out.println(
                            renk + currentTime + ".0000 sn "
                                    + "Process Zaman Asimi (id: " + item.procesId
                                    + "\tOncelik: " + item.priority
                                    + "\tkalan sure: " + item.currentBurstTime + ")"
                    );
                    silinenProsesSayısı++;
                }
            }
        }
        zamanAsimi.clear();
        
    }

}
