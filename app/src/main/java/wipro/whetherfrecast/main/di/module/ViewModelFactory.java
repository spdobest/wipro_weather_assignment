package wipro.whetherfrecast.main.di.module;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import wipro.whetherfrecast.main.ui.viewmodel.WeatherViewModel;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class ViewModelFactory implements ViewModelProvider.Factory {

    private final WeatherViewModel weatherViewModel;


    @Inject
    public ViewModelFactory(WeatherViewModel myViewModel) {
        weatherViewModel = myViewModel;
    }

    @SuppressWarnings("unchecked")
    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        ViewModel viewModel;
        if (modelClass == WeatherViewModel.class) {
            viewModel = weatherViewModel;
        } else {
            throw new RuntimeException("unsupported view model class: " + modelClass);
        }

        return (T) viewModel;
    }
}