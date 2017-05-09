/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.talend.designer.pigmap.model.emf.pigmap.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.EObjectResolvingEList;

import org.eclipse.emf.ecore.util.InternalEList;
import org.talend.designer.pigmap.model.emf.pigmap.AbstractInOutTable;
import org.talend.designer.pigmap.model.emf.pigmap.FilterConnection;
import org.talend.designer.pigmap.model.emf.pigmap.PigmapPackage;
import org.talend.designer.pigmap.model.emf.pigmap.TableNode;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Abstract In Out Table</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.talend.designer.pigmap.model.emf.pigmap.impl.AbstractInOutTableImpl#getNodes <em>Nodes</em>}</li>
 *   <li>{@link org.talend.designer.pigmap.model.emf.pigmap.impl.AbstractInOutTableImpl#getExpressionFilter <em>Expression Filter</em>}</li>
 *   <li>{@link org.talend.designer.pigmap.model.emf.pigmap.impl.AbstractInOutTableImpl#isActivateExpressionFilter <em>Activate Expression Filter</em>}</li>
 *   <li>{@link org.talend.designer.pigmap.model.emf.pigmap.impl.AbstractInOutTableImpl#isActivateCondensedTool <em>Activate Condensed Tool</em>}</li>
 *   <li>{@link org.talend.designer.pigmap.model.emf.pigmap.impl.AbstractInOutTableImpl#isMinimized <em>Minimized</em>}</li>
 *   <li>{@link org.talend.designer.pigmap.model.emf.pigmap.impl.AbstractInOutTableImpl#getName <em>Name</em>}</li>
 *   <li>{@link org.talend.designer.pigmap.model.emf.pigmap.impl.AbstractInOutTableImpl#getFilterIncomingConnections <em>Filter Incoming Connections</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public abstract class AbstractInOutTableImpl extends EObjectImpl implements AbstractInOutTable {
    /**
     * The cached value of the '{@link #getNodes() <em>Nodes</em>}' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getNodes()
     * @generated
     * @ordered
     */
    protected EList<TableNode> nodes;

    /**
     * The default value of the '{@link #getExpressionFilter() <em>Expression Filter</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getExpressionFilter()
     * @generated
     * @ordered
     */
    protected static final String EXPRESSION_FILTER_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getExpressionFilter() <em>Expression Filter</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getExpressionFilter()
     * @generated
     * @ordered
     */
    protected String expressionFilter = EXPRESSION_FILTER_EDEFAULT;

    /**
     * The default value of the '{@link #isActivateExpressionFilter() <em>Activate Expression Filter</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isActivateExpressionFilter()
     * @generated
     * @ordered
     */
    protected static final boolean ACTIVATE_EXPRESSION_FILTER_EDEFAULT = false;

    /**
     * The cached value of the '{@link #isActivateExpressionFilter() <em>Activate Expression Filter</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isActivateExpressionFilter()
     * @generated
     * @ordered
     */
    protected boolean activateExpressionFilter = ACTIVATE_EXPRESSION_FILTER_EDEFAULT;

    /**
     * The default value of the '{@link #isActivateCondensedTool() <em>Activate Condensed Tool</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isActivateCondensedTool()
     * @generated
     * @ordered
     */
    protected static final boolean ACTIVATE_CONDENSED_TOOL_EDEFAULT = false;

    /**
     * The cached value of the '{@link #isActivateCondensedTool() <em>Activate Condensed Tool</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isActivateCondensedTool()
     * @generated
     * @ordered
     */
    protected boolean activateCondensedTool = ACTIVATE_CONDENSED_TOOL_EDEFAULT;

    /**
     * The default value of the '{@link #isMinimized() <em>Minimized</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isMinimized()
     * @generated
     * @ordered
     */
    protected static final boolean MINIMIZED_EDEFAULT = false;

    /**
     * The cached value of the '{@link #isMinimized() <em>Minimized</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isMinimized()
     * @generated
     * @ordered
     */
    protected boolean minimized = MINIMIZED_EDEFAULT;

    /**
     * The default value of the '{@link #getName() <em>Name</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getName()
     * @generated
     * @ordered
     */
    protected static final String NAME_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getName() <em>Name</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getName()
     * @generated
     * @ordered
     */
    protected String name = NAME_EDEFAULT;

    /**
     * The cached value of the '{@link #getFilterIncomingConnections() <em>Filter Incoming Connections</em>}' reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getFilterIncomingConnections()
     * @generated
     * @ordered
     */
    protected EList<FilterConnection> filterIncomingConnections;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected AbstractInOutTableImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return PigmapPackage.Literals.ABSTRACT_IN_OUT_TABLE;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EList<TableNode> getNodes() {
        if (nodes == null) {
            nodes = new EObjectContainmentEList<TableNode>(TableNode.class, this, PigmapPackage.ABSTRACT_IN_OUT_TABLE__NODES);
        }
        return nodes;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String getExpressionFilter() {
        return expressionFilter;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setExpressionFilter(String newExpressionFilter) {
        String oldExpressionFilter = expressionFilter;
        expressionFilter = newExpressionFilter;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, PigmapPackage.ABSTRACT_IN_OUT_TABLE__EXPRESSION_FILTER, oldExpressionFilter, expressionFilter));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean isActivateExpressionFilter() {
        return activateExpressionFilter;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setActivateExpressionFilter(boolean newActivateExpressionFilter) {
        boolean oldActivateExpressionFilter = activateExpressionFilter;
        activateExpressionFilter = newActivateExpressionFilter;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, PigmapPackage.ABSTRACT_IN_OUT_TABLE__ACTIVATE_EXPRESSION_FILTER, oldActivateExpressionFilter, activateExpressionFilter));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean isActivateCondensedTool() {
        return activateCondensedTool;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setActivateCondensedTool(boolean newActivateCondensedTool) {
        boolean oldActivateCondensedTool = activateCondensedTool;
        activateCondensedTool = newActivateCondensedTool;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, PigmapPackage.ABSTRACT_IN_OUT_TABLE__ACTIVATE_CONDENSED_TOOL, oldActivateCondensedTool, activateCondensedTool));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean isMinimized() {
        return minimized;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setMinimized(boolean newMinimized) {
        boolean oldMinimized = minimized;
        minimized = newMinimized;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, PigmapPackage.ABSTRACT_IN_OUT_TABLE__MINIMIZED, oldMinimized, minimized));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String getName() {
        return name;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setName(String newName) {
        String oldName = name;
        name = newName;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, PigmapPackage.ABSTRACT_IN_OUT_TABLE__NAME, oldName, name));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EList<FilterConnection> getFilterIncomingConnections() {
        if (filterIncomingConnections == null) {
            filterIncomingConnections = new EObjectResolvingEList<FilterConnection>(FilterConnection.class, this, PigmapPackage.ABSTRACT_IN_OUT_TABLE__FILTER_INCOMING_CONNECTIONS);
        }
        return filterIncomingConnections;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
        switch (featureID) {
            case PigmapPackage.ABSTRACT_IN_OUT_TABLE__NODES:
                return ((InternalEList<?>)getNodes()).basicRemove(otherEnd, msgs);
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
            case PigmapPackage.ABSTRACT_IN_OUT_TABLE__NODES:
                return getNodes();
            case PigmapPackage.ABSTRACT_IN_OUT_TABLE__EXPRESSION_FILTER:
                return getExpressionFilter();
            case PigmapPackage.ABSTRACT_IN_OUT_TABLE__ACTIVATE_EXPRESSION_FILTER:
                return isActivateExpressionFilter();
            case PigmapPackage.ABSTRACT_IN_OUT_TABLE__ACTIVATE_CONDENSED_TOOL:
                return isActivateCondensedTool();
            case PigmapPackage.ABSTRACT_IN_OUT_TABLE__MINIMIZED:
                return isMinimized();
            case PigmapPackage.ABSTRACT_IN_OUT_TABLE__NAME:
                return getName();
            case PigmapPackage.ABSTRACT_IN_OUT_TABLE__FILTER_INCOMING_CONNECTIONS:
                return getFilterIncomingConnections();
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
            case PigmapPackage.ABSTRACT_IN_OUT_TABLE__NODES:
                getNodes().clear();
                getNodes().addAll((Collection<? extends TableNode>)newValue);
                return;
            case PigmapPackage.ABSTRACT_IN_OUT_TABLE__EXPRESSION_FILTER:
                setExpressionFilter((String)newValue);
                return;
            case PigmapPackage.ABSTRACT_IN_OUT_TABLE__ACTIVATE_EXPRESSION_FILTER:
                setActivateExpressionFilter((Boolean)newValue);
                return;
            case PigmapPackage.ABSTRACT_IN_OUT_TABLE__ACTIVATE_CONDENSED_TOOL:
                setActivateCondensedTool((Boolean)newValue);
                return;
            case PigmapPackage.ABSTRACT_IN_OUT_TABLE__MINIMIZED:
                setMinimized((Boolean)newValue);
                return;
            case PigmapPackage.ABSTRACT_IN_OUT_TABLE__NAME:
                setName((String)newValue);
                return;
            case PigmapPackage.ABSTRACT_IN_OUT_TABLE__FILTER_INCOMING_CONNECTIONS:
                getFilterIncomingConnections().clear();
                getFilterIncomingConnections().addAll((Collection<? extends FilterConnection>)newValue);
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
            case PigmapPackage.ABSTRACT_IN_OUT_TABLE__NODES:
                getNodes().clear();
                return;
            case PigmapPackage.ABSTRACT_IN_OUT_TABLE__EXPRESSION_FILTER:
                setExpressionFilter(EXPRESSION_FILTER_EDEFAULT);
                return;
            case PigmapPackage.ABSTRACT_IN_OUT_TABLE__ACTIVATE_EXPRESSION_FILTER:
                setActivateExpressionFilter(ACTIVATE_EXPRESSION_FILTER_EDEFAULT);
                return;
            case PigmapPackage.ABSTRACT_IN_OUT_TABLE__ACTIVATE_CONDENSED_TOOL:
                setActivateCondensedTool(ACTIVATE_CONDENSED_TOOL_EDEFAULT);
                return;
            case PigmapPackage.ABSTRACT_IN_OUT_TABLE__MINIMIZED:
                setMinimized(MINIMIZED_EDEFAULT);
                return;
            case PigmapPackage.ABSTRACT_IN_OUT_TABLE__NAME:
                setName(NAME_EDEFAULT);
                return;
            case PigmapPackage.ABSTRACT_IN_OUT_TABLE__FILTER_INCOMING_CONNECTIONS:
                getFilterIncomingConnections().clear();
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
            case PigmapPackage.ABSTRACT_IN_OUT_TABLE__NODES:
                return nodes != null && !nodes.isEmpty();
            case PigmapPackage.ABSTRACT_IN_OUT_TABLE__EXPRESSION_FILTER:
                return EXPRESSION_FILTER_EDEFAULT == null ? expressionFilter != null : !EXPRESSION_FILTER_EDEFAULT.equals(expressionFilter);
            case PigmapPackage.ABSTRACT_IN_OUT_TABLE__ACTIVATE_EXPRESSION_FILTER:
                return activateExpressionFilter != ACTIVATE_EXPRESSION_FILTER_EDEFAULT;
            case PigmapPackage.ABSTRACT_IN_OUT_TABLE__ACTIVATE_CONDENSED_TOOL:
                return activateCondensedTool != ACTIVATE_CONDENSED_TOOL_EDEFAULT;
            case PigmapPackage.ABSTRACT_IN_OUT_TABLE__MINIMIZED:
                return minimized != MINIMIZED_EDEFAULT;
            case PigmapPackage.ABSTRACT_IN_OUT_TABLE__NAME:
                return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
            case PigmapPackage.ABSTRACT_IN_OUT_TABLE__FILTER_INCOMING_CONNECTIONS:
                return filterIncomingConnections != null && !filterIncomingConnections.isEmpty();
        }
        return super.eIsSet(featureID);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public String toString() {
        if (eIsProxy()) return super.toString();

        StringBuffer result = new StringBuffer(super.toString());
        result.append(" (expressionFilter: ");
        result.append(expressionFilter);
        result.append(", activateExpressionFilter: ");
        result.append(activateExpressionFilter);
        result.append(", activateCondensedTool: ");
        result.append(activateCondensedTool);
        result.append(", minimized: ");
        result.append(minimized);
        result.append(", name: ");
        result.append(name);
        result.append(')');
        return result.toString();
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
        AbstractInOutTableImpl other = (AbstractInOutTableImpl) obj;
        if(this.activateCondensedTool != other.activateCondensedTool){
            return false;
        }
        if(this.activateExpressionFilter != other.activateExpressionFilter){
            return false;
        }
        if(this.minimized != other.minimized){
            return false;
        }
        if (this.name == null) {
            if (other.name != null) {
                return false;
            }
        } else if (!this.name.equals(other.name)) {
            return false;
        }
        
        if (this.expressionFilter == null) {
            if (other.expressionFilter != null) {
                return false;
            }
        } else if (!this.expressionFilter.equals(other.expressionFilter)) {
            return false;
        }
        EList<FilterConnection> otherFilters = other.filterIncomingConnections;
        EList<TableNode> otherNodes = other.nodes;
        
        if(this.filterIncomingConnections.size() != otherFilters.size()){
            return false;
        }
        if(this.nodes.size() != otherNodes.size()){
            return false;
        }
        for(FilterConnection filter:filterIncomingConnections){
            boolean found = false;
            for(FilterConnection otherFilter:otherFilters){
                if(filter.getName().equals(otherFilter.getName())){
                    found = true;
                    if(!filter.equals(otherFilter)){
                        return false;
                    }
                    break;
                }
            }
            if(found == false){
                return false;
            }
        }
        
        for(TableNode node:nodes){
            boolean found = false;
            for(TableNode otherNode:otherNodes){
                if(node.getName().equals(otherNode.getName())){
                    found = true;
                    if(!node.equals(otherNode)){
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

} //AbstractInOutTableImpl
