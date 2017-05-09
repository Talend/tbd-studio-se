/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.talend.designer.pigmap.model.emf.pigmap.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

import org.eclipse.emf.ecore.util.EObjectResolvingEList;

import org.talend.designer.pigmap.model.emf.pigmap.AbstractNode;
import org.talend.designer.pigmap.model.emf.pigmap.Connection;
import org.talend.designer.pigmap.model.emf.pigmap.FilterConnection;
import org.talend.designer.pigmap.model.emf.pigmap.PigmapPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Abstract Node</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.talend.designer.pigmap.model.emf.pigmap.impl.AbstractNodeImpl#getName <em>Name</em>}</li>
 *   <li>{@link org.talend.designer.pigmap.model.emf.pigmap.impl.AbstractNodeImpl#getExpression <em>Expression</em>}</li>
 *   <li>{@link org.talend.designer.pigmap.model.emf.pigmap.impl.AbstractNodeImpl#getType <em>Type</em>}</li>
 *   <li>{@link org.talend.designer.pigmap.model.emf.pigmap.impl.AbstractNodeImpl#isKey <em>Key</em>}</li>
 *   <li>{@link org.talend.designer.pigmap.model.emf.pigmap.impl.AbstractNodeImpl#getPattern <em>Pattern</em>}</li>
 *   <li>{@link org.talend.designer.pigmap.model.emf.pigmap.impl.AbstractNodeImpl#isNullable <em>Nullable</em>}</li>
 *   <li>{@link org.talend.designer.pigmap.model.emf.pigmap.impl.AbstractNodeImpl#getOutgoingConnections <em>Outgoing Connections</em>}</li>
 *   <li>{@link org.talend.designer.pigmap.model.emf.pigmap.impl.AbstractNodeImpl#getIncomingConnections <em>Incoming Connections</em>}</li>
 *   <li>{@link org.talend.designer.pigmap.model.emf.pigmap.impl.AbstractNodeImpl#getFilterOutGoingConnections <em>Filter Out Going Connections</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public abstract class AbstractNodeImpl extends EObjectImpl implements AbstractNode {
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
     * The default value of the '{@link #getExpression() <em>Expression</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getExpression()
     * @generated
     * @ordered
     */
    protected static final String EXPRESSION_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getExpression() <em>Expression</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getExpression()
     * @generated
     * @ordered
     */
    protected String expression = EXPRESSION_EDEFAULT;

    /**
     * The default value of the '{@link #getType() <em>Type</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getType()
     * @generated
     * @ordered
     */
    protected static final String TYPE_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getType() <em>Type</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getType()
     * @generated
     * @ordered
     */
    protected String type = TYPE_EDEFAULT;

    /**
     * The default value of the '{@link #isKey() <em>Key</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isKey()
     * @generated
     * @ordered
     */
    protected static final boolean KEY_EDEFAULT = false;

    /**
     * The cached value of the '{@link #isKey() <em>Key</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isKey()
     * @generated
     * @ordered
     */
    protected boolean key = KEY_EDEFAULT;

    /**
     * The default value of the '{@link #getPattern() <em>Pattern</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getPattern()
     * @generated
     * @ordered
     */
    protected static final String PATTERN_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getPattern() <em>Pattern</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getPattern()
     * @generated
     * @ordered
     */
    protected String pattern = PATTERN_EDEFAULT;

    /**
     * The default value of the '{@link #isNullable() <em>Nullable</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isNullable()
     * @generated
     * @ordered
     */
    protected static final boolean NULLABLE_EDEFAULT = false;

    /**
     * The cached value of the '{@link #isNullable() <em>Nullable</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isNullable()
     * @generated
     * @ordered
     */
    protected boolean nullable = NULLABLE_EDEFAULT;

    /**
     * The cached value of the '{@link #getOutgoingConnections() <em>Outgoing Connections</em>}' reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getOutgoingConnections()
     * @generated
     * @ordered
     */
    protected EList<Connection> outgoingConnections;

    /**
     * The cached value of the '{@link #getIncomingConnections() <em>Incoming Connections</em>}' reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getIncomingConnections()
     * @generated
     * @ordered
     */
    protected EList<Connection> incomingConnections;

    /**
     * The cached value of the '{@link #getFilterOutGoingConnections() <em>Filter Out Going Connections</em>}' reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getFilterOutGoingConnections()
     * @generated
     * @ordered
     */
    protected EList<FilterConnection> filterOutGoingConnections;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected AbstractNodeImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return PigmapPackage.Literals.ABSTRACT_NODE;
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
            eNotify(new ENotificationImpl(this, Notification.SET, PigmapPackage.ABSTRACT_NODE__NAME, oldName, name));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String getExpression() {
        return expression;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setExpression(String newExpression) {
        String oldExpression = expression;
        expression = newExpression;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, PigmapPackage.ABSTRACT_NODE__EXPRESSION, oldExpression, expression));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String getType() {
        return type;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setType(String newType) {
        String oldType = type;
        type = newType;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, PigmapPackage.ABSTRACT_NODE__TYPE, oldType, type));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean isKey() {
        return key;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setKey(boolean newKey) {
        boolean oldKey = key;
        key = newKey;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, PigmapPackage.ABSTRACT_NODE__KEY, oldKey, key));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String getPattern() {
        return pattern;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setPattern(String newPattern) {
        String oldPattern = pattern;
        pattern = newPattern;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, PigmapPackage.ABSTRACT_NODE__PATTERN, oldPattern, pattern));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean isNullable() {
        return nullable;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setNullable(boolean newNullable) {
        boolean oldNullable = nullable;
        nullable = newNullable;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, PigmapPackage.ABSTRACT_NODE__NULLABLE, oldNullable, nullable));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EList<Connection> getOutgoingConnections() {
        if (outgoingConnections == null) {
            outgoingConnections = new EObjectResolvingEList<Connection>(Connection.class, this, PigmapPackage.ABSTRACT_NODE__OUTGOING_CONNECTIONS);
        }
        return outgoingConnections;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EList<Connection> getIncomingConnections() {
        if (incomingConnections == null) {
            incomingConnections = new EObjectResolvingEList<Connection>(Connection.class, this, PigmapPackage.ABSTRACT_NODE__INCOMING_CONNECTIONS);
        }
        return incomingConnections;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EList<FilterConnection> getFilterOutGoingConnections() {
        if (filterOutGoingConnections == null) {
            filterOutGoingConnections = new EObjectResolvingEList<FilterConnection>(FilterConnection.class, this, PigmapPackage.ABSTRACT_NODE__FILTER_OUT_GOING_CONNECTIONS);
        }
        return filterOutGoingConnections;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public Object eGet(int featureID, boolean resolve, boolean coreType) {
        switch (featureID) {
            case PigmapPackage.ABSTRACT_NODE__NAME:
                return getName();
            case PigmapPackage.ABSTRACT_NODE__EXPRESSION:
                return getExpression();
            case PigmapPackage.ABSTRACT_NODE__TYPE:
                return getType();
            case PigmapPackage.ABSTRACT_NODE__KEY:
                return isKey();
            case PigmapPackage.ABSTRACT_NODE__PATTERN:
                return getPattern();
            case PigmapPackage.ABSTRACT_NODE__NULLABLE:
                return isNullable();
            case PigmapPackage.ABSTRACT_NODE__OUTGOING_CONNECTIONS:
                return getOutgoingConnections();
            case PigmapPackage.ABSTRACT_NODE__INCOMING_CONNECTIONS:
                return getIncomingConnections();
            case PigmapPackage.ABSTRACT_NODE__FILTER_OUT_GOING_CONNECTIONS:
                return getFilterOutGoingConnections();
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
            case PigmapPackage.ABSTRACT_NODE__NAME:
                setName((String)newValue);
                return;
            case PigmapPackage.ABSTRACT_NODE__EXPRESSION:
                setExpression((String)newValue);
                return;
            case PigmapPackage.ABSTRACT_NODE__TYPE:
                setType((String)newValue);
                return;
            case PigmapPackage.ABSTRACT_NODE__KEY:
                setKey((Boolean)newValue);
                return;
            case PigmapPackage.ABSTRACT_NODE__PATTERN:
                setPattern((String)newValue);
                return;
            case PigmapPackage.ABSTRACT_NODE__NULLABLE:
                setNullable((Boolean)newValue);
                return;
            case PigmapPackage.ABSTRACT_NODE__OUTGOING_CONNECTIONS:
                getOutgoingConnections().clear();
                getOutgoingConnections().addAll((Collection<? extends Connection>)newValue);
                return;
            case PigmapPackage.ABSTRACT_NODE__INCOMING_CONNECTIONS:
                getIncomingConnections().clear();
                getIncomingConnections().addAll((Collection<? extends Connection>)newValue);
                return;
            case PigmapPackage.ABSTRACT_NODE__FILTER_OUT_GOING_CONNECTIONS:
                getFilterOutGoingConnections().clear();
                getFilterOutGoingConnections().addAll((Collection<? extends FilterConnection>)newValue);
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
            case PigmapPackage.ABSTRACT_NODE__NAME:
                setName(NAME_EDEFAULT);
                return;
            case PigmapPackage.ABSTRACT_NODE__EXPRESSION:
                setExpression(EXPRESSION_EDEFAULT);
                return;
            case PigmapPackage.ABSTRACT_NODE__TYPE:
                setType(TYPE_EDEFAULT);
                return;
            case PigmapPackage.ABSTRACT_NODE__KEY:
                setKey(KEY_EDEFAULT);
                return;
            case PigmapPackage.ABSTRACT_NODE__PATTERN:
                setPattern(PATTERN_EDEFAULT);
                return;
            case PigmapPackage.ABSTRACT_NODE__NULLABLE:
                setNullable(NULLABLE_EDEFAULT);
                return;
            case PigmapPackage.ABSTRACT_NODE__OUTGOING_CONNECTIONS:
                getOutgoingConnections().clear();
                return;
            case PigmapPackage.ABSTRACT_NODE__INCOMING_CONNECTIONS:
                getIncomingConnections().clear();
                return;
            case PigmapPackage.ABSTRACT_NODE__FILTER_OUT_GOING_CONNECTIONS:
                getFilterOutGoingConnections().clear();
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
            case PigmapPackage.ABSTRACT_NODE__NAME:
                return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
            case PigmapPackage.ABSTRACT_NODE__EXPRESSION:
                return EXPRESSION_EDEFAULT == null ? expression != null : !EXPRESSION_EDEFAULT.equals(expression);
            case PigmapPackage.ABSTRACT_NODE__TYPE:
                return TYPE_EDEFAULT == null ? type != null : !TYPE_EDEFAULT.equals(type);
            case PigmapPackage.ABSTRACT_NODE__KEY:
                return key != KEY_EDEFAULT;
            case PigmapPackage.ABSTRACT_NODE__PATTERN:
                return PATTERN_EDEFAULT == null ? pattern != null : !PATTERN_EDEFAULT.equals(pattern);
            case PigmapPackage.ABSTRACT_NODE__NULLABLE:
                return nullable != NULLABLE_EDEFAULT;
            case PigmapPackage.ABSTRACT_NODE__OUTGOING_CONNECTIONS:
                return outgoingConnections != null && !outgoingConnections.isEmpty();
            case PigmapPackage.ABSTRACT_NODE__INCOMING_CONNECTIONS:
                return incomingConnections != null && !incomingConnections.isEmpty();
            case PigmapPackage.ABSTRACT_NODE__FILTER_OUT_GOING_CONNECTIONS:
                return filterOutGoingConnections != null && !filterOutGoingConnections.isEmpty();
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
        result.append(" (name: ");
        result.append(name);
        result.append(", expression: ");
        result.append(expression);
        result.append(", type: ");
        result.append(type);
        result.append(", key: ");
        result.append(key);
        result.append(", pattern: ");
        result.append(pattern);
        result.append(", nullable: ");
        result.append(nullable);
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
        AbstractNodeImpl other = (AbstractNodeImpl) obj;
        if (this.name == null) {
            if (other.name != null) {
                return false;
            }
        } else if (!this.name.equals(other.name)) {
            return false;
        }
        if (this.pattern == null) {
            if (other.pattern != null) {
                return false;
            }
        } else if (!this.pattern.equals(other.pattern)) {
            return false;
        }
        
        if (this.expression == null) {
            if (other.expression != null) {
                return false;
            }
        } else if (!this.expression.equals(other.expression)) {
            return false;
        }
        
        if (this.type == null) {
            if (other.type != null) {
                return false;
            }
        } else if (!this.type.equals(other.type)) {
            return false;
        }
        if(this.nullable != other.nullable){
            return false;
        }
        if(this.key != other.key){
            return false;
        }
        if(this.getFilterOutGoingConnections().size() != other.getFilterOutGoingConnections().size()){
            return false;
        }
        if(this.getIncomingConnections().size() != other.getIncomingConnections().size()){
            return false;
        }
        if(this.getOutgoingConnections().size() != other.getOutgoingConnections().size()){
            return false;
        }
        
        for(FilterConnection filter:this.filterOutGoingConnections){
            boolean found = false;
            for(FilterConnection otherFilter:other.filterOutGoingConnections){
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
        
        for(Connection conn:this.outgoingConnections){
            boolean found = false;
            for(Connection otherConn:other.outgoingConnections){
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
        
        for(Connection conn:this.incomingConnections){
            boolean found = false;
            for(Connection otherConn:other.incomingConnections){
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
        return true;
    }

} //AbstractNodeImpl
