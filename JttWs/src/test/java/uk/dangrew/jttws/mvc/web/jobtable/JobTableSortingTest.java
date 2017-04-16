/*
 * ----------------------------------------
 *    Jenkins Test Tracker Web Services
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2017
 * ----------------------------------------
 */
package uk.dangrew.jttws.mvc.web.jobtable;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import uk.dangrew.jtt.model.jobs.BuildResultStatus;
import uk.dangrew.jtt.utility.TestCommon;
import uk.dangrew.jttws.mvc.repository.PageJob;
import uk.dangrew.jttws.mvc.web.jobtable.JobTableSorting;

public class JobTableSortingTest {

   @Mock private PageJob firstJws;
   @Mock private PageJob secondJws;
   
   private List< PageJob > jobs;
   private List< PageJob > firstSecond;
   private List< PageJob > secondFirst;
   
   @Before public void initialiseSystemUnderTest(){
      MockitoAnnotations.initMocks( this );
      
      jobs = new ArrayList<>();
      jobs.add( firstJws );
      jobs.add( secondJws );
      
      when( firstJws.name() ).thenReturn( "first" );
      when( secondJws.name() ).thenReturn( "second" );
      
      when( firstJws.progress() ).thenReturn( 45 );
      when( secondJws.progress() ).thenReturn( 67 );
      
      when( firstJws.status() ).thenReturn( BuildResultStatus.SUCCESS );
      when( secondJws.status() ).thenReturn( BuildResultStatus.FAILURE );
      
      when( firstJws.timestampValue() ).thenReturn( 45L );
      when( secondJws.timestampValue() ).thenReturn( 67L );
      
      when( firstJws.committers() ).thenReturn( "one" );
      when( secondJws.committers() ).thenReturn( "two" );
      
      firstSecond = Arrays.asList( firstJws, secondJws );
      secondFirst = Arrays.asList( secondJws, firstJws );
   }//End Method
   
   @Test public void shouldValueOfToString() {
      TestCommon.assertEnumToStringWithValueOf( JobTableSorting.class );
   }//End Method
   
   @Test public void shouldValueOfName() {
      TestCommon.assertEnumNameWithValueOf( JobTableSorting.class );
   }//End Method

   @Test public void shouldCompareNameIncreasing(){
      assertThat( jobs, is( firstSecond ) );
      JobTableSorting.NameIncreasing.sort( jobs );
      assertThat( jobs, is( firstSecond ) );
      
      when( firstJws.name() ).thenReturn( "b" );
      when( secondJws.name() ).thenReturn( "a" );
      JobTableSorting.NameIncreasing.sort( jobs );
      assertThat( jobs, is( secondFirst ) );
   }//End Method
   
   @Test public void shouldCompareNameDecreasing(){
      JobTableSorting.NameDecreasing.sort( jobs );
      assertThat( jobs, is( secondFirst ) );
      
      when( firstJws.name() ).thenReturn( "b" );
      when( secondJws.name() ).thenReturn( "a" );
      JobTableSorting.NameDecreasing.sort( jobs );
      assertThat( jobs, is( firstSecond ) );
   }//End Method
   
   @Test public void shouldCompareProgressIncreasing(){
      assertThat( jobs, is( firstSecond ) );
      JobTableSorting.ProgressIncreasing.sort( jobs );
      assertThat( jobs, is( firstSecond ) );
      
      when( firstJws.progress() ).thenReturn( 50 );
      when( secondJws.progress() ).thenReturn( 3 );
      JobTableSorting.ProgressIncreasing.sort( jobs );
      assertThat( jobs, is( secondFirst ) );
   }//End Method
   
   @Test public void shouldCompareProgressDecreasing(){
      JobTableSorting.ProgressDecreasing.sort( jobs );
      assertThat( jobs, is( secondFirst ) );
      
      when( firstJws.progress() ).thenReturn( 50 );
      when( secondJws.progress() ).thenReturn( 3 );
      JobTableSorting.ProgressDecreasing.sort( jobs );
      assertThat( jobs, is( firstSecond ) );
   }//End Method
   
   @Test public void shouldCompareStatusIncreasing(){
      assertThat( jobs, is( firstSecond ) );
      JobTableSorting.StatusIncreasing.sort( jobs );
      assertThat( jobs, is( firstSecond ) );
      
      when( firstJws.status() ).thenReturn( BuildResultStatus.UNSTABLE );
      when( secondJws.status() ).thenReturn( BuildResultStatus.SUCCESS );
      JobTableSorting.StatusIncreasing.sort( jobs );
      assertThat( jobs, is( secondFirst ) );
   }//End Method
   
   @Test public void shouldCompareStatusDecreasing(){
      JobTableSorting.StatusDecreasing.sort( jobs );
      assertThat( jobs, is( secondFirst ) );
      
      when( firstJws.status() ).thenReturn( BuildResultStatus.UNSTABLE );
      when( secondJws.status() ).thenReturn( BuildResultStatus.SUCCESS );
      JobTableSorting.StatusDecreasing.sort( jobs );
      assertThat( jobs, is( firstSecond ) );
   }//End Method
   
   @Test public void shouldCompareTimestampIncreasing(){
      assertThat( jobs, is( firstSecond ) );
      JobTableSorting.TimestampIncreasing.sort( jobs );
      assertThat( jobs, is( firstSecond ) );
      
      when( firstJws.timestampValue() ).thenReturn( 50L );
      when( secondJws.timestampValue() ).thenReturn( 3L );
      JobTableSorting.TimestampIncreasing.sort( jobs );
      assertThat( jobs, is( secondFirst ) );
   }//End Method
   
   @Test public void shouldCompareTimestampDecreasing(){
      JobTableSorting.TimestampDecreasing.sort( jobs );
      assertThat( jobs, is( secondFirst ) );
      
      when( firstJws.timestampValue() ).thenReturn( 50L );
      when( secondJws.timestampValue() ).thenReturn( 3L );
      JobTableSorting.TimestampDecreasing.sort( jobs );
      assertThat( jobs, is( firstSecond ) );
   }//End Method
   
   @Test public void shouldCompareCommittersIncreasing(){
      assertThat( jobs, is( firstSecond ) );
      JobTableSorting.CommittersIncreasing.sort( jobs );
      assertThat( jobs, is( firstSecond ) );
      
      when( firstJws.committers() ).thenReturn( "b" );
      when( secondJws.committers() ).thenReturn( "a" );
      JobTableSorting.CommittersIncreasing.sort( jobs );
      assertThat( jobs, is( secondFirst ) );
   }//End Method
   
   @Test public void shouldCompareCommittersDecreasing(){
      JobTableSorting.CommittersDecreasing.sort( jobs );
      assertThat( jobs, is( secondFirst ) );
      
      when( firstJws.committers() ).thenReturn( "b" );
      when( secondJws.committers() ).thenReturn( "a" );
      JobTableSorting.CommittersDecreasing.sort( jobs );
      assertThat( jobs, is( firstSecond ) );
   }//End Method
   
}//End Class
