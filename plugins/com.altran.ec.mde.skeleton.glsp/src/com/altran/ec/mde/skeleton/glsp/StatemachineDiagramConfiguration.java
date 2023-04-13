/********************************************************************************
 * Copyright (c) 2022 EclipseSource and others.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * https://www.eclipse.org/legal/epl-2.0.
 *
 * This Source Code may also be made available under the following Secondary
 * Licenses when the conditions for such availability set forth in the Eclipse
 * Public License v. 2.0 are satisfied: GNU General Public License, version 2
 * with the GNU Classpath Exception which is available at
 * https://www.gnu.org/software/classpath/license.html.
 *
 * SPDX-License-Identifier: EPL-2.0 OR GPL-2.0 WITH Classpath-exception-2.0
 ********************************************************************************/
package com.altran.ec.mde.skeleton.glsp;

import java.util.List;

import org.eclipse.glsp.server.diagram.BaseDiagramConfiguration;
import org.eclipse.glsp.server.types.EdgeTypeHint;
import org.eclipse.glsp.server.types.ShapeTypeHint;

public class StatemachineDiagramConfiguration extends BaseDiagramConfiguration {

   @Override
   public List<ShapeTypeHint> getShapeTypeHints() {
      // states can be moved, deleted and resized
      return List.of(new ShapeTypeHint(StatemachineModelTypes.STATE, true, true, true, false));
   }

   @Override
   public List<EdgeTypeHint> getEdgeTypeHints() {
      return List.of(new EdgeTypeHint(StatemachineModelTypes.TRANSITION, true, true, true,
            List.of(StatemachineModelTypes.STATE), List.of(StatemachineModelTypes.STATE)));
   }

}
