package cn.ppps.forwarder.utils.task

import cn.ppps.forwarder.R
import cn.ppps.forwarder.utils.SP_DATA_SIM_SLOT
import cn.ppps.forwarder.utils.SP_IPV4
import cn.ppps.forwarder.utils.SP_IPV6
import cn.ppps.forwarder.utils.SP_IP_LIST
import cn.ppps.forwarder.utils.SP_NETWORK_STATE
import cn.ppps.forwarder.utils.SP_SIM_STATE
import cn.ppps.forwarder.utils.SP_WIFI_SSID
import cn.ppps.forwarder.utils.SharedPreference
import cn.ppps.forwarder.utils.TASK_ACTION_ALARM
import cn.ppps.forwarder.utils.TASK_ACTION_CLEANER
import cn.ppps.forwarder.utils.TASK_ACTION_NOTIFICATION
import cn.ppps.forwarder.utils.TASK_ACTION_RESEND
import cn.ppps.forwarder.utils.TASK_ACTION_RULE
import cn.ppps.forwarder.utils.TASK_ACTION_SENDER
import cn.ppps.forwarder.utils.TASK_ACTION_SENDSMS
import cn.ppps.forwarder.utils.TASK_ACTION_TASK
import cn.ppps.forwarder.utils.TASK_CONDITION_CALL
import cn.ppps.forwarder.utils.TASK_CONDITION_CRON
import cn.ppps.forwarder.utils.TASK_CONDITION_SIM
import cn.ppps.forwarder.utils.TASK_CONDITION_SMS

/**
 * 自动任务工具类 —— 用于存储自动任务相关的配置
 */
class TaskUtils private constructor() {

    companion object {

        //获取任务类型图标
        fun getTypeImageId(type: Int): Int {
            return when (type) {
                TASK_CONDITION_CRON -> R.drawable.auto_task_icon_custom_time
                TASK_CONDITION_SIM -> R.drawable.auto_task_icon_sim
                TASK_CONDITION_SMS -> R.drawable.auto_task_icon_sms
                TASK_CONDITION_CALL -> R.drawable.auto_task_icon_incall
                TASK_ACTION_SENDSMS -> R.drawable.auto_task_icon_sms
                TASK_ACTION_NOTIFICATION -> R.drawable.auto_task_icon_notification
                TASK_ACTION_CLEANER -> R.drawable.auto_task_icon_cleaner
                TASK_ACTION_RULE -> R.drawable.auto_task_icon_rule
                TASK_ACTION_SENDER -> R.drawable.auto_task_icon_sender
                TASK_ACTION_ALARM -> R.drawable.auto_task_icon_alarm
                TASK_ACTION_RESEND -> R.drawable.auto_task_icon_resend
                TASK_ACTION_TASK -> R.drawable.auto_task_icon_task
                else -> R.drawable.auto_task_icon_custom_time
            }
        }

        //获取任务类型图标（灰色）
        fun getTypeGreyImageId(type: Int): Int {
            return when (type) {
                TASK_CONDITION_CRON -> R.drawable.auto_task_icon_custom_time_grey
                TASK_CONDITION_SIM -> R.drawable.auto_task_icon_sim_grey
                TASK_CONDITION_SMS -> R.drawable.auto_task_icon_sms_grey
                TASK_CONDITION_CALL -> R.drawable.auto_task_icon_incall_grey
                TASK_ACTION_SENDSMS -> R.drawable.auto_task_icon_sms_grey
                TASK_ACTION_NOTIFICATION -> R.drawable.auto_task_icon_notification_grey
                TASK_ACTION_CLEANER -> R.drawable.auto_task_icon_cleaner_grey
                TASK_ACTION_RULE -> R.drawable.auto_task_icon_rule_grey
                TASK_ACTION_SENDER -> R.drawable.auto_task_icon_sender_grey
                TASK_ACTION_ALARM -> R.drawable.auto_task_icon_alarm_grey
                TASK_ACTION_RESEND -> R.drawable.auto_task_icon_resend_grey
                TASK_ACTION_TASK -> R.drawable.auto_task_icon_task_grey
                else -> R.drawable.auto_task_icon_custom_time_grey
            }
        }

        //网络状态：0-没有网络，1-移动网络，2-WiFi，3-以太网, 4-未知
        var networkState: Int by SharedPreference(SP_NETWORK_STATE, 0)

        //数据卡槽：0-未知，1-卡1，2-卡2
        var dataSimSlot: Int by SharedPreference(SP_DATA_SIM_SLOT, 0)

        //WiFi名称
        var wifiSsid: String by SharedPreference(SP_WIFI_SSID, "")

        //IPv4地址
        var ipv4: String by SharedPreference(SP_IPV4, "")

        //IPv6地址
        var ipv6: String by SharedPreference(SP_IPV6, "")

        //IP地址列表
        var ipList: String by SharedPreference(SP_IP_LIST, "")

        //SIM卡状态：0-未知状态，1-卡被移除，5-卡已准备就绪
        var simState: Int by SharedPreference(SP_SIM_STATE, 0)

    }
}