package com.classgo.keepnotes;

// this class is for to get the data from the firebase
public class user {
    private String time;
   private   String className;
   private String teacherName;
   private String roomNo;

   private  String key;



    public user()
    {

    }

    public user( String classname, String roomno, String teachername,String time) {

        this.className = classname;
        this.roomNo = roomno;
        this.teacherName = teachername;
        this.time = time;
    }

    public  String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getClassname() {
        return className;
    }

    public void setClassname(String classname) {
        this.className = classname;
    }

    public  String getTeachername() {
        return teacherName;
    }

    public void setTeachername(String teachername) {
        this.teacherName = teachername;
    }

    public  String getRoomno() {
        return roomNo;
    }

    public void setRoomno(String roomno) {
        this.roomNo = roomno;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

}
