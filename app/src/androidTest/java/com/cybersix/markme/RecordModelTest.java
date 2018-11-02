package com.cybersix.markme;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;

import static org.junit.Assert.*;

public class RecordModelTest {

    @Test
    public void testSetTitle() {
        String title = "title";
        String newTitle = "new title";
        RecordModel rm = new RecordModel(title,"");
        assertEquals(title,rm.getTitle());
        rm.setTitle(newTitle);
        assertEquals(newTitle,rm.getTitle());
    }

    @Test
    public void testGetTitle() {
        String title = "title";
        RecordModel rm = new RecordModel(title,"");
        String getTitle = rm.getTitle();
        assertEquals(title,getTitle);
    }

    @Test
    public void testSetDescription() {
        String desc = "desc";
        String newDesc = "new desc";
        RecordModel rm = new RecordModel("",desc);
        assertEquals(desc,rm.getDescription());
        rm.setDescription(newDesc);
        assertEquals(newDesc,rm.getDescription());
    }

    @Test
    public void testGetDescription() {
        String desc = "desc";
        RecordModel rm = new RecordModel("",desc);
        String getDesc = rm.getDescription();
        assertEquals(desc,getDesc);
    }

    @Test
    public void testSetBodyLocation() {
        RecordModel rm = new RecordModel("test","test");
        for(EBodyPart e : EBodyPart.values()){
            BodyLocation bl = new BodyLocation(e);
            rm.setBodyLocation(bl);
            BodyLocation loc = rm.getBodyLocation();
            assertEquals(bl,loc);
        }
    }

    @Test
    public void testGetBodyLocation() {
        EBodyPart testPart = EBodyPart.CHEST;
        BodyLocation bodyLocation = new BodyLocation(testPart);
        RecordModel rm = new RecordModel("test","test");
        rm.setBodyLocation(bodyLocation);
        BodyLocation getBodyLocation = rm.getBodyLocation();
        assertEquals(getBodyLocation,bodyLocation);
    }


    @Test
    public void testSetTimestamp() {
        RecordModel rm = new RecordModel("test","test");
        Date dateOld = new Date();
        Date dateNew = new Date();
        rm.setTimestamp(dateOld);
        assertEquals(rm.getTimestamp(),dateOld);
        rm.setTimestamp(dateNew);
        assertEquals(rm.getTimestamp(),dateNew);
    }

    @Test
    public void testGetTimestamp() {
        Date date = new Date();
        RecordModel rm = new RecordModel("","");
        rm.setTimestamp(date);
        Date getDate = rm.getTimestamp();
        assertEquals(date,getDate);
    }


    @Test
    public void testSetMapLocation() {
        RecordModel rm = new RecordModel("test","test");
        GPSCoordinate gpOld = new GPSCoordinate();
        GPSCoordinate gpNew = new GPSCoordinate();
        rm.setMapLocation(gpOld);
        assertEquals(rm.getMapLocation(),gpOld);
        rm.setMapLocation(gpNew);
        assertEquals(rm.getMapLocation(),gpNew);
    }

    @Test
    public void testGetMapLocation() {
        RecordModel rm = new RecordModel("test","test");
        GPSCoordinate gp = new GPSCoordinate();
        rm.setMapLocation(gp);
        GPSCoordinate getMapLoc = rm.getMapLocation();
        assertEquals(getMapLoc,gp);
    }

    @Test
    public void testAddPhoto() {
        RecordModel rm = new RecordModel("test","test");
        PhotoRecord photo = new PhotoRecord();
        try{
            rm.addPhoto(photo);
            ArrayList<PhotoRecord> ph = rm.getPhotos();
            assertEquals(photo,ph.get(0));
        } catch(TooManyPhotosException e){
            fail();
        }

        rm = new RecordModel("test","test");
        try{
            for(int i=0;i<=11;i++){
                PhotoRecord ph = new PhotoRecord();
                rm.addPhoto(ph);
            }
        } catch(TooManyPhotosException e){
            assertEquals(e.getClass(),TooManyPhotosException.class);
        }


    }

    @Test
    public void testGetPhotos() {
        RecordModel rm = new RecordModel("test","test");
        PhotoRecord photo = new PhotoRecord();
        try{
            rm.addPhoto(photo);
            ArrayList<PhotoRecord> ph = rm.getPhotos();
            PhotoRecord p = ph.get(0);
            assertEquals(photo,p);
        } catch(TooManyPhotosException e){
            fail();
        }
    }

    @Test
    public void testRemovePhoto() {
        RecordModel rm = new RecordModel("test","test");
        PhotoRecord photo = new PhotoRecord();
        try{
            rm.addPhoto(photo);
            ArrayList<PhotoRecord> ph = rm.getPhotos();
            PhotoRecord p = ph.get(0);
            assertEquals(photo,p);

            rm.removePhoto(photo);
            ph = rm.getPhotos();
            assertFalse(ph.contains(photo));

        } catch(TooManyPhotosException e){
            fail();
        }
    }

    @Test
    public void testSetComment() {
        String comment = "comment";
        String newComment = "new comment";
        RecordModel rm = new RecordModel("","");
        rm.setComment(comment);
        assertEquals(comment,rm.getComment());
        rm.setComment(newComment);
        assertEquals(newComment,rm.getComment());
    }




    @Test
    public void testGetComment() {
        String comment = "comment";
        RecordModel rm = new RecordModel("","");
        rm.setComment(comment);
        String getComment = rm.getComment();
        assertEquals(comment,getComment);
    }
}