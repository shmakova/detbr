package ru.shmakova.detbr.tabs;

import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ru.shmakova.detbr.R;
import ru.shmakova.detbr.data.tabs.Tab;
import ru.shmakova.detbr.utils.UrlUtils;
import rx.Observable;
import rx.subjects.PublishSubject;

public class TabsAdapter extends RecyclerView.Adapter<TabsAdapter.TabViewHolder> {
    private List<Tab> tabs = new ArrayList<>();
    private final PublishSubject<Tab> onClickSubject = PublishSubject.create();
    private final OnRemoveTabButtonClickListener listener;

    public TabsAdapter(OnRemoveTabButtonClickListener onRemoveTabButtonClickListener) {
        this.listener = onRemoveTabButtonClickListener;
    }

    @Override
    public TabViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_tabs, parent, false);
        return new TabViewHolder(convertView, listener);
    }

    @Override
    public void onBindViewHolder(TabViewHolder holder, int position) {
        Tab tab = tabs.get(position);
        holder.bind(tab);
    }

    public Observable<Tab> getPositionClicks() {
        return onClickSubject.asObservable();
    }

    @Override
    public int getItemCount() {
        return tabs.size();
    }

    public List<Tab> getTabs() {
        return tabs;
    }

    public void setTabs(List<Tab> tabs) {
        this.tabs = tabs;
    }

    public class TabViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.url)
        TextView url;
        @BindView(R.id.title)
        TextView title;
        @BindView(R.id.preview)
        ImageView preview;

        private final OnRemoveTabButtonClickListener listener;

        TabViewHolder(View itemView, OnRemoveTabButtonClickListener onRemoveTabButtonClickListener) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(v -> onClickSubject.onNext(tabs.get(getAdapterPosition())));
            listener = onRemoveTabButtonClickListener;
        }

        public void bind(Tab tab) {
            url.setText(UrlUtils.getHost(tab.getUrl()));

            if (tab.getTitle() != null && tab.getTitle().isEmpty()) {
                title.setVisibility(View.GONE);
            } else {
                title.setVisibility(View.VISIBLE);
                title.setText(tab.getTitle());
            }

            preview.setImageDrawable(ContextCompat.getDrawable(
                    preview.getContext(),
                    R.drawable.ic_empty_tab));

            if (tab.getPreview() != null) {
                preview.setImageBitmap(tab.getPreview());
            }
        }

        @OnClick(R.id.remove_btn)
        void onRemoveButtonClick() {
            final int position = getAdapterPosition();
            listener.onRemoveButtonClick(tabs.get(position));
            notifyItemRemoved(position);
        }
    }
}
