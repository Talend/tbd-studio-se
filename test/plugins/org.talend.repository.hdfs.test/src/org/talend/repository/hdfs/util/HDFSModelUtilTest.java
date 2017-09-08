// ============================================================================
//
// Copyright (C) 2006-2017 Talend Inc. - www.talend.com
//
// This source code is available under agreement available at
// %InstallDIR%\features\org.talend.rcp.branding.%PRODUCTNAME%\%PRODUCTNAME%license.txt
//
// You should have received a copy of the agreement
// along with this program; if not, write to Talend SA
// 9 rue Pages 92150 Suresnes, France
//
// ============================================================================
package org.talend.repository.hdfs.util;

import static org.junit.Assert.*;

import org.eclipse.core.runtime.Path;
import org.eclipse.emf.common.util.EList;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.talend.commons.exception.PersistenceException;
import org.talend.core.model.properties.ContextItem;
import org.talend.core.model.properties.ItemState;
import org.talend.core.model.properties.PropertiesFactory;
import org.talend.core.model.properties.Property;
import org.talend.core.model.repository.RepositoryObject;
import org.talend.core.repository.model.ProxyRepositoryFactory;
import org.talend.designer.core.model.utils.emf.talendfile.ContextType;
import org.talend.designer.core.model.utils.emf.talendfile.TalendFileFactory;
import org.talend.designer.hdfsbrowse.model.HDFSConnectionBean;
import org.talend.repository.model.hadoopcluster.HadoopClusterConnection;
import org.talend.repository.model.hadoopcluster.HadoopClusterConnectionItem;
import org.talend.repository.model.hadoopcluster.HadoopClusterFactory;
import org.talend.repository.model.hdfs.HDFSConnection;
import org.talend.repository.model.hdfs.HDFSFactory;

public class HDFSModelUtilTest {

	private ProxyRepositoryFactory factory;

	private HadoopClusterConnectionItem hcConnectionItem;

	private HadoopClusterConnection hcConnection;

	private HDFSConnection hdfsConnection;

	private ContextItem contextItem;

	private String contextItemId;

	private String hcItemId;

	@Before
	public void setUp() throws Exception {

		factory = ProxyRepositoryFactory.getInstance();

		createContextItem();

		createHadoopclusterConnectionItem();

		createHDFSConnection();
	}

	private void createHadoopclusterConnectionItem() throws PersistenceException {
		hcItemId = factory.getNextId();
		hcConnection = HadoopClusterFactory.eINSTANCE.createHadoopClusterConnection();
		hcConnection.setContextId(contextItemId);
		hcConnection.setContextMode(true);
		hcConnection.setContextName("DEV");

		Property property = PropertiesFactory.eINSTANCE.createProperty();
		property.setId(hcItemId);
		property.setLabel("hadoopcluster1");
		property.setVersion("0.1");

		ItemState itemState = PropertiesFactory.eINSTANCE.createItemState();
		itemState.setDeleted(false);
		itemState.setPath("");

		hcConnectionItem = HadoopClusterFactory.eINSTANCE.createHadoopClusterConnectionItem();
		hcConnectionItem.setConnection(hcConnection);
		hcConnectionItem.setProperty(property);
		hcConnectionItem.setState(itemState);

		factory.create(hcConnectionItem, new Path(""));
	}

	private void createHDFSConnection() {
		hdfsConnection = HDFSFactory.eINSTANCE.createHDFSConnection();
		hdfsConnection.setRelativeHadoopClusterId(hcItemId);
	}

	private void createContextItem() throws PersistenceException {
		contextItem = PropertiesFactory.eINSTANCE.createContextItem();
		contextItemId = factory.getNextId();

		Property property = PropertiesFactory.eINSTANCE.createProperty();
		property.setId(contextItemId);
		property.setLabel("context1");
		property.setVersion("0.1");

		ItemState itemState = PropertiesFactory.eINSTANCE.createItemState();
		itemState.setDeleted(false);
		itemState.setPath("");

		contextItem.setState(itemState);
		contextItem.setProperty(property);

		EList contexts = contextItem.getContext();
		ContextType dev = TalendFileFactory.eINSTANCE.createContextType();
		dev.setName("DEV");
		ContextType prod = TalendFileFactory.eINSTANCE.createContextType();
		prod.setName("PROD");
		contexts.add(dev);
		contexts.add(prod);

		contextItem.setDefaultContext("PROD");

		factory.create(contextItem, new Path(""));
	}

	@Test
	public void testConvert2HDFSConnectionBean() {
		HDFSConnectionBean connBean = HDFSModelUtil.convert2HDFSConnectionBean(hdfsConnection);
		assertEquals(contextItem.getDefaultContext(), connBean.getParentContextType().getName());
	}

	@After
	public void tearDown() throws PersistenceException {
		factory.deleteObjectPhysical(new RepositoryObject(contextItem.getProperty()));
		factory.deleteObjectPhysical(new RepositoryObject(hcConnectionItem.getProperty()));
	}

}
