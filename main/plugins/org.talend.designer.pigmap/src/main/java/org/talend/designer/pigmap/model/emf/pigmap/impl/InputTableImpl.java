/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.talend.designer.pigmap.model.emf.pigmap.impl;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.talend.designer.pigmap.model.emf.pigmap.InputTable;
import org.talend.designer.pigmap.model.emf.pigmap.PigmapPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Input Table</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.talend.designer.pigmap.model.emf.pigmap.impl.InputTableImpl#isLookup <em>Lookup</em>}</li>
 *   <li>{@link org.talend.designer.pigmap.model.emf.pigmap.impl.InputTableImpl#getJoinModel <em>Join Model</em>}</li>
 *   <li>{@link org.talend.designer.pigmap.model.emf.pigmap.impl.InputTableImpl#getJoinOptimization <em>Join Optimization</em>}</li>
 *   <li>{@link org.talend.designer.pigmap.model.emf.pigmap.impl.InputTableImpl#getCustomPartitioner <em>Custom Partitioner</em>}</li>
 *   <li>{@link org.talend.designer.pigmap.model.emf.pigmap.impl.InputTableImpl#getIncreaseParallelism <em>Increase Parallelism</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class InputTableImpl extends AbstractInOutTableImpl implements InputTable {
    /**
     * The default value of the '{@link #isLookup() <em>Lookup</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isLookup()
     * @generated
     * @ordered
     */
    protected static final boolean LOOKUP_EDEFAULT = false;

    /**
     * The cached value of the '{@link #isLookup() <em>Lookup</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isLookup()
     * @generated
     * @ordered
     */
    protected boolean lookup = LOOKUP_EDEFAULT;

    /**
     * The default value of the '{@link #getJoinModel() <em>Join Model</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getJoinModel()
     * @generated
     * @ordered
     */
    protected static final String JOIN_MODEL_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getJoinModel() <em>Join Model</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getJoinModel()
     * @generated
     * @ordered
     */
    protected String joinModel = JOIN_MODEL_EDEFAULT;

    /**
     * The default value of the '{@link #getJoinOptimization() <em>Join Optimization</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getJoinOptimization()
     * @generated
     * @ordered
     */
    protected static final String JOIN_OPTIMIZATION_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getJoinOptimization() <em>Join Optimization</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getJoinOptimization()
     * @generated
     * @ordered
     */
    protected String joinOptimization = JOIN_OPTIMIZATION_EDEFAULT;

    /**
     * The default value of the '{@link #getCustomPartitioner() <em>Custom Partitioner</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getCustomPartitioner()
     * @generated
     * @ordered
     */
    protected static final String CUSTOM_PARTITIONER_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getCustomPartitioner() <em>Custom Partitioner</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getCustomPartitioner()
     * @generated
     * @ordered
     */
    protected String customPartitioner = CUSTOM_PARTITIONER_EDEFAULT;

    /**
     * The default value of the '{@link #getIncreaseParallelism() <em>Increase Parallelism</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getIncreaseParallelism()
     * @generated
     * @ordered
     */
    protected static final String INCREASE_PARALLELISM_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getIncreaseParallelism() <em>Increase Parallelism</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getIncreaseParallelism()
     * @generated
     * @ordered
     */
    protected String increaseParallelism = INCREASE_PARALLELISM_EDEFAULT;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected InputTableImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return PigmapPackage.Literals.INPUT_TABLE;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean isLookup() {
        return lookup;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setLookup(boolean newLookup) {
        boolean oldLookup = lookup;
        lookup = newLookup;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, PigmapPackage.INPUT_TABLE__LOOKUP, oldLookup, lookup));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String getJoinModel() {
        return joinModel;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setJoinModel(String newJoinModel) {
        String oldJoinModel = joinModel;
        joinModel = newJoinModel;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, PigmapPackage.INPUT_TABLE__JOIN_MODEL, oldJoinModel, joinModel));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String getJoinOptimization() {
        return joinOptimization;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setJoinOptimization(String newJoinOptimization) {
        String oldJoinOptimization = joinOptimization;
        joinOptimization = newJoinOptimization;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, PigmapPackage.INPUT_TABLE__JOIN_OPTIMIZATION, oldJoinOptimization, joinOptimization));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String getCustomPartitioner() {
        return customPartitioner;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setCustomPartitioner(String newCustomPartitioner) {
        String oldCustomPartitioner = customPartitioner;
        customPartitioner = newCustomPartitioner;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, PigmapPackage.INPUT_TABLE__CUSTOM_PARTITIONER, oldCustomPartitioner, customPartitioner));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String getIncreaseParallelism() {
        return increaseParallelism;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setIncreaseParallelism(String newIncreaseParallelism) {
        String oldIncreaseParallelism = increaseParallelism;
        increaseParallelism = newIncreaseParallelism;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, PigmapPackage.INPUT_TABLE__INCREASE_PARALLELISM, oldIncreaseParallelism, increaseParallelism));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public Object eGet(int featureID, boolean resolve, boolean coreType) {
        switch (featureID) {
            case PigmapPackage.INPUT_TABLE__LOOKUP:
                return isLookup();
            case PigmapPackage.INPUT_TABLE__JOIN_MODEL:
                return getJoinModel();
            case PigmapPackage.INPUT_TABLE__JOIN_OPTIMIZATION:
                return getJoinOptimization();
            case PigmapPackage.INPUT_TABLE__CUSTOM_PARTITIONER:
                return getCustomPartitioner();
            case PigmapPackage.INPUT_TABLE__INCREASE_PARALLELISM:
                return getIncreaseParallelism();
        }
        return super.eGet(featureID, resolve, coreType);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public void eSet(int featureID, Object newValue) {
        switch (featureID) {
            case PigmapPackage.INPUT_TABLE__LOOKUP:
                setLookup((Boolean)newValue);
                return;
            case PigmapPackage.INPUT_TABLE__JOIN_MODEL:
                setJoinModel((String)newValue);
                return;
            case PigmapPackage.INPUT_TABLE__JOIN_OPTIMIZATION:
                setJoinOptimization((String)newValue);
                return;
            case PigmapPackage.INPUT_TABLE__CUSTOM_PARTITIONER:
                setCustomPartitioner((String)newValue);
                return;
            case PigmapPackage.INPUT_TABLE__INCREASE_PARALLELISM:
                setIncreaseParallelism((String)newValue);
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
            case PigmapPackage.INPUT_TABLE__LOOKUP:
                setLookup(LOOKUP_EDEFAULT);
                return;
            case PigmapPackage.INPUT_TABLE__JOIN_MODEL:
                setJoinModel(JOIN_MODEL_EDEFAULT);
                return;
            case PigmapPackage.INPUT_TABLE__JOIN_OPTIMIZATION:
                setJoinOptimization(JOIN_OPTIMIZATION_EDEFAULT);
                return;
            case PigmapPackage.INPUT_TABLE__CUSTOM_PARTITIONER:
                setCustomPartitioner(CUSTOM_PARTITIONER_EDEFAULT);
                return;
            case PigmapPackage.INPUT_TABLE__INCREASE_PARALLELISM:
                setIncreaseParallelism(INCREASE_PARALLELISM_EDEFAULT);
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
            case PigmapPackage.INPUT_TABLE__LOOKUP:
                return lookup != LOOKUP_EDEFAULT;
            case PigmapPackage.INPUT_TABLE__JOIN_MODEL:
                return JOIN_MODEL_EDEFAULT == null ? joinModel != null : !JOIN_MODEL_EDEFAULT.equals(joinModel);
            case PigmapPackage.INPUT_TABLE__JOIN_OPTIMIZATION:
                return JOIN_OPTIMIZATION_EDEFAULT == null ? joinOptimization != null : !JOIN_OPTIMIZATION_EDEFAULT.equals(joinOptimization);
            case PigmapPackage.INPUT_TABLE__CUSTOM_PARTITIONER:
                return CUSTOM_PARTITIONER_EDEFAULT == null ? customPartitioner != null : !CUSTOM_PARTITIONER_EDEFAULT.equals(customPartitioner);
            case PigmapPackage.INPUT_TABLE__INCREASE_PARALLELISM:
                return INCREASE_PARALLELISM_EDEFAULT == null ? increaseParallelism != null : !INCREASE_PARALLELISM_EDEFAULT.equals(increaseParallelism);
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
        result.append(" (lookup: ");
        result.append(lookup);
        result.append(", joinModel: ");
        result.append(joinModel);
        result.append(", joinOptimization: ");
        result.append(joinOptimization);
        result.append(", customPartitioner: ");
        result.append(customPartitioner);
        result.append(", increaseParallelism: ");
        result.append(increaseParallelism);
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
        InputTableImpl other = (InputTableImpl) obj;
        if (this.lookup != other.lookup) {
            return false;
        }
        if (this.joinModel == null) {
            if (other.joinModel != null) {
                return false;
            }
        } else if (!this.joinModel.equals(other.joinModel)) {
            return false;
        }
        if (this.joinOptimization == null) {
            if (other.joinOptimization != null) {
                return false;
            }
        } else if (!this.joinOptimization.equals(other.joinOptimization)) {
            return false;
        }
        if (this.customPartitioner == null) {
            if (other.customPartitioner != null) {
                return false;
            }
        } else if (!this.customPartitioner.equals(other.customPartitioner)) {
            return false;
        }
        if (this.increaseParallelism == null) {
            if (other.increaseParallelism != null) {
                return false;
            }
        } else if (!this.increaseParallelism.equals(other.increaseParallelism)) {
            return false;
        }
        
        return true;
    }
} //InputTableImpl
