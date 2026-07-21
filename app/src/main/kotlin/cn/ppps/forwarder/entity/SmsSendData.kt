package cn.ppps.forwarder.entity

data class SmsSendData(
    val simSlot: Int = 1,
    val phoneNumbers: String = "",
    val msgContent: String = ""
)
