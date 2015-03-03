/**
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.camel.example.etl;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Eine Klasse, die aus Getter/Setter-Methoden besteht.
 * The JAXB2 POJO used to parse the XML
 *
 * @author Thomas Taschner <thomas.taschner1995@hotmail.com>
 * @author Michael Weinberger <mweinberger@student.tgm.ac.at>
 */
 //Das Hauptelement im XML-File ist mit "Person" benannt.
@XmlRootElement(name = "person")
//Hier wird festgelegt, ein Feld auszulesen
@XmlAccessorType(XmlAccessType.FIELD)
public class PersonDocument {
	//Die Attribute aus dem  XML-File.
    @XmlAttribute
    private String user;
    @XmlElement
    private String firstName;
    @XmlElement
    private String lastName;
    @XmlElement
    private String city;

	/*
	* Gibt den Username zurueck.
	*/
    @Override
    public String toString() {
        return "Person[user: " + user + "]";
    }
	
	/*
	* Liefert City zurueck.
	*/
    public String getCity() {
        return city;
    }

	/*
	* Setzt City auf einen beliebigen mitgelieferten Wert.
	*/
    public void setCity(String city) {
        this.city = city;
    }

	/*
	* Liefert FirstName zurueck.
	*/
    public String getFirstName() {
        return firstName;
    }

	/*
	* Setzt FirstName auf einen beliebigen mitgelieferten Wert.
	*/
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

	/*
	* Liefert LastName zurueck.
	*/
    public String getLastName() {
        return lastName;
    }

	/*
	* Setzt LastName auf einen beliebigen mitgelieferten Wert.
	*/
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

	/*
	* Liefert User zurueck.
	*/
    public String getUser() {
        return user;
    }

	/*
	* Setzt User auf einen beliebigen mitgelieferten Wert.
	*/
    public void setUser(String user) {
        this.user = user;
    }
}