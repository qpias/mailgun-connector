/**
 * Mule Development Kit
 * Copyright 2010-2011 (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

/**
 * This file was automatically generated by the Mule Development Kit
 */
package org.mailgun;

import org.mule.api.annotations.Module;
import org.mule.api.annotations.Configurable;
import org.mule.api.annotations.Processor;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.filter.HTTPBasicAuthFilter;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.core.util.MultivaluedMapImpl;
import javax.ws.rs.core.MediaType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Cloud Connector
 *
 * @author MuleSoft, Inc.
 */
@Module(name="mailgun", schemaVersion="1.0-SNAPSHOT")
public class MailgunConnector
{
    private static final Logger logger = LoggerFactory.getLogger(MailgunConnector.class);

    /**
     * Configurable
     */
    @Configurable
    private String key;

    /**
     * Configurable
     */
    @Configurable
    private String domain;

    /**
     * Set API key
     *
     * @param key API key
     */
    public void setKey(String key)
    {
        this.key = key;
    }

    /**
     * Set domain name
     *
     * @param domain domain name
     */
    public void setDomain(String domain)
    {
        this.domain = domain;
    }

    /**
     * Custom processor
     * <p/>
     * {@sample.xml ../../../doc/Mailgun-connector.xml.sample mailgun:my-processor}
     *
     * @param from sender
     * @param to receiver
     * @param subject subject
     * @param message message
     * @return Some string
     */
    @Processor
    public String myProcessor(String from, String to, String subject, String message) throws Exception
    {
       Client client = Client.create();
       client.addFilter(new HTTPBasicAuthFilter("api",
                        this.key));
       WebResource webResource =
               client.resource("https://api.mailgun.net/v2/" +
                               this.domain +
                               "/messages");
       MultivaluedMapImpl formData = new MultivaluedMapImpl();
       formData.add("from", from);
       formData.add("to", to);
       formData.add("subject", subject);
       formData.add("text", message);
       ClientResponse response = webResource.type(MediaType.APPLICATION_FORM_URLENCODED).
               post(ClientResponse.class, formData);
       if (response.getStatus() != 200) {
          logger.error(response.toString());
          throw new Exception("Could not send message.");
       }
       return "";
    }
}
