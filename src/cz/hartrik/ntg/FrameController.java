
package cz.hartrik.ntg;

import cz.hartrik.common.Color;
import cz.hartrik.common.ui.javafx.DragAndDropInitializer;
import cz.hartrik.ntg.dialogs.ExportDialog;
import cz.hartrik.ntg.dialogs.FilterDialog;
import cz.hartrik.ntg.io.DefaultUIProvider;
import cz.hartrik.ntg.io.IOManager;
import cz.hartrik.ntg.texture.Component;
import cz.hartrik.ntg.texture.ComponentTexture;
import cz.hartrik.ntg.texture.Texture;
import cz.hartrik.ntg.texture.WhiteTexture;
import java.util.Random;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Window;

/**
 * @version 2015-04-01
 * @author Patrik Harag
 */
public class FrameController implements Initializable {
    
    private final Color[] DEF_COLORS =
            { Color.createGray(128), Color.createGray(120) };
    
    @FXML private ImageView sampleView;
    @FXML private AnchorPane anchorPane;
    @FXML private ListView<Component> listView;
    
    @FXML private Button buttonRemove;
    @FXML private Button buttonDown;
    @FXML private Button buttonUp;
    
    @FXML private Pane componentEditPane;
    @FXML private ColorPicker colorPicker;
    @FXML private TextField textField;
          private TextFieldValidator textFieldValidator;
    
    private IOManager ioManager;
    private Renderer renderer;
    private Texture texture = new ComponentTexture(Component.create(DEF_COLORS));
    
    FilterDialog filterDialog;
    ExportDialog exportDialog;
    
    @Override
    public void initialize(java.net.URL url, java.util.ResourceBundle rb) {
        exportDialog = new ExportDialog(this::getWindow, listView::getItems);
        filterDialog = new FilterDialog(this::getWindow, listView::getItems,
                (c) -> listView.getItems().setAll(c));
        
        DragAndDropInitializer.initFileDragAndDrop(listView, (files) -> {
            ioManager.load(files.get(0)).ifPresent((components) -> {
                listView.getItems().setAll(components);
                updateTexture();
            });
        });
        
        Platform.runLater(() -> {
            listView.setCellFactory(new ComponentCellFactory());
            listView.getItems().addAll(Component.create(DEF_COLORS));
            
            
            listView.getSelectionModel().selectedItemProperty()
                    .addListener((observable, oldValue, newValue) -> {

                selected(newValue != null);
                if (newValue != null) {
                    textField.setText(newValue.getCount() + "");
                    colorPicker.setValue(newValue.getColor().toJavaFXColor());
                }
            });
            
            anchorPane.widthProperty().addListener(
                (observable, o, n) -> sampleView.setFitWidth(n.doubleValue()));
            anchorPane.heightProperty().addListener(
                (observable, o, n) -> sampleView.setFitHeight(n.doubleValue()));
            
            sampleView.fitWidthProperty().addListener((ob, o, n) -> resize());
            sampleView.fitHeightProperty().addListener((ob, o, n) -> resize());
            
            textFieldValidator = new TextFieldValidator(textField, 0, 999999, 0) {
                @Override public void changed(int newValue) {
                    componentEdit();
                }
            };
            
            listView.getSelectionModel().select(0);
            renderer  = new Renderer(sampleView, texture);
            ioManager = new IOManager(getWindow(), new DefaultUIProvider());
        });
    }
    
    protected void selected(boolean selected) {
        componentEditPane.setVisible(selected);
        buttonRemove.setDisable(!selected);
        buttonDown.setDisable(!selected);
        buttonUp.setDisable(!selected);
    }
    
    protected Window getWindow() {
        return listView.getScene().getWindow();
    }
    
    private void resize() {
        renderer.resize(
                (int) anchorPane.getWidth(),
                (int) anchorPane.getHeight());
    }
    
    private Component getSelected() {
        if (listView.getSelectionModel().isEmpty()) return null;
        return listView.getSelectionModel().getSelectedItem();
    }
    
    protected void updateTexture() {
        texture = (listView.getItems().isEmpty())
                ? new WhiteTexture()
                : new ComponentTexture(listView.getItems());
        
        renderer.setTexture(texture);
        renderer.repaint();
    }
    
    // seznam s komponentami
    
    @FXML protected void componentAdd() {
        final Component selected = getSelected();
        final Random random = new Random();
        
        listView.getItems().add((selected != null)
                ? selected.clone()
                : new Component(new Color(
                        random.nextDouble(),
                        random.nextDouble(),
                        random.nextDouble(), 1)));
        
        updateTexture();
    }
    
    @FXML protected void componentRemove() {
        if (listView.getItems().remove(getSelected()))
            updateTexture();
    }
    
    @FXML protected void componentEdit() {
        Component newComponent = new Component(
                Color.fromJavaFXColor(colorPicker.getValue()),
                textFieldValidator.getValue());
        
        int index = listView.getSelectionModel().getSelectedIndex();
        listView.getItems().set(index, newComponent);
        
        updateTexture();
    }
    
    @FXML protected void componentMoveDown() { moveSelected(+1); }
    @FXML protected void componentMoveUp()   { moveSelected(-1); }
    
    private void moveSelected(int diff) {
        Component selected = getSelected();
        if (selected == null) return;
        
        ObservableList<Component> items = listView.getItems();
        int pos1 = items.indexOf(selected);
        int pos2 = pos1 + diff;
        
        if (pos2 < 0 || pos2 >= items.size()) return;
        
        Component get = items.get(pos2);
        items.set(pos1, get);
        items.set(pos2, selected);
        
        listView.getSelectionModel().select(selected);
    }
    
    @FXML protected void componentFilter() {
        filterDialog.showAndWait();
        updateTexture();
    }
    
    // soubor
    
    @FXML protected void fileSave() {
        ioManager.save(listView.getItems());
    }
    
    @FXML protected void fileExport() {
        exportDialog.showAndWait();
    }
    
    @FXML protected void fileLoad() {
        ioManager.load().ifPresent((components) -> {
            listView.getItems().setAll(components);
            updateTexture();
        });
    }
    
    @FXML protected void info() {
        InfoDialog infoDialog = new InfoDialog();
        infoDialog.initOwner(getWindow());
        infoDialog.showAndWait();
    }
    
}