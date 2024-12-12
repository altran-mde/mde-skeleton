package org.eclipse.xtext.example.fowlerdsl.glsp.server.idgen;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.glsp.server.emf.EMFIdGenerator;

/**
 * An ID generator that uses the fragment as a unique identifier for an element. If this strategy is used, the
 * fragment should be considered unique across all operations. For instance, if an element is removed within a list, all
 * subsequent elements are moved ahead. If the index position is considered in the fragment, objects may be identified
 * incorrectly.
 */
public class FragmentIdGenerator implements EMFIdGenerator {
   @Override
   public String getOrCreateId(final EObject element) {
      return EcoreUtil.getURI(element).fragment();
   }
}
