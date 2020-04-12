/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hypocampus.controller;


import com.hypocampus.models.Sprint;
import com.hypocampus.models.Task;
import com.hypocampus.services.ServiceSprint;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DataFormat;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.AnchorPane;


/**
 * FXML Controller class
 *
 * @author 21694
 */
public class DRAG_DROPController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        afficher();
    }    
    @FXML
    private AnchorPane ContentPane;
    @FXML
    private Button backAffsprint;

    @FXML
    private ListView<Task> to_do;

    @FXML
    private ListView<Task> in_Progress;

    @FXML
    private ListView<Task> Done;
    static final DataFormat Task_LIST = new DataFormat("TaskList");
    ServiceSprint st =new ServiceSprint();
        
         public void inflateUI(Sprint S) {      
         ServiceSprint ss =new ServiceSprint();
         to_do.setItems((ObservableList<Task>) ss.afficher_Sprintask_toDo2(new Sprint(S.getId())));
         in_Progress.setItems((ObservableList<Task>) ss.afficher_Sprintask_INProgress2(new Sprint(S.getId())));
         Done.setItems((ObservableList<Task>) ss.afficher_Sprintask_Done2(new Sprint(S.getId())));
    } 
         public void afficher()
     {
                       
  		to_do.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		in_Progress.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
                Done.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);


                // Add mouse event handlers for the source
		to_do.setOnDragDetected(new EventHandler <MouseEvent>()
		{
            public void handle(MouseEvent event)
            {
            	//writelog("Event on Source: drag detected");
            	dragDetected(event, to_do);
            }
        });

		to_do.setOnDragOver(new EventHandler <DragEvent>()
		{
            public void handle(DragEvent event)
            {
            	//writelog("Event on Source: drag over");
            	dragOver(event, to_do);
            }
        });

		to_do.setOnDragDropped(new EventHandler <DragEvent>()
		{
            public void handle(DragEvent event)
            {
               
                Task done = Done.getSelectionModel().getSelectedItem();
                Task inProgress = in_Progress.getSelectionModel().getSelectedItem();
                if(inProgress!=null){
                    
                    st.modifierstate_todo(inProgress);
                    st.updatesprintetat2(inProgress);
                    dragDropped(event, to_do);
                }
                if(done!=null){
                    
                    st.modifierstate_todo(done);
                    st.updatesprintetat2(done);
                    dragDropped(event, to_do);
                }
         	
            }
        });

		to_do.setOnDragDone(new EventHandler <DragEvent>()
		{
            public void handle(DragEvent event)
            {
            	//writelog("Event on Source: drag done");
            	dragDone(event, to_do);
            }
        });

                
     
		// Add mouse event handlers for the target
		in_Progress.setOnDragDetected(new EventHandler <MouseEvent>()
		{
            public void handle(MouseEvent event)
            {
            	//writelog("Event on Target: drag detected");
            	dragDetected(event, in_Progress);
            }
        });

		in_Progress.setOnDragOver(new EventHandler <DragEvent>()
		{
            public void handle(DragEvent event)
            {
            	//writelog("Event on Target: drag over");
            	dragOver(event, in_Progress);
            }
        });

		in_Progress.setOnDragDropped(new EventHandler <DragEvent>()
		{
            public void handle(DragEvent event)
            {
                Task todo = to_do.getSelectionModel().getSelectedItem();
                Task done = Done.getSelectionModel().getSelectedItem();
                //Task inProgress = in_Progress.getSelectionModel().getSelectedItem();
                //Task t = Done.getSelectionModel().getSelectedItem();
                if(todo!=null){
                    
                    st.modifierstate_InProgress(todo);
                    st.updatesprintetat2(todo);

                    dragDropped(event, in_Progress);
                }
                if(done!=null){
                    
                    st.modifierstate_InProgress(done);
                    st.updatesprintetat2(done);

                    dragDropped(event, in_Progress);
                } 
            	
                
            	
            }
        });

		in_Progress.setOnDragDone(new EventHandler <DragEvent>()
		{
            public void handle(DragEvent event)
            {
            	//writelog("Event on Target: drag done");
            	dragDone(event, in_Progress);
            }
        });           
                
       //*************
       
		// Add mouse event handlers for the target
		Done.setOnDragDetected(new EventHandler <MouseEvent>()
		{
            public void handle(MouseEvent event)
            {
            	dragDetected(event, Done);
            }
        });

		Done.setOnDragOver(new EventHandler <DragEvent>()
		{
            public void handle(DragEvent event)
            {
            	dragOver(event, Done);
            }
        });

		Done.setOnDragDropped(new EventHandler <DragEvent>()
		{
            public void handle(DragEvent event)
            {
                Task todo = to_do.getSelectionModel().getSelectedItem();
                Task inProgress = in_Progress.getSelectionModel().getSelectedItem();
                //Task t = Done.getSelectionModel().getSelectedItem();
                if(todo!=null){
                    
                 
                    st.modifierstate_Done(todo);
                    if (st.getDone(todo.getSprint_id())== st.getDoneT(todo.getSprint_id())){
                        st.updatesprintetat(inProgress);
                    }
                    dragDropped(event, Done);
                }
                if(inProgress!=null){
                    
                    st.modifierstate_Done(inProgress);
                    if (st.getDone(inProgress.getSprint_id())== st.getDoneT(inProgress.getSprint_id())){
                        st.updatesprintetat(inProgress);
                    }
                    
                    //st.updatesprintetat2(inProgress);
                    dragDropped(event, Done);
                }  
                
                
            	
            }
        });

		Done.setOnDragDone(new EventHandler <DragEvent>()
		{
            public void handle(DragEvent event)
            { 
            
            	dragDone(event, Done);
            }
        });           
                
                
                              
     }
      
      	// Create the task List


	private void dragDetected(MouseEvent event, ListView<Task> listView)
	{
		// Make sure at least one item is selected
		int selectedCount = listView.getSelectionModel().getSelectedIndices().size();

		if (selectedCount == 0)
		{
			event.consume();
			return;
		}

		// Initiate a drag-and-drop gesture
		Dragboard dragboard = listView.startDragAndDrop(TransferMode.COPY_OR_MOVE);

		// Put the the selected items to the dragboard
		ArrayList<Task> selectedItems = this.getSelectedtasks(listView);

		ClipboardContent content = new ClipboardContent();
		content.put(Task_LIST, selectedItems);

		dragboard.setContent(content);
		event.consume();
	}

	private void dragOver(DragEvent event, ListView<Task> listView)
	{
		// If drag board has an ITEM_LIST and it is not being dragged
		// over itself, we accept the MOVE transfer mode
		Dragboard dragboard = event.getDragboard();

		if (event.getGestureSource() != listView && dragboard.hasContent(Task_LIST))
		{
			event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
		}

		event.consume();
	}

	@SuppressWarnings("unchecked")
	private void dragDropped(DragEvent event, ListView<Task> listView)
	{
		boolean dragCompleted = false;

		// Transfer the data to the target
		Dragboard dragboard = event.getDragboard();

		if(dragboard.hasContent(Task_LIST))
		{
			ArrayList<Task> list = (ArrayList<Task>)dragboard.getContent(Task_LIST);
			listView.getItems().addAll(list);
			// Data transfer is successful
			dragCompleted = true;
		}

		// Data transfer is not successful
		event.setDropCompleted(dragCompleted);
		event.consume();
	}

	private void dragDone(DragEvent event, ListView<Task> listView)
	{
		// Check how data was transfered to the target
		// If it was moved, clear the selected items
		TransferMode tm = event.getTransferMode();

		if (tm == TransferMode.MOVE)
		{
			removeSelectedtasks(listView);
		}

		event.consume();
	}

	private ArrayList<Task> getSelectedtasks(ListView<Task> listView)
	{
		// Return the list of selected task in an ArratyList, so it is
		// serializable and can be stored in a Dragboard.
		ArrayList<Task> list = new ArrayList<>(listView.getSelectionModel().getSelectedItems());

		return list;
	}

	private void removeSelectedtasks(ListView<Task> listView)
	{
		// Get all selected tasks in a separate list to avoid the shared list issue
		List<Task> selectedList = new ArrayList<>();

		for(Task task : listView.getSelectionModel().getSelectedItems())
		{
			selectedList.add(task);
		}

		// Clear the selection
		listView.getSelectionModel().clearSelection();
		// Remove items from the selected list
		listView.getItems().removeAll(selectedList);
	}   
         
    @FXML
    void backAffsprint(ActionEvent event) throws IOException {
    AnchorPane pane = FXMLLoader.load(getClass().getResource("/com/hypocampus/gui/afficherProject.fxml"));
    
        ContentPane.getChildren().setAll(pane);
    }
   
}
