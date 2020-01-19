package application;

import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class SampleController implements Initializable {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField TextFieldLengthZ;

    @FXML
    private TextField TextFieldIntervalX;

    @FXML
    private TextField TextFieldLengthY;

    @FXML
    private TextField TextFieldIntervalY;

    @FXML
    private TextField TextFieldLengthX;

    @FXML
    private Button buttonSetup;
    
    @FXML
    private Button buttonAddSeries;
    
    @FXML
    private Button buttonDeleteSeries;
    
    @FXML
    private Button buttonCalculate;
    
    @FXML
    private TextField TextFieldIntervalZ;

    @FXML
    private TextField TextFieldWindSpeed;

    @FXML
    private TextField TextFieldWindDirection;
    
    @FXML
    private TextField TextFieldLocationX;
    
    @FXML
    private TextField TextFieldLocationY;
    
    @FXML
    private TextField TextFieldHeight;
    
    @FXML
    private TextField TextFieldRate;
    
    @FXML
    private TextField TextFieldTotal;
    
    @FXML
    private TextField TextFieldX;
    
    @FXML
    private TextField TextFieldY;
    
    @FXML
    private TextField TextFieldZ;
    
    @FXML
    private TextField TextFieldC;
    
    @FXML
    private TextArea TextAreaCondition;
    
    @FXML
    private ComboBox comboSeries;
    
Base base = new Base();
    
    
    @FXML
    void buttonSetup_clicked(ActionEvent event) {
    	int LengthX = Integer.parseInt(TextFieldLengthX.getText());
    	int LengthY = Integer.parseInt(TextFieldLengthY.getText());
    	int LengthZ = Integer.parseInt(TextFieldLengthZ.getText());
    	int IntervalX = Integer.parseInt(TextFieldIntervalX.getText());
    	int IntervalY = Integer.parseInt(TextFieldIntervalY.getText());
    	int IntervalZ = Integer.parseInt(TextFieldIntervalZ.getText());
    	int WindDirection = Integer.parseInt(TextFieldWindDirection.getText());
    	int WindSpeed = Integer.parseInt(TextFieldWindSpeed.getText());
    	base = new Base(LengthX,LengthY,LengthZ,IntervalX,IntervalY,IntervalZ,WindDirection,WindSpeed);
    	base.initialize();
    	TextAreaCondition.appendText("Area has been set up.\n");
    }
    
    @FXML
    void buttonAddseries_clicked(ActionEvent event) {
    	String series, number;
    	series = TextFieldLocationX.getText() +',' +TextFieldLocationY.getText() +',' +TextFieldHeight.getText() +',' +TextFieldRate.getText();
    	TextAreaCondition.appendText("Chimney added." +series +"\n");
    	comboSeries.getItems().add(series);
    	comboSeries.getSelectionModel().select(0);
    	number = Integer.toString(Integer.parseInt(TextFieldTotal.getText())+1);
    	TextFieldTotal.setText(number);
    	
    }
    
    @FXML
    void buttonDeleteseries_clicked(ActionEvent event) {
    	String number;
    	comboSeries.getItems().remove(0);
    	comboSeries.getSelectionModel().select(0);
    	number = Integer.toString(Integer.parseInt(TextFieldTotal.getText())-1);
    	TextFieldTotal.setText(number);
    	TextAreaCondition.appendText("Chimney deleted.\n");
    }
    
    @FXML
    void buttonCalculate_clicked(ActionEvent event) {
    	TextAreaCondition.appendText("----------Calculating----------\n");
    	int x,y,i,numChi;
    	double H,Q;
        numChi = Integer.parseInt(TextFieldTotal.getText());
    	ArrayList<Chimney> chimneyList = new ArrayList<>();
    	i=0;
    	while(i < numChi) {
	    	String[] split_line = ((String) comboSeries.getItems().get(i)).split(",");
	    	x = Integer.parseInt(split_line[0]);
	    	y = Integer.parseInt(split_line[1]);
	    	H = Double.parseDouble(split_line[2]);
	    	Q = Double.parseDouble(split_line[3]);
	    	Chimney chimney = new Chimney(x,y,H,Q);
	        chimneyList.add(chimney);
	        i++;
    	}
        Chimney chi;
        Equation equation = new Equation();
 
        i=0;
        while(i < numChi){
            chi=chimneyList.get(i);
            TextAreaCondition.appendText("chimney" +(i+1) +": " +"x=" +chi.getX() +" " +"y=" +chi.getY() +" " +"H=" +chi.getH() +" " +"Q=" +chi.getQ()+"\n");
            equation.setBaseChimney(chi,base);
            equation.G_model();
            equation.maxConcentration();//find the position where the maximum concentration exists
            //base.showCMax();
            TextAreaCondition.appendText("MAX: " +base.getCMax() +" at " +"("+base.getXMax() +","+base.getYMax() +","+base.getZMax() +")"+"\n");
            i++;
        }
        TextAreaCondition.appendText("----------Calculation Completetd----------\n");
    	TextFieldX.setText(Integer.toString(base.getXMax()));
    	TextFieldY.setText(Integer.toString(base.getYMax()));
    	TextFieldZ.setText(Integer.toString(base.getZMax()));
    	String value=null;
    	DecimalFormat decimalFormat=new DecimalFormat("0.000000");
    	value=decimalFormat.format(base.getCMax());
    	TextFieldC.setText(value);
    }
    

    @FXML
    void initialize() {
        assert TextFieldLengthZ != null : "fx:id=\"TextFieldLengthZ\" was not injected: check your FXML file 'Sample.fxml'.";
        assert TextFieldIntervalX != null : "fx:id=\"TextFieldIntervalX\" was not injected: check your FXML file 'Sample.fxml'.";
        assert TextFieldLengthY != null : "fx:id=\"TextFieldLengthY\" was not injected: check your FXML file 'Sample.fxml'.";
        assert TextFieldIntervalY != null : "fx:id=\"TextFieldIntervalY\" was not injected: check your FXML file 'Sample.fxml'.";
        assert TextFieldLengthX != null : "fx:id=\"TextFieldLengthX\" was not injected: check your FXML file 'Sample.fxml'.";
        assert buttonSetup != null : "fx:id=\"buttonSetup\" was not injected: check your FXML file 'Sample.fxml'.";
        assert TextFieldIntervalZ != null : "fx:id=\"TextFieldIntervalZ\" was not injected: check your FXML file 'Sample.fxml'.";
        assert TextFieldWindSpeed != null : "fx:id=\"TextFieldWindSpeed\" was not injected: check your FXML file 'Sample.fxml'.";
        assert TextFieldWindDirection != null : "fx:id=\"TextFieldWindDirection\" was not injected: check your FXML file 'Sample.fxml'.";

    }

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		int seriesNumber;
		
		TextFieldLengthX.setText("50000");
		TextFieldLengthY.setText("50000");
		TextFieldLengthZ.setText("1000");
		TextFieldIntervalX.setText("1000");
		TextFieldIntervalY.setText("1000");
		TextFieldIntervalZ.setText("25");
		TextFieldWindDirection.setText("45");
		TextFieldWindSpeed.setText("3");
		TextFieldLocationX.setText("25000");
		TextFieldLocationY.setText("25000");
		TextFieldHeight.setText("20");
		TextFieldRate.setText("20");
		TextFieldTotal.setText("0");
		TextAreaCondition.setText("Please set up the area\n");
	}
}