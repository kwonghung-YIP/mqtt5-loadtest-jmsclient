package org.hung.mqtt.config;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.zip.GZIPOutputStream;

import javax.jms.BytesMessage;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Session;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.connection.CachingConnectionFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.jms.support.converter.MessageType;
import org.springframework.lang.Nullable;

import com.fasterxml.jackson.databind.ObjectWriter;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
public class JmsConfig {

	@Autowired
	private ConnectionFactory connectionFactory;
	
	@Bean
	public JmsTemplate solaceJmsTemplate() {
		// Update the jmsTemplate's connection factory to cache the connection
		CachingConnectionFactory ccf = new CachingConnectionFactory(connectionFactory);
		
		JmsTemplate jmsTemplate = new JmsTemplate(ccf);
		//jmsTemplate.set
				
		// By default Spring Integration uses Queues, but if you set this to true you
		// will send to a PubSub+ topic destination
		jmsTemplate.setPubSubDomain(true);
		
		
		MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter() {

			@Nullable
			private String encoding;
			
			@Nullable
			private String encodingPropertyName;
			
			@Override
			public void setEncoding(String encoding) {
				super.setEncoding(encoding);
				this.encoding = encoding;
			}

			@Override
			public void setEncodingPropertyName(String encodingPropertyName) {
				super.setEncodingPropertyName(encodingPropertyName);
				this.encodingPropertyName = encodingPropertyName;
			}			

			@Override
			protected BytesMessage mapToBytesMessage(Object object, Session session, ObjectWriter objectWriter)
					throws JMSException, IOException {

				try (ByteArrayOutputStream byteOut = new ByteArrayOutputStream(1024)) {
					GZIPOutputStream gzipOut = new GZIPOutputStream(byteOut);
					if (this.encoding != null) {
						OutputStreamWriter writer = new OutputStreamWriter(gzipOut, this.encoding);
						objectWriter.writeValue(writer, object);
					}
					else {
						// Jackson usually defaults to UTF-8 but can also go straight to bytes, e.g. for Smile.
						// We use a direct byte array argument for the latter case to work as well.
						objectWriter.writeValue(gzipOut, object);
					}
	
					BytesMessage message = session.createBytesMessage();
					message.writeBytes(byteOut.toByteArray());
					if (this.encodingPropertyName != null) {
						message.setStringProperty(this.encodingPropertyName,
								(this.encoding != null ? this.encoding : DEFAULT_ENCODING));
					}
					return message;
				} catch (IOException e) {
					throw e;
				}
			}			
		};
		converter.setTargetType(MessageType.BYTES);
		jmsTemplate.setMessageConverter(converter);
		
		return jmsTemplate;
	}

}
