/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.talend.designer.pigmap.model.emf.pigmap.impl;

import java.util.Collection;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.util.EObjectResolvingEList;
import org.talend.designer.pigmap.model.emf.pigmap.Connection;
import org.talend.designer.pigmap.model.emf.pigmap.FilterConnection;
import org.talend.designer.pigmap.model.emf.pigmap.LookupConnection;
import org.talend.designer.pigmap.model.emf.pigmap.PigmapPackage;
import org.talend.designer.pigmap.model.emf.pigmap.TableNode;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Table Node</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.talend.designer.pigmap.model.emf.pigmap.impl.TableNodeImpl#getLookupOutgoingConnections <em>Lookup Outgoing Connections</em>}</li>
 *   <li>{@link org.talend.designer.pigmap.model.emf.pigmap.impl.TableNodeImpl#getLookupIncomingConnections <em>Lookup Incoming Connections</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class TableNodeImpl extends AbstractNodeImpl implements TableNode {
    /**
     * The cached value of the '{@link #getLookupOutgoingConnections() <em>Lookup Outgoing Connections</em>}' reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getLookupOutgoingConnections()
     * @generated
     * @ordered
     */
    protected EList<LookupConnection> lookupOutgoingConnections;
    /**
     * The cached value of the '{@link #getLookupIncomingConnections() <em>Lookup Incoming Connections</em>}' reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getLookupIncomingConnections()
     * @generated
     * @ordered
     */
    protected EList<LookupConnection> lookupIncomingConnections;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected TableNodeImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return PigmapPackage.Literals.TABLE_NODE;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EList<LookupConnection> getLookupOutgoingConnections() {
        if (lookupOutgoingConnections == null) {
            lookupOutgoingConnections = new EObjectResolvingEList<LookupConnection>(LookupConnection.class, this, PigmapPackage.TABLE_NODE__LOOKUP_OUTGOING_CONNECTIONS);
        }
        return lookupOutgoingConnections;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EList<LookupConnection> getLookupIncomingConnections() {
        if (lookupIncomingConnections == null) {
            lookupIncomingConnections = new EObjectResolvingEList<LookupConnection>(LookupConnection.class, this, PigmapPackage.TABLE_NODE__LOOKUP_INCOMING_CONNECTIONS);
        }
        return lookupIncomingConnections;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public Object eGet(int featureID, boolean resolve, boolean coreType) {
        switch (featureID) {
            case PigmapPackage.TABLE_NODE__LOOKUP_OUTGOING_CONNECTIONS:
                return getLookupOutgoingConnections();
            case PigmapPackage.TABLE_NODE__LOOKUP_INCOMING_CONNECTIONS:
                return getLookupIncomingConnections();
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
            case PigmapPackage.TABLE_NODE__LOOKUP_OUTGOING_CONNECTIONS:
                getLookupOutgoingConnections().clear();
                getLookupOutgoingConnections().addAll((Collection<? extends LookupConnection>)newValue);
                return;
            case PigmapPackage.TABLE_NODE__LOOKUP_INCOMING_CONNECTIONS:
                getLookupIncomingConnections().clear();
                getLookupIncomingConnections().addAll((Collection<? extends LookupConnection>)newValue);
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
            case PigmapPackage.TABLE_NODE__LOOKUP_OUTGOING_CONNECTIONS:
                getLookupOutgoingConnections().clear();
                return;
            case PigmapPackage.TABLE_NODE__LOOKUP_INCOMING_CONNECTIONS:
                getLookupIncomingConnections().clear();
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
            case PigmapPackage.TABLE_NODE__LOOKUP_OUTGOING_CONNECTIONS:
                return lookupOutgoingConnections != null && !lookupOutgoingConnections.isEmpty();
            case PigmapPackage.TABLE_NODE__LOOKUP_INCOMING_CONNECTIONS:
                return lookupIncomingConnections != null && !lookupIncomingConnections.isEmpty();
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
        TableNodeImpl other = (TableNodeImpl) obj;
        if(this.getLookupIncomingConnections().size() != other.getLookupIncomingConnections().size()){
            return false;
        }
        if(this.getLookupOutgoingConnections().size() != other.getLookupOutgoingConnections().size()){
            return false;
        }
        
        for(LookupConnection conn:this.lookupOutgoingConnections){
            boolean found = false;
            for(LookupConnection otherConn:other.lookupOutgoingConnections){
                if(conn.getName().equals(otherConn.getName())){
                    found = true;
                    if(!conn.equals(otherConn)){
                        return false;
                    }
                    break;
                }
            }
            if(found == false){
                return false;
            }
        }
        
        for(LookupConnection conn:this.lookupIncomingConnections){
            boolean found = false;
            for(LookupConnection otherConn:other.lookupIncomingConnections){
                if(conn.getName().equals(otherConn.getName())){
                    found = true;
                    if(!conn.equals(otherConn)){
                        return false;
                    }
                    break;
                }
            }
            if(found == false){
                return false;
            }
        }
        return super.equals(obj);
    }

} //TableNodeImpl
