package com.example.keepnotes;

public class myaddmondayapter  {
    String time,roomNo,teacherName,className;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getRoomNo() {
        return roomNo;
    }

    public void setRoomNo(String roomNo) {
        this.roomNo = roomNo;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public myaddmondayapter(String time, String roomNo, String teacherName, String className)
    {
        this.time=time;
        this.roomNo=roomNo;
        this.teacherName=teacherName;
        this.className=className;

    }


}
