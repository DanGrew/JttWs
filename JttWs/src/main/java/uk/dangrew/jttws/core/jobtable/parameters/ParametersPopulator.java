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

import uk.dangrew.jttws.core.jobtable.TableData;
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
    * @param tableData the {@link TableData} being configured.
    * @param request the {@link HttpServletRequest}.
    * @param response the {@link HttpServletResponse}.
    * @return the constructed {@link JobTableParameters}.
    */
   public JobTableParameters construct( TableData tableData, HttpServletRequest request, HttpServletResponse response ){
      JobTableParameters parameters = new JobTableParameters();
      
      for ( Column column : tableData.columns() ) {
         parameters.filterBy( column.name(), cookies.retrieveCookie( column.id(), request, response ) );
      }
      
      String id = cookies.retrieveCookie( ID, request, response );
      if ( id == null ) {
         return parameters;
      }
      
      Column column = tableData.columnForId( id );
      if ( column == null ) {
         return parameters;
      }
       
      String filter = cookies.retrieveCookie( VALUE, request, response );
      cookies.saveCookie( column.id(), filter, response );
      parameters.filterBy( column.name(), filter );
      return parameters;
   }//End Method

}//End Class
