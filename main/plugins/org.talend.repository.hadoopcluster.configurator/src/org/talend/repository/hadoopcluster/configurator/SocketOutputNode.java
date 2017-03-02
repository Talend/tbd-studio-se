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
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.SocketAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.talend.commons.exception.ExceptionHandler;
import org.talend.commons.exception.SystemException;
import org.talend.core.CorePlugin;
import org.talend.core.model.components.ComponentCategory;
import org.talend.core.model.components.IComponentsFactory;
import org.talend.core.model.components.IODataComponent;
import org.talend.core.model.process.AbstractNode;
import org.talend.core.model.process.IElementParameter;
import org.talend.core.model.process.INode;
import org.talend.core.model.runprocess.shadow.TextElementParameter;
import org.talend.core.model.temp.ECodePart;
import org.talend.core.model.utils.ContextParameterUtils;
import org.talend.core.model.utils.TalendTextUtils;
import org.talend.designer.codegen.ICodeGeneratorService;

public class SocketOutputNode extends AbstractNode {

    private static final Random RANDOM = new Random();

    private static final String SOCKETOUT = "tSocketOutput"; //$NON-NLS-1$
    
    private static final int defaultPort = 6666;

    /**
     * qzhang LogRowNode constructor comment.
     */
    public SocketOutputNode() {

        super();

        this.setElementParameters(new ArrayList<IElementParameter>());

        IComponentsFactory compFac = CorePlugin.getDefault().getRepositoryService().getComponentsFactory();
        setComponent(compFac.get(SOCKETOUT, ComponentCategory.CATEGORY_4_DI.getName()));
        setUniqueName(getComponentName() + "_gen");

        TextElementParameter param = new TextElementParameter("UNIQUE_NAME", getUniqueName()); //$NON-NLS-1$
        ((List<IElementParameter>) this.getElementParameters()).add(param);

        int port = defaultPort;
        try {
            port = findUnusedPort(InetAddress.getByName("127.0.0.1"), 40000, 50000); //$NON-NLS-1$
        } catch (UnknownHostException e) {
            port = defaultPort;
            // e.printStackTrace();
            ExceptionHandler.process(e);
        }

        if (isJavaProject()) {
            param = new TextElementParameter("PORT", "" + port + ""); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
        } else {
            param = new TextElementParameter("PORT", "" + port + ""); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
        }
        this.addParameter(param);

        if (isJavaProject()) {
            param = new TextElementParameter("ROWSEPARATOR", "\"\\n\""); //$NON-NLS-1$ //$NON-NLS-2$
        } else {
            param = new TextElementParameter("ROWSEPARATOR", "\'\\n\'"); //$NON-NLS-1$ //$NON-NLS-2$
        }
        this.addParameter(param);

        if (isJavaProject()) {
            param = new TextElementParameter("FIELDSEPARATOR", "\";\""); //$NON-NLS-1$ //$NON-NLS-2$
        } else {
            param = new TextElementParameter("FIELDSEPARATOR", "\';\'"); //$NON-NLS-1$ //$NON-NLS-2$
        }
        this.addParameter(param);

        if (isJavaProject()) {
            param = new TextElementParameter("ESCAPE_CHAR", TalendTextUtils.addQuotes("\\\\")); //$NON-NLS-1$ //$NON-NLS-2$
        } else {
            param = new TextElementParameter("ESCAPE_CHAR", "\'\"\'"); //$NON-NLS-1$ //$NON-NLS-2$
        }
        this.addParameter(param);

        if (isJavaProject()) {
            param = new TextElementParameter("TEXT_ENCLOSURE", "\"\"\""); //$NON-NLS-1$ //$NON-NLS-2$
        } else {
            param = new TextElementParameter("TEXT_ENCLOSURE", "\'\"\'"); //$NON-NLS-1$ //$NON-NLS-2$
        }
        this.addParameter(param);

        if (isJavaProject()) {
            param = new TextElementParameter("HOST", "\"127.0.0.1\""); //$NON-NLS-1$ //$NON-NLS-2$
        } else {
            param = new TextElementParameter("HOST", "\'127.0.0.1\'"); //$NON-NLS-1$ //$NON-NLS-2$
        }
        this.addParameter(param);

        if (isJavaProject()) {
            param = new TextElementParameter("TIMEOUT", "1000"); //$NON-NLS-1$ //$NON-NLS-2$
        } else {
            param = new TextElementParameter("TIMEOUT", "1000"); //$NON-NLS-1$ //$NON-NLS-2$
        }
        this.addParameter(param);

        if (isJavaProject()) {
            param = new TextElementParameter("RETRY", "10"); //$NON-NLS-1$ //$NON-NLS-2$
        } else {
            param = new TextElementParameter("RETRY", "10"); //$NON-NLS-1$ //$NON-NLS-2$
        }
        this.addParameter(param);
    }

    public void addEncodingParameters(String encoding) {

        TextElementParameter param;
        boolean isContextValue = ContextParameterUtils.isContainContextParam(encoding);
        if (!isContextValue) {
            encoding = TalendTextUtils.addQuotes(encoding);
        }
        param = new TextElementParameter("ENCODING", encoding); //$NON-NLS-1$ 

        this.addParameter(param);
    }

    protected void addParameter(IElementParameter param) {
        ((List<IElementParameter>) getElementParameters()).add(param);
    }

    /*
     * (non-Java)
     * 
     * @see org.talend.core.model.process.INode#getGeneratedCode()
     */
    public String getGeneratedCode() {
        String generatedCode;
        try {
            ICodeGeneratorService service = CorePlugin.getDefault().getCodeGeneratorService();
            generatedCode = service.createCodeGenerator().generateComponentCode(this, ECodePart.MAIN);
        } catch (SystemException e) {
            generatedCode = null;
        }
        return generatedCode;
    }

    @Override
    public String getComponentName() {
        return SOCKETOUT;
    }

    /*
     * (non-Java)
     * 
     * @see org.talend.core.model.process.INode#renameMetadataColumnName(java.lang.String, java.lang.String,
     * java.lang.String)
     */
    @Override
    public void metadataInputChanged(IODataComponent dataComponent, String connectionToApply) {
        // Nothing to do as it's shadow node
    }

    @Override
    public void metadataOutputChanged(IODataComponent dataComponent, String connectionToApply) {
        // Nothing to do as it's shadow node
    }

    @Override
    public boolean isReadOnly() {
        return false;
    }

    @Override
    public boolean isThereLinkWithMerge() {
        return false;
    }

    @Override
    public Map<INode, Integer> getLinkedMergeInfo() {
        return null;
    }

    private static int findUnusedPort(InetAddress address, int from, int to) {
        for (int i = 0; i < 12; i++) {
            ServerSocket ss = null;
            int port = getRandomPort(from, to);
            try {
                ss = new ServerSocket();
                SocketAddress sa = new InetSocketAddress(address, port);
                ss.bind(sa);
                return ss.getLocalPort();
            } catch (IOException e) {
            } finally {
                if (ss != null) {
                    try {
                        ss.close();
                    } catch (IOException ioe) {
                    }
                }
            }
        }
        return -1;
    }

    private static int getRandomPort(int low, int high) {
        return (int) (RANDOM.nextFloat() * (high - low)) + low;
    }
    
    private boolean isJavaProject() {
        return true;
    }

 }
