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
package org.opends.server.types;



import java.util.List;
import java.util.Random;

import org.opends.server.config.ConfigException;

import static org.opends.server.loggers.Debug.*;
import static org.opends.server.messages.MessageHandler.*;
import static org.opends.server.messages.UtilityMessages.*;
import static org.opends.server.util.StaticUtils.*;



/**
 * This class provides a data structure that makes it possible to
 * associate a name with a given set of characters.  The name must
 * consist only of ASCII alphabetic characters.
 */
public class NamedCharacterSet
{
  /**
   * The fully-qualified name of this class for debugging purposes.
   */
  private static final String CLASS_NAME =
       "org.opends.server.types.NamedCharacterSet";



  // The characters contained in this character set.
  private char[] characters;

  // The random number generator to use with this character set.
  private Random random;

  // The name assigned to this character set.
  private String name;



  /**
   * Creates a new named character set with the provided information.
   *
   * @param  name        The name for this character set.
   * @param  characters  The characters to include in this character
   *                     set.
   *
   * @throws  ConfigException  If the provided name contains one or
   *                           more illegal characters.
   */
  public NamedCharacterSet(String name, char[] characters)
         throws ConfigException
  {
    assert debugConstructor(CLASS_NAME, String.valueOf(name),
                            String.valueOf(characters));

    this.name       = name;
    this.characters = characters;

    random     = new Random();

    if ((name == null) || (name.length() == 0))
    {
      int msgID = MSGID_CHARSET_CONSTRUCTOR_NO_NAME;
      String message = getMessage(msgID);
      throw new ConfigException(msgID, message);
    }

    for (int i=0; i < name.length(); i++)
    {
      if (! isAlpha(name.charAt(i)))
      {
        int    msgID   = MSGID_CHARSET_CONSTRUCTOR_INVALID_NAME_CHAR;
        String message = getMessage(msgID,
                              String.valueOf(name.charAt(i)), i);
        throw new ConfigException(msgID, message);
      }
    }
  }



  /**
   * Creates a new named character set with the provided information.
   *
   * @param  name        The name for this character set.
   * @param  characters  The characters to include in this character
   *                     set.
   * @param  random      The random number generator to use with this
   *                     character set.
   *
   * @throws  ConfigException  If the provided name contains one or
   *                           more illegal characters.
   */
  public NamedCharacterSet(String name, char[] characters,
                           Random random)
         throws ConfigException
  {
    assert debugConstructor(CLASS_NAME, String.valueOf(name),
                            String.valueOf(characters),
                            String.valueOf(random));

    this.name       = name;
    this.characters = characters;
    this.random     = random;

    if ((name == null) || (name.length() == 0))
    {
      int msgID = MSGID_CHARSET_CONSTRUCTOR_NO_NAME;
      String message = getMessage(msgID);
      throw new ConfigException(msgID, message);
    }

    for (int i=0; i < name.length(); i++)
    {
      if (! isAlpha(name.charAt(i)))
      {
        int    msgID   = MSGID_CHARSET_CONSTRUCTOR_INVALID_NAME_CHAR;
        String message = getMessage(msgID,
                              String.valueOf(name.charAt(i)), i);
        throw new ConfigException(msgID, message);
      }
    }
  }



  /**
   * Retrieves the name for this character set.
   *
   * @return  The name for this character set.
   */
  public String getName()
  {
    assert debugEnter(CLASS_NAME, "getName");

    return name;
  }



  /**
   * Retrieves the characters included in this character set.
   *
   * @return  The characters included in this character set.
   */
  public char[] getCharacters()
  {
    assert debugEnter(CLASS_NAME, "getCharacters");

    return characters;
  }



  /**
   * Retrieves a character at random from this named character set.
   *
   * @return  The randomly-selected character from this named
   *          character set;
   */
  public char getRandomCharacter()
  {
    assert debugEnter(CLASS_NAME, "getRandomCharacter");

    if ((characters == null) || (characters.length == 0))
    {
      return 0;
    }

    return characters[random.nextInt(characters.length)];
  }



  /**
   * Appends the specified number of characters chosen at random from
   * this character set to the provided buffer.
   *
   * @param  buffer  The buffer to which the characters should be
   *                 appended.
   * @param  count   The number of characters to append to the
   *                 provided buffer.
   */
  public void getRandomCharacters(StringBuilder buffer, int count)
  {
    assert debugEnter(CLASS_NAME, "getRandomCharacters",
                      "java.lang.StringBuilder",
                      String.valueOf(count));

    if ((characters == null) || (characters.length == 0))
    {
      return;
    }

    for (int i=0; i < count; i++)
    {
      buffer.append(characters[random.nextInt(characters.length)]);
    }
  }



  /**
   * Encodes this character set to a form suitable for use in the
   * value of a configuration attribute.
   *
   * @return  The encoded character set in a form suitable for use in
   *          the value of a configuration attribute.
   */
  public String encode()
  {
    return name + ":" + new String(characters);
  }



  /**
   * Decodes the values of the provided configuration attribute as a
   * set of character set definitions.
   *
   * @param  values  The set of encoded character set values to
   *                 decode.
   *
   * @return  The decoded character set definitions.
   *
   * @throws  ConfigException  If a problem occurs while attempting to
   *                           decode the character set definitions.
   */
  public static NamedCharacterSet[]
                     decodeCharacterSets(List<String> values)
         throws ConfigException
  {
    NamedCharacterSet[] sets = new NamedCharacterSet[values.size()];
    for (int i=0; i < sets.length; i++)
    {
      String value = values.get(i);
      int colonPos = value.indexOf(':');
      if (colonPos < 0)
      {
        int msgID = MSGID_CHARSET_NO_COLON;
        String message = getMessage(msgID, String.valueOf(value));
        throw new ConfigException(msgID, message);
      }
      else if (colonPos == 0)
      {
        int msgID = MSGID_CHARSET_NO_NAME;
        String message = getMessage(msgID, String.valueOf(value));
        throw new ConfigException(msgID, message);
      }
      else if (colonPos == (value.length() - 1))
      {
        int msgID = MSGID_CHARSET_NO_CHARS;
        String message = getMessage(msgID, String.valueOf(value));
        throw new ConfigException(msgID, message);
      }
      else
      {
        String name       = value.substring(0, colonPos);
        char[] characters = value.substring(colonPos+1).toCharArray();
        sets[i] = new NamedCharacterSet(name, characters);
      }
    }

    return sets;
  }
}

