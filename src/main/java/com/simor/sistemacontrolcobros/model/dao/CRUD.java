package com.simor.sistemacontrolcobros.model.dao;

import java.util.List;

public interface CRUD<T> {

    /**
     * Finds a single record by its identifier.
     * @param id the unique identifier of the record to find.
     * @return the record if found, or null otherwise.
     */
    T findOne(int id);

    /**
     * Retrieves all records from the data source.
     * @return a list of all records.
     */
    List<T> findAll();

    /**
     * Inserts a new record into the data source.
     * @param entity the record to insert.
     * @return true if the insertion was successful, false otherwise.
     */
    boolean insert(T entity);

    /**
     * Updates an existing record in the data source.
     * @param entity the record with updated information.
     * @return true if the update was successful, false otherwise.
     */
    boolean update(T entity);

    /**
     * Deletes a record from the data source by its identifier.
     * @param id the unique identifier of the record to delete.
     * @return true if the deletion was successful, false otherwise.
     */
    boolean delete(int id);
}
