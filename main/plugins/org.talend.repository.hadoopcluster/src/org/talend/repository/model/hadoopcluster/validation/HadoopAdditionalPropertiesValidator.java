/**
 *
 * $Id$
 */
package org.talend.repository.model.hadoopcluster.validation;


/**
 * A sample validator interface for {@link java.util.Map.Entry}.
 * This doesn't really do anything, and it's not a real EMF artifact.
 * It was generated by the org.eclipse.emf.examples.generator.validator plug-in to illustrate how EMF's code generator can be extended.
 * This can be disabled with -vmargs -Dorg.eclipse.emf.examples.generator.validator=false.
 */
public interface HadoopAdditionalPropertiesValidator {
    boolean validate();

    boolean validateTypedKey(String value);
    boolean validateTypedValue(String value);
}
