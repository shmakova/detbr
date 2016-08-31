package ru.yandex.detbr.ui.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ru.yandex.detbr.R;
import ru.yandex.detbr.data.tabs.models.Tab;
import rx.Observable;
import rx.subjects.PublishSubject;

/**
 * Created by shmakova on 29.08.16.
 */

public class TabsAdapter extends RecyclerView.Adapter<TabsAdapter.TabViewHolder> {
    private List<Tab> tabs;
    private final PublishSubject<Tab> onClickSubject = PublishSubject.create();

    @Override
    public TabViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.tabs_item, parent, false);
        return new TabViewHolder(convertView);
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

        TabViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(v -> onClickSubject.onNext(tabs.get(getAdapterPosition())));
        }

        public void bind(Tab tab) {
            url.setText(tab.getHost());

            if (tab.getTitle() != null && tab.getTitle().isEmpty()) {
                title.setVisibility(View.GONE);
            } else {
                title.setVisibility(View.VISIBLE);
                title.setText(tab.getTitle());
            }

            if (tab.getPreview() != null) {
                preview.setImageBitmap(tab.getPreview());
            }
        }

        @OnClick(R.id.remove_btn)
        void onRemoveButtonClick() {
            final int position = getAdapterPosition();
            tabs.remove(position);
            notifyItemRemoved(position);
        }
    }
}
