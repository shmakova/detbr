package ru.yandex.detbr.ui.fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.github.pedrovgs.lynx.LynxActivity;
import com.github.pedrovgs.lynx.LynxConfig;
import com.jakewharton.processphoenix.ProcessPhoenix;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;
import ru.yandex.detbr.App;
import ru.yandex.detbr.R;
import ru.yandex.detbr.di.components.DeveloperSettingsComponent;
import ru.yandex.detbr.performance.AnyThread;
import ru.yandex.detbr.ui.presenters.DeveloperSettingsPresenter;
import ru.yandex.detbr.ui.views.DeveloperSettingsView;

public class DeveloperSettingsFragment extends BaseMvpFragment<DeveloperSettingsView, DeveloperSettingsPresenter> implements DeveloperSettingsView {
    @Inject
    LynxConfig lynxConfig;

    @BindView(R.id.developer_settings_build_version_code_text_view)
    TextView buildVersionCodeTextView;

    @BindView(R.id.developer_settings_build_version_name_text_view)
    TextView buildVersionNameTextView;

    @BindView(R.id.developer_settings_stetho_switch)
    Switch stethoSwitch;

    @BindView(R.id.developer_settings_leak_canary_switch)
    Switch leakCanarySwitch;

    @BindView(R.id.developer_settings_tiny_dancer_switch)
    Switch tinyDancerSwitch;

    @NonNull
    private DeveloperSettingsComponent developerSettingsComponent;
    private Handler mainThreadHandler;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mainThreadHandler = App.get(context).applicationComponent().mainThreadHandler();
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        injectDependencies();
    }

    private void injectDependencies() {
        developerSettingsComponent = App.get(getContext()).applicationComponent().plusDeveloperSettingsComponent();
        developerSettingsComponent.inject(this);
    }

    @NonNull
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_developer_settings, container, false);
    }

    @NonNull
    @Override
    public DeveloperSettingsPresenter createPresenter() {
        return developerSettingsComponent.presenter();
    }

    @OnCheckedChanged(R.id.developer_settings_stetho_switch)
    void onStethoSwitchCheckedChanged(boolean checked) {
        presenter.changeStethoState(checked);
    }

    @OnCheckedChanged(R.id.developer_settings_tiny_dancer_switch)
    void onTinyDancerSwitchCheckedChanged(boolean checked) {
        presenter.changeTinyDancerState(checked);
    }

    @OnClick(R.id.developer_settings_restart_app_button)
    void onRestartAppClick() {
        final FragmentActivity activity = getActivity();
        ProcessPhoenix.triggerRebirth(activity, new Intent(activity, activity.getClass()));
    }

    @Override
    @AnyThread
    public void changeBuildVersionCode(@NonNull String versionCode) {
        runOnUiThreadIfFragmentAlive(() -> {
            if (buildVersionCodeTextView != null) {
                buildVersionCodeTextView.setText(versionCode);
            }
        });
    }

    @Override
    @AnyThread
    public void changeBuildVersionName(@NonNull String versionName) {
        runOnUiThreadIfFragmentAlive(() -> {
            if (buildVersionNameTextView != null) {
                buildVersionNameTextView.setText(versionName);
            }
        });
    }

    @Override
    @AnyThread
    public void changeStethoState(boolean enabled) {
        runOnUiThreadIfFragmentAlive(() -> {
            if (stethoSwitch != null) {
                stethoSwitch.setChecked(enabled);
            }
        });
    }

    @Override
    @AnyThread
    public void changeLeakCanaryState(boolean enabled) {
        runOnUiThreadIfFragmentAlive(() -> {
            if (leakCanarySwitch != null) {
                leakCanarySwitch.setChecked(enabled);
            }
        });
    }

    @Override
    @AnyThread
    public void changeTinyDancerState(boolean enabled) {
        runOnUiThreadIfFragmentAlive(() -> {
            if (tinyDancerSwitch != null) {
                tinyDancerSwitch.setChecked(enabled);
            }
        });
    }

    @SuppressLint("ShowToast") // Yeah, Lambdas and Lint are not good friends…
    @Override
    @AnyThread
    public void showMessage(@NonNull String message) {
        runOnUiThreadIfFragmentAlive(() -> Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show());
    }

    @OnCheckedChanged(R.id.developer_settings_leak_canary_switch)
    void onLeakCanarySwitchCheckedChanged(boolean checked) {
        presenter.changeLeakCanaryState(checked);
    }

    @SuppressLint("ShowToast") // Yeah, Lambdas and Lint are not good friends…
    @Override
    @AnyThread
    public void showAppNeedsToBeRestarted() {
        runOnUiThreadIfFragmentAlive(() -> Toast.makeText(getContext(), "To apply new settings app needs to be restarted", Toast.LENGTH_LONG).show());
    }

    @OnClick(R.id.b_show_log)
    void showLog() {
        Context context = getActivity();
        context.startActivity(LynxActivity.getIntent(context, lynxConfig));
    }

    protected void runOnUiThreadIfFragmentAlive(@NonNull Runnable runnable) {
        if (Looper.myLooper() == Looper.getMainLooper() && isFragmentAlive()) {
            runnable.run();
        } else {
            if (mainThreadHandler != null) {
                mainThreadHandler.post(() -> {
                    if (isFragmentAlive()) {
                        runnable.run();
                    }
                });
            }
        }
    }

    private boolean isFragmentAlive() {
        return getActivity() != null && isAdded() && !isDetached() && getView() != null && !isRemoving();
    }

    @Override
    public void onDestroy() {
        App.get(getContext()).applicationComponent().leakCanaryProxy().watch(this);
        super.onDestroy();
    }
}
