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

import org.eclipse.emf.ecore.util.EObjectResolvingEList;
import org.talend.designer.pigmap.model.emf.pigmap.OutputTable;
import org.talend.designer.pigmap.model.emf.pigmap.PigmapPackage;
import org.talend.designer.pigmap.model.emf.pigmap.TableNode;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Output Table</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.talend.designer.pigmap.model.emf.pigmap.impl.OutputTableImpl#isReject <em>Reject</em>}</li>
 *   <li>{@link org.talend.designer.pigmap.model.emf.pigmap.impl.OutputTableImpl#isRejectInnerJoin <em>Reject Inner Join</em>}</li>
 *   <li>{@link org.talend.designer.pigmap.model.emf.pigmap.impl.OutputTableImpl#isErrorReject <em>Error Reject</em>}</li>
 *   <li>{@link org.talend.designer.pigmap.model.emf.pigmap.impl.OutputTableImpl#isAllInOne <em>All In One</em>}</li>
 *   <li>{@link org.talend.designer.pigmap.model.emf.pigmap.impl.OutputTableImpl#isEnableEmptyElement <em>Enable Empty Element</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class OutputTableImpl extends AbstractInOutTableImpl implements OutputTable {
    /**
     * The default value of the '{@link #isReject() <em>Reject</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isReject()
     * @generated
     * @ordered
     */
    protected static final boolean REJECT_EDEFAULT = false;

    /**
     * The cached value of the '{@link #isReject() <em>Reject</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isReject()
     * @generated
     * @ordered
     */
    protected boolean reject = REJECT_EDEFAULT;

    /**
     * The default value of the '{@link #isRejectInnerJoin() <em>Reject Inner Join</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isRejectInnerJoin()
     * @generated
     * @ordered
     */
    protected static final boolean REJECT_INNER_JOIN_EDEFAULT = false;

    /**
     * The cached value of the '{@link #isRejectInnerJoin() <em>Reject Inner Join</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isRejectInnerJoin()
     * @generated
     * @ordered
     */
    protected boolean rejectInnerJoin = REJECT_INNER_JOIN_EDEFAULT;

    /**
     * The default value of the '{@link #isErrorReject() <em>Error Reject</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isErrorReject()
     * @generated
     * @ordered
     */
    protected static final boolean ERROR_REJECT_EDEFAULT = false;

    /**
     * The cached value of the '{@link #isErrorReject() <em>Error Reject</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isErrorReject()
     * @generated
     * @ordered
     */
    protected boolean errorReject = ERROR_REJECT_EDEFAULT;

    /**
     * The default value of the '{@link #isAllInOne() <em>All In One</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isAllInOne()
     * @generated
     * @ordered
     */
    protected static final boolean ALL_IN_ONE_EDEFAULT = false;

    /**
     * The cached value of the '{@link #isAllInOne() <em>All In One</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isAllInOne()
     * @generated
     * @ordered
     */
    protected boolean allInOne = ALL_IN_ONE_EDEFAULT;

    /**
     * The default value of the '{@link #isEnableEmptyElement() <em>Enable Empty Element</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isEnableEmptyElement()
     * @generated
     * @ordered
     */
    protected static final boolean ENABLE_EMPTY_ELEMENT_EDEFAULT = true;

    /**
     * The cached value of the '{@link #isEnableEmptyElement() <em>Enable Empty Element</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isEnableEmptyElement()
     * @generated
     * @ordered
     */
    protected boolean enableEmptyElement = ENABLE_EMPTY_ELEMENT_EDEFAULT;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected OutputTableImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return PigmapPackage.Literals.OUTPUT_TABLE;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean isReject() {
        return reject;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setReject(boolean newReject) {
        boolean oldReject = reject;
        reject = newReject;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, PigmapPackage.OUTPUT_TABLE__REJECT, oldReject, reject));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean isRejectInnerJoin() {
        return rejectInnerJoin;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setRejectInnerJoin(boolean newRejectInnerJoin) {
        boolean oldRejectInnerJoin = rejectInnerJoin;
        rejectInnerJoin = newRejectInnerJoin;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, PigmapPackage.OUTPUT_TABLE__REJECT_INNER_JOIN, oldRejectInnerJoin, rejectInnerJoin));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean isErrorReject() {
        return errorReject;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setErrorReject(boolean newErrorReject) {
        boolean oldErrorReject = errorReject;
        errorReject = newErrorReject;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, PigmapPackage.OUTPUT_TABLE__ERROR_REJECT, oldErrorReject, errorReject));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean isAllInOne() {
        return allInOne;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setAllInOne(boolean newAllInOne) {
        boolean oldAllInOne = allInOne;
        allInOne = newAllInOne;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, PigmapPackage.OUTPUT_TABLE__ALL_IN_ONE, oldAllInOne, allInOne));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean isEnableEmptyElement() {
        return enableEmptyElement;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setEnableEmptyElement(boolean newEnableEmptyElement) {
        boolean oldEnableEmptyElement = enableEmptyElement;
        enableEmptyElement = newEnableEmptyElement;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, PigmapPackage.OUTPUT_TABLE__ENABLE_EMPTY_ELEMENT, oldEnableEmptyElement, enableEmptyElement));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public Object eGet(int featureID, boolean resolve, boolean coreType) {
        switch (featureID) {
            case PigmapPackage.OUTPUT_TABLE__REJECT:
                return isReject();
            case PigmapPackage.OUTPUT_TABLE__REJECT_INNER_JOIN:
                return isRejectInnerJoin();
            case PigmapPackage.OUTPUT_TABLE__ERROR_REJECT:
                return isErrorReject();
            case PigmapPackage.OUTPUT_TABLE__ALL_IN_ONE:
                return isAllInOne();
            case PigmapPackage.OUTPUT_TABLE__ENABLE_EMPTY_ELEMENT:
                return isEnableEmptyElement();
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
            case PigmapPackage.OUTPUT_TABLE__REJECT:
                setReject((Boolean)newValue);
                return;
            case PigmapPackage.OUTPUT_TABLE__REJECT_INNER_JOIN:
                setRejectInnerJoin((Boolean)newValue);
                return;
            case PigmapPackage.OUTPUT_TABLE__ERROR_REJECT:
                setErrorReject((Boolean)newValue);
                return;
            case PigmapPackage.OUTPUT_TABLE__ALL_IN_ONE:
                setAllInOne((Boolean)newValue);
                return;
            case PigmapPackage.OUTPUT_TABLE__ENABLE_EMPTY_ELEMENT:
                setEnableEmptyElement((Boolean)newValue);
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
            case PigmapPackage.OUTPUT_TABLE__REJECT:
                setReject(REJECT_EDEFAULT);
                return;
            case PigmapPackage.OUTPUT_TABLE__REJECT_INNER_JOIN:
                setRejectInnerJoin(REJECT_INNER_JOIN_EDEFAULT);
                return;
            case PigmapPackage.OUTPUT_TABLE__ERROR_REJECT:
                setErrorReject(ERROR_REJECT_EDEFAULT);
                return;
            case PigmapPackage.OUTPUT_TABLE__ALL_IN_ONE:
                setAllInOne(ALL_IN_ONE_EDEFAULT);
                return;
            case PigmapPackage.OUTPUT_TABLE__ENABLE_EMPTY_ELEMENT:
                setEnableEmptyElement(ENABLE_EMPTY_ELEMENT_EDEFAULT);
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
            case PigmapPackage.OUTPUT_TABLE__REJECT:
                return reject != REJECT_EDEFAULT;
            case PigmapPackage.OUTPUT_TABLE__REJECT_INNER_JOIN:
                return rejectInnerJoin != REJECT_INNER_JOIN_EDEFAULT;
            case PigmapPackage.OUTPUT_TABLE__ERROR_REJECT:
                return errorReject != ERROR_REJECT_EDEFAULT;
            case PigmapPackage.OUTPUT_TABLE__ALL_IN_ONE:
                return allInOne != ALL_IN_ONE_EDEFAULT;
            case PigmapPackage.OUTPUT_TABLE__ENABLE_EMPTY_ELEMENT:
                return enableEmptyElement != ENABLE_EMPTY_ELEMENT_EDEFAULT;
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
        result.append(" (reject: ");
        result.append(reject);
        result.append(", rejectInnerJoin: ");
        result.append(rejectInnerJoin);
        result.append(", errorReject: ");
        result.append(errorReject);
        result.append(", allInOne: ");
        result.append(allInOne);
        result.append(", enableEmptyElement: ");
        result.append(enableEmptyElement);
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
        OutputTableImpl other = (OutputTableImpl) obj;
        if (this.reject != other.reject) {
            return false;
        }
        if (this.rejectInnerJoin != other.rejectInnerJoin) {
            return false;
        }
        if (this.errorReject != other.errorReject) {
            return false;
        }
        if (this.enableEmptyElement != other.enableEmptyElement) {
            return false;
        }
        if (this.allInOne != other.allInOne) {
            return false;
        }
        return true;
    }

} //OutputTableImpl
