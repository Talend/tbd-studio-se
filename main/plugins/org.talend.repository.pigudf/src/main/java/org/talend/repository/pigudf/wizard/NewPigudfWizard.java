// ============================================================================
//
// Copyright (C) 2006-2018 Talend Inc. - www.talend.com
//
// This source code is available under agreement available at
// %InstallDIR%\features\org.talend.rcp.branding.%PRODUCTNAME%\%PRODUCTNAME%license.txt
//
// You should have received a copy of the agreement
// along with this program; if not, write to Talend SA
// 9 rue Pages 92150 Suresnes, France
//
// ============================================================================
package org.talend.repository.pigudf.wizard;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.IPath;
import org.eclipse.jface.wizard.Wizard;
import org.talend.commons.exception.PersistenceException;
import org.talend.commons.ui.runtime.exception.ExceptionHandler;
import org.talend.commons.ui.runtime.exception.RuntimeExceptionHandler;
import org.talend.commons.ui.runtime.image.ECoreImage;
import org.talend.commons.ui.runtime.image.ImageProvider;
import org.talend.commons.utils.VersionUtils;
import org.talend.core.CorePlugin;
import org.talend.core.GlobalServiceRegister;
import org.talend.core.ILibraryManagerService;
import org.talend.core.context.Context;
import org.talend.core.context.RepositoryContext;
import org.talend.core.model.general.ILibrariesService;
import org.talend.core.model.properties.ByteArray;
import org.talend.core.model.properties.PigudfItem;
import org.talend.core.model.properties.PropertiesFactory;
import org.talend.core.model.properties.Property;
import org.talend.core.repository.model.ProxyRepositoryFactory;
import org.talend.designer.core.model.utils.emf.component.ComponentFactory;
import org.talend.designer.core.model.utils.emf.component.IMPORTType;
import org.talend.repository.model.IProxyRepositoryFactory;
import org.talend.repository.pigudf.i18n.Messages;

/**
 * Wizard for the creation of a new project. <br/>
 * 
 * $Id: NewPigudfWizard.java 914 2006-12-08 08:28:53 +0000 (ven., 08 déc. 2006) bqian $
 * 
 */
public class NewPigudfWizard extends Wizard {

    /** Main page. */
    private NewPigudfWizardPage mainPage;

    /** Created project. */
    private PigudfItem pigUDFItem;

    private Property property;

    private IPath path;

    /**
     * Constructs a new NewProjectWizard.
     * 
     * @param author Project author.
     * @param server
     * @param password
     */
    public NewPigudfWizard(IPath path) {
        super();
        this.path = path;

        this.property = PropertiesFactory.eINSTANCE.createProperty();
        this.property.setAuthor(((RepositoryContext) CorePlugin.getContext().getProperty(Context.REPOSITORY_CONTEXT_KEY))
                .getUser());
        this.property.setVersion(VersionUtils.DEFAULT_VERSION);
        this.property.setStatusCode(""); //$NON-NLS-1$

        pigUDFItem = PropertiesFactory.eINSTANCE.createPigudfItem();

        pigUDFItem.setProperty(property);

    }

    /**
     * @see org.eclipse.jface.wizard.Wizard#addPages()
     */
    @Override
    public void addPages() {
        mainPage = new NewPigudfWizardPage(property, path);
        addPage(mainPage);
        setWindowTitle(Messages.getString("NewPigudfWizard.title")); //$NON-NLS-1$
        setDefaultPageImageDescriptor(ImageProvider.getImageDesc(ECoreImage.ROUTINE_WIZ));
    }

    /**
     * @see org.eclipse.jface.wizard.Wizard#performFinish()
     */
    @Override
    public boolean performFinish() {
        IProxyRepositoryFactory repositoryFactory = ProxyRepositoryFactory.getInstance();
        try {
            ILibrariesService service = CorePlugin.getDefault().getLibrariesService();
            URL templateUrl = service.getPigudfTemplate(mainPage.getSelectedTemplate());
            ByteArray byteArray = PropertiesFactory.eINSTANCE.createByteArray();
            InputStream stream = null;
            try {
                stream = templateUrl.openStream();
                byte[] innerContent = new byte[stream.available()];
                stream.read(innerContent);
                stream.close();
                byteArray.setInnerContent(innerContent);
            } catch (IOException e) {
                RuntimeExceptionHandler.process(e);
            }

            pigUDFItem.setContent(byteArray);
            updateItemContent();
            property.setId(repositoryFactory.getNextId());
            property.setLabel(property.getDisplayName());

            // set a default pig version from org.talend.libraries.pig
            IMPORTType type = ComponentFactory.eINSTANCE.createIMPORTType();
            String jarName = "pig-0.10.0.jar";//$NON-NLS-1$
            String relativePath = "platform:/plugin/org.talend.libraries.pig/lib/" + jarName; //$NON-NLS-1$
            URI uri = new URI(relativePath);
            ILibraryManagerService librairesService = (ILibraryManagerService) GlobalServiceRegister.getDefault().getService(
                    ILibraryManagerService.class);
            // try to get from local library
            URL url = FileLocator.toFileURL(uri.toURL());
            File file = new File(url.getFile());
            if (file.exists()) {
                CorePlugin.getDefault().getLibrariesService().deployLibrary(file.toURL());
            }

            type.setMODULE(jarName);
            type.setREQUIRED(true);
            type.setUrlPath(librairesService.getJarPath(jarName));
            pigUDFItem.getImports().add(type);
            repositoryFactory.create(pigUDFItem, mainPage.getDestinationPath());
            CorePlugin.getDefault().getRunProcessService().updateLibraries(pigUDFItem);
        } catch (PersistenceException e) {
            ExceptionHandler.process(e);
        } catch (URISyntaxException e) {
            ExceptionHandler.process(e);
        } catch (MalformedURLException e) {
            ExceptionHandler.process(e);
        } catch (IOException e) {
            ExceptionHandler.process(e);
        }

        return pigUDFItem != null;
    }

    /**
     * Getter for project.
     * 
     * @return the project
     */
    public PigudfItem getPigudf() {
        return this.pigUDFItem;
    }

    private void updateItemContent() {
        if (pigUDFItem == null) {
            return;
        }
        ByteArray content = pigUDFItem.getContent();
        if (content != null) {
            String routineContent = new String(content.getInnerContent());
            String label = pigUDFItem.getProperty().getLabel();
            String fileName = mainPage.getSelectedTemplate().getFileName();
            if (routineContent != null && !label.equals(fileName)) {
                routineContent = routineContent.replaceAll(fileName, label);
                content.setInnerContent(routineContent.getBytes());
            }
        }
    }
}
