package com.example.projekt.controllers;

import com.example.projekt.database.DefaultDbAccessor;

/**
 * Abstract controller class for forms.
 * @param <T> The type of the entity which we want to work with.
 */
public abstract class FormController<T> {
    /**
     * Reference to associated ListController.
     * It is needed to refresh the list after form submission.
     */
    protected ListController<T> listController;
    /**
     * Boolean variable to indicate if it's edit mode .
     */
    protected boolean editMode;
    /**
     * Currently editing item, if not editing this should be null.
     */
    protected T editingItem;
    /**
     * DbAccessor for this controller.
     */
    protected final DefaultDbAccessor<T> dbAccessor;

    /**
     * Constructor.
     * @param clazz class of entity which we're working with.
     */
    public FormController(Class<T> clazz) {
        dbAccessor = new DefaultDbAccessor<>(clazz);
    }

    /**
     * Sets the associated list controller.
     * @param listController ListController to be set.
     */
    public void setListController(ListController<?> listController) {
        this.listController = (ListController<T>) listController;
        this.listController.edit = this::edit;
    }

    /**
     * Event handler for save form button.
     * Handles form validation and saves or updates the item to database.
     */
    public void add() {
        if(!validate()){
            showError();
            return;
        }

        editingItem = editMode ? modelFromForm(editingItem) : modelFromForm(null);
        dbAccessor.save(editingItem, editMode);
        cancel();
    }

    /**
     * Event handler for edit ListView menu.
     * Sets the editing mode to true and loads the item to form.
     * @param item item to be edited.
     */
    protected void edit(T item){
        editMode = true;
        setInfoLabel(true);
        editingItem = item;
        formFromModel(item);
    }

    /**
     * Event handler for form cancel button.
     * Sets the editing mode to false, clears the form and reloads list.
     */
    public void cancel() {
        editMode = false;
        editingItem = null;
        formFromModel(null);
        setInfoLabel(false);
        hideError();
        reloadList();
    }

    /**
     * Wrapper function for reloading list.
     */
    private void reloadList() {
        listController.reload();
    }

    /**
     * Loads the item to form.
     * @param item entity to be loaded in form.
     */
    protected abstract void formFromModel(T item);

    /**
     * Updates passed model with data in form.
     * if model is null, creates new model with data from form.
     * @param item entity to be loaded in form.
     * @return model with data from form.
     */
    protected abstract T modelFromForm(T item);
    /**
     * Validates the form.
     * @return true if form is valid, false otherwise.
     */
    protected abstract boolean validate();

    /**
     * Sets the info label.
     * @param edit true if it's edit mode, false otherwise.
     */
    protected abstract void setInfoLabel(boolean edit);
    /**
     * Shows error message.
     */
    protected abstract void showError();
    /**
     * Hides error message.
     */
    protected abstract void hideError();
}
