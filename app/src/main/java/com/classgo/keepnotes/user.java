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

    public user(String time, String classname, String teachername, String roomno) {
        this.time = time;
        this.className = classname;
        this.teacherName = teachername;
        this.roomNo = roomno;
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
