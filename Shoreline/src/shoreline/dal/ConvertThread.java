/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shoreline.dal;

import shoreline.dal.ConvertFile;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import shoreline.be.Tasks;

/**
 *
 * @author Jesper Riis
 */
public class ConvertThread {

    public static Thread thread;

    private static final ExecutorService executor = Executors.newSingleThreadExecutor(new ThreadFactory() {
        public Thread newThread(Runnable runnable) {
            Thread thread = Executors.defaultThreadFactory().newThread(runnable);
            thread.setDaemon(true);
            return thread;
        }
    });

    public void convert(Tasks task) {
        executor.submit(new ConvertFile(task));
    }
}
