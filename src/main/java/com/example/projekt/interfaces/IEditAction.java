package com.example.projekt.interfaces;

/**
 * Helper interface for EditAction interactions between Form and List
 * @param <T> type of the object to be edited
 */
public interface IEditAction<T> {
    /**
     * Method to enable edit mode and pass edited object to Form
     * @param entity object to be edited
     */
    void edit(T entity);
}
