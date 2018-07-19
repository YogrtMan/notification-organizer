//package notificationorganizer.yogrtman.com.notificationorganizer.Utils
//
//import android.app.DatePickerDialog
//import android.app.Dialog
//import android.app.TimePickerDialog
//import android.icu.util.Calendar
//import android.os.Bundle
//import android.support.v4.app.DialogFragment
//import android.text.format.DateFormat
//import android.widget.DatePicker
//import android.widget.TimePicker
//
//open class DatePickerFragment : DialogFragment(), DatePickerDialog.OnDateSetListener {
//
//    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
//        val c: Calendar = Calendar.getInstance();
//        val year: Int = c.get(Calendar.YEAR);
//        val month: Int = c.get(Calendar.MONTH);
//        val day: Int = c.get(Calendar.DAY_OF_MONTH);
//
//        return DatePickerDialog(activity, this, year, month, day);
//    }
//
//    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
//        if (activity != null) {
//            var fragment: DialogFragment = TimePickerFragment();
//            fragment.show(activity.supportFragmentManager, "timePicker");
//        }
//    }
//
//}