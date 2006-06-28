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
package org.opends.server.tools;

import org.opends.server.types.DereferencePolicy;
import org.opends.server.types.SearchScope;

import static org.opends.server.loggers.Debug.*;
import static org.opends.server.messages.MessageHandler.*;
import static org.opends.server.messages.ToolMessages.*;
import static org.opends.server.types.DereferencePolicy.*;
import static org.opends.server.types.SearchScope.*;



/**
 * This class defines options for the search operations used
 * by the ldapsearch tool.
 */
public class LDAPSearchOptions extends LDAPToolOptions
{
  /**
   * The fully-qualified name of this class for debugging purposes.
   */
  private static final String CLASS_NAME =
       "org.opends.server.tools.LDAPSearchOptions";

  private DereferencePolicy dereferencePolicy =  NEVER_DEREF_ALIASES;
  private SearchScope searchScope = WHOLE_SUBTREE;
  private int sizeLimit = 0;
  private int timeLimit = 0;
  private boolean typesOnly = false;

  /**
   * Creates the options instance.
   *
   */
  public LDAPSearchOptions()
  {
    assert debugConstructor(CLASS_NAME);
  }

  /**
   * Set the timeLimit for the operation.
   *
   * @param timeLimit    The time limit for the search.
   *
   */

  public void setTimeLimit(int timeLimit)
  {
    this.timeLimit = timeLimit;
  }

  /**
   * Return the timeLimit value.
   *
   * @return  The timeLimit value.
   */
  public int getTimeLimit()
  {
    return timeLimit;
  }

  /**
   * Set the sizeLimit for the operation.
   *
   * @param sizeLimit    The size limit for the search.
   *
   */

  public void setSizeLimit(int sizeLimit)
  {
    this.sizeLimit = sizeLimit;
  }

  /**
   * Return the sizeLimit value.
   *
   * @return  The sizeLimit value.
   */
  public int getSizeLimit()
  {
    return sizeLimit;
  }

  /**
   * Set the search scope .
   *
   * @param  scope  The search scope string.
   *
   * @return  <CODE>true</CODE> if the scope was set properly, or
   *          <CODE>false</CODE> if not.
   */

  public boolean setSearchScope(String scope)
  {
      if(scope == null)
      {
        searchScope = WHOLE_SUBTREE;
      }
      else if(scope.equals("base"))
      {
        searchScope = BASE_OBJECT;
      } else if(scope.equals("one"))
      {
        searchScope = SINGLE_LEVEL;
      } else if (scope.equals("sub"))
      {
        searchScope = WHOLE_SUBTREE;
      } else if (scope.equals("subordinate"))
      {
        searchScope = SUBORDINATE_SUBTREE;
      } else
      {
        int msgID = MSGID_SEARCH_INVALID_SEARCH_SCOPE;
        System.err.println(getMessage(msgID, scope));
        return false;
      }
      return true;
  }

  /**
   * Get the search scope value.
   *
   * @return  The search scope value.
   */
  public SearchScope getSearchScope()
  {
    return searchScope;
  }

  /**
   * Set the dereference policy.
   *
   * @param policy    The dereference policy.
   *
   * @return  <CODE>true</CODE> if the dereference policy was set properly, or
   *          <CODE>false</CODE> if not.
   */

  public boolean setDereferencePolicy(String policy)
  {
      if(policy == null)
      {
        dereferencePolicy = NEVER_DEREF_ALIASES;
      } else if(policy.equals("never"))
      {
        dereferencePolicy = NEVER_DEREF_ALIASES;
      } else if(policy.equals("always"))
      {
        dereferencePolicy = DEREF_ALWAYS;
      } else if (policy.equals("search"))
      {
        dereferencePolicy = DEREF_IN_SEARCHING;
      } else if (policy.equals("find"))
      {
        dereferencePolicy = DEREF_FINDING_BASE_OBJECT;
      } else
      {
        System.err.println("Invalid deref alias specified:" + policy);
        return false;
      }
      return true;
  }

  /**
   * Return the dereference policy.
   *
   * @return  The alias dereference policy.
   */
  public DereferencePolicy getDereferencePolicy()
  {
    return dereferencePolicy;
  }

  /**
   * Return only the attribute types in the search result.
   *
   * @return  <CODE>true</CODE> if only attribute types should be returned in
   *          matching entries, or <CODE>false</CODE> if both types and values
   *          should be included.
   */
  public boolean getTypesOnly()
  {
    return this.typesOnly;
  }


  /**
   * Return only the attribute types in the search result.
   *
   * @param  typesOnly  Specifies whether only attribute types should be
   *                    returned in matching entries, or both types and values.
   */
  public void setTypesOnly(boolean typesOnly)
  {
    this.typesOnly = typesOnly;
  }

}

