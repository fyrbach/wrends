/*
 * CDDL HEADER START
 *
 * The contents of this file are subject to the terms of the
 * Common Development and Distribution License, Version 1.0 only
 * (the "License").  You may not use this file except in compliance
 * with the License.
 *
 * You can obtain a copy of the license at
 * trunk/opends/resource/legal-notices/OpenDS.LICENSE
 * or https://OpenDS.dev.java.net/OpenDS.LICENSE.
 * See the License for the specific language governing permissions
 * and limitations under the License.
 *
 * When distributing Covered Code, include this CDDL HEADER in each
 * file and include the License file at
 * trunk/opends/resource/legal-notices/OpenDS.LICENSE.  If applicable,
 * add the following below this CDDL HEADER, with the fields enclosed
 * by brackets "[]" replaced with your own identifying * information:
 *      Portions Copyright [yyyy] [name of copyright owner]
 *
 * CDDL HEADER END
 *
 *
 *      Portions Copyright 2006 Sun Microsystems, Inc.
 */
package org.opends.server.api;



import java.util.LinkedHashMap;

import org.opends.server.types.DN;

import static org.opends.server.loggers.Debug.*;



/**
 * This class defines an interface that may be used to define a set of
 * alert notifications that may be generated by this Directory Server
 * component.  The notifications will be made available through JMX
 * and may be published through other mechanisms as well.
 */
public interface AlertGenerator
{
  /**
   * Retrieves the DN of the configuration entry with which this alert
   * generator is associated.
   *
   * @return  The DN of the configuration entry with which this alert
   *          generator is associated.
   */
  public DN getComponentEntryDN();



  /**
   * Retrieves the fully-qualified name of the Java class for this
   * alert generator implementation.
   *
   * @return  The fully-qualified name of the Java class for this
   *          alert generator implementation.
   */
  public String getClassName();



  /**
   * Retrieves information about the set of alerts that this generator
   * may produce.  The map returned should be between the notification
   * type for a particular notification and the human-readable
   * description for that notification.  This alert generator must not
   * generate any alerts with types that are not contained in this
   * list.
   *
   * @return  Information about the set of alerts that this generator
   *          may produce.
   */
  public LinkedHashMap<String,String> getAlerts();
}

