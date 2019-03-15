package br.com.renannunessil.wirecardtest.utils

import java.time.LocalDate
import java.time.format.DateTimeFormatter

class DateUtils {
    companion object {
        fun formatDate(stringDate: String): String {
            var string = stringDate.split("T")[0]
            val listDate = string.split("-")
            string = listDate[2] + "/" + listDate[1] + "/" + listDate[0]
            return string
        }
    }
}