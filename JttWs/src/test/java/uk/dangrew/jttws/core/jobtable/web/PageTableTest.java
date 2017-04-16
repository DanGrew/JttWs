/*
 * ----------------------------------------
 *    Jenkins Test Tracker Web Services
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2017
 * ----------------------------------------
 */
package uk.dangrew.jttws.core.jobtable.web;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import uk.dangrew.jttws.mvc.repository.PageJob;

public class PageTableTest {

   @Mock private PageJob job1;
   @Mock private PageJob job2;
   @Mock private PageColumn column1;
   @Mock private PageColumn column2;
   @Mock private PageSorting sorting1;
   @Mock private PageSorting sorting2;
   @Mock private PageFilter filter1;
   @Mock private PageFilter filter2;
   private PageTable systemUnderTest;

   @Before public void initialiseSystemUnderTest() {
      MockitoAnnotations.initMocks( this );
      systemUnderTest = new PageTable();
   }//End Method

   @Test public void shouldProvideColumns() {
      assertThat( systemUnderTest.columns(), is( new ArrayList<>() ) );
      systemUnderTest.addColumn( column1 );
      assertThat( systemUnderTest.columns(), is( Arrays.asList( column1 ) ) );
      systemUnderTest.addColumn( column2 );
      assertThat( systemUnderTest.columns(), is( Arrays.asList( column1, column2 ) ) );
   }//End Method
   
   @Test public void shouldHandleDuplicateColumns() {
      systemUnderTest.addColumn( column1 );
      systemUnderTest.addColumn( column1 );
      assertThat( systemUnderTest.columns(), is( Arrays.asList( column1 ) ) );
   }//End Method
   
   @Test public void shouldProvideFiltersForColumn() {
      systemUnderTest.addColumn( column1 );
      systemUnderTest.addColumn( column2 );
      
      assertThat( systemUnderTest.filtersFor( column1 ), is( new ArrayList<>() ) );
      systemUnderTest.addFilter( column1, filter1 );
      assertThat( systemUnderTest.filtersFor( column1 ), is( Arrays.asList( filter1 ) ) );
      systemUnderTest.addFilter( column1, filter2 );
      assertThat( systemUnderTest.filtersFor( column1 ), is( Arrays.asList( filter1, filter2 ) ) );
      
      assertThat( systemUnderTest.filtersFor( column2 ), is( new ArrayList<>() ) );
   }//End Method
   
   @Test public void shouldProvideNoFiltersForColumnNotPresent() {
      assertThat( systemUnderTest.filtersFor( column1 ), is( new ArrayList<>() ) );
   }//End Method
   
   @Test( expected = IllegalArgumentException.class ) public void shouldNotAcceptFilterForColumnThatIsNotPresent() {
      systemUnderTest.addFilter( column1, filter1 );
   }//End Method
   
   @Test public void shouldHandleDuplicateFilterForColumn() {
      systemUnderTest.addColumn( column1 );
      
      assertThat( systemUnderTest.filtersFor( column1 ), is( new ArrayList<>() ) );
      systemUnderTest.addFilter( column1, filter1 );
      systemUnderTest.addFilter( column1, filter1 );
      
      assertThat( systemUnderTest.filtersFor( column1 ), is( Arrays.asList( filter1 ) ) );
   }//End Method
   
   @Test public void shouldProvideSortings() {
      assertThat( systemUnderTest.sortings(), is( new ArrayList<>() ) );
      systemUnderTest.addSorting( sorting1 );
      assertThat( systemUnderTest.sortings(), is( Arrays.asList( sorting1 ) ) );
      systemUnderTest.addSorting( sorting2 );
      assertThat( systemUnderTest.sortings(), is( Arrays.asList( sorting1, sorting2 ) ) );
   }//End Method
   
   @Test public void shouldHandleDuplicateSortings() {
      systemUnderTest.addSorting( sorting1 );
      systemUnderTest.addSorting( sorting1 );
      assertThat( systemUnderTest.sortings(), is( Arrays.asList( sorting1 ) ) );
   }//End Method
   
   @Test public void shouldProvideFilteredJobs(){
      assertThat( systemUnderTest.jobs(), is( new ArrayList<>() ) );
      systemUnderTest.addJob( job1 );
      assertThat( systemUnderTest.jobs(), is( Arrays.asList( job1 ) ) );
      systemUnderTest.addJob( job2 );
      assertThat( systemUnderTest.jobs(), is( Arrays.asList( job1, job2 ) ) );
   }//End Method
   
   @Test public void shouldHandleDuplicateJobs() {
      systemUnderTest.addJob( job1 );
      systemUnderTest.addJob( job1 );
      assertThat( systemUnderTest.jobs(), is( Arrays.asList( job1 ) ) );
   }//End Method

}//End Class
