/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.talend.designer.pigmap.model.emf.pigmap.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

import org.talend.designer.core.model.utils.emf.talendfile.impl.AbstractExternalDataImpl;
import org.talend.designer.pigmap.model.emf.pigmap.IConnection;
import org.talend.designer.pigmap.model.emf.pigmap.InputTable;
import org.talend.designer.pigmap.model.emf.pigmap.OutputTable;
import org.talend.designer.pigmap.model.emf.pigmap.PigMapData;
import org.talend.designer.pigmap.model.emf.pigmap.PigmapPackage;
import org.talend.designer.pigmap.model.emf.pigmap.VarTable;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Pig Map Data</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.talend.designer.pigmap.model.emf.pigmap.impl.PigMapDataImpl#getInputTables <em>Input Tables</em>}</li>
 *   <li>{@link org.talend.designer.pigmap.model.emf.pigmap.impl.PigMapDataImpl#getOutputTables <em>Output Tables</em>}</li>
 *   <li>{@link org.talend.designer.pigmap.model.emf.pigmap.impl.PigMapDataImpl#getVarTables <em>Var Tables</em>}</li>
 *   <li>{@link org.talend.designer.pigmap.model.emf.pigmap.impl.PigMapDataImpl#getConnections <em>Connections</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class PigMapDataImpl extends AbstractExternalDataImpl implements PigMapData {
    /**
     * The cached value of the '{@link #getInputTables() <em>Input Tables</em>}' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getInputTables()
     * @generated
     * @ordered
     */
    protected EList<InputTable> inputTables;

    /**
     * The cached value of the '{@link #getOutputTables() <em>Output Tables</em>}' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getOutputTables()
     * @generated
     * @ordered
     */
    protected EList<OutputTable> outputTables;

    /**
     * The cached value of the '{@link #getVarTables() <em>Var Tables</em>}' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getVarTables()
     * @generated
     * @ordered
     */
    protected EList<VarTable> varTables;

    /**
     * The cached value of the '{@link #getConnections() <em>Connections</em>}' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getConnections()
     * @generated
     * @ordered
     */
    protected EList<IConnection> connections;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected PigMapDataImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return PigmapPackage.Literals.PIG_MAP_DATA;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EList<InputTable> getInputTables() {
        if (inputTables == null) {
            inputTables = new EObjectContainmentEList<InputTable>(InputTable.class, this, PigmapPackage.PIG_MAP_DATA__INPUT_TABLES);
        }
        return inputTables;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EList<OutputTable> getOutputTables() {
        if (outputTables == null) {
            outputTables = new EObjectContainmentEList<OutputTable>(OutputTable.class, this, PigmapPackage.PIG_MAP_DATA__OUTPUT_TABLES);
        }
        return outputTables;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EList<VarTable> getVarTables() {
        if (varTables == null) {
            varTables = new EObjectContainmentEList<VarTable>(VarTable.class, this, PigmapPackage.PIG_MAP_DATA__VAR_TABLES);
        }
        return varTables;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EList<IConnection> getConnections() {
        if (connections == null) {
            connections = new EObjectContainmentEList<IConnection>(IConnection.class, this, PigmapPackage.PIG_MAP_DATA__CONNECTIONS);
        }
        return connections;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
        switch (featureID) {
            case PigmapPackage.PIG_MAP_DATA__INPUT_TABLES:
                return ((InternalEList<?>)getInputTables()).basicRemove(otherEnd, msgs);
            case PigmapPackage.PIG_MAP_DATA__OUTPUT_TABLES:
                return ((InternalEList<?>)getOutputTables()).basicRemove(otherEnd, msgs);
            case PigmapPackage.PIG_MAP_DATA__VAR_TABLES:
                return ((InternalEList<?>)getVarTables()).basicRemove(otherEnd, msgs);
            case PigmapPackage.PIG_MAP_DATA__CONNECTIONS:
                return ((InternalEList<?>)getConnections()).basicRemove(otherEnd, msgs);
        }
        return super.eInverseRemove(otherEnd, featureID, msgs);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public Object eGet(int featureID, boolean resolve, boolean coreType) {
        switch (featureID) {
            case PigmapPackage.PIG_MAP_DATA__INPUT_TABLES:
                return getInputTables();
            case PigmapPackage.PIG_MAP_DATA__OUTPUT_TABLES:
                return getOutputTables();
            case PigmapPackage.PIG_MAP_DATA__VAR_TABLES:
                return getVarTables();
            case PigmapPackage.PIG_MAP_DATA__CONNECTIONS:
                return getConnections();
        }
        return super.eGet(featureID, resolve, coreType);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @SuppressWarnings("unchecked")
    @Override
    public void eSet(int featureID, Object newValue) {
        switch (featureID) {
            case PigmapPackage.PIG_MAP_DATA__INPUT_TABLES:
                getInputTables().clear();
                getInputTables().addAll((Collection<? extends InputTable>)newValue);
                return;
            case PigmapPackage.PIG_MAP_DATA__OUTPUT_TABLES:
                getOutputTables().clear();
                getOutputTables().addAll((Collection<? extends OutputTable>)newValue);
                return;
            case PigmapPackage.PIG_MAP_DATA__VAR_TABLES:
                getVarTables().clear();
                getVarTables().addAll((Collection<? extends VarTable>)newValue);
                return;
            case PigmapPackage.PIG_MAP_DATA__CONNECTIONS:
                getConnections().clear();
                getConnections().addAll((Collection<? extends IConnection>)newValue);
                return;
        }
        super.eSet(featureID, newValue);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public void eUnset(int featureID) {
        switch (featureID) {
            case PigmapPackage.PIG_MAP_DATA__INPUT_TABLES:
                getInputTables().clear();
                return;
            case PigmapPackage.PIG_MAP_DATA__OUTPUT_TABLES:
                getOutputTables().clear();
                return;
            case PigmapPackage.PIG_MAP_DATA__VAR_TABLES:
                getVarTables().clear();
                return;
            case PigmapPackage.PIG_MAP_DATA__CONNECTIONS:
                getConnections().clear();
                return;
        }
        super.eUnset(featureID);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public boolean eIsSet(int featureID) {
        switch (featureID) {
            case PigmapPackage.PIG_MAP_DATA__INPUT_TABLES:
                return inputTables != null && !inputTables.isEmpty();
            case PigmapPackage.PIG_MAP_DATA__OUTPUT_TABLES:
                return outputTables != null && !outputTables.isEmpty();
            case PigmapPackage.PIG_MAP_DATA__VAR_TABLES:
                return varTables != null && !varTables.isEmpty();
            case PigmapPackage.PIG_MAP_DATA__CONNECTIONS:
                return connections != null && !connections.isEmpty();
        }
        return super.eIsSet(featureID);
    }
    
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated not
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        PigMapDataImpl dbObj = (PigMapDataImpl) obj;
        EList<InputTable> inputs = dbObj.getInputTables();
        EList<OutputTable> outputs =dbObj.getOutputTables();
        EList<VarTable> vars =dbObj.getVarTables();
        EList<IConnection> conns = dbObj.getConnections();
        if(inputs.size() != getInputTables().size()){
            return false;
        }
        if(outputs.size() != getOutputTables().size()){
            return false;
        }
        if(vars.size() != getVarTables().size()){
            return false;
        }
        if(getConnections().size() != conns.size()){
            return false;
        }
        
        for(InputTable inputTable:inputTables){
            boolean found = false;
            for(InputTable input:inputs){
                if(inputTable.getName().equals(input.getName())){
                    found = true;
                    if(!inputTable.equals(input)){
                        return false;
                    }
                    break;
                }
            }
            if(found == false){
                return false;
            }
        }
        
        for(OutputTable outputTable:outputTables){
            boolean found = false;
            for(OutputTable output:outputs){
                if(outputTable.getName().equals(output.getName())){
                    found = true;
                    if(!outputTable.equals(output)){
                        return false;
                    }
                    break;
                }
            }
            if(found == false){
                return false;
            }
        }
        
        for(VarTable varTable:varTables){
            boolean found = false;
            for(VarTable var:vars){
                if(varTable.getName().equals(var.getName())){
                    found = true;
                    if(!varTable.equals(var)){
                        return false;
                    }
                    break;
                }
            }
            if(found == false){
                return false;
            }
        }
        
        for(IConnection connection:connections){
            boolean found = false;
            for(IConnection conn:conns){
                if(connection.getName().equals(conn.getName())){
                    found = true;
                    if(!conn.equals(connection)){
                        return false;
                    }
                    break;
                }
            }
            if(found == false){
                return false;
            }
        }
        
        return true;
    }

} //PigMapDataImpl
