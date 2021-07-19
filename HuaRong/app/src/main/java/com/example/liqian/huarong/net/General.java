package com.example.liqian.huarong.net;


import com.example.liqian.huarong.base.BaseApp;
import com.example.liqian.huarong.bean.EntityWorkFlow;
import com.example.liqian.huarong.bean.PubReturn;

import java.lang.reflect.Field;

public class General<T> {

    private StringBuffer sb;

    public String bllInJson(T ent, String jsonSet) {
        Field[] fields = ent.getClass().getDeclaredFields();
        for (int i = 0; i < fields.length; i++) {
            fields[i].setAccessible(true);
            String fildName = "\"" + fields[i].getName().toLowerCase() + "\"";
            if (jsonSet.indexOf(fildName) != -1) {
                String fildValue = "";
                try {
                    fildValue = fields[i].get(ent).toString();
                } catch (Exception ex) {
                }
                jsonSet = jsonSet.replace(fildName
                        , "\"" + Common.defEncode(fildValue) + "\"");

            }
        }

        return jsonSet;
    }

    public String bllMonJson(T ent, String jsonSet) {

        Field[] fields = ent.getClass().getDeclaredFields();

        for (int i = 0; i < fields.length; i++) {

            fields[i].setAccessible(true);

            String vfildName = "v_" + fields[i].getName().toLowerCase() + "_fld";
            String pfildName = "p_" + fields[i].getName().toLowerCase() + "_fld";

            if (jsonSet.indexOf(vfildName) != -1) {

                String fildValue = "";
                try {
                    fildValue = fields[i].get(ent).toString();
                } catch (Exception ex) {
                }

                String fildType = fields[i].getGenericType().toString().toLowerCase();

                if (fildType.indexOf("double") != -1) {
                    jsonSet = jsonSet.replace(vfildName, "double&" + Common.defEncode(fildValue));
                } else if (fildType.indexOf("float") != -1) {
                    jsonSet = jsonSet.replace(vfildName, "float&" + Common.defEncode(fildValue));
                } else if (fildType.indexOf("int") != -1) {
                    jsonSet = jsonSet.replace(vfildName, "int&" + Common.defEncode(fildValue));
                } else if (fildType.indexOf("long") != -1) {
                    jsonSet = jsonSet.replace(vfildName, "int&" + Common.defEncode(fildValue));
                } else if (fildType.indexOf("boolean") != -1) {
                    jsonSet = jsonSet.replace(vfildName, "bool&" + Common.defEncode(fildValue));
                } else {
                    jsonSet = jsonSet.replace(vfildName, Common.defEncode(fildValue));
                }


            } else {
                if (jsonSet.indexOf(pfildName) != -1) {

                    String fildValue = "";
                    try {
                        fildValue = fields[i].get(ent).toString();
                    } catch (Exception ex) {
                    }

                    String fildType = fields[i].getGenericType().toString().toLowerCase();
                    if (fildType.indexOf("string") != -1) {
                        jsonSet = jsonSet.replace(pfildName, "string|" + Common.defEncode(fildValue));
                    } else if (fildType.indexOf("double") != -1) {
                        jsonSet = jsonSet.replace(pfildName, "double|" + Common.defEncode(fildValue));
                    } else if (fildType.indexOf("float") != -1) {
                        jsonSet = jsonSet.replace(pfildName, "float|" + Common.defEncode(fildValue));
                    } else if (fildType.indexOf("int") != -1) {
                        jsonSet = jsonSet.replace(pfildName, "int|" + Common.defEncode(fildValue));
                    } else if (fildType.indexOf("long") != -1) {
                        jsonSet = jsonSet.replace(pfildName, "int|" + Common.defEncode(fildValue));
                    } else if (fildType.indexOf("boolean") != -1) {
                        jsonSet = jsonSet.replace(pfildName, "bool|" + Common.defEncode(fildValue));
                    } else {
                        jsonSet = jsonSet.replace(pfildName, "string|" + Common.defEncode(fildValue));

                    }
                }
            }
        }

        //  jsonSet=jsonSet.replace("\"MachineCode\"","\"MachineCode\":\""+Common.MachineCode+"\"");
        //    jsonSet=jsonSet.replace("\"RandomCode\"","\"RandomCode\":\""+Common.RandomCode+"\"");
        // jsonSet=jsonSet.replace("\"LoginType\"","\"LoginType\":\""+Common.LoginType+"\"");

        return jsonSet;
    }

    public String bllJson(T ent, String path) {
        sb = new StringBuffer();
        String Json = "{\"path\":\"" + path + "\"" +
                ",\"MachineCode\":\"" + Common.MachineCode + "\",\"RandomCode\":\"" + Common.RandomCode + "\"" +
                ",\"LoginType\":\"" + Common.LoginType + "\"}";

        Json = Common.defEncode(Json);

        Common.httpPost1(Json, 12, BaseApp.getInstance(), new MylWithListener() {

            @Override
            public String ServerDataDealWith(PubReturn pubReturn) {

                EntityWorkFlow ent = new EntityWorkFlow();
                ent.setpapt_code("001");
                General<EntityWorkFlow> general = new General<>();

                String inJson = general.bllInJson(ent, pubReturn.getData());
                sb.append(inJson);

                return sb.toString();
            }
        });
        return sb.toString();
    }

}
