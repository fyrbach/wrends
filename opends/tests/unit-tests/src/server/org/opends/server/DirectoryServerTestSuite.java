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
package org.opends.server;



import junit.framework.*;
import org.opends.server.core.*;
import org.opends.server.protocols.asn1.*;
import org.opends.server.backends.jeb.JebTestSuite;



/**
 * This class defines the base test suite for the Directory Server.  Whenever a
 * new set of tests are created, then they should be added into a
 * package-specific test suite, and then that package-specific suite should be
 * added to this overall test suite.
 *
 *
 * @author   Neil A. Wilson
 */
public class DirectoryServerTestSuite
       extends TestCase
{
  /**
   * Retrieves a test suite containing all of the Directory Server tests.
   *
   * @return  A test suite containing all of the Directory Server tests.
   */
  public static Test suite()
  {
    TestSuite directorySuite = new TestSuite("DirectoryServer Unit Tests");

    directorySuite.addTest(ASN1TestSuite.suite());
    directorySuite.addTest(JebTestSuite.suite());
    directorySuite.addTest(CoreTestSuite.suite());

    return directorySuite;
  }
}

