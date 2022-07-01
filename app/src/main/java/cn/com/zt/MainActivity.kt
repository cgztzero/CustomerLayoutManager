package cn.com.zt

import android.os.Bundle
import android.os.Message
import android.os.MessageQueue
import android.provider.Settings
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import cn.com.zt.databinding.ActivityMainBinding
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.*


class MainActivity : AppCompatActivity() {
    private lateinit var activityMainBinding: ActivityMainBinding
    private lateinit var adapter: ImageAdapter
    private val imageOriginList = arrayListOf<String>()
    private val imageList = arrayListOf<String>()

    init {
        imageOriginList.add("https://img1.baidu.com/it/u=2446596364,2803953186&fm=253&fmt=auto&app=138&f=JPEG?w=500&h=625")
        imageOriginList.add("https://img1.baidu.com/it/u=1387792056,1045411987&fm=253&fmt=auto&app=138&f=JPEG?w=281&h=499")
        imageOriginList.add("https://img0.baidu.com/it/u=3508041821,2698276987&fm=253&fmt=auto&app=138&f=JPEG?w=400&h=534")
        imageOriginList.add("https://img0.baidu.com/it/u=3405302890,1535703917&fm=253&fmt=auto&app=138&f=JPEG?w=282&h=500")
        imageOriginList.add("https://img1.baidu.com/it/u=849813952,911218261&fm=253&fmt=auto&app=138&f=JPEG?w=231&h=500")
        imageOriginList.add("https://img2.baidu.com/it/u=2411433193,2790032740&fm=253&fmt=auto&app=138&f=JPEG?w=500&h=753")
        imageOriginList.add("https://img0.baidu.com/it/u=1378406799,164654982&fm=253&fmt=auto&app=138&f=JPEG?w=400&h=711")
        imageOriginList.add("https://img2.baidu.com/it/u=2703485616,488723820&fm=253&fmt=auto&app=138&f=PNG?w=460&h=817")
        imageOriginList.add("https://img1.baidu.com/it/u=3098978509,282365552&fm=253&fmt=auto&app=138&f=JPEG?w=420&h=746")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding.root)
        imageList.addAll(imageOriginList)

        adapter = ImageAdapter(this, imageList)
        val layoutManager = SlideLayoutManager(this)
        activityMainBinding.recyclerView.layoutManager = layoutManager
        activityMainBinding.recyclerView.adapter = adapter

        val itemTouchHelper = ItemTouchHelper(SlideCallBack(object : OnSwipeCallBack {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                //mock
                imageList.removeAt(viewHolder.adapterPosition)
                if (imageList.size <= layoutManager.maxShowCount + 1) {
                    imageList.addAll(0, imageOriginList)
                }
                adapter.notifyDataSetChanged()
            }

        }, layoutManager))
        itemTouchHelper.attachToRecyclerView(activityMainBinding.recyclerView)

    }

}