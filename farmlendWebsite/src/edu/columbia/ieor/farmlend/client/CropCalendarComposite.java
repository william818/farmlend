package edu.columbia.ieor.farmlend.client;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.CaptionPanel;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;

import edu.columbia.ieor.farmlend.shared.model.Country;
import edu.columbia.ieor.farmlend.shared.model.Crop;
import edu.columbia.ieor.farmlend.shared.model.CropPeriod;
import edu.columbia.ieor.farmlend.shared.model.Zone;

public class CropCalendarComposite extends Composite {

    private Map<String, Country> countryMap = new HashMap<String, Country>();
    private Map<String, Zone> zoneMap = new HashMap<String, Zone>();
    private Map<String, Crop> cropMap = new HashMap<String, Crop>();

    private final GreetingServiceAsync greetingService = GWT
            .create(GreetingService.class);
    
    ListBox countrySelector = new ListBox();
    ListBox zoneSelector = new ListBox();
    ListBox cropSelector = new ListBox();
    Grid calGrid = new Grid(2, 12);
    
    Label[] monthLabels = new Label[12];
    
    public CropCalendarComposite() {
        
        FlowPanel flowPanel = new FlowPanel();
        initWidget(flowPanel);
        flowPanel.setHeight("452px");
        
        CaptionPanel cptnpnlCropBackground = new CaptionPanel("Crop Background");
        flowPanel.add(cptnpnlCropBackground);
        
        Grid grid = new Grid(3, 2);
        cptnpnlCropBackground.setContentWidget(grid);
        grid.setSize("100%", "100%");
        
        Label lblCountry = new Label("Country: ");
        grid.setWidget(0, 0, lblCountry);
        
        grid.setWidget(0, 1, countrySelector);
        countrySelector.setWidth("100%");
        
        Label lblNewLabel = new Label("Agro-Ecological Zone: ");
        grid.setWidget(1, 0, lblNewLabel);
        
        grid.setWidget(1, 1, zoneSelector);
        zoneSelector.setWidth("100%");
        
        Label lblNewLabel_1 = new Label("Crop: ");
        grid.setWidget(2, 0, lblNewLabel_1);
        
        grid.setWidget(2, 1, cropSelector);
        cropSelector.setWidth("100%");
        grid.getCellFormatter().setVerticalAlignment(2, 1, HasVerticalAlignment.ALIGN_TOP);
        grid.getCellFormatter().setVerticalAlignment(1, 1, HasVerticalAlignment.ALIGN_TOP);
        grid.getCellFormatter().setVerticalAlignment(0, 1, HasVerticalAlignment.ALIGN_TOP);
        grid.getCellFormatter().setVerticalAlignment(0, 0, HasVerticalAlignment.ALIGN_TOP);
        grid.getCellFormatter().setVerticalAlignment(1, 0, HasVerticalAlignment.ALIGN_TOP);
        grid.getCellFormatter().setVerticalAlignment(2, 0, HasVerticalAlignment.ALIGN_TOP);
        grid.getCellFormatter().setHorizontalAlignment(0, 0, HasHorizontalAlignment.ALIGN_LEFT);
        grid.getCellFormatter().setHorizontalAlignment(1, 0, HasHorizontalAlignment.ALIGN_LEFT);
        grid.getCellFormatter().setHorizontalAlignment(2, 0, HasHorizontalAlignment.ALIGN_LEFT);
        
        CaptionPanel cptnpnlTimingRecommendation = new CaptionPanel("Timing Recommendation");
        flowPanel.add(cptnpnlTimingRecommendation);
        cptnpnlTimingRecommendation.setHeight("281px");
        
        FlowPanel flowPanel_1 = new FlowPanel();
        cptnpnlTimingRecommendation.setContentWidget(flowPanel_1);
        
        Grid grid_1 = new Grid(8, 1);
        flowPanel_1.add(grid_1);
        grid_1.setSize("5cm", "3cm");
        
        Label lblNewLabel_2 = new Label("Pre-Planting");
        lblNewLabel_2.setStyleName("rec-color1");
        grid_1.setWidget(0, 0, lblNewLabel_2);
        
        Label lblNewLabel_3 = new Label("Planting or Sowing");
        lblNewLabel_3.setStyleName("rec-color2");
        grid_1.setWidget(1, 0, lblNewLabel_3);
        
        Label lblWeeding = new Label("Weeding");
        lblWeeding.setStyleName("rec-color3");
        grid_1.setWidget(2, 0, lblWeeding);
        
        Label lblGrowing = new Label("Growing");
        lblGrowing.setStyleName("rec-color4");
        grid_1.setWidget(3, 0, lblGrowing);
        
        Label lblPreharvest = new Label("Pre-harvest");
        lblPreharvest.setStyleName("rec-color5");
        grid_1.setWidget(4, 0, lblPreharvest);
        
        Label lblHarvesting = new Label("Harvesting");
        lblHarvesting.setStyleName("rec-color6");
        grid_1.setWidget(5, 0, lblHarvesting);
        
        Label lblPostharvesting = new Label("Post-harvesting");
        lblPostharvesting.setStyleName("rec-color7");
        grid_1.setWidget(6, 0, lblPostharvesting);
        
        Label lblSoilManagement = new Label("Soil Management");
        lblSoilManagement.setStyleName("rec-color8");
        grid_1.setWidget(7, 0, lblSoilManagement);
        
        flowPanel_1.add(calGrid);
        
        Label lblJanurary = new Label("Janurary");
        calGrid.setWidget(0, 0, lblJanurary);
        
        Label lblFebruary = new Label("February");
        calGrid.setWidget(0, 1, lblFebruary);
        
        Label lblNewLabel_4 = new Label("March");
        calGrid.setWidget(0, 2, lblNewLabel_4);
        
        Label lblApril = new Label("April");
        calGrid.setWidget(0, 3, lblApril);
        
        Label lblMay = new Label("May");
        calGrid.setWidget(0, 4, lblMay);
        
        Label lblJune = new Label("June");
        calGrid.setWidget(0, 5, lblJune);
        
        Label lblJuly = new Label("July");
        calGrid.setWidget(0, 6, lblJuly);
        
        Label lblAugust = new Label("August");
        calGrid.setWidget(0, 7, lblAugust);
        
        Label lblSeptember = new Label("September");
        calGrid.setWidget(0, 8, lblSeptember);
        
        Label lblOctober = new Label("October");
        calGrid.setWidget(0, 9, lblOctober);
        
        Label lblNovember = new Label("November");
        calGrid.setWidget(0, 10, lblNovember);
        
        Label lblDecember = new Label("December");
        calGrid.setWidget(0, 11, lblDecember);
        
        for (int i = 0; i < monthLabels.length; i++) {
            monthLabels[i] = new Label("----");
            calGrid.setWidget(1, i, monthLabels[i]);
        }
        
        countrySelector.setEnabled(false);
        zoneSelector.setEnabled(false);
        cropSelector.setEnabled(false);
        calGrid.setVisible(false);
        
        countrySelector.addChangeHandler(new ChangeHandler() {
            public void onChange(ChangeEvent event) {
                loadZones(countryMap.get(countrySelector.getValue(countrySelector.getSelectedIndex())));
            }
        });
        
        zoneSelector.addChangeHandler(new ChangeHandler() {
            public void onChange(ChangeEvent event) {
                loadCrops(countryMap.get(countrySelector.getValue(countrySelector.getSelectedIndex())),
                        zoneMap.get(zoneSelector.getValue(zoneSelector.getSelectedIndex())));
            }
        });

        cropSelector.addChangeHandler(new ChangeHandler() {
            public void onChange(ChangeEvent event) {
                loadPeriods(Long.parseLong(cropSelector.getValue(cropSelector.getSelectedIndex())));
            }

        });
        
        loadCountries();
    }
    
    public void loadCountries() {
        greetingService.getAllCountries(new AsyncCallback<List<Country>>() {
            public void onFailure(Throwable caught) {
            }

            public void onSuccess(List<Country> result) {
                countrySelector.clear();
                for (Country c : result) {
                    countryMap.put(c.getName(), c);
                    countrySelector.addItem(c.getName());
                }
                countrySelector.setEnabled(true);
             
            }
        });
    }

    private void loadZones(Country country) {
        greetingService.getZonesByCountry(country, new AsyncCallback<List<Zone>>() {
            public void onFailure(Throwable caught) {
            }

            public void onSuccess(List<Zone> result) {
                zoneSelector.clear();
                for (Zone z : result) {
                    zoneMap.put(z.getName(), z);
                    zoneSelector.addItem(z.getName());
                }
                zoneSelector.setEnabled(true);
            }
        });
    }

    private void loadCrops(Country country, Zone zone) {
        greetingService.getCropsByCountryAndZone(country, zone, new AsyncCallback<List<Crop>>() {
            public void onFailure(Throwable caught) {
            }

            public void onSuccess(List<Crop> result) {
                cropSelector.clear();
                for (Crop c : result) {
                    cropMap.put(c.getName(), c);
                    cropSelector.addItem(c.getName(), c.getId().toString());
                }
                cropSelector.setEnabled(true);
            }
        });
    }

    private void loadPeriods(Long parseLong) {
        greetingService.getCropPeriodsById(parseLong, new AsyncCallback<List<CropPeriod>>() {
            public void onFailure(Throwable caught) {
            }

            public void onSuccess(List<CropPeriod> result) {
                for (CropPeriod period : result) {
/*                    DateTime startT = new DateTime(2013, period.getStartMonth(), period.getStartDay(), 0, 0);
                    DateTime endT = new DateTime(2013, period.getStartMonth(), period.getStartDay(), 0, 0);
                    int startD = startT.getDayOfYear();
                    int endD = endT.getDayOfYear(); */
                    int startD = 30 * (period.getStartMonth()-1) + period.getStartDay();
                    int endD = 30 * (period.getEndMonth()-1) + period.getEndDay();
                    if (endD < startD)
                        continue;
                    for (int i = startD/30; i <= endD/30; i++) {
                        if (i < 0 || i > 12)
                            continue;
                        monthLabels[i].setStyleName("rec-color" + period.getColor().toString());
                    }
                    
                }
                calGrid.setVisible(true);
            }
        });
    }
    
}
