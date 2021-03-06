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

package org.apache.commons.vfs.provider;

import org.apache.commons.vfs.FileObject;
import org.apache.commons.vfs.FileSystemException;
import org.apache.commons.vfs.FileSystemOptions;

/**
 * Description.
 *
 * @author <a href="mailto:imario@apache.org">Mario Ivankovits</a>
 * @version $Revision: 804548 $ $Date: 2009-08-15 22:12:32 -0400 (Sat, 15 Aug 2009) $
 */
public abstract class CompositeFileProvider extends AbstractFileProvider
{
    private static final int INITIAL_BUFSZ = 80;

    public CompositeFileProvider()
    {
        super();
    }

    /**
     * The schemes to use for resolve
     */
    protected abstract String[] getSchemes();

    /**
     * Locates a file object, by absolute URI.
     * @param baseFile The base FileObject.
     * @param uri The file to find.
     * @param fileSystemOptions The options for the FileSystem.
     * @return A FileObject for the located file.
     * @throws FileSystemException if an error occurs.
     */
    public FileObject findFile(final FileObject baseFile,
                               final String uri,
                               final FileSystemOptions fileSystemOptions)
        throws FileSystemException
    {
        StringBuffer buf = new StringBuffer(INITIAL_BUFSZ);

        UriParser.extractScheme(uri, buf);

        String[] schemes = getSchemes();
        for (int iterSchemes = 0; iterSchemes < schemes.length; iterSchemes++)
        {
            buf.insert(0, ":");
            buf.insert(0, schemes[iterSchemes]);
        }

        FileObject fo = getContext().getFileSystemManager().resolveFile(buf.toString(), fileSystemOptions);
        return fo;
    }
}
