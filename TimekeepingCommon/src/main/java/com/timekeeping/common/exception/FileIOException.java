package com.timekeeping.common.exception;

import java.io.Serializable;

/**
 * @author minhtq2 on 25/10/2023
 * @project TimeKeeping
 */
public class FileIOException extends Exception implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 2962209147503143449L;

    public FileIOException(final String message) {
        super(message);
    }
}
