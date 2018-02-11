package us.buddman.cheers.activity

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import kotlinx.android.synthetic.main.activity_main.*
import us.buddman.cheers.R
import us.buddman.cheers.fragment.*
import us.buddman.cheers.utils.BaseActivity

class MainActivity : BaseActivity() {
    override val viewId: Int = R.layout.activity_main
    override val toolbarId: Int = 0

    lateinit var pagerAdapter: MainPagerAdapter
    override fun setDefault() {
        pagerAdapter = MainPagerAdapter(supportFragmentManager)
        mainPager.adapter = pagerAdapter
        mainPager.offscreenPageLimit = 4
        mainBottomBar.setOnTabSelectListener { tabId: Int ->
            when (tabId) {
                R.id.main_home -> mainPager.currentItem = 0
                R.id.main_cheer -> mainPager.currentItem = 1
                R.id.main_cheerline -> mainPager.currentItem = 2
                R.id.main_info -> mainPager.currentItem = 3
            }
        }
        mainPager.currentItem = 0
        mainBottomBar.setDefaultTab(R.id.main_home)
    }

    class MainPagerAdapter(fm: FragmentManager) : FragmentStatePagerAdapter(fm) {
        override fun getItem(position: Int): Fragment? {

            return when (position) {
                0 -> MainDashboardFragment()
                1 -> MainCheerFragment()
                2 -> MainCheerLinesFragment()
                3 -> MainMyInfoFragment()
                else -> null
            }
        }

        override fun getCount(): Int {
            return 4
        }

        override fun getPageTitle(position: Int): CharSequence {
            return ""
        }
    }

}
