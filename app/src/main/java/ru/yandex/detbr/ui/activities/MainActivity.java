package ru.yandex.detbr.ui.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.Toast;

import javax.inject.Inject;
import javax.inject.Named;

import ru.yandex.detbr.App;
import ru.yandex.detbr.R;
import ru.yandex.detbr.cards.Card;
import ru.yandex.detbr.developer_settings.DeveloperSettingsModule;
import ru.yandex.detbr.ui.fragments.CardsFragment;
import ru.yandex.detbr.ui.fragments.ContentFragment;
import ru.yandex.detbr.ui.other.ViewModifier;

public class MainActivity extends BaseActivity implements
        ContentFragment.OnBrowserButtonClickListener,
        CardsFragment.OnCardsItemClickListener {

    @Inject @Named(DeveloperSettingsModule.MAIN_ACTIVITY_VIEW_MODIFIER)
    ViewModifier viewModifier;

    @SuppressLint("InflateParams")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        App.get(this).applicationComponent().inject(this);

        setContentView(viewModifier.modify(getLayoutInflater().inflate(R.layout.activity_main, null)));

        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.main_frame_layout, new CardsFragment())
                    .commit();
        }
    }

    @Override
    public void onBrowserButtonCLick(String url) {
        Intent intent = new Intent(this, BrowserActivity.class);
        intent.setData(Uri.parse(url));
        intent.setAction(Intent.ACTION_VIEW);
        startActivity(intent);
    }

    @Override
    public void onCardsItemClick(Card card) {
        Toast.makeText(this, card.getUrl(), Toast.LENGTH_LONG).show();
        Intent intent = new Intent(this, BrowserActivity.class);
        intent.setData(Uri.parse(card.getUrl()));
        intent.setAction(Intent.ACTION_VIEW);
        startActivity(intent);
    }
}
