package notificationorganizer.yogrtman.com.notificationorganizer.TaskList

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import notificationorganizer.yogrtman.com.notificationorganizer.TaskList.TaskCalendarPage.CalendarFragment
import notificationorganizer.yogrtman.com.notificationorganizer.TaskList.TaskListPage.ListFragment

class TaskViewPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

    val NUM_PAGES: Int = 2;

    val TASK_LIST_INDEX: Int = 0;
    val TITLE_TASK_LIST: String = "List";

    val TASK_CALENDAR_INDEX: Int = 1;
    val TITLE_TASK_CALENDAR: String = "Calendar";

    override fun getCount(): Int  = NUM_PAGES;

    override fun getItem(i: Int): Fragment {
        var fragment: Fragment;
        when (i) {
            TASK_LIST_INDEX ->
                fragment = ListFragment();
            TASK_CALENDAR_INDEX ->
                fragment = CalendarFragment();
            else ->
                fragment = ListFragment();
        }
        return fragment;
    }

    override fun getPageTitle(position: Int): CharSequence {
        return when (position) {
            TASK_LIST_INDEX -> TITLE_TASK_LIST;
            TASK_CALENDAR_INDEX -> TITLE_TASK_CALENDAR;
            else -> "";
        }
    }
}