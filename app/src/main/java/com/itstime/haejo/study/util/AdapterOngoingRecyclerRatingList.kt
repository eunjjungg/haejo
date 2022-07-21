package com.itstime.haejo.study.util
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.itstime.haejo.R
import com.itstime.haejo.api.StudyMemberDTO
import com.itstime.haejo.databinding.CustomRecyclerLoadingBinding
import com.itstime.haejo.databinding.CustomRecyclerRatingBinding
import com.itstime.haejo.util.AppSetting

class AdapterOngoingRecyclerRatingList
    : RecyclerView.Adapter<RecyclerView.ViewHolder>(){
    private val VIEW_TYPE_ITEM = 0
    private val VIEW_TYPE_LOADING = 1

    var listData = mutableListOf<StudyMemberDTO>()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerView.ViewHolder {
        return when (viewType) {
            VIEW_TYPE_ITEM -> {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = CustomRecyclerRatingBinding.inflate(layoutInflater, parent, false)
                PostViewHolder(binding)
            }
            //VIEW_TYPE_LOADING
            else -> {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = CustomRecyclerLoadingBinding.inflate(layoutInflater, parent, false)
                LoadingViewHolder(binding)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if(holder is PostViewHolder)
            holder.bind(listData[position])
    }

    override fun getItemCount(): Int {
        return listData.size
    }

    override fun getItemViewType(position: Int): Int {
        return when(listData[position].nickname) {
            " " -> VIEW_TYPE_LOADING
            else -> VIEW_TYPE_ITEM
        }
    }

    inner class PostViewHolder(private val binding: CustomRecyclerRatingBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(item: StudyMemberDTO) {
            //nickname
            if(item.grade == "host")
                binding.tvNickname.setText(item.nickname + " 👑")
            else
                binding.tvNickname.setText(item.nickname)

            //battery
            when(item.battery) {
                in 0..20 -> binding.imgBattery.setImageDrawable(binding.root.resources.getDrawable(R.drawable.ic_battery_20))
                in 21..40 -> binding.imgBattery.setImageDrawable(binding.root.resources.getDrawable(R.drawable.ic_battery_40))
                in 41..70 -> binding.imgBattery.setImageDrawable(binding.root.resources.getDrawable(R.drawable.ic_battery_70))
                in 71..100 -> binding.imgBattery.setImageDrawable(binding.root.resources.getDrawable(R.drawable.ic_battery_90))
            }
            binding.tvBattery.setText(item.battery.toString() + "%")

            when(item.profile) {
                0 -> binding.imgProfile.setImageDrawable(binding.root.resources.getDrawable(R.drawable.ic_profile_0))
                1 -> binding.imgProfile.setImageDrawable(binding.root.resources.getDrawable(R.drawable.ic_profile_1))
                2 -> binding.imgProfile.setImageDrawable(binding.root.resources.getDrawable(R.drawable.ic_profile_2))
                3 -> binding.imgProfile.setImageDrawable(binding.root.resources.getDrawable(R.drawable.ic_profile_3))
            }

            if(item.memberId == AppSetting.prefs.getMemberId().toLong())
                binding.btnRating.isEnabled = false
        }
    }

    inner class LoadingViewHolder(private val binding: CustomRecyclerLoadingBinding): RecyclerView.ViewHolder(binding.root) {

    }
}