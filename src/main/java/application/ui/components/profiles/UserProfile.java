package application.ui.components.profiles;

import application.backend.users.models.SpecificUser;
import application.backend.users.models.User;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.card.Card;
import lombok.Getter;

public abstract class UserProfile<T extends SpecificUser<?>> extends Card {

    @Getter
    protected final T profileSpecificUser;
    @Getter
    private final User profileUser;


    public UserProfile(T profileSpecificUser, User profileUser) {
        this.profileSpecificUser = profileSpecificUser;
        this.profileUser = profileUser;

        setTitle(getName());
        setSubtitle(getSchoolUnit());
        setHeaderPrefix(getAvatar());
        if (getDetails() != null) setHeaderSuffix(getDetails());
    }

    abstract Component getName();

    abstract Component getSchoolUnit();

    abstract Component getAvatar();

    abstract Component getDetails();
}
