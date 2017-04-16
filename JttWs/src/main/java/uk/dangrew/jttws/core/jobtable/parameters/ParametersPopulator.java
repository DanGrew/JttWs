/*
 * ----------------------------------------
 *    Jenkins Test Tracker Web Services
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2017
 * ----------------------------------------
 */
package uk.dangrew.jttws.core.jobtable.parameters;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;

import uk.dangrew.jttws.core.jobtable.TableSpecification;
import uk.dangrew.jttws.core.jobtable.structure.Column;
import uk.dangrew.jttws.mvc.web.configuration.CookieManager;

/**
 * The {@link ParametersPopulator} is responsible for managing the parameters provided
 * by the web ui and populating them for handling by the server.
 */
@Component
public class ParametersPopulator {
   
   static final String VALUE = "value";
   static final String ID = "id";
   static final String SORT = "sort";
   
   private final CookieManager cookies;
   
   /**
    * Constructs a new {@link ParametersPopulator}.
    */
   public ParametersPopulator() {
      this( new CookieManager() );
   }//End Constructor
   
   /**
    * Constructs a new {@link ParametersPopulator}.
    * @param cookies the {@link CookieManager}.
    */
   ParametersPopulator( CookieManager cookies ){
      this.cookies = cookies;
   }//End Constructor

   /**
    * Method to construct the {@link JobTableParameters} for handling by the server, saving any newly applied configuration.
    * @param tableData the {@link TableSpecification} being configured.
    * @param request the {@link HttpServletRequest}.
    * @param response the {@link HttpServletResponse}.
    * @return the constructed {@link JobTableParameters}.
    */
   public JobTableParameters construct( TableSpecification tableData, HttpServletRequest request, HttpServletResponse response ){
      JobTableParameters parameters = new JobTableParameters();
      
      populateFilters( parameters, tableData, request, response );
      populateSorting( parameters, tableData, request, response );
      
      return parameters;
   }//End Method
   
   /**
    * Method to populate the filters information for the page from the given parameters.
    * @param parameters the {@link JobTableParameters}.
    * @param tableData the {@link TableSpecification}.
    * @param request the {@link HttpServletRequest}.
    * @param response the {@link HttpServletResponse}.
    */
   private void populateFilters( 
            JobTableParameters parameters, 
            TableSpecification tableData, 
            HttpServletRequest request, 
            HttpServletResponse response 
   ){
      for ( Column column : tableData.columns() ) {
         parameters.filterBy( column.name(), cookies.retrieveCookie( column.id(), request, response ) );
      }
      
      String id = cookies.retrieveCookie( ID, request, response );
      if ( id == null ) {
         return;
      }
      
      Column column = tableData.columnForId( id );
      if ( column == null ) {
         return;
      }
       
      String filter = cookies.retrieveCookie( VALUE, request, response );
      cookies.saveCookie( column.id(), filter, response );
      parameters.filterBy( column.name(), filter );
   }//End Method
   
   /**
    * Method to populate the sorting information for the page from the given parameters.
    * @param parameters the {@link JobTableParameters}.
    * @param tableData the {@link TableSpecification}.
    * @param request the {@link HttpServletRequest}.
    * @param response the {@link HttpServletResponse}.
    */
   private void populateSorting( 
            JobTableParameters parameters, 
            TableSpecification tableData, 
            HttpServletRequest request, 
            HttpServletResponse response 
   ){
      String sort = cookies.retrieveCookie( SORT, request, response );
      if ( sort == null ) {
         return;
      }
      
      parameters.sortBy( sort );
   }//End Method

}//End Class
