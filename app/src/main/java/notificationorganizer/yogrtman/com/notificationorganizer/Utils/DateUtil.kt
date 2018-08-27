package notificationorganizer.yogrtman.com.notificationorganizer.Utils

import android.util.Log
import sun.bob.mcalendarview.vo.DateData
import java.util.*

class DateUtil {
    companion object {
        val TAG = "DateUtil";

        fun getDateDataFromJavaData(javaDate: Date): DateData {
            var cal = Calendar.getInstance();
            cal.time = javaDate;

//            val dateData = DateData(
//                    cal.get(Calendar.YEAR),
//                    cal.get(Calendar.MONTH)+1,
//                    cal.get(Calendar.DAY_OF_MONTH)
//            );
//            Log.d(TAG, "getDateDataFromJavaData: returning " + dateData.year + "-" + dateData.month + "-" + dateData.day);

            return DateData(
                    cal.get(Calendar.YEAR),
                    cal.get(Calendar.MONTH)+1,
                    cal.get(Calendar.DAY_OF_MONTH)
            );
        }

        fun getJavaDateFromDateData(dateData: DateData): Date {
            var cal = Calendar.getInstance();
            cal.set(
                    dateData.year,
                    dateData.month-1,
                    dateData.day,
                    dateData.hour,
                    dateData.minute
            )
            return cal.time;
        }

        fun truncateToDay(date: Long): Long {
            return truncateToDay(Date(date));

        }

        fun truncateToDay(date: Date): Long {
            var calendar = Calendar.getInstance();
            calendar.time = date;

            calendar.set(Calendar.HOUR_OF_DAY, 0);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.SECOND, 0);
            calendar.set(Calendar.MILLISECOND, 0);

            return calendar.timeInMillis;
        }
    }
}