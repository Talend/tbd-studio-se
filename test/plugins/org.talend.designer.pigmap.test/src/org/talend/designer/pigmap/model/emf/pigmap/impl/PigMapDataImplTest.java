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
package org.talend.designer.pigmap.model.emf.pigmap.impl;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.talend.designer.pigmap.model.emf.pigmap.InputTable;
import org.talend.designer.pigmap.model.emf.pigmap.OutputTable;
import org.talend.designer.pigmap.model.emf.pigmap.PigmapFactory;
import org.talend.designer.pigmap.model.emf.pigmap.TableNode;
import org.talend.designer.pigmap.model.emf.pigmap.VarTable;

/**
 * DOC hwang  class global comment. Detailled comment
 */
public class PigMapDataImplTest {
    
    @Test
    public void testEquals() {
        test1();
        test2();
        test3();
        test4();
        test5();
        test6();
        test7();
    }

    private void test1(){
        PigMapDataImpl mapData1 = new PigMapDataImpl();
        PigMapDataImpl mapData2 = new PigMapDataImpl();
        assertTrue(mapData1.equals(mapData2));
        
        final InputTable inputTable = PigmapFactory.eINSTANCE.createInputTable();
        inputTable.setName("name1");
        inputTable.setLookup(false);
        inputTable.setExpressionFilter("filter");
        inputTable.setActivateCondensedTool(true);
        inputTable.setActivateExpressionFilter(true);
        inputTable.setCustomPartitioner("custom");
        inputTable.setIncreaseParallelism("increase");
        inputTable.setJoinModel("join");
        inputTable.setJoinOptimization("optimization");
        inputTable.setMinimized(false);
        
        mapData1.getInputTables().add(inputTable);
        
        final InputTable inputTable2 = PigmapFactory.eINSTANCE.createInputTable();
        inputTable2.setName("name1");
        inputTable2.setLookup(false);
        inputTable2.setExpressionFilter("filter");
        inputTable2.setActivateCondensedTool(true);
        inputTable2.setActivateExpressionFilter(true);
        inputTable2.setCustomPartitioner("custom");
        inputTable2.setIncreaseParallelism("increase");
        inputTable2.setJoinModel("join");
        inputTable2.setJoinOptimization("optimization");
        inputTable2.setMinimized(false);
        
        mapData2.getInputTables().add(inputTable2);
        assertTrue(mapData1.equals(mapData2));
    }
    
    private void test2(){
        PigMapDataImpl mapData1 = new PigMapDataImpl();
        PigMapDataImpl mapData2 = new PigMapDataImpl();
        mapData1.equals(mapData2);
        
        final InputTable inputTable = PigmapFactory.eINSTANCE.createInputTable();
        inputTable.setName("name1");
        inputTable.setLookup(false);
        inputTable.setExpressionFilter("filter");
        inputTable.setActivateCondensedTool(true);
        inputTable.setActivateExpressionFilter(true);
        inputTable.setCustomPartitioner("custom");
        inputTable.setIncreaseParallelism("increase");
        inputTable.setJoinModel("join");
        inputTable.setJoinOptimization("optimization");
        inputTable.setMinimized(false);
        
        mapData1.getInputTables().add(inputTable);
        
        final InputTable inputTable2 = PigmapFactory.eINSTANCE.createInputTable();
        inputTable2.setName("name1");
        inputTable2.setLookup(false);
        inputTable2.setExpressionFilter("filter2");
        inputTable2.setActivateCondensedTool(true);
        inputTable2.setActivateExpressionFilter(false);
        inputTable2.setCustomPartitioner("custom2");
        inputTable2.setIncreaseParallelism("increase");
        inputTable2.setJoinModel("join");
        inputTable2.setJoinOptimization("optimization");
        inputTable2.setMinimized(false);
        
        mapData2.getInputTables().add(inputTable2);
        assertFalse(mapData1.equals(mapData2));
    }
    
    private void test3(){
        PigMapDataImpl mapData1 = new PigMapDataImpl();
        PigMapDataImpl mapData2 = new PigMapDataImpl();
        assertTrue(mapData1.equals(mapData2));
        
        final OutputTable outputTable = PigmapFactory.eINSTANCE.createOutputTable();
        outputTable.setName("name1");
        outputTable.setExpressionFilter("filter");
        outputTable.setActivateCondensedTool(true);
        outputTable.setActivateExpressionFilter(true);
        outputTable.setMinimized(false);
        outputTable.setAllInOne(false);
        outputTable.setEnableEmptyElement(true);
        outputTable.setErrorReject(true);
        outputTable.setReject(false);
        outputTable.setRejectInnerJoin(true);
        mapData1.getOutputTables().add(outputTable);
        
        final OutputTable outputTable2 = PigmapFactory.eINSTANCE.createOutputTable();
        outputTable2.setName("name1");
        outputTable2.setExpressionFilter("filter");
        outputTable2.setActivateCondensedTool(true);
        outputTable2.setActivateExpressionFilter(true);
        outputTable2.setMinimized(false);
        outputTable2.setAllInOne(false);
        outputTable2.setEnableEmptyElement(true);
        outputTable2.setErrorReject(true);
        outputTable2.setReject(false);
        outputTable2.setRejectInnerJoin(true);
        mapData2.getOutputTables().add(outputTable2);
        
        assertTrue(mapData1.equals(mapData2));
    }
    
    private void test4(){
        PigMapDataImpl mapData1 = new PigMapDataImpl();
        PigMapDataImpl mapData2 = new PigMapDataImpl();
        assertTrue(mapData1.equals(mapData2));
        
        final OutputTable outputTable = PigmapFactory.eINSTANCE.createOutputTable();
        outputTable.setName("name1");
        outputTable.setExpressionFilter("filter");
        outputTable.setActivateCondensedTool(true);
        outputTable.setActivateExpressionFilter(true);
        outputTable.setMinimized(false);
        outputTable.setAllInOne(false);
        outputTable.setEnableEmptyElement(true);
        outputTable.setErrorReject(true);
        outputTable.setReject(false);
        outputTable.setRejectInnerJoin(true);
        mapData1.getOutputTables().add(outputTable);
        
        final OutputTable outputTable2 = PigmapFactory.eINSTANCE.createOutputTable();
        outputTable2.setName("name1");
        outputTable2.setExpressionFilter("filhter");
        outputTable2.setActivateCondensedTool(true);
        outputTable2.setActivateExpressionFilter(true);
        outputTable2.setMinimized(true);
        outputTable2.setAllInOne(false);
        outputTable2.setEnableEmptyElement(true);
        outputTable2.setErrorReject(true);
        outputTable2.setReject(false);
        outputTable2.setRejectInnerJoin(true);
        mapData2.getOutputTables().add(outputTable2);
        
        assertTrue(mapData1.equals(mapData2));
    }
    
    private void test5(){
        PigMapDataImpl mapData1 = new PigMapDataImpl();
        PigMapDataImpl mapData2 = new PigMapDataImpl();
        assertTrue(mapData1.equals(mapData2));
        
        final VarTable varTable = PigmapFactory.eINSTANCE.createVarTable();
        varTable.setName("name1");
        varTable.setMinimized(false);
        mapData1.getVarTables().add(varTable);
        
        final VarTable varTable2 = PigmapFactory.eINSTANCE.createVarTable();
        varTable2.setName("name1");
        varTable2.setMinimized(false);
        mapData2.getVarTables().add(varTable2);
        
        assertTrue(mapData1.equals(mapData2));
    }
    
    private void test6(){
        PigMapDataImpl mapData1 = new PigMapDataImpl();
        PigMapDataImpl mapData2 = new PigMapDataImpl();
        assertTrue(mapData1.equals(mapData2));
        
        final VarTable varTable = PigmapFactory.eINSTANCE.createVarTable();
        varTable.setName("name1");
        varTable.setMinimized(false);
        mapData1.getVarTables().add(varTable);
        
        final VarTable varTable2 = PigmapFactory.eINSTANCE.createVarTable();
        varTable2.setName("name1");
        varTable2.setMinimized(true);
        mapData2.getVarTables().add(varTable2);
        
        assertFalse(mapData1.equals(mapData2));
    }
    
    private void test7(){
        TableNode node1 = PigmapFactory.eINSTANCE.createTableNode();
        node1.setName("name");
        node1.setType("type");
        node1.setNullable(false);
        
        TableNode node2 = PigmapFactory.eINSTANCE.createTableNode();
        node2.setName("name");
        node2.setType("type");
        node2.setNullable(false);
        assertTrue(node1.equals(node2));
        
        TableNode node3 = PigmapFactory.eINSTANCE.createTableNode();
        node3.setName("name");
        node3.setType("type1");
        node3.setNullable(false);
        
        TableNode node4 = PigmapFactory.eINSTANCE.createTableNode();
        node4.setName("name");
        node4.setType("type");
        node4.setNullable(true);
        assertFalse(node3.equals(node4));
        
    }
}
