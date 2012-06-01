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
import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.annotate.JsonTypeInfo;
import org.codehaus.jackson.map.annotate.JsonTypeIdResolver;
import org.gedcomx.common.*;
import org.gedcomx.rt.*;
import org.gedcomx.types.RecordType;
import org.gedcomx.types.TypeReference;

import javax.xml.XMLConstants;
import javax.xml.bind.annotation.*;
import java.util.List;

/**
 * A record.
 */
@XmlRootElement
@JsonElementWrapper ( name = "records" )
@JsonTypeInfo ( use =JsonTypeInfo.Id.CUSTOM, property = XmlTypeIdResolver.TYPE_PROPERTY_NAME)
@JsonTypeIdResolver (XmlTypeIdResolver.class)
@XmlType ( name = "Record", propOrder = { "persistentId", "identifiers", "sources", "type", "personas", "relationships", "facts" } )
public class Record extends GenealogicalResource implements PersistentIdentifiable, HasFacts {

  private String lang;
  private URI persistentId;
  private List<Identifier> identifiers;
  private List<ResourceReference> sources;
  @XmlElement (namespace = CommonModels.RDF_NAMESPACE)
  @JsonProperty
  private TypeReference<RecordType> type;
  private List<Persona> personas;
  private List<Fact> facts;
  private List<Relationship> relationships;

  /**
   * A long-term, persistent, globally unique identifier for this record.
   *
   * @return A long-term, persistent, globally unique identifier for this record.
   */
  @XmlSchemaType (name = "anyURI", namespace = XMLConstants.W3C_XML_SCHEMA_NS_URI)
  public URI getPersistentId() {
    return persistentId;
  }

  /**
   * A long-term, persistent, globally unique identifier for this record.
   *
   * @param persistentId A long-term, persistent, globally unique identifier for this record.
   */
  public void setPersistentId(URI persistentId) {
    this.persistentId = persistentId;
  }

  /**
   * The list of identifiers of the record.
   *
   * @return The list of identifiers of the record.
   */
  @XmlElement (name="identifier")
  @JsonProperty ("identifiers")
  @JsonName ("identifiers")
  public List<Identifier> getIdentifiers() {
    return identifiers;
  }

  /**
   * The list of identifiers of the record.
   *
   * @param identifiers The list of identifiers of the record.
   */
  @JsonProperty ("identifiers")
  public void setIdentifiers(List<Identifier> identifiers) {
    this.identifiers = identifiers;
  }

  /**
   * The source references for a record.
   *
   * @return The source references for a record.
   */
  @XmlElement (name="source")
  @JsonProperty ("sources")
  @JsonName ("sources")
  @RDFSubPropertyOf ( CommonModels.DUBLIN_CORE_NAMESPACE + "source")
  @RDFDomain ({}) //any resource can be identified persistently.
  @RDFRange ({}) //any resource can be identified as a source.
  @SuppressWarnings("rdf:no_range")
  public List<ResourceReference> getSources() {
    return sources;
  }

  /**
   * The source references for a record.
   *
   * @param sources The source references for a record.
   */
  @JsonProperty("sources")
  public void setSources(List<ResourceReference> sources) {
    this.sources = sources;
  }

  /**
   * The type of the record.
   * 
   * @return The type of the record.
   */
  @XmlTransient
  @JsonIgnore
  public URI getType() {
    return this.type == null ? null : this.type.getType();
  }

  /**
   * The type of the record.
   * 
   * @param type The type of the record.
   */
  @JsonIgnore
  public void setType(URI type) {
    this.type = type == null ? null : new TypeReference<RecordType>(type);
  }

  /**
   * The enum referencing the known type of the record, or {@link org.gedcomx.types.RecordType#OTHER} if not known.
   * 
   * @return The enum referencing the known type of the record, or {@link org.gedcomx.types.RecordType#OTHER} if not known.
   */
  @XmlTransient
  @JsonIgnore
  public RecordType getKnownType() {
    return this.type == null ? null : RecordType.fromQNameURI(this.type.getType());
  }

  /**
   * The enum referencing the known type of the record, or {@link org.gedcomx.types.RecordType#OTHER} if not known.
   * 
   * @param knownType The enum referencing the known type of the record, or {@link org.gedcomx.types.RecordType#OTHER} if not known.
   */
  @JsonIgnore
  public void setKnownType(RecordType knownType) {
    this.type = knownType == null ? null : new TypeReference<RecordType>(knownType);
  }

  /**
   * The language of the record. See <a href="http://www.w3.org/International/articles/language-tags/">http://www.w3.org/International/articles/language-tags/</a>
   *
   * @return The language of the record.
   */
  @XmlAttribute( namespace = XMLConstants.XML_NS_URI )
  public String getLang() {
    return lang;
  }

  /**
   * The language of the record. See <a href="http://www.w3.org/International/articles/language-tags/">http://www.w3.org/International/articles/language-tags/</a>
   *
   * @param lang The language of the record.
   */
  public void setLang(String lang) {
    this.lang = lang;
  }

  /**
   * The personas of this record.
   *
   * @return The personas of this record.
   */
  @XmlElement(name = "persona")
  @JsonProperty("personas")
  @JsonName("personas")
  public List<Persona> getPersonas() {
    return personas;
  }

  /**
   * The personas of this record.
   *
   * @param personas The personas of this record.
   */
  @JsonProperty("personas")
  public void setPersonas(List<Persona> personas) {
    this.personas = personas;
  }

  /**
   * Facts described by the record outside the scope of a persona or relationship.
   *
   * @return Facts described by the record outside the scope of a persona or relationship.
   */
  @XmlElement(name = "fact")
  @JsonProperty("facts")
  @JsonName("facts")
  public List<Fact> getFacts() {
    return facts;
  }

  /**
   * Facts described by the record outside the scope of a persona or relationship.
   *
   * @param facts Facts described by the record outside the scope of a persona or relationship.
   */
  @JsonProperty("facts")
  public void setFacts(List<Fact> facts) {
    this.facts = facts;
  }

  /**
   * The relationships on this record.
   *
   * @return The relationships on this record.
   */
  @XmlElement(name = "relationship")
  @JsonProperty("relationships")
  @JsonName("relationships")
  public List<Relationship> getRelationships() {
    return relationships;
  }

  /**
   * The relationships on this record.
   *
   * @param relationships The relationships on this record.
   */
  @JsonProperty("relationships")
  public void setRelationships(List<Relationship> relationships) {
    this.relationships = relationships;
  }

  /**
   * Provide a simple toString() method.
   */
  @Override
  public String toString() {
    return (getId() == null) ? "" : getId();
  }
}
