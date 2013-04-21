
/*
* To change this template, choose Tools | Templates
* and open the template in the editor.
 */
package com.agrologic.web;

//~--- JDK imports ------------------------------------------------------------

import java.io.Serializable;

/**
 * {Insert class description here}
 *
 * @version $Revision: 1.1.1.1 $
 * @since Build {insert version here} (MM YYYY)
 * @author Valery Manakhimov
 * @author $Author: nbweb $, (this version)
 */
public class TaskBean implements Runnable, Serializable {
    private int     counter;
    private boolean running;
    private int     sleep;
    private boolean started;
    private int     sum;

    public TaskBean() {
        counter = 0;
        sum     = 0;
        started = false;
        running = false;
        sleep   = 100;
    }

    protected void work() {
        try {
            Thread.sleep(sleep);
            counter++;
            sum += counter;
        } catch (InterruptedException e) {
            setRunning(false);
        }
    }

    public synchronized int getPercent() {
        return counter;
    }

    public synchronized boolean isStarted() {
        return started;
    }

    public synchronized boolean isCompleted() {
        return counter == 100;
    }

    public synchronized boolean isRunning() {
        return running;
    }

    public synchronized void setRunning(boolean running) {
        this.running = running;

        if (running) {
            started = true;
        }
    }

    public synchronized Object getResult() {
        if (isCompleted()) {
            return new Integer(sum);
        } else {
            return null;
        }
    }

    public void run() {
        try {
            setRunning(true);

            while (isRunning() &&!isCompleted()) {
                work();
            }
        } finally {
            setRunning(false);
        }
    }
}


//~ Formatted by Jindent --- http://www.jindent.com
