package com.example.liqian.huarong.bean;

/**
 * Created by Administrator on 2019-07-10.
 */

public class PubReturn {

    public static PubReturn pubReturn = null;

    private boolean State = false;//
    private int ID = 0;//
    private boolean IsOk = false;//
    private String MachineCode = null;//
    private String LoginType = null;//
    private String RandomCode = null;//
    private String OldRandomCode = null;//
    private String Data = null;//
    private String Message = null;//
    private String Content = null;//
    private Object obj = null;//
    private Object file = null;

    public Object getFile() {
        return file;
    }

    public void setFile(Object file) {
        this.file = file;
    }

    public Boolean getState() {
        return State;
    }

    public void setState(boolean state) {
        State = state;
    }

    public int getID() {
        return ID;
    }

    public void setID(int id) {
        this.ID = id;
    }

    public boolean isOk() {
        return IsOk;
    }

    public void setOk(boolean ok) {
        IsOk = ok;
    }

    public String getMachineCode() {
        return MachineCode;
    }
    public void setMachineCode(String machineCode) {
        MachineCode = machineCode;
    }

    public String getLoginType() {
        return LoginType;
    }
    public void setLoginType(String loginType) {
        LoginType = loginType;
    }

    public String getRandomCode() {
        return RandomCode;
    }
    public void setRandomCode(String randomCode) {
        RandomCode = randomCode;
    }

    public String getOldRandomCode() {
        return OldRandomCode;
    }
    public void setOldRandomCode(String oldRandomCode) {
        OldRandomCode = oldRandomCode;
    }

    public String getData() {
        return Data;
    }
    public void setData(String data) {
        Data = data;
    }

    public String getContent() {
        return Content;
    }
    public void setContent(String content) {
        Content = content;
    }

    public String getMessage() {
        return Message;
    }
    public void setMessage(String message) {
        Message = message;
    }

    public Object getobj() {
        return obj;
    }
    public void setobj(Object _obj) {
        obj = _obj;
    }
    @Override
    public String toString() {
        return "{" +
                "State=" + State +
                ", Message=\"" + Message + "\"" +
                ", ID=" + ID +
                ", IsOk=" + IsOk +
                ", MachineCode=\"" + MachineCode + "\"" +
                ", LoginType=\"" + LoginType + "\"" +
                ", RandomCode=\"" + RandomCode + "\"" +
                ", OldRandomCode=\"" + OldRandomCode + "\"" +
                ", Data=\"" + Data + "\"" +
                '}';
    }
}