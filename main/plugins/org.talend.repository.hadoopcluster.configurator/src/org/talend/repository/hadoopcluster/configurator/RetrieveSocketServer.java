// ============================================================================
//
// Copyright (C) 2006-2016 Talend Inc. - www.talend.com
//
// This source code is available under agreement available at
// %InstallDIR%\features\org.talend.rcp.branding.%PRODUCTNAME%\%PRODUCTNAME%license.txt
//
// You should have received a copy of the agreement
// along with this program; if not, write to Talend SA
// 9 rue Pages 92150 Suresnes, France
//
// ============================================================================
package org.talend.repository.hadoopcluster.configurator;

import java.io.IOException;

import org.talend.commons.exception.ExceptionHandler;

public class RetrieveSocketServer extends Thread {

    private String ecnoding = null;

    private boolean donetSocketInput = true;

    private boolean isNeedTimeout = true;
    
    private static final int defaultPort = 6667;

    public RetrieveSocketServer() {

    }

    public static void main(String[] args) {
        new RetrieveSocketServer().start();
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Thread#run()
     */
    @Override
    public void run() {
        tSocketInputProcess();
        super.run();
    }

    public boolean isNeedTimeout() {
        return this.isNeedTimeout;
    }

    public void setNeedTimeout(boolean isNeedTimeout) {
        this.isNeedTimeout = isNeedTimeout;
    }

    public void tSocketInputProcess() {

        java.net.ServerSocket dataSocketServer = null;

        java.net.Socket dataSocketClient = null;

        try {
            donetSocketInput = true;

            int port = defaultPort;

            dataSocketServer = new java.net.ServerSocket(port);
            if (isNeedTimeout) {
                dataSocketServer.setSoTimeout(30000);
            }
            while (donetSocketInput) {
                dataSocketClient = dataSocketServer.accept();

                if (dataSocketClient.getInetAddress().getHostAddress().equals("127.0.0.1")) { //$NON-NLS-1$
                    String encoding = getEcnoding();
                    String jvmEncoding = System.getProperty("file.encoding");
                    if (jvmEncoding != null) {
                        encoding = jvmEncoding;
                    }
                    //TODO --KK
//                    CsvArray array = new CsvArray();
//                    array = array.createFrom(dataSocketClient.getInputStream(), encoding);
//                    rows = array.getRows();
//                    nbLine = rows.size();

                    dataSocketClient.close();

                    donetSocketInput = false;
                }
            }

            dataSocketServer.close();
        } catch (final Exception e) {
        } finally {
            if (dataSocketServer != null && !dataSocketServer.isClosed()) {
                try {
                    dataSocketServer.close();
                } catch (IOException e) {
                    ExceptionHandler.process(e);
                }
            }

            if (dataSocketClient != null && !dataSocketClient.isClosed()) {
                try {
                    dataSocketClient.close();
                } catch (IOException e) {
                    ExceptionHandler.process(e);
                }
            }

            donetSocketInput = false;
        }
    }

    public boolean isDonetSocketInput() {
        return this.donetSocketInput;
    }

    public String getEcnoding() {
        return this.ecnoding;
    }

    public void setEcnoding(String ecnoding) {
        this.ecnoding = ecnoding;
    }

    public void cancel() {
        donetSocketInput = false;
    }

}
