/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.talend.designer.pigmap.model.emf.pigmap;

import org.eclipse.emf.common.util.EList;

import org.talend.designer.gefabstractmap.model.abstractmap.MapperTable;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Var Table</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.talend.designer.pigmap.model.emf.pigmap.VarTable#getName <em>Name</em>}</li>
 *   <li>{@link org.talend.designer.pigmap.model.emf.pigmap.VarTable#isMinimized <em>Minimized</em>}</li>
 *   <li>{@link org.talend.designer.pigmap.model.emf.pigmap.VarTable#getNodes <em>Nodes</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.talend.designer.pigmap.model.emf.pigmap.PigmapPackage#getVarTable()
 * @model
 * @generated
 */
public interface VarTable extends MapperTable {
    /**
     * Returns the value of the '<em><b>Name</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Name</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Name</em>' attribute.
     * @see #setName(String)
     * @see org.talend.designer.pigmap.model.emf.pigmap.PigmapPackage#getVarTable_Name()
     * @model
     * @generated
     */
    String getName();

    /**
     * Sets the value of the '{@link org.talend.designer.pigmap.model.emf.pigmap.VarTable#getName <em>Name</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Name</em>' attribute.
     * @see #getName()
     * @generated
     */
    void setName(String value);

    /**
     * Returns the value of the '<em><b>Minimized</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Minimized</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Minimized</em>' attribute.
     * @see #setMinimized(boolean)
     * @see org.talend.designer.pigmap.model.emf.pigmap.PigmapPackage#getVarTable_Minimized()
     * @model
     * @generated
     */
    boolean isMinimized();

    /**
     * Sets the value of the '{@link org.talend.designer.pigmap.model.emf.pigmap.VarTable#isMinimized <em>Minimized</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Minimized</em>' attribute.
     * @see #isMinimized()
     * @generated
     */
    void setMinimized(boolean value);

    /**
     * Returns the value of the '<em><b>Nodes</b></em>' containment reference list.
     * The list contents are of type {@link org.talend.designer.pigmap.model.emf.pigmap.VarNode}.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Nodes</em>' containment reference list isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Nodes</em>' containment reference list.
     * @see org.talend.designer.pigmap.model.emf.pigmap.PigmapPackage#getVarTable_Nodes()
     * @model containment="true"
     * @generated
     */
    EList<VarNode> getNodes();

} // VarTable
