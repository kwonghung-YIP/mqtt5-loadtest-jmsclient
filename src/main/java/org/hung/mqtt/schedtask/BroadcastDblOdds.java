package org.hung.mqtt.schedtask;

import org.hung.pojo.odds.FullOdds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.Resource;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.scheduling.annotation.Scheduled;

import com.solacesystems.jms.SupportedProperty;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
@Profile("case4")
public class BroadcastDblOdds {

	@Autowired
	private JmsTemplate jmsTemplate;
	
	@Value("${dbl-odds.f1:34}")
	private int f1;
	
	@Value("${dbl-odds.f2:34}")
	private int f2;

	@Value("${dbl-odds.topic-zip:public/push/odds/dbl-zip}") 
	private String dblOddsTopic;
	
	@Value("file:/c:/projects/mqtt-client/dbl-odds-14x14.json")
	private Resource jsonFile;
	
	private long countraw = 1;
	private long countzip = 1;
	
	@Scheduled(fixedRateString = "${dbl-odds.fixed-rate:1000}")
	public void broadcastDoubleOddsZip() {
		
		//OddsInfo oddsInfo = readOddsInfoFromFile();
		FullOdds fullOdds = FullOdds.genDblOdds(countzip++,f1,f2);
		
		final String topic = dblOddsTopic;
		
		jmsTemplate.convertAndSend(topic, fullOdds, (msg) -> {
			//msg.setStringProperty("my-header", "HaHa!");
			//msg.setStringProperty("my-header2", "HeHe!");
			//msg.setStringProperty(SupportedProperty.SOLACE_JMS_PROP_HTTP_CONTENT_ENCODING, "gzip");
			msg.setStringProperty(SupportedProperty.SOLACE_JMS_PROP_HTTP_CONTENT_TYPE,"application/json");
			//msg.
			return msg;
		});
	}	
}
