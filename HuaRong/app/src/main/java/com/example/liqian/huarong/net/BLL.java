package com.example.liqian.huarong.net;


import com.example.liqian.huarong.bean.EntityBase;
import com.example.liqian.huarong.bean.EntityFile;
import com.example.liqian.huarong.bean.EntityInformation;
import com.example.liqian.huarong.bean.EntityOA;
import com.example.liqian.huarong.bean.EntityPersonal;
import com.example.liqian.huarong.bean.EntityTable;
import com.example.liqian.huarong.bean.EntityWorkFlow;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Administrator on 2019-07-14.
 */

public class BLL {

    public static String BLLJsonByStream(String str) {
        String Json = "{\"BLLJsonPath\":\"" + str + "\""
                + ",\"MachineCode\":\"" + Common.MachineCode
                + "\",\"RandomCode\":\"" + Common.RandomCode
                + "\",\"LoginType\":\"" + Common.LoginType + "\"}";
        Json = Common.defEncode(Json);
        return Json;
    }


    public static String TaskPageSelect(int page_size, int page_current, String state_code) {

        String Json = "{\"procName\":\"Proc_Task_PageSelect\"" +
                ",\"outStr\":\"r_result,r_error,r_cursor,r_total_capacity,r_page_capacity,r_total_pages,r_page_current\"" +
                ",\"inJson\":{\"v_order_list\":\"\",\"v_page_size\":" + Common.defEncode(Integer.toString(page_size))
                + ",\"v_page_current\":" + Common.defEncode(Integer.toString(page_current))
                + ",\"v_corp_tn\":\"" + Common.defEncode(Common.LoginUser.getcorp_tn())
                + "\",\"v_wf_code\":\"\",\"v_task_code\":\"\",\"v_task_name\":\"\"" +
                ",\"v_node_code\":\"\",\"v_node_name\":\"\",\"v_init_pers_ucode\":\"\"" +
                ",\"v_init_pers_uname\":\"\",\"v_init_pers_dcode\":\"\"" +
                ",\"v_init_pers_dname\":\"\",\"v_init_pcode\":\"\"" +
                ",\"v_init_pun\":\"\",\"v_init_pname\":\"\",\"v_papt_pcode\":\"" + Common.defEncode(Common.LoginUser.getpers_code())
                + "\",\"v_papt_pun\":\"\",\"v_papt_pname\":\"" + Common.defEncode(Common.LoginUser.getpers_name())
                + "\",\"v_state_code\":\"" + Common.defEncode(state_code) + "\",\"v_state_code_no\":\"\",\"v_tb_code\":\"\"" +
                ",\"v_type_code\":\"\",\"v_kind_code\":\"\",\"v_task_key\":\"\",\"v_task_arrvdate_1\":\"\"" +
                ",\"v_task_arrvdate_2\":\"\",\"v_task_imp_date_1\":\"\",\"v_task_imp_date_2\":\"\",\"v_if_no_imp|bool\":RmFsc2U=}" +
                ",\"MachineCode\":\"" + Common.MachineCode + "\",\"RandomCode\":\"" + Common.RandomCode
                + "\",\"LoginType\":\"" + Common.LoginType + "\"}";
        Json = Common.defEncode(Json);
        return Json;
    }

    public static String TaskPageSelect(int page_size, int page_current, String state_code, String wf_code) {

        String Json = "{\"procName\":\"Proc_Task_PageSelect\"" +
                ",\"outStr\":\"r_result,r_error,r_cursor,r_total_capacity,r_page_capacity,r_total_pages,r_page_current\"" +
                ",\"inJson\":{\"v_order_list\":\"task_arrvdate desc\",\"v_page_size\":" + Common.defEncode(Integer.toString(page_size))
                + ",\"v_page_current\":" + Common.defEncode(Integer.toString(page_current))
                + ",\"v_corp_tn\":\"" + Common.defEncode(Common.LoginUser.getcorp_tn())
                + "\",\"v_wf_code\":\"" + Common.defEncode(wf_code) + "\",\"v_task_code\":\"\",\"v_task_name\":\"\"" +
                ",\"v_node_code\":\"\",\"v_node_name\":\"\",\"v_init_pers_ucode\":\"\"" +
                ",\"v_init_pers_uname\":\"\",\"v_init_pers_dcode\":\"\"" +
                ",\"v_init_pers_dname\":\"\",\"v_init_pcode\":\"\"" +
                ",\"v_init_pun\":\"\",\"v_init_pname\":\"\",\"v_papt_pcode\":\"" + Common.defEncode(Common.LoginUser.getpers_code())
                + "\",\"v_papt_pun\":\"\",\"v_papt_pname\":\"" + Common.defEncode(Common.LoginUser.getpers_name())
                + "\",\"v_state_code\":\"" + Common.defEncode(state_code) + "\",\"v_state_code_no\":\"\",\"v_tb_code\":\"\"" +
                ",\"v_type_code\":\"\",\"v_kind_code\":\"\",\"v_task_key\":\"\",\"v_task_arrvdate_1\":\"\"" +
                ",\"v_task_arrvdate_2\":\"\",\"v_task_imp_date_1\":\"\",\"v_task_imp_date_2\":\"\",\"v_if_no_imp|bool\":RmFsc2U=}" +
                ",\"MachineCode\":\"" + Common.MachineCode + "\",\"RandomCode\":\"" + Common.RandomCode
                + "\",\"LoginType\":\"" + Common.LoginType + "\"}";
        Json = Common.defEncode(Json);
        return Json;
    }

    public static String OASelect(String oa_code) {

        String Json = "{\"procName\":\"Proc_OAMain_Select\",\"outStr\":\"r_result,r_error,r_cursor\"" +
                ",\"inJson\":{,\"v_order_list\":\"\",\"v_corp_tn\":\""
                + Common.defEncode(Common.LoginUser.getcorp_tn()) + "\"" +
                ",\"v_tb_code\":\"\",\"v_type_code\":\"\",\"v_kind_code\":\"\",\"v_oa_kind\":\"\"" +
                ",\"v_make_ucode\":\"\",\"v_make_uname\":\"\",\"v_make_dcode\":\"\",\"v_make_dname\":\"\"" +
                ",\"v_make_pcode\":\"\",\"v_make_pun\":\"\",\"v_make_pname\":\"\",\"v_make_date_1|date\":\"\"" +
                ",\"v_make_date_2|date\":\"\",\"v_task_name\":\"\",\"v_check_pcodes\":\"\"" +
                ",\"v_check_log\":\"\",\"v_is_oa_precise|bool\":VHJ1ZQ==,\"v_oa_code\":\"" +
                Common.defEncode(oa_code) + "\"" +
                ",\"v_oa_title\":\"\",\"v_oa_content\":\"\",\"v_oa_upcode\":\"\",\"v_state_code\":\"\"" +
                ",\"v_state_code_no\":\"\",\"v_oa_nvar100_1\":\"\",\"v_oa_nvar100_2\":\"\"" +
                ",\"v_oa_nvar100_3\":\"\",\"v_oa_nvar100_4\":\"\",\"v_oa_nvar100_5\":\"\",\"v_oa_nvar100_6\":\"\"" +
                ",\"v_oa_nvar100_7\":\"\",\"v_oa_nvar100_8\":\"\",\"v_oa_nvar100_9\":\"\",\"v_oa_nvar100_10\":\"\"" +
                ",\"v_oa_nvar100_11\":\"\",\"v_oa_nvar100_12\":\"\",\"v_oa_nvar100_13\":\"\",\"v_oa_nvar100_14\":\"\"" +
                ",\"v_oa_nvar100_15\":\"\",\"v_oa_nvar100_16\":\"\",\"v_oa_nvar100_17\":\"\",\"v_oa_nvar100_18\":\"\"" +
                ",\"v_oa_nvar100_19\":\"\",\"v_oa_nvar100_20\":\"\",\"v_oa_yxm\":\"\",\"v_oa_nvarmax_1\":\"\"" +
                ",\"v_oa_nvarmax_2\":\"\",\"v_oa_nvarmax_3\":\"\",\"v_oa_nvarmax_4\":\"\",\"v_oa_nvarmax_5\":\"\"" +
                ",\"v_oa_date_1_1|date\":\"\",\"v_oa_date_1_2|date\":\"\"}" +
                ",\"MachineCode\":\"" + Common.MachineCode
                + "\",\"RandomCode\":\"" + Common.RandomCode
                + "\",\"LoginType\":\"" + Common.LoginType + "\"}";

        Json = Common.defEncode(Json);
        return Json;
    }

    public static String TaskDispose(EntityWorkFlow ent) {

        String Json = "{\"procName\":\"Proc_Task_Dispose\",\"outStr\":\"r_result,r_error,r_content\"" +
                ",\"inJson\":{\"v_corp_tn\":\"" + Common.defEncode(Common.LoginUser.getcorp_tn())
                + "\",\"v_wf_code\":\"" + Common.defEncode(ent.getwf_code()) + "\"" +
                ",\"v_task_code\":\"" + Common.defEncode(ent.gettask_code()) + "\"" +
                ",\"v_task_id\":" + Common.defEncode(Integer.toString(ent.gettask_id()))
                + ",\"v_pers_code\":\"" + Common.defEncode(ent.getpers_code()) + "\",\"v_stage_code\":\"\"" +
                ",\"v_state_code\":\"" + Common.defEncode(ent.getstate_code())
                + "\",\"v_task_imp_date|date\":\"" + Common.defEncode(ent.gettask_imp_date()) + "\"" +
                ",\"v_task_imp_result\":\"" + Common.defEncode(ent.gettask_imp_result()) + "\"" +
                ",\"v_task_imp_content\":\"" + Common.defEncode(ent.gettask_imp_content()) + "\"" +
                ",\"v_front_node_path\":\"" + Common.defEncode(ent.getfront_node_path())
                + "\",\"v_accepter_pcode\":\"\",\"v_accepter_pun\":\"\",\"v_accepter_pname\":\"\"" +
                ",\"v_wf_name\":\"" + Common.defEncode(ent.getwf_name()) + "\"" +
                ",\"v_task_name\":\"" + Common.defEncode(ent.gettask_name()) + "\"" +
                ",\"v_tb_code\":\"" + Common.defEncode(ent.gettb_code())
                + "\",\"v_type_code\":\"" + Common.defEncode(ent.gettype_code())
                + "\",\"v_kind_code\":\"" + Common.defEncode(ent.getkind_code())
                + "\",\"v_task_key\":\"" + Common.defEncode(ent.gettask_key()) + "\""
                + "\",\"v_is_end\":" + Common.defEncode(Integer.toString(ent.getis_end()))
                +
                ",\"v_node_code\":\"\",\"v_wfb_code\":\"\",\"v_wfb_ncode\":\"\",\"v_taskb_code\":\"\",\"v_parent_wfb_code\":\"\"" +
                ",\"v_parent_wfb_ncode\":\"\",\"v_parent_taskb_code\":\"\",\"v_task_imp_sign\":\""
                + Common.defEncode(ent.gettask_imp_sign()) + "\"}" +
                ",\"MachineCode\":\"" + Common.MachineCode
                + "\",\"RandomCode\":\"" + Common.RandomCode + "\",\"LoginType\":\"" + Common.LoginType + "\"}";

        Json = Common.defEncode(Json);
        return Json;

    }

    public static String TaskSelect(EntityWorkFlow ent) {

        String Json = "{\"procName\":\"Proc_Task_Select\",\"outStr\":\"r_result,r_error,r_cursor\"" +
                ",\"inJson\":{\"v_order_list\":\"" + Common.defEncode(ent.getorder_list())
                + "\",\"v_corp_tn\":\"" + Common.defEncode(Common.LoginUser.getcorp_tn()) + "\",\"v_wf_code\":\"\"" +
                ",\"v_task_code\":\"\",\"v_task_name\":\"\",\"v_node_code\":\"\",\"v_node_name\":\"\"" +
                ",\"v_init_pers_ucode\":\"\",\"v_init_pers_uname\":\"\",\"v_init_pers_dcode\":\"\"" +
                ",\"v_init_pers_dname\":\"\",\"v_init_pcode\":\"\",\"v_init_pun\":\"\",\"v_init_pname\":\"\"" +
                ",\"v_papt_pcode\":\"\",\"v_papt_pun\":\"\",\"v_papt_pname\":\"\",\"v_state_code\":\""
                + Common.defEncode(ent.getstate_code()) + "\"" +
                ",\"v_state_code_no\":\"\",\"v_tb_code\":\"" + Common.defEncode(ent.gettb_code())
                + "\",\"v_type_code\":\"" + Common.defEncode(ent.gettype_code()) + "\",\"v_kind_code\":\""
                + Common.defEncode(ent.getkind_code()) + "\"" +
                ",\"v_task_key\":\"" + Common.defEncode(ent.gettask_key())
                + "\",\"v_task_arrvdate_1\":\"\",\"v_task_arrvdate_2\":\"\"" +
                ",\"v_task_imp_date_1\":\"\",\"v_task_imp_date_2\":\"\",\"v_if_no_imp|bool\":"
                + Common.defEncode(Boolean.toString(ent.getif_no_imp())) + "}" +
                ",\"MachineCode\":\"" + Common.MachineCode + "\",\"RandomCode\":\"" + Common.RandomCode + "\"" +
                ",\"LoginType\":\"" + Common.LoginType + "\"}";

        Json = Common.defEncode(Json);
        return Json;
    }

    public static String FileSelect(EntityFile ent) {

        String Json = "{\"tableName\":\"t_file\",\"queryJson\":{\"and\":[" +
                "{\"eq\":[{\"corp_tn\"},{\"" + Common.LoginUser.getcorp_tn() + "\"}]}" +
                ",{\"eq\":[{\"tb_code\"},{\"" + ent.gettb_code() + "\"}]}" +
                ",{\"eq\":[{\"type_code\"},{\"" + ent.gettype_code() + "\"}]}" +
                ",{\"eq\":[{\"kind_code\"},{\"" + ent.getkind_code() + "\"}]}" +
                ",{\"eq\":[{\"file_becode\"},{\"" + ent.getfile_becode() + "\"}]}" +
                ",{\"eq\":[{\"file_check_1\"},{" + ent.getfile_check_1() + "}]}" +
                "]}" +
                ",\"sortJson\":[{corp_tn,1},{file_uptime_stamp,-1},{file_name,1}]" +
                ",\"showJson\":\"_id,corp_tn,corp_name,file_id,tb_code,type_code" +
                ",kind_code,file_code,file_md5,file_name,file_link,file_save,file_server_map" +
                ",file_path,file_orgpath,file_oldpath,file_uptime,unit_code,unit_name" +
                ",dept_code,dept_name,pers_un,pers_code,pers_name,file_becode,file_size" +
                ",file_extend,file_origname,file_version,file_openstate,file_deci2_1" +
                ",file_deci2_2,file_deci2_3,file_deci2_4,file_deci2_5,file_check_1,file_check_2" +
                ",file_check_3,file_check_4,file_check_5,flow_path,flow_trail\"" +
                ",\"pageSize\":0" +
                ",\"MachineCode\":\"" + Common.MachineCode + "\"" +
                ",\"RandomCode\":\"" + Common.RandomCode + "\"" +
                ",\"LoginType\":\"" + Common.LoginType + "\"}";

        Json = Common.defEncode(Json);
        return Json;
    }

    public static String FileSelectList(EntityFile ent) {

        String Json = "{\"tableName\":\"t_file\",\"queryJson\":{\"and\":[" +
                "{\"eq\":[{\"corp_tn\"},{\"" + Common.LoginUser.getcorp_tn() + "\"}]}" +
                ",{\"eq\":[{\"tb_code\"},{\"" + ent.gettb_code() + "\"}]}" +
                ",{\"eq\":[{\"file_becode\"},{\"" + ent.getfile_becode() + "\"}]}]}" +
                ",\"sortJson\":[{corp_tn,1},{file_uptime_stamp,-1},{file_name,1}]" +
                ",\"showJson\":\"_id,corp_tn,corp_name,file_id,tb_code,type_code" +
                ",kind_code,file_code,file_md5,file_name,file_link,file_save,file_server_map" +
                ",file_path,file_orgpath,file_oldpath,file_uptime,unit_code,unit_name" +
                ",dept_code,dept_name,pers_un,pers_code,pers_name,file_becode,file_size" +
                ",file_extend,file_origname,file_version,file_openstate,file_deci2_1" +
                ",file_deci2_2,file_deci2_3,file_deci2_4,file_deci2_5,file_check_1,file_check_2" +
                ",file_check_3,file_check_4,file_check_5,flow_path,flow_trail\"" +
                ",\"pageSize\":0" +
                ",\"MachineCode\":\"" + Common.MachineCode + "\"" +
                ",\"RandomCode\":\"" + Common.RandomCode + "\"" +
                ",\"LoginType\":\"" + Common.LoginType + "\"}";

        Json = Common.defEncode(Json);
        return Json;
    }

    public static String FileSelectOpen(EntityFile ent) {

        String Json = "{\"tableName\":\"t_file\",\"queryJson\":{\"and\":[" +
                "{\"eq\":[{\"corp_tn\"},{\"" + Common.LoginUser.getcorp_tn() + "\"}]}" +
                ",{\"eq\":[{\"tb_code\"},{\"" + ent.gettb_code() + "\"}]}" +
                ",{\"eq\":[{\"type_code\"},{\"" + ent.gettype_code() + "\"}]}" +
                ",{\"eq\":[{\"kind_code\"},{\"" + ent.getkind_code() + "\"}]}" +
                ",{\"eq\":[{\"file_becode\"},{\"" + ent.getfile_becode() + "\"}]}" +
                ",{\"eq\":[{\"file_openstate\"},{True}]}]}" +
                ",\"sortJson\":[{corp_tn,1},{file_uptime_stamp,-1},{file_name,1}]" +
                ",\"showJson\":\"_id,corp_tn,corp_name,file_id,tb_code,type_code" +
                ",kind_code,file_code,file_md5,file_name,file_link,file_save,file_server_map" +
                ",file_path,file_orgpath,file_oldpath,file_uptime,file_uptime_stamp,unit_code,unit_name" +
                ",dept_code,dept_name,pers_un,pers_code,pers_name,file_becode,file_size" +
                ",file_extend,file_origname,file_version,file_openstate,file_deci2_1" +
                ",file_deci2_2,file_deci2_3,file_deci2_4,file_deci2_5,file_check_1,file_check_2" +
                ",file_check_3,file_check_4,file_check_5,flow_path,flow_trail\"" +
                ",\"pageSize\":0" +
                ",\"MachineCode\":\"" + Common.MachineCode + "\"" +
                ",\"RandomCode\":\"" + Common.RandomCode + "\"" +
                ",\"LoginType\":\"" + Common.LoginType + "\"}";

        Json = Common.defEncode(Json);
        return Json;
    }

    public static String FileSelectCheck(EntityFile ent) {

        String Json = "{\"tableName\":\"t_file\",\"queryJson\":{\"and\":[" +
                "{\"eq\":[{\"corp_tn\"},{\"" + Common.LoginUser.getcorp_tn() + "\"}]}" +
                ",{\"eq\":[{\"tb_code\"},{\"" + ent.gettb_code() + "\"}]}" +
                ",{\"eq\":[{\"type_code\"},{\"" + ent.gettype_code() + "\"}]}" +
                ",{\"eq\":[{\"kind_code\"},{\"" + ent.getkind_code() + "\"}]}" +
                ",{\"eq\":[{\"file_becode\"},{\"" + ent.getfile_becode() + "\"}]}" +
                ",{\"eq\":[{\"file_check_1\"},{True}]}]}" +
                ",\"sortJson\":[{corp_tn,1},{file_uptime_stamp,-1},{file_name,1}]" +
                ",\"showJson\":\"_id,corp_tn,corp_name,file_id,tb_code,type_code" +
                ",kind_code,file_code,file_md5,file_name,file_link,file_save,file_server_map" +
                ",file_path,file_orgpath,file_oldpath,file_uptime,unit_code,unit_name" +
                ",dept_code,dept_name,pers_un,pers_code,pers_name,file_becode,file_size" +
                ",file_extend,file_origname,file_version,file_openstate,file_deci2_1" +
                ",file_deci2_2,file_deci2_3,file_deci2_4,file_deci2_5,file_check_1,file_check_2" +
                ",file_check_3,file_check_4,file_check_5,flow_path,flow_trail\"" +
                ",\"pageSize\":0" +
                ",\"MachineCode\":\"" + Common.MachineCode + "\"" +
                ",\"RandomCode\":\"" + Common.RandomCode + "\"" +
                ",\"LoginType\":\"" + Common.LoginType + "\"}";

        Json = Common.defEncode(Json);
        return Json;
    }

    public static String UploadFile(EntityFile ent) {

        String Json = "{ \"MachineCode\": \"" + Common.MachineCode + "\"," +
                "\"RandomCode\": \"" + Common.RandomCode + "\"," +
                "\"LoginType\": \"" + Common.LoginType + "\"," +
                "\"file_code\": \"" + ent.getfile_code() + "\"," +
                "\"file_extend\": \"" + ent.getfile_extend() + "\"," +
                "\"file_body\": \"" + ent.getfile_body() + "\"," +
                "\"file_end_flag\": " + ent.getfile_end_flag() + "," +
                "\"file_server_map\": \"" + ent.getfile_server_map() + "\"," +
                "\"file_belong\": \"" + ent.getfile_belong() + "\"}";

        Json = Common.defEncode(Json);
        return Json;
    }

    public static String FileAdd(EntityFile ent) {

        String Json = "{\"tableName\":\"t_file\",\"dataJson\":{\"corp_tn\":\"string|" + Common.defEncode(ent.getcorp_tn()) + "\"" +
                ",\"corp_name\":\"string|\",\"tb_code\":\"string|" + Common.defEncode(ent.gettb_code())
                + "\",\"type_code\":\"string|" + Common.defEncode(ent.gettype_code()) + "\"" +
                ",\"kind_code\":\"string|" + Common.defEncode(ent.getkind_code())
                + "\",\"file_id\":\"string|" + Common.defEncode(ent.getfile_id()) + "\"" +
                ",\"file_code\":\"string|" + Common.defEncode(ent.getfile_code()) + "\"" +
                ",\"file_name\":\"string|" + Common.defEncode(ent.getfile_name()) + "\"" +
                ",\"file_save\":\"string|" + Common.defEncode(ent.getfile_save())
                + "\",\"file_server_map\":\"" + Common.defEncode(ent.getfile_server_map()) + "\"" +
                ",\"file_link\":\"string|" + Common.defEncode(ent.getfile_link()) + "\"" +
                ",\"file_path\":\"string|" + Common.defEncode(ent.getfile_path()) + "\"" +
                ",\"file_orgpath\":\"string|" + Common.defEncode(ent.getfile_orgpath()) + "\"" +
                ",\"file_uptime\":\"string|" + Common.defEncode(ent.getfile_uptime()) + "\"" +
                ",\"file_uptime_stamp\":\"int|" + Common.defEncode(String.valueOf(ent.getfile_uptime_stamp())) + "\"" +
                ",\"unit_code\":\"string|" + Common.defEncode(ent.getunit_code())
                + "\",\"unit_name\":\"string|" + Common.defEncode(ent.getunit_name()) + "\"" +
                ",\"dept_code\":\"string|" + Common.defEncode(ent.getdept_code())
                + "\",\"dept_name\":\"string|" + Common.defEncode(ent.getdept_name()) + "\"" +
                ",\"pers_un\":\"string|" + Common.defEncode(ent.getpers_un())
                + "\",\"pers_code\":\"string|" + Common.defEncode(ent.getpers_code()) + "\"" +
                ",\"pers_name\":\"string|" + Common.defEncode(ent.getpers_name()) + "\"" +
                ",\"file_becode\":\"string|" + Common.defEncode(ent.getfile_becode())
                + "\",\"file_size\":\"int|" + Common.defEncode(Integer.toString(ent.getfile_size())) + "\"" +
                ",\"file_extend\":\"string|" + Common.defEncode(ent.getfile_extend())
                + "\",\"file_origname\":\"string|" + Common.defEncode(ent.getfile_origname()) + "\"" +
                ",\"file_openstate\":\"bool|" + Common.defEncode(Boolean.toString(ent.getfile_openstate())) + "\"}" +
                ",\"MachineCode\":\"" + Common.MachineCode + "\"" +
                ",\"RandomCode\":\"" + Common.RandomCode + "\"" +
                ",\"LoginType\":\"" + Common.LoginType + "\"}";

        Json = Common.defEncode(Json);
        return Json;
    }

    public static String FileDelete(EntityFile ent) {

        String Json = "{\"tableName\":\"t_file\",\"dataJson\":{\"_id\":\"string|" + Common.defEncode(ent.get_id()) + "\"}" +
                ",\"MachineCode\":\"" + Common.MachineCode + "\"" +
                ",\"RandomCode\":\"" + Common.RandomCode + "\"" +
                ",\"LoginType\":\"" + Common.LoginType + "\"}";

        Json = Common.defEncode(Json);
        return Json;
    }

    public static String Task_UpdateState(EntityWorkFlow ent) {

        String Json = "{\"procName\":\"Proc_Task_UpdateState\",\"outStr\":\"r_result,r_error\"" +
                ",\"inJson\":{\"v_corp_tn\":\"" + Common.defEncode(Common.LoginUser.getcorp_tn())
                + "\",\"v_task_id\":" + Common.defEncode(Integer.toString(ent.gettask_id()))
                + ",\"v_state_code\":\"" + Common.defEncode(ent.getstate_code()) + "\"}"
                + ",\"MachineCode\":\"" + Common.MachineCode
                + "\",\"RandomCode\":\"" + Common.RandomCode + "\",\"LoginType\":\"" + Common.LoginType + "\"}";

        Json = Common.defEncode(Json);
        return Json;

    }

    public static String File_UpdateOpenstateByBeCode(EntityFile ent) {

        String Json = "{\"tableName\":\"t_file\",\"queryJson\":{\"and\":[{\"eq\":[{\"corp_tn\"},{\"" + ent.getcorp_tn() + "\"}]}" +
                ",{\"eq\":[{\"file_becode\"},{\"" + ent.getfile_becode() + "\"}]}]}" +
                ",\"dataJson\":{\"file_openstate\":\"bool|" + Common.defEncode(ent.getfile_openstate().toString()) + "\"}" +
                ",\"MachineCode\":\"" + Common.MachineCode + "\"" +
                ",\"RandomCode\":\"" + Common.RandomCode + "\",\"LoginType\":\"" + Common.LoginType + "\"}";

        Json = Common.defEncode(Json);
        return Json;
    }


    public static String FileSelectForUpgrade(EntityFile ent) {

        String Json = "{\"tableName\":\"t_file\",\"queryJson\":{\"and\":[" +
                "{\"eq\":[{\"corp_tn\"},{\"" + Common.LoginUser.getcorp_tn() + "\"}]}" +
                ",{\"eq\":[{\"tb_code\"},{\"" + ent.gettb_code() + "\"}]}" +
                ",{\"eq\":[{\"type_code\"},{\"" + ent.gettype_code() + "\"}]}" +
                ",{\"eq\":[{\"kind_code\"},{\"" + ent.getkind_code() + "\"}]}]}" +
                ",\"sortJson\":[{corp_tn,1},{file_uptime_stamp,-1},{file_name,1}]" +
                ",\"showJson\":\"_id,corp_tn,file_link,file_version\"" +
                ",\"pageSize\":0" +
                ",\"MachineCode\":\"" + Common.MachineCode + "\"" +
                ",\"RandomCode\":\"" + Common.RandomCode + "\"" +
                ",\"LoginType\":\"" + Common.LoginType + "\"}";

        Json = Common.defEncode(Json);
        return Json;
    }


    public static String PersonPageSelect(int page_size, int page_current) {
        String json = "{\"procName\":\"Proc_Personal_PageSelect\"" +
                ",\"outStr\":\"r_result,r_error,r_cursor,r_total_capacity,r_page_capacity,r_total_pages,r_page_current\"" +
                ",\"inJson\":{\"v_order_list\":\"\"" +
                ",\"v_page_size\":" + Common.defEncode(Integer.toString(page_size)) +
                ",\"v_page_current\":" + Common.defEncode(Integer.toString(page_current)) +
                ",\"v_corp_tn\":\"" + Common.defEncode(Common.LoginUser.getcorp_tn()) +
                "\",\"v_pers_un\":\"\",\"v_pers_name\":\"\",\"v_pers_code\":\"\",\"v_pers_number\":\"\",\"v_pers_sex\":\"\",\"v_pers_idnumber\":\"\",\"v_pers_birth_1\":\"\",\"v_pers_birth_2\":\"\",\"v_join_time_1\":\"\",\"v_join_time_2\":\"\",\"v_pers_degree\":\"\",\"v_pers_address\":\"\",\"v_pers_othercontact\":\"\"" +
                ",\"v_tb_code\":\"NA==\"" +
                ",\"v_type_code\":\"MDAx\"" +
                ",\"v_kind_code\":\"MDAx\"" +
                ",\"v_user_name\":\"\",\"v_unit_code\":\"\",\"v_dept_code\":\"\",\"v_pos_code\":\"\",\"v_role_code\":\"\",\"v_corp_code\":\"\",\"v_pers_openstate|bool\":RmFsc2U=,\"v_if_pers_openstate|bool\":RmFsc2U=,\"v_pers_state\":\"\",\"v_state_code\":\"\",\"v_pers_major\":\"\",\"v_pers_old_1\":MA==,\"v_pers_old_2\":MA==" +
                ",\"v_pers_deci2_1_1\":MA==,\"v_pers_deci2_1_2\":MA==}" +
                ",\"MachineCode\":\"" + Common.MachineCode + "\",\"RandomCode\":\"" + Common.RandomCode
                + "\",\"LoginType\":\"" + Common.LoginType + "\"}";

        json = Common.defEncode(json);
        return json;
    }


    public static String Code_Create(EntityTable ent) {

        String json = "{\"procName\":\"Proc_Code_Create\"" +
                ",\"outStr\":\"r_result,r_error,r_content\"" +
                ",\"inJson\":{\"v_corp_tn\":\"" + Common.defEncode(Common.LoginUser.getcorp_tn())
                + "\",\"v_table_name\":\"" + Common.defEncode(ent.gettable_name()) + "\"" +
                ",\"v_field_name\":\"" + Common.defEncode(ent.getfield_name()) + "\"" +
                ",\"v_field_str\":\"" + Common.defEncode(ent.getfield_str()) + "\"" +
                ",\"v_field_num\":NA==" +
                ",\"v_tb_code\":\"\"" +
                ",\"v_type_code\":\"\",\"v_kind_code\":\"\"}" +
                ",\"MachineCode\":\"" + Common.MachineCode + "\",\"RandomCode\":\"" + Common.RandomCode
                + "\",\"LoginType\":\"" + Common.LoginType + "\"}";
        return json;
    }


    public static String OAMainUpdate(EntityOA ent) {
        String Json = "{\"procName\":\"Proc_OAMain_Update\",\"outStr\":\"r_result,r_error\"" +
                ",\"inJson\":{\"v_corp_tn\":\"" + Common.defEncode(Common.LoginUser.getcorp_tn()) + "\"" +
                ",\"v_oa_code\":\"" + Common.defEncode(ent.getoa_code())
                + "\",\"v_oa_title\":\"" + Common.defEncode(ent.getoa_title())
                + "\",\"v_oa_content\":\"" + Common.defEncode(ent.getoa_content())
                + "\",\"v_tb_code\":\"" + Common.defEncode(ent.gettb_code())
                + "\",\"v_type_code\":\"" + Common.defEncode(ent.gettype_code())
                + "\",\"v_type_name\":\"" + Common.defEncode(ent.gettype_name())
                + "\",\"v_kind_code\":\"" + Common.defEncode(ent.getkind_code())
                + "\",\"v_kind_name\":\"" + Common.defEncode(ent.getkind_name())
                + "\",\"v_oa_kind\":\"" + Common.defEncode(ent.getoa_kind())
                + "\",\"v_oa_upcode\":\"" + Common.defEncode(ent.getoa_upcode())
                + "\",\"v_make_ucode\":\"" + Common.defEncode(ent.getmake_ucode())
                + "\",\"v_make_uname\":\"" + Common.defEncode(ent.getmake_uname())
                + "\",\"v_make_dcode\":\"" + Common.defEncode(ent.getmake_dcode())
                + "\",\"v_make_dname\":\"" + Common.defEncode(ent.getmake_dname())
                + "\",\"v_make_pcode\":\"" + Common.defEncode(ent.getmake_pcode())
                + "\",\"v_make_pun\":\"" + Common.defEncode(ent.getmake_pun())
                + "\",\"v_make_pname\":\"" + Common.defEncode(ent.getmake_pname())
                + "\",\"v_make_date|date\":\"" + Common.defEncode(ent.getmake_date())
                + "\",\"v_oa_nvar100_1\":\"" + Common.defEncode(ent.getoa_nvar100_1())
                + "\",\"v_oa_nvar100_2\":\"" + Common.defEncode(ent.getoa_nvar100_2())
                + "\",\"v_oa_nvar100_3\":\"" + Common.defEncode(ent.getoa_nvar100_3())
                + "\",\"v_oa_nvar100_4\":\"" + Common.defEncode(ent.getoa_nvar100_4())
                + "\",\"v_oa_nvar100_5\":\"" + Common.defEncode(ent.getoa_nvar100_5())
                + "\",\"v_oa_nvar100_6\":\"" + Common.defEncode(ent.getoa_nvar100_6())
                + "\",\"v_oa_nvar100_7\":\"" + Common.defEncode(ent.getoa_nvar100_7())
                + "\",\"v_oa_nvar100_8\":\"" + Common.defEncode(ent.getoa_nvar100_8())
                + "\",\"v_oa_nvar100_9\":\"" + Common.defEncode(ent.getoa_nvar100_9())
                + "\",\"v_oa_nvar100_10\":\"" + Common.defEncode(ent.getoa_nvar100_10())
                + "\",\"v_oa_nvar100_11\":\"" + Common.defEncode(ent.getoa_nvar100_11())
                + "\",\"v_oa_nvar100_12\":\"" + Common.defEncode(ent.getoa_nvar100_12())
                + "\",\"v_oa_nvar100_13\":\"" + Common.defEncode(ent.getoa_nvar100_13())
                + "\",\"v_oa_nvar100_14\":\"" + Common.defEncode(ent.getoa_nvar100_14())
                + "\",\"v_oa_nvar100_15\":\"" + Common.defEncode(ent.getoa_nvar100_15())
                + "\",\"v_oa_nvar100_16\":\"" + Common.defEncode(ent.getoa_nvar100_16())
                + "\",\"v_oa_nvar100_17\":\"" + Common.defEncode(ent.getoa_nvar100_17())
                + "\",\"v_oa_nvar100_18\":\"" + Common.defEncode(ent.getoa_nvar100_18())
                + "\",\"v_oa_nvar100_19\":\"" + Common.defEncode(ent.getoa_nvar100_19())
                + "\",\"v_oa_nvar100_20\":\"" + Common.defEncode(ent.getoa_nvar100_20())
                + "\",\"v_oa_nvarmax_1|nclob\":\"" + Common.defEncode(ent.getoa_nvarmax_1())
                + "\",\"v_oa_nvarmax_2|nclob\":\"" + Common.defEncode(ent.getoa_nvarmax_2())
                + "\",\"v_oa_nvarmax_3|nclob\":\"" + Common.defEncode(ent.getoa_nvarmax_3())
                + "\",\"v_oa_nvarmax_4|nclob\":\"" + Common.defEncode(ent.getoa_nvarmax_4())
                + "\",\"v_oa_nvarmax_5|nclob\":\"" + Common.defEncode(ent.getoa_nvarmax_5())
                + "\",\"v_oa_deci2_1\":" + Common.defEncode(Double.toString(ent.getoa_deci2_1()))
                + "\"v_oa_deci2_2\":" + Common.defEncode(Double.toString(ent.getoa_deci2_2()))
                + "\"v_oa_deci2_3\":" + Common.defEncode(Double.toString(ent.getoa_deci2_3()))
                + "\"v_oa_deci2_4\":" + Common.defEncode(Double.toString(ent.getoa_deci2_4()))
                + "\"v_oa_deci2_5\":" + Common.defEncode(Double.toString(ent.getoa_deci2_5()))
                + "\"v_oa_deci2_6\":" + Common.defEncode(Double.toString(ent.getoa_deci2_6()))
                + "\"v_oa_deci2_7\":" + Common.defEncode(Double.toString(ent.getoa_deci2_7()))
                + "\"v_oa_deci2_8\":" + Common.defEncode(Double.toString(ent.getoa_deci2_8()))
                + "\"v_oa_deci2_9\":" + Common.defEncode(Double.toString(ent.getoa_deci2_9()))
                + "\"v_oa_deci2_10\":" + Common.defEncode(Double.toString(ent.getoa_deci2_10()))
                + "\"v_oa_deci2_11\":" + Common.defEncode(Double.toString(ent.getoa_deci2_11()))
                + "\"v_oa_deci2_12\":" + Common.defEncode(Double.toString(ent.getoa_deci2_12()))
                + "\"v_oa_deci2_13\":" + Common.defEncode(Double.toString(ent.getoa_deci2_13()))
                + "\"v_oa_deci2_14\":" + Common.defEncode(Double.toString(ent.getoa_deci2_14()))
                + "\"v_oa_deci2_15\":" + Common.defEncode(Double.toString(ent.getoa_deci2_15()))
              /*  +"\"v_oa_check_1|bool\":"+Common.defEncode(Boolean.toString( ent.getoa_check_1()))
                +"\"v_oa_check_2|bool\":"+Common.defEncode(Boolean.toString( ent.getoa_check_2()))
                +"\"v_oa_check_3|bool\":"+Common.defEncode(Boolean.toString( ent.getoa_check_3()))
                +"\"v_oa_check_4|bool\":"+Common.defEncode(Boolean.toString( ent.getoa_check_4()))
                +"\"v_oa_check_5|bool\":"+Common.defEncode(Boolean.toString( ent.getoa_check_5()))*/
                + "\"v_oa_date_1|date\":\"" + Common.defEncode(ent.getoa_date_1())
                + "\",\"v_oa_date_2|date\":\"" + Common.defEncode(ent.getoa_date_2())
                + "\",\"v_oa_date_3|date\":\"" + Common.defEncode(ent.getoa_date_3())
                + "\",\"v_oa_date_4|date\":\"" + Common.defEncode(ent.getoa_date_4())
                + "\",\"v_oa_date_5|date\":\"" + Common.defEncode(ent.getoa_date_5())
                + "\",\"v_oa_sum_deci2_1\":" + Common.defEncode(Double.toString(ent.getoa_sum_deci2_1()))
                + "\"v_oa_sum_deci2_2\":" + Common.defEncode(Double.toString(ent.getoa_sum_deci2_2()))
                + "\"v_oa_sum_deci2_3\":" + Common.defEncode(Double.toString(ent.getoa_sum_deci2_3()))
                + "\"v_oa_sum_deci2_4\":" + Common.defEncode(Double.toString(ent.getoa_sum_deci2_4()))
                + "\"v_oa_sum_deci2_5\":" + Common.defEncode(Double.toString(ent.getoa_sum_deci2_5()))
                + "\"v_oa_sum_nvarmax_1|nclob\":\"" + Common.defEncode(ent.getoa_sum_nvarmax_1())
                + "\",\"v_oa_sum_nvarmax_2|nclob\":\"" + Common.defEncode(ent.getoa_sum_nvarmax_2())
                + "\",\"v_oa_sum_nvarmax_3|nclob\":\"" + Common.defEncode(ent.getoa_sum_nvarmax_3())
                + "\",\"v_oa_sum_nvarmax_4|nclob\":\"" + Common.defEncode(ent.getoa_sum_nvarmax_4())
                + "\",\"v_oa_sum_nvarmax_5|nclob\":\"" + Common.defEncode(ent.getoa_sum_nvarmax_5())
                + "\",\"v_state_code\":\"" + Common.defEncode(ent.getstate_code())
                + "\",\"v_state_name\":\"" + Common.defEncode(ent.getstate_name())
                + "\",\"v_oa_order_1\":" + Common.defEncode(Double.toString(ent.getoa_order_1()))
                + "\"v_oa_order_2\":" + Common.defEncode(Double.toString(ent.getoa_order_2()))
                + "\"v_oa_order_3\":" + Common.defEncode(Double.toString(ent.getoa_order_3()))
                + "\"v_oa_yxm\":\"" + Common.defEncode(ent.getoa_yxm())
                + "\",\"v_task_condition\":\"" + Common.defEncode(ent.gettask_condition())
                + "\"},\"MachineCode\":\"" + Common.MachineCode
                + "\",\"RandomCode\":\"" + Common.RandomCode + "\",\"LoginType\":\"" + Common.LoginType + "\"}";

        Json = Common.defEncode(Json);
        return Json;
    }


    public static String BaseSelect(EntityBase ent) {
        String Json = "{\"procName\":\"Proc_Base_Select\"" +
                ",\"outStr\":\"r_result,r_error,r_cursor\"" +
                ",\"inJson\":{\"v_order_list\":\"" + Common.defEncode(ent.getorder_list()) + "\"" +
                ",\"v_corp_tn\":\"" + Common.defEncode(Common.LoginUser.getcorp_tn()) + "\"" +
                ",\"v_tb_code\":\"" + Common.defEncode(ent.gettb_code()) + "\"" +
                ",\"v_type_code\":\"" + Common.defEncode(ent.gettype_code()) + "\"" +
                ",\"v_kind_code\":\"" + Common.defEncode(ent.getkind_code()) + "\"" +
                ",\"v_base_code\":\"" + Common.defEncode(ent.getbase_code()) + "\"" +
                ",\"v_base_number\":\"" + Common.defEncode(ent.getbase_number()) + "\"" +
                ",\"v_make_ucode\":\"" + Common.defEncode(ent.getmake_ucode()) + "\"" +
                ",\"v_make_uname\":\"" + Common.defEncode(ent.getmake_uname()) + "\"" +
                ",\"v_make_dcode\":\"" + Common.defEncode(ent.getmake_dcode()) + "\"" +
                ",\"v_make_dname\":\"" + Common.defEncode(ent.getmake_dname()) + "\"" +
                ",\"v_make_pcode\":\"" + Common.defEncode(ent.getmake_pcode()) + "\"" +
                ",\"v_make_pun\":\"" + Common.defEncode(ent.getmake_pun()) + "\"" +
                ",\"v_make_pname\":\"" + Common.defEncode(ent.getmake_pname()) + "\"" +
                ",\"v_make_date_1|date\":\"" + Common.defEncode(ent.getmake_date_1()) + "\"" +
                ",\"v_make_date_2|date\":\"" + Common.defEncode(ent.getmake_date_2()) + "\"" +
                ",\"v_base_name\":\"" + Common.defEncode(ent.getbase_name()) + "\"" +
                ",\"v_base_nvar100_1\":\"" + Common.defEncode(ent.getbase_nvar100_1()) + "\"" +
                ",\"v_base_nvar100_2\":\"" + Common.defEncode(ent.getbase_nvar100_2()) + "\"" +
                ",\"v_base_nvar100_3\":\"" + Common.defEncode(ent.getbase_nvar100_3()) + "\"" +
                ",\"v_base_nvar100_4\":\"" + Common.defEncode(ent.getbase_nvar100_4()) + "\"" +
                ",\"v_base_nvar100_5\":\"" + Common.defEncode(ent.getbase_nvar100_5()) + "\"" +
                ",\"v_base_nvar100_6\":\"" + Common.defEncode(ent.getbase_nvar100_6()) + "\"" +
                ",\"v_base_nvar100_7\":\"" + Common.defEncode(ent.getbase_nvar100_7()) + "\"" +
                ",\"v_base_nvar100_8\":\"" + Common.defEncode(ent.getbase_nvar100_8()) + "\"" +
                ",\"v_base_nvar100_9\":\"" + Common.defEncode(ent.getbase_nvar100_9()) + "\"" +
                ",\"v_base_nvar100_10\":\"" + Common.defEncode(ent.getbase_nvar100_10()) + "\"}" +
                ",\"MachineCode\":\"" + Common.MachineCode + "\",\"RandomCode\":\"" + Common.RandomCode + "\"" +
                ",\"LoginType\":\"" + Common.LoginType + "\"}";

        Json = Common.defEncode(Json);
        return Json;
    }


    public static String BaseSelectForShijian(EntityBase ent) {

        String Json = "{\"procName\":\"Proc_Base_SelectForShijian\"" +
                ",\"outStr\":\"r_result,r_error,r_cursor\"" +
                ",\"inJson\":{\"v_order_list\":\"" + Common.defEncode(ent.getorder_list()) + "\"" +
                ",\"v_corp_tn\":\"" + Common.defEncode(Common.LoginUser.getcorp_tn()) + "\"" +
                ",\"v_base_date_1_1|date\":\"" + Common.defEncode(ent.getbase_date_1_1()) + "\"}" +
                ",\"MachineCode\":\"" + Common.MachineCode + "\",\"RandomCode\":\"" + Common.RandomCode + "\"" +
                ",\"LoginType\":\"" + Common.LoginType + "\"}";

        Json = Common.defEncode(Json);
        return Json;
    }

    public static String Base_Add(EntityBase ent) {
        SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date(System.currentTimeMillis());
        String Json = "{\"procName\":\"Proc_Base_Add\",\"outStr\":\"r_result,r_error\"" +
                ",\"inJson\":{\"v_corp_tn\":\"" + Common.defEncode(Common.LoginUser.getcorp_tn()) + "\"" +
                ",\"v_base_code\":\"" + Common.defEncode(ent.getbase_code()) + "\"" +
                ",\"v_base_number\":\"" + Common.defEncode(ent.getbase_number()) + "\"" +
                ",\"v_base_name\":\"" + Common.defEncode(ent.getbase_name()) + "\"" +
                ",\"v_base_nvar100_1\":\"" + Common.defEncode(ent.getbase_nvar100_1()) + "\"" +
                ",\"v_base_nvar100_2\":\"" + Common.defEncode(ent.getbase_nvar100_2()) + "\"" +
                ",\"v_base_nvar100_3\":\"" + Common.defEncode(ent.getbase_nvar100_3()) + "\"" +
                ",\"v_base_nvar100_4\":\"" + Common.defEncode(ent.getbase_nvar100_4()) + "\"" +
                ",\"v_base_nvar100_5\":\"" + Common.defEncode(ent.getbase_nvar100_5()) + "\"" +
                ",\"v_base_nvar100_6\":\"" + Common.defEncode(ent.getbase_nvar100_6()) + "\"" +
                ",\"v_base_nvar100_7\":\"" + Common.defEncode(ent.getbase_nvar100_7()) + "\"" +
                ",\"v_base_nvar100_8\":\"" + Common.defEncode(ent.getbase_nvar100_8()) + "\"" +
                ",\"v_base_nvar100_9\":\"" + Common.defEncode(ent.getbase_nvar100_9()) + "\"" +
                ",\"v_base_nvar100_10\":\"" + Common.defEncode(ent.getbase_nvar100_10()) + "\"" +
                ",\"v_base_nvar100_11\":\"" + Common.defEncode(ent.getbase_nvar100_11()) + "\"" +
                ",\"v_base_nvar100_12\":\"" + Common.defEncode(ent.getbase_nvar100_12()) + "\"" +
                ",\"v_base_nvar100_13\":\"" + Common.defEncode(ent.getbase_nvar100_13()) + "\"" +
                ",\"v_base_nvar100_14\":\"" + Common.defEncode(ent.getbase_nvar100_14()) + "\"" +
                ",\"v_base_nvar100_15\":\"" + Common.defEncode(ent.getbase_nvar100_15()) + "\"" +
                ",\"v_base_nvar100_16\":\"" + Common.defEncode(ent.getbase_nvar100_16()) + "\"" +
                ",\"v_base_nvar100_17\":\"" + Common.defEncode(ent.getbase_nvar100_17()) + "\"" +
                ",\"v_base_nvar100_18\":\"" + Common.defEncode(ent.getbase_nvar100_18()) + "\"" +
                ",\"v_base_nvar100_19\":\"" + Common.defEncode(ent.getbase_nvar100_19()) + "\"" +
                ",\"v_base_nvar100_20\":\"" + Common.defEncode(ent.getbase_nvar100_20()) + "\"" +
                ",\"v_base_nvarmax_1\":\"" + Common.defEncode(ent.getbase_nvarmax_1()) + "\"" +
                ",\"v_base_nvarmax_2\":\"" + Common.defEncode(ent.getbase_nvarmax_2()) + "\"" +
                ",\"v_base_nvarmax_3\":\"" + Common.defEncode(ent.getbase_nvarmax_3()) + "\"" +
                ",\"v_base_nvarmax_4\":\"" + Common.defEncode(ent.getbase_nvarmax_4()) + "\"" +
                ",\"v_base_nvarmax_5\":\"" + Common.defEncode(ent.getbase_nvarmax_5()) + "\"" +
                ",\"v_base_nvarmax_6\":\"" + Common.defEncode(ent.getbase_nvarmax_6()) + "\"" +
                ",\"v_base_nvarmax_7\":\"" + Common.defEncode(ent.getbase_nvarmax_7()) + "\"" +
                ",\"v_base_nvarmax_8\":\"" + Common.defEncode(ent.getbase_nvarmax_8()) + "\"" +
                ",\"v_base_nvarmax_9\":\"" + Common.defEncode(ent.getbase_nvarmax_9()) + "\"" +
                ",\"v_base_nvarmax_10\":\"" + Common.defEncode(ent.getbase_nvarmax_10()) + "\"" +
                ",\"v_base_deci2_1\":" + Common.defEncode(Double.toString(ent.getbase_deci2_1())) +
                ",\"v_base_deci2_2\":" + Common.defEncode(Double.toString(ent.getbase_deci2_2())) +
                ",\"v_base_deci2_3\":" + Common.defEncode(Double.toString(ent.getbase_deci2_3())) +
                ",\"v_base_deci2_4\":" + Common.defEncode(Double.toString(ent.getbase_deci2_4())) +
                ",\"v_base_deci2_5\":" + Common.defEncode(Double.toString(ent.getbase_deci2_5())) +
                ",\"v_base_deci2_6\":" + Common.defEncode(Double.toString(ent.getbase_deci2_6())) +
                ",\"v_base_deci2_7\":" + Common.defEncode(Double.toString(ent.getbase_deci2_7())) +
                ",\"v_base_deci2_8\":" + Common.defEncode(Double.toString(ent.getbase_deci2_8())) +
                ",\"v_base_deci2_9\":" + Common.defEncode(Double.toString(ent.getbase_deci2_9())) +
                ",\"v_base_deci2_10\":" + Common.defEncode(Double.toString(ent.getbase_deci2_10())) +
                ",\"v_base_int_1\":" + Common.defEncode(Integer.toString(ent.getbase_int_1())) +
                ",\"v_base_int_2\":" + Common.defEncode(Integer.toString(ent.getbase_int_2())) +
                ",\"v_base_int_3\":" + Common.defEncode(Integer.toString(ent.getbase_int_3())) +
                ",\"v_base_int_4\":" + Common.defEncode(Integer.toString(ent.getbase_int_4())) +
                ",\"v_base_int_5\":" + Common.defEncode(Integer.toString(ent.getbase_int_5())) +
                ",\"v_base_int_6\":" + Common.defEncode(Integer.toString(ent.getbase_int_6())) +
                ",\"v_base_int_7\":" + Common.defEncode(Integer.toString(ent.getbase_int_7())) +
                ",\"v_base_int_8\":" + Common.defEncode(Integer.toString(ent.getbase_int_8())) +
                ",\"v_base_int_9\":" + Common.defEncode(Integer.toString(ent.getbase_int_9())) +
                ",\"v_base_int_10\":" + Common.defEncode(Integer.toString(ent.getbase_int_10())) +
                ",\"v_base_check_1|bool\":" + Common.defEncode(Integer.toString(ent.getbase_check_1())) +
                ",\"v_base_check_2|bool\":" + Common.defEncode(Integer.toString(ent.getbase_check_2())) +
                ",\"v_base_check_3|bool\":" + Common.defEncode(Integer.toString(ent.getbase_check_3())) +
                ",\"v_base_check_4|bool\":" + Common.defEncode(Integer.toString(ent.getbase_check_4())) +
                ",\"v_base_check_5|bool\":" + Common.defEncode(Integer.toString(ent.getbase_check_5())) +
                ",\"v_base_check_6|bool\":" + Common.defEncode(Integer.toString(ent.getbase_check_6())) +
                ",\"v_base_check_7|bool\":" + Common.defEncode(Integer.toString(ent.getbase_check_7())) +
                ",\"v_base_check_8|bool\":" + Common.defEncode(Integer.toString(ent.getbase_check_8())) +
                ",\"v_base_check_9|bool\":" + Common.defEncode(Integer.toString(ent.getbase_check_9())) +
                ",\"v_base_check_10|bool\":" + Common.defEncode(Integer.toString(ent.getbase_check_10())) +
                ",\"v_base_date_1|date\":\"" + Common.defEncode(ent.getbase_date_1()) + "\"" +
                ",\"v_base_date_2|date\":\"" + Common.defEncode(ent.getbase_date_2()) + "\"" +
                ",\"v_base_date_3|date\":\"" + Common.defEncode(ent.getbase_date_3()) + "\"" +
                ",\"v_base_date_4|date\":\"" + Common.defEncode(ent.getbase_date_4()) + "\"" +
                ",\"v_base_date_5|date\":\"" + Common.defEncode(ent.getbase_date_5()) + "\"" +
                ",\"v_base_date_6|date\":\"" + Common.defEncode(ent.getbase_date_6()) + "\"" +
                ",\"v_base_date_7|date\":\"" + Common.defEncode(ent.getbase_date_7()) + "\"" +
                ",\"v_base_date_8|date\":\"" + Common.defEncode(ent.getbase_date_8()) + "\"" +
                ",\"v_base_date_9|date\":\"" + Common.defEncode(ent.getbase_date_9()) + "\"" +
                ",\"v_base_date_10|date\":\"" + Common.defEncode(ent.getbase_date_10()) + "\"" +
                ",\"v_base_order_1\":" + Common.defEncode(Double.toString(ent.getbase_order_1())) +
                ",\"v_base_order_2\":" + Common.defEncode(Double.toString(ent.getbase_order_2())) +
                ",\"v_base_order_3\":" + Common.defEncode(Double.toString(ent.getbase_order_3())) +
                ",\"v_tb_code\":\"" + Common.defEncode(ent.gettb_code()) + "\"" +
                ",\"v_type_code\":\"" + Common.defEncode(ent.gettype_code()) + "\"" +
                ",\"v_kind_code\":\"" + Common.defEncode(ent.getkind_code()) + "\"" +
                ",\"v_base_route\":\"" + Common.defEncode(ent.getbase_route()) + "\"" +
                ",\"v_base_level\":" + Common.defEncode(Integer.toString(ent.getbase_level())) +
                ",\"v_base_upcode\":\"" + Common.defEncode(ent.getbase_upcode()) + "\"" +
                ",\"v_base_unit_code\":\"" + Common.defEncode(ent.getbase_unit_code()) + "\"" +
                ",\"v_base_fname\":\"" + Common.defEncode(ent.getbase_fname()) + "\"" +
                ",\"v_base_yxm\":\"" + Common.defEncode(ent.getbase_yxm()) + "\"" +
                ",\"v_make_ucode\":\"" + Common.defEncode(Common.LoginUser.getunit_code()) + "\"" +
                ",\"v_make_uname\":\"" + Common.defEncode(Common.LoginUser.getunit_name()) + "\"" +
                ",\"v_make_dcode\":\"" + Common.defEncode(Common.LoginUser.getdept_code()) + "\"" +
                ",\"v_make_dname\":\"" + Common.defEncode(Common.LoginUser.getdept_name()) + "\"" +
                ",\"v_make_pcode\":\"" + Common.defEncode(Common.LoginUser.getpers_code()) + "\"" +
                ",\"v_make_pun\":\"" + Common.defEncode(Common.LoginUser.getpers_un()) + "\"" +
                ",\"v_make_pname\":\"" + Common.defEncode(Common.LoginUser.getpers_name()) + "\"" +
                ",\"v_make_date|date\":\"" + Common.defEncode(format1.format(date)) + "\"" +
                ",\"v_base_type\":\"" + Common.defEncode(ent.getbase_type()) + "\" }" +
                ",\"MachineCode\":\"" + Common.MachineCode + "\"" +
                ",\"RandomCode\":\"" + Common.RandomCode + "\",\"LoginType\":\"" + Common.LoginType + "\"}";
        Json = Common.defEncode(Json);
        return Json;
    }


    public static String BasePageSelect(int page_size, int page_current, EntityBase ent) {

        String Json = "{\"procName\":\"Proc_Base_PageSelect\"" +
                ",\"outStr\":\"r_result,r_error,r_cursor,r_total_capacity,r_page_capacity,r_total_pages,r_page_current\"" +
                ",\"inJson\":{,\"v_order_list\":\"" + Common.defEncode(ent.getorder_list()) + "\"" +
                ",\"v_page_size\":" + Common.defEncode(Integer.toString(page_size)) +
                ",\"v_page_current\":" + Common.defEncode(Integer.toString(page_current)) +
                ",\"v_corp_tn\":\"" + Common.defEncode(Common.LoginUser.getcorp_tn()) + "\"" +
                ",\"v_tb_code\":\"" + Common.defEncode(ent.gettb_code()) + "\"" +
                ",\"v_type_code\":\"" + Common.defEncode(ent.gettype_code()) + "\"" +
                ",\"v_kind_code\":\"" + Common.defEncode(ent.getkind_code()) + "\"" +
                ",\"v_base_code\":\"" + Common.defEncode(ent.getbase_code()) + "\"" +
                ",\"v_base_number\":\"" + Common.defEncode(ent.getbase_number()) + "\"" +
                ",\"v_make_ucode\":\"" + Common.defEncode(ent.getmake_ucode()) + "\"" +
                ",\"v_make_uname\":\"" + Common.defEncode(ent.getmake_uname()) + "\"" +
                ",\"v_make_dcode\":\"" + Common.defEncode(ent.getmake_dcode()) + "\"" +
                ",\"v_make_dname\":\"" + Common.defEncode(ent.getmake_dname()) + "\"" +
                ",\"v_make_pcode\":\"" + Common.defEncode(ent.getmake_pcode()) + "\"" +
                ",\"v_make_pun\":\"" + Common.defEncode(ent.getmake_pun()) + "\"" +
                ",\"v_make_pname\":\"" + Common.defEncode(ent.getmake_pname()) + "\"" +
                ",\"v_make_date_1|date\":\"" + Common.defEncode(ent.getmake_date_1()) + "\"" +
                ",\"v_make_date_2|date\":\"" + Common.defEncode(ent.getmake_date_2()) + "\"" +
                ",\"v_base_name\":\"" + Common.defEncode(ent.getbase_name()) + "\"" +
                ",\"v_base_nvar100_1\":\"" + Common.defEncode(ent.getbase_nvar100_1()) + "\"" +
                ",\"v_base_nvar100_2\":\"" + Common.defEncode(ent.getbase_nvar100_2()) + "\"" +
                ",\"v_base_nvar100_3\":\"" + Common.defEncode(ent.getbase_nvar100_3()) + "\"" +
                ",\"v_base_nvar100_4\":\"" + Common.defEncode(ent.getbase_nvar100_4()) + "\"" +
                ",\"v_base_nvar100_5\":\"" + Common.defEncode(ent.getbase_nvar100_5()) + "\"" +
                ",\"v_base_nvar100_6\":\"" + Common.defEncode(ent.getbase_nvar100_6()) + "\"" +
                ",\"v_base_nvar100_7\":\"" + Common.defEncode(ent.getbase_nvar100_7()) + "\"" +
                ",\"v_base_nvar100_8\":\"" + Common.defEncode(ent.getbase_nvar100_8()) + "\"" +
                ",\"v_base_nvar100_9\":\"" + Common.defEncode(ent.getbase_nvar100_9()) + "\"" +
                ",\"v_base_nvar100_10\":\"" + Common.defEncode(ent.getbase_nvar100_10()) + "\"" +
                ",\"v_base_nvar100_1_dim\":\"" + Common.defEncode(ent.getbase_nvar100_1_dim()) + "\"" +
                ",\"v_base_nvar100_2_dim\":\"" + Common.defEncode(ent.getbase_nvar100_2_dim()) + "\"" +
                ",\"v_base_nvar100_3_dim\":\"" + Common.defEncode(ent.getbase_nvar100_3_dim()) + "\"" +
                ",\"v_base_nvar100_4_dim\":\"" + Common.defEncode(ent.getbase_nvar100_4_dim()) + "\"" +
                ",\"v_base_nvar100_5_dim\":\"" + Common.defEncode(ent.getbase_nvar100_5_dim()) + "\"" +
                ",\"v_base_nvar100_6_dim\":\"" + Common.defEncode(ent.getbase_nvar100_6_dim()) + "\"" +
                ",\"v_base_nvar100_7_dim\":\"" + Common.defEncode(ent.getbase_nvar100_7_dim()) + "\"" +
                ",\"v_base_nvar100_8_dim\":\"" + Common.defEncode(ent.getbase_nvar100_8_dim()) + "\"" +
                ",\"v_base_nvar100_9_dim\":\"" + Common.defEncode(ent.getbase_nvar100_9_dim()) + "\"" +
                ",\"v_base_nvar100_10_dim\":\"" + Common.defEncode(ent.getbase_nvar100_10_dim()) + "\"" +
                ",\"v_base_type\":\"" + Common.defEncode(ent.getbase_type()) + "\"" +
                ",\"v_base_date_1_1|date\":\"" + Common.defEncode(ent.getbase_date_1_1()) + "\"" +
                ",\"v_base_date_1_2|date\":\"" + Common.defEncode(ent.getbase_date_1_2()) + "\"}" +
                ",\"MachineCode\":\"" + Common.MachineCode + "\",\"RandomCode\":\"" + Common.RandomCode
                + "\",\"LoginType\":\"" + Common.LoginType + "\"}";
        Json = Common.defEncode(Json);
        return Json;
    }


    public static String PersonalSelectBySelf() {
        String Json = "{\"procName\":\"Proc_Personal_SelectByKey\"" +
                ",\"outStr\":\"r_result,r_error,r_cursor\"" +
                ",\"inJson\":{\"v_order_list\":\"\"" +
                ",\"v_corp_tn\":\"" + Common.defEncode(Common.LoginUser.getcorp_tn()) + "\"" +
                ",\"v_pers_code\":\"" + Common.defEncode(Common.LoginUser.getpers_code()) + "\"}" +
                ",\"MachineCode\":\"" + Common.MachineCode + "\",\"RandomCode\":\"" + Common.RandomCode + "\"" +
                ",\"LoginType\":\"" + Common.LoginType + "\"}";

        Json = Common.defEncode(Json);
        return Json;
    }


    public static String OAListAdd(EntityOA ent) {

        String Json = "\"procName\":\"Proc_OAList_Add\",\"outStr\":\"r_result,r_error,r_id\"" +
                ",\"inJson\":{\"v_corp_tn\":\"" + Common.defEncode(Common.LoginUser.getcorp_tn()) + "\"" +
                ",\"v_oa_code\":\"" + Common.defEncode(ent.getoa_code()) + "\"" +
                ",\"v_oal_nvar100_1\":\"" + Common.defEncode(ent.getoal_nvar100_1()) + "\"" +
                ",\"v_oal_nvar100_2\":\"" + Common.defEncode(ent.getoal_nvar100_2()) + "\"" +
                ",\"v_oal_nvar100_3\":\"" + Common.defEncode(ent.getoal_nvar100_3()) + "\"" +
                ",\"v_oal_nvar100_4\":\"" + Common.defEncode(ent.getoal_nvar100_4()) + "\"" +
                ",\"v_oal_nvar100_5\":\"" + Common.defEncode(ent.getoal_nvar100_5()) + "\"" +
                ",\"v_oal_nvar100_6\":\"" + Common.defEncode(ent.getoal_nvar100_6()) + "\"" +
                ",\"v_oal_nvar100_7\":\"" + Common.defEncode(ent.getoal_nvar100_7()) + "\"" +
                ",\"v_oal_nvar100_8\":\"" + Common.defEncode(ent.getoal_nvar100_8()) + "\"" +
                ",\"v_oal_nvar100_9\":\"" + Common.defEncode(ent.getoal_nvar100_9()) + "\"" +
                ",\"v_oal_nvar100_10\":\"" + Common.defEncode(ent.getoal_nvar100_10()) + "\"" +
                ",\"v_oal_nvarmax_1|nclob\":\"" + Common.defEncode(ent.getoal_nvarmax_1()) + "\"" +
                ",\"v_oal_nvarmax_2|nclob\":\"" + Common.defEncode(ent.getoal_nvarmax_2()) + "\"" +
                ",\"v_oal_nvarmax_3|nclob\":\"" + Common.defEncode(ent.getoal_nvarmax_3()) + "\"" +
                ",\"v_oal_nvarmax_4|nclob\":\"" + Common.defEncode(ent.getoal_nvarmax_4()) + "\"" +
                ",\"v_oal_nvarmax_5|nclob\":\"" + Common.defEncode(ent.getoal_nvarmax_5()) + "\"" +
                ",\"v_oal_int_1\":" + Common.defEncode(Integer.toString(ent.getoal_int_1())) +
                ",\"v_oal_int_2\":" + Common.defEncode(Integer.toString(ent.getoal_int_2())) +
                ",\"v_oal_int_3\":" + Common.defEncode(Integer.toString(ent.getoal_int_3())) +
                ",\"v_oal_int_4\":" + Common.defEncode(Integer.toString(ent.getoal_int_4())) +
                ",\"v_oal_int_5\":" + Common.defEncode(Integer.toString(ent.getoal_int_5())) +
                ",\"v_oal_deci2_1\":" + Common.defEncode(Double.toString(ent.getoal_deci2_1())) +
                ",\"v_oal_deci2_2\":" + Common.defEncode(Double.toString(ent.getoal_deci2_2())) +
                ",\"v_oal_deci2_3\":" + Common.defEncode(Double.toString(ent.getoal_deci2_3())) +
                ",\"v_oal_deci2_4\":" + Common.defEncode(Double.toString(ent.getoal_deci2_4())) +
                ",\"v_oal_deci2_5\":" + Common.defEncode(Double.toString(ent.getoal_deci2_5())) +
                ",\"v_oal_deci2_6\":" + Common.defEncode(Double.toString(ent.getoal_deci2_6())) +
                ",\"v_oal_deci2_7\":" + Common.defEncode(Double.toString(ent.getoal_deci2_7())) +
                ",\"v_oal_deci2_8\":" + Common.defEncode(Double.toString(ent.getoal_deci2_8())) +
                ",\"v_oal_deci2_9\":" + Common.defEncode(Double.toString(ent.getoal_deci2_9())) +
                ",\"v_oal_deci2_10\":" + Common.defEncode(Double.toString(ent.getoal_deci2_10())) +
                ",\"v_oal_deci10_1\":" + Common.defEncode(Double.toString(ent.getoal_deci10_1())) +
                ",\"v_oal_deci10_2\":" + Common.defEncode(Double.toString(ent.getoal_deci10_2())) +
                ",\"v_oal_deci10_3\":" + Common.defEncode(Double.toString(ent.getoal_deci10_3())) +
                ",\"v_oal_deci10_4\":" + Common.defEncode(Double.toString(ent.getoal_deci10_4())) +
                ",\"v_oal_deci10_5\":" + Common.defEncode(Double.toString(ent.getoal_deci10_5())) +
                ",\"v_oal_deci15_1\":" + Common.defEncode(Double.toString(ent.getoal_deci15_1())) +
                ",\"v_oal_deci15_2\":" + Common.defEncode(Double.toString(ent.getoal_deci15_2())) +
                ",\"v_oal_deci15_3\":" + Common.defEncode(Double.toString(ent.getoal_deci15_3())) +
                ",\"v_oal_deci15_4\":" + Common.defEncode(Double.toString(ent.getoal_deci15_4())) +
                ",\"v_oal_deci15_5\":" + Common.defEncode(Double.toString(ent.getoal_deci15_5())) +
                ",\"v_oal_check_1|bool\":" + Common.defEncode(Integer.toString(ent.getoal_check_1())) +
                ",\"v_oal_check_2|bool\":" + Common.defEncode(Integer.toString(ent.getoal_check_2())) +
                ",\"v_oal_check_3|bool\":" + Common.defEncode(Integer.toString(ent.getoal_check_3())) +
                ",\"v_oal_check_4|bool\":" + Common.defEncode(Integer.toString(ent.getoal_check_4())) +
                ",\"v_oal_check_5|bool\":" + Common.defEncode(Integer.toString(ent.getoal_check_5())) +
                ",\"v_pers_code\":\"" + Common.defEncode(ent.getpers_code()) + "\"" +
                ",\"v_pers_un\":\"" + Common.defEncode(ent.getpers_un()) + "\"" +
                ",\"v_pers_name\":\"" + Common.defEncode(ent.getpers_name()) + "\"" +
                ",\"v_oal_date_1|date\":\"" + Common.defEncode(ent.getoal_date_1()) + "\"" +
                ",\"v_oal_date_2|date\":\"" + Common.defEncode(ent.getoal_date_2()) + "\"" +
                ",\"v_oal_date_3|date\":\"" + Common.defEncode(ent.getoal_date_3()) + "\"" +
                ",\"v_oal_date_4|date\":\"" + Common.defEncode(ent.getoal_date_4()) + "\"" +
                ",\"v_oal_date_5|date\":\"" + Common.defEncode(ent.getoal_date_5()) + "\"" +
                ",\"v_oal_date_1_1\":\"" + Common.defEncode(ent.getoal_date_1_1()) + "\"" +
                ",\"v_oal_date_2_1\":\"" + Common.defEncode(ent.getoal_date_2_1()) + "\"" +
                ",\"v_oal_date_3_1\":\"" + Common.defEncode(ent.getoal_date_3_1()) + "\"" +
                ",\"v_oal_date_4_1\":\"" + Common.defEncode(ent.getoal_date_4_1()) + "\"" +
                ",\"v_oal_date_5_1\":\"" + Common.defEncode(ent.getoal_date_5_1()) + "\"" +
                ",\"v_oa_oldcode\":\"" + Common.defEncode(ent.getoa_oldcode()) + "\"" +
                ",\"v_oal_order_1\":" + Common.defEncode(Double.toString(ent.getoal_order_1())) +
                ",\"v_oal_order_2\":" + Common.defEncode(Double.toString(ent.getoal_order_2())) +
                ",\"v_oal_order_3\":" + Common.defEncode(Double.toString(ent.getoal_order_3())) +
                ",\"v_oal_type\":\"" + Common.defEncode(ent.getoal_type()) + "\"" +
                ",\"v_oal_nvar100_11\":\"" + Common.defEncode(ent.getoal_nvar100_11()) + "\"" +
                ",\"v_oal_nvar100_12\":\"" + Common.defEncode(ent.getoal_nvar100_12()) + "\"" +
                ",\"v_oal_nvar100_13\":\"" + Common.defEncode(ent.getoal_nvar100_13()) + "\"" +
                ",\"v_oal_nvar100_14\":\"" + Common.defEncode(ent.getoal_nvar100_14()) + "\"" +
                ",\"v_oal_nvar100_15\":\"" + Common.defEncode(ent.getoal_nvar100_15()) + "\"" +
                ",\"v_oal_nvar100_16\":\"" + Common.defEncode(ent.getoal_nvar100_16()) + "\"" +
                ",\"v_oal_nvar100_17\":\"" + Common.defEncode(ent.getoal_nvar100_17()) + "\"" +
                ",\"v_oal_nvar100_18\":\"" + Common.defEncode(ent.getoal_nvar100_18()) + "\"" +
                ",\"v_oal_nvar100_19\":\"" + Common.defEncode(ent.getoal_nvar100_19()) + "\"" +
                ",\"v_oal_nvar100_20\":\"" + Common.defEncode(ent.getoal_nvar100_20()) + "\"},\"MachineCode\":\"" + Common.MachineCode
                + "\",\"RandomCode\":\"" + Common.RandomCode + "\",\"LoginType\":\"" + Common.LoginType + "\"}";

        Json = Common.defEncode(Json);
        return Json;
    }


    public static String OAListSelect(EntityOA ent) {

        String Json = "{\"procName\":\"Proc_OAList_Select\",\"outStr\":\"r_result,r_error,r_cursor\"" +
                ",\"inJson\":{,\"v_order_list\":\"" + Common.defEncode(ent.getorder_list()) + "\"" +
                ",\"v_corp_tn\":\"" + Common.defEncode(Common.LoginUser.getcorp_tn()) + "\"" +
                ",\"v_oa_code\":\"" + Common.defEncode(ent.getoa_code()) + "\"" +
                ",\"v_pers_code\":\"" + Common.defEncode(ent.getpers_code()) + "\"" +
                ",\"v_pers_un\":\"" + Common.defEncode(ent.getpers_un()) + "\"" +
                ",\"v_pers_name\":\"" + Common.defEncode(ent.getpers_name()) + "\"" +
                ",\"v_oa_oldcode\":\"" + Common.defEncode(ent.getoa_oldcode()) + "\"" +
                ",\"v_oal_nvar100_1\":\"" + Common.defEncode(ent.getoal_nvar100_1()) + "\"" +
                ",\"v_oal_nvar100_2\":\"" + Common.defEncode(ent.getoal_nvar100_2()) + "\"" +
                ",\"v_oal_nvar100_3\":\"" + Common.defEncode(ent.getoal_nvar100_3()) + "\"" +
                ",\"v_oal_nvar100_4\":\"" + Common.defEncode(ent.getoal_nvar100_4()) + "\"" +
                ",\"v_oal_nvar100_5\":\"" + Common.defEncode(ent.getoal_nvar100_5()) + "\"" +
                ",\"v_oal_nvar100_6\":\"" + Common.defEncode(ent.getoal_nvar100_6()) + "\"" +
                ",\"v_oal_nvar100_7\":\"" + Common.defEncode(ent.getoal_nvar100_7()) + "\"" +
                ",\"v_oal_nvar100_8\":\"" + Common.defEncode(ent.getoal_nvar100_8()) + "\"" +
                ",\"v_oal_nvar100_9\":\"" + Common.defEncode(ent.getoal_nvar100_9()) + "\"" +
                ",\"v_oal_nvar100_10\":\"" + Common.defEncode(ent.getoal_nvar100_10()) + "\"" +
                ",\"v_oal_nvarmax_1\":\"" + Common.defEncode(ent.getoal_nvarmax_1()) + "\"" +
                ",\"v_oal_nvarmax_2\":\"" + Common.defEncode(ent.getoal_nvarmax_2()) + "\"" +
                ",\"v_oal_nvarmax_3\":\"" + Common.defEncode(ent.getoal_nvarmax_3()) + "\"" +
                ",\"v_oal_nvarmax_4\":\"" + Common.defEncode(ent.getoal_nvarmax_4()) + "\"" +
                ",\"v_oal_nvarmax_5\":\"" + Common.defEncode(ent.getoal_nvarmax_5()) + "\"" +
                ",\"v_oal_type\":\"" + Common.defEncode(ent.getoal_type()) + "\"" +
                ",\"v_tb_code\":\"" + Common.defEncode(ent.gettb_code()) + "\"" +
                ",\"v_type_code\":\"" + Common.defEncode(ent.gettype_code()) + "\"" +
                ",\"v_kind_code\":\"" + Common.defEncode(ent.getkind_code()) + "\"" +
                ",\"v_state_code\":\"" + Common.defEncode(ent.getstate_code()) + "\"}" +
                ",\"MachineCode\":\"" + Common.MachineCode + "\",\"RandomCode\":\"" + Common.RandomCode + "\"" +
                ",\"LoginType\":\"" + Common.LoginType + "\"}";

        Json = Common.defEncode(Json);
        return Json;
    }


    public static String OAListUpdate(EntityOA ent) {

        String Json = "\"procName\":\"Proc_OAList_Update\",\"outStr\":\"r_result,r_error\"" +
                ",\"inJson\":{\"v_corp_tn\":\"" + Common.defEncode(Common.LoginUser.getcorp_tn()) + "\"" +
                ",\"v_oal_id\":" + Common.defEncode(Integer.toString(ent.getoal_id())) +
                ",\"v_oa_code\":\"" + Common.defEncode(ent.getoa_code()) + "\"" +
                ",\"v_oal_nvar100_1\":\"" + Common.defEncode(ent.getoal_nvar100_1()) + "\"" +
                ",\"v_oal_nvar100_2\":\"" + Common.defEncode(ent.getoal_nvar100_2()) + "\"" +
                ",\"v_oal_nvar100_3\":\"" + Common.defEncode(ent.getoal_nvar100_3()) + "\"" +
                ",\"v_oal_nvar100_4\":\"" + Common.defEncode(ent.getoal_nvar100_4()) + "\"" +
                ",\"v_oal_nvar100_5\":\"" + Common.defEncode(ent.getoal_nvar100_5()) + "\"" +
                ",\"v_oal_nvar100_6\":\"" + Common.defEncode(ent.getoal_nvar100_6()) + "\"" +
                ",\"v_oal_nvar100_7\":\"" + Common.defEncode(ent.getoal_nvar100_7()) + "\"" +
                ",\"v_oal_nvar100_8\":\"" + Common.defEncode(ent.getoal_nvar100_8()) + "\"" +
                ",\"v_oal_nvar100_9\":\"" + Common.defEncode(ent.getoal_nvar100_9()) + "\"" +
                ",\"v_oal_nvar100_10\":\"" + Common.defEncode(ent.getoal_nvar100_10()) + "\"" +
                ",\"v_oal_nvarmax_1|nclob\":\"" + Common.defEncode(ent.getoal_nvarmax_1()) + "\"" +
                ",\"v_oal_nvarmax_2|nclob\":\"" + Common.defEncode(ent.getoal_nvarmax_2()) + "\"" +
                ",\"v_oal_nvarmax_3|nclob\":\"" + Common.defEncode(ent.getoal_nvarmax_3()) + "\"" +
                ",\"v_oal_nvarmax_4|nclob\":\"" + Common.defEncode(ent.getoal_nvarmax_4()) + "\"" +
                ",\"v_oal_nvarmax_5|nclob\":\"" + Common.defEncode(ent.getoal_nvarmax_5()) + "\"" +
                ",\"v_oal_int_1\":" + Common.defEncode(Integer.toString(ent.getoal_int_1())) +
                ",\"v_oal_int_2\":" + Common.defEncode(Integer.toString(ent.getoal_int_2())) +
                ",\"v_oal_int_3\":" + Common.defEncode(Integer.toString(ent.getoal_int_3())) +
                ",\"v_oal_int_4\":" + Common.defEncode(Integer.toString(ent.getoal_int_4())) +
                ",\"v_oal_int_5\":" + Common.defEncode(Integer.toString(ent.getoal_int_5())) +
                ",\"v_oal_deci2_1\":" + Common.defEncode(Double.toString(ent.getoal_deci2_1())) +
                ",\"v_oal_deci2_2\":" + Common.defEncode(Double.toString(ent.getoal_deci2_2())) +
                ",\"v_oal_deci2_3\":" + Common.defEncode(Double.toString(ent.getoal_deci2_3())) +
                ",\"v_oal_deci2_4\":" + Common.defEncode(Double.toString(ent.getoal_deci2_4())) +
                ",\"v_oal_deci2_5\":" + Common.defEncode(Double.toString(ent.getoal_deci2_5())) +
                ",\"v_oal_deci2_6\":" + Common.defEncode(Double.toString(ent.getoal_deci2_6())) +
                ",\"v_oal_deci2_7\":" + Common.defEncode(Double.toString(ent.getoal_deci2_7())) +
                ",\"v_oal_deci2_8\":" + Common.defEncode(Double.toString(ent.getoal_deci2_8())) +
                ",\"v_oal_deci2_9\":" + Common.defEncode(Double.toString(ent.getoal_deci2_9())) +
                ",\"v_oal_deci2_10\":" + Common.defEncode(Double.toString(ent.getoal_deci2_10())) +
                ",\"v_oal_deci10_1\":" + Common.defEncode(Double.toString(ent.getoal_deci10_1())) +
                ",\"v_oal_deci10_2\":" + Common.defEncode(Double.toString(ent.getoal_deci10_2())) +
                ",\"v_oal_deci10_3\":" + Common.defEncode(Double.toString(ent.getoal_deci10_3())) +
                ",\"v_oal_deci10_4\":" + Common.defEncode(Double.toString(ent.getoal_deci10_4())) +
                ",\"v_oal_deci10_5\":" + Common.defEncode(Double.toString(ent.getoal_deci10_5())) +
                ",\"v_oal_deci15_1\":" + Common.defEncode(Double.toString(ent.getoal_deci15_1())) +
                ",\"v_oal_deci15_2\":" + Common.defEncode(Double.toString(ent.getoal_deci15_2())) +
                ",\"v_oal_deci15_3\":" + Common.defEncode(Double.toString(ent.getoal_deci15_3())) +
                ",\"v_oal_deci15_4\":" + Common.defEncode(Double.toString(ent.getoal_deci15_4())) +
                ",\"v_oal_deci15_5\":" + Common.defEncode(Double.toString(ent.getoal_deci15_5())) +
                ",\"v_oal_check_1|bool\":" + Common.defEncode(Integer.toString(ent.getoal_check_1())) +
                ",\"v_oal_check_2|bool\":" + Common.defEncode(Integer.toString(ent.getoal_check_2())) +
                ",\"v_oal_check_3|bool\":" + Common.defEncode(Integer.toString(ent.getoal_check_3())) +
                ",\"v_oal_check_4|bool\":" + Common.defEncode(Integer.toString(ent.getoal_check_4())) +
                ",\"v_oal_check_5|bool\":" + Common.defEncode(Integer.toString(ent.getoal_check_5())) +
                ",\"v_pers_code\":\"" + Common.defEncode(ent.getpers_code()) + "\"" +
                ",\"v_pers_un\":\"" + Common.defEncode(ent.getpers_un()) + "\"" +
                ",\"v_pers_name\":\"" + Common.defEncode(ent.getpers_name()) + "\"" +
                ",\"v_oal_date_1|date\":\"" + Common.defEncode(ent.getoal_date_1()) + "\"" +
                ",\"v_oal_date_2|date\":\"" + Common.defEncode(ent.getoal_date_2()) + "\"" +
                ",\"v_oal_date_3|date\":\"" + Common.defEncode(ent.getoal_date_3()) + "\"" +
                ",\"v_oal_date_4|date\":\"" + Common.defEncode(ent.getoal_date_4()) + "\"" +
                ",\"v_oal_date_5|date\":\"" + Common.defEncode(ent.getoal_date_5()) + "\"" +
                ",\"v_oal_date_1_1\":\"" + Common.defEncode(ent.getoal_date_1_1()) + "\"" +
                ",\"v_oal_date_2_1\":\"" + Common.defEncode(ent.getoal_date_2_1()) + "\"" +
                ",\"v_oal_date_3_1\":\"" + Common.defEncode(ent.getoal_date_3_1()) + "\"" +
                ",\"v_oal_date_4_1\":\"" + Common.defEncode(ent.getoal_date_4_1()) + "\"" +
                ",\"v_oal_date_5_1\":\"" + Common.defEncode(ent.getoal_date_5_1()) + "\"" +
                ",\"v_oa_oldcode\":\"" + Common.defEncode(ent.getoa_oldcode()) + "\"" +
                ",\"v_oal_order_1\":" + Common.defEncode(Double.toString(ent.getoal_order_1())) +
                ",\"v_oal_order_2\":" + Common.defEncode(Double.toString(ent.getoal_order_2())) +
                ",\"v_oal_order_3\":" + Common.defEncode(Double.toString(ent.getoal_order_3())) +
                ",\"v_oal_type\":\"" + Common.defEncode(ent.getoal_type()) + "\"" +
                ",\"v_oal_nvar100_11\":\"" + Common.defEncode(ent.getoal_nvar100_11()) + "\"" +
                ",\"v_oal_nvar100_12\":\"" + Common.defEncode(ent.getoal_nvar100_12()) + "\"" +
                ",\"v_oal_nvar100_13\":\"" + Common.defEncode(ent.getoal_nvar100_13()) + "\"" +
                ",\"v_oal_nvar100_14\":\"" + Common.defEncode(ent.getoal_nvar100_14()) + "\"" +
                ",\"v_oal_nvar100_15\":\"" + Common.defEncode(ent.getoal_nvar100_15()) + "\"" +
                ",\"v_oal_nvar100_16\":\"" + Common.defEncode(ent.getoal_nvar100_16()) + "\"" +
                ",\"v_oal_nvar100_17\":\"" + Common.defEncode(ent.getoal_nvar100_17()) + "\"" +
                ",\"v_oal_nvar100_18\":\"" + Common.defEncode(ent.getoal_nvar100_18()) + "\"" +
                ",\"v_oal_nvar100_19\":\"" + Common.defEncode(ent.getoal_nvar100_19()) + "\"" +
                ",\"v_oal_nvar100_20\":\"" + Common.defEncode(ent.getoal_nvar100_20()) + "\"},\"MachineCode\":\"" + Common.MachineCode
                + "\",\"RandomCode\":\"" + Common.RandomCode + "\",\"LoginType\":\"" + Common.LoginType + "\"}";

        Json = Common.defEncode(Json);
        return Json;
    }

    public static String OAMainAdd(EntityOA ent) {
        String Json = "{\"procName\":\"Proc_OAMain_Add\",\"outStr\":\"r_result,r_error\"" +
                ",\"inJson\":{\"v_corp_tn\":\"" + Common.defEncode(Common.LoginUser.getcorp_tn()) + "\"" +
                ",\"v_oa_code\":\"" + Common.defEncode(ent.getoa_code())
                + "\",\"v_oa_title\":\"" + Common.defEncode(ent.getoa_title())
                + "\",\"v_oa_content\":\"" + Common.defEncode(ent.getoa_content())
                + "\",\"v_tb_code\":\"" + Common.defEncode(ent.gettb_code())
                + "\",\"v_type_code\":\"" + Common.defEncode(ent.gettype_code())
                + "\",\"v_type_name\":\"" + Common.defEncode(ent.gettype_name())
                + "\",\"v_kind_code\":\"" + Common.defEncode(ent.getkind_code())
                + "\",\"v_kind_name\":\"" + Common.defEncode(ent.getkind_name())
                + "\",\"v_oa_kind\":\"" + Common.defEncode(ent.getoa_kind())
                + "\",\"v_oa_upcode\":\"" + Common.defEncode(ent.getoa_upcode())
                + "\",\"v_make_ucode\":\"" + Common.defEncode(ent.getmake_ucode())
                + "\",\"v_make_uname\":\"" + Common.defEncode(ent.getmake_uname())
                + "\",\"v_make_dcode\":\"" + Common.defEncode(ent.getmake_dcode())
                + "\",\"v_make_dname\":\"" + Common.defEncode(ent.getmake_dname())
                + "\",\"v_make_pcode\":\"" + Common.defEncode(ent.getmake_pcode())
                + "\",\"v_make_pun\":\"" + Common.defEncode(ent.getmake_pun())
                + "\",\"v_make_pname\":\"" + Common.defEncode(ent.getmake_pname())
                + "\",\"v_make_date|date\":\"" + Common.defEncode(ent.getmake_date())
                + "\",\"v_oa_nvar100_1\":\"" + Common.defEncode(ent.getoa_nvar100_1())
                + "\",\"v_oa_nvar100_2\":\"" + Common.defEncode(ent.getoa_nvar100_2())
                + "\",\"v_oa_nvar100_3\":\"" + Common.defEncode(ent.getoa_nvar100_3())
                + "\",\"v_oa_nvar100_4\":\"" + Common.defEncode(ent.getoa_nvar100_4())
                + "\",\"v_oa_nvar100_5\":\"" + Common.defEncode(ent.getoa_nvar100_5())
                + "\",\"v_oa_nvar100_6\":\"" + Common.defEncode(ent.getoa_nvar100_6())
                + "\",\"v_oa_nvar100_7\":\"" + Common.defEncode(ent.getoa_nvar100_7())
                + "\",\"v_oa_nvar100_8\":\"" + Common.defEncode(ent.getoa_nvar100_8())
                + "\",\"v_oa_nvar100_9\":\"" + Common.defEncode(ent.getoa_nvar100_9())
                + "\",\"v_oa_nvar100_10\":\"" + Common.defEncode(ent.getoa_nvar100_10())
                + "\",\"v_oa_nvar100_11\":\"" + Common.defEncode(ent.getoa_nvar100_11())
                + "\",\"v_oa_nvar100_12\":\"" + Common.defEncode(ent.getoa_nvar100_12())
                + "\",\"v_oa_nvar100_13\":\"" + Common.defEncode(ent.getoa_nvar100_13())
                + "\",\"v_oa_nvar100_14\":\"" + Common.defEncode(ent.getoa_nvar100_14())
                + "\",\"v_oa_nvar100_15\":\"" + Common.defEncode(ent.getoa_nvar100_15())
                + "\",\"v_oa_nvar100_16\":\"" + Common.defEncode(ent.getoa_nvar100_16())
                + "\",\"v_oa_nvar100_17\":\"" + Common.defEncode(ent.getoa_nvar100_17())
                + "\",\"v_oa_nvar100_18\":\"" + Common.defEncode(ent.getoa_nvar100_18())
                + "\",\"v_oa_nvar100_19\":\"" + Common.defEncode(ent.getoa_nvar100_19())
                + "\",\"v_oa_nvar100_20\":\"" + Common.defEncode(ent.getoa_nvar100_20())
                + "\",\"v_oa_nvarmax_1|nclob\":\"" + Common.defEncode(ent.getoa_nvarmax_1())
                + "\",\"v_oa_nvarmax_2|nclob\":\"" + Common.defEncode(ent.getoa_nvarmax_2())
                + "\",\"v_oa_nvarmax_3|nclob\":\"" + Common.defEncode(ent.getoa_nvarmax_3())
                + "\",\"v_oa_nvarmax_4|nclob\":\"" + Common.defEncode(ent.getoa_nvarmax_4())
                + "\",\"v_oa_nvarmax_5|nclob\":\"" + Common.defEncode(ent.getoa_nvarmax_5())
                + "\",\"v_oa_deci2_1\":" + Common.defEncode(Double.toString(ent.getoa_deci2_1()))
                + ",\"v_oa_deci2_2\":" + Common.defEncode(Double.toString(ent.getoa_deci2_2()))
                + ",\"v_oa_deci2_3\":" + Common.defEncode(Double.toString(ent.getoa_deci2_3()))
                + ",\"v_oa_deci2_4\":" + Common.defEncode(Double.toString(ent.getoa_deci2_4()))
                + ",\"v_oa_deci2_5\":" + Common.defEncode(Double.toString(ent.getoa_deci2_5()))
                + ",\"v_oa_deci2_6\":" + Common.defEncode(Double.toString(ent.getoa_deci2_6()))
                + ",\"v_oa_deci2_7\":" + Common.defEncode(Double.toString(ent.getoa_deci2_7()))
                + ",\"v_oa_deci2_8\":" + Common.defEncode(Double.toString(ent.getoa_deci2_8()))
                + ",\"v_oa_deci2_9\":" + Common.defEncode(Double.toString(ent.getoa_deci2_9()))
                + ",\"v_oa_deci2_10\":" + Common.defEncode(Double.toString(ent.getoa_deci2_10()))
                + ",\"v_oa_deci2_11\":" + Common.defEncode(Double.toString(ent.getoa_deci2_11()))
                + ",\"v_oa_deci2_12\":" + Common.defEncode(Double.toString(ent.getoa_deci2_12()))
                + ",\"v_oa_deci2_13\":" + Common.defEncode(Double.toString(ent.getoa_deci2_13()))
                + ",\"v_oa_deci2_14\":" + Common.defEncode(Double.toString(ent.getoa_deci2_14()))
                + ",\"v_oa_deci2_15\":" + Common.defEncode(Double.toString(ent.getoa_deci2_15()))
                + ",\"v_oa_check_1|bool\":" + Common.defEncode(Integer.toString(ent.getoa_check_1()))
                + ",\"v_oa_check_2|bool\":" + Common.defEncode(Integer.toString(ent.getoa_check_2()))
                + ",\"v_oa_check_3|bool\":" + Common.defEncode(Integer.toString(ent.getoa_check_3()))
                + ",\"v_oa_check_4|bool\":" + Common.defEncode(Integer.toString(ent.getoa_check_4()))
                + ",\"v_oa_check_5|bool\":" + Common.defEncode(Integer.toString(ent.getoa_check_5()))
                + ",\"v_oa_date_1|date\":\"" + Common.defEncode(ent.getoa_date_1())
                + "\",\"v_oa_date_2|date\":\"" + Common.defEncode(ent.getoa_date_2())
                + "\",\"v_oa_date_3|date\":\"" + Common.defEncode(ent.getoa_date_3())
                + "\",\"v_oa_date_4|date\":\"" + Common.defEncode(ent.getoa_date_4())
                + "\",\"v_oa_date_5|date\":\"" + Common.defEncode(ent.getoa_date_5())
                + "\",\"v_oa_sum_deci2_1\":" + Common.defEncode(Double.toString(ent.getoa_sum_deci2_1()))
                + ",\"v_oa_sum_deci2_2\":" + Common.defEncode(Double.toString(ent.getoa_sum_deci2_2()))
                + ",\"v_oa_sum_deci2_3\":" + Common.defEncode(Double.toString(ent.getoa_sum_deci2_3()))
                + ",\"v_oa_sum_deci2_4\":" + Common.defEncode(Double.toString(ent.getoa_sum_deci2_4()))
                + ",\"v_oa_sum_deci2_5\":" + Common.defEncode(Double.toString(ent.getoa_sum_deci2_5()))
                + ",\"v_oa_sum_nvarmax_1|nclob\":\"" + Common.defEncode(ent.getoa_sum_nvarmax_1())
                + "\",\"v_oa_sum_nvarmax_2|nclob\":\"" + Common.defEncode(ent.getoa_sum_nvarmax_2())
                + "\",\"v_oa_sum_nvarmax_3|nclob\":\"" + Common.defEncode(ent.getoa_sum_nvarmax_3())
                + "\",\"v_oa_sum_nvarmax_4|nclob\":\"" + Common.defEncode(ent.getoa_sum_nvarmax_4())
                + "\",\"v_oa_sum_nvarmax_5|nclob\":\"" + Common.defEncode(ent.getoa_sum_nvarmax_5())
                + "\",\"v_state_code\":\"" + Common.defEncode(ent.getstate_code())
                + "\",\"v_state_name\":\"" + Common.defEncode(ent.getstate_name())
                + "\",\"v_oa_order_1\":" + Common.defEncode(Double.toString(ent.getoa_order_1()))
                + ",\"v_oa_order_2\":" + Common.defEncode(Double.toString(ent.getoa_order_2()))
                + ",\"v_oa_order_3\":" + Common.defEncode(Double.toString(ent.getoa_order_3()))
                + ",\"v_oa_yxm\":\"" + Common.defEncode(ent.getoa_yxm())
                + "\"},\"MachineCode\":\"" + Common.MachineCode
                + "\",\"RandomCode\":\"" + Common.RandomCode + "\",\"LoginType\":\"" + Common.LoginType + "\"}";

        Json = Common.defEncode(Json);
        return Json;
    }



    public static String PersonalPageSelect(int page_size, int page_current, EntityPersonal ent) {
        String Json = "{\"procName\":\"Proc_Personal_PageSelect\"" +
                ",\"outStr\":\"r_result,r_error,r_cursor,r_total_capacity,r_page_capacity,r_total_pages,r_page_current\"" +
                ",\"inJson\":{\"v_order_list\":\"" + Common.defEncode(ent.getorder_list()) + "\"" +
                ",\"v_page_size\":" + Common.defEncode(Integer.toString(page_size)) +
                ",\"v_page_current\":" + Common.defEncode(Integer.toString(page_current)) +
                ",\"v_corp_tn\":\"" + Common.defEncode(Common.LoginUser.getcorp_tn()) + "\"" +
                ",\"v_pers_un\":\"" + Common.defEncode(ent.getpers_un()) + "\"" +
                ",\"v_pers_name\":\"" + Common.defEncode(ent.getpers_name()) + "\"" +
                ",\"v_pers_code\":\"" + Common.defEncode(ent.getpers_code()) + "\"" +
                ",\"v_pers_number\":\"" + Common.defEncode(ent.getpers_number()) + "\"" +
                ",\"v_pers_sex\":\"" + Common.defEncode(ent.getpers_sex()) + "\"" +
                ",\"v_pers_idnumber\":\"" + Common.defEncode(ent.getpers_idnumber()) + "\"" +
                ",\"v_pers_birth_1\":\"" + Common.defEncode(ent.getpers_birth_1()) + "\"" +
                ",\"v_pers_birth_2\":\"" + Common.defEncode(ent.getpers_birth_2()) + "\"" +
                ",\"v_join_time_1\":\"" + Common.defEncode(ent.getjoin_time_1()) + "\"" +
                ",\"v_join_time_2\":\"" + Common.defEncode(ent.getjoin_time_2()) + "\"" +
                ",\"v_pers_degree\":\"" + Common.defEncode(ent.getpers_degree()) + "\"" +
                ",\"v_pers_address\":\"" + Common.defEncode(ent.getpers_address()) + "\"" +
                ",\"v_pers_othercontact\":\"" + Common.defEncode(ent.getpers_othercontact()) + "\"" +
                ",\"v_tb_code\":\"" + Common.defEncode(ent.gettb_code()) + "\"" +
                ",\"v_type_code\":\"" + Common.defEncode(ent.gettype_code()) + "\"" +
                ",\"v_kind_code\":\"" + Common.defEncode(ent.getkind_code()) + "\"" +
                ",\"v_user_name\":\"" + Common.defEncode(ent.getuser_name()) + "\"" +
                ",\"v_unit_code\":\"" + Common.defEncode(ent.getuser_code()) + "\"" +
                ",\"v_dept_code\":\"" + Common.defEncode(ent.getdept_code()) + "\"" +
                ",\"v_pos_code\":\"" + Common.defEncode(ent.getpos_code()) + "\"" +
                ",\"v_role_code\":\"" + Common.defEncode(ent.getrole_code()) + "\"" +
                ",\"v_corp_code\":\"" + Common.defEncode(ent.getcorp_code()) + "\"" +
                ",\"v_pers_openstate|bool\":" + Common.defEncode(Integer.toString(ent.getpers_openstate())) +
                ",\"v_if_pers_openstate|bool\":" + Common.defEncode(Integer.toString(ent.getif_pers_openstate())) +
                ",\"v_pers_state\":\"" + Common.defEncode(ent.getpers_state()) + "\"" +
                ",\"v_state_code\":\"" + Common.defEncode(ent.getstate_code()) + "\"" +
                ",\"v_pers_major\":\"" + Common.defEncode(ent.getpers_major()) + "\"" +
                ",\"v_pers_old_1\":" + Common.defEncode(Integer.toString(ent.getpers_old_1())) +
                ",\"v_pers_old_2\":" + Common.defEncode(Integer.toString(ent.getpers_old_2())) +
                ",\"v_pers_deci2_1_1\":" + Common.defEncode(Double.toString(ent.getpers_deci2_1_1())) +
                ",\"v_pers_deci2_1_2\":" + Common.defEncode(Double.toString(ent.getpers_deci2_1_2())) + "}" +
                ",\"MachineCode\":\"" + Common.MachineCode + "\",\"RandomCode\":\"" + Common.RandomCode
                + "\",\"LoginType\":\"" + Common.LoginType + "\"}";
        Json = Common.defEncode(Json);
        return Json;
    }


    public static String TaskNodePaptAdd(EntityWorkFlow ent) {

        String Json = "{\"procName\":\"Proc_TaskNodePapt_Add" +
                "\",\"outStr\":\"r_result,r_error\"" +
                ",\"inJson\":{\"v_corp_tn\":\"" + Common.defEncode(Common.LoginUser.getcorp_tn()) + "\"" +
                ",\"v_wf_code\":\"" + Common.defEncode(ent.getwf_code()) + "\"" +
                ",\"v_task_code\":\"" + Common.defEncode(ent.gettask_code()) + "\"" +
                ",\"v_node_code\":\"" + Common.defEncode(ent.getnode_code()) + "\"" +
                ",\"v_init_pers_ucode\":\"" + Common.defEncode(ent.getinit_pers_ucode()) + "\"" +
                ",\"v_init_pers_uname\":\"" + Common.defEncode(ent.getinit_pers_uname()) + "\"" +
                ",\"v_init_pcode\":\"" + Common.defEncode(ent.getinit_pcode()) + "\"" +
                ",\"v_init_pun\":\"" + Common.defEncode(ent.getinit_pun()) + "\"" +
                ",\"v_init_pname\":\"" + Common.defEncode(ent.getinit_pname()) + "\"" +
                ",\"v_papt_code\":\"" + Common.defEncode(ent.getpapt_code()) + "\"" +
                ",\"v_papt_pun\":\"" + Common.defEncode(ent.getpapt_pun()) + "\"" +
                ",\"v_papt_name\":\"" + Common.defEncode(ent.getpapt_name()) + "\"" +
                ",\"v_papt_order\":" + Common.defEncode(Double.toString(ent.getpapt_order())) +
                ",\"v_papt_level\":" + Common.defEncode(Integer.toString(ent.getpapt_level())) +
                ",\"v_term_day\":" + Common.defEncode(Integer.toString(ent.getterm_day())) +
                ",\"v_term_hour\":" + Common.defEncode(Integer.toString(ent.getterm_hour())) +
                ",\"v_term_minute\":" + Common.defEncode(Integer.toString(ent.getterm_minute())) +
                ",\"v_form_code\":\"" + Common.defEncode(ent.getform_code()) + "\"" +
                ",\"v_papt_tcode\":\"" + Common.defEncode(ent.getpapt_tcode()) + "\"" +
                ",\"v_papt_tname\":\"" + Common.defEncode(ent.getpapt_tname()) + "\"" +
                ",\"v_papt_key\":\"" + Common.defEncode(ent.getpapt_key()) + "\"},\"MachineCode\":\"" + Common.MachineCode
                + "\",\"RandomCode\":\"" + Common.RandomCode + "\",\"LoginType\":\"" + Common.LoginType + "\"}";

        Json = Common.defEncode(Json);
        return Json;
    }


    public static String TaskNodePaptSelect(EntityWorkFlow ent) {

        String Json = "{\"procName\":\"Proc_TaskNodePapt_Select" +
                "\",\"outStr\":\"r_result,r_error,r_cursor\"" +
                ",\"inJson\":{\"v_order_list\":\"" + Common.defEncode(ent.getorder_list()) + "\"" +
                ",\"v_corp_tn\":\"" + Common.defEncode(Common.LoginUser.getcorp_tn()) + "\"" +
                ",\"v_wf_code\":\"" + Common.defEncode(ent.getwf_code()) + "\"" +
                ",\"v_task_code\":\"" + Common.defEncode(ent.gettask_code()) + "\"" +
                ",\"v_node_code\":\"" + Common.defEncode(ent.getnode_code()) + "\"" +
                ",\"v_init_pers_ucode\":\"" + Common.defEncode(ent.getinit_pers_ucode()) + "\"" +
                ",\"v_init_pcode\":\"" + Common.defEncode(ent.getnit_pcode()) + "\"" +
                ",\"v_papt_tcode\":\"" + Common.defEncode(ent.getpapt_tcode()) + "\"" +
                ",\"v_papt_code\":\"" + Common.defEncode(ent.getpapt_code()) + "\"}" +
                ",\"MachineCode\":\"" + Common.MachineCode + "\",\"RandomCode\":\"" + Common.RandomCode + "\"" +
                ",\"LoginType\":\"" + Common.LoginType + "\"}";

        Json = Common.defEncode(Json);
        return Json;
    }


    public static String TaskNodePaptSelect1(EntityWorkFlow ent) {

        String path = "oracle\\Inside\\WorkFlow\\TaskNodePapt_Select.txt";

        General<EntityWorkFlow> general = new General<EntityWorkFlow>();
        String Json = general.bllJson(ent, path);
        Json = Common.defEncode(Json);

        return Json;

    }


    public static String InfoSelect(EntityInformation ent) {
        String Json = "{\"procName\":\"Proc_Infomation_Select\"" +
                ",\"outStr\":\"r_result,r_error,r_cursor\"" +
                ",\"inJson\":{,\"v_order_list\":\"" + Common.defEncode(ent.getorder_list())
                + "\",\"v_corp_tn\":\""
                + Common.defEncode(Common.LoginUser.getcorp_tn()) + "\"" +
                ",\"v_tb_code\":\"" + Common.defEncode(ent.gettb_code())
                + "\",\"v_type_code\":\"" + Common.defEncode(ent.gettype_code()) + "\"" +
                ",\"v_kind_code\":\"" + Common.defEncode(ent.getkind_code())
                + "\",\"v_info_key_1\":\"" + Common.defEncode(ent.getinfo_key_1()) + "\"" +
                ",\"v_info_key_2\":\"" + Common.defEncode(ent.getinfo_key_2()) + "\"" +
                ",\"v_info_key_3\":\"" + Common.defEncode(ent.getinfo_key_3())
                + "\",\"v_info_key_4\":\"" + Common.defEncode(ent.getinfo_key_4()) + "\"" +
                ",\"v_info_key_5\":\"" + Common.defEncode(ent.getinfo_key_5())
                + "\",\"v_info_key_6\":\"" + Common.defEncode(ent.getinfo_key_6()) + "\"" +
                ",\"v_info_key_7\":\"" + Common.defEncode(ent.getinfo_key_7())
                + "\",\"v_info_key_8\":\"" + Common.defEncode(ent.getinfo_key_8()) + "\"" +
                ",\"v_info_key_9\":\"" + Common.defEncode(ent.getinfo_key_9())
                + "\",\"v_info_key_10\":\"" + Common.defEncode(ent.getinfo_key_10()) + "\"" +
                ",\"v_info_nvar100_1\":\"" + Common.defEncode(ent.getinfo_nvar100_1())
                + "\",\"v_info_nvar100_2\":\"" + Common.defEncode(ent.getinfo_nvar100_2()) + "\"" +
                ",\"v_info_nvar100_3\":\"" + Common.defEncode(ent.getinfo_nvar100_3())
                + "\",\"v_info_nvar100_4\":\"" + Common.defEncode(ent.getinfo_nvar100_4()) + "\"" +
                ",\"v_info_nvar100_5\":\"" + Common.defEncode(ent.getinfo_nvar100_5())
                + "\",\"v_info_nvar100_6\":\"" + Common.defEncode(ent.getinfo_nvar100_6()) + "\"" +
                ",\"v_info_nvar100_7\":\"" + Common.defEncode(ent.getinfo_nvar100_7())
                + "\",\"v_info_nvar100_8\":\"" + Common.defEncode(ent.getinfo_nvar100_8()) + "\"" +
                ",\"v_info_nvar100_9\":\"" + Common.defEncode(ent.getinfo_nvar100_9())
                + "\",\"v_info_nvar100_10\":\"" + Common.defEncode(ent.getinfo_nvar100_10()) + "\"" +
                ",\"v_make_date_1|date\":\"" + Common.defEncode(ent.getmake_date_1())
                + "\",\"v_make_date_2|date\":\"" + Common.defEncode(ent.getmake_date_2()) + "\"" +
                ",\"v_info_key_2_1|date\":\"" + Common.defEncode(ent.getinfo_key_2_1())
                + "\",\"v_info_key_2_2|date\":\"" + Common.defEncode(ent.getinfo_key_2_2()) + "\"" +
                "}" +
                ",\"MachineCode\":\"" + Common.MachineCode
                + "\",\"RandomCode\":\"" + Common.RandomCode
                + "\",\"LoginType\":\"" + Common.LoginType + "\"}";

        Json = Common.defEncode(Json);
        return Json;
    }


    public static String MyinitiateTaskAdd(EntityWorkFlow ent) {

        String Json = "{\"procName\":\"Proc_MyinitiateTask_Add\",\"outStr\":\"r_result,r_error,r_content\"" +
                ",\"inJson\":{\"v_corp_tn\":\"" + Common.defEncode(Common.LoginUser.getcorp_tn()) + "\"" +
                ",\"v_wf_code\":\"" + Common.defEncode(ent.getwf_code())
                + "\",\"v_wf_name\":\"" + Common.defEncode(ent.getwf_name())
                + "\",\"v_task_code\":\"" + Common.defEncode(ent.gettask_code())
                + "\",\"v_task_name\":\"" + Common.defEncode(ent.gettask_name())
                + "\",\"v_init_pers_ucode\":\"" + Common.defEncode(ent.getinit_pers_ucode())
                + "\",\"v_init_pers_uname\":\"" + Common.defEncode(ent.getinit_pers_uname())
                + "\",\"v_init_pers_dcode\":\"" + Common.defEncode(ent.getinit_pers_dcode())
                + "\",\"v_init_pers_dname\":\"" + Common.defEncode(ent.getinit_pers_dname())
                + "\",\"v_init_pcode\":\"" + Common.defEncode(ent.getinit_pcode())
                + "\",\"v_init_pun\":\"" + Common.defEncode(ent.getinit_pun())
                + "\",\"v_init_pname\":\"" + Common.defEncode(ent.getinit_pname())
                + "\",\"v_init_date|date\":\"" + Common.defEncode(ent.getinit_date())
                + "\",\"v_stage_code\":\"" + Common.defEncode(ent.getstage_code())
                + "\",\"v_stage_name\":\"" + Common.defEncode(ent.getstage_name())
                + "\",\"v_task_prmvalue\":\"" + Common.defEncode(ent.gettask_prmvalue())
                + "\",\"v_doing_state|bool\":" + Common.defEncode(ent.getdoing_state())
                + ",\"v_task_condition|nclob\":\"" + Common.defEncode(ent.gettask_condition())
                + "\",\"v_tb_code\":\"" + Common.defEncode(ent.gettb_code())
                + "\",\"v_type_code\":\"" + Common.defEncode(ent.gettype_code())
                + "\",\"v_kind_code\":\"" + Common.defEncode(ent.getkind_code())
                + "\",\"v_task_key\":\"" + Common.defEncode(ent.gettask_key())
                + "\",\"v_if_check|bool\":" + Common.defEncode(Integer.toString(ent.getif_check()))
                + "},\"MachineCode\":\"" + Common.MachineCode
                + "\",\"RandomCode\":\"" + Common.RandomCode + "\",\"LoginType\":\"" + Common.LoginType + "\"}";

        Json = Common.defEncode(Json);
        return Json;
    }


    public static String OAMainUpdateTask(EntityOA ent) {
        String Json = "{\"procName\":\"Proc_OAMain_Update_Task\",\"outStr\":\"r_result,r_error\"" +
                ",\"inJson\":{\"v_corp_tn\":\"" + Common.defEncode(Common.LoginUser.getcorp_tn()) + "\"" +
                ",\"v_oa_code\":\"" + Common.defEncode(ent.getoa_code())
                + "\",\"v_wf_code\":\"" + Common.defEncode(ent.getwf_code())
                + "\",\"v_task_code\":\"" + Common.defEncode(ent.gettask_code())
                + "\"},\"MachineCode\":\"" + Common.MachineCode
                + "\",\"RandomCode\":\"" + Common.RandomCode + "\",\"LoginType\":\"" + Common.LoginType + "\"}";

        Json = Common.defEncode(Json);
        return Json;
    }


    public static String WFTaskSMS(String pers_un, String task_content) {
        String Json = "{\"Pers_un\":\"" + pers_un + "\""
                + ",\"Task_content\":\"" + task_content+"\""
                + ",\"MachineCode\":\"" + Common.MachineCode
                + "\",\"RandomCode\":\"" + Common.RandomCode
                + "\",\"LoginType\":\"" + Common.LoginType + "\"}";
        Json = Common.defEncode(Json);
        return Json;
    }

    public static String getServerTime() {
        String Json = "{\"MachineCode\":\"" + Common.MachineCode
                + "\",\"RandomCode\":\"" + Common.RandomCode
                + "\",\"LoginType\":\"" + Common.LoginType + "\"}";
        Json = Common.defEncode(Json);
        return Json;
    }
}
