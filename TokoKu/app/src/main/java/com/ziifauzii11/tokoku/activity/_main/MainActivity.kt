package com.ziifauzii11.tokoku.activity._main

import android.os.Bundle
import android.util.Log
import android.util.Log.d
import android.view.Menu
import android.view.MenuItem
import com.ziifauzii11.tokoku.R
import com.ziifauzii11.tokoku.activity._main.adapter.MenuAdapter
import com.ziifauzii11.tokoku.activity._main.presenter.MainPresenter
import com.ziifauzii11.tokoku.activity._main.presenter.MainView
import com.ziifauzii11.tokoku.activity.data_barang.DataBarangActivity
import com.ziifauzii11.tokoku.activity.login.LoginActivity
import com.ziifauzii11.tokoku.activity.penjualan.PenjualanBarang
import com.ziifauzii11.tokoku.activity.report.ReportActivity
import com.ziifauzii11.tokoku.base.BaseActivity
import com.ziifauzii11.tokoku.model.Keranjang
import com.ziifauzii11.tokoku.utils.Uang
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.alert
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.yesButton

class MainActivity : BaseActivity(), MainView {

    private val TAG = MainActivity::class.java.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        cekSesi(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initActionButton()

    }

    private fun initActionButton() {
        mainMenu.adapter = MenuAdapter(object : MenuAdapter.OnMenuClick {
            override fun onClick(image: Int) {

                when(image) {
                    R.drawable.ic_goods -> openDataBarang()
                    R.drawable.ic_shopping_cart -> openDataPenjualan()
                    R.drawable.ic_report -> openDataLaporan()
                }
            }
        })
    }

    private fun openDataLaporan() {
        Log.d(TAG, "Data Laporan")
        startActivity<ReportActivity>(TAGS.USER to user)
    }

    private fun openDataPenjualan() {
        Log.d(TAG, "Penjualan")
        startActivity<PenjualanBarang>(TAGS.USER to user)
    }

    private fun openDataBarang() {
        Log.d(TAG, "Data Barang")
        startActivity<DataBarangActivity>(TAGS.USER to user)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        finish()
        startActivity<LoginActivity>()
        return super.onOptionsItemSelected(item)
    }

    override fun onResume() {
        super.onResume()
        refreshReport()
    }

    private fun refreshReport() {
        val presenter = MainPresenter(this)
        presenter.getReportLastDay(user?.idUser)
        presenter.getReportLastWeek(user?.idUser)
        presenter.getReportLastMonth(user?.idUser)
        presenter.getReportNowDay(user?.idUser)
        presenter.getReportNowWeek(user?.idUser)
        presenter.getReportNowMonth(user?.idUser)
    }

    // Last
    override fun onSuccessReportLastDay(keranjang: List<Keranjang?>?) {
        val total = countTotal(keranjang)
            alert {
                title = "Kemarin"
                message = "${Uang.indonesia(total.toDouble())}"
                yesButton {  }
            }.show()
    }

    override fun onSuccessReportLastWeek(keranjang: List<Keranjang?>?) {
        val total = countTotal(keranjang)

            alert {
                title = "Minggu kemarin"
                message = "${Uang.indonesia(total.toDouble())}"
                yesButton {  }
            }.show()
    }

    override fun onSuccessReportLastMonth(keranjang: List<Keranjang?>?) {
        val total = countTotal(keranjang)
            alert {
                title = "Bulan kemarin"
                message = "${Uang.indonesia(total.toDouble())}"
                yesButton {  }
            }.show()
    }
    // Now
    override fun onSuccessReportNowDay(keranjang: List<Keranjang?>?) {
        val total = countTotal(keranjang)
            alert {
                title = "Hari ini"
                message = "${Uang.indonesia(total.toDouble())}"
                yesButton {  }
            }.show()
    }

    override fun onSuccessReportNowWeek(keranjang: List<Keranjang?>?) {
        val total = countTotal(keranjang)
            alert {
                title = "Minggu ini"
                message = "${Uang.indonesia(total.toDouble())}"
                yesButton {  }
            }.show()
    }

    override fun onSuccessReportNowMonth(keranjang: List<Keranjang?>?) {
        val total = countTotal(keranjang)
            alert {
                title = "Bulan ini"
                message = "${Uang.indonesia(total.toDouble())}"
                yesButton {  }
            }.show()
    }


    private fun countTotal(keranjang: List<Keranjang?>?) : Int {
        var total: Double = 0.0
        keranjang?.iterator()?.forEach { i ->
            total += i?.totalHarga ?: 0.0
        }
        return total.toInt()
    }
    private fun singkat(ttl : Int) : String {
        return if (ttl < 1000) {
            val stotal = ttl.toString()
            stotal
        } else if (ttl < 1000000) {
            val stotal = ttl.toString()
            stotal.substring(0, stotal.length-3) + "rb"
        }else if (ttl < 1000000000) {
            val stotal = ttl.toString()
            stotal.substring(0, stotal.length-6) + "jt"
        } else {
            val stotal = ttl.toString()
            stotal.substring(0, stotal.length-9) + "m"
        }
    }

    // failed
    override fun onFailedReport(msg: String?) {
        d(MainActivity::class.java.simpleName, msg ?: "")
    }
}
