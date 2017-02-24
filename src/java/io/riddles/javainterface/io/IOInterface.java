/*
 * Copyright 2016 riddles.io (developers@riddles.io)
 *
 *     Licensed under the Apache License, Version 2.0 (the "License");
 *     you may not use this file except in compliance with the License.
 *     You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *     Unless required by applicable law or agreed to in writing, software
 *     distributed under the License is distributed on an "AS IS" BASIS,
 *     WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *     See the License for the specific language governing permissions and
 *     limitations under the License.
 *
 *     For the full copyright and license information, please view the LICENSE
 *     file that was distributed with this source code.
 */

package io.riddles.javainterface.io;

import java.io.IOException;

/**
 * io.riddles.javainterface.io.IO - Created on 15-9-16
 *
 * [description]
 *
 * @author Jim van Eeden - jim@riddles.io
 */
public interface IOInterface {

    void sendMessage(String message);

    void waitForMessage(String expected);

    String getNextMessage() throws IOException;
}
