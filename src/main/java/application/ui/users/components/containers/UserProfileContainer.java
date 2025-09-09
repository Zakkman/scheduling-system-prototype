package application.ui.users.components.containers;

import application.ui.users.components.cards.profiles.UserProfile;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class UserProfileContainer extends VerticalLayout {

    public void addUserProfile(UserProfile<?> userProfile) {
        add(userProfile);
    }

    public int getProfileCount() {
        return getComponentCount();
    }

    public Optional<? extends UserProfile<?>> getUserProfile() {
        return getChildren()
            .filter(component -> component instanceof UserProfile)
            .map(component -> (UserProfile<?>) component)
            .findFirst();
    }

    public List<UserProfile<?>> getAllProfiles() {
        return getChildren()
            .filter(component -> component instanceof UserProfile)
            .map(component -> (UserProfile<?>) component)
            .collect(Collectors.toList());
    }
}
