package com.example.internetradioapp.presenter;

import android.app.Application;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.example.internetradioapp.R;
import com.example.internetradioapp.model.Channel;
import com.example.internetradioapp.model.ChannelRepository;
import com.example.internetradioapp.model.RetrofitHelper;
import com.example.internetradioapp.view.ChannelDetailFragment;
import com.example.internetradioapp.view.MainActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class ChannelListFragmentPresenter implements ChannelListPresenterInterface{
    Context context;
    List<Channel> channelList;

    public ChannelListFragmentPresenter(Context context) {
        this.context = context;

    }

    @Override
    public Channel searchChannel(String s){
        List<Channel> matchingChannel = getMatchingChannel(s);
        if (matchingChannel.size() == 1){
            if (matchingChannel.get(0).getTitle().toLowerCase().equals(s.toLowerCase())){ // exact match ignore case
                return matchingChannel.get(0);
            }
        }
        return null;
    }

    @Override
    public void populateRecyclerView(RecyclerView recyclerView) {
        new SetUpChannelRepoAsyncTask(context, recyclerView).execute();
    }

    @Override
    public void editRecyclerView(String s, RecyclerView recyclerView) {
        List<Channel> matchingChannel = getMatchingChannel(s);
        setUpRecyclerView(matchingChannel, recyclerView);
    }

    @Override
    public Bundle createChannelBundle(Channel searchResult) {
        Bundle bundle = new Bundle();
        bundle.putString("title", searchResult.getTitle());
        bundle.putString("dj", searchResult.getDj());
        bundle.putString("description", searchResult.getDescription());
        bundle.putString("djEmail", searchResult.getDjEmail());
        bundle.putString("listeners", searchResult.getListeners());
        bundle.putString("genre", searchResult.getGenre());
        bundle.putString("thumbnailUrlLarge", searchResult.getThumbnailUrlLarge());
        bundle.putString("previewUrl", searchResult.getPreviewUrl());
        return bundle;
    }

    private void setUpRecyclerView(List<Channel> channelList, RecyclerView recyclerView){
        CustomAdapter customAdapter = new CustomAdapter(context, channelList);
        recyclerView.setAdapter(customAdapter);
        LinearLayoutManager llManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(llManager);
        for (Channel channel: channelList){
            Log.d(channel.getDj(), channel.getThumbnailUrlSmall());
        }
    }

    // if dj or title has substring then match
    private List<Channel> getMatchingChannel(String s){
        List<Channel> matchingChannel = new ArrayList<Channel>();
        if (channelList == null){
            channelList = ChannelRepository.getINSTANCE((Application)context.getApplicationContext()).getAllChannels();
        }
        for (Channel channel : channelList){
            if (channel.getTitle().toLowerCase().contains(s.toLowerCase()) ||
                    channel.getDj().toLowerCase().contains(s.toLowerCase())){
                matchingChannel.add(channel);
            }
        }
        return matchingChannel;
    }

    private void loadChannelRepo(ChannelRepository channelRepo) {
        RetrofitHelper retrofitHelper = new RetrofitHelper(channelRepo);
        if (MainActivity.INTERNET_AVAILABLE) {
            retrofitHelper.makeRetrofitCall();
        }
        else{
            Toast.makeText(context,"Internet Access Permission Denied", Toast.LENGTH_SHORT).show();
        }
    }

    private class SetUpChannelRepoAsyncTask extends AsyncTask<Void, Void, List<Channel>> {
        private Context context;
        private RecyclerView recyclerView;

        SetUpChannelRepoAsyncTask(Context context, RecyclerView recyclerView){
            this.context = context;
            this.recyclerView = recyclerView;
        }

        @Override
        protected List<Channel> doInBackground(Void... voids) {
            loadChannelRepo(ChannelRepository.getINSTANCE((Application)context.getApplicationContext()));

            List<Channel> channelList = ChannelRepository.getINSTANCE((Application)context.getApplicationContext()).getAllChannels();
            return channelList;
        }

        @Override
        protected void onPostExecute(List<Channel> channelList) {
            Log.d("post execute", " list size = " + channelList.size());
            super.onPostExecute(channelList);
            ChannelListFragmentPresenter.this.channelList = channelList;
            setUpRecyclerView(channelList, recyclerView);
        }
    }

    private class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.CustomViewHolder> {
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
            final Channel channel = channelList.get(position);
            holder.clChannel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Bundle bundle = createChannelBundle(channel);
                        Fragment fragment = new ChannelDetailFragment();
                        fragment.setArguments(bundle);
                        ((MainActivity)context).addFragment(false, fragment);
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
