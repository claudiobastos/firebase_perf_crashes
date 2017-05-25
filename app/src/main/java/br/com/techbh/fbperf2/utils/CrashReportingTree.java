package br.com.techbh.fbperf2.utils;

/**
 * @author Claudio Bastos
 * @since 11/11/2016
 */

import android.util.Log;

import com.google.firebase.crash.FirebaseCrash;

import timber.log.BuildConfig;
import timber.log.Timber;

/** A tree which logs important information for crash reporting on {@link ???}. */
public class CrashReportingTree extends Timber.Tree {

    /**
     *
     * @param priority  priority of message : error, warning, verbose, debug, information
     * @param tag       indentify the part of system where events occur
     * @param message   message to log
     * @param t         exception that occur
     */
    @Override
    protected void log(int priority, String tag, String message, Throwable t) {

        if(!BuildConfig.DEBUG){
            if (priority == Log.VERBOSE || priority == Log.DEBUG) {
                return;
            }
        }

        if(message != null) {
            FirebaseCrash.log(message);
        }
        if(t != null){
            FirebaseCrash.report(t);
        }

    }
}