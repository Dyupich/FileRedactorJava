package sample;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class Controller {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button showButton;

    @FXML
    private Button propertiesButton;

    @FXML
    private Button deleteButton;

    @FXML
    private Button readButton;

    @FXML
    private Button newFileButton;

    @FXML
    private Button newCatalogueButton;


    @FXML
    private TextField textFieldAddress;

    @FXML
    private TextArea textFieldMain;

    @FXML
    private Label AddressLabel;

    @FXML
    void deleteButtonClicked(ActionEvent event)
    {
        File file = new File(textFieldAddress.getText());

        if (file.exists())
        {
            textFieldMain.setText("Файл удален по адресу " + textFieldAddress.getText() + " удален!");
            file.delete();
        }
        else
        {
            textFieldMain.setText("Файла с таким названием по этому адресу не существует");
        }
    }

    @FXML
    void newCatalogueButtonClicked(ActionEvent event)
    {
        File file = new File(textFieldAddress.getText());

        if (!file.exists())
        {
                file.mkdir();
                textFieldMain.setText("Каталог успешно создан по адресу: " + textFieldAddress.getText());
        }
        else
        {
            textFieldMain.setText("Что-то пошло не так");
        }
    }

    @FXML
    void newFileButtonClicked(ActionEvent event)
    {
        File file = new File(textFieldAddress.getText());

        if (!file.exists())
        {
            try
            {
                file.createNewFile();
                textFieldMain.setText("Файл успешно создан по адресу " + textFieldAddress.getText());


            } catch (IOException exception) {
                textFieldMain.setText("Ошибка создания файла: " + exception.getMessage());
            }
        }
        else
        {
            textFieldMain.setText("Что-то пошло не так");
        }
    }

    @FXML
    void propertiesButtonClicked(ActionEvent event)
    {
        File file = new File(textFieldAddress.getText());
        String text = new String();
        if (file.exists())
        {
            try
            {
                BasicFileAttributes attributes = Files.readAttributes(Path.of(textFieldAddress.getText()), BasicFileAttributes.class);

                text += "creationTime: " + attributes.creationTime() + "\n";
                text += "lastAccessTime: " + attributes.lastAccessTime() + "\n";
                text += "lastModifiedTime: " + attributes.lastModifiedTime() + "\n";
                text += "fileKey: " + attributes.fileKey() + "\n";
                text += "isRegularFile: " + attributes.isRegularFile() + "\n";
                text += "isDirectory: " + attributes.isDirectory() + "\n";
                text += "isOther: " + attributes.isOther() + "\n";
                text += "size: " + attributes.size() + " байт" + "\n";
                text += "isSymbolicLink: " + attributes.isSymbolicLink() + "\n";
            }
            catch (IOException exception)
            {
                textFieldMain.setText("Ошибка:" + exception.getMessage());
                text = "Ошибка:" + exception.getMessage();
            }

                textFieldMain.setText(text);
        }
        else
        {
            textFieldMain.setText("Файла с таким названием не существует");
        }
    }

    @FXML
    void readButtonClicked(ActionEvent event)
    {

        File file = new File(textFieldAddress.getText());

        if (file.exists())
        {

            try
            {
                ProcessBuilder pb = new ProcessBuilder("Notepad.exe", textFieldAddress.getText());
                pb.start();
            } catch (IOException exception) {

                textFieldMain.setText("Ошибка:" + exception.getMessage());
            }
        }
        else
        {
                textFieldMain.setText("Файла по данному расположению не существует");
        }
    }

    @FXML
    void showButtonClicked(ActionEvent event)
    {
        File file = new File(textFieldAddress.getText());
        String text = new String();
        if (file.exists() & file.isDirectory())
        {
            File[] files = file.listFiles();

            for (File iterator : files)
            {
                if (iterator.isDirectory())
                {
                    text += "Папка:  " + iterator.getName();
                } else if (iterator.isFile())
                {
                    text += "Файл:  " + iterator.getName();
                }
                text += "\n";
            }
        }
        textFieldMain.setText(text);
    }


    @FXML
    void initialize()
    {
        textFieldAddress.setText(System.getProperty("user.dir"));
    }
}
