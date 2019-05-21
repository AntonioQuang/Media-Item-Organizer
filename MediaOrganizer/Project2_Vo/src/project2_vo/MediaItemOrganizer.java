/**
 * Media Item Organizer
 * The purpose for this program is to help organize your different media items.This
 * GUI can add, remove, loan, and return items in a collection. It can also sort
 * the items in a collection in two different ways. By alphabetical order and
 * by the date it was loaned on.
 */
package project2_vo;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 *
 * @author Antonio Quang
 */
public class MediaItemOrganizer extends Application {

    private ObservableList<Item> collection;
    boolean isSorted = false;
    boolean sortedByAlphabet = false;
    boolean sortedByDate = false;

    @Override
    public void start(Stage primaryStage) throws Exception {
        collection = FXCollections.observableArrayList();
        File file = new File("collection.dat");
        if (file.exists()) {
            ObjectInputStream ois = new ObjectInputStream(
                    new FileInputStream(file));
            List<Item> list = (List<Item>) ois.readObject();
            collection = FXCollections.observableArrayList(list);
            ois.close();
        }
        Library library = new Library(collection);
        BorderPane mainPane = new BorderPane();
        ListView<Item> list = new ListView<>();
        list.setItems(collection);
        mainPane.setCenter(list);

        VBox mainPane2 = new VBox(10);
        mainPane.setRight(mainPane2);

        HBox titleLine = new HBox(15);
        mainPane2.getChildren().addAll(titleLine);
        Label title = new Label("Title: ");
        TextField titleField = new TextField();
        titleLine.getChildren().addAll(title);
        titleLine.getChildren().addAll(titleField);

        HBox formatLine = new HBox();
        mainPane2.getChildren().addAll(formatLine);
        Label format = new Label("Format: ");
        TextField formatField = new TextField();
        formatLine.getChildren().addAll(format);
        formatLine.getChildren().addAll(formatField);

        Button addButton = new Button("Add");
        mainPane2.getChildren().addAll(addButton);
        addButton.setOnAction(e -> {
            String titleString = titleField.getText();
            String formatString = formatField.getText();
            library.addItem(titleString, formatString);
            if (isSorted) {
                if (sortedByAlphabet) {
                    Collections.sort(collection);
                } else if (sortedByDate) {
                    Collections.sort(collection);
                    Collections.sort(collection, new ItemByDateLoanedComparator());

                }
            }
            list.refresh();
        });

        Button removeButton = new Button("Remove");
        mainPane2.getChildren().addAll(removeButton);
        removeButton.setOnAction(e -> {
            Item item = list.getSelectionModel().getSelectedItem();
            collection.remove(item);
            list.refresh();
        });

        Button returnButton = new Button("Return");
        mainPane2.getChildren().addAll(returnButton);
        returnButton.setOnAction(e -> {
            Item item = list.getSelectionModel().getSelectedItem();
            if(item != null){
            item.returnItem();
            }
            if (isSorted) {
                if (sortedByAlphabet) {
                    Collections.sort(collection);
                } else if (sortedByDate) {
                    Collections.sort(collection);
                    Collections.sort(collection, new ItemByDateLoanedComparator());

                }
            }
            list.refresh();
        });

        HBox loanedToLine = new HBox();
        mainPane2.getChildren().addAll(loanedToLine);
        Label loanedTo = new Label("Loaned To:");
        TextField loanedToField = new TextField();
        loanedToLine.getChildren().addAll(loanedTo);
        loanedToLine.getChildren().addAll(loanedToField);

        HBox loanedOnLine = new HBox();
        mainPane2.getChildren().addAll(loanedOnLine);
        Label loanedOn = new Label("Loaned On:");
        TextField loanedOnField = new TextField("MM-dd-yyyy");
        loanedOnLine.getChildren().addAll(loanedOn);
        loanedOnLine.getChildren().addAll(loanedOnField);

        Button loanButton = new Button("Loan");
        mainPane2.getChildren().addAll(loanButton);
        loanButton.setOnAction(e -> {
            try {
                String loanedToString = loanedToField.getText();
                Date loanedOnDate = new SimpleDateFormat(
                        "MM-dd-yyyy").parse(loanedOnField.getText());
                Item item = list.getSelectionModel().getSelectedItem();
                item.loanItem(loanedToString, loanedOnDate);
                if (isSorted) {
                    if (sortedByAlphabet) {
                        Collections.sort(collection);
                    } else if (sortedByDate) {
                        Collections.sort(collection);
                        Collections.sort(collection, new ItemByDateLoanedComparator());

                    }
                }
                list.refresh();
            } catch (Exception ex) {

            }

        });

        Label sort = new Label("Sort");
        mainPane2.getChildren().addAll(sort);

        RadioButton byTitle = new RadioButton("By Title");
        RadioButton byDateLoaned = new RadioButton("By date loaned");
        mainPane2.getChildren().addAll(byTitle);
        mainPane2.getChildren().addAll(byDateLoaned);

        ToggleGroup group = new ToggleGroup();
        byTitle.setToggleGroup(group);
        byDateLoaned.setToggleGroup(group);

        byTitle.setOnAction(e -> {
            isSorted = true;
            sortedByAlphabet = true;
            sortedByDate = false;
            Collections.sort(collection);
            list.refresh();
        });

        byDateLoaned.setOnAction(e -> {
            isSorted = true;
            sortedByDate = true;
            sortedByAlphabet = false;
            Collections.sort(collection);
            Collections.sort(collection, new ItemByDateLoanedComparator());
            list.refresh();
        });

        Scene scene = new Scene(mainPane, 550, 400);
        primaryStage.setTitle("Media Collection");
        primaryStage.setScene(scene);
        primaryStage.show();
        primaryStage.setOnCloseRequest((WindowEvent we) -> {
            try {
                ObjectOutputStream oos = new ObjectOutputStream(
                        new FileOutputStream(new File("collection.dat")));
                oos.writeObject(new ArrayList<Item>(collection));
                oos.close();
            } catch (Exception ex) {

            }
        });

    }

    public static void main(String[] args) {
        Application.launch(args);
    }

}
