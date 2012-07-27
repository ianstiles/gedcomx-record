/**
 * Copyright 2011 Intellectual Reserve, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.gedcomx.record;

import org.codehaus.jackson.annotate.JsonTypeInfo;
import org.codehaus.jackson.map.annotate.JsonTypeIdResolver;
import org.gedcomx.common.Attribution;
import org.gedcomx.common.FormalValue;
import org.gedcomx.rt.XmlTypeIdResolver;

import javax.xml.bind.annotation.XmlType;

/**
 * A value of a field.
 */
@JsonTypeInfo( use =JsonTypeInfo.Id.CUSTOM, property = XmlTypeIdResolver.TYPE_PROPERTY_NAME)
@JsonTypeIdResolver(XmlTypeIdResolver.class)
@XmlType( name = "FieldValue", propOrder = {"text", "formal", "attribution" } )
public final class FieldValue implements org.gedcomx.common.Attributable {
  private String text;
  private FormalValue formal;
  private org.gedcomx.common.Attribution attribution;

  /**
   * Text directly extracted from the record field. What you see is what you get, including misspellings and other errors.
   *
   * @return Text directly extracted from the record field. What you see is what you get, including misspellings and other errors.
   */
  public String getText() {
    return text;
  }

  /**
   * Text directly extracted from the record field. What you see is what you get, including misspellings and other errors.
   *
   * @param text Text directly extracted from the record field. What you see is what you get, including misspellings and other errors.
   */
  public void setText(String text) {
    this.text = text;
  }

  /**
   * The discreet, formal value of the field as supplied by a user or applied by an algorithm based on the original and interpreted values.
   *
   * @return The discreet, formal value of the field as supplied by a user or applied by an algorithm based on the original and interpreted values.
   */
  public FormalValue getFormal() {
    return formal;
  }

  /**
   * The discreet, formal value of the field as supplied by a user or applied by an algorithm based on the original and interpreted values.
   *
   * @param formal The discreet, formal value of the field as supplied by a user or applied by an algorithm based on the original and interpreted values.
   */
  public void setFormal(FormalValue formal) {
    this.formal = formal;
  }


  public Attribution getAttribution() {
    return attribution;
  }

  public void setAttribution(Attribution attribution) {
    this.attribution = attribution;
  }

  /**
   * Provide a simple toString() method.
   */
  @Override
  public String toString() {
    String s;

    // Show one of the following values arranged in priority: Processed, Interpreted or Original
    if ((formal != null) && (! formal.toString().isEmpty())) {
      s = formal.toString();
    } else if ((text != null) && (! text.isEmpty())) {
      s = text;
    } else {
      s = "";
    }

    return s;
  }
}
