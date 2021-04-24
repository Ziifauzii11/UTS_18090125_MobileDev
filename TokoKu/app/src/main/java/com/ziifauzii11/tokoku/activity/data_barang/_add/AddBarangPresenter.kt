package com.ziifauzii11.tokoku.activity.data_barang._add

import com.ziifauzii11.tokoku.model.Barang
import com.ziifauzii11.tokoku.network.NetworkConfig
import com.ziifauzii11.tokoku.response.ResultSimple
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AddBarangPresenter(val addBarangView: AddBarangView) {
    fun addBarang(dataBarang: Barang) {
        NetworkConfig.service()
            .addBarang(Integer.parseInt(dataBarang.idUser), dataBarang.barcode, dataBarang.namaBarang, dataBarang.kategori, dataBarang.hargaBeli, dataBarang.hargaJual)
            .enqueue(object : Callback<ResultSimple> {
                override fun onFailure(call: Call<ResultSimple>, t: Throwable) {
                    addBarangView.onErrorAddBarang(t.localizedMessage)
                }

                override fun onResponse(
                    call: Call<ResultSimple>,
                    response: Response<ResultSimple>
                ) {
                    val body = response.body()
                    if (body?.status == 200) {
                        addBarangView.onSuccessAddBarang(body?.message)
                    } else {
                        addBarangView.onErrorAddBarang(body?.message)
                    }
                }

            })

    }

    fun updateBarang(dataBarang: Barang) {
        NetworkConfig.service()
            .updateBarang(Integer.parseInt(dataBarang.idUser), Integer.parseInt(dataBarang.idBarang),
                dataBarang.barcode, dataBarang.namaBarang, dataBarang.kategori, dataBarang.hargaBeli, dataBarang.hargaJual)
            .enqueue(object : Callback<ResultSimple> {
                override fun onFailure(call: Call<ResultSimple>, t: Throwable) {
                    addBarangView.onErrorAddBarang(t.localizedMessage)
                }

                override fun onResponse(
                    call: Call<ResultSimple>,
                    response: Response<ResultSimple>
                ) {
                    val body = response.body()
                    if (body?.status == 200) {
                        addBarangView.onSuccessAddBarang(body?.message)
                    } else {
                        addBarangView.onErrorAddBarang(body?.message)
                    }
                }

            })

    }
}