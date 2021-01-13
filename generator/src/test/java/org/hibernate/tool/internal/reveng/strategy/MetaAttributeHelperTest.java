/*
 * Hibernate Tools, Tooling for your Hibernate Projects
 * 
 * Copyright 2020 Red Hat, Inc.
 *
 * Licensed under the GNU Lesser General Public License (LGPL), 
 * version 2.1 or later (the "License").
 * You may not use this file except in compliance with the License.
 * You may read the licence in the 'lgpl.txt' file in the root folder of 
 * project or obtain a copy at
 *
 *     http://www.gnu.org/licenses/lgpl-2.1.html
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" basis,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.hibernate.tool.internal.reveng.strategy;

import java.io.ByteArrayInputStream;
import java.util.Collection;
import java.util.Optional;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.commons.collections4.MultiValuedMap;
import org.hibernate.tool.internal.reveng.strategy.MetaAttributeHelper;
import org.hibernate.tool.internal.reveng.strategy.MetaAttributeHelper.SimpleMetaAttribute;
import org.junit.Assert;
import org.junit.Test;
import org.w3c.dom.Document;

public class MetaAttributeHelperTest {
	
	private static String XML = 
			"<element>                                         " +
			"  <meta attribute='blah' inherit='true'>foo</meta>" +
			"</element>                                        ";
	
	@Test
	public void testLoadMetaMap() throws Exception {
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = dbf.newDocumentBuilder();
		Document document = db.parse(new ByteArrayInputStream(XML.getBytes()));
		MultiValuedMap<String, SimpleMetaAttribute> mm = MetaAttributeHelper.loadMetaMap(document.getDocumentElement());
		Assert.assertEquals(1, mm.size());
		Collection<SimpleMetaAttribute> attributeList = mm.get("blah");
		Assert.assertEquals(1, attributeList.size());
		Optional<SimpleMetaAttribute> first = attributeList.stream().findFirst();
		Assert.assertTrue(first.isPresent());
		SimpleMetaAttribute attribute = first.get();
		Assert.assertEquals(true, attribute.inheritable);
		Assert.assertEquals("foo", attribute.value);
	}

}
