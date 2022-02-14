package org.hung.pojo.odds;

import java.util.Date;

import org.hung.pojo.odds.FullOdds.CombinationOdds;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;

@Data
public class FullOdds {

	private Date updAt;
	private String colSt;
	//private List<CombinationOdds> cmb = new ArrayList<CombinationOdds>();
	private CombinationOdds[] cmb;

	@Data
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	public static class CombinationOdds {
		
		private String cmbStr;
		private String cmbSt;
		private int scrOrd;
		private Boolean hf;
		//private double wP;
		private String odds;
		private Integer oDrp;

	}
	
	public static FullOdds genWinPlaOdds(int noOfHorse) {
		
		FullOdds fullOdds = new FullOdds();
		
		CombinationOdds[] odds = new CombinationOdds[noOfHorse];
		//int n=0;
		int noOfHf = 0;
		for (int i=0;i<noOfHorse;i++) {
			long randOdds = Math.round(Math.random()*999);
			odds[i] = new CombinationOdds();
			odds[i].setCmbStr(String.format("%02d", i+1));
			odds[i].setScrOrd(i+1);
			odds[i].setCmbSt("Defined");
			//odds[n].setWP(99999.9);
			odds[i].setOdds(String.valueOf(randOdds));
			if (Math.random()>0.7 && noOfHf<=0) {
				odds[i].setHf(true);
				noOfHf++;
			}
			if (Math.random()>0.9) {
				odds[i].setODrp(30);
			}
		}
		fullOdds.setCmb(odds);
		fullOdds.setColSt("Interim");
		fullOdds.setUpdAt(new Date());
		
		return fullOdds;
	}
	
	public static FullOdds genQinQplOdds(long count,int f1,int f2) {
		
		//Random rand = new Random();
		//IntStream intStream = rand.ints(1,999);
		//intStream.iterator().ne
		
		//OddsInfo oddsInfo = new OddsInfo();
		FullOdds fullOdds = new FullOdds();
		
		CombinationOdds[] odds = new CombinationOdds[f1*f2];
		int n=0;
		int noOfHf = 0;
		int noOfODrp = 0;
		for (int i=0;i<f1;i++) {
			for (int j=0;j<f2;j++) {
				long randOdds = Math.round(Math.random()*999);
				odds[n] = new CombinationOdds();
				odds[n].setCmbStr(String.format("%02d,%02d", i+1, j+1));
				odds[n].setScrOrd(n+1);
				odds[n].setCmbSt("Defined");
				if (count%2==0 && (i==5 || j==3)) {
					odds[n].setCmbSt("Scratched");
				}
				//odds[n].setWP(99999.9);
				odds[n].setOdds(String.valueOf(randOdds));
				if (noOfHf <= 0) {
					odds[n].setHf(true);
					noOfHf++;
				}
				if (noOfODrp <= 10) {
					odds[n].setODrp(30);
					noOfODrp++;
				}
				n++;
			}
		}
		fullOdds.setCmb(odds);
		fullOdds.setColSt("Final");
		fullOdds.setUpdAt(new Date());
		
		//oddsInfo.setFullodds(fullOdds);
		//oddsInfo.setCount(count2);
		//oddsInfo.setSentTime(LocalDateTime.now());
		
		return fullOdds;
	}
	
	public static FullOdds genDblOdds(long count2,int f1,int f2) {
		
		//Random rand = new Random();
		//IntStream intStream = rand.ints(1,999);
		//intStream.iterator().ne
		
		//OddsInfo oddsInfo = new OddsInfo();
		FullOdds fullOdds = new FullOdds();
		
		CombinationOdds[] odds = new CombinationOdds[f1*f2];
		int n=0;
		int noOfHf = 0;
		int noOfODrp = 0;
		for (int i=0;i<f1;i++) {
			for (int j=0;j<f2;j++) {
				long randOdds = Math.round(Math.random()*999);
				odds[n] = new CombinationOdds();
				odds[n].setCmbStr(String.format("%02d/%02d", i+1, j+1));
				odds[n].setScrOrd(n+1);
				odds[n].setCmbSt("Defined");
				//odds[n].setWP(99999.9);
				odds[n].setOdds(String.valueOf(randOdds));
				if (noOfHf <= 0) {
					odds[n].setHf(true);
					noOfHf++;
				}
				if (noOfODrp <= 10) {
					odds[n].setODrp(30);
					noOfODrp++;
				}
				n++;
			}
		}
		fullOdds.setCmb(odds);
		fullOdds.setColSt("Final");
		fullOdds.setUpdAt(new Date());
		
		//oddsInfo.setFullodds(fullOdds);
		//oddsInfo.setCount(count2);
		//oddsInfo.setSentTime(LocalDateTime.now());
		
		return fullOdds;
	}
}
