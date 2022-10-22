package gui;

import java.awt.Color;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;

import org.knowm.xchart.PieChart;
import org.knowm.xchart.PieChartBuilder;
import org.knowm.xchart.SwingWrapper;
import org.knowm.xchart.XYChart;
import org.knowm.xchart.XYChartBuilder;
import org.knowm.xchart.XYSeries.XYSeriesRenderStyle;
import org.knowm.xchart.style.Styler.LegendPosition;

import entity.IzvestajRezervacija;
import entity.Rezervacija;
import entity.StatusRezervacije;
import entity.TipSobe;
import managers.KorisniciManager;
import managers.RezervacijeManager;

public class Grafici {
	private RezervacijeManager rezervacijeManager;
	private KorisniciManager korisniciManager;
	private PieChart chartSobarice;
	private PieChart chartStatusRez;
	private XYChart godisnjiPrikazXYChart;
	
	public Grafici(RezervacijeManager rezervacijeManager, KorisniciManager korisniciManager) {
		this.rezervacijeManager = rezervacijeManager;
		this.korisniciManager = korisniciManager;

		chartStatusRez = getPieChartStatusRezervacija();
		chartSobarice = getPieChartSobarice();
		godisnjiPrikazXYChart = getGodisnjiPrikazPrihodaPoSobama();

	}
	


	public XYChart getGodisnjiPrikazPrihodaPoSobama() {
		XYChart chart = new XYChartBuilder().width(1000).height(600).title("Prihodi po tipu sobe u poslednjih 12 meseci").xAxisTitle("Meseci").yAxisTitle("Prihodi")
				.build();

	    chart.getStyler().setDefaultSeriesRenderStyle(XYSeriesRenderStyle.Line);
	    chart.getStyler().setPlotMargin(0);
	    chart.getStyler().setPlotContentSize(.95);
		chart.getStyler().setLegendPosition(LegendPosition.OutsideS);
		chart.getStyler().setDatePattern("d.MMM");
		chart.getStyler().setDecimalPattern("#0.00");

		LocalDate today = LocalDate.now();
		LocalDate todayLastYear = LocalDate.now().minusYears(1);
//		int broj = 0;
		for (TipSobe ts : TipSobe.values()) {
			ArrayList<Date> xData = new ArrayList<Date>();
			ArrayList<Integer> yData = new ArrayList<Integer>();
			

			
			LocalDate datum = todayLastYear;
			Date DateDatum = null;
			int i = 0;
			while(!datum.isAfter(today)) {
				String formattedDate = datum.format(DateTimeFormatter.ofPattern("d.MM.yyyy"));
				try {
					SimpleDateFormat formatter = new SimpleDateFormat("d.MM.yyyy");
					DateDatum = formatter.parse(formattedDate);
				} catch (ParseException e) {
					e.printStackTrace();
				}
				xData.add(DateDatum);
				yData.add(rezervacijeManager.getMesecniPrihodi(ts, datum, datum.plusMonths(1)));
				
				datum = datum.plusMonths(1);
				
			}
			chart.addSeries("" + ts, xData, yData);
		}
		
		return chart;
	}
	
	public PieChart getPieChartSobarice() {
		PieChart chart = new PieChartBuilder().width(800).height(600).title(getClass().getSimpleName()).build();
		 
	    // Customize Chart
	    Color[] sliceColors = new Color[] { new Color(22, 33, 62), new Color(15, 52, 96), new Color(83, 52, 131), new Color(233, 69, 96) };
	    chart.getStyler().setSeriesColors(sliceColors);
	 
	    // Series
	    LocalDate today = LocalDate.now();
	    LocalDate datum = today.minusMonths(1);
	     
	    ArrayList<LocalDate> datumi = new ArrayList<LocalDate>();
		LocalDate date = datum;
		while(true) {
			datumi.add(date);
			if(date.isEqual(today)) {
				break;
			}
			date = date.plusDays(1);
		}
		
		ArrayList<String> imenaSobarica = new ArrayList<String>();
//		ArrayList<Integer> brojSpremljenihSoba = new ArrayList<Integer>();
		for(String username: korisniciManager.getSobarice().keySet()) {
			imenaSobarica.add(username);
		}
		for(String username: imenaSobarica) {
			int count = 0;
			for(Rezervacija rez: rezervacijeManager.getRezervacije()) {
				if(datumi.contains(rez.getKrajnjiDatum()) & rez.getSobaricaIme().trim().equals(username)) {
					count++;
				}
			}
			chart.addSeries(username, count);
		}

	    
	 
	    return chart;
	}
	
	public PieChart getPieChartStatusRezervacija() {
		PieChart chart = new PieChartBuilder().width(800).height(600).title(getClass().getSimpleName()).build();
		 
	    // Customize Chart
	    Color[] sliceColors = new Color[] { new Color(22, 33, 62), new Color(15, 52, 96), new Color(83, 52, 131), new Color(233, 69, 96) };
	    chart.getStyler().setSeriesColors(sliceColors);
	    
	    LocalDate today = LocalDate.now();
	    LocalDate datum = today.minusMonths(1);
	     
	    ArrayList<LocalDate> datumi = new ArrayList<LocalDate>();
		LocalDate date = datum;
		while(true) {
			datumi.add(date);
			if(date.isEqual(today)) {
				break;
			}
			date = date.plusDays(1);
		}
		
	    for(StatusRezervacije status: StatusRezervacije.values()) {
	    	int count = 0;
	    	for(IzvestajRezervacija izvRez: rezervacijeManager.getIzvestajiRezervacija()) {
	    		if(izvRez.getStatusRezervacije().equals(status)) {
	    			if(datumi.contains(izvRez.getDatum())) {
	    				count++;
	    			}
	    		}
	    	}
	    	chart.addSeries(status.toString(), count);
	    }
	    
	    return chart;
	    
	}

	public PieChart getChartSobarice() {
		return chartSobarice;
	}





	public void setChartSobarice(PieChart chartSobarice) {
		this.chartSobarice = chartSobarice;
	}


	public PieChart getChartStatusRez() {
		return chartStatusRez;
	}



	public void setChartStatusRez(PieChart chartStatusRez) {
		this.chartStatusRez = chartStatusRez;
	}

	public XYChart getGodisnjiPrikazXYChart() {
		return godisnjiPrikazXYChart;
	}


	public void setGodisnjiPrikazXYChart(XYChart godisnjiPrikazXYChart) {
		this.godisnjiPrikazXYChart = godisnjiPrikazXYChart;
	}
	
}
