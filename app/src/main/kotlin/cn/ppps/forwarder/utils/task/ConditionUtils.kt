package cn.ppps.forwarder.utils.task

import com.google.gson.Gson
import cn.ppps.forwarder.database.entity.Rule
import cn.ppps.forwarder.entity.TaskSetting
import cn.ppps.forwarder.entity.condition.CronSetting
import cn.ppps.forwarder.entity.condition.SimSetting
import cn.ppps.forwarder.utils.DELAY_TIME_AFTER_SIM_READY
import cn.ppps.forwarder.utils.Log
import cn.ppps.forwarder.utils.TASK_CONDITION_CALL
import cn.ppps.forwarder.utils.TASK_CONDITION_CRON
import cn.ppps.forwarder.utils.TASK_CONDITION_SIM
import cn.ppps.forwarder.utils.TASK_CONDITION_SMS
import gatewayapps.crondroid.CronExpression
import java.util.Date
import kotlin.math.min

/**
 * 自动任务条件工具类
 */
class ConditionUtils private constructor() {

    companion object {

        private val TAG: String = ConditionUtils::class.java.simpleName

        //遍历条件列表，判断是否满足条件，默认不校验第一个条件（第一个条件是触发条件）
        fun checkCondition(taskId: Long, conditionList: MutableList<TaskSetting>, beginIndex: Int = 1, endIndex: Int = -1): Boolean {
            val untilIndex = if (endIndex == -1) conditionList.size else min(endIndex + 1, conditionList.size)
            if (beginIndex >= untilIndex) {
                Log.d(TAG, "TASK-$taskId：no condition need to check")
                return true
            }

            //注意：触发条件 = SIM卡已准备就绪/网络状态改变时，延迟5秒（给够搜索信号时间）才执行任务
            val firstCondition = conditionList.firstOrNull()
            val needDelay = (firstCondition?.type == TASK_CONDITION_SIM && TaskUtils.simState == 5)
            for (i in beginIndex until untilIndex) { //不包括untilIndex
                val condition = conditionList[i]
                when (condition.type) {
                    TASK_CONDITION_CRON -> {
                        val cronSetting = Gson().fromJson(condition.setting, CronSetting::class.java)
                        if (cronSetting == null) {
                            Log.d(TAG, "TASK-$taskId：cronSetting is null")
                            continue
                        }

                        val currentDate = if (needDelay) Date((Date().time / 1000) * 1000 - DELAY_TIME_AFTER_SIM_READY) else Date()
                        currentDate.time = currentDate.time / 1000 * 1000
                        val previousSecond = Date(currentDate.time - 1000)
                        val cronExpression = CronExpression(cronSetting.expression)
                        val nextValidTime = cronExpression.getNextValidTimeAfter(previousSecond)
                        nextValidTime.time = nextValidTime.time / 1000 * 1000
                        if (currentDate.time != nextValidTime.time) {
                            Log.d(TAG, "TASK-$taskId：cron condition is not satisfied")
                            return false
                        }

                        Log.d(TAG, "TASK-$taskId：cron condition is satisfied")
                    }

                    TASK_CONDITION_SIM -> {
                        val simSetting = Gson().fromJson(condition.setting, SimSetting::class.java)
                        if (simSetting == null) {
                            Log.d(TAG, "TASK-$taskId：simSetting is null")
                            continue
                        }

                        if (TaskUtils.simState != simSetting.simState) {
                            Log.d(TAG, "TASK-$taskId：simState is not match, simSetting = $simSetting")
                            return false
                        }

                        Log.d(TAG, "TASK-$taskId：simState is match, simSetting = $simSetting")
                    }

                    TASK_CONDITION_SMS, TASK_CONDITION_CALL -> {
                        val ruleSetting = Gson().fromJson(condition.setting, Rule::class.java)
                        if (ruleSetting == null) {
                            Log.d(TAG, "TASK-$taskId：ruleSetting is null")
                            continue
                        }
                        //TODO: 判断消息是否满足条件
                    }

                }
            }

            return true
        }

    }
}