/*
 * ----------------------------------------
 *    Jenkins Test Tracker Web Services
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2017
 * ----------------------------------------
 */
package uk.dangrew.jttws.core.jobtable.web;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import uk.dangrew.jttws.mvc.repository.PageJob;

/**
 * {@link PageTable} provides a web ui representation of the {@link PageJob} table.
 */
public class PageTable {
   
   private final Set< PageColumn > columns;
   private final Map< PageColumn, Set< PageFilter > > filters;
   private final Set< PageSorting > sortings;
   private final Set< PageJob > jobs;
   
   /**
    * Constructs a new {@link PageTable}.
    */
   public PageTable() {
      this.columns = new LinkedHashSet<>();
      this.filters = new LinkedHashMap<>();
      this.sortings = new LinkedHashSet<>();
      this.jobs = new LinkedHashSet<>();
   }//End Constructor

   /**
    * Access to the {@link PageColumn}s.
    * @return the {@link PageColumn}s.
    */
   public List< PageColumn > columns() {
      return new ArrayList<>( columns );
   }//End Method

   /**
    * Method to add the given {@link PageColumn} to the table.
    * @param column the {@link PageColumn}.
    */
   public void addColumn( PageColumn column ) {
      columns.add( column );
   }//End Method

   /**
    * Access to the {@link PageFilter}s for the given {@link PageColumn}.
    * @param column the {@link PageColumn} the {@link PageFilter}s are for.
    * @return the {@link List} of {@link PageFilter}s.
    */
   public List< PageFilter > filtersFor( PageColumn column ) {
      Set< PageFilter > columnFilters = filters.get( column );
      if ( columnFilters == null ) {
         return new ArrayList<>();
      } else {
         return new ArrayList<>( columnFilters );
      }
   }//End Method

   /**
    * Method to add a {@link PageFilter} to the given {@link PageColumn}.
    * @param column the {@link PageColumn} to add a {@link PageFilter} for.
    * @param filter the {@link PageFilter} to add.
    */
   public void addFilter( PageColumn column, PageFilter filter ) {
      if ( !columns.contains( column ) ) {
         throw new IllegalArgumentException( "Column is not part of table: " + column.name() );
      }
      
      Set< PageFilter > columnFilters = filters.get( column );
      if ( columnFilters == null ) {
         columnFilters = new LinkedHashSet<>();
         filters.put( column, columnFilters );
      }
      columnFilters.add( filter );
   }//End Method

   /**
    * Access to the {@link PageSorting}s.
    * @return the {@link PageSorting}s.
    */
   public List< PageSorting > sortings() {
      return new ArrayList<>( sortings );
   }//End Method

   /**
    * Method to add a {@link PageSorting}.
    * @param sorting the {@link PageSorting}.
    */
   public void addSorting( PageSorting sorting ) {
      sortings.add( sorting );
   }//End Method

   /**
    * Access to the {@link PageJob}s to show.
    * @return the {@link PageJob}s.
    */
   public List< PageJob > jobs() {
      return new ArrayList<>( jobs );
   }//End Method

   /**
    * Method to add the {@link PageJob}.
    * @param job the {@link PageJob}.
    */
   public void addJob( PageJob job ) {
      jobs.add( job );
   }//End Method

}//End Class
