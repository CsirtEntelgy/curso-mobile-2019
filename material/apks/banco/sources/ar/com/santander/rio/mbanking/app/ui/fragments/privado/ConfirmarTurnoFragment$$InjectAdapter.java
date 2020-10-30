package ar.com.santander.rio.mbanking.app.ui.fragments.privado;

import ar.com.santander.rio.mbanking.app.base.BaseFragment;
import ar.com.santander.rio.mbanking.managers.data.IDataManager;
import dagger.MembersInjector;
import dagger.internal.Binding;
import dagger.internal.Linker;
import java.util.Set;
import javax.inject.Provider;

public final class ConfirmarTurnoFragment$$InjectAdapter extends Binding<ConfirmarTurnoFragment> implements MembersInjector<ConfirmarTurnoFragment>, Provider<ConfirmarTurnoFragment> {
    private Binding<IDataManager> a;
    private Binding<BaseFragment> b;

    public ConfirmarTurnoFragment$$InjectAdapter() {
        super("ar.com.santander.rio.mbanking.app.ui.fragments.privado.ConfirmarTurnoFragment", "members/ar.com.santander.rio.mbanking.app.ui.fragments.privado.ConfirmarTurnoFragment", false, ConfirmarTurnoFragment.class);
    }

    public void attach(Linker linker) {
        this.a = linker.requestBinding("ar.com.santander.rio.mbanking.managers.data.IDataManager", ConfirmarTurnoFragment.class, getClass().getClassLoader());
        this.b = linker.requestBinding("members/ar.com.santander.rio.mbanking.app.base.BaseFragment", ConfirmarTurnoFragment.class, getClass().getClassLoader(), false, true);
    }

    public void getDependencies(Set<Binding<?>> set, Set<Binding<?>> set2) {
        set2.add(this.a);
        set2.add(this.b);
    }

    public ConfirmarTurnoFragment get() {
        ConfirmarTurnoFragment confirmarTurnoFragment = new ConfirmarTurnoFragment();
        injectMembers(confirmarTurnoFragment);
        return confirmarTurnoFragment;
    }

    public void injectMembers(ConfirmarTurnoFragment confirmarTurnoFragment) {
        confirmarTurnoFragment.mDataManager = (IDataManager) this.a.get();
        this.b.injectMembers(confirmarTurnoFragment);
    }
}
