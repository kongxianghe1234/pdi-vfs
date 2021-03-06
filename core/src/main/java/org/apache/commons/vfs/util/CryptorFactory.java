/*!
* Copyright 2010 - 2013 Pentaho Corporation.  All rights reserved.
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
* http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*
*/

package org.apache.commons.vfs.util;

/**
 * Factory to create an instance of a Cryptor.
 * @author <a href="http://commons.apache.org/vfs/team-list.html">Commons VFS team</a>
 */
public final class CryptorFactory
{
    /**
     * The System property name to identify the Cryptor class to be used.
     */
    public static final String CRYPTOR_CLASS = "org.apache.commons.vfs.cryptor";

    private static Cryptor instance;

    /**
     * Prevent instantiation of the class.
     */
    private CryptorFactory()
    {

    }

    /**
     * Allows the Cryptor class to be set programmatically.
     * @param cryptor The Cryptor.
     */
    public static synchronized void setCryptor(Cryptor cryptor)
    {
        instance = cryptor;
    }

    /**
     * Return the Cryptor. If one has not been previously set, create it. The Cryptor class
     * can be set by setting the "org.apache.commons.vfs.cryptor" System property to the
     * name of the Cryptor class.
     * @return The Cryptor.
     */
    public static synchronized Cryptor getCryptor()
    {
        if (instance != null)
        {
            return instance;
        }

        String cryptorClass = System.getProperty(CRYPTOR_CLASS);
        if (cryptorClass != null)
        {
            try
            {
                Class clazz = Class.forName(cryptorClass);
                instance = (Cryptor) clazz.newInstance();
                return instance;
            }
            catch (Exception ex)
            {
                throw new RuntimeException("Unable to create Cryptor " + cryptorClass, ex);
            }
        }
        instance = new DefaultCryptor();
        return instance;
    }
}
