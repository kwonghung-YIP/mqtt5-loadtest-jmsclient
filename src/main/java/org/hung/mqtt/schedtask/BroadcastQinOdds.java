package org.hung.mqtt.schedtask;

import org.hung.pojo.odds.FullOdds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.scheduling.annotation.Scheduled;

import com.solacesystems.jms.SupportedProperty;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
@Profile("case6")
public class BroadcastQinOdds {

	@Autowired
	private JmsTemplate jmsTemplate;
	
	@Value("${qin-odds.f1:12}")
	private int f1;
	
	@Value("${qin-odds.f2:12}")
	private int f2;

	@Value("${qin-odds.topic-zip:hk/d/prdt/wager/evt/01/upd/racing/20140120/s1/01/qin/odds/full}") 
	private String qinOddsTopic;
	
	@Scheduled(fixedRateString = "${qin-odds.fixed-rate:1000}")
	public void broadcastQinOddsZip() {
		
		//OddsInfo oddsInfo = readOddsInfoFromFile();
		FullOdds fullOdds = FullOdds.genQinQplOdds(1,f1,f2);
		
		final String topic = qinOddsTopic;
		
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
