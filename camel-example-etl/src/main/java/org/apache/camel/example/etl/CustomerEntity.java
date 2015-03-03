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

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@Entity(name = "customer")
//Das Hauptelement im XML-File ist mit "Customer" benannt.
@XmlRootElement(name = "customer")
//Hier wird festgelegt, ein Feld auszulesen
@XmlAccessorType(XmlAccessType.FIELD)
//Diese Abfrage verlangt untenstehende Query
@NamedQuery(name = "findCustomerByUsername", query = "SELECT c FROM customer c WHERE c.userName = :userName")

/**
 * An example entity bean which can be marshalled to/from XML (!)
 * The JPA entity bean (i.e. a POJO with @Entity)
 * 
 * @author Thomas Taschner <thomas.taschner1995@hotmail.com>
 * @author Michael Weinberger <mweinberger@student.tgm.ac.at>
 */
public class CustomerEntity {
	//Die Attribute aus dem  XML-File.
	@XmlAttribute
	private Long id;
	private String userName;
	private String firstName;
	private String surname;
	private String street;
	private String city;
	private String zip;
	private String phone;

	/*
	 * Liefert City zurueck.
	 */
	@Id
	@GeneratedValue
	public Long getId() {
		return id;
	}

	/*
	 * Setzt die ID auf einen beliebigen mitgelieferten Wert.
	 */
	public void setId(Long id) {
		this.id = id;
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
	 * Liefert die Telefonnummer zurueck.
	 */
	public String getPhone() {
		return phone;
	}

	/*
	 * Setzt die Telefonnummer auf einen beliebigen mitgelieferten Wert.
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}

	/*
	 * Liefert die Straße zurueck.
	 */
	public String getStreet() {
		return street;
	}

	/*
	 * Setzt die Straße auf einen beliebigen mitgelieferten Wert.
	 */
	public void setStreet(String street) {
		this.street = street;
	}

	/*
	 * Liefert den Nachnamen zurueck.
	 */
	public String getSurname() {
		return surname;
	}

	/*
	 * Setzt den Nachnamen auf einen beliebigen mitgelieferten Wert.
	 */
	public void setSurname(String surname) {
		this.surname = surname;
	}

	/*
	 * Liefert den Usernamen zurueck.
	 */
	public String getUserName() {
		return userName;
	}

	/*
	 * Setzt den Usernamen auf einen beliebigen mitgelieferten Wert.
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}

	/*
	 * Liefert LastName zurueck.
	 */
	public String getZip() {
		return zip;
	}

	/*
	 * Setzt LastName auf einen beliebigen mitgelieferten Wert.
	 */
	public void setZip(String zip) {
		this.zip = zip;
	}

	/*
	 * Gibt obenstehende Werte als String zurueck.
	 */
	public String toString() {
		return "Customer[userName: " + getUserName() + " firstName: " + getFirstName() + " surname: " + getSurname() + "]";
	}

}