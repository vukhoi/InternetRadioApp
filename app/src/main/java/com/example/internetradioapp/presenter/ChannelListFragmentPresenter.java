package com.example.internetradioapp.presenter;

import android.app.Application;
import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.internetradioapp.R;
import com.example.internetradioapp.model.Channel;
import com.example.internetradioapp.model.ChannelRepository;
import com.example.internetradioapp.model.RetrofitHelper;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.List;

public class ChannelListFragmentPresenter {
    Application application;
    Context context;

    public ChannelListFragmentPresenter(Context context) {
        this.context = context;
    }


    public void populateRecyclerView(RecyclerView recyclerView) {
        ChannelRepository channelRepo = new ChannelRepository((Application) (context.getApplicationContext()));
        loadChannelRepo(channelRepo);
        List<Channel> channelList = channelRepo.getAllChannels();

        // set up recycler view
        CustomAdapter customAdapter = new CustomAdapter(context, channelList);
        recyclerView.setAdapter(customAdapter);
        LinearLayoutManager llManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(llManager);

    }

    private void loadChannelRepo(ChannelRepository channelRepo) {
        RetrofitHelper retrofitHelper = new RetrofitHelper(channelRepo);
        retrofitHelper.makeRetrofitCall();
    }


    class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.CustomViewHolder> {
        private List<Channel> channelList;
        private Context context;

        public CustomAdapter(Context context, List<Channel> channelList) {
            this.channelList = channelList;
            this.context = context;
        }

        @NonNull
        @Override
        public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            LinearLayout itemLayout = (LinearLayout) inflater.inflate(R.layout.item_layout, parent, false);
            ConstraintLayout cl = itemLayout.findViewById(R.id.cl_channel);
            if (cl.getParent() != null) {
                ((ViewGroup) cl.getParent()).removeView(cl);
            }
            return new CustomViewHolder(cl);
        }

        @Override
        public void onBindViewHolder(@NonNull CustomViewHolder holder, int position) {
            Channel channel = channelList.get(position);
            holder.clChannel.setOnClickListener(new View.OnClickListener() {
                                                    @Override
                                                    public void onClick(View view) {
                                                        // todo activity function : remove this fragment, add ChannelDetailFragment
                                                    }
                                                }
            );
            Picasso.get().load(channel.getThumbnailUrlSmall()).into(holder.ivChannelThumbnail);
            holder.tvTitle.setText(channel.getTitle());
            holder.tvDj.setText(channel.getDj());
            holder.tvDescription.setText(channel.getDescription());
        }


        @Override
        public int getItemCount() {
            return channelList.size();
        }


        class CustomViewHolder extends RecyclerView.ViewHolder {
            ConstraintLayout clChannel;
            ImageView ivChannelThumbnail;
            TextView tvTitle, tvDj, tvDescription;

            CustomViewHolder(View itemView) {
                super(itemView);
                clChannel = (ConstraintLayout) itemView;
                ivChannelThumbnail = itemView.findViewById(R.id.iv_channel_thumbnail);
                tvTitle = itemView.findViewById(R.id.tv_title);
                tvDj = itemView.findViewById(R.id.tv_dj);
                tvDescription = itemView.findViewById(R.id.tv_description);
            }
        }
    }
}
