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

import org.codehaus.enunciate.json.JsonName;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.annotate.JsonTypeInfo;
import org.codehaus.jackson.map.annotate.JsonTypeIdResolver;
import org.gedcomx.common.GenealogicalResource;
import org.gedcomx.common.ResourceReference;
import org.gedcomx.rt.*;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.util.List;

/**
 * A field on a record.
 */
@JsonTypeInfo ( use =JsonTypeInfo.Id.CUSTOM, property = XmlTypeIdResolver.TYPE_PROPERTY_NAME)
@JsonTypeIdResolver (XmlTypeIdResolver.class)
@XmlType ( name = "Field", propOrder = {"fieldValues", "source" } )
public abstract class Field extends GenealogicalResource {

  private String label;
  private List<FieldValue> fieldValues;
  private ResourceReference source;

  /**
   * A label for the field. The label can be used to associate fields that were taken from the same section of
   * the source, such as identified by an indexing template.
   *
   * @return The field label.
   */
  @XmlAttribute
  public String getLabel() {
    return label;
  }

  /**
   * A label for the field. The label can be used to associate fields that were taken from the same section of
   * the source, such as identified by an indexing template.
   *
   * @param label The field label.
   */
  public void setLabel(String label) {
    this.label = label;
  }

  /**
   * The list of field values of the field.
   *
   * @return The list of field values of the field.
   */
  @XmlElement(name="fieldValue")
  @JsonProperty("fieldValues")
  @JsonName("fieldValues")
  public List<FieldValue> getFieldValues() {
    return fieldValues;
  }

  /**
   * The list of field values of the field.
   *
   * @param fieldValues The list of field values of the field.
   */
  @JsonProperty ("fieldValues")
  public void setFieldValues(List<FieldValue> fieldValues) {
    this.fieldValues = fieldValues;
  }

  /**
   * The source for the field.
   *
   * @return The source for the field.
   */
  @RDFSubPropertyOf ( CommonModels.DUBLIN_CORE_NAMESPACE + "source")
  @RDFDomain ({}) //any resource can be identified persistently.
  @RDFRange ({}) //any resource can be identified as a source.
  @SuppressWarnings("rdf:no_range")
  public ResourceReference getSource() {
    return source;
  }

  /**
   * The source for the field.
   *
   * @param source The source for the field.
   */
  public void setSource(ResourceReference source) {
    this.source = source;
  }

  /**
   * Provide a simple toString() method.
   */
  @Override
  public String toString() {
    String s;

    // Show one of the following values arranged in priority: last in list.
    if (fieldValues != null && fieldValues.size() > 0 && (! fieldValues.get(fieldValues.size() - 1).toString().isEmpty())) {
      s = fieldValues.get(fieldValues.size() - 1).toString();
    } else {
      s = "";
    }

    return s;
  }
}
