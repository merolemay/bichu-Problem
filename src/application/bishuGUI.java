package application;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

import graphs.AdjListGraph;
import graphs.AdjMatrixGraph;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;


public class bishuGUI {
	
	private BufferedReader br;
	private AdjListGraph<Integer> a;
	private AdjMatrixGraph<Integer> b;
	
	
    @FXML
    private Button dfsBtn;

    @FXML
    private Button bfsBtn;

	@FXML
	private TabPane myTabPane;

	@FXML
	private Tab tabInfo;

	@FXML
	private Tab tabResult;

	@FXML
	private Text girlNumberAnswer;
	
	
	private Stage window;
	
	public bishuGUI(Stage w) {
		a = new AdjListGraph<Integer>(true,true);
		b = new AdjMatrixGraph<Integer>(true,true);
		window = w;

	}

	@FXML
	void bfsBtnAction(ActionEvent event) throws NumberFormatException, IOException {
		bfs();
		tabResult.setDisable(false);
		bfsBtn.setDisable(true);
		dfsBtn.setDisable(true);
		myTabPane.getSelectionModel().select(tabResult);
		

	}
	void bfs() throws NumberFormatException, IOException {
		int numCountries = Integer.parseInt(br.readLine());
		for(int i=0;i<numCountries;i++) {
			b.addVertex(i+1);

		}
		for(int i=0;i<numCountries-1;i++) {
			String[] line = br.readLine().split(" ");
			int v1 = Integer.parseInt(line[0]);
			int v2 = Integer.parseInt(line[1]);
			b.addEdge(v1, v2, 0);
			
		}
		int numGirls = Integer.parseInt(br.readLine());
		for(int i=0;i<numGirls;i++) {
			int country = Integer.parseInt(br.readLine());
			b.searchVertex(country).addPerson(country);
		}
		b.bfs(b.searchVertex(1));
		girlNumberAnswer.setText(b.closestW()+"");
		br.close();
		
	}

	@FXML
	void dfsBtnAction(ActionEvent event) throws NumberFormatException, IOException {
		dfs();
		tabResult.setDisable(false);
		bfsBtn.setDisable(true);
		dfsBtn.setDisable(true);
		myTabPane.getSelectionModel().select(tabResult);

	}
	
	void dfs() throws NumberFormatException, IOException {
		int numCountries = Integer.parseInt(br.readLine());
		for(int i=0;i<numCountries;i++) {
			a.addVertex(i+1);

		}
		for(int i=0;i<numCountries-1;i++) {
			String[] line = br.readLine().split(" ");
			int v1 = Integer.parseInt(line[0]);
			int v2 = Integer.parseInt(line[1]);
			a.addEdge(v1, v2, 0);
			
		}
		int numGirls = Integer.parseInt(br.readLine());
		for(int i=0;i<numGirls;i++) {
			int country = Integer.parseInt(br.readLine());
			a.searchVertex(country).addPerson(country);
		}
		girlNumberAnswer.setText(a.dfs()+"");
		br.close();
	}

	@FXML
	void selectTxt(ActionEvent event) throws FileNotFoundException {
		JFileChooser choose = new JFileChooser();
		FileNameExtensionFilter filter = new FileNameExtensionFilter("doc","docx", "txt");
		choose.setAcceptAllFileFilterUsed(false);
		choose.setFileFilter(filter);
		int op = choose.showOpenDialog(null);
		if (op == JFileChooser.APPROVE_OPTION) {
			bfsBtn.setDisable(false);
			dfsBtn.setDisable(false);
			
			br = new BufferedReader(new FileReader(new File(choose.getSelectedFile().getAbsolutePath())));
			
			a = new AdjListGraph<Integer>(true,true);
			b = new AdjMatrixGraph<Integer>(true,true);

		}

	}
    @FXML
    void backToInfo(ActionEvent event) {
    	
    	tabResult.setDisable(true);
    	myTabPane.getSelectionModel().select(tabInfo);
    	
    	bfsBtn.setDisable(true);
		dfsBtn.setDisable(true);
    	
    	
    	
    	
    	

    }
}

