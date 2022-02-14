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
@Profile("case5")
public class WinPlaOddsSimulator {
	
	@Autowired
	private JmsTemplate jmsTemplate;

	@Value("${odds.noOfHorse:14}")
	private int noOfHorse;
	
	@Value("${win-odds.topic:hk/d/prdt/wager/evt/01/upd/racing/20140120/s1/01/win/odds/full}") 
	private String winOddsTopic;

	@Value("${win-odds.compress:true}")
	private boolean winOddsCompress;
	
	@Value("${pla-odds.topic:hk/d/prdt/wager/evt/01/upd/racing/20140120/s1/01/pla/odds/full}") 
	private String plaOddsTopic;
	
	@Value("${pla-odds.compress:true}")
	private boolean plaOddsCompress;	
	
	@Scheduled(fixedRateString = "${win-odds.fixed-rate:1000}")
	public void broadcastWinOdds() {
		
		FullOdds fullOdds = FullOdds.genWinPlaOdds(noOfHorse);
		
		final String topic = winOddsTopic;
		
		jmsTemplate.convertAndSend(topic, fullOdds, (msg) -> {
			//msg.setStringProperty("my-header", "HaHa!");
			//msg.setStringProperty("my-header2", "HeHe!");
			//msg.setStringProperty(SupportedProperty.SOLACE_JMS_PROP_HTTP_CONTENT_ENCODING, "gzip");
			msg.setStringProperty(SupportedProperty.SOLACE_JMS_PROP_HTTP_CONTENT_TYPE,"application/json");
			//msg.
			return msg;
		});
	}
	
	@Scheduled(fixedRateString = "${pla-odds.fixed-rate:1000}")
	public void broadcastPlaOdds() {
		
		FullOdds fullOdds = FullOdds.genWinPlaOdds(noOfHorse);
		
		final String topic = plaOddsTopic;
		
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
