/*
  Copyright (c) 2016, Oracle and/or its affiliates. All rights reserved.

  The MySQL Connector/J is licensed under the terms of the GPLv2
  <http://www.gnu.org/licenses/old-licenses/gpl-2.0.html>, like most MySQL Connectors.
  There are special exceptions to the terms and conditions of the GPLv2 as it is applied to
  this software, see the FOSS License Exception
  <http://www.mysql.com/about/legal/licensing/foss-exception.html>.

  This program is free software; you can redistribute it and/or modify it under the terms
  of the GNU General Public License as published by the Free Software Foundation; version 2
  of the License.

  This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
  without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
  See the GNU General Public License for more details.

  You should have received a copy of the GNU General Public License along with this
  program; if not, write to the Free Software Foundation, Inc., 51 Franklin St, Fifth
  Floor, Boston, MA 02110-1301  USA

 */

package com.mysql.cj.mysqla.io;

import com.mysql.cj.api.mysqla.io.StructureFactory;
import com.mysql.cj.api.mysqla.result.ProtocolStructure;
import com.mysql.cj.api.mysqla.result.Resultset;
import com.mysql.cj.api.mysqla.result.Resultset.Concurrency;
import com.mysql.cj.api.mysqla.result.Resultset.Type;
import com.mysql.cj.api.mysqla.result.ResultsetRows;
import com.mysql.cj.core.exceptions.ExceptionFactory;
import com.mysql.cj.core.exceptions.WrongArgumentException;
import com.mysql.cj.mysqla.result.MysqlaResultset;
import com.mysql.cj.mysqla.result.OkPacket;

public class ResultsetFactory implements StructureFactory<Resultset> {

    private Type type = Type.FORWARD_ONLY;
    private Concurrency concurrency = Concurrency.READ_ONLY;

    public ResultsetFactory(Type type, Concurrency concurrency) {
        this.type = type;
        this.concurrency = concurrency;
    }

    public Resultset.Type getResultSetType() {
        return this.type;
    }

    public Resultset.Concurrency getResultSetConcurrency() {
        return this.concurrency;
    }

    @Override
    public Resultset createFromProtocolStructure(ProtocolStructure protocolStructure) {
        if (protocolStructure instanceof OkPacket) {
            return new MysqlaResultset((OkPacket) protocolStructure);

        } else if (protocolStructure instanceof ResultsetRows) {
            return new MysqlaResultset((ResultsetRows) protocolStructure);

        }
        throw ExceptionFactory.createException(WrongArgumentException.class, "Unknown ProtocolStructure class " + protocolStructure);
    }

}
