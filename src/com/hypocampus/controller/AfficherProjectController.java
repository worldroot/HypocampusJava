/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hypocampus.controller;

import com.hypocampus.models.Project;
import com.hypocampus.models.Sprint;
import com.hypocampus.services.ServiceProject;
import com.hypocampus.utils.DataSource;
import javafx.scene.input.MouseEvent;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Pagination;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.control.TableColumn;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;

import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;



/**
 * FXML Controller class
 *
 * @author 21694
 */
public class AfficherProjectController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
      
        afficher();    
        //createChart();

    }    
    
    @FXML
    private AnchorPane ContentPane;
    @FXML
    private Pagination pagination;
    @FXML
    private TableView<Project> tab;

    @FXML
    private TableColumn<Project, String> Colprojetname;

    @FXML
    private TableColumn<Project, String> Colowner;

    @FXML
    private TableColumn<Project, Date> Colstartdate;

    @FXML
    private TableColumn<Project, Date> Colenddate;

    @FXML
    private TableColumn<Project, String> Coldescription;
    @FXML
    private TableColumn<Sprint, String> Colprogress;
    @FXML
    private Button affSprint;
    @FXML
    private Button history;
    @FXML 
    private javafx.scene.control.Button closeButton;
    @FXML
    private ImageView stat;

    
    private List<Project> L = new ArrayList();
    public List <Project> data2 ;
    private final ObservableList<Project> dataList = FXCollections.observableArrayList();
        
    int from =0,to=0;
    int intempage=3;
    Connection cnx = DataSource.getInstance().getCnx();


    
     public void afficher()
     {
          
             ServiceProject sP =new ServiceProject();

                    Colprojetname.setCellValueFactory(new PropertyValueFactory<>("name"));
                    Colowner.setCellValueFactory(new PropertyValueFactory<>("owner"));
                    Colstartdate.setCellValueFactory(new PropertyValueFactory<>("start_date"));
                    Colenddate.setCellValueFactory(new PropertyValueFactory<>("end_date"));
                    Coldescription.setCellValueFactory(new PropertyValueFactory<>("description"));
                    Colprogress.setCellValueFactory(new PropertyValueFactory<>("progressbar"));
                    
                
                            
                    
                    //Pagination
                    int pagecount=(sP.affPagination()/intempage)+1;
                    pagination.setPageCount(pagecount);
                    pagination.setPageFactory(this::creatPage);
                    tab.setItems((ObservableList<Project>) sP.afficher());
     }
    @FXML
    void newProjectAction(ActionEvent event) throws IOException {
   AnchorPane pane = FXMLLoader.load(getClass().getResource("/com/hypocampus/gui/Project.fxml"));
        ContentPane.getChildren().setAll(pane);
    }
    
        @FXML
    void historyAction(ActionEvent event) throws IOException {
   AnchorPane pane = FXMLLoader.load(getClass().getResource("/com/hypocampus/gui/historyProject.fxml"));
        ContentPane.getChildren().setAll(pane);
    }

    
    
    @FXML
    void supprimerProject(ActionEvent event) {
         
          Project Pr = tab.getSelectionModel().getSelectedItem();
     
            if (Pr == null) {

           Image img = new Image("/com/hypocampus/uploads/error.png");
                      Notifications n = Notifications.create()
                                                     .title("Error")
                                                     .text("  Choix invalide")
                                                     .graphic(new ImageView(img))
                                                     .position(Pos.TOP_CENTER)
                                                     .hideAfter(Duration.seconds(5));
                                    n.darkStyle();
                                    n.show();
                                }
             else{
                 ServiceProject sP =new ServiceProject();
                 Project P =new Project(Pr.getId(),1);
                 Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                                    alert.setTitle("Confirmation ");
                                    alert.setHeaderText(null);
                                    alert.setContentText("Vous voulez vraiment supprimer ce projet ");
                                    Optional<ButtonType> action = alert.showAndWait();
                    if (action.get() == ButtonType.OK) {
                                       sP.modifier_history(P);
                                       afficher();

                                                       }
                                  }
 
    }
    
    
    
    @FXML
    void editprojet(ActionEvent event) throws IOException {
        
         Project Pr = tab.getSelectionModel().getSelectedItem();

                                  if (Pr == null) {

                                    Image img = new Image("/com/hypocampus/uploads/error.png");
                                    Notifications n = Notifications.create()
                                                     .title("Error")
                                                     .text("No Project selected :  Please select a Project for edit")
                                                     .graphic(new ImageView(img))
                                                     .position(Pos.TOP_CENTER)
                                                      .hideAfter(Duration.seconds(5));
                                    n.darkStyle();
                                    n.show();
                                }
     else{
                                      
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/hypocampus/gui/EditProject.fxml"));              
            Parent parent = loader.load();
            ContentPane.getChildren().setAll(parent);

            EditProjectController controller =(EditProjectController) loader.getController();
            controller.inflateUI(Pr);
            controller.add(Pr);

            
          }
     
    }

        @FXML
    void affSprint(ActionEvent event) throws IOException {
         Project Pr = tab.getSelectionModel().getSelectedItem();

                                  if (Pr == null) {

                                    Image img = new Image("/com/hypocampus/uploads/error.png");
                                    Notifications n = Notifications.create()
                                                     .title("Error")
                                                     .text("No Project selected :  Please select a project to see their sprint")
                                                     .graphic(new ImageView(img))
                                                     .position(Pos.TOP_CENTER)
                                                      .hideAfter(Duration.seconds(5));
                                    n.darkStyle();
                                    n.show();
                                }
     else{
                                      
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/hypocampus/gui/AfficherSprint.fxml"));              
            Parent parent = loader.load();
            ContentPane.getChildren().setAll(parent);

            AfficherSprintController controllerPS =(AfficherSprintController) loader.getController();
           controllerPS.inflateUI(Pr);
           controllerPS.recherche(Pr);

            
          }
     

    }

  
    
       public List<Project> gettabdata() {
       
        ObservableList <Project> ListProject =FXCollections.observableArrayList();

        try {
            String requete = "SELECT * FROM projets WHERE history=0 LIMIT "+from+","+to+"";
            PreparedStatement pst = cnx.prepareStatement(requete);
            ResultSet rs = pst.executeQuery();
              ServiceProject sP =new ServiceProject();
             Image progress =new Image("/com/hypocampus/uploads/in progress.png");
            Image completed =new Image("/com/hypocampus/uploads/completed.png"); 
            ImageView progressbar ;
            while (rs.next()) {
                 if ((sP.getProgressC(rs.getInt("id"))== sP.getProgressI(rs.getInt("id"))) && (sP.getProgressI(rs.getInt("id"))!=0))
                                         {
                                       progressbar=new ImageView(completed);
                                         }
                                         else{
                                        progressbar=new ImageView(progress);
                                         }
                
                
                ListProject.add(new Project(rs.getInt("id"), rs.getString("projet_name"),rs.getString("owner")
                ,rs.getDate("start_date"), rs.getDate("end_date"),rs.getString("description"),rs.getInt("history"),progressbar));
            }

        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
      
        return ListProject;
    }
   private Node creatPage(int pageIndex) 
   {
       from=pageIndex *intempage;
       to=intempage;
       tab.setItems((ObservableList<Project>) gettabdata());

       return tab;
   }
   
   
     private  PieDataset criarDadosFake() {
        Project Pr = tab.getSelectionModel().getSelectedItem();
        ServiceProject sP =new ServiceProject();
        DefaultPieDataset dataSet = new DefaultPieDataset();
         if (Pr == null) {

            Image img = new Image("/com/hypocampus/uploads/error.png");
            Notifications n = Notifications.create()
                                           .title("Error")
                                           .text("  Choix invalide")
                                           .graphic(new ImageView(img))
                                           .position(Pos.TOP_CENTER)
                                           .hideAfter(Duration.seconds(5));
                                    n.darkStyle();
                                    n.show();
                                     
                                }
         else{ 
             if( sP.getProgressI(Pr.getId())==0){
             dataSet.setValue("Pas des sprints",0);
             }
             else{
             dataSet.setValue("Sprints terminés", sP.getProgressC(Pr.getId()));
             dataSet.setValue("les sprints non réalisés",sP.getProgress(Pr.getId()));
             return dataSet;
             }
         }

        return dataSet;
    }
   
     
     //stat
    @FXML
    void statAction(MouseEvent event) {
        PieDataset pizzaDataSet = criarDadosFake();
         Project Pr = tab.getSelectionModel().getSelectedItem();
        if(Pr!= null){
        SprintStat grafico = new SprintStat(
                "Statistique sur les sprints",
                Pr.getName(),
                pizzaDataSet
        );
         grafico.pack();
        grafico.setVisible(true);
        
        }
    }
 
}
