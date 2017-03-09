package ru.shmakova.detbr.developer_settings;

import org.junit.Before;
import org.junit.Test;

import ru.shmakova.detbr.data.developer_settings.DeveloperSettingsModelImpl;

import static org.mockito.Matchers.anyBoolean;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class DeveloperSettingsPresenterTest {

    private DeveloperSettingsModelImpl developerSettingsModel;
    private DeveloperSettingsPresenter developerSettingsPresenter;
    private DeveloperSettingsView developerSettingsView;

    @Before
    public void beforeEachTest() {
        developerSettingsModel = mock(DeveloperSettingsModelImpl.class);
        developerSettingsPresenter = new DeveloperSettingsPresenter(developerSettingsModel);
        developerSettingsView = mock(DeveloperSettingsView.class);
    }

    @Test
    public void bindView_shouldSendBuildVersionCodeToTheView() {
        when(developerSettingsModel.getBuildVersionCode()).thenReturn("test build version code");

        developerSettingsPresenter.attachView(developerSettingsView);
        verify(developerSettingsView).changeBuildVersionCode("test build version code");
    }

    @Test
    public void bindView_shouldSendBuildVersionNameToTheView() {
        when(developerSettingsModel.getBuildVersionName()).thenReturn("test build version name");

        developerSettingsPresenter.attachView(developerSettingsView);
        verify(developerSettingsView).changeBuildVersionName("test build version name");
    }

    @Test
    public void bindView_shouldSendStethoEnabledStateToTheView() {
        when(developerSettingsModel.isStethoEnabled()).thenReturn(true);

        developerSettingsPresenter.attachView(developerSettingsView);
        verify(developerSettingsView).changeStethoState(true);
        verify(developerSettingsModel).isStethoEnabled();
    }

    @Test
    public void bindView_shouldSendStethoDisabledStateToTheView() {
        when(developerSettingsModel.isStethoEnabled()).thenReturn(false);

        developerSettingsPresenter.attachView(developerSettingsView);
        verify(developerSettingsView).changeStethoState(false);
        verify(developerSettingsModel).isStethoEnabled();
    }

    @Test
    public void bindView_shouldSendLeakCanaryEnabledStateToTheView() {
        when(developerSettingsModel.isLeakCanaryEnabled()).thenReturn(true);

        developerSettingsPresenter.attachView(developerSettingsView);
        verify(developerSettingsView).changeLeakCanaryState(true);
        verify(developerSettingsModel).isLeakCanaryEnabled();
    }

    @Test
    public void bindView_shouldSendLeakCanaryDisabledStateToTheView() {
        when(developerSettingsModel.isLeakCanaryEnabled()).thenReturn(false);

        developerSettingsPresenter.attachView(developerSettingsView);
        verify(developerSettingsView).changeLeakCanaryState(false);
        verify(developerSettingsModel).isLeakCanaryEnabled();
    }

    @Test
    public void bindView_shouldSendTinyDancerEnabledStateToTheView() {
        when(developerSettingsModel.isTinyDancerEnabled()).thenReturn(true);

        developerSettingsPresenter.attachView(developerSettingsView);
        verify(developerSettingsView).changeTinyDancerState(true);
        verify(developerSettingsModel).isTinyDancerEnabled();
    }

    @Test
    public void bindView_shouldSendTinyDancerDisabledStateToTheView() {
        when(developerSettingsModel.isTinyDancerEnabled()).thenReturn(false);

        developerSettingsPresenter.attachView(developerSettingsView);
        verify(developerSettingsView).changeTinyDancerState(false);
        verify(developerSettingsModel).isTinyDancerEnabled();
    }

    @Test
    public void changeStethoState_shouldNoOpIfStateAlreadySameAndEnabled() {
        when(developerSettingsModel.isStethoEnabled()).thenReturn(true);

        developerSettingsPresenter.attachView(developerSettingsView);
        developerSettingsPresenter.changeStethoState(true);

        verify(developerSettingsModel, never()).changeStethoState(anyBoolean());
        verify(developerSettingsView, never()).showMessage(anyString());
        verify(developerSettingsView, never()).showAppNeedsToBeRestarted();
    }

    @Test
    public void changeStethoState_shouldNoOpIfStateAlreadySameAndDisabled() {
        when(developerSettingsModel.isStethoEnabled()).thenReturn(false);

        developerSettingsPresenter.attachView(developerSettingsView);
        developerSettingsPresenter.changeStethoState(false);

        verify(developerSettingsModel, never()).changeStethoState(anyBoolean());
        verify(developerSettingsView, never()).showMessage(anyString());
        verify(developerSettingsView, never()).showAppNeedsToBeRestarted();
    }

    @Test
    public void changeStethoState_shouldEnableStethoAndNotifyView() {
        developerSettingsPresenter.attachView(developerSettingsView);

        developerSettingsPresenter.changeStethoState(true);
        verify(developerSettingsModel).changeStethoState(true);
        verify(developerSettingsView).showMessage("Stetho was enabled");
        verify(developerSettingsView, never()).showAppNeedsToBeRestarted();
    }

    @Test
    public void changeStethoState_shouldDisableStethoAndNotifyView() {
        developerSettingsPresenter.attachView(developerSettingsView);

        when(developerSettingsModel.isStethoEnabled()).thenReturn(true);
        developerSettingsPresenter.changeStethoState(false);
        verify(developerSettingsModel).changeStethoState(false);
        verify(developerSettingsView).showMessage("Stetho was disabled");
        verify(developerSettingsView).showAppNeedsToBeRestarted();
    }

    @Test
    public void changeStethoState_shouldDisableStethoAndNotifyViewAndAskAppRestartIfStethoWasAlreadyEnabled() {
        developerSettingsPresenter.attachView(developerSettingsView);

        when(developerSettingsModel.isStethoEnabled()).thenReturn(true);

        developerSettingsPresenter.changeStethoState(false);
        verify(developerSettingsModel).changeStethoState(false);
        verify(developerSettingsView).showMessage("Stetho was disabled");

        verify(developerSettingsView).showAppNeedsToBeRestarted();
    }

    @Test
    public void changeLeakCanaryState_shouldNoOpIfStateAlreadySameAndEnabled() {
        when(developerSettingsModel.isLeakCanaryEnabled()).thenReturn(true);

        developerSettingsPresenter.attachView(developerSettingsView);
        developerSettingsPresenter.changeLeakCanaryState(true);

        verify(developerSettingsModel, never()).changeLeakCanaryState(anyBoolean());
        verify(developerSettingsView, never()).showMessage(anyString());
        verify(developerSettingsView, never()).showAppNeedsToBeRestarted();
    }

    @Test
    public void changeLeakCanaryState_shouldNoOpIfStateAlreadySameAndDisabled() {
        when(developerSettingsModel.isLeakCanaryEnabled()).thenReturn(false);

        developerSettingsPresenter.attachView(developerSettingsView);
        developerSettingsPresenter.changeLeakCanaryState(false);

        verify(developerSettingsModel, never()).changeLeakCanaryState(anyBoolean());
        verify(developerSettingsView, never()).showMessage(anyString());
        verify(developerSettingsView, never()).showAppNeedsToBeRestarted();
    }

    @Test
    public void changeLeakCanaryState_shouldEnableLeakCanaryAndNotifyView() {
        developerSettingsPresenter.attachView(developerSettingsView);

        developerSettingsPresenter.changeLeakCanaryState(true);
        verify(developerSettingsModel).changeLeakCanaryState(true);
        verify(developerSettingsView).showMessage("LeakCanary was enabled");
        verify(developerSettingsView).showAppNeedsToBeRestarted();
    }

    @Test
    public void changeLeakCanaryState_shouldDisableLeakCanaryAndNotifyView() {
        developerSettingsPresenter.attachView(developerSettingsView);

        when(developerSettingsModel.isLeakCanaryEnabled()).thenReturn(true);

        developerSettingsPresenter.changeLeakCanaryState(false);
        verify(developerSettingsModel).changeLeakCanaryState(false);
        verify(developerSettingsView).showMessage("LeakCanary was disabled");
        verify(developerSettingsView).showAppNeedsToBeRestarted();
    }

    @Test
    public void changeTinyDancerState_shouldNoOpIfStateAlreadySameAndEnabled() {
        when(developerSettingsModel.isTinyDancerEnabled()).thenReturn(true);

        developerSettingsPresenter.attachView(developerSettingsView);
        developerSettingsPresenter.changeTinyDancerState(true);

        verify(developerSettingsModel, never()).changeTinyDancerState(anyBoolean());
        verify(developerSettingsView, never()).showMessage(anyString());
        verify(developerSettingsView, never()).showAppNeedsToBeRestarted();
    }

    @Test
    public void changeTinyDancerState_shouldNoOpIfStateAlreadySameAndDisabled() {
        when(developerSettingsModel.isTinyDancerEnabled()).thenReturn(false);

        developerSettingsPresenter.attachView(developerSettingsView);
        developerSettingsPresenter.changeTinyDancerState(false);

        verify(developerSettingsModel, never()).changeTinyDancerState(anyBoolean());
        verify(developerSettingsView, never()).showMessage(anyString());
        verify(developerSettingsView, never()).showAppNeedsToBeRestarted();
    }

    @Test
    public void changeTinyDancerState_shouldEnableTinyDancerAndNotifyView() {
        developerSettingsPresenter.attachView(developerSettingsView);

        developerSettingsPresenter.changeTinyDancerState(true);
        verify(developerSettingsModel).changeTinyDancerState(true);
        verify(developerSettingsView).showMessage("TinyDancer was enabled");
        verify(developerSettingsView, never()).showAppNeedsToBeRestarted();
    }

    @Test
    public void changeTinyDancerState_shouldDisableTinyDancerAndNotifyView() {
        when(developerSettingsModel.isTinyDancerEnabled()).thenReturn(true);
        developerSettingsPresenter.attachView(developerSettingsView);

        developerSettingsPresenter.changeTinyDancerState(false);
        verify(developerSettingsModel).changeTinyDancerState(false);
        verify(developerSettingsView).showMessage("TinyDancer was disabled");
        verify(developerSettingsView, never()).showAppNeedsToBeRestarted();
    }

}
