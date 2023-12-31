package br.com.senac.aulaprojeto;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

import java.net.URL;

public class JavaFxApplication extends Application {

    private ConfigurableApplicationContext applicationContext;

    @Override
    public void init () {
        String [] args = getParameters().getRaw().toArray(new   String[0]);

        this.applicationContext = new SpringApplicationBuilder().
                sources(AulaProjetoApplication.class).run(args);
    }

    @Override
   public void start (Stage stage) throws Exception{
        FXMLLoader loader = new FXMLLoader();
        URL xmlUrl = getClass().getResource("/main.fxml");
        loader.setLocation(xmlUrl);
        Parent root = loader.load();

        stage.setScene(new Scene(root));
        stage.setTitle("Cadastro Cliente");
        stage.show();
    }
    /*public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/fxml/loginScene.fxml"));
        Parent root = loader.load();
        stage.setScene(new Scene(root));
        stage.show();
    }*/
}
