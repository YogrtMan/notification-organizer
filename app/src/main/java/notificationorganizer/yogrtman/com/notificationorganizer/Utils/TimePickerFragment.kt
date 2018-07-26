package notificationorganizer.yogrtman.com.notificationorganizer.Utils

import android.app.Dialog
import android.app.TimePickerDialog
import android.icu.util.Calendar
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.text.format.DateFormat
import android.widget.TimePicker

open class TimePickerFragment : DialogFragment() {

    var timeSetCallback: TimePickerDialog.OnTimeSetListener? = null;

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        timeSetCallback = context as TimePickerDialog.OnTimeSetListener;

        val c: Calendar = Calendar.getInstance();
        val hour: Int = c.get(Calendar.HOUR_OF_DAY);
        val minute = c.get(Calendar.MINUTE);

        return TimePickerDialog(activity, timeSetCallback, hour, minute, DateFormat.is24HourFormat(activity));
    }
}